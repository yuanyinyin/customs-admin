<template>
  <div class="roles-container scroll-y p">
        <!--工具栏-->
        <div class="head-container m-1 mr-3 rowSS">
            <!-- 搜索 -->
            <el-form :inline="true" :model="listQuery" class="demo-form-inline">
              <el-form-item label="类型">
                 <el-select v-model="listQuery.type" placeholder="请选择类型">
            <el-option
              v-for="item in types"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>

              </el-form-item>
              <el-form-item label="是否推送">
                <el-select v-model="listQuery.flag" placeholder="请选择是否推送">
            <el-option
              v-for="item in flags"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
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
            <span>信息列表</span>
          </div>
        </template>
        <el-table v-model:data="tableData" v-loading="loading" stripe style="width: 100%"  @row-dblclick="showDetail">
          <el-table-column type="index" label="序号" align="center" width="55" >
            <template #default="scope">
              <span>{{ (listQuery.page - 1) * listQuery.limit + scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="type" label="类型" align="center" :formatter="formatType">
          </el-table-column>
          <el-table-column prop="noticeUserName" label="推送人" align="center">
          </el-table-column>
          <el-table-column prop="flag" label="是否推送" align="center" :formatter="formatFlag">
          </el-table-column>
          <el-table-column prop="content" label="详情" align="center">
          </el-table-column>
          <el-table-column prop="date" label="创建时间" align="center">
            <template #default="scope">
              {{ parseTime(scope.row.createTime) }}
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
    
      <DialogDetail ref="dialogDetailRef" 
       :dialog-detail="dialogDetailData"
       :form-data="formDetail"
        />


  </div>
</template>

<script setup lang="ts">
import {Plus} from '@element-plus/icons-vue'
import {Search} from '@element-plus/icons-vue'
import {dialogTy} from '~/dialog'

import {Ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {parseTime} from '@/utils/dateTime'


import {getListPage as listPage} from "@/api/info";



import DialogDetail from './dialogDetail.vue'
const dialogDetailData: Ref<dialogTy> = ref({})//ts的规范写法，定义变量的类型
let formDetail = ref({})//这是相对自由点的写法，不定义类型




const showDetail = (row, column, event) => {
  console.log(row)
  
   dialogDetailData.value = {
    show: true,
    title: '信息详情',
    
  }
  formDetail.value = row
}




const types = [
  { value: '1', label: '提醒', },
  { value: '2', label: '通知', },
 
]


const flags = [
  { value: '0', label: '未推送', },
  { value: '1', label: '已推送', },
 
]


const formatType= (row) => {
  switch (row.type) {
    case "1":
      return "提醒";
    case "2":
      return "通知";
  }
}
const formatFlag= (row) => {
  switch (row.flag) {
    case "0":
      return "未推送";
    case "1":
      return "已推送";
    
  }
}




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
const route = useRoute()
onMounted(() => {
 
  console.log(123)
    console.log(route.query)
    console.log(route.query.id)
  if (route.query.id) {
      // listQuery.value = {id:route.query.id}
      
      getRoles(route.query.id)
  }else{
    getRoles()
  }


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


// 获取角色列表
const getRoles = (idStr) => {
  if(idStr){
    listPage({id:idStr,
    page: 1,
  limit: 10}).then((res) => {
    tableData.value = res.data.items
    total.value = res.data.total
  }).catch()
  }else{
  listPage(listQuery.value).then((res) => {
    tableData.value = res.data.items
    total.value = res.data.total
  }).catch()
  }

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
