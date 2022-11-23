<template>
  <div class="LogContainer scroll-y">

    <div class="head-container m-1 mr-3 rowSS">
      <!-- 查询工具条 -->
      <el-form :inline="true" :model="listQuery" class="demo-form-inline">

        <el-form-item label="上报日期">
          <!-- <el-input
            v-model="listQuery.reportTime"
            placeholder="发布日期"
            class="filter-item"
            @keyup.enter="handleFilter" clearable
          /> -->
          <el-date-picker
              v-model="listQuery.reportTime"
              placeholder="上报日期"
              value-format="YYYY-MM-DD"
            />
        </el-form-item>
        <el-form-item>
      <span class="filter-item">
        <el-button type="success" :icon="Search" @click="handleFilter">查询</el-button>
         <!-- <el-button type="primary" :icon="Plus" @click="handleAdd">新增</el-button> -->
      </span>
        </el-form-item>
      </el-form>
    </div>

    <el-card class="m-1">
      <template #header>
        <div class="clearfix">
          <span>隐患发现列表</span>
        </div>
      </template>

      <el-table
        v-loading="loading"
        :data="tableData" stripe style="width: 100%"
      >
        <el-table-column fixed type="index" label="序号" align="center">
          <template #default="scope">
            <span>{{ (listQuery.page - 1) * listQuery.limit + scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="id" label="编号" align="center"></el-table-column>
        <el-table-column prop="reportTime" label="上报日期" align="center"></el-table-column>
        
          <el-table-column prop="type" label="风险类别" align="center">
          <template #default="scope">
              <span v-if="scope.row.type==0">一般</span>
              <span v-if="scope.row.type==1" style="color:red;">较大风险</span>
            </template>
        </el-table-column>
           <el-table-column prop="situation" label="具体情况" align="center" >
           <template #default="scope">
              <span style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">{{scope.row.situation}}</span>
            </template>
        </el-table-column>
       <el-table-column prop="status" label="处置结果" align="center">
          <template #default="scope">
              <span style="color:red;" v-if="scope.row.status==0">未处理</span>
              <span style="color:green;" v-if="scope.row.status==1">已处理</span>
            </template>
        </el-table-column>
        <el-table-column prop="result" label="处置情况" align="center" >
           <template #default="scope">
              <span style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">{{scope.row.result}}</span>
            </template>
        </el-table-column>
        <el-table-column prop="creatorName" label="创建人" align="center"/>
        <el-table-column prop="memo" label="备注" align="center">
          <template #default="scope">
              <span style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">{{scope.row.memo}}</span>
            </template>
        </el-table-column>

        <el-table-column label="操作" align="center" show-overflow-tooltip min-width="100%">
          <template #default="scope">
            <el-button-group>
              <el-button type="primary" @click="viewDetail(scope.row)">查看详情</el-button>
              <el-button  type="warning" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button  type="danger" @click="handleDelete(scope.row)">删除</el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="block columnCC mt-2">
        <el-pagination
          v-show="total > 0"
          v-model:currentPage="listQuery.page"
          v-model:page-size="listQuery.limit"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleFilter"
          @current-change="getList"
        >
        </el-pagination>
      </div>
    </el-card>

        <risk-dialog
        :user-dialog="userDialog"
        :form-data="formData"
        @userHandleSubmitAdd="userHandleSubmitAdd"
        @userHandleSubmitEdit="userHandleSubmitEdit"
    />
  </div>
</template>

<script setup lang="ts">
import {parseTime} from '@/utils/dateTime'
import {Ref} from "vue";
import {dialogTy} from "~/dialog";
import {Search,Plus} from '@element-plus/icons-vue'
import RiskDialog from './riskDialog.vue'
import {getRiskList,addRisk,updateRisk,deleteRisk} from "@/api/risk";
import {ElMessage, ElMessageBox} from 'element-plus'

const loading = false
const store = useStore()
// table显示日志数组
const tableData: any = ref(null)
// 日志总数
const total = ref(null)
const userDialog: Ref<dialogTy> = ref({
  show: false
}) //ts的规范写法，定义变量的类型
const formData = ref({}) //这是相对自由点的写法，不定义类型

// 查询配置 [pageNum 当前页数, pageSize 每页大小]
const listQuery: any = ref({
  page: 1,
  limit: 10
})

onMounted(()=>{
  getList()
})

const getList = () =>{
    getRiskList(listQuery._rawValue)
    .then((response) => {
      tableData.value = response.data.items
      total.value = response.data.total
    })
    .catch((response) => {
    })
}

const handleFilter = () => {
  listQuery.page = 1
  // 刷新日志列表
  getList()
}

/**
 * '新增'按钮触发
 * 空Dialog，标题‘新增用户’
 */
const handleAdd = () => {
  userDialog.value = {
    show: true,
    title: '新增隐患详情',
    option: 'add'
  }
  formData.value = {
    /** 传向dialog的 空params
     */
    reportTime: '',
    pointId: '' ,
    situation: '',
    memo: ''
  }
}

function userHandleSubmitAdd() {
  addRisk(formData.value).then((res) => {
    userDialog.value.show = false
    ElMessage({message: '新增成功！', type: 'success'})
    // 刷新用户列表
    getList()
  })
}

function userHandleSubmitEdit() {
  updateRisk(formData.value.id, formData.value).then((res) => {
    userDialog.value.show = false
    ElMessage({message: '更新成功！', type: 'success'})
    // 刷新用户列表
    getList()
  })
}


const viewDetail = (row) => {
  userDialog.value = {
    show: true,
    title: '隐患发现详情',
    option: 'detail'
  }

  formData.value = {
    id: row.id,
    reportTime: row.reportTime,
    pointId:row.pointId,
    photoId:row.photoId,
    situation:row.situation,
    type:row.type,
    result:row.result,
    memo:row.memo
  }
}

const handleEdit = (row) => {
  userDialog.value = {
    show: true,
    title: '修改隐患发现',
    option: 'edit'
  }
  formData.value = {
    id: row.id,
    reportTime: row.reportTime,
    pointId:row.pointId,
    photoId:row.photoId,
    situation:row.situation,
    type:row.type,
    result:row.result,
    memo:row.memo
  }
}

/**
 * '删除'按钮触发
 */
const handleDelete = (row) => {
  ElMessageBox.confirm('是否确认删除该条数据, 是否继续?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteRisk( row.id)
        .then((response) => {
          ElMessage({message: '删除成功！', type: 'success'})
          getList()
        })
        .catch((response) => {
        })
  }).catch(() => {
  })
}

</script>