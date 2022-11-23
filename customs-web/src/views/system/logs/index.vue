<!--
 * @FileDescription: 系统日志查看页面
 *
 * @Author: Zhang,yue
 * @Date: 2022-02-15 22w08
 *
 * @LastEditors: Yang, Botao
 * @LastEditTime: 2022-02-24 22w09
 -->
<template>
  <div class="LogContainer scroll-y">

    <div class="head-container m-1 mr-3 rowSS">
      <!-- 查询工具条 -->
      <el-form :inline="true" :model="listQuery" class="demo-form-inline">

        <el-form-item label="操作模块">
          <el-input
              v-model="listQuery.opeModule"
              placeholder="操作模块"
              class="filter-item"
              @keyup.enter="handleFilter" clearable
          />
        </el-form-item>
        <el-form-item label="操作描述">
          <el-input
              v-model="listQuery.opeDesc"
              placeholder="操作描述"
              class="filter-item"
              @keyup.enter="handleFilter" clearable
          />
        </el-form-item>
        <el-form-item label="操作者">
          <el-input
              v-model="listQuery.handler"
              placeholder="操作者"
              class="filter-item"
              @keyup.enter="handleFilter" clearable
          />
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
          <span>系统日志列表</span>
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

        <el-table-column prop="opeModule" label="操作模块" align="center"/>

        <el-table-column prop="opeDesc" label="操作描述" align="center"/>

        <el-table-column prop="handler" label="操作者" align="center"/>

        <el-table-column prop="createTime" label="操作时间" align="center">
          <template #default="scope">
            {{ parseTime(scope.row.createTime) }}
          </template>
        </el-table-column>

        <el-table-column prop="opeIp" label="操作IP" align="center"/>
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

  </div>
</template>

<script setup lang="ts">
import settings from '@/settings'
import {Search} from '@element-plus/icons-vue'
import {parseTime} from '@/utils/dateTime'
import {getLogsListPage as listPage} from "@/api/log";

const loading = false

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
 * 获取日志列表
 */
const getItems = () => {
  listPage(listQuery.value).then((res) => {
    tableData.value = res.data.items
    total.value = res.data.total
  }).catch()
}

onMounted(() => {
  // 刷新日志列表
  getItems()
})

/**
 * 查询框处理
 */
const handleFilter = () => {
  listQuery.page = 1
  // 刷新日志列表
  getItems()
}

</script>
