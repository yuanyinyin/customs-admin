
<template>
    <div class="scroll-y">
        <el-tabs v-model="activeName" @tab-change="handleTabChange">
            <el-tab-pane label="进口贸易方式统计" name="first" >
                <div class="dashboard-container">
  
                    <div class="dashboard-card"> 
                        <div class="flex-between">
                        </div>
                        <div id="main1" style="width: 100%;height:100%;"></div>             
                    </div>

                    <div class="dashboard-card">
                        <div class="dashboard-card-header">
                        <div class="flex-between">
                            <span>进口报关单量列表</span>
                        </div>
                        </div>
                        <div class="dashboard-card-body">
                            <el-card>
          
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
                            <el-table tooltip-effect="dark"
                                :data="tableData"
                                style="width: 100%" 
                                fit
                                border
                                height="200"
                                :summary-method="getSummariesI"   show-summary 
                                highlight-current-row
                                >
                                <el-table-column type="index" label="序号" align="center" width="55"/>
                                <el-table-column prop="MON" label="时间" align="center"/>

                                <el-table-column prop="NUMB" label="报关单量" align="center"/>
                                <el-table-column prop="average" label="VS均值" align="center">
                                    <template #default="scope">
                                        <span v-if="scope.row.average === `---`">---</span>
                                        <span v-if="parseInt(scope.row.NUMB) > parseInt(scope.row.average)"  style="color:red">高于均值</span>
                                        <span v-if="parseInt(scope.row.NUMB) < parseInt(scope.row.average)"  style="color:green">低于均值</span>
                                        <span v-if="parseInt(scope.row.NUMB )=== parseInt(scope.row.average)" >---</span>
                                        </template>
                                </el-table-column>

                             </el-table>
                            </div>           
                          </el-card>
                       </div>
                    </div>

                    <div class="dashboard-card"> 
                        <div class="flex-between">
                        </div>
                        <div id="main2" style="width: 100%;height:100%;"></div>
                      
                        
                    </div>

                    <div class="dashboard-card">
                        <div class="dashboard-card-header">
                        <div class="flex-between">
                            <span>贸易方式列表</span>
                        </div>
                        </div>
                        <div class="dashboard-card-body">
                            <el-card>

                        <div class="import-table mb-1">
                            <el-table
                                :data="tableData2"
                                style="width: 100%" 
                                fit
                                border
                                height="200"
                                highlight-current-row
                                :summary-method="getSummariesI"   show-summary 
                                >
                                        <el-table-column type="index" label="序号" align="center" width="55"/>
                                        <el-table-column prop="TRADEMODE" label="贸易方式" align="center"/>
                                        <el-table-column prop="NUMB" label="单量" align="center"/>
                                        <el-table-column prop="rate" label="占比" align="center"/>
                            </el-table>
                            </div>           
                            </el-card>
                        </div>
                    </div>

                    </div>
            </el-tab-pane>
            <el-tab-pane label="出口贸易方式统计" name="second" >
                <div class="dashboard-container">
  
                    <div class="dashboard-card"> 
                        <div class="flex-between">
                        </div>
                        <div id="main3" style="width: 100%;height:100%;"></div>             
                    </div>

                    <div class="dashboard-card">
                        <div class="dashboard-card-header">
                        <div class="flex-between">
                            <span>出口贸易方式列表</span>
                        </div>
                        </div>
                        <div class="dashboard-card-body">
                            <el-card>

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
                                :data="tableData3"
                                style="width: 100%" 
                                fit
                                border
                                height="200"
                                :summary-method="getSummariesI"   show-summary 
                                highlight-current-row
                                >
                                <el-table-column type="index" label="序号" align="center" width="55"/>
                                <el-table-column prop="MON" label="时间" align="center"/>

                                <el-table-column prop="NUMB" label="报关单量" align="center"/>
                                <el-table-column prop="average" label="VS均值" align="center">
                                    <template #default="scope">
                                        <span v-if="scope.row.average === `---`">---</span>
                                        <span v-if="parseInt(scope.row.NUMB) > parseInt(scope.row.average)"  style="color:red">高于均值</span>
                                        <span v-if="parseInt(scope.row.NUMB) < parseInt(scope.row.average)"  style="color:green">低于均值</span>
                                        <span v-if="parseInt(scope.row.NUMB )=== parseInt(scope.row.average)" >---</span>
                                        </template>
                                </el-table-column>
                            </el-table>
                            </div>           
                            </el-card>
                        </div>
                    </div>

                    <div class="dashboard-card"> 
                        <div class="flex-between">
                        </div>
                        <div id="main4" style="width: 100%;height:100%;"></div>
                        
                        
                    </div>

                    <div class="dashboard-card">
                        <div class="dashboard-card-header">
                        <div class="flex-between">
                            <span>贸易方式列表</span>
                        </div>
                        </div>
                        <div class="dashboard-card-body">
                            <el-card>

                        <div class="import-table mb-1">
                            <el-table
                                :data="tableData4"
                                style="width: 100%" 
                                fit
                                border
                                height="200"
                                highlight-current-row
                                :summary-method="getSummariesI"   show-summary 
                                >
                                <el-table-column type="index" label="序号" align="center" width="55"/>
                                <el-table-column prop="TRADEMODE" label="贸易方式" align="center"/>
                                <el-table-column prop="NUMB" label="单量" align="center"/>
                                <el-table-column prop="rate" label="占比" align="center"/>
                            </el-table>
                            </div>           
                            </el-card>
                        </div>
                    </div>

                    </div>
            </el-tab-pane>
        </el-tabs>

    </div>
  </template>
  
  <script setup lang="ts">
  import { ElMessage } from 'element-plus'
  import { useRouter } from 'vue-router';
  import { parseDateWithoutDayNew,parseDateWithoutDay } from '@/utils/dateTime'
  import {Search} from '@element-plus/icons-vue'
  import * as echarts from 'echarts'
  const store = useStore()
  const tableData: any = ref(null)
  const tableData2: any = ref(null)
  const tableData3: any = ref(null)
  const tableData4: any = ref(null)

  /**查询部分 */
