<template>
    <div>
    
        <el-dialog v-model="dialogGood.show" :title="dialogGood.title" width="45%" center>
            <template #header>
    
              <div class="clearfix">
    
                <span>使用人</span>
    
              </div>
            </template>

            <el-table  height="240" v-model:data="tableData" v-loading="loading" stripe style="width: 100%">
          <el-table-column type="selection" align="center" width="50" />
          <el-table-column type="index" label="序号" align="center" width="55">
            <template #default="scope">
              <span>{{  scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="docucode" label="使用单位联系人" align="center">
          </el-table-column>
          <el-table-column prop="certcode" label="使用单位联系电话" align="center">
          </el-table-column>
        
          
        </el-table>



         
        
       

    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { dialogTy } from '~/dialog'
import { PropType } from 'vue'
import { getLogList } from "@/api/qpDec";
import { parseTime, deepClone } from '@/utils/dateTime'

import { listUser } from "@/api/qpDec";

const tableData: any = ref(null)
// const tableData: any = ref(null)
const loading = false
// 角色总数
const total = ref(null)

onMounted(() => {
    // getList()
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
    dialogGood: {
        require: true,
        default: null,
        type: Object as PropType < dialogTy >
    }
})

//定义变量
// const tenderGroup = [
//   { id: '1', realName: '税单无纸化',},
//   { id: '2', realName: '自主报税', },
//   { id: '3', realName: '水运中转', },
//   { id: '4', realName: '自报自缴', },
//   { id: '5', realName: '担保验放', },
//   { id: '6', realName: '查验分流', },
// ]

// // 获取角色列表
const getList = (_headId) => {
    // console.log(123)
    //  console.log(props.headId)
    if (!_headId) {
        return;
    }
    let params =  {
        headid: _headId

    }
    listUser(params)
        .then((response) => {
            tableData.value = response.data.items
            // total.value = response.data.total
        })
        .catch((response) => {})
}
// /**
//  * 分页大小修改处理
//  */
// const handleSizeChange = () => {
//     listQuery.page = 1
//     getList()
// }
// /**
//  * 查询框处理
//  */
// const handleFilter = () => {
//     listQuery.page = 1
//     getList()
// }


// const resetForm = () => {
//   props.dialogRoles.show = false
// }

defineExpose({ getList })
</script>

<style scoped lang="scss">
._el_col {
  height: 16px !important;
}

.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-tabs__content {
  padding-top: 30px;
  border: 1px solid #e4e7ed;
  border-top: none;
}

.el-tabs__header {
  margin: 0;
}

.pb-100 {
  padding-bottom: 100px;
}

.upload-demo {
  width: 60%;
}
</style>
