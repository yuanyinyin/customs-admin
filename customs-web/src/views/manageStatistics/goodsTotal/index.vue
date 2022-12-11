<template>

    <div class="customsData-container scroll-y p">
        
        <el-tabs v-model="activeName" @tab-change="handleTabChange">
        <el-tab-pane label="进口商品货值统计" name="first" >
            <el-container >
                <el-card style="width: 150%;">
                    <el-header>进口商品货值统计</el-header>
                <el-main style="width: 100%;height:500px;">
                        <div id="main1" style="width: 70%;height:100%;"></div>
                </el-main>
                </el-card>
                <el-card style="width: 100%;">
            <el-main style="width: 100%;">     
                <div class="import-search mb-1">
                    <el-form :inline="true" :model="listQueryI" class="demo-form-inline">
                    <el-form-item label="统计日期">
                        <div class="block">
                            <span class="demonstration"></span>
                            <el-date-picker
                            v-model="listQueryI.startDate"
                            type="month"
                            placeholder="统计起始日期">
                            </el-date-picker>
                        </div>
                        <div class="block">
                            <span class="demonstration">~</span>
                            <el-date-picker
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
                style="width: 100%" 
                fit
                border
                :summary-method="getSummariesI"   show-summary 
                highlight-current-row
                >
                        <el-table-column type="index" label="序号" align="center" width="55"/>
                        <el-table-column prop="CODETS" label="商品编码" align="center"/>
                        <el-table-column prop="GNAME" label="商品名称" align="center"/>
                        <el-table-column prop="NUMB" label="币制" align="center"/>
                        <el-table-column prop="NUMB" label="币值金额" align="center"/>
                        <el-table-column prop="RATE" label="汇率" align="center"/>
                        <el-table-column prop="RMB" label="人民币金额" align="center"/>
                     </el-table>
                 </div>
              </el-main>
            </el-card>            
            </el-container>
        </el-tab-pane>
        <el-tab-pane label="出口商品货值统计" name="second" >
            <el-container>
                <el-card style="width: 100%;">
                    <el-header>出口商品货值统计</el-header>
                <el-main style="width: 100%;height:500px;">
                        <div id="main2" style="width: 70%;height:100%;"></div>
                </el-main>
                </el-card>
                <el-card style="width: 100%;">
            <el-main style="width: 100%;">
           
                <div class="export-search mb-1">
                    <el-form :inline="true" :model="listQueryE" class="demo-form-inline">
                    <el-form-item label="统计日期">
                        <div class="block">
                            <span class="demonstration"></span>
                            <el-date-picker
                            v-model="listQueryE.startDate"
                            type="month"
                            placeholder="统计起始日期">
                            </el-date-picker>
                        </div>
                        <div class="block">
                            <span class="demonstration">~</span>
                            <el-date-picker
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
                :summary-method="getSummariesI"   show-summary 
                highlight-current-row
                >
                <el-table-column type="index" label="序号" align="center" width="55"/>
                        <el-table-column prop="MON" label="时间" align="center"/>
                        <el-table-column prop="CURRENCY" label="币值" align="center"/>
                        <el-table-column prop="NUMB" label="金额" align="center"/>
                        <el-table-column prop="RATE" label="汇率" align="center"/>
                        <el-table-column prop="RMB" label="人民币金额" align="center"/>
                     </el-table>
                 </div>
                </el-main>
               </el-card>
            </el-container>
        </el-tab-pane>
    </el-tabs>       
    </div>
    <el-drawer
    v-model="table3"
    title="I have a nested table inside!"
    direction="rtl"
    size="50%"
  >
    <el-table :data="gridData">
      <el-table-column property="date" label="Date" width="150" />
      <el-table-column property="name" label="Name" width="200" />
      <el-table-column property="address" label="Address" />
    </el-table>
  </el-drawer>

  <el-drawer
    ref="drawerRef"
    v-model="dialog"
    title="I have a nested form inside!"
    :before-close="handleClose"
    direction="ltr"
    custom-class="demo-drawer"
  >
    <div class="demo-drawer__content">
      <el-form :model="form">
        <el-form-item label="Name" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="Area" :label-width="formLabelWidth">
          <el-select
            v-model="form.region"
            placeholder="Please select activity area"
          >
            <el-option label="Area1" value="shanghai" />
            <el-option label="Area2" value="beijing" />
          </el-select>
        </el-form-item>
      </el-form>
    </div>
  </el-drawer>
