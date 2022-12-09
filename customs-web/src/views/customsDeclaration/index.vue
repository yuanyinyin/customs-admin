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
            @change="onSearch"
          >
          </el-date-picker>
          </el-form-item>

             
              <el-form-item label="进出口类型">
            <el-select v-model="listQuery.ieFlag" placeholder="进出口类型" clearable
                       @change="onSearch">
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
                <el-button type="primary" :icon="Plus" @click="exportExcel">导出</el-button>
                <el-button type="primary" :icon="Plus" @click="handleFilter">打印</el-button>
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
        <el-table :data="tableData" v-loading="loading"  row-key="id" lazy  fit stripe style="width: 100%">
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
           <el-table-column prop="iedate" label="进出口日期" align="center">
          </el-table-column>
          <el-table-column prop="declarationdata" label="申报日期" align="center">
          </el-table-column>
           <el-table-column prop="trafname" label="运输工具" align="center">
          </el-table-column>
          <el-table-column prop="agentname" label="申报单位" align="center">
          </el-table-column>
          <el-table-column prop="ieflag" label="进出口标志" align="center">
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

  </div>
</template>

<script setup lang="ts">
import {Plus} from '@element-plus/icons-vue'
import {Search} from '@element-plus/icons-vue'
import {dialogTy} from '~/dialog'
import DialogState from './dialogState.vue'
import {Ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {parseTime, deepClone} from '@/utils/dateTime'

const store = useStore()

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
onMounted(() => {
  getRoles()
})

/**
 * 新增
 */
const dialogData: Ref<dialogTy> = ref({})//ts的规范写法，定义变量的类型
// let formData = ref({})//这是相对自由点的写法，不定义类型

// 审核状态
const statuses = [
  {name: '全部', value: ''},
  {name: '进口', value: 'I'},
  {name: '出口', value: 'E'},

]
import { useRouter } from 'vue-router';
const router = useRouter()

const goDetail = (id) =>{
  console.log(id)

  let routeData
    // const routeData
      const _path =  '/customsDeclarationDetail'
      const pramSelf = { headId: id };
       routeData = router.resolve({ //使用resolve
      name: '',
      path: _path,
      query: pramSelf,
      })
      window.open(routeData.href, '_blank')
    



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
        tableData.value = response.items
        total.value = response.total
      })
      .catch((response) => {
      })
}


const exportExcel = (year) => {
  exportMajorProcess(year).then(res => {
    let blob = new Blob([res.data], {type: 'application/octet-stream'});
    let url = URL.createObjectURL(blob);
    const link = document.createElement('a'); //创建a标签
    link.href = url;
    link.download = '重大进程.xlsx'; //重命名文件
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


