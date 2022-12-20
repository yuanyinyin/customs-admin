<template>
    <div class="scroll-y">
      <div class="dashboard-container">

        <el-row>
          <el-col :span="24">
             <el-card :body-style="{padding:'10px'}">
              <div class="notice">
                <div class="notice-title">
                  <el-tag
                      type="danger"
                      effect="plain"
                    >
                      <svg-icon class="el-input__icon" class-name="notice-icon" icon-class="wm-notice"/>
                      <span class="notice-text">通知</span>
                  </el-tag>
                </div>

                <div class="notice-list">
                  <vue3-seamless-scroll :list="noticeList" class="notice-scroll" :step="0.8" hover="true" direction="left" :limitMoveNum="3" singleLine="true" >
                    <div class="item" v-for="(item, index) in noticeList" :key="index">
                      <el-link type="primary"  @click="openLink(item.title,item.text,item.updateTime)">{{ item.title }}</el-link>
                    </div>
                  </vue3-seamless-scroll>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>


        <el-row :gutter="10">
          <el-col :sm="12" :md="6">
            <el-card class="card-item" :body-style="{padding:'15px'}">
              <div class="card-info">
                <dl>
                  <dt>
                    <span class="data">{{numbNowDay}}</span>
                    <span class="red">{{rate1}}</span>
                  </dt>
                  <dd>今日同步单量</dd>
                </dl>
                <div class="card-icon">
                  <img  src="@/assets/main/icons/icon1.png" />
                </div>
              </div>
            </el-card>
          </el-col>

          <el-col :sm="12" :md="6">
            <el-card class="card-item" :body-style="{padding:'15px'}">
              <div class="card-info">
                <dl>
                  <dt>
                    <span class="data">{{numbNowMonth}}</span>
                    <span class="red">{{rate2}}</span>
                  </dt>
                  <dd>当月数据同步数量</dd>
                </dl>
                <div class="card-icon">
                  <img  src="@/assets/main/icons/icon2.png" />
                </div>
              </div>
            </el-card>
          </el-col>

          <el-col :sm="12" :md="6">
            <el-card class="card-item" :body-style="{padding:'15px'}">
              <div class="card-info">
                <dl>
                  <dt>
                    <span class="data">{{numbNowYear}}</span>
                    <span class="green">{{rate3}}</span>
                  </dt>
                  <dd>当年数据同步数量</dd>
                </dl>
                <div class="card-icon">
                  <img  src="@/assets/main/icons/icon3.png" />
                </div>
              </div>
            </el-card>
          </el-col>

          <el-col :sm="12" :md="6">
            <el-card class="card-item" :body-style="{padding:'15px'}">
              <div class="card-info">
                <dl>
                  <dt>
                    <span class="data">{{numbTotal}}</span>
                    <!-- <span class="green">+12.32%</span> -->
                  </dt>
                  <dd>累计同步报关单数量</dd>
                </dl>
                <div class="card-icon">
                  <img  src="@/assets/main/icons/icon4.png" />
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="24">
            <div class="page-title">
              <h3>总体进出口情况</h3>
              <div>
                <el-date-picker
                  v-model="listQueryTotalCustomData.applyDate"
                  type="monthrange"
                  style="width:200px"
                  unlink-panels
                  range-separator="-"
                  start-placeholder="开始月份"
                  end-placeholder="结束月份"
                  size="small"
                  format="YYYY-MM"
                  value-format="YYYY-MM"
                  @change="onSearchTotalCustomData"
                />
              </div>
            </div>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :sm="12" :md="12" :lg="6">
            <el-card class="card-item" :body-style="{padding:'15px',height:'100%'}">
              <div class="inout-info">
                <div class="inout-item">
                  <div class="inout-tit">
                    <img src="@/assets/main/icons/icon5.png" alt="">
                    <span>进口单量</span>
                  </div>
                  <div class="inout-con">
                    <span class="data">{{numbI}}</span>
                    <span class="unit">票</span>
                  </div>
                </div>
                <div class="inout-item">
                  <div class="inout-tit">
                    <img src="@/assets/main/icons/icon7.png" alt="">
                    <span>货值</span>
                  </div>
                  <div class="inout-con">
                    <span class="data">{{rmbI}}</span>
                    <span class="unit">万元</span>
                  </div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :sm="12" :md="12" :lg="6">
            <el-card class="card-item" :body-style="{padding:'15px',height:'100%'}">
              <div class="inout-info">
                <div class="inout-item">
                  <div class="inout-tit">
                    <img src="@/assets/main/icons/icon6.png" alt="">
                    <span>出口单量</span>
                  </div>
                  <div class="inout-con">
                    <span class="data">{{numbE}}</span>
                    <span class="unit">票</span>
                  </div>
                </div>
                <div class="inout-item">
                  <div class="inout-tit">
                    <img src="@/assets/main/icons/icon7.png" alt="">
                    <span>货值</span>
                  </div>
                  <div class="inout-con">
                    <span class="data">{{rmbE}}</span>
                    <span class="unit">万元</span>
                  </div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col  :md="24" :lg="12">
            <el-card class="card-item" :body-style="{padding:'15px',height:'100%'}" >
              <div class="statistics">
                <div class="statistics-item">
                  <pie-chart summary="汇总" unit="单" :colors="['#DE6BFF','#FFD26F']" title="进出口单量占比" :list="listOne" v-show="flagTotalCustomDataNumb" />
                </div>
                <div class="statistics-item">
                  <pie-chart summary="汇总" unit="单" :colors="['#DE6BFF','#FFD26F']" title="进出口货值占比" :list="listTwo" v-show="flagTotalCustomDataSum"/>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>


        <el-row :gutter="10">
          <el-col :span="24">
            <div class="page-title">
              <h3>近一年报关单数据情况</h3>
              <div>
                <el-date-picker
                  v-model="listQueryCustomsData.applyDate"
                  type="monthrange"
                  style="width:200px"
                  unlink-panels
                  range-separator="-"
                  start-placeholder="开始月份"
                  end-placeholder="结束月份"
                  size="small"
                  format="YYYY-MM"
                  value-format="YYYY-MM"
                  @change="onSearchCustomsData"
                />
              </div>
            </div>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="24">
            <el-card class="card-item" :body-style="{padding:'15px'}" >
              <template #header>
                <div class="card-header">
                  <el-tabs v-model="activeName" class="nav-tabs" @tab-change="switchReport">
                    <el-tab-pane label="单 量" name="first"></el-tab-pane>
                    <el-tab-pane label="货 值" name="second"></el-tab-pane>
                  </el-tabs>
                </div>
              </template>
              <div v-if="activeName == 'first'" class="nav-report">
                <line-chart :list="listThree"  v-show="flagCustomNumb"/>
              </div>
              <div v-if="activeName == 'second'" class="nav-report">
                <line-chart :list="listFour" v-show="flagCustomSum" />
              </div>
            </el-card>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="24">
            <div class="page-title">
              <h3>近一年申报商品货值TOP10</h3>
            </div>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :sm="24" :md="14">
            <el-card class="card-item" :body-style="{padding:'15px',height:'100%'}" >
              <template #header>
                <div class="inout-header">
                  <el-tabs v-model="activeIndex" class="nav-tabs" @tab-change="switchInOut">
                    <el-tab-pane label="进口" name="first"></el-tab-pane>
                    <el-tab-pane label="出口" name="second"></el-tab-pane>
                  </el-tabs>
                  <div>
                    <el-date-picker
                      v-model="listQueryTopGoods.applyDate"
                      type="monthrange"
                      style="width:200px"
                      unlink-panels
                      range-separator="-"
                      start-placeholder="开始月份"
                      end-placeholder="结束月份"
                      size="small"
                      format="YYYY-MM"
                      value-format="YYYY-MM"
                      @change="onSearchTopGoods"
                    />
                  </div>
                </div>
              </template>
              <div v-if="activeIndex == 'first'">
                <el-table :data="tableDataOne" style="width: 100%" size="small" :show-header="false">
                  <el-table-column type="index" :index="index" />
                  <el-table-column prop="GNAME" show-overflow-tooltip />
                  <el-table-column prop="RMB" width="120" align="right">
                    <template #default="scope">￥{{ scope.row.RMB || 0 }}</template>
                  </el-table-column>
                </el-table>
              </div>
              <div v-if="activeIndex == 'second'">
                <el-table :data="tableDataTwo" style="width: 100%" size="small" :show-header="false">
                  <el-table-column type="index" :index="index" />
                  <el-table-column prop="GNAME" show-overflow-tooltip />
                  <el-table-column prop="RMB" width="120" align="right">
                    <template #default="scope">￥{{ scope.row.RMB || 0 }}</template>
                  </el-table-column>
                </el-table>
              </div>
            </el-card>
          </el-col>
          <el-col :sm="24" :md="10">
             <el-card class="card-item" :body-style="{padding:'15px',height:'100%'}" >
              <template #header>
                <div class="inout-header">
                  <div class="card-title">查验率</div>

                  <div>
                    <el-date-picker
                      v-model="listQueryInspectRate.applyDate"
                      type="monthrange"
                      style="width:200px"
                      unlink-panels
                      range-separator="-"
                      start-placeholder="开始月份"
                      end-placeholder="结束月份"
                      size="small"
                      format="YYYY-MM"
                      value-format="YYYY-MM"
                      @change="onSearchInspectRate"
                    />
                  </div>
                </div>
              </template>
              <div class="report-box">
                <pie-chart unit="次" :colors="['#2C9AD5','#FFD26F']" :list="listFive" v-show="flagInpspectRate"/>
              </div>
             </el-card>
          </el-col>
        </el-row>


      </div>
    </div>
  <el-dialog v-model="tablemodel"  :dataTmp="dataTmp" :dataname="dataname" :datatype="datatype"  @open="openFun"
             width="75%"
             align-center>

    <div id="main3" style="width: 100%;height:400px;"></div>

  </el-dialog>
  </template>

  <script lang="ts" setup>
  import type { TabsPaneContext } from 'element-plus'
  import { Vue3SeamlessScroll } from "vue3-seamless-scroll";
  import PieChart from '../components/PieChart/index';
  import LineChart from '../components/LineChart/index';
  import { ElMessageBox, ElMessage } from 'element-plus'
  import { parseDateWithoutDay,parseTimeThrid} from '@/utils/dateTime'
