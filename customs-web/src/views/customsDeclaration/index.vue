<template>
  <div class="roles-container scroll-y p">
        <!--工具栏-->
        <div class="head-container m-1 mr-3 rowSS">
            <!-- 搜索 -->
          <el-form :inline="true" :model="listQuery" class="demo-form-inline">
          <el-form-item label="申报日期">
          <el-date-picker
            v-model="listQuery.declarationData"
            type="daterange"
            range-separator="到"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="handleFilter"
          >
          </el-date-picker>
          </el-form-item>

          
          <el-form-item label="进出口类型">
            <el-select v-model="listQuery.ieFlag" placeholder="进出口类型" clearable
                       @change="handleFilter">
              <el-option
                v-for="item in statuses"
                :key="item.value"
                :label="item.name"
                :value="item.value"
              >
              </el-option>
            </el-select>
            </el-form-item>
              <el-form-item label="报关单号">
                <el-input
                    v-model="listQuery.entryId"
                    placeholder="报关单号"
                    class="filter-item"
                    @keyup.enter.native="handleFilter" clearable
                />
              </el-form-item>
              <el-form-item>
              <span class="filter-item">
                <el-button type="success" :icon="Search" @click="handleFilter">查询</el-button>
                <el-button type="primary" :icon="Download" @click="exportExcel('T')">导出</el-button>
                 <el-button type="primary" :icon="Download" @click="exportExcel('F')">导出(不合并表头)</el-button>
                <el-button type="primary" :icon="Printer" @click="handlePrint">打印</el-button>
              </span>
              </el-form-item>
            </el-form>
        </div>
      <el-card class="m-1">
        <template #header>
          <div class="clearfix">
            <span>报关单列表</span>
          </div>
          

        </template>
        <el-table :data="tableData" v-loading="loading"  row-key="id" lazy  fit stripe style="width: 100%"  @selection-change="handleSelectionChange">
          <el-table-column type="selection" align="center" width="50" />
          <el-table-column type="index" label="序号" align="center" width="55">
            <template #default="scope">
              <span>{{ (listQuery.page - 1) * listQuery.limit + scope.$index + 1 }}</span>
            </template>
          </el-table-column>
         <el-table-column v-if="false" prop="id" label="idd" ></el-table-column>

          <el-table-column prop="entryid" label="报关单号" align="center">
            <template  #default="scope">
                
                 <a class="text_self_blue" href="javascript:;" @click="goDetail(scope.row.id)">{{scope.row.entryid}}</a>
                
             </template>
          </el-table-column>
          <el-table-column prop="tradename" label="收发货人" align="center">
          </el-table-column>
           <el-table-column prop="manualno" label="备案号" align="center">
          </el-table-column>
          <el-table-column prop="billno" label="提运单号" align="center">
          </el-table-column>
           <el-table-column prop="iedate" label="进出口日期" align="center" >
          </el-table-column>
          <el-table-column prop="declarationdata" label="申报日期" align="center">
          </el-table-column>
           <el-table-column prop="trafname" label="运输工具" align="center">
          </el-table-column>
          <el-table-column prop="agentname" label="申报单位" align="center">
          </el-table-column>
          <el-table-column prop="ieflag" label="进出口标志" align="center" :formatter="formatIeFlag">
          </el-table-column>
          <el-table-column prop="custommasterValue" label="申报地口岸" align="center">
          </el-table-column>
          <el-table-column prop="customstatename" label="回执状态" align="center">
             <template  #default="scope">
              
                 <a class="text_self_blue" href="javascript:;" @click="showState(scope.row.id)">{{scope.row.customstatename}}</a>
                
             </template>

          </el-table-column>
          <el-table-column prop="createTime" label="同步时间" align="center">
            <template #default="scope">
              {{ parseTime(scope.row.createTime) }}
            </template>
          </el-table-column>
         
        </el-table>
        <div class="block columnCC mt-2">
          <el-pagination
              v-show="total > 0"
              v-model:currentPage="listQuery.page"
              v-model:page-size="listQuery.limit"
              :page-sizes="[10, 25, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="handleSizeChange"
              @current-change="getRoles"
          >
          </el-pagination>
        </div>
      </el-card>

        <DialogState ref="stateRecordRef" 
       :dialog-roles="dialogData"
       :head-id="currentId"
        />


         <DialogPrint ref="dialogPrintRef" 
       :dialog-good="dialogPrintData"
       :form-data="formPrint"
        />


       <!-- <Detail ref="detailRef" 
       :dialog-more="dialogDetailData"
       
        /> -->


        





  </div>