const listQueryI = reactive({
  startDate:'',
  endDate:'',
})
const listQueryE = reactive({
  startDate:'',
  endDate:'',
})
onMounted(() => {
    initPickI()
})
const activeName = ref('first')
const handleTabChange = () =>{
    switch(activeName._value){
        case "first":initPickI();break;
        case "second": initPickE();break;
    }
}
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
    getImportTradeMode()
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
    getExportTradetMode()
} 
const initPickI = () => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);
    listQueryI.startDate=start
    listQueryI.endDate=end
    //异步，加入延迟
    setTimeout(function () {
    try {
        getImportTradeMode()
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
            getExportTradetMode()
        } catch (e) {
        }

    }, 500)
}

//加载运输方式统计，再加载报关单量统计页面
const getImportTradeMode = () => {
    let params={
      startDate: parseDateWithoutDay(listQueryI.startDate),
      endDate: parseDateWithoutDay(listQueryI.endDate),
      ieFlag:'I'
    }
  store
    .dispatch('companyStatistics/getTradeMode',params)
    .then((response) => {
      console.log(response)
      tableData2.value = response.rs
      //贸易方式图表
      loadChar2(response.rs,'I')
      //加载报关单量统计
      getCustomsData('I')
    })
    .catch((response) => {})
}
const getCustomsData = (type) => {
    let params={
      startDate: parseDateWithoutDay(listQueryI.startDate),
      endDate: parseDateWithoutDay(listQueryI.endDate),
      ieFlag:type,
      qyFlag:'1'
    }
  store
    .dispatch('companyStatistics/getImOrExCustomsData',params)
    .then((response) => {
      console.log(response)
      if(type=='I'){
        tableData.value = response.items
      }else if(type=='E'){
        tableData3.value = response.items
      }
    
      loadChar1(response.rs,type)

    })
    .catch((response) => {})
}
const getExportTradetMode = () => {
    let params={
      startDate: parseDateWithoutDay(listQueryI.startDate),
      endDate: parseDateWithoutDay(listQueryI.endDate),
      ieFlag:'E'
    }
  store
    .dispatch('companyStatistics/getTradeMode',params)
    .then((response) => {
      console.log(response)
      tableData4.value = response.rs
     //贸易方式图表
     loadChar2(response.rs,'E');
      //加载报关单量统计
      getCustomsData('E')

    })
    .catch((response) => {})
}

    //进口贸易方式统计