import { fa } from 'element-plus/es/locale';


  const store = useStore()

  const noticeList = ref([])
  const listOne = ref([])
  const listTwo = ref([])
  const listThree = ref([])
  const listFour = ref([])
  const listFive = ref([])
  const flagTotalCustomDataNumb = ref(false)
  const flagTotalCustomDataSum = ref(false)
  const flagCustomNumb = ref(false)
  const flagCustomSum = ref(false)
  const flagInpspectRate = ref(false)
  //总体进出口情况
  const onSearchTotalCustomData = () => {


 if(listQueryTotalCustomData.applyDate?.length==0||listQueryTotalCustomData.applyDate==null){
     ElMessage({ message: '总体进出口情况查询   请填写统计起始日期', type: 'error' })
     return;
 }
 let startDate1=  listQueryTotalCustomData.applyDate?.length>0 ?listQueryTotalCustomData.applyDate[0]:"" ;
 let endDate1=  listQueryTotalCustomData.applyDate?.length>0 ?listQueryTotalCustomData.applyDate[1]:"";
 if(startDate1.substr(0, startDate1.length - 3)!=endDate1.substr(0, startDate1.length - 3)){
     ElMessage({ message: '总体进出口情况查询     统计起始日期应为同一年份', type: 'error' })
     return;
 }
 getTotalCustomData();
}
  //报关单
  const onSearchCustomsData = () => {


 if(listQueryCustomsData.applyDate?.length==0||listQueryCustomsData.applyDate==null){
     ElMessage({ message: '报关单数据情况查询   请填写统计起始日期', type: 'error' })
     return;
 }
 let startDate1=  listQueryCustomsData.applyDate?.length>0 ?listQueryCustomsData.applyDate[0]:"" ;
 let endDate1=  listQueryCustomsData.applyDate?.length>0 ?listQueryCustomsData.applyDate[1]:"";
 if(startDate1.substr(0, startDate1.length - 3)!=endDate1.substr(0, startDate1.length - 3)){
     ElMessage({ message: '报关单数据情况查询     统计起始日期应为同一年份', type: 'error' })
     return;
 }
 if(activeName.value=='first'){
        getCustomsDataNumb();
  }else if(activeName.value=='second'){
        getCustomsDataSum();
  }
}
  //查验率
