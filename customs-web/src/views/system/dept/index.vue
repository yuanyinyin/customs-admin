<template>
   <div class="menu-container scroll-y p">
     <div class="filter-item m">
       <el-button type="primary" :icon="Plus" @click="handleAdd">新增</el-button>
     </div>
      <el-card class="my-2">
         <el-table
         style="width: 100%"
         :data="tableData"
         ref="table"
         row-key="id"
          lazy
          fit
         highlight-current-row
          default-expand-all
         :tree-props="{ children: 'children'}"
      >

          <el-table-column type="index" label="序号" width="100"/>
         <el-table-column prop="id" label="部门id" ></el-table-column>
         <el-table-column prop="deptName" label="部门名称" ></el-table-column>
         <el-table-column label="创建时间" align="center" prop="createTime" width="200">
            <template #default="scope">
               <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
         </el-table-column>
         <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template #default="scope">
                <el-button type="warning" @click="handleEdit(scope.row)">修改</el-button>
               <el-button type="danger"  v-if="scope.row.parentId != 0" @click.native.prevent="handleDelete(scope.row)">
                删除
              </el-button>
            </template>
         </el-table-column>
      </el-table>
      </el-card>
     <dialog-dept :dialog-dept="dialogData" :form-data="formData" @handleSubmitAdd="handleSubmitAdd"
                  @handleSubmitEdit="handleSubmitEdit"/>
   </div>
</template>

<script setup lang="ts">
import {getDept, delDept, addDept, updateDept} from "@/api/dept";
import {ElMessage, ElMessageBox} from 'element-plus'
import {Plus} from "@element-plus/icons-vue";
import {parseTime} from '@/utils/dateTime'
import {Ref} from "vue";
import {dialogTy} from "~/dialog";
import dialogDept from "./dialogDept.vue"
import {getMenuParentList, updateMenu} from "@/api/menu";

onMounted(() => {
  getDeptList()
})

const tableData = ref([])
const dialogData: Ref<dialogTy> = ref({})//ts的规范写法，定义变量的类型
let formData = ref({})//这是相对自由点的写法，不定义类型

const getDeptList = () => {
  getDept().then((response) => {
        tableData.value = response.data
      })
      .catch((res) => {
    })
}

const handleAdd = () => {
  dialogData.value = {
    show: true,
    title: '新增部门',
    option: 'add'
  }

  formData.value = {
    parentId:'',
    deptName:'',
    orderNum:0,
  }
}

/** 删除按钮操作 */
const handleDelete = (row) => {
  ElMessageBox.confirm('此操作将永久删除该部门及其所有子部门, 是否继续?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    delDept(row.id)
        .then(res => {
          ElMessage({message: '删除成功', type: 'success'})
          getDeptList()
        })
  }).catch(() => {
  })
}

const handleEdit = (row) => {
  dialogData.value = {
    show: true,
    title: '修改菜单',
    option: 'edit'
  }

  formData.value = {
    id: row.id,
    parentId: row.parentId,
    ancestors: row.ancestors,
    deptName: row.deptName,
    areaId: row.areaId,
    streetId: row.streetId,
    orderNum: row.orderNum,
    leaderId: row.leaderId,
    phone: row.phone,
    email: row.email,
    status: row.status,
    delFlag: row.delFlag
  }
}

function handleSubmitAdd() {
  let requestData = formData._rawValue
  var pidArr = requestData.parentId
  requestData.parentId = pidArr[pidArr.length - 1];
  addDept(requestData).then(res => {
    dialogData.value.show = false
    ElMessage({message: '新增成功！', type: 'success'})
    getDeptList()
  })
}

function handleSubmitEdit() {
  let requestData = formData._rawValue
  if (requestData.parentId == requestData.id) {
    ElMessage({message: '自己不能成为自己的下级！', type: 'error'})
  } else {
    var pidArr = requestData.parentId
    if (pidArr instanceof Array) {
      requestData.parentId = pidArr[pidArr.length - 1];
    } else {
      requestData.parentId = pidArr
    }
    updateDept(requestData).then(res => {
      dialogData.value.show = false
      ElMessage({message: '修改成功！', type: 'success'})
      getDeptList()
    })
  }
}
</script>
