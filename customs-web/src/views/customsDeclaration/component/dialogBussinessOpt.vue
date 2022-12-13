<template>
    <div>
    
        <el-dialog v-model="dialogGood.show" :title="dialogGood.title" width="40%" center>
            <template #header>
    
              <div class="clearfix">
    
                <span>业务事项</span>
    
              </div>
</template>
         
        <el-form
      ref="formRef"
      :model="formData"
      label-width="210px"
      :label-position="labelPosition"
      class="demo-ruleForm ml-3"
    >
               <el-row>
                <el-col :span="24" class="_el_col">
            <el-checkbox-group v-model="formData.checkList"  disabled>
          <el-checkbox
            v-for="(item, index) in formData.tenderGroup"
            :key="index"
            :label="item.id"
          >{{ item.realName }}
          </el-checkbox>
        </el-checkbox-group>
                </el-col>
              
              </el-row>

              

              
              
    </el-form>
       

    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { dialogTy } from '~/dialog'
import { PropType } from 'vue'
import { getLogList } from "@/api/qpDec";
import { parseTime, deepClone } from '@/utils/dateTime'
const tableData: any = ref(null)
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
    formData: {
    require: true,
    default: null,
    type: Object
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
const getList = () => {
    // console.log(123)
    //  console.log(props.headId)
    if (!props.headId) {
        return;
    }
    let params = Object.assign(deepClone(listQuery._rawValue), {
        headid: props.headId

    })
    getLogList(params)
        .then((response) => {
            tableData.value = response.data.items
            total.value = response.data.total
        })
        .catch((response) => {})
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
