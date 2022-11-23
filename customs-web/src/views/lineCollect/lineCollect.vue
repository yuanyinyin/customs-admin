<template>
  <div class="LogContainer scroll-y">

    <div class="head-container m-1 mr-3 rowSS">
      <!-- 查询工具条 -->
      <el-form :inline="true" :model="listQuery" class="demo-form-inline">

        <el-form-item label="点位名称">
          <el-input
            v-model="listQuery.name"
            placeholder="点位名称"
            class="filter-item"
            @keyup.enter="handleFilter" clearable
          />
        </el-form-item>
        <el-form-item label="点位所在地区">
          <el-select
            v-model="listQuery.area"
            placeholder="请选择地区"
            class="filter-item"
            @keyup.enter="handleFilter" clearable
          >
            <el-option label="崇川区" value="崇川区"></el-option>
            <el-option label="通州区" value="通州区"></el-option>
            <el-option label="如皋市" value="如皋市"></el-option>
            <el-option label="海安市" value="海安市"></el-option>
            <el-option label="海门区" value="海门区"></el-option>
            <el-option label="如东县" value="如东县"></el-option>
            <el-option label="启东市" value="启东市"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <span class="filter-item">
            <el-button type="success" :icon="Search" @click="handleFilter">查询</el-button>
          </span>
        </el-form-item>
      </el-form>
    </div>

    <el-card class="m-1">
      <template #header>
        <div class="clearfix">
          <span>岸线采集列表</span>
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

        <el-table-column prop="name" label="点位名称" align="center"/>

        <el-table-column prop="area" label="点位所在区域" align="center"/>

        <el-table-column prop="createTime" label="创建时间" align="center">
          <template #default="scope">
            {{parseTime(scope.row.createTime) }}
          </template>
        </el-table-column>

        <el-table-column prop="creatorName" label="创建人" align="center"/>
        <el-table-column prop="status" label="审核状态" align="center" :formatter="statusFormatter"/>
        <el-table-column label="操作" align="center" show-overflow-tooltip min-width="100%">
          <template #default="scope">
            <el-button-group>
              <el-button type="primary" @click="viewDetail(scope.row)">查看详情</el-button>
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
          @current-change="getLines"
        >
        </el-pagination>
      </div>
    </el-card>
    <dialog-line :dialog-line="dialogData" :form-data="formData" @audit="getLines"></dialog-line>
  </div>
</template>

<script setup lang="ts">
import {parseTime} from '@/utils/dateTime'
import {Ref} from "vue";
import {dialogTy} from "~/dialog";
import DialogLine from "@/views/lineCollect/dialogLine.vue";
import {deleteById} from "@/api/line";
import {ElMessage, ElMessageBox} from 'element-plus'

const loading = false
const store = useStore()
// table显示日志数组
const tableData: any = ref(null)
// 日志总数
const total = ref(null)
const dialogData: Ref<dialogTy> = ref({})//ts的规范写法，定义变量的类型
let formData = ref({})//这是相对自由点的写法，不定义类型

// 查询配置 [pageNum 当前页数, pageSize 每页大小]
const listQuery: any = ref({
  page: 1,
  limit: 10
})

onMounted(()=>{
  getLines()
})

const getLines = () =>{
  store
    .dispatch('line/getLinesList', listQuery._rawValue)
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
  getLines()
}

const viewDetail = (row) => {
  dialogData.value = {
    show: true,
    title: '详细信息'
  }

  formData.value = {
    id: row.id,
    type:row.type,
    name:row.name,
    position:{
      startPosition:row.startPosition,
      endPosition:row.endPosition
    },
    area:row.area,
    memo:row.memo,
    status:row.status,
    patrolPersonList:row.patrolPersonList,
  }
}

const statusFormatter = (row) => {
  switch (row.status){
    case "0":return "暂存";
    case "1":return "待审核";
    case "2":return "审核通过";
    case "3":return "审核未通过";
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
    deleteById( row.id)
        .then((response) => {
          ElMessage({message: '删除成功！', type: 'success'})
          getLines()
        })
        .catch((response) => {
        })
  }).catch(() => {
  })
}
</script>
