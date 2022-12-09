<template>

    <div class="customsData-container scroll-y p">
        
        <el-tabs v-model="activeName" @tab-change="handleTabChange">
        <el-tab-pane label="企业进口区域统计" name="first" >
            <el-container >
                <el-card style="width: 100%;">
                    <el-header>企业进口区域统计</el-header>
                    <div class="export-search mb-1">
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
                <el-main style="width: 100%;height:500px;">
                        <div id="main1" style="width: 70%;height:100%;"></div>
                </el-main>
                </el-card>          
            </el-container>
        </el-tab-pane>
        <el-tab-pane label="企业出口区域统计" name="second" >
            <el-container >
                <el-card style="width: 100%;">
                    <el-header>企业出口区域统计</el-header>
                    <div class="export-search mb-1">
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
                        <el-button type="success" :icon="Search" @click="onSearchE">查询</el-button>
                    </span>
                    </el-form-item>
                    </el-form>
                </div>
                <el-main style="width: 100%;height:500px;">
                        <div id="main2" style="width: 70%;height:100%;"></div>
                </el-main>
                </el-card>          
            </el-container>
        </el-tab-pane>
    </el-tabs>       
    </div>
</template>

<script setup lang="ts">
import { dialogTy } from '~/dialog'
import {Ref} from 'vue'
import { parseDateWithoutDayNew,parseDateWithoutDay } from '@/utils/dateTime'
import {ElMessage } from 'element-plus'
import {Search} from '@element-plus/icons-vue'
import * as echarts from 'echarts'
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
const listQuery = reactive({
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
    getImportAreaData()
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
    getExportAreaData()
}
const initPickI = () => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);
    listQueryI.startDate=start
    listQueryI.endDate=end
    //异步，加入延迟
    setTimeout(function () {
    try {
        getImportAreaData()
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
        getExportAreaData()
    } catch (e) {
    }

    }, 500)
}
const getImportAreaData = () => {
    let params={
      startDate: parseDateWithoutDay(listQueryI.startDate),
      endDate: parseDateWithoutDay(listQueryI.endDate),
      ieFlag:'I'
    }
  store
    .dispatch('manageStatistics/getAreaData',params)
    .then((response) => {
      console.log(response)
      loadChar(response.rs,'I');

    })
    .catch((response) => {})
}
const getAreaDataByCode = (areaCode,areaName,type) => {
    let date1=parseDateWithoutDay(listQueryI.startDate);
    let date2=parseDateWithoutDay(listQueryI.endDate);
    if(type=='E'){
        date1=parseDateWithoutDay(listQueryE.startDate);
        date2=parseDateWithoutDay(listQueryE.endDate);
    }
    let params={
        areaCode:areaCode,
        startDate: date1,
        endDate: date2,
        ieFlag:type
        }
    console.log(params) 
  store
    .dispatch('manageStatistics/getAreaDataByCode',params)
    .then((response) => {
      console.log(11111,response)
      loadChar2(response.rs,areaName,type);

    })
    .catch((response) => {})
}
const getExportAreaData = () => {
    let params2={
      startDate: parseDateWithoutDay(listQueryE.startDate),
      endDate: parseDateWithoutDay(listQueryE.endDate),
      ieFlag:'E'
    }
  store
    .dispatch('manageStatistics/getAreaData',params2)
    .then((response) => {
      console.log(response)
      loadChar(response.rs,'E');

    })
    .catch((response) => {})
}
let canvasI: any = null//document.getElementById('canvas')
    //绘制出口报关单量柱状图