</template>

<script setup lang="ts">
import {Plus} from '@element-plus/icons-vue'
import {Search , Download ,Printer} from '@element-plus/icons-vue'
import {dialogTy} from '~/dialog'
import DialogState from './dialogState.vue'
import {Ref} from 'vue'
import {ElMessage} from 'element-plus'

import Detail from './detail.vue'

const dialogDetailData: Ref<dialogTy> = ref({})

// import { ElMessage } from "element-plus/es";
import {parseTime, deepClone} from '@/utils/dateTime'

const store = useStore()

import DialogPrint from './component/dialogPrint.vue'
let formPrint = ref({})
const dialogPrintData: Ref<dialogTy> = ref({})
const dialogPrintRef: any = ref(null)

// table显示角色数组
const currentId = ref<string>('')
const tableData: any = ref(null)
const loading = false
// 角色总数
const total = ref(null)
// 查询配置 [pageNum 当前页数, pageSize 每页大小]
const listQuery: any = ref({
  page: 1,
  limit: 10
})

import {getCurrentInstance} from 'vue';

const instance = getCurrentInstance();
onMounted(() => {
  console.log("********************************************9")
  let customs = sessionStorage.getItem('customs');
   console.log(789)
      console.log(customs)
  if(customs){
      var data = JSON.parse(customs)
       listQuery.value =  data
      instance.ctx.$forceUpdate();
  }
  // nextTick(() => {
    getRoles()
  // })
  
})


onUnmounted(() => {
  console.log(router.currentRoute.value.path)
      if (router.currentRoute.value.path == "/customsDeclarationDetail") {
          //不清除
      } else {
        sessionStorage.removeItem('customs')
      }
    })



let multipleSelection = ref([])
const handleSelectionChange = (val: any) => {
  multipleSelection.value = val
}



/**
 * 新增
 */
const dialogData: Ref<dialogTy> = ref({})//ts的规范写法，定义变量的类型
// let formData = ref({})//这是相对自由点的写法，不定义类型

// 审核状态
const statuses = [
  {name: '请选择', value: ''},
  {name: '进口', value: 'I'},
  {name: '出口', value: 'E'},

]

const formatIeFlag= (row) => {
  switch (row.ieflag) {
    case "I":
      return "进口";
    case "E":
      return "出口";
    
  }
}




import { useRouter } from 'vue-router';
const router = useRouter()

 const detailRef = ref<InstanceType<typeof Detail>>()



const goDetail = (id) =>{
  //todo

sessionStorage.setItem('customs', JSON.stringify(listQuery._rawValue));
  // console.log(id)

  let routeData
    // const routeData

      const _path =  '/customsDeclarationDetail'
      const pramSelf = { headId: id };
       routeData = router.resolve({ //使用resolve
      //  mode: 'history',
      name: '报关单详情',
      path: _path,
      query: pramSelf,
      })
      // window.open(routeData.href, '_self')
      window.open(routeData.href, '_parent')
     

  //   dialogDetailData.value = {
  //   show: true,
  //   title: '报关单详情',
    
  // }
  // detailRef.value.getData(id);
}
const stateRecordRef = ref<InstanceType<typeof DialogState>>()
const showState = (id) =>{
  console.log(id)
  currentId.value = id
  dialogData.value = {
    show: true,
    title: '回执状态列表',
    option: 'add'
  }

  stateRecordRef.value?.getList()

}


const permissionsDialog: Ref<dialogTy> = ref({})//ts的规范写法，定义变量的类型