const onSearchInspectRate = () => {


    if(listQueryInspectRate.applyDate?.length==0||listQueryInspectRate.applyDate==null){
        ElMessage({ message: '查验率查询    请填写统计起始日期', type: 'error' })
        return;
    }
    let startDate1=  listQueryInspectRate.applyDate?.length>0 ?listQueryInspectRate.applyDate[0]:"" ;
    let endDate1=  listQueryInspectRate.applyDate?.length>0 ?listQueryInspectRate.applyDate[1]:"";
    if(startDate1.substr(0, startDate1.length - 3)!=endDate1.substr(0, startDate1.length - 3)){
        ElMessage({ message: '查验率查询     统计起始日期应为同一年份', type: 'error' })
        return;
    }
    getInspectRateDash();
}
//商品top10
  const onSearchTopGoods = () => {
    if(listQueryTopGoods.applyDate?.length==0||listQueryTopGoods.applyDate==null){
        ElMessage({ message: '申报商品货值TOP10查询     请填写统计起始日期', type: 'error' })
        return;
    }
    let startDate1=  listQueryTopGoods.applyDate?.length>0 ?listQueryTopGoods.applyDate[0]:"" ;
    let endDate1=  listQueryTopGoods.applyDate?.length>0 ?listQueryTopGoods.applyDate[1]:"";
    if(startDate1.substr(0, startDate1.length - 3)!=endDate1.substr(0, startDate1.length - 3)){
        ElMessage({ message: '申报商品货值TOP10查询     统计起始日期应为同一年份', type: 'error' })
        return;
    }
    if(activeIndex.value=='first'){
        getTopGoods('I');
     }else{
        getTopGoods('E');
     }
}
//商品top10
  const tableDataOne: any = ref(null)
  const tableDataTwo: any = ref(null)

  const dateRange = ref('')
  const dateRangeTwo = ref('')
  const dateRangeThree = ref('')
  const numbI = ref('')
  const numbE = ref('')
  const rmbI = ref('')
  const rmbE = ref('')
  const numbNowDay = ref('')
  const rate1 = ref('')
  const numbNowMonth = ref('')
  const rate2 = ref('')
  const numbNowYear = ref('')
  const rate3 = ref('')
  const numbTotal = ref('')
  const activeName = ref('first')
  //报关单
  const switchReport = (tab: TabsPaneContext, event: Event) => {
    console.log(tab, event)
    flagCustomNumb.value=false;
      flagCustomSum.value=false;
    if(activeName.value=='first'){

        initCustomsDataNumb();
     }else{
        initCustomsDataSum();
     }
  }
