<template>
  <div class="LogContainer scroll-y">
<!--    <div class="head-container m-1 mr-3 rowSS">
      &lt;!&ndash; 查询工具条 &ndash;&gt;
      <el-form :inline="true" :model="listQuery" class="demo-form-inline">

        <el-form-item label="巡查日期">
          <el-date-picker
            v-model="listQuery.patrolTime"
            placeholder="巡查日期"
            style="width: 120px"
            @change="changeDate" clearable
          />
        </el-form-item>
        <el-form-item label="点位名称">
          <el-input
            v-model="listQuery.pointName"
            placeholder="点位名称"
            style="width: 120px"
            @keyup.enter="handleFilter" clearable
          />
        </el-form-item>
        <el-form-item label="巡查人">
          <el-input
              v-model="listQuery.creatorName"
              placeholder="巡查人"
              style="width: 120px"
              @keyup.enter="handleFilter" clearable
            />
        </el-form-item>
        <el-form-item>
          <span class="filter-item">
            <el-button type="success" :icon="Search" @click="handleFilter">查询</el-button>
          </span>
        </el-form-item>
      </el-form>
    </div>-->

    <el-card class="m-1">
      <template #header>
        <div class="clearfix">
          <span>定期查记录表</span>
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

        <el-table-column prop="patrolTime" label="巡查日期" align="center"/>

        <el-table-column prop="creatorName" label="巡查人" align="center"/>

        <el-table-column prop="result" label="巡查结果" align="center"/>

        <el-table-column prop="startTime" label="巡查开始时间" align="center">
          <template #default="scope">
            {{ parseTime(scope.row.startTime) }}
          </template>
        </el-table-column>

        <el-table-column prop="endTime" label="巡查结束时间" align="center">
          <template #default="scope">
            {{ parseTime(scope.row.endTime) }}
          </template>
        </el-table-column>

        <el-table-column prop="createTime" label="创建时间" align="center">
          <template #default="scope">
            {{ parseTime(scope.row.createTime) }}
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button type="primary" @click="viewDetail(scope.row)">查看详情</el-button>
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
          @current-change="getItems"
        >
        </el-pagination>
      </div>
    </el-card>

    <dialog-period :dialog-period="dialogData"></dialog-period>
  </div>
</template>

<script setup lang="ts">
import {Search} from '@element-plus/icons-vue'
import {parseTime, parseTimeThrid} from '@/utils/dateTime'
import {listPage} from "@/api/periodRecord";
import {Ref} from "vue";
import DialogPeriod from '@/views/inspect/dialogPeriod.vue'

const loading = false
const dialogData = ref({})
// table显示日志数组
const tableData: any = ref(null)
// 日志总数
const total = ref(null)

// 查询配置 [pageNum 当前页数, pageSize 每页大小]
const listQuery: any = ref({
  page: 1,
  limit: 10
})

/**
 * 获取每日巡列表
 */
const getItems = () => {
  listPage(listQuery.value).then((res) => {
    tableData.value = res.data.items
    total.value = res.data.total
  }).catch()
}

onMounted(() => {
  // 刷新每日巡列表
  getItems()
})

/**
 * 查询框处理
 */
const handleFilter = () => {
  listQuery.page = 1
  // 刷新每日巡列表
  getItems()
}

const changeDate = () => {
  if(listQuery._rawValue.patrolTime){
    listQuery._rawValue.patrolTime = parseTimeThrid(listQuery._rawValue.patrolTime)
  }
}

const viewDetail = (row) => {
  dialogData.value = {
    show: true,
    title: '定期查详情',
    id:row.id
  }
}
</script>
