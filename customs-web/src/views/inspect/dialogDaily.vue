<template>
  <div>
    <el-dialog v-model="dialogDaily.show" :title="dialogDaily.title" width="40%" center @open="initData(dialogDaily.id)" destroy-on-close>
      <div style="height:500px">
        <el-scrollbar>
          <div>
            <baidu-map :detailId="dialogDaily.id" style="width:100%;height:500px;"></baidu-map>
            <el-table v-show="false" v-model:data="tableData" v-loading="loading" stripe style="width: 100%">
              <el-table-column type="index" label="序号" align="center" width="55" >
                <template #default="scope">
                  <span>{{scope.$index +1 }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="name" label="点位名称" align="center"/>
              <el-table-column prop="position" label="打卡坐标" align="center"/>
              <el-table-column prop="create_time" label="打卡时间" align="center"/>
              <el-table-column prop="creator_name" label="巡查人" align="center"/>
            </el-table>
          </div>
        </el-scrollbar>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import {getDetail} from "@/api/dailyRecord";
import {PropType} from "vue";
import {dialogTy} from "~/dialog";
import BaiduMap from "@/views/inspect/baiduMap.vue"

const refDailyForm: any = ref(null)
const tableData = ref(null)
const loading = false
const props = defineProps({
  dialogDaily:{
    require: true,
    default: null,
    type: Object
  }
})

const initData = (id) => {
  getDetail(id).then(res =>{
    tableData.value = res.data
  })
}

</script>

<style scoped>

</style>
