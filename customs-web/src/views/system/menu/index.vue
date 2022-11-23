<template>
  <div class="menu-container scroll-y p">
    <div class="filter-item m">
      <el-button type="primary" :icon="Plus" @click="handleAdd">新增</el-button>
    </div>
    <el-card class="my-2">
      <el-table
          ref="table"
          :data="tableData"
          style="width: 100%"
          row-key="id"
          :load="getChildMenus"
          lazy
          fit
          border
          highlight-current-row
          default-expand-all
          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      >
        <el-table-column type="index" label="序号" width="55"/>
        <el-table-column prop="title" label="菜单标题" width="165" show-overflow-tooltip/>
        <el-table-column prop="icon" label="图标" align="center" width="70">
          <template #default="scope">
            <el-icon :size="16">
              <component :is="ElSvg[scope.row.icon]"/>
            </el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="70" align="center"/>
        <el-table-column prop="component" label="组件路径" show-overflow-tooltip align="center">
          <template #default="scope">
            <span v-if="scope.row.component == 'Layout'"></span>
            <span v-else>{{ scope.row.component == undefined ? '--' : scope.row.component }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="菜单类型" align="center" width="80">
          <template #default="scope">
            <el-tag v-if="scope.row.type == 1" type="success">目录</el-tag>
            <el-tag v-if="scope.row.type == 2" type="warning">菜单</el-tag>
            <el-tag v-if="scope.row.type == 3" type="danger">按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="hidden" label="是否可见" align="center">
          <template #default="scope">
            <el-switch
                v-model="scope.row.hidden"
                disabled
                :active-value="false"
                active-text="是"
                :inactive-value="true"
                inactive-text="否"
            />
          </template>
        </el-table-column>
        <el-table-column prop="date" label="创建时间" show-overflow-tooltip align="center">
          <template #default="scope">
            {{ parseTime(scope.row.create_time) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template #default="scope">
            <el-button type="primary" :disabled="scope.row.action == 1" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" :disabled="scope.row.action == 1" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <dialog-menu :dialog-menu="dialogData" :form-data="formData" @handleSubmitAdd="handleSubmitAdd"
                 @handleSubmitEdit="handleSubmitEdit"/>
  </div>
</template>

<script setup lang="ts">
import * as ElSvg from '@element-plus/icons-vue'
import {dialogTy} from '~/dialog'
import DialogMenu from './dialogMenu.vue'
import {Ref} from 'vue'
import {addMenu, delMenu, updateMenu, getMenuParentList} from "@/api/menu";
import {ElMessage, ElMessageBox} from 'element-plus'
import {Plus} from "@element-plus/icons-vue";
import {parseTime} from '@/utils/dateTime'

const tableData = ref(null)

onMounted(() => {
  getMenuList()
})

/**
 * 新增
 */
const dialogData: Ref<dialogTy> = ref({})//ts的规范写法，定义变量的类型
let formData = ref({})//这是相对自由点的写法，不定义类型
let oldPid = ref({})
const handleAdd = () => {
  dialogData.value = {
    show: true,
    title: '新增菜单',
    option: 'add'
  }

  formData.value = {
    type: 1,
    hidden: 1,
    pid: '',
    icon: '',
    path: '',
    title: '',
    sort: '',
    name: '',
    component: '',
    permissions: '',
    noCache: '0',
  }
}

const handleEdit = (row) => {
  dialogData.value = {
    show: true,
    title: '修改菜单',
    option: 'edit'
  }

  formData.value = {
    id: row.id,
    type: row.type,
    icon: row.icon,
    hidden: row.hidden === true ? '0' : '1',
    title: row.title,
    path: row.path,
    sort: row.sort,
    name: row.name,
    component: row.component,
    pid: row.pid + '',
    permissions: row.permissions,
    noCache: row.noCache === 0 ? '0' : '1'
  }
  oldPid = row.pid
}

const handleDelete = (row) => {
  ElMessageBox.confirm('此操作将永久删除该菜单，并删除所属子级, 是否继续?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    delMenu(row.id)
        .then(res => {
          ElMessage({message: '删除成功', type: 'success'})
          getMenuList()
        })
  }).catch(() => {
  })
}


//调用后台
const store = useStore() //假如页面需要用到store.则需要使用这个。我们把请求后端的代码封装在这里，所以要调用api的时候就要引入这个

const getMenuList = () => {
  store
      .dispatch('menu/getMenu') //请求后端接口，可以像这样到vuex去请求，也可以直接到api中去请求。注意：要代token的不需要另行处理，我已经封装好了。
      .then((response) => {
        tableData.value = response
      })
      .catch((res) => {
      })
}

function handleSubmitAdd() {
  let requestData = formData._rawValue
  var pidArr = requestData.pid
  requestData.pid = pidArr[pidArr.length - 1];
  addMenu(requestData).then(res => {
    dialogData.value.show = false
    ElMessage({message: '新增成功！', type: 'success'})
    if (formData.value.pid !== 0) {
      // 实现无感刷新
      getMenuParentList({pid: formData.value.pid}).then(res => {
        // this.$set(this.$refs.table.store.states.lazyTreeNodeMap, this.formData.pid, JSON.parse(res.data))
      })
      getMenuList()
    } else {
      getMenuList()
    }
  })
}

function handleSubmitEdit() {
  if (formData.value.id === formData.value.pid) {
    ElMessage({message: '自己不能成为自己的下级！', type: 'error'})
    return false
  }
  let requestData = formData._rawValue
  var pidArr = requestData.pid
  if (pidArr instanceof Array) {
    requestData.pid = pidArr[pidArr.length - 1];
  } else {
    requestData.pid = pidArr
  }
  updateMenu(formData.value.id, requestData).then(res => {
    dialogData.value.show = false
    ElMessage({message: '修改成功！', type: 'success'})
    if (formData.value.pid !== 0) {
      if (formData.value.pid !== oldPid) {
        // 这时候说明已经改变了上级类目
        getMenuParentList({pid: oldPid}).then(res => {
          // this.$set(this.$refs.table.store.states.lazyTreeNodeMap, this.oldPid, JSON.parse(res.data))
          getMenuParentList({pid: formData.value.pid}).then(res => {
            // this.$set(this.$refs.table.store.states.lazyTreeNodeMap, this.formData.pid, JSON.parse(res.data))
          })
        })
      } else {
        getMenuParentList({pid: formData.value.pid}).then(res => {
          // this.$set(this.$refs.table.store.states.lazyTreeNodeMap, this.formData.pid, JSON.parse(res.data))
        })
      }
      getMenuList()
    } else {
      getMenuList()
    }
    // 无感刷新子节点数据
    getMenuParentList({pid: formData.value.pid}).then(res => {
      // this.$set(this.$refs.table.store.states.lazyTreeNodeMap, this.formData.pid, res.data)
    })
  })
}
</script>