//总体进出口情况
const listQueryTotalCustomData = reactive({

 applyDate: [],
})
//报关单
const listQueryCustomsData = reactive({

 applyDate: [],
})
//查验率
const listQueryInspectRate = reactive({

 applyDate: [],
})



//商品货值top10
const listQueryTopGoods = reactive({

 applyDate: [],
})
  //商品top10
  const activeIndex = ref('first')
  const switchInOut = (tab: TabsPaneContext, event: Event) => {
    console.log(tab, event)
     if(activeIndex.value=='first'){
        initTopGoods('I');
     }else{
        initTopGoods('E');
     }


  }
  const initNotice = () => {
    let params2={}

    store
      .dispatch('companyStatistics/getNoticeInfoDash',params2)
      .then((responseE) => {
        noticeList.value=responseE
      })
      .catch((response) => {})
  }

  //总体进出口情况
    const initTotalCustomData = () => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);

    listQueryTotalCustomData.applyDate[0]=parseDateWithoutDay(start)
    listQueryTotalCustomData.applyDate[1]=parseDateWithoutDay(end)
    getTotalCustomData();
}
  //单量以及货值
  const initCustomsDataNumb = () => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);

    listQueryCustomsData.applyDate[0]=parseDateWithoutDay(start)
    listQueryCustomsData.applyDate[1]=parseDateWithoutDay(end)
    getCustomsDataNumb();
}
const initCustomsDataSum = () => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);

    listQueryCustomsData.applyDate[0]=parseDateWithoutDay(start)
    listQueryCustomsData.applyDate[1]=parseDateWithoutDay(end)
    getCustomsDataSum();
}
  //查验率


  const initInspectRate = () => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);

    listQueryInspectRate.applyDate[0]=parseDateWithoutDay(start)
    listQueryInspectRate.applyDate[1]=parseDateWithoutDay(end)
    getInspectRateDash();
}

  const initTopGoods = (inittype) => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);
    listQueryTopGoods.applyDate[0]=parseDateWithoutDay(start)
    listQueryTopGoods.applyDate[1]=parseDateWithoutDay(end)

    getTopGoods(inittype)

}


