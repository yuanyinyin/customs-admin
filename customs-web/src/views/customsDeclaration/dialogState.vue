<template>
  <div>
   <el-dialog v-model="dialogRoles.show" :title="dialogRoles.title" width="60%" center>
      <!-- <el-card class="m-1"> -->
        <template #header>
          <div class="clearfix">
            <span>通关列表</span>
          </div>
          

        </template>
        <el-table  v-model:data="tableData" v-loading="loading" stripe style="width: 100%">
          <el-table-column type="index" label="序号" align="center" width="55">
            <template #default="scope">
              <span>{{ (listQuery.page - 1) * listQuery.limit + scope.$index + 1 }}</span>
            </template>
          </el-table-column>

          <el-table-column prop="operdate" label="通关时间" align="center">
            <template #default="scope">
              {{ parseTime(scope.row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column prop="opercode" label="编码" align="center">
          </el-table-column>
          <el-table-column prop="opercontent" label="内容" align="center">
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
      <!-- </el-card> -->

    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import {dialogTy} from '~/dialog'
import {PropType} from 'vue'
import {getLogList} from "@/api/qpDec";
import {parseTime, deepClone} from '@/utils/dateTime'
const tableData: any = ref(null)
const loading = false
// 角色总数
const total = ref(null)

onMounted(() => {
  getList()
})

// // 查询配置 [pageNum 当前页数, pageSize 每页大小]
const listQuery: any = ref({
  page: 1,
  limit: 10
})

// //定义组件的属性
const props = defineProps({
  headId: {
    type: String,
    default: '',
    require: true
  },
  dialogRoles: {
    require: true,
    default: null,
    type: Object as PropType<dialogTy>
  }
})


// // const store = useStore()

// const handleSizeChange = () => {
//   //  if(!props.headId){
//   //     return;
//   //  }
      
//   }

// const getLogList2 = () => {
//   // listQuery.page = 1
//   // getLogList()
// }

// // 获取角色列表
const getList = () => {
  // console.log(123)
  //  console.log(props.headId)
  if (!props.headId) {
      return;
  }
  let params = Object.assign(deepClone(listQuery._rawValue),
            {
             headid: props.headId 
             
             }
             )
    getLogList(params)
      .then((response) => {
        tableData.value = response.data.items
        total.value = response.data.total
      })
      .catch((response) => {
      })
}
// /**
//  * 分页大小修改处理
//  */
const handleSizeChange = () => {
  listQuery.page = 1
  getList()
}
/**
 * 查询框处理
 */
const handleFilter = () => {
  listQuery.page = 1
  getList()
}


// const resetForm = () => {
//   props.dialogRoles.show = false
// }

defineExpose({getList})
</script>
