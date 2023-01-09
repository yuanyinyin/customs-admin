<template>
  <div>
    <div class="scroll-y">
      <div class="dashboard-container">

        <el-row :gutter="10">
          <el-col :span="24">
            <el-card class="card-item" :body-style="{padding:'5px 15px 15px',height:'100%'}">
              <template #header>
                <div class="inout-header">
                  <div class="common-title">
                    <h3>平台活跃度</h3>
                    <el-tabs v-model="activeMonth" class="nav-tabs" @tab-change="switchMonth">
                      <el-tab-pane label="今日" name="first"></el-tab-pane>
                      <el-tab-pane label="近7天" name="second"></el-tab-pane>
                      <el-tab-pane label="近30天" name="third"></el-tab-pane>
                      <el-tab-pane label="近半年" name="fourth"></el-tab-pane>
                      <el-tab-pane label="本年" name="fifth"></el-tab-pane>
                    </el-tabs>
                  </div>
                </div>
              </template>
              <el-row :gutter="15">
                <el-col :md="24" :lg="12">
                  <div class="data-item">
                    <div class="tit">活跃企业数</div>
                    <div class="stati"> <span class="number blue">{{ liveness.activeEnterprise.amount || 0 }}</span>
                      <span class="number blue"> /</span><span
                      class="unit">{{ liveness.activeEnterprise.proportion || 0 }} </span>
                    </div>
                    <img class="data-img" src="@/assets/main/icons/icon8.png" alt="">
                  </div>
                </el-col>
                <el-col :md="24" :lg="12">
                  <div class="data-item">
                    <div class="tit">重点企业数</div>
                    <div class="stati"> <span class="number red"> {{ liveness.keyEnterprise.molecule || 0 }}</span>
                      <span class="number red"> /</span><span
                      class="denominator">  {{ liveness.keyEnterprise.proportion || 0 }}</span>
                    </div>
                    <img class="data-img" src="@/assets/main/icons/icon9.png" alt="">
                  </div>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
        </el-row>


        <div class="workflow-container">
          <el-card shadow="hover" :body-style="{ padding: '20px 20px 5px  20px ' }">
            <div class="export-search mb-1">
              <el-form :inline="true" :model="listQuery" class="demo-form-inline">
                <el-form-item label="企业名称">
                  <el-input v-model="listQuery.companyName" placeholder="企业名称" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <span class="filter-item">
                        <el-button type="success" :icon="Search" @click="onSearch">查询</el-button>
                    </span>
                </el-form-item>
              </el-form>
            </div>
            <el-tabs v-model="activeName" @tab-change="handleTabChange">
              <el-tab-pane label="活跃用户" name="first">
                <div class="workflow-table mb-1">
                  <el-table :data="tableData" style="width: 100%" :height="`calc(100vh - ${windowSpanceHeight})`" fit border
                            highlight-current-row  :default-sort="{ prop: 'companyInfo[0].UPDATE_TIME', order: 'descending' }">
                    <el-table-column type="index" label="序号" align="center" width="55">
                      <template #default="scope">
                        <span>{{ (listQuery.page - 1) * listQuery.limit + scope.$index + 1 }}</span>
                      </template>
                    </el-table-column>
                    <el-table-column prop="DEPT_ID" label="企业编号" align="center">
                      <template #default="scope">
                        <div>
                          <a href="javascript:;" @click="getCompanyDataDetail(scope.row)">{{ scope.row.DEPT_ID }}</a>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column prop="ORG_NAME_CN" label="企业名称" align="center"/>
                    <el-table-column prop="companyInfo[0].UPDATE_TIME" label="最近上线时间" align="center" sortable>

                    </el-table-column>
                    <el-table-column   prop="companyInfo[0].TODATE" label="距离上次操作时间（天）" align="center"/>
                  </el-table>
                  <div class="pagination-wrap block columnCC mt-2">
                    <el-pagination
                      v-model:currentPage="listQuery.page"
                      v-model:page-size="listQuery.limit"
                      :page-sizes="pageSizeOption"
                      :disabled="disabled"
                      :background="background"
                      :layout="pageLayout"
                      :total="pageTotal"
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                    ></el-pagination>
                  </div>
                </div>
              </el-tab-pane>
              <el-tab-pane label="不活跃用户" name="second">
                <div class="workflow-table mb-1">
                  <el-table :data="tableData1" style="width: 100%" :height="`calc(100vh - ${windowSpanceHeight})`" fit border
                            highlight-current-row>
                    <el-table-column type="index" label="序号" align="center" width="55">
                      <template #default="scope">
                        <span>{{ (listQuery.page - 1) * listQuery.limit + scope.$index + 1 }}</span>
                      </template>
                    </el-table-column>
                    <el-table-column prop="DEPT_ID" label="企业编号" align="center">
                      <template #default="scope">
                        <div>
                          <a href="javascript:;" @click="getCompanyDataDetail(scope.row)">{{ scope.row.DEPT_ID }}</a>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column prop="ORG_NAME_CN" label="企业名称" align="center"/>
                    <el-table-column prop="modify_time" label="最近上线时间" align="center" sortable>
                      <template #default="scope">
                        {{ parseTime(scope.row.companyInfo[0].UPDATE_TIME) }}
                      </template>
                    </el-table-column>
                    <el-table-column   prop="companyInfo[0].TODATE" label="距离上次操作时间（天）" align="center"/>
                  </el-table>
                  <div class="pagination-wrap block columnCC mt-2">
                    <el-pagination
                      v-model:currentPage="listQuery.page"
                      v-model:page-size="listQuery.limit"
                      :page-sizes="pageSizeOption1"
                      :disabled="disabled"
                      :background="background"
                      :layout="pageLayout"
                      :total="pageTotal1"
                      @size-change="handleSizeChange1"
                      @current-change="handleCurrentChange1"
                    ></el-pagination>
                  </div>
                </div>
              </el-tab-pane>
            </el-tabs>


          </el-card>
          <div>
          </div>
        </div>


      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {tabBarProps, TabsPaneContext} from 'element-plus'
