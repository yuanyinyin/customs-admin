<template>

    <div class="customsData-container scroll-y p">

        <el-tabs v-model="activeName" @tab-change="handleTabChange">
        <el-tab-pane label="进口查验统计" name="first" >
            <el-container >
                <el-card style="width: 150%;">
                    <el-header>进口查验统计</el-header>
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
                <el-main style="width: 100%;height:500px;">
                        <div id="main1" style="width: 70%;height:100%;"></div>
                </el-main>
                </el-card>
            </el-container>
        </el-tab-pane>
        <el-tab-pane label="出口查验统计" name="second" >
            <el-container>
                <el-card style="width: 100%;">
                    <el-header>出口查验统计</el-header>

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
    getImportInspectRate()
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
    getExportInspectRate()
}
const initPickI = () => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);
    listQueryI.startDate=start
    listQueryI.endDate=end
    //异步，加入延迟
    setTimeout(function () {
    try {
        getImportInspectRate()
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
        getExportInspectRate()
    } catch (e) {
    }

    }, 500)
}
const getImportInspectRate = () => {
    let params={
      startDate: parseDateWithoutDay(listQueryI.startDate),
      endDate: parseDateWithoutDay(listQueryI.endDate),
      ieFlag:'I',
      qyFlag:'1'
    }
  store
    .dispatch('companyStatistics/getInspectRate',params)
    .then((response) => {
      console.log(response)
      tableData.value = response.items
      loadChar(response.rs,'I')

    })
    .catch((response) => {})
}
const getExportInspectRate = () => {
    let params2={
      startDate: parseDateWithoutDay(listQueryE.startDate),
      endDate: parseDateWithoutDay(listQueryE.endDate),
      ieFlag:'E',
      qyFlag:'1'
    }
  store
    .dispatch('companyStatistics/getInspectRate',params2)
    .then((response) => {
      console.log(response)
      tableData2.value = response.items
      loadChar(response.rs,'E')

    })
    .catch((response) => {})
}
let canvasI: any = null//document.getElementById('canvas')
const loadChar = (_data,type) => {

    let date1=parseDateWithoutDayNew(listQueryI.startDate);
    let date2=parseDateWithoutDayNew(listQueryI.endDate);
    // 指定图表的配置项和数据
    let monthArry=[];
    let monthValueArry1=[];
    let monthValueArry2=[];
    let monthValueArry3=[];
    for (let i = 0; i < _data.length; i++) {
        monthArry.push(_data[i].MON);
        monthValueArry1.push(_data[i].NUMBCHECKED);

        monthValueArry2.push(_data[i].NUMBUNCHECKED)
        monthValueArry3.push(_data[i].rate)
    }
    let data={month:monthArry,val1:monthValueArry1,val2:monthValueArry2,val3:monthValueArry3};
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
            text: date1+'~'+date2+totalType+'查验统计'
        },
        tooltip: {
				trigger: 'axis',
				axisPointer: {            // 坐标轴指示器，坐标轴触发有效
					type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'

				},
				formatter:function(datas){
					var res = datas[0].name + '<br/>', val;
					let percent = '';
                    let sum = '';
					if(datas[0].value=='0'){
						percent = 0;
					}
					else{
                        sum=parseFloat(datas[0].value)+parseFloat(datas[1].value);
                        console.log(sum)
						percent = ((datas[0].value / ( sum)) * 100).toFixed(2);
					}
					let dotColor0 = '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:' + datas[0].color + '"></span>'
					let dotColor1 = '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:' + datas[1].color + '"></span>'

					res += dotColor0 + datas[0].seriesName  + ": " + datas[0].value  + '</br>';
					res += dotColor1 + datas[1].seriesName  + ": " + datas[1].value  + '</br>';
					res += '查验率: '  + percent + '%' + '</br>'
					return res;

				}
			},
        legend: {
            data: ['查验数量','未查验数量'],
            top:'6%'
        },
        xAxis: {
            data:data.month,
            axisLabel:{
                interval:0,
                rotate:45,
                margin:10
            }
        },
        yAxis: {
        },
        // yAxis: {
        //     axisLabel:{
        //         interval:0,
        //         rotate:0
        //     }
        // },
        series: [
            {
            name: '查验数量',
            data:  data.val1,
            barGap: '20%',
            barCategoryGap: '30%',
            barMaxWidth:'25',
            type: 'bar',
            stack:"Search Engine",
            emphasis: {//折线图的高亮状态。
              focus: "series",//聚焦当前高亮的数据所在的系列的所有图形。
            },
            },
            {
            name: '未查验数量',
            data:  data.val2,
            barGap: '20%',
            barCategoryGap: '30%',
            barMaxWidth:'25',
            type: 'bar',
            stack:"Search Engine",
            emphasis: {//折线图的高亮状态。
              focus: "series",//聚焦当前高亮的数据所在的系列的所有图形。
            },
            }
        ]
    };
    canvasI = document.getElementById(mainType)
    let myChartI = echarts.init(canvasI)
  // 绘制图表
  myChartI.setOption(option)
}
</script>

<style scoped lang="scss"></style>