</template>

<script setup lang="ts">
import { dialogTy } from '~/dialog'
import {reactive,Ref} from 'vue'
import { parseDateWithoutDayNew,parseDateWithoutDay } from '@/utils/dateTime'
import {ElDrawer,ElMessage ,ElMessageBox} from 'element-plus'
import {Search} from '@element-plus/icons-vue'
import * as echarts from 'echarts'
const loading = false
const store = useStore()
const tableData: any = ref(null)
const tableData2: any = ref(null)
/* 抽屉*/
const drawer = ref(false)
const direction = ref('rtl')
const drawerWidth = ref('60%')
const drawerTitle = ref('')

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
    console.log(111,activeName.value)
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
    getImportCustomsData()
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
    getExportCustomsData()
}
const initPickI = () => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);
    listQueryI.startDate=start
    listQueryI.endDate=end
    //异步，加入延迟
    setTimeout(function () {
    try {
        getImportCustomsData()
    } catch (e) {
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
        getExportCustomsData()
    } catch (e) {
    }

    }, 500)
}
const getImportCustomsData = () => {
    let params={
      startDate: parseDateWithoutDay(listQueryI.startDate),
      endDate: parseDateWithoutDay(listQueryI.endDate),
      ieFlag:'I'
    }
  store
    .dispatch('manageStatistics/getGoodsData',params)
    .then((response) => {
      console.log(response)
      tableData.value = response.rs
      loadChar(response.rs,'I')

    })
    .catch((response) => {})
}
const getExportCustomsData = () => {
    let params2={
      startDate: parseDateWithoutDay(listQueryE.startDate),
      endDate: parseDateWithoutDay(listQueryE.endDate),
      ieFlag:'E'
    }
  store
    .dispatch('manageStatistics/getGoodsData',params2)
    .then((response) => {
      console.log(response)
      tableData2.value = response.rs
      loadChar(response.rs,'E')

    })
    .catch((response) => {})
}
const getSummariesI = (param) => {
  const { columns, data } = param
  const sums: string[] = []
  columns.forEach((column, index) => {
    if (index === 1) {
      sums[index] = '汇总'
      return
    } 
    if(data!=null){
        const values = data.map((item) =>
     Number(item[column.property]))
    if (index==6) {
      sums[index] = `${values.reduce((prev, curr) => {
        const value = Number(curr)
        if (!Number.isNaN(value)) {
          return prev + curr
        } else {
          return prev
        }
      }, 0).toFixed(2)}`
    } else {
    //   sums[index] = ''
    }
    }

  })
  return sums;
}
const getGoodsDataByGNAME = (CODETS,gname,type) => {
    let date1=parseDateWithoutDay(listQueryI.startDate);
    let date2=parseDateWithoutDay(listQueryI.endDate);
    if(type=='E'){
        date1=parseDateWithoutDay(listQueryE.startDate);
        date2=parseDateWithoutDay(listQueryE.endDate);
    }
    let params={
        gname:gname,
        startDate: date1,
        endDate: date2,
        ieFlag:type
        }
    console.log(params) 
  store
    .dispatch('manageStatistics/getGoodsDataByGNAME',params)
    .then((response) => {
      console.log(11111,response)
      loadChar2(response.rs,gname,type);

    })
    .catch((response) => {})
}
let canvasI: any = null//document.getElementById('canvas')
    //绘制出口商品货值柱状图
