<template>
  <div class="LogContainer scroll-y">

    <el-card class="m-1">
      <template #header>
        <div class="clearfix">
          <span>巡查统计列表</span>
        </div>
      </template>

      <el-table
        v-loading="loading"
        :data="tableData" stripe style="width: 100%"
      >
        <el-table-column fixed type="index" label="序号" align="center">
          <template #default="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>

        <el-table-column prop="deptName" label="部门名称" align="center"/>

        <el-table-column prop="dailySum" label="当月每日巡次数" align="center">
          <template #default="scope">
            <span>{{scope.row.dailySum || 0}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="periodSum" label="当月定期查次数" align="center">
          <template #default="scope">
            <span>{{scope.row.periodSum || 0}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="trendsSum" label="当月动态督次数" align="center">
          <template #default="scope">
            <span>{{scope.row.trendsSum || 0}}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">

import {statRecords} from "@/api/dailyRecord";

const loading = false
// table显示日志数组
const tableData: any = ref(null)

onMounted(()=>{
  getStatRecords()
})

const getStatRecords = () =>{
  statRecords().then((response) => {
    tableData.value = response.data
  })
    .catch((response) => {
    })
}
</script>