import { parseTime, parseDate } from '@/utils/dateTime'
import {ElMessageBox, ElMessage} from 'element-plus'
import _ from 'lodash'

const store = useStore()


/**查询部分 */
const listQuery = reactive({
  startDate: '',
  endDate: '',
  page: 1,
  limit: 10,
  companyName:''
})


const liveness = reactive({
  activeEnterprise: {
    amount: '',
    proportion: ''
  },
  keyEnterprise: {
    molecule: '',
    proportion: ''
  }
})
const activeMonth = ref('first')
const switchMonth = (tab: TabsPaneContext, event: Event) => {
  initSwitch1(activeMonth.value)
}
const onSearch = () => {
  if (activeName.value == 'first') {
    handleSizeChange()
  } else if (activeName.value == 'second') {
    handleSizeChange1()
  }
}
const activeName = ref('first')
const handleTabChange = () => {
  console.log(111, activeName.value)
  switch (activeName._rawValue) {
    case "first":
      initSwitch2('first')
      break;
    case "second":
      initSwitch2('second')
      break;
  }
}
// function changeSort(val){
//   let sortData=_.cloneDeep(this.tableData)
//   if(val.order==="descending"){
//     if(val.prop==="modify_time"){
//       // console.log(val.prop)
//       sortData.sort(this.sortKeys(val.prop,true))
//       // console.log(sortData)
//     }
//   }else{
//     if(val.prop==="modify_time"){
//       sortData.sort(this.sortKeys(val.prop,false))
//     }
//   }
//   this.tableData=sortData
//   // this.currentPage=1
// }
// // 升序降序
// function sortKeys(key,order){
//   if(order){
//     return (val1,val2)=>{
//       return val2[key] > val1[key] ? 1 : -1
//     }
//   }else{
//     return (val1,val2)=>{
//       return val2[key] < val1[key] ? 1 : -1
//     }
//   }
// }
const initSwitch1 = (indexvalue) => {
  let params = {
  }
  if (indexvalue == 'first') {
    params = {
      searchFlag: '1',
    }
  } else if (indexvalue == 'second') {
    params = {
      searchFlag: '2',
    }
  } else if (indexvalue == 'third') {
    params = {
      searchFlag: '3',
    }
  } else if (indexvalue == 'fourth') {
    params = {
      searchFlag: '4',
    }
  }else if (indexvalue == 'fifth') {
    params = {
      searchFlag: '5',
    }
  }
  store
    .dispatch('manageStatistics/getCompanyRateData', params)
    .then((response) => {

        liveness.activeEnterprise.amount = response.rs[0].USEQY
        liveness.activeEnterprise.proportion = response.rs[0].ALLQY
        liveness.keyEnterprise.molecule = response.rs[0].USEKEYQY
        liveness.keyEnterprise.proportion = response.rs[0].ALLKEYQY
      initSwitch2( activeName.value)
    })
    .catch((response) => {
    })
}
const initSwitch2 = (indexvalue) => {
  let params = {
  }
  let queryFlag=1;
  if (indexvalue == 'first') {
    queryFlag=1
  } else if (indexvalue == 'second') {
    queryFlag=2
  }
  if (activeMonth.value == 'first') {
    params = {
      searchFlag: '1',
      queryFlag:queryFlag,
      companyName: listQuery.companyName,
      page: listQuery.page,
      limit: listQuery.limit,
    }
  } else if (activeMonth.value == 'second') {
    params = {
      searchFlag: '2',
      queryFlag:queryFlag,
      companyName: listQuery.companyName,
      page: listQuery.page,
      limit: listQuery.limit,
    }
  } else if (activeMonth.value == 'third') {
    params = {
      searchFlag: '3',
      queryFlag:queryFlag,
      companyName: listQuery.companyName,
      page: listQuery.page,
      limit: listQuery.limit,
    }
  } else if (activeMonth.value == 'fourth') {
    params = {
      searchFlag: '4',
      queryFlag:queryFlag,
      companyName: listQuery.companyName,
      page: listQuery.page,
      limit: listQuery.limit,
    }
  }else if (activeMonth.value == 'fifth') {
    params = {
      searchFlag: '5',
      queryFlag:queryFlag,
      companyName: listQuery.companyName,
      page: listQuery.page,
      limit: listQuery.limit,
    }
  }
  store
    .dispatch('manageStatistics/getCompanyUseDataList', params)
    .then((response) => {
      console.log('eee', response)
      if (indexvalue == 'first') {
        tableData.value = response.items
        pageTotal.value = response.total
      } else if (indexvalue == 'second') {
        tableData1.value = response.items
        pageTotal1.value = response.total
      }

console.log(tableData.value)
    })
    .catch((response) => {
    })
}
onMounted(() => {
  initSwitch1('first');
  initSwitch2('first');
})
/*以下是分页部分*/
const page = ref(1)
const limit = ref(20)
const background = ref(false)
const disabled = ref(false)
const pageSizeOption = ref([10, 25, 50, 100])
const pageLayout = ref('total, sizes, prev, pager, next, jumper') //设置分页展示的元素
const pageTotal = ref(0)
// table显示角色数组
const tableData: any = ref(null)
const pageTotal1 = ref(0)
// table显示角色数组
const tableData1: any = ref(null)
const tablemodel = ref(false)
const dataname = ref(null)
const dataTmp = ref(null)
const rowData = ref(null)

