<template>
  <div>
    <div class="digitalDiv">
      <div class="digitalSingle">
        <div class="imgDiv">
          <img src="../../../img/test1.png" alt="" style="width:42px;height:42px;">
        </div>
        <div class="fontDiv" style="width: 80%;">
          <div><span>进口单量</span></div>
          <div style="margin-top: 7px;">
            <span class="number">{{ numbI }}</span>
            <span class="unit">条</span>
          </div>
        </div>
      </div>
      <div class="digitalSingle">
        <div class="imgDiv">
          <img src="../../../img/test1.png" alt="" style="width:42px;height:42px;">
        </div>
        <div class="fontDiv" style="width: 80%;">
          <div><span>出口单量</span></div>
          <div style="margin-top: 7px;">
            <span class="number">{{ numbE }}</span>
            <span class="unit">条</span>
          </div>
        </div>
      </div>
      <div class="digitalSingle">
        <div class="imgDiv">
          <img src="../../../img/test.png" alt="" style="width:42px;height:42px;">
        </div>
        <div class="fontDiv" style="width: 80%;">
          <div><span>人民币进口货值</span></div>
          <div style="margin-top: 7px;">
            <span class="number">{{ rmbI }}</span>
            <span class="unit">元</span>
          </div>
        </div>
      </div>
      <div class="digitalSingle">
        <div class="imgDiv">
          <img src="../../../img/test.png" alt="" style="width:42px;height:42px;">
        </div>
        <div class="fontDiv" style="width: 80%;">
          <div><span>美元进口货值</span></div>
          <div style="margin-top: 7px;">
            <span class="number">{{ usdI }}</span>
            <span class="unit">美元</span>
          </div>
        </div>
      </div>
      <div class="digitalSingle">
        <div class="imgDiv">
          <img src="../../../img/test.png" alt="" style="width:42px;height:42px;">
        </div>
        <div class="fontDiv" style="width: 80%;">
          <div><span>人民币出口货值</span></div>
          <div style="margin-top: 7px;">
            <span class="number">{{ usdE }}</span>
            <span class="unit">元</span>
          </div>
        </div>
      </div>
      <div class="digitalSingle">
        <div class="imgDiv">
          <img src="../../../img/test.png" alt="" style="width:42px;height:42px;">
        </div>
        <div class="fontDiv" style="width: 80%;">
          <div><span>美元出口货值</span></div>
          <div style="margin-top: 7px;">
            <span class="number">{{ numbI }}</span>
            <span class="unit">美元</span>
          </div>
        </div>
      </div>
    </div>

    <div class="workflow-container">
      <el-card shadow="hover" :body-style="{ padding: '20px 20px 5px  20px ' }">
        <div class="export-search mb-1">
          <el-form :inline="true" :model="listQuery" class="demo-form-inline">
            <el-form-item label="统计日期">
              <div class="block">
                <span class="demonstration"></span>
                <el-date-picker
                  v-model="listQuery.startDate"
                  type="month"
                  placeholder="统计起始日期">
                </el-date-picker>
              </div>
              <div class="block">
                <span class="demonstration">~</span>
                <el-date-picker
                  v-model="listQuery.endDate"
                  type="month"
                  placeholder="统计起始日期">
                </el-date-picker>
              </div>
            </el-form-item>
            <el-form-item>
                    <span class="filter-item">
                        <el-button type="success" :icon="Search" @click="onSearch">查询</el-button>
                    </span>
            </el-form-item>
          </el-form>
        </div>

        <div class="workflow-table mb-1">
          <el-table :data="tableData" style="width: 100%" :height="`calc(100vh - ${windowSpanceHeight})`" fit border
                    highlight-current-row>
            <el-table-column type="index" label="序号" align="center" width="55">
              <template #default="scope">
                <span>{{ (listQuery.page - 1) * listQuery.limit + scope.$index + 1 }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="ORGID" label="企业编号" align="center">
              <template #default="scope">
                <div>
                  <a href="javascript:;" @click="getCompanyDataDetail(scope.row)">{{ scope.row.ORGID }}</a>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="ORG_NAME" label="企业名称" align="center"/>
            <el-table-column prop="NUMBI" label="累计进口单量" align="center"/>
            <el-table-column prop="NUMBE" label="累计进口单量" align="center"/>
            <el-table-column prop="CUSTOMTOTALRMBI" label="累计进口人民币货值" align="center"/>
            <el-table-column prop="CUSTOMTOTALUSDI" label="累计进口美元货值" align="center"/>
            <el-table-column prop="CUSTOMTOTALRMBE" label="累计出口人民币货值" align="center"/>
            <el-table-column prop="CUSTOMTOTALUSDE" label="累计出口美元货值" align="center"/>
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
      </el-card>
      <div>
      </div>
    </div>

    <el-dialog v-model="tablemodel" :dataTmp="dataTmp" :dataname="dataname" @open="openFun"
               width="75%"
               align-center>

      <div id="main3" style="width: 100%;height:400px;"></div>

    </el-dialog>
  </div>

</template>

<script lang="ts" setup>
import {Search} from '@element-plus/icons-vue'
import {ElMessageBox, ElMessage} from 'element-plus'
import {parseDateWithoutDayNew, parseDateWithoutDay} from '@/utils/dateTime'
import * as echarts from 'echarts'

const store = useStore()
const dialogVisible = ref(false)
const radio = ref(1)
const numbI = ref(null)
const numbE = ref(null)
const rmbI = ref(null)
const usdI = ref(null)
const rmbE = ref(null)
const usdE = ref(null)
const tablemodel = ref(false)

// table显示角色数组
const tableData: any = ref(null)
const loading = false
// 角色总数
const total = ref(null)
// 查询配置 [pageNum 当前页数, pageSize 每页大小]

const route = useRoute()
let id = ref(null)
onMounted(() => {

  initPick();

})
const initPick = () => {
  const end = new Date();
  const start = new Date(new Date().getFullYear(), 0);
  listQuery.startDate = start
  listQuery.endDate = end
  //异步，加入延迟
  setTimeout(function () {
    try {
      handleCurrentChange()
    } catch (e) {
    }

  }, 500)
}

const dataname = ref(null)
const dataTmp = ref(null)
const rowData = ref(null)
const getCompanyDataDetail = (row) => {
  rowData.value = row
  tablemodel.value = true;

}
let canvasI: any = null//document.getElementById('canvas')
const openFun = () => {

  nextTick(() => {
    let date1 = parseDateWithoutDay(listQuery.startDate);
    let date2 = parseDateWithoutDay(listQuery.endDate);

    let params = {
      orgId: rowData.value.ORGID,
      orgName: rowData.value.ORG_NAME,
      startDate: date1,
      endDate: date2,
    }
    console.log(1111, params)
    tablemodel.value = true;
    store
      .dispatch('manageStatistics/getKeyCompanyDataByCode', params)
      .then((response) => {
        tablemodel.value = true;
        dataTmp.value = response.rs
        dataname.value = rowData.value.ORG_NAME
        let qrBox = document.getElementById('main3')    // 获取到div元素
        const _data = dataTmp.value
        const gname = dataname.value
        date1 = parseDateWithoutDayNew(listQuery.startDate);
        date2 = parseDateWithoutDayNew(listQuery.endDate);
        // 指定图表的配置项和数据
        let monthArry = [];
        let monthValueArry1 = [];
        let monthValueArry2 = [];
        let monthValueArry3 = [];
        let monthValueArry4 = [];
        for (let i = 0; i < _data.length; i++) {
          monthArry.push(_data[i].MON);
          monthValueArry1.push(_data[i].NUMBI);
          monthValueArry2.push(_data[i].NUMBE);
          monthValueArry3.push(_data[i].CUSTOMTOTALRMBI);
          monthValueArry4.push(_data[i].CUSTOMTOTALRMBE);
        }
        let data = {
          month: monthArry,
          val1: monthValueArry1,
          val2: monthValueArry2,
          val3: monthValueArry3,
          val4: monthValueArry4
        };
        let totalType = ''
        let mainType = ''

        const option = {
          title: {
            text: gname + '       ' + date1 + '~' + date2 + totalType + '进出口单量货值统计'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['进口单量', '出口单量', '进口人民币货值', '出口人民币货值'],
            top: '6%'
          },
          toolbox: {
            show: true,
            feature: {
              dataView: {show: true, readOnly: false},
              magicType: {show: true, type: ['line', 'bar']},
              restore: {show: false},
              saveAsImage: {show: true}
            }
          },
          calculable: true,
          xAxis: {
            data: data.month,
            triggerEvent: false,
            axisLabel: {
              interval: 0,
              margin: 20,
              rotate: -15
            }
          },
          yAxis: {
            axisLabel: {
              interval: 0,
              rotate: 0
            }
          },
          series: [
            {
              name: '进口单量',
              type: 'bar',
              data: data.val1,
              barGap: '20%',
              barCategoryGap: '30%',
              barMaxWidth: '25',
            },
            {
              name: '出口单量',
              type: 'bar',
              data: data.val2,
              barGap: '20%',
              barCategoryGap: '30%',
              barMaxWidth: '25',
            },
            {
              name: '进口人民币货值',
              type: 'bar',
              data: data.val3,
              barGap: '20%',
              barCategoryGap: '30%',
              barMaxWidth: '25',
            },
            {
              name: '出口人民币货值',
              type: 'bar',
              data: data.val4,
              barGap: '20%',
              barCategoryGap: '30%',
              barMaxWidth: '25',
            },
          ],
        };
        canvasI = document.getElementById('main3')

        let myChartI = echarts.init(canvasI)
// 绘制图表
        myChartI.clear();

        myChartI.setOption(option);

      })

  })
    .catch((response) => {
    })


}

const windowSpanceHeight = ref('300px') //这个变量用来控制页面表格的大小，来让分页和查询都一直可见


/**按钮部分 -end */

/**查询部分 */
const listQuery = reactive({
  startDate: '',
  endDate: '',
  page: 1,
  limit: 10
})

const onSearch = () => {
  if (listQuery.startDate === null || listQuery.startDate === '' || listQuery.endDate === null || listQuery.endDate === '') {
    ElMessage({message: '请填写统计起始日期', type: 'error'})
    return;
  }
  let startDate1 = parseDateWithoutDay(listQuery.startDate);
  let endDate1 = parseDateWithoutDay(listQuery.endDate);
  if (startDate1.substr(0, startDate1.length - 3) != endDate1.substr(0, startDate1.length - 3)) {
    ElMessage({message: '统计起始日期应为同一年份', type: 'error'})
    return;
  }
  handleSizeChange()
}
/**查询部分 end*/

// 获取备用金借款申请列表


/*以下是分页部分*/
const page = ref(1)
const limit = ref(20)
const background = ref(false)
const disabled = ref(false)
const pageSizeOption = ref([10, 25, 50, 100])
const pageLayout = ref('total, sizes, prev, pager, next, jumper') //设置分页展示的元素
const pageTotal = ref(0)


const handleSizeChange = () => {
  handleCurrentChange();
}
const handleCurrentChange = () => {
  let params = {
    startDate: parseDateWithoutDay(listQuery.startDate),
    endDate: parseDateWithoutDay(listQuery.endDate),
    page: listQuery.page,
    limit: listQuery.limit,
  }
  console.log('aaa', params)
  store
    .dispatch('manageStatistics/getKeyCompanyData', params)
    .then((response) => {
      console.log('eee', response)
      tableData.value = response.items
      pageTotal.value = response.total

      handleCurrentChangeDataSum()
    })
    .catch((response) => {
    })
}
const handleCurrentChangeDataSum = () => {
  let params = {
    startDate: parseDateWithoutDay(listQuery.startDate),
    endDate: parseDateWithoutDay(listQuery.endDate),
    page: listQuery.page,
    limit: listQuery.limit,
  }
  console.log('1111', params)
  store
    .dispatch('manageStatistics/getKeyCompanyDataSum', params)
    .then((response) => {
      console.log('eee111', response)
      numbI.value = response.rs[0].NUMBISUM
      numbE.value = response.rs[0].NUMBESUM
      rmbI.value = response.rs[0].CUSTOMTOTALRMBISUM
      numbE.value = response.rs[0].CUSTOMTOTALRMBESUM
      usdI.value = response.rs[0].CUSTOMTOTALUSDISUM
      usdE.value = response.rs[0].CUSTOMTOTALUSDESUM
    })
    .catch((response) => {
    })
}
/*分页结束*/

/*tab页面 结束 */
</script>

<style scoped lang="scss">
.mb-1 {
  margin-top: -10px !important;
}

.mt-2 {
  margin-top: 0px !important;
}

.sign-wraps {
  // border: 1px dashed #dcdfe6;
  width: 150px;
  text-align: center;

  .sign-img {
    width: 90px;
  }
}

.digitalDiv {
  background-color: rgb(223, 220, 220);
  margin: 5px 5px;
  height: 90px;

  .digitalSingle {
    width: 15.3%;
    height: 70px;
    border: 1px solid #eee;
    background-color: white;
    border-radius: 5px;
    box-shadow: 0 5px 5px #888888;
    margin-left: 20px;
    float: left;
    margin-top: 10px;
    padding-top: 10px;

    .imgDiv {
      float: left;
      margin-left: 20px;
      height: 100%;
      line-height: 70px;
      width: 28%;
    }

    .fontDiv {
      .number {
        color: cornflowerblue;
        font-size: 18px;
        font-weight: bold;
      }

      .unit {
        color: grey;
        font-size: 16px;
        margin-left: 3px;
      }
    }
  }


}
</style>