const loadChar = (_data,type) => {
        let date1=parseDateWithoutDayNew(listQueryI.startDate);
        let date2=parseDateWithoutDayNew(listQueryI.endDate);
        // 指定图表的配置项和数据
        let areaArry=[];
        let areaCodeArry=[];
        let areaValueArry1=[];
        let areaValueArry2=[];
        let areaValueArry3=[];
        let areaValueArry4=[];
        for (let i = 0; i < _data.length; i++) {
            // areaCodeArry.push(_data[i].AREACODE);
            areaArry.push(_data[i].AREACODE+'--'+_data[i].AREANAME);
            areaValueArry1.push(_data[i].NUMB);
            areaValueArry2.push(_data[i].SUMTOTAL);
            areaValueArry3.push(_data[i].SUMNETWT);
            areaValueArry4.push(_data[i].SUMGROSSWT);
        }
        let data={areaCode:areaCodeArry,area:areaArry,val1:areaValueArry1,val2:areaValueArry2,val3:areaValueArry3,val4:areaValueArry4};
      
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
            const option = {
                title: {
            text: '企业'+totalType+'区域单量、货值、货重分析',
            subtext: ''
        },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['单量', '货值','净重','毛重']
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
                    data:data.area,
                    axisLabel:{
                        interval: 0,
                        formatter: function (value) {
                            //截取最后一个>后面的字符串
                            let index = value.lastIndexOf("--")
                            let str =value .substring(index+2,value.length);
                            //x轴的文字改为竖版显示
                            str = str.split("");
                            return str.join("\n");
                        }
                    },
                    triggerEvent: true
                    },
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
                    name: '货值',
                    type: 'bar',
                    data: data.val2,
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
                    name: '净重',
                    type: 'bar',
                    data: data.val3,
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
                    data: data.val4,
                    markPoint: {
                        data: [
                        { type: 'max', name: 'Max' },
                        { type: 'min', name: 'Min' }
                        ]
                    },
                    markLine: {
                        data: [{ type: 'average', name: 'Avg' }]
                    }
                    }
                ]
                };
        canvasI = document.getElementById(mainType)
        let myChartI = echarts.init(canvasI)
      // 绘制图表
      myChartI.setOption(option)
      myChartI.on("click", function (param) {
        // 获取dataIndex
        // console.log(2342,param)
        // let str_before = param.value.split('--')[0];
        // let index = param.value.lastIndexOf("--")
        // let str =param.value.substring(index+2,param.value.length);
        let str_before=_data[param.dataIndex].AREACODE
        let str=_data[param.dataIndex].AREANAME
        getAreaDataByCode(str_before,str,type);
     })
        
    }
    const loadChar2 = (_data,areaName,type) => {
        let date1=parseDateWithoutDayNew(listQueryI.startDate);
        let date2=parseDateWithoutDayNew(listQueryI.endDate);
        // 指定图表的配置项和数据
        let monthArry=[];
        let monthValueArry1=[];
        let monthValueArry2=[];
        let monthValueArry3=[];
        let monthValueArry4=[];
        for (let i = 0; i < _data.length; i++) {
            // areaCodeArry.push(_data[i].AREACODE);
            monthArry.push(_data[i].MON);
            monthValueArry1.push(_data[i].NUMB);
            monthValueArry2.push(_data[i].SUMTOTAL);
            monthValueArry3.push(_data[i].SUMNETWT);
            monthValueArry4.push(_data[i].SUMGROSSWET);
        }
        let data={month:monthArry,val1:monthValueArry1,val2:monthValueArry2,val3:monthValueArry3,val4:monthValueArry4};
      console.log(11111111111,data)
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
            const option = {
        title: {
            text: areaName+'        '+date1+'~'+date2+totalType+'区域单量、货值、货重分析',
            subtext: ''
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['单量', '货值','净重','毛重']
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
            barGap: '30%',
            barCategoryGap: '40%',
            barMaxWidth :'10%',
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
            name: '货值',
            type: 'bar',
            data:data.val2,
            barGap: '30%',
            barCategoryGap: '40%',
            barMaxWidth :'10%',
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
            name: '净重',
            type: 'bar',
            data: data.val3,
            barGap: '30%',
            barCategoryGap: '40%',
            barMaxWidth :'10%',
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
            data: data.val4,
            barGap: '30%',
            barCategoryGap: '40%',
            barMaxWidth :'10%',
            markPoint: {
                data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
                ]
            },
            markLine: {
                data: [{ type: 'average', name: 'Avg' }]
            }
            }
        ],
        };
        canvasI = document.getElementById(mainType)
        let myChartI = echarts.init(canvasI)
      // 绘制图表
      myChartI.setOption(option)
      myChartI.off("click")
        
    }
</script>

<style scoped lang="scss"></style>