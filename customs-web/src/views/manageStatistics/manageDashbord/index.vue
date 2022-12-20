<template>
  <div class="scroll-y">
    <div class="dashboard-container">

<!--      <el-row>-->
<!--        <el-col :span="24">-->
<!--           <el-card :body-style="{padding:'10px'}">-->
<!--            <div class="notice">-->
<!--              <div class="notice-title">-->
<!--                <el-tag-->
<!--                    type="danger"-->
<!--                    effect="plain"-->
<!--                  >-->
<!--                    <svg-icon class="el-input__icon" class-name="notice-icon" icon-class="wm-notice"/>-->
<!--                    <span class="notice-text">通知</span>-->
<!--                </el-tag>-->
<!--              </div>-->

<!--              <div class="notice-list">-->
<!--                <vue3-seamless-scroll :list="noticeList" class="notice-scroll" :step="0.8" hover="true" direction="left" :limitMoveNum="3" singleLine="true" >-->
<!--                  <div class="item" v-for="(item, index) in noticeList" :key="index">-->
<!--                    <el-link type="primary">{{ item.title }}</el-link>-->
<!--                  </div>-->
<!--                </vue3-seamless-scroll>-->
<!--              </div>-->
<!--            </div>-->
<!--          </el-card>-->
<!--        </el-col>-->
<!--      </el-row>-->

      <el-row :gutter="10">
        <el-col :span="24">
          <el-card class="card-item" :body-style="{padding:'5px 15px 15px',height:'100%'}" >
            <template #header>
              <div class="inout-header">
                <div class="common-title">
                  <h3>平台活跃度</h3>
                  <el-tabs v-model="activeMonth" class="nav-tabs" @tab-change="switchMonth">
                    <el-tab-pane label="今日" name="first"></el-tab-pane>
                    <el-tab-pane label="本周" name="second"></el-tab-pane>
                    <el-tab-pane label="本月" name="third"></el-tab-pane>
                    <el-tab-pane label="本年" name="fourth"></el-tab-pane>
                  </el-tabs>
                </div>
              </div>
            </template>
            <el-row :gutter="15">
              <el-col :md="24" :lg="12">
                <div class="data-item">
                  <div class="tit">活跃企业数</div>
                  <div class="stati">
                    <span class="number blue">{{ liveness.activeEnterprise.amount || 0 }}</span><span class="unit">家</span>
                  </div>
                  <div class="ratio" v-if="showflag">
                    同比
                    <span v-if="liveness.activeEnterprise.proportion >= 0" class="green">↑ {{ liveness.activeEnterprise.proportion || 0}}%</span>
                    <span v-else class="red">↓ {{ Math.abs(liveness.activeEnterprise.proportion) || 0}}%</span>
                  </div>
                  <img class="data-img" src="@/assets/main/icons/icon8.png" alt="">
                </div>
              </el-col>
              <el-col :md="24" :lg="12">
                <div class="data-item">
                  <div class="tit">重点企业数</div>
                  <div class="stati">
                    <span class="number red">{{liveness.keyEnterprise.molecule || 0}}/</span><span class="denominator">{{liveness.keyEnterprise.denominator || 0}}</span>
                  </div>
                  <div class="ratio" v-if="showflag">
                    同比
                    <span v-if="liveness.keyEnterprise.proportion >= 0" class="green">↑ {{ liveness.keyEnterprise.proportion || 0}}%</span>
                    <span v-else class="red">↓ {{ Math.abs(liveness.keyEnterprise.proportion) || 0}}%</span>
                  </div>
                  <img class="data-img" src="@/assets/main/icons/icon9.png" alt="">
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>


       <el-row :gutter="10">
        <el-col :span="24">
          <el-card class="card-item" :body-style="{padding:'5px 15px 15px',height:'100%'}" >
            <template #header>
              <div class="inout-header">
                <div class="common-title">
                  <h3>数据同步量</h3>
                </div>
                <div class="opt-group">
                  <el-radio-group v-model="selectIndex" @change="toggleData" size="small">
                    <el-radio-button label="1">全部企业</el-radio-button>
                    <el-radio-button label="2">重点企业</el-radio-button>
                  </el-radio-group>
                </div>
              </div>
            </template>
            <el-row :gutter="15">
              <el-col :md="24" :lg="8">
                <div class="info-item">
                  <div class="info-desc">
                    <div class="id-data">
                      <span class="number">{{numbNowDay}}</span>
                      <span class="denominator green">{{rate1}}</span>
                    </div>
                    <div class="tit">今日同步量</div>
                  </div>
                  <div class="info-icon"><img src="@/assets/main/icons/icon10.png" /></div>
                </div>
              </el-col>
              <el-col :md="24" :lg="8">
                <div class="info-item">
                  <div class="info-desc">
                    <div class="id-data">
                      <span class="number">{{numbNowMonth}}</span>
                      <span class="denominator red">{{rate2}}</span>
                    </div>
                    <div class="tit">本月同步量</div>
                  </div>
                  <div class="info-icon"><img src="@/assets/main/icons/icon11.png" /></div>
                </div>
              </el-col>
              <el-col :md="24" :lg="8">
                <div class="info-item">
                  <div class="info-desc">
                    <div class="id-data">
                      <span class="number">{{numbNowYear}}</span>
                      <span class="denominator green">{{rate3}}</span>
                    </div>
                    <div class="tit">本年同步量</div>
                  </div>
                  <div class="info-icon"><img src="@/assets/main/icons/icon12.png" /></div>
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
       </el-row>

      <el-row :gutter="10">
        <el-col :span="24">
          <el-card class="card-item" :body-style="{padding:'5px 15px 15px',height:'100%'}" >
            <template #header>
              <div class="inout-header">
                <div class="common-title">
                  <h3>总体进出口情况</h3>
                </div>
                <div class="opt-group">
                  <el-radio-group v-model="selectIndexTwo" @change="toggleDataTwo" size="small">
                    <el-radio-button label="1">全部企业</el-radio-button>
                    <el-radio-button label="2">重点企业</el-radio-button>
                  </el-radio-group>
                  <el-date-picker
                    v-model="listQueryTotalCustomData.applyDate"
                    type="monthrange"
                    style="width:200px; margin-left:5px;"
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
            </template>
            <el-row :gutter="15">
              <el-col :md="12" :lg="6">
                <div class="tab-info">
                  <div class="tab-tit">进口</div>
                  <div class="tab-con">
                    <div class="tab-item">
                      <div class="lab">单量</div>
                      <div class="val"><span class="txt">{{numbI}}</span><span class="unit">票</span></div>
                    </div>

                    <div class="tab-item">
                      <div class="lab">金额</div>
                      <div class="val"><span class="txt">{{rmbI}}</span><span class="unit">万元</span></div>
                    </div>
                  </div>
                </div>
              </el-col>
              <el-col :md="12" :lg="6">
                <div class="tab-info">
                  <div class="tab-tit">出口</div>
                  <div class="tab-con">
                    <div class="tab-item">
                      <div class="lab">单量</div>
                      <div class="val"><span class="txt">{{numbE}}</span><span class="unit">票</span></div>
                    </div>

                    <div class="tab-item">
                      <div class="lab">金额</div>
                      <div class="val"><span class="txt">{{rmbE}}</span><span class="unit">万元</span></div>
                    </div>
                  </div>
                </div>
              </el-col>
              <el-col :md="24" :lg="12">
                <div class="rg-box">
                  <div class="rg-item">
                    <pie-chart summary="汇总" unit="单" :colors="['#DE6BFF','#FFD26F']" title="货值" :list="listOne" v-if="flagTotalCustomDataSum"/>
                  </div>
                  <div class="rg-item">
                    <pie-chart summary="汇总" unit="单" :colors="['#DE6BFF','#FFD26F']" title="单量" :list="listTwo" v-if="flagTotalCustomDataNumb"/>
                  </div>
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="24">
          <el-card class="card-item" :body-style="{padding:'15px',height:'100%'}" >
            <template #header>
              <div class="inout-header">
                <div class="common-title">
                  <el-tabs v-model="activeName" class="nav-tabs" @tab-change="switchReport">
                    <el-tab-pane label="单量" name="first"></el-tab-pane>
                    <el-tab-pane label="货值" name="second"></el-tab-pane>
                    <el-tab-pane label="货量" name="third"></el-tab-pane>
                  </el-tabs>
                </div>
                 <div class="opt-group">
                  <el-radio-group v-model="selectIndexThree" @change="toggleDataThree" size="small">
                    <el-radio-button label="1">全部企业</el-radio-button>
                    <el-radio-button label="2">重点企业</el-radio-button>
                  </el-radio-group>
                  <el-date-picker
                    v-model="listQueryReport.applyDate"
                    type="monthrange"
                    style="width:200px; margin-left:5px;"
                    unlink-panels
                    range-separator="-"
                    start-placeholder="开始月份"
                    end-placeholder="结束月份"
                    size="small"
                    format="YYYY-MM"
                    value-format="YYYY-MM"
                    @change="onSearchReport"
                  />
                 </div>
              </div>
            </template>
            <div v-if="activeName == 'first'" class="nav-report">
              <line-chart :list="listThree"  v-if="flagThree"/>
            </div>
            <div v-if="activeName == 'second'" class="nav-report">
              <line-chart :list="listFour"  v-if="flagFour"/>
            </div>
            <div v-if="activeName == 'third'" class="nav-report">
              <line-chart :list="listFive"  v-if="flagFive"/>
            </div>
          </el-card>
        </el-col>
      </el-row>


    <el-row :gutter="10">
        <el-col :span="24">
          <el-card class="card-item" :body-style="{padding:'15px',height:'100%'}" >
            <template #header>
              <div class="inout-header">
                <div class="common-title">
                  <el-tabs v-model="activeNameTwo" class="nav-tabs" @tab-change="switchReportTwo">
                    <el-tab-pane label="单量" name="first"></el-tab-pane>
                    <el-tab-pane label="货值" name="second"></el-tab-pane>
                    <el-tab-pane label="货量" name="third"></el-tab-pane>
                  </el-tabs>
                </div>
                 <div class="opt-group">
                  <el-radio-group v-model="selectIndexFour" @change="toggleDataFour" size="small">
                    <el-radio-button label="1">全部企业</el-radio-button>
                    <el-radio-button label="2">重点企业</el-radio-button>
                  </el-radio-group>
                  <el-date-picker
                    v-model="listQueryReport2.applyDate"
                    type="monthrange"
                    style="width:200px; margin-left:5px;"
                    unlink-panels
                    range-separator="-"
                    start-placeholder="开始月份"
                    end-placeholder="结束月份"
                    size="small"
                    format="YYYY-MM"
                    value-format="YYYY-MM"
                    @change="onSearchReport2"
                  />
                 </div>
              </div>
            </template>
            <div v-if="activeNameTwo == 'first'" class="nav-report">
              <bar-chart :list="listSix" v-if="flagSix"/>
            </div>
            <div v-if="activeNameTwo == 'second'" class="nav-report">
              <bar-chart :list="listSeven" v-if="flagSeven" />
            </div>
            <div v-if="activeNameTwo == 'third'" class="nav-report">
              <bar-chart :list="listEight" v-if="flagEight"/>
            </div>
          </el-card>
        </el-col>
      </el-row>


    </div>
  </div>