// 获取角色列表
const getRoles = () => {
   console.log(8888)
  console.log(listQuery._rawValue)
  let params = Object.assign(deepClone(listQuery._rawValue),
           {
             startTime: parseTime(listQuery._rawValue.declarationData?.length > 0 ? listQuery._rawValue.declarationData[0] : ""),
             endTime: parseTime(listQuery._rawValue.declarationData?.length > 1 ? listQuery._rawValue.declarationData[1] : "")
             }
             )

  params.declarationData = '';
  store
      .dispatch('qpDec/getHeadList', params)
      .then((response) => {
        // console.log(1)
        tableData.value = response.items
        // console.log(2)
        total.value = response.total
        // console.log(3)
      })
      .catch((response) => {
        console.log(response)
      })
}

import {exportHead } from "@/api/qpDec";
// const { elMessage } = useElement()

import qs from 'qs';



const handlePrint = () => {
  let rowDeleteIdArr: Array<any> = []
  rowDeleteIdArr = multipleSelection.value.map((mItem: any) => {
    return mItem.id
  })
 
  if(rowDeleteIdArr && rowDeleteIdArr.length != 1){
      // alert("请选择一天记录打印!")
       ElMessage({
            message: '请选择一条记录打印!',
            type: 'error',
            showClose: true,
            offset: 50
          });
      return;
  } 
  // params  = qs.stringify({ids:rowDeleteIdArr} , { arrayFormat: 'indices',allowDots: true })
  
    formPrint.value = rowDeleteIdArr[0];
    console.log(formPrint)
    dialogPrintData.value = {
    show: true,
    title: '选择打印类型',
    
  }


  //todo 
  let params = Object.assign(deepClone(listQuery._rawValue),
           {
             startTime: parseTime(listQuery._rawValue.declarationData?.length > 0 ? listQuery._rawValue.declarationData[0] : ""),
             endTime: parseTime(listQuery._rawValue.declarationData?.length > 1 ? listQuery._rawValue.declarationData[1] : "")
             }
      )

  // }

 
}
const getDateTime = () => {
   var dateObj = new Date(); //表示当前系统时间的Date对象
            var year = dateObj.getFullYear(); //当前系统时间的完整年份值
            var month = dateObj.getMonth()+1; //当前系统时间的月份值
            var date = dateObj.getDate(); //当前系统时间的月份中的日
            var day = dateObj.getDay(); //当前系统时间中的星期值
            return '' + year + dateFilter(month) + dateFilter(date)
}

const dateFilter = (date) => {
 if(date < 10){return "0"+date;} 
    return date;
}

 

         


const exportExcel = (_isMerge) => {
  
  let rowDeleteIdArr: Array<any> = []
  rowDeleteIdArr = multipleSelection.value.map((mItem: any) => {
    return mItem.id
  })
  let params ;
  // if(rowDeleteIdArr && rowDeleteIdArr.length > 0){
  //     params  = qs.stringify({ids:rowDeleteIdArr} , { arrayFormat: 'indices',allowDots: true })
  // }else{
    if(!listQuery._rawValue.ieFlag){
    // ElMessage({ message: '请选择进出口类型', type: 'error' })
    ElMessage({
            message: '请选择进出口类型',
            type: 'error',
            showClose: true,
            offset: 50
          });
    // alert("请选择进出口类型")
    //  elMessage.error("请选择进出口类型")
    return;
  }
  //todo 
   params = Object.assign(deepClone(listQuery._rawValue),
           {
             startTime: parseTime(listQuery._rawValue.declarationData?.length > 0 ? listQuery._rawValue.declarationData[0] : ""),
             endTime: parseTime(listQuery._rawValue.declarationData?.length > 1 ? listQuery._rawValue.declarationData[1] : ""),
             isMerge :_isMerge
             }
      )

  // }

  // exportHead({ieFlag:"I"}).then(res => {
    exportHead(params).then(res => {
    let blob = new Blob([res.data], {type: 'application/octet-stream'});
    let url = URL.createObjectURL(blob);
    const link = document.createElement('a'); //创建a标签
    link.href = url;
    link.download = '报关单'+ getDateTime() +'.xlsx'; //重命名文件
    link.click();
    URL.revokeObjectURL(url);
  })
}


/**
 * 分页大小修改处理
 */
const handleSizeChange = () => {
  listQuery.page = 1
  getRoles()
}
/**
 * 查询框处理
 */
const handleFilter = () => {
  listQuery.page = 1
  getRoles()
}
</script>


<style scoped lang="scss">
.text_self_blue {
  color: var(--el-color-primary);
}

</style>