const handleSizeChange = () => {
  handleCurrentChange()
}
const handleSizeChange1 = () => {
  handleCurrentChange1()
}
const handleCurrentChange = () => {
  initSwitch2('first');
}
const handleCurrentChange1 = () => {
  initSwitch2('second');
}
const getCompanyDataDetail = (row) => {
  rowData.value = row
  tablemodel.value = true;

}
</script>

<style lang="scss" scoped>
@import '@/styles/wm.scss';
</style>

<style scoped>
.card-item >>> .el-card__header {
  padding: 0px !important;
}

.nav-tabs >>> .el-tabs__header {
  margin: 0;
}

.nav-tabs >>> .el-tabs__nav-wrap::after {
  height: 0;
}
.stati .number{
  font-size: 28px;
  font-family: 'SourceHanSansCN-Regular';
  font-weight: bold;
  color: #2C363E;
}

.stati .number.blue{
  color: #03A7FF;
}

.stati .number.red{
  color: #F02D2D;
}

.stati .denominator{
  font-size: 22px;
  margin-top: 10px;
}

.ratio{
  display: flex;
  align-items: center;
}

.ratio span{
  font-size: 12px;
  margin-left: 10px;
}

.ratio span.green{
  color: #0EC270;
}

.ratio span.red{
  color: #F02D2D;
}

</style>
