<template>

  <div>
    <div class="customsData-container scroll-y p">

      <el-tabs v-model="activeName" @tab-change="handleTabChange">
        <el-tab-pane label="进出口商品金额TOP10" name="first" >
          <el-container >
            <el-card style="width: 100%;">
              <el-header>进口商品金额TOP10</el-header>
              <el-main style="width: 100%;height:500px;">
                <div id="wordcloud_echarts1" style="width: 70%;height:100%;"></div>
              </el-main>
            </el-card>
            <el-card style="width: 100%;">
              <el-main style="width: 100%;">
                <div class="import-search mb-1">
                  <el-form :inline="true" :model="listQueryI" class="demo-form-inline">
                    <el-form-item label="统计日期">
                      <div class="block">
                        <span class="demonstration"></span>
                        <el-date-picker  style="width:100px;"
                          v-model="listQueryI.startDate"
                          type="month"
                          placeholder="统计起始日期">
                        </el-date-picker>
                      </div>
                      <div class="block">
                        <span class="demonstration">~</span>
                        <el-date-picker  style="width:100px;"
                          v-model="listQueryI.endDate"
                          type="month"
                          placeholder="统计起始日期">
                        </el-date-picker>
                      </div>
                    </el-form-item>
                    <el-form-item>
                    <span class="filter-item">
                        <el-button type="success" :icon="Search" @click="onSearchI">查询</el-button>
                    </span>
                    </el-form-item>
                  </el-form>
                </div>
                <div class="import-table mb-1">

                  <el-table
                    :data="tableData"
                    style="width: 140%"
                    fit
                    border
                    highlight-current-row
                  >
                    <el-table-column type="index" label="TOP10" align="center" width="65"   />
                    <el-table-column prop="CODETS" label="商品编码" align="center"/>
                    <el-table-column prop="GNAME" label="商品名称" align="center"/>
                    <el-table-column prop="NUMB" label="申报频率" align="center"/>
                  </el-table>
                </div>
              </el-main>
            </el-card>
          </el-container>
        </el-tab-pane>
        <el-tab-pane label="出口商品金额TOP10" name="second" >
          <el-container>
            <el-card style="width: 100%;">
              <el-header>出口商品金额TOP10</el-header>
              <el-main style="width: 100%;height:500px;">
                <div id="wordcloud_echarts2" style="width: 70%;height:100%;"></div>
              </el-main>
            </el-card>
            <el-card style="width: 100%;">
              <el-main style="width: 100%;">

                <div class="export-search mb-1">
                  <el-form :inline="true" :model="listQueryE" class="demo-form-inline">
                    <el-form-item label="统计日期">
                      <div class="block">
                        <span class="demonstration"></span>
                        <el-date-picker style="width:100px;"
                          v-model="listQueryE.startDate"
                          type="month"
                          placeholder="统计起始日期">
                        </el-date-picker>
                      </div>
                      <div class="block">
                        <span class="demonstration">~</span>
                        <el-date-picker style="width:100px;"
                          v-model="listQueryE.endDate"
                          type="month"
                          placeholder="统计起始日期">
                        </el-date-picker>
                      </div>
                    </el-form-item>
                    <el-form-item>
                    <span class="filter-item">
                        <el-button type="success" :icon="Search" @click="onSearchE">查询</el-button>
                    </span>
                    </el-form-item>
                  </el-form>
                </div>

                <div class="export-table mb-1">
                  <el-table
                    :data="tableData2"
                    style="width: 100%"
                    fit
                    border
                    height="400px"
                    highlight-current-row
                  >
                    <el-table-column type="index" label="TOP10" align="center" width="65"   />
                    <el-table-column prop="CODETS" label="商品编码" align="center"/>
                    <el-table-column prop="GNAME" label="商品名称" align="center"/>
                    <el-table-column prop="NUMB" label="申报频率" align="center"/>
                  </el-table>
                </div>
              </el-main>
            </el-card>
          </el-container>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import { dialogTy } from '~/dialog'
import {Ref} from 'vue'
import { parseDateWithoutDayNew,parseDateWithoutDay} from '@/utils/dateTime'
import {ElMessage } from 'element-plus'
import {Search} from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import 'echarts-wordcloud'
const loading = false
const store = useStore()
const tableData: any = ref(null)
const tableData2: any = ref(null)

/**查询部分 */
const listQueryI = reactive({
  startDate:'',
  endDate:'',
})
const listQueryE = reactive({
  startDate:'',
  endDate:'',
})


const activeName = ref('first')
const handleTabChange = () =>{
    switch(activeName._rawValue){
       case "first":initPickI();break;
       case "second": initPickE();break;
    }
}