</template>

<script lang="ts" setup>
import { tabBarProps, TabsPaneContext } from 'element-plus'
import { Vue3SeamlessScroll } from "vue3-seamless-scroll";
import PieChart from '../../components/PieChart/index';
import LineChart from '../../components/LineChart/index';
import BarChart from '../../components/BarChart/index';
import { ElMessageBox, ElMessage } from 'element-plus'
import { parseDateWithoutDay} from '@/utils/dateTime';
import { type } from 'os';
const store = useStore()
//模拟数据
const noticeList = reactive([
  {
    title: '南通跨境电商迎来实质性利好'
  },
  {
    title: '关于建设“南通跨境电子商务综合试验区”的一点思考'
  },
  {
    title: '总值超三千亿，同比增长13.2% 前10月南通外贸进出口数据出炉'
  },
  {
    title: '南通电子口岸组织党员干部观看党的二十大开幕盛况'
  },
  {
    title: 'RCEP生效6个月 ，海安市签发RCEP原产地证书164份'
  }
])

const listOne = ref([])
const listTwo = ref([])
const showflag = ref(true)


const listThree = ref([])
const listFour = ref([])
const listFive = ref([])
const listSix = ref([])
const listSeven = ref([])
const listEight = ref([])
const flagThree = ref(false)
const flagFour = ref(false)
const flagFive = ref(false)
const flagSix = ref(false)
const flagSeven = ref(false)
const flagEight = ref(false)