const initCalculateData = () => {
    let params={
      qyFlag:'1',
      keyFlag:'0'
    }
  store
    .dispatch('companyStatistics/getCalculateCustomDataDash',params)
    .then((response) => {
        numbNowDay.value=response.rs[0].NUMBNOWDAY
        rate1.value=response.rs[0].rate1
        numbNowMonth.value=response.rs[0].NUMBNOWMONTH
        rate2.value=response.rs[0].rate2
        numbNowYear.value=response.rs[0].NUMBNOWYEAR
        rate3.value=response.rs[0].rate3
        numbTotal.value=response.rs[0].NUMBTOTAL
    })
    .catch((response) => {})
}
const getTotalCustomData = () => {
  flagTotalCustomDataNumb.value=false;
  flagTotalCustomDataSum.value=false;
    let startDate1=  listQueryTotalCustomData.applyDate?.length>0 ?listQueryTotalCustomData.applyDate[0]:"" ;
    let endDate1=  listQueryTotalCustomData.applyDate?.length>0 ?listQueryTotalCustomData.applyDate[1]:"";
    let params={
      startDate: startDate1,
      endDate: endDate1,
      qyFlag:'1',
      keyFlag:'0'
    }
  store
    .dispatch('companyStatistics/getTotalCustomDataDash',params)
    .then((response) => {
        numbI.value=response.rs[0].NUMBI
        numbE.value=response.rs[0].NUMBE
        rmbI.value=response.rs[0].RMBI
        rmbE.value=response.rs[0].RMBE
      flagTotalCustomDataNumb.value=false;
      flagTotalCustomDataSum.value=false;
        listOne.value=[]
        listTwo.value=[]
        let nameI='进口单量';
        let valueI=parseInt(response.rs[0].NUMBI);
        let dataI={name:nameI,value:valueI};
        listOne.value.push(dataI)
        let nameE='出口单量';
        let valueE= parseInt(response.rs[0].NUMBE);
        let dataE={name:nameE,value:valueE};
        listOne.value.push(dataE)

        let nameI1='进口货值';
        let valueI1=parseFloat(response.rs[0].RMBI);
        let dataI1={name:nameI1,value:valueI1};
        listTwo.value.push(dataI1)
        let nameE1='出口货值';
        let valueE1= parseFloat(response.rs[0].RMBE);
        let dataE1={name:nameE1,value:valueE1};
        listTwo.value.push(dataE1)

        flagTotalCustomDataNumb.value=true;
        flagTotalCustomDataSum.value=true;


    })
    .catch((response) => {})
}

