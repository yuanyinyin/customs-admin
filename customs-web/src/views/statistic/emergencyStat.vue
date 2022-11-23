<template>
  <div class="LogContainer scroll-y">

    <el-card class="m-1">
      <template #header>
        <div class="clearfix">
          <span>突发事件统计列表</span>
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

        <el-table-column prop="area" label="地区" align="center"/>

        <el-table-column prop="sum" label="当月突发事件上报数" align="center"/>
        <el-table-column prop="solvedSum" label="当月突发事件已处理数" align="center"/>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">

import {statEmergency} from "@/api/emergency";

const loading = false
// table显示日志数组
const tableData: any = ref(null)

onMounted(()=>{
  getStatEmergency()
})

const getStatEmergency = () =>{
  statEmergency().then((response) => {
    tableData.value = response.data
  })
    .catch((response) => {
    })
}
</script>