const dateRange = ref('')
const dateRangeTwo = ref('')
const dateRangeThree = ref('')
//总体进出口情况
const listQueryTotalCustomData = reactive({

 applyDate: [],
})
//单量货值货量1
const listQueryReport = reactive({

 applyDate: [],
})
const listQueryReport2 = reactive({

 applyDate: [],
})
const flagTotalCustomDataNumb = ref(false)
const flagTotalCustomDataSum = ref(false)
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

const activeName = ref('first')
const switchReport = (tab: TabsPaneContext, event: Event) => {
    flagThree.value=false;
    flagFour.value=false;
    flagFive.value=false;
    initCustomDataDash(selectIndexThree._rawValue,activeName.value);
}

const activeNameTwo = ref('first')
const switchReportTwo = (tab: TabsPaneContext, event: Event) => {

    flagSix.value=false;
    flagSeven.value=false;
    flagEight.value=false;
    initAreaDataDash(selectIndexFour._rawValue,activeNameTwo.value);

}

const liveness = reactive({
  activeEnterprise:{
    amount: '',
    proportion: ''
  },
  keyEnterprise:{
    molecule: '',
    denominator: '',
    proportion: ''
  }
})
const activeMonth = ref('first')
const switchMonth = (tab: TabsPaneContext, event: Event) => {
  initDashHead(activeMonth.value)
}

