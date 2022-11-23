<!--
 * @FileDescription: 用户管理页面
 * @Author: Zhou, Zhongqi
 * @Date:
 * @LastEditors: Yang, Botao
 * @LastEditTime: 2022-02-28 22w10
 -->
<template>
  <div class="UserContainer scroll-y">
    <div class="head-container m-1 mr-3 rowSS">
      <!-- 查询工具条 -->
      <el-form :inline="true" :model="listQuery" class="demo-form-inline">
        <el-form-item label="姓名">
          <el-input
              v-model="listQuery.realName"
              placeholder="姓名"
              class="filter-item"
              @keyup.enter="handleFilter"
              clearable
          />
        </el-form-item>
        <!-- <el-form-item label="用户名">
          <el-input
              v-model="listQuery.userName"
              placeholder="用户名"
              class="filter-item"
              @keyup.enter="handleFilter"
              clearable
          />
        </el-form-item> -->
        <el-form-item label="审核状态">
          <el-select v-model="listQuery.status" clearable  placeholder="请选择审核状态">
            <el-option
              v-for="item in approveOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
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
          <span>用户列表</span>
        </div>
      </template>

      <el-table
          v-loading="loading"
          :data="tableData"
          stripe
          style="width: 100%"
          @selection-change="handleFilter"
      >
        <el-table-column fixed type="index" label="序号" align="center" width="55">
          <template #default="scope">
            <span>{{ (listQuery.page - 1) * listQuery.limit + scope.$index + 1 }}</span>
          </template>
        </el-table-column>

        <el-table-column prop="realName" label="姓名" align="center" width="100px"/>
        <el-table-column prop="userName" label="账号" align="center" width="200px"/>
        <el-table-column prop="roles" label="身份" align="center" :show-overflow-tooltip="true" width="300px">
          <template #default="scope">
            <span v-for="role in scope.row.roles" :key="role.id" style="padding:0px 4px;">
              <el-tag size="small">{{ role.roleName }}</el-tag>
            </span>
          </template>
        </el-table-column>
         <el-table-column prop="dept.deptName" label="部门" align="center" :show-overflow-tooltip="true" width="300px">
          <!-- <template #default="scope">
            <span v-for="role in scope.row.roles" :key="role.id" style="padding:0px 4px;">
              <el-tag size="small">{{ role.roleName }}</el-tag>
            </span>
          </template> -->
        </el-table-column>
        <el-table-column prop="telephone" label="手机号码" align="center" min-width="50%"/>
        <el-table-column prop="job" label="职务" align="center" min-width="50%"/>
        <el-table-column label="操作" align="center" show-overflow-tooltip min-width="100%" width="300px">
          <template #default="scope">
            <el-button-group>
              <el-button v-if="scope.row.status==='2'" type="success" @click="handleEditRole(scope.row)">分配角色</el-button>
              <el-button v-if="scope.row.status==='2'" type="warning" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button v-if="scope.row.status==='2'" type="danger" @click="handleDelete(scope.row)">删除</el-button>
              <el-button v-if="scope.row.status==='1'" type="success" @click="approvePass(scope.row)">审核通过</el-button>
              <el-button v-if="scope.row.status==='1'" type="danger" @click="approveNotPass(scope.row)">审核不通过</el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>

      <div class="block columnCC mt-2">
        <el-pagination
            v-show="total > 0"
            v-model:currentPage="listQuery.page"
            v-model:page-size="listQuery.limit"
            :page-sizes="[10, 20, 50, 100]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleFilter"
            @current-change="getUsers"
        ></el-pagination>
      </div>
    </el-card>

    <user-dialog
        :user-dialog="userDialog"
        :form-data="formData"
        @userHandleSubmitAdd="userHandleSubmitAdd"
        @userHandleSubmitEdit="userHandleSubmitEdit"
    />

    <PermissionsDialog :permissions-dialog="permissionsDialog" ref="role" @giveRules="giveRules"/>
  </div>
</template>

<script setup lang="ts">
import settings from '@/settings'
import {Plus} from '@element-plus/icons-vue'
import {Search} from '@element-plus/icons-vue'
import PermissionsDialog from './dialogPermission.vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {Ref} from 'vue'
import {dialogTy} from '~/dialog'
import UserDialog from './userDialog.vue'
import {addUser, updateUser, authorizeUserRole, setWorkStatus,passUser,notPassUser} from '@/api/user'

