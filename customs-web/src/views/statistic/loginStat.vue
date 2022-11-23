<template>
  <div class="LogContainer scroll-y">

    <el-card class="m-1">
      <template #header>
        <div class="clearfix">
          <span>登录统计列表</span>
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

        <el-table-column prop="dept_name" label="部门名称" align="center"/>

        <el-table-column prop="todayTimes" label="当日登录次数" align="center"/>

        <el-table-column prop="monthTimes" label="当月登录次数" align="center"/>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">

import {statLogin} from "@/api/login";

const loading = false
// table显示日志数组
const tableData: any = ref(null)

onMounted(()=>{
  getLoginList()
})

const getLoginList = () =>{
  statLogin().then((response) => {
    tableData.value = response.data
  })
    .catch((response) => {
    })
}
</script>