const selectIndex = ref(1)
const toggleData = (index) => {
  initCustomData(index)
}

const selectIndexTwo = ref(1)
const toggleDataTwo = (index) => {
  getTotalCustomData(index)
}

const selectIndexThree = ref(1)
const toggleDataThree = (index) => {
  initCustomDataDash(index,'first')
}

const selectIndexFour = ref(1)
const toggleDataFour = (index) => {
  initAreaDataDash(index,'first')
}

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
 getTotalCustomData(selectIndexTwo._rawValue);
}
// 单量货值货量
const onSearchReport = () => {


 if(listQueryReport.applyDate?.length==0||listQueryReport.applyDate==null){
     ElMessage({ message: '单量货值货量查询   请填写统计起始日期', type: 'error' })
     return;
 }
 let startDate1=  listQueryReport.applyDate?.length>0 ?listQueryReport.applyDate[0]:"" ;
 let endDate1=  listQueryReport.applyDate?.length>0 ?listQueryReport.applyDate[1]:"";
 if(startDate1.substr(0, startDate1.length - 3)!=endDate1.substr(0, startDate1.length - 3)){
     ElMessage({ message: '单量货值货量查询     统计起始日期应为同一年份', type: 'error' })
     return;
 }
 getCustomDataDash(selectIndexThree._rawValue,activeName.value);
}
// 区域单量货值货量
const onSearchReport2 = () => {


 if(listQueryReport2.applyDate?.length==0||listQueryReport2.applyDate==null){
     ElMessage({ message: '区域单量货值货量查询   请填写统计起始日期', type: 'error' })
     return;
 }
 let startDate1=  listQueryReport2.applyDate?.length>0 ?listQueryReport2.applyDate[0]:"" ;
 let endDate1=  listQueryReport2.applyDate?.length>0 ?listQueryReport2.applyDate[1]:"";
 if(startDate1.substr(0, startDate1.length - 3)!=endDate1.substr(0, startDate1.length - 3)){
     ElMessage({ message: '区域单量货值货量查询     统计起始日期应为同一年份', type: 'error' })
     return;
 }
 getAreaDataDash(selectIndexFour._rawValue,activeNameTwo.value);
}
const initDashHead = (indexvalue) => {
  let params={
        qyFlag:'0',
  }
  showflag.value=true;
  store
    .dispatch('manageStatistics/getQyUseDataDash',params)
    .then((response) => {

      if(indexvalue=='first'){
        liveness.activeEnterprise.amount = response.rs[0].NOWDAY
        liveness.activeEnterprise.proportion = response.rs[0].rate1
        liveness.keyEnterprise.molecule =  response.rs[0].NOWDAY1
        liveness.keyEnterprise.denominator =  response.rs[0].NOWDAY11
        liveness.keyEnterprise.proportion = response.rs[0].rate11
      }else if(indexvalue=='second'){
        liveness.activeEnterprise.amount = response.rs[0].NOWWEEK
        liveness.activeEnterprise.proportion = response.rs[0].rate3
        liveness.keyEnterprise.molecule =  response.rs[0].NOWWEEK1
        liveness.keyEnterprise.denominator =  response.rs[0].NOWWEEK11
        liveness.keyEnterprise.proportion = response.rs[0].rate33
        showflag.value=false;
      }else if(indexvalue=='third'){
        liveness.activeEnterprise.amount = response.rs[0].NOWMONTH
        liveness.activeEnterprise.proportion = response.rs[0].rate3
        liveness.keyEnterprise.molecule =  response.rs[0].NOWMONTH1
        liveness.keyEnterprise.denominator =  response.rs[0].NOWMONTH11
        liveness.keyEnterprise.proportion = response.rs[0].rate33
      }else if(indexvalue=='fourth'){
        liveness.activeEnterprise.amount = response.rs[0].NOWYEAR
        liveness.activeEnterprise.proportion = response.rs[0].rate4
        liveness.keyEnterprise.molecule =  response.rs[0].NOWYEAR1
        liveness.keyEnterprise.denominator =  response.rs[0].NOWYEAR11
        liveness.keyEnterprise.proportion = response.rs[0].rate44
      }

    })
    .catch((response) => {})
}
//数据同步量
const initCustomData = (type) => {
  let params={
    }
  if(type=='1'){
    params={
      qyFlag:'0',
      keyFlag:'0'
    }
  }else if(type=='2'){
    params={
      qyFlag:'0',
      keyFlag:'1'
    }
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
    })
    .catch((response) => {})
}
  //总体进出口情况
   const initTotalCustomData = (type) => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);

    listQueryTotalCustomData.applyDate[0]=parseDateWithoutDay(start)
    listQueryTotalCustomData.applyDate[1]=parseDateWithoutDay(end)
    getTotalCustomData(type);
}
const initCustomDataDash = (type,tabtype) => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);

    listQueryReport.applyDate[0]=parseDateWithoutDay(start)
    listQueryReport.applyDate[1]=parseDateWithoutDay(end)
    getCustomDataDash(type,tabtype);
}
//区域单量统计
const initAreaDataDash = (type,tabtype) => {
    const end = new Date();
    const start = new Date(new Date().getFullYear(), 0);

    listQueryReport2.applyDate[0]=parseDateWithoutDay(start)
    listQueryReport2.applyDate[1]=parseDateWithoutDay(end)
    getAreaDataDash(type,tabtype);
}