onMounted(() => {
    initPickI()
})
const onSearchI = () => {
    if(listQueryI.startDate===null||listQueryI.startDate===''||listQueryI.endDate===null||listQueryI.endDate===''){
        ElMessage({ message: '请填写统计起始日期', type: 'error' })
        return;
    }
    let startDate1=parseDateWithoutDay(listQueryI.startDate);
    let endDate1=parseDateWithoutDay(listQueryI.endDate);
    if(startDate1.substr(0, startDate1.length - 3)!=endDate1.substr(0, startDate1.length - 3)){
        ElMessage({ message: '统计起始日期应为同一年份', type: 'error' })
        return;
    }
    getImportTopGoods()
}
const onSearchE = () => {
    if(listQueryE.startDate===null||listQueryE.startDate===''||listQueryE.endDate===null||listQueryE.endDate===''){
        ElMessage({ message: '请填写统计起始日期', type: 'error' })
        return;
    }
    let startDate1=parseDateWithoutDay(listQueryE.startDate);
    let endDate1=parseDateWithoutDay(listQueryE.endDate);
    if(startDate1.substr(0, startDate1.length - 3)!=endDate1.substr(0, startDate1.length - 3)){
        ElMessage({ message: '统计起始日期应为同一年份', type: 'error' })
        return;
    }
    getExporTopGoods()
}
const initPickI = () => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);
    listQueryI.startDate=start
    listQueryI.endDate=end
    //异步，加入延迟
    setTimeout(function () {
    try {
        getImportTopGoods()
    } catch (e) {
      console.log(e)
    }

    }, 500)
}
const initPickE = () => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);
    listQueryE.startDate=start
    listQueryE.endDate=end
    //异步，加入延迟
    setTimeout(function () {
    try {
        getExporTopGoods()
    } catch (e) {
    }

    }, 500)
}
const getImportTopGoods = () => {
    let params={
      startDate: parseDateWithoutDay(listQueryI.startDate),
      endDate: parseDateWithoutDay(listQueryI.endDate),
      ieFlag:'I',
      qyFlag:'1'
    }
  store
    .dispatch('companyStatistics/getTopFrequency',params)
    .then((response) => {
      console.log(response)
      tableData.value = response.rs
      loadChar(response.rs,'I');

    })
    .catch((response) => {})
}
const getExporTopGoods = () => {
    let params2={
      startDate: parseDateWithoutDay(listQueryE.startDate),
      endDate: parseDateWithoutDay(listQueryE.endDate),
      ieFlag:'E',
      qyFlag:'1'
    }
  store
    .dispatch('companyStatistics/getTopFrequency',params2)
    .then((response) => {
      console.log(response)
      tableData2.value = response.rs
      loadChar(response.rs,'E');

    })
    .catch((response) => {})
}
let canvasI: any = null//document.getElementById('canvas')
    //绘制出口商品金额TOP10柱状图
const loadChar = (_data,type) => {
        let date1=parseDateWithoutDayNew(listQueryI.startDate);
        let date2=parseDateWithoutDayNew(listQueryI.endDate);
        let totalType=''
            let mainType=''
            if(type=='I'){
                totalType='进口';
                mainType='wordcloud_echarts1';
            }else if(type=='E'){
                totalType='出口';
                mainType='wordcloud_echarts2';
                date1=parseDateWithoutDayNew(listQueryE.startDate);
                date2=parseDateWithoutDayNew(listQueryE.endDate);
            }
            var goodsArray=[];
        var goodsFrequency=[];
        for (let i = 0; i < _data.length; i++) {
            goodsArray.push(_data[i].CODETS);
            goodsFrequency.push(_data[i].NUMB);
        }
        let data={
            name:goodsArray,
            value:goodsFrequency
        }
        // 指定图表的配置项和数据
        const option = {
            title: {
                text: date1+'~'+date2+totalType+'商品申报频率',
            },
            tooltip: {},
            legend: {
                data: ['申报频率'],
                top:'8%'
            },
            xAxis: {
                type:'value',
            },
            yAxis: {
                type:'category',
                data:data.name ,
                inverse: true,
                axisLabel:{
                    interval:0,
                    rotate:30
                }
            },
            grid: {
              left: '110',
            },
            series: [
                {
                    name: '申报频率',
                    type: 'bar',
                    data: data.value,
                    label:{ // 展示具体柱状图的数值
                        show:true
                    }
                },
            ]
        };
        canvasI = document.getElementById(mainType)
        let myChartI = echarts.init(canvasI)
      //绘制图表
      myChartI.setOption(option)
    }
</script>
const
<style scoped lang="scss"></style>
<style>
  .el-input__inner{
    width: 50px;
  }
</style>