const getCustomsDataNumb = () => {

    let startDate1=  listQueryCustomsData.applyDate?.length>0 ?listQueryCustomsData.applyDate[0]:"" ;
    let endDate1=  listQueryCustomsData.applyDate?.length>0 ?listQueryCustomsData.applyDate[1]:"";
    let params={
      startDate: startDate1,
      endDate: endDate1,
      qyFlag:'1',
      ieFlag:'I'
    }

  store
    .dispatch('companyStatistics/getImOrExCustomsData',params)
    .then((response) => {
        listThree.value=[]
        let dataI1 = response.rs

        let monthArryI=[];
        let monthValueArryI=[];
        let colorArryI= ['rgba(31,223,55,0)', '#26C6DA'];
        for (let i = 0; i < dataI1.length; i++) {
          monthArryI.push(parseInt(dataI1[i].MON));
          monthValueArryI.push(parseInt(dataI1[i].NUMB));
        }
        let dataI={month:monthArryI,data:monthValueArryI,title:'进口单量',colors:colorArryI};
        listThree.value.push(dataI)
      let params2={
      startDate: startDate1,
      endDate: endDate1,
      qyFlag:'1',
      ieFlag:'E'
    }
    store
    .dispatch('companyStatistics/getImOrExCustomsData',params2)
    .then((responseE) => {

        let dataE1 = responseE.rs

        let monthArryE=[];
        let monthValueArryE=[];
        let colorArryE=  ['rgba(49,171,227,0)', '#1E88E5'];
        if(dataE1){
          for (let i = 0; i < dataE1.length; i++) {
            monthArryE.push(dataE1[i].MON);
            monthValueArryE.push(dataE1[i].NUMB);
         }
        let dataE={month:monthArryE,data:monthValueArryE,title:'出口单量',colors:colorArryE};
        listThree.value.push(dataE)
        }

        flagCustomNumb.value = true

    })
    .catch((response) => {})

    })
    .catch((response) => {})
}
const getCustomsDataSum = () => {

 let startDate1=  listQueryCustomsData.applyDate?.length>0 ?listQueryCustomsData.applyDate[0]:"" ;
 let endDate1=  listQueryCustomsData.applyDate?.length>0 ?listQueryCustomsData.applyDate[1]:"";
 let params={
   startDate: startDate1,
   endDate: endDate1,
   qyFlag:'1',
   ieFlag:'I'
 }
 listFour.value=[]
store
 .dispatch('companyStatistics/getCustomSumDash',params)
 .then((response) => {

     let dataI1 = response.rs

     let monthArryI=[];
     let monthValueArryI=[];
     let colorArryI= ['rgba(31,223,55,0)','rgba(31,223,55,1)'];
     for (let i = 0; i < dataI1.length; i++) {
       monthArryI.push(dataI1[i].MON);
       monthValueArryI.push(dataI1[i].RMB);
     }
     let dataI={month:monthArryI,data:monthValueArryI,title:'进口货值',colors:colorArryI};
     listFour.value.push(dataI)
   let params2={
   startDate: startDate1,
   endDate: endDate1,
   qyFlag:'1',
   ieFlag:'E'
 }
 store
 .dispatch('companyStatistics/getCustomSumDash',params2)
 .then((responseE) => {

     let dataE1 = responseE.rs

     let monthArryE=[];
     let monthValueArryE=[];
     let colorArryE= ['rgba(49,171,227,0)','rgba(49,171,227,1)'];
     if(dataE1){
       for (let i = 0; i < dataE1.length; i++) {
         monthArryE.push(dataE1[i].MON);
         monthValueArryE.push(dataE1[i].RMB);
      }
     let dataE={month:monthArryE,data:monthValueArryE,title:'出口货值',colors:colorArryE};
     listFour.value.push(dataE)
     }
     flagCustomSum.value = true

 })
 .catch((response) => {})

 })
 .catch((response) => {})
}
const getInspectRateDash = () => {
  flagInpspectRate.value = false
    let startDate1=  listQueryInspectRate.applyDate?.length>0 ?listQueryInspectRate.applyDate[0]:"" ;
    let endDate1=  listQueryInspectRate.applyDate?.length>0 ?listQueryInspectRate.applyDate[1]:"";
    let params={
      startDate: startDate1,
      endDate: endDate1,
      qyFlag:'1'
    }
    listFive.value =[]
  store
    .dispatch('companyStatistics/getInspectRateDash',params)
    .then((response) => {
        console.log(177777,response.rs)
        let data = response.rs
        if(data) {
            for(let i=0;i<data.length;i++) {

                let subJson = {}
              subJson.value = parseInt(data[i].VALUE)
                subJson.name = data[i].NAME

                console.log(subJson)

                console.log(123,listFive)
                listFive.value.push(subJson)
                console.log(124,listFive.value)
            }
        }
        flagInpspectRate.value = true
    })
    .catch((response) => {})
}
const getTopGoods = (inittype) => {
    let startDate1=  listQueryTopGoods.applyDate?.length>0 ?listQueryTopGoods.applyDate[0]:"" ;
    let endDate1=  listQueryTopGoods.applyDate?.length>0 ?listQueryTopGoods.applyDate[1]:"";
    let params={
      startDate: startDate1,
      endDate: endDate1,
      ieFlag:inittype,
      qyFlag:'1'
    }
  store
    .dispatch('companyStatistics/getTopGoods',params)
    .then((response) => {
        if(inittype=='I'){
            tableDataOne.value = response.rs
        }else if(inittype=='E'){
            tableDataTwo.value= response.rs
        }

    })
    .catch((response) => {})
}
  const openLink = (title,text,updateTime) => {
    ElMessageBox({
      // title: title, //MessageBox 标题
      // message: `修改时间：`+updateTime+`<br/>`+text,
      message:`<div class="article"  >`+
            `<h2 style="color: #355e92;font-size: 20px; line-height: 2;text-align: center;">`+title+`</h2>`+
            `<h3 class="tit-info" style="padding: 30px 0 20px; margin-bottom: 10px;border-bottom: 1px solid #ccc;color: #666;font-size: 15px;line-height: 1;
                text-align: center;">发布时间：<span style="padding-left: 10px;">`+parseTimeThrid(updateTime)+`</span></h3>
                <p style="line-height: 35px; margin-bottom: 5px; text-indent: 2em; text-align: left; font-size: 18px;color: #333;font-size: 16px; line-height: 2;">`+text+`</p>
             </div>`,
      dangerouslyUseHTMLString:true,
      customClass: 'message-logout'
    })
  }
onMounted(() => {
  initNotice();
    initCalculateData();
  //总体进出口情况
  initTotalCustomData();
    //报关单
    initCustomsDataNumb();
    //商品top10
    initTopGoods('I');
    //查验率
    initInspectRate()
})
  </script>

  <style lang="scss" scoped>
@import '@/styles/wm.scss';
  </style>

  <style scoped>
    .card-item >>> .el-card__header{
      padding: 0px !important;
    }

    .nav-tabs >>> .el-tabs__header{
      margin: 0;
    }

    .nav-tabs >>>  .el-tabs__nav-wrap::after{
      height: 0;
    }


  </style>
<style>

.message-logout {
  width: 55%;
}
.el-message-box{
  max-width:none;
  vertical-align: middle;
}
</style>