//总体进出口情况
const getTotalCustomData = (type) => {
  flagTotalCustomDataNumb.value=false;
  flagTotalCustomDataSum.value=false;
  let startDate1=  listQueryTotalCustomData.applyDate?.length>0 ?listQueryTotalCustomData.applyDate[0]:"" ;
  let endDate1=  listQueryTotalCustomData.applyDate?.length>0 ?listQueryTotalCustomData.applyDate[1]:"";
  let params={}
  if(type=='1'){
    params={
      startDate: startDate1,
      endDate: endDate1,
      qyFlag:'0',
      keyFlag:'0'

    }
  }else if(type=='2'){
    params={
      startDate: startDate1,
      endDate: endDate1,
      qyFlag:'0',
      keyFlag:'1'
    }
  }
  store
    .dispatch('companyStatistics/getTotalCustomDataDash',params)
    .then((response) => {
        numbI.value=response.rs[0].NUMBI
        numbE.value=response.rs[0].NUMBE
        rmbI.value=response.rs[0].RMBI
        rmbE.value=response.rs[0].RMBE

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
//报关单量
const getCustomDataDash = (type,tabtype) => {
  let startDate1=  listQueryReport.applyDate?.length>0 ?listQueryReport.applyDate[0]:"" ;
  let endDate1=  listQueryReport.applyDate?.length>0 ?listQueryReport.applyDate[1]:"";
  let params={}
  if(type=='1'){
    params={
      startDate: startDate1,
      endDate: endDate1,
      qyFlag:'0',
      keyFlag:'0',
      ieFlag:'I'

    }
  }else if(type=='2'){
    params={
      startDate: startDate1,
      endDate: endDate1,
      qyFlag:'0',
      keyFlag:'1' ,
      ieFlag:'I'
    }
  }
  store
    .dispatch('manageStatistics/getCustomDataDash',params)
    .then((response) => {
        listThree.value=[]
        listFour.value=[]
        listFive.value=[]
        let data = response.rs

        let areaArryI=[];
        let areaValueArryI1=[];
        let areaValueArryI2=[];
        let areaValueArryI3=[];
        let areaValueArryI4=[];
        let areaValueArryI5=[];
        let areaValueArryI6=[];
        let colorArryI1=['rgba(31,223,55,0)','#26C6DA'];
        let colorArryI2=['rgba(49,171,227,0)','#1E88E5'];
        let colorArryI3=['rgba(31,223,55,0)','rgba(31,223,55,1)'];
        let colorArryI4=['rgba(49,171,227,0)','rgba(49,171,227,1)'];
        let colorArryI5=['rgba(31,223,55,0)','#176ee3'];
        let colorArryI6=['rgba(49,171,227,0)','#98e919'];
        for (let i = 0; i < data.length; i++) {
          areaArryI.push(data[i].MON);
          areaValueArryI1.push(data[i].NUMBI);
          areaValueArryI2.push(data[i].NUMBE);
          areaValueArryI3.push(data[i].RMBI);
          areaValueArryI4.push(data[i].RMBE);
          areaValueArryI5.push(data[i].GROSSWETI);
          areaValueArryI6.push(data[i].GROSSWETE);
        }
        let dataI1={mon:areaArryI,data:areaValueArryI1,title:'进口',colors:colorArryI1};
        let dataI2={mon:areaArryI,data:areaValueArryI2,title:'出口',colors:colorArryI2};
        listThree.value.push(dataI1)
        listThree.value.push(dataI2)

        let dataI3={mon:areaArryI,data:areaValueArryI3,title:'进口',colors:colorArryI3};
        let dataI4={mon:areaArryI,data:areaValueArryI4,title:'出口',colors:colorArryI4};
        listFour.value.push(dataI3)
        listFour.value.push(dataI4)

        let dataI5={mon:areaArryI,data:areaValueArryI5,title:'进口',colors:colorArryI5};
        let dataI6={mon:areaArryI,data:areaValueArryI6,title:'出口',colors:colorArryI6};
        listFive.value.push(dataI5)
        listFive.value.push(dataI6)


        if(tabtype=='first'){
          flagThree.value = true
        }else if(tabtype=='second'){
          flagFour.value = true
        }else if(tabtype=='third'){
          flagFive.value = true
        }



    })
    .catch((response) => {})
}
//区域统计
const getAreaDataDash = (type,tabtype) => {
  let startDate1=  listQueryReport2.applyDate?.length>0 ?listQueryReport2.applyDate[0]:"" ;
  let endDate1=  listQueryReport2.applyDate?.length>0 ?listQueryReport2.applyDate[1]:"";
  let params={}
  if(type=='1'){
    params={
      startDate: startDate1,
      endDate: endDate1,
      qyFlag:'0',
      keyFlag:'0',
      ieFlag:'I'

    }
  }else if(type=='2'){
    params={
      startDate: startDate1,
      endDate: endDate1,
      qyFlag:'0',
      keyFlag:'1' ,
      ieFlag:'I'
    }
  }
  store
    .dispatch('manageStatistics/getAreaDataDash',params)
    .then((response) => {
        listSix.value=[]
        listSeven.value=[]
        listEight.value=[]
        let data = response.rs

        let areaArryI=[];
        let areaValueArryI1=[];
        let areaValueArryI2=[];
        let areaValueArryI3=[];
        let areaValueArryI4=[];
        let areaValueArryI5=[];
        let areaValueArryI6=[];
        let colorArryI1=['rgba(31,223,55,0.6)','#26C6DA'];
        let colorArryI2=['rgba(49,171,227,0.6)','#1E88E5'];
        let colorArryI3=['rgba(49,171,227,0.6)','rgba(49,171,227,1)'];
        let colorArryI4=['rgba(31,223,55,0.6)','rgba(31,223,55,1)'];
        let colorArryI5=['rgba(96, 197, 255, 1)','rgba(66, 138, 245, 1)'];
        let colorArryI6=['rgba(128, 231, 235, 1)','rgba(26, 217, 202, 1)'];

      flagSix.value=false;
      flagSeven.value=false;
      flagEight.value=false;

        if(data.length>0){

          for (let i = 0; i < data.length; i++) {
            areaArryI.push(data[i].AREA_NAME);
            areaValueArryI1.push(data[i].NUMBI);
            areaValueArryI2.push(data[i].NUMBE);
            areaValueArryI3.push(data[i].RMBI);
            areaValueArryI4.push(data[i].RMBE);
            areaValueArryI5.push(data[i].GROSSWETI);
            areaValueArryI6.push(data[i].GROSSWETE);
          }
          let dataI1={areaName:areaArryI,data:areaValueArryI1,title:'进口',colors:colorArryI1};
          let dataI2={areaName:areaArryI,data:areaValueArryI2,title:'出口',colors:colorArryI2};
          listSix.value.push(dataI1)
          listSix.value.push(dataI2)

          let dataI3={areaName:areaArryI,data:areaValueArryI3,title:'进口',colors:colorArryI3};
          let dataI4={areaName:areaArryI,data:areaValueArryI4,title:'出口',colors:colorArryI4};
          listSeven.value.push(dataI3)
          listSeven.value.push(dataI4)

          let dataI5={areaName:areaArryI,data:areaValueArryI5,title:'进口',colors:colorArryI5};
          let dataI6={areaName:areaArryI,data:areaValueArryI6,title:'出口',colors:colorArryI6};
          listEight.value.push(dataI5)
          listEight.value.push(dataI6)


          if(tabtype=='first'){
            flagSix.value = true
          }else if(tabtype=='second'){
            flagSeven.value = true
          }else if(tabtype=='third'){
            flagEight.value = true
          }
        }



    })
    .catch((response) => {})
}
onMounted(() => {
  initDashHead('first')
  initCustomData('1');
  initTotalCustomData('1');
  initCustomDataDash('1','first');
  initAreaDataDash('1','first');
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