const loadChar = (_data,type) => {
    
        let date1=parseDateWithoutDayNew(listQueryI.startDate);
        let date2=parseDateWithoutDayNew(listQueryI.endDate);
        // 指定图表的配置项和数据
        let monthArry=[];
        let monthValueArry=[];
        for (let i = 0; i < _data.length; i++) {
            monthArry.push(_data[i].GNAME);
            monthValueArry.push(_data[i].RMB);
        }
        let data={month:monthArry,val:monthValueArry};
        let totalType=''
            let mainType=''
            if(type=='I'){
                totalType='进口';
                mainType='main1';
            }else if(type=='E'){
                totalType='出口';
                mainType='main2';
            }
        console.log(data);
        const option = {
            title: {
                text: date1+'~'+date2+totalType+'商品货值统计'
            },
            tooltip: {},
            legend: {
                data: ['商品货值','平均值'],
                top:'6%'
            },
            toolbox: {
                        show: true,
                        feature: {
                        dataView: { show: true, readOnly: false },
                        magicType: { show: true, type: ['line', 'bar'] },
                        restore: { show: false },
                        saveAsImage: { show: true }
                        }
                    },
            xAxis: {
                data:data.month,
                axisLabel:{
                    interval:0,
                    margin:20,
                    rotate:-15
                },
                    triggerEvent: true
            },
            yAxis: {
                axisLabel:{
                    interval:0,
                    rotate:0
                }
            },
            series: [
                {
                    name: '商品货值',
                    type: 'bar',
                    data: data.val,
                    barGap: '20%',
                    barCategoryGap: '30%',
                    barMaxWidth:'25',
                    markLine:{ // 设置平均线
                        data:[
                            {
                                type: "average",
                                name: "平均值",
                                color: "#baf",
                                lineStyle: {
                                    normal: {
                                        color: '#baf',
                                        width: 2
                                    }
                                }
                            }
                        ]
                    },
                    label:{ // 展示具体柱状图的数值
                        show:true
                    }
                }
            ]
        };
        canvasI = document.getElementById(mainType)
        let myChartI = echarts.init(canvasI)
      // 绘制图表

      myChartI.setOption(option);
      myChartI.on("click", function (param) {
        let str_before=_data[param.dataIndex].CODETS
        let str=_data[param.dataIndex].GNAME
        drawer.value = true
        getGoodsDataByGNAME(str_before,str,type);
        alert(drawer.value )
     })
      
    }
    const loadChar2 = (_data,areaName,type,tabType) => {
        let date1=parseDateWithoutDayNew(listQueryI.startDate);
        let date2=parseDateWithoutDayNew(listQueryI.endDate);
        // 指定图表的配置项和数据
        let monthArry=[];
        let monthValueArry1=[];
        let monthValueArry2=[];
        let monthValueArry3=[];
        let monthValueArry4=[];
        let monthValueArry5=[];
        for (let i = 0; i < _data.length; i++) {
            // areaCodeArry.push(_data[i].AREACODE);
            monthArry.push(_data[i].MON);
            monthValueArry1.push(_data[i].NUMB);
            monthValueArry2.push(_data[i].SUMTOTALRMB);
            monthValueArry3.push(_data[i].SUMTOTALUSD);
            monthValueArry4.push(_data[i].SUMNETWT);
            monthValueArry5.push(_data[i].SUMGROSSWET);
        }
        let data={month:monthArry,val1:monthValueArry1,val2:monthValueArry2,val3:monthValueArry3,val4:monthValueArry4,val5:monthValueArry5};
 
        let totalType=''
            let mainType=''
            if(type=='I'){
                totalType='进口';
                mainType='main1';
            }else if(type=='E'){
                totalType='出口';
                mainType='main2';
                date1=parseDateWithoutDayNew(listQueryE.startDate);
                date2=parseDateWithoutDayNew(listQueryE.endDate);
            }

            let option={}
            if(tabType=='firstI'||tabType=='firstE'){
                 option = {
                title: {
                    text: areaName+'        '+date1+'~'+date2+totalType+'区域单量分析',
                    subtext: ''
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['单量']
                },
                toolbox: {
                    show: true,
                    feature: {
                    dataView: { show: true, readOnly: false },
                    magicType: { show: true, type: ['line', 'bar'] },
                    restore: { show: true },
                    saveAsImage: { show: true }
                    }
                },
                calculable: true,
                xAxis: [
                    {
                    type: 'category',
                    // prettier-ignore
                    data:data.month,
                    axisLabel:{
                                interval: 0,
                                formatter: function (value) {
                                    return value;
                                }
                            },
                    }
                ],
                yAxis: [
                    {
                    type: 'value'
                    }
                ],
                series: [
                    {
                    name: '单量',
                    type: 'bar',
                    data: data.val1,
                    barGap: '20%',
                    barCategoryGap: '30%',
                    barMaxWidth:'25',
                    markPoint: {
                        data: [
                        { type: 'max', name: 'Max' },
                        { type: 'min', name: 'Min' }
                        ]
                    },
                    markLine: {
                        data: [{ type: 'average', name: 'Avg' }]
                    }
                    },
                ],
                };
                }else if(tabType=='secondI'||tabType=='secondE'){
                    option = {
                title: {
                    text: areaName+'        '+date1+'~'+date2+totalType+'区域人民币、美元货值分析',
                    subtext: ''
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['人民币货值','美元货值']
                },
                toolbox: {
                    show: true,
                    feature: {
                    dataView: { show: true, readOnly: false },
                    magicType: { show: true, type: ['line', 'bar'] },
                    restore: { show: true },
                    saveAsImage: { show: true }
                    }
                },
                calculable: true,
                xAxis: [
                    {
                    type: 'category',
                    // prettier-ignore
                    data:data.month,
                    axisLabel:{
                                interval: 0,
                                formatter: function (value) {
                                    return value;
                                }
                            },
                    }
                ],
                yAxis: [
                    {
                    type: 'value'
                    }
                ],
                series: [
                    {
                    name: '人民币货值',
                    type: 'bar',
                    data: data.val2,
                    barGap: '20%',
                    barCategoryGap: '30%',
                    barMaxWidth:'25',
                    markPoint: {
                        data: [
                        { type: 'max', name: 'Max' },
                        { type: 'min', name: 'Min' }
                        ]
                    },
                    markLine: {
                        data: [{ type: 'average', name: 'Avg' }]
                    }
                    },
                    {
                    name: '美元货值',
                    type: 'bar',
                    data: data.val3,
                    barGap: '20%',
                    barCategoryGap: '30%',
                    barMaxWidth:'25',
                    markPoint: {
                        data: [
                        { type: 'max', name: 'Max' },
                        { type: 'min', name: 'Min' }
                        ]
                    },
                    markLine: {
                        data: [{ type: 'average', name: 'Avg' }]
                    }
                    },
                ],
                };
                }else if(tabType=='thirdI'||tabType=='thirdE'){
                    option = {
                title: {
                    text: areaName+'        '+date1+'~'+date2+totalType+'区域净重、毛重分析',
                    subtext: ''
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['净重','毛重']
                },
                toolbox: {
                    show: true,
                    feature: {
                    dataView: { show: true, readOnly: false },
                    magicType: { show: true, type: ['line', 'bar'] },
                    restore: { show: true },
                    saveAsImage: { show: true }
                    }
                },
                calculable: true,
                xAxis: [
                    {
                    type: 'category',
                    // prettier-ignore
                    data:data.month,
                    axisLabel:{
                                interval: 0,
                                formatter: function (value) {
                                    return value;
                                }
                            },
                    }
                ],
                yAxis: [
                    {
                    type: 'value'
                    }
                ],
                series: [
                    {
                    name: '净重',
                    type: 'bar',
                    data: data.val4,
                    barGap: '20%',
                    barCategoryGap: '30%',
                    barMaxWidth:'25',
                    markPoint: {
                        data: [
                        { type: 'max', name: 'Max' },
                        { type: 'min', name: 'Min' }
                        ]
                    },
                    markLine: {
                        data: [{ type: 'average', name: 'Avg' }]
                    }
                    },
                    {
                    name: '毛重',
                    type: 'bar',
                    data: data.val5,
                    barGap: '20%',
                    barCategoryGap: '30%',
                    barMaxWidth:'25',
                    markPoint: {
                        data: [
                        { type: 'max', name: 'Max' },
                        { type: 'min', name: 'Min' }
                        ]
                    },
                    markLine: {
                        data: [{ type: 'average', name: 'Avg' }]
                    }
                    },
                ],
                };
                }
        canvasI = document.getElementById(mainType)
        let myChartI = echarts.init(canvasI)
      // 绘制图表
      myChartI.clear()
      myChartI.setOption(option)
      myChartI.off("click")
        
    }
</script>

<style scoped lang="scss"></style>