const loadChar2 = (_data,type) => {
    
    let date1=parseDateWithoutDayNew(listQueryI.startDate);
    let date2=parseDateWithoutDayNew(listQueryI.endDate);
    // 指定图表的配置项和数据
            let modeArry=[];
            let modeValueArry=[];
            for (let i = 0; i < _data.length; i++) {
                modeArry.push(_data[i].TRADEMODE);
                modeValueArry[i] = {value:_data[i].NUMB,name:modeArry[i]}
            }
            let totalType=''
            let mainType=''
            if(type=='I'){
                totalType='进口';
                mainType='main2';
            }else if(type=='E'){
                totalType='出口';
                mainType='main4';
                date1=parseDateWithoutDayNew(listQueryE.startDate);
                date2=parseDateWithoutDayNew(listQueryE.endDate);
            }
            const option = {
                tooltip: {
                    trigger: 'item'
                },
                title: {
                    text: date1+'~'+date2+totalType+'贸易方式统计'
                },
                legend: {
                    orient:'vertical',
                    data: modeArry,
                    left:'0%',
                    y:'center'
                },
                series: [
                    {
                        type: 'pie',
                        data: modeValueArry,
                        radius: ['40%', '70%'],
                        minAngle:5,
                        label: {
                        show: true,
                        formatter: '{b}         {d}%',//模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。{d}数据会根据value值计算百分比
                        },
                        emphasis: {
                            label: {
                            show: true,
                            fontSize: '15',
                            fontWeight: 'bold'
                            }
                        },
                    }
                ]
            };
    let canvasI: any = null
    canvasI = document.getElementById(mainType)
    let myChartI = echarts.init(canvasI)
   //异步，加入延迟
    // 使用刚指定的配置项和数据显示图表。
    setTimeout(function () {
        try {
            myChartI.setOption(option);
        } catch (e) {
        }

    }, 500)
}
//绘制关单量柱状图
const loadChar1= (_data,type) => {
    
    let date1=parseDateWithoutDayNew(listQueryI.startDate);
    let date2=parseDateWithoutDayNew(listQueryI.endDate);
    // 指定图表的配置项和数据
    let monthArry=[];
    let monthValueArry=[];
    for (let i = 0; i < _data.length; i++) {
        monthArry.push(_data[i].MON);
        monthValueArry.push(_data[i].NUMB);
    }
    let data={month:monthArry,val:monthValueArry};
    let totalType=''
    let mainType=''
    if(type=='I'){
        totalType='进口';
        mainType='main1';
    }else if(type=='E'){
        totalType='出口';
        mainType='main3';
    }
    const option = {
        title: {
            text: date1+'~'+date2+totalType+'报关单量统计'
        },
        tooltip: {},
            legend: {
                data: ['报关单量','平均值'],
                top:'6%'
            },
            xAxis: {
                data:data.month,
                axisLabel:{
                    interval:0,
                    rotate:0
                }
            },
            yAxis: {
                axisLabel:{
                    interval:0,
                    rotate:0
                }
            },
            series: [
                {
                    name: '报关单量',
                    type: 'bar',
                    data: data.val,
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
    let canvas2: any = null
     canvas2 = document.getElementById(mainType)
      let myChart2 = echarts.init(canvas2)
      // 绘制图表
      myChart2.setOption(option)
}
const getSummariesI = (param) => {
  const { columns, data } = param
  const sums: string[] = []
  columns.forEach((column, index) => {
    if (index === 1) {
      sums[index] = '合计'
      return
    } 
    if(data!=null){
        const values = data.map((item) =>
     Number(item[column.property]))
    if (index==2) {
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

  return sums
}
  
  </script>
  
  <style lang="scss" scoped>
  @import '@/styles/addNew.scss';
  </style>
  