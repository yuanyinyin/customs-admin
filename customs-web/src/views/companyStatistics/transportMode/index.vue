
<template>
    <div class="scroll-y">
        <el-tabs v-model="activeName" @tab-change="handleTabChange">
            <el-tab-pane label="进口运输方式统计" name="first" >
                <div class="dashboard-container">

                    <div class="dashboard-card">
                        <div class="flex-between">
                        </div>
                        <div id="main1" style="width: 100%;height:100%;"></div>
                    </div>

                    <div class="dashboard-card">
                        <div class="dashboard-card-header">
                        <div class="flex-between">
                            <span>进口运输方式列表</span>
                        </div>
                        </div>
                        <div class="dashboard-card-body">
                            <el-card>

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
                                            <el-date-picker style="width:100px;"
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
                                        <el-table-column type="index" label="序号" align="center" width="45"/>
                                        <el-table-column prop="TRAF" label="运输方式" align="center" />
                                        <el-table-column prop="NUMB" label="报关单量" align="center"  />
                                        <el-table-column prop="rate" label="占比" align="center" >
                                        </el-table-column>
                                        <el-table-column fixed="right" label="操作" width="75" align="center">
                                            <template #default="scope">
                                            <el-button
                                                v-for="(item, index) in btnInRows"
                                                :key="index"
                                                @click="handleClick(item.option,'I', scope.row)"
                                            >
                                                {{ item.text }}
                                            </el-button>
                                            </template>
                                        </el-table-column>
                             </el-table>
                            </div>
                          </el-card>
                       </div>
                    </div>

                    <div class="dashboard-card">
                        <div class="flex-between">
                            <!-- <span>进口运输方式统计</span> -->
                        </div>
                        <div id="main2" style="width: 100%;height:100%;"></div>


                    </div>

                    <div class="dashboard-card">
                        <div class="dashboard-card-header">
                        <div class="flex-between">
                            <span>运输方式列表</span>
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
                                >
                                        <el-table-column type="index" label="序号" align="center" width="45"/>
                                        <el-table-column prop="TRAF" label="运输方式" align="center"/>
                                        <el-table-column prop="CUSTOM" label="进出口岸" align="center"/>
                                        <el-table-column prop="rate1" label="占比" align="center"/>
                                        <el-table-column prop="IEPORT" label="申报地海关" align="center"/>
                                        <el-table-column prop="NUMB" label="报关单量" align="center"/>
                                        <el-table-column prop="rate2" label="占比" align="center"/>
                            </el-table>
                            </div>
                            </el-card>
                        </div>
                    </div>

                    </div>
            </el-tab-pane>
            <el-tab-pane label="出口运输方式统计" name="second" >
                <div class="dashboard-container">

                    <div class="dashboard-card">
                        <div class="flex-between">
                        </div>
                        <div id="main3" style="width: 100%;height:100%;"></div>
                    </div>

                    <div class="dashboard-card">
                        <div class="dashboard-card-header">
                        <div class="flex-between">
                            <span>出口运输方式列表</span>
                        </div>
                        </div>
                        <div class="dashboard-card-body">
                            <el-card>

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
                                :data="tableData3"
                                style="width: 100%"
                                fit
                                border
                                height="200"
                                :summary-method="getSummariesE"   show-summary
                                highlight-current-row
                                >
                                        <el-table-column type="index" label="序号" align="center" width="45"/>
                                        <el-table-column prop="TRAF" label="运输方式" align="center"/>
                                        <el-table-column prop="NUMB" label="报关单量" align="center"/>
                                        <el-table-column prop="rate" label="占比" align="center">
                                        </el-table-column>
                                        <el-table-column fixed="right" label="操作" width="75" align="center">
                                            <template #default="scope">
                                            <el-button
                                                v-for="(item, index) in btnInRows"
                                                :key="index"
                                                @click="handleClick(item.option, 'E',scope.row)"
                                            >
                                                {{ item.text }}
                                            </el-button>
                                            </template>
                                        </el-table-column>
                            </el-table>
                            </div>
                            </el-card>
                        </div>
                    </div>

                    <div class="dashboard-card">
                        <div class="flex-between">
                            <!-- <span>进口运输方式统计</span> -->
                        </div>
                        <div id="main4" style="width: 100%;height:100%;"></div>


                    </div>

                    <div class="dashboard-card">
                        <div class="dashboard-card-header">
                        <div class="flex-between">
                            <span>运输方式列表</span>
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
                                >
                                        <el-table-column type="index" label="序号" align="center" width="55"/>
                                        <el-table-column prop="TRAF" label="运输方式" align="center"/>
                                        <el-table-column prop="CUSTOM" label="进出口岸" align="center"/>
                                        <el-table-column prop="rate1" label="占比" align="center"/>
                                        <el-table-column prop="IEPORT" label="申报地海关" align="center"/>
                                        <el-table-column prop="NUMB" label="报关单量" align="center"/>
                                        <el-table-column prop="rate2" label="占比" align="center"/>
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
  import { addSuggest } from '@/api/suggest'
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
    initPickI();
})
const activeName = ref('first')
const handleTabChange = () =>{
    switch(activeName._value){
        case "first":initPickI();break;
        case "second":initPickE();break;
    }
}
const current = ref(-1)
//处理按钮的点击事件
const handleClick = (op: string, type: string,...restPara: any) => {
  if (op == 'query') {
    handleQuery(restPara[0],type)
  }
}
const btnInRows = ref(
  [
    {
      text: '查询',
      type: 'text',
      option: 'query',
    },
  ]
)
let handleQuery = (para,type) => {
   getPortStatistics(para,type);
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
    getImportTransportMode()
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
    getExportTransportMode()
}
const initPickI = () => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);
    listQueryI.startDate=start
    listQueryI.endDate=end
      //异步，加入延迟
      setTimeout(function () {
        try {
            getImportTransportMode()
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
            getExportTransportMode()
        } catch (e) {
        }

    }, 500)
}
const getImportTransportMode = () => {
    let params={
      startDate: parseDateWithoutDay(listQueryI.startDate),
      endDate: parseDateWithoutDay(listQueryI.endDate),
      ieFlag:'I'
    }
  store
    .dispatch('companyStatistics/getTransportMode',params)
    .then((response) => {
      console.log(response)
      tableData.value = response.rs
      loadChar(response.rs,'I')

    })
    .catch((response) => {})
}
const getPortStatistics = (para,type) => {
    let params={
      startDate: parseDateWithoutDay(listQueryI.startDate),
      endDate: parseDateWithoutDay(listQueryI.endDate),
      ieFlag:type,
      trafMode:para.TRAF
    }
  store
    .dispatch('companyStatistics/getPortStatistic',params)
    .then((response) => {
      console.log(response)
      if(type=='I'){
        tableData2.value = response.rs
      }else  if(type=='E'){
        tableData4.value = response.rs
      }

      loadPort(response.rs,type)

    })
    .catch((response) => {})
}