const store = useStore()
const loading = false
// table显示用户数组
const tableData: any = ref(null)

// 用户总数
const total = ref(null)

// 用户新增、修改对话框属性
const userDialog: Ref<dialogTy> = ref({
  show: false
}) //ts的规范写法，定义变量的类型

// 显示用户数据
const formData = ref({}) //这是相对自由点的写法，不定义类型

const listQuery: any = ref({
  page: 1,
  limit: 10
})

const approveOptions = [
  { value: '1', label: '待审核', },
  { value: '2', label: '审核通过', }
]

onMounted(() => {
  // 刷新用户列表
  getUsers()
})
/**
 * 获取用户列表
 */
const getUsers = () => {
  store.dispatch('user/getUserList', listQuery._rawValue)
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
  // 刷新用户列表
  getUsers()
}

/**
 * 查询框处理
 */
const handleFilter = () => {
  listQuery.page = 1
  // 刷新用户列表
  getUsers()
}

/**
 * '新增'按钮触发
 * 空Dialog，标题‘新增用户’
 */
const handleAdd = () => {
  userDialog.value = {
    show: true,
    title: '新增用户',
    option: 'add'
  }
  formData.value = {
    /** 传向dialog的 空params
     */
    realName: '',
    userName: '',
    userPassword: '',
    deptId: '',
    telephone: '',
    job: ''
  }
}

function userHandleSubmitAdd() {
  addUser(formData.value).then((res) => {
    userDialog.value.show = false
    ElMessage({message: '新增成功！', type: 'success'})
    // 刷新用户列表
    getUsers()
  })
}

/**
 * '编辑'按钮触发
 * 编辑Dialog， 标题‘修改用户’，原有旧数据填充
 */
const handleEdit = (row) => {
  userDialog.value = {
    show: true,
    title: '修改用户',
    option: 'edit'
  }
  formData.value = {
    id: row.id,
    realName: row.realName,
    userName: row.userName,
    deptId: row.deptId,
    telephone: row.telephone,
    job: row.job
  }
}

function userHandleSubmitEdit() {
  updateUser(formData.value.id, formData.value).then((res) => {
    userDialog.value.show = false
    ElMessage({message: '更新成功！', type: 'success'})
    // 刷新用户列表
    getUsers()
  })
}

/**
 * '删除'按钮触发
 */
const handleDelete = (row) => {
  ElMessageBox.confirm('此操作将永久删除该用户, 是否继续?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    store.dispatch('user/deleteUser', row.id)
        .then((response) => {
          ElMessage({message: '删除成功！', type: 'success'})
          getUsers()
        })
        .catch((response) => {
        })
  }).catch(() => {
  })
}

const approvePass = (row) => {
  ElMessageBox.confirm('确认审核通过该用户吗?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    passUser( row.id)
        .then((response) => {
          ElMessage({message: '审核成功！', type: 'success'})
          getUsers()
        })
        .catch((response) => {
        })
  }).catch(() => {
  })
}

const approveNotPass = (row) => {
  ElMessageBox.confirm('确认不通过该用户?确认后，该用户信息将被删除，请及时提醒该用户重新注册', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    notPassUser( row.id)
        .then((response) => {
          ElMessage({message: '操作成功！', type: 'success'})
          getUsers()
        })
        .catch((response) => {
        })
  }).catch(() => {
  })
}

const permissionsDialog: Ref<dialogTy> = ref({})

/**
 * '分配角色'按钮触发
 * 角色Dialog，标题‘分配角色’，展示可分配角色
 */
const handleEditRole = (row) => {
  permissionsDialog.value = {
    show: true,
    title: '分配角色',
    option: row.id
  }
}

// 授予用户角色
const giveRules = (data) => {
  let id = permissionsDialog.value.option
  authorizeUserRole(id, {roleIds: data}).then((res) => {
    ElMessage({message: '分配成功！', type: 'success'})
    permissionsDialog.value.show = false
    getUsers()
  })
}
</script>
