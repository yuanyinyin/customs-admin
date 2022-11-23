<template>
  <div class="roles-container scroll-y p">
        <!--工具栏-->
        <div class="head-container m-1 mr-3 rowSS">
            <!-- 搜索 -->
            <el-form :inline="true" :model="listQuery" class="demo-form-inline">
              <el-form-item label="角色名称">
                <el-input
                    v-model="listQuery.roleName"
                    placeholder="角色名称"
                    class="filter-item"
                    @keyup.enter.native="handleFilter" clearable
                />
              </el-form-item>
              <el-form-item label="角色编码">
                <el-input
                    v-model="listQuery.roleCode"
                    placeholder="角色编码"
                    class="filter-item"
                    @keyup.enter.native="handleFilter" clearable
                />
              </el-form-item>
              <el-form-item>
              <span class="filter-item">
                <el-button type="success" :icon="Search" @click="handleFilter">查询</el-button>
                <el-button type="primary" :icon="Plus" @click="handleAdd">新增</el-button>
              </span>
              </el-form-item>
            </el-form>
        </div>
      <el-card class="m-1">
        <template #header>
          <div class="clearfix">
            <span>角色列表</span>
          </div>
        </template>
        <el-table v-model:data="tableData" v-loading="loading" stripe style="width: 100%">
          <el-table-column type="index" label="序号" align="center" width="55">
            <template #default="scope">
              <span>{{ (listQuery.page - 1) * listQuery.limit + scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="roleName" label="角色名称" align="center">
          </el-table-column>
          <el-table-column prop="roleCode" label="角色编码" align="center">
          </el-table-column>
          <el-table-column prop="date" label="创建时间" align="center">
            <template #default="scope">
              {{ parseTime(scope.row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button-group>
                <el-button type="success" @click.native.prevent="handleEditRole(scope.row)">
                  分配权限
                </el-button>
                <el-button type="warning" @click.native.prevent="handleEdit(scope.row)">
                  编辑
                </el-button>
                <el-button type="danger" @click.native.prevent="handleDelete(scope.row)">
                  删除
                </el-button>
              </el-button-group>
            </template>
          </el-table-column>
        </el-table>
        <div class="block columnCC mt-2">
          <el-pagination
              v-show="total > 0"
              v-model:currentPage="listQuery.page"
              v-model:page-size="listQuery.limit"
              :page-sizes="[10, 25, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="handleSizeChange"
              @current-change="getRoles"
          >
          </el-pagination>
        </div>
      </el-card>
    <dialog-roles
        :dialog-roles="dialogData"
        :form-data="formData"
        @handleSubmitAdd="handleSubmitAdd"
        @handleSubmitEdit="handleSubmitEdit"
    />
    <PermissionsDialog
        :permissions-dialog="permissionsDialog"
        ref="menu"
        @giveRules='giveRules'
    />
  </div>
</template>

<script setup lang="ts">
import {Plus} from '@element-plus/icons-vue'
import {Search} from '@element-plus/icons-vue'
import {dialogTy} from '~/dialog'
import DialogRoles from './dialogRoles.vue'
import PermissionsDialog from './dialogPermission.vue'
import {Ref} from 'vue'
import {addRoles, updateRoles, delRoles, authorizeRoleMenu} from "@/api/roles";
import {ElMessage, ElMessageBox} from 'element-plus'
import {parseTime} from '@/utils/dateTime'

const store = useStore()

// table显示角色数组
const tableData: any = ref(null)
const loading = false
// 角色总数
const total = ref(null)
// 查询配置 [pageNum 当前页数, pageSize 每页大小]
const listQuery: any = ref({
  page: 1,
  limit: 10
})
onMounted(() => {
  getRoles()
})

/**
 * 新增
 */
const dialogData: Ref<dialogTy> = ref({})//ts的规范写法，定义变量的类型
let formData = ref({})//这是相对自由点的写法，不定义类型

const handleAdd = () => {
  dialogData.value = {
    show: true,
    title: '新增角色',
    option: 'add'
  }

  formData.value = {
    title: '',
  }
}

const handleEdit = (row) => {
  dialogData.value = {
    show: true,
    title: '编辑角色',
    option: 'edit'
  }

  formData.value = {
    id: row.id,
    roleName: row.roleName,
    roleCode: row.roleCode
  }
}

const handleDelete = (row) => {
  ElMessageBox.confirm('此操作将永久删除该角色, 是否继续?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    delRoles(row.id)
        .then(res => {
          ElMessage({message: '删除成功', type: 'success'})
          // todo 无感刷新
          getRoles()
        })
  }).catch(() => {
  })
}

const permissionsDialog: Ref<dialogTy> = ref({})//ts的规范写法，定义变量的类型
const handleEditRole = (row) => {
  permissionsDialog.value = {
    show: true,
    title: '分配菜单',
    option: row.id
  }
}


// 授予用户角色
const giveRules = (data) => {
  let id = permissionsDialog.value.option
  authorizeRoleMenu(id, {menuIds: data}).then(res => {
    ElMessage({message: '分配成功！', type: 'success'})
    permissionsDialog.value.show = false
    getRoles()
  })
}

function handleSubmitAdd() {
  let requestData = formData._rawValue
  addRoles(requestData).then(res => {
    dialogData.value.show = false
    ElMessage({message: '新增成功！', type: 'success'})
    getRoles()
  })
}

function handleSubmitEdit() {
  updateRoles(formData.value.id, formData.value).then(res => {
    dialogData.value.show = false
    ElMessage({message: '编辑角色成功！', type: 'success'})
    getRoles();
  })
}

// 获取角色列表
const getRoles = () => {
  store
      .dispatch('roles/getRolesList', listQuery._rawValue)
      .then((response) => {
        tableData.value = response.items
        total.value = response.total
      })
      .catch((response) => {
      })
}
/**
 * 分页大小修改处理
 */
const handleSizeChange = () => {
  listQuery.page = 1
  getRoles()
}
/**
 * 查询框处理
 */
const handleFilter = () => {
  listQuery.page = 1
  getRoles()
}
</script>