const getExportTransportMode = () => {
    let params={
      startDate: parseDateWithoutDay(listQueryI.startDate),
      endDate: parseDateWithoutDay(listQueryI.endDate),
      ieFlag:'E'
    }
  store
    .dispatch('companyStatistics/getTransportMode',params)
    .then((response) => {
      console.log(response)
      tableData3.value = response.rs
      loadChar(response.rs,'E');

    })
    .catch((response) => {})
}

    //进口运输方式统计
const loadChar = (_data,type) => {

    let date1=parseDateWithoutDayNew(listQueryI.startDate);
    let date2=parseDateWithoutDayNew(listQueryI.endDate);
    // 指定图表的配置项和数据
            let modeArry=[];
            let modeValueArry=[];
            for (var i = 0; i < _data.length; i++) {
                modeArry.push(_data[i].TRAF);
                modeValueArry[i] = {value:_data[i].NUMB,name:modeArry[i]}
            }
            let totalType=''
            let mainType=''
            if(type=='I'){
                totalType='进口';
                mainType='main1';
            }else if(type=='E'){
                totalType='出口';
                mainType='main3';
                date1=parseDateWithoutDayNew(listQueryE.startDate);
                date2=parseDateWithoutDayNew(listQueryE.endDate);
            }
            const option = {
                tooltip: {
                    trigger: 'item'
                },
                title: {
                    text: date1+'~'+date2+totalType+'运输方式统计'
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
                        radius: ['30%', '70%'],
                        center:['50%','50%'],
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
                    },
                ],
            };
    let canvasI: any = null
     canvasI = document.getElementById(mainType)
      let myChartI = echarts.init(canvasI)
      // 绘制图表
      myChartI.setOption(option)
}
const loadPort= (_data,type) => {

    let date1=parseDateWithoutDayNew(listQueryI.startDate);
    let date2=parseDateWithoutDayNew(listQueryI.endDate);
            // 指定图表的配置项和数据
            // 指定图表的配置项和数据
            //中心圆数据
            let portArray=[];
            let portValueArray=[];
            for (let i = 0; i < _data.length; i++) {
                portArray.push(_data[i].IEPORT);
                portValueArray[i]={value:_data[i].PORTSUM,name:portArray[i]};
            }
            //圆环数据
            let customArray=[];
            let customValueArray=[];
            for (let i = 0; i < _data.length; i++) {
                customArray.push(_data[i].CUSTOM);
                customValueArray[i]={value:_data[i].NUMB,name:customArray[i]};
            }
            let totalType=''
            let mainType=''
            if(type=='I'){
                totalType='进口';
                mainType='main2';
            }else if(type=='E'){
                totalType='出口';
                mainType='main4';
            }
            const option = {
                title: {
                    text: date1+'~'+date2+totalType     +_data[0].TRAF+'口岸统计'
                },
                legend: {
                    orient:'vertical',
                    data: customArray,
                    left:'0%',
                    y:'center'
                },
                series: [
                    {
                        type: 'pie',
                        data: portValueArray,
                        radius: ['0', '30%'],
                        center:['60%','50%'],
                        minAngle:5
                    },
                    {
                        name:'申报地海关',
                        type: 'pie',
                        labelLine:{length:50},
                        label:{
                            formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                            backgroundColor: '#F6F8FC',
                            borderColor: '#8C8D8E',
                            borderWidth:1,
                            borderRadius:4,
                            rich: {
                                a: {
                                    color: '#6E7079',
                                    lineHeight: 22,
                                    align: 'center'
                                },
                                hr: {
                                    borderColor: '#8C8D8E',
                                    width: '100%',
                                    borderWidth: 1,
                                    height: 0
                                },
                                b: {
                                    color: '#4C5058',
                                    fontSize: 14,
                                    fontWeight: 'bold',
                                    lineHeight: 33
                                },
                                per: {
                                    color: '#fff',
                                    backgroundColor: '#4C5058',
                                    padding: [3, 4],
                                    borderRadius: 4
                                }
                            }
                        },
                        data: customValueArray,
                        radius: ['45%', '60%'],
                        center:['60%','50%'],
                        minAngle:5
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
const getSummariesE = (param) => {
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
      }, 0)}`
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
