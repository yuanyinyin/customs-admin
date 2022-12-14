<template>
    <div>
    
        <el-dialog v-model="dialogGood.show" :title="dialogGood.title" width="24%" center>
            <template #header>
    
              <div class="clearfix">
    
                <span>选择打印类型</span>
    
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
                   <el-checkbox-group v-model="formData.checkList"  >
          <el-checkbox
            v-for="(item, index) in tenderGroup"
            :key="index"
            :label="item.id"
          >{{ item.realName }}
          </el-checkbox>
        </el-checkbox-group>
                </el-col>
              
              </el-row>

              

              
              
    </el-form>
       
    <br>
     <br>
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
const tenderGroup = [
  { id: '1', realName: '报关单核对单',},
  { id: '2', realName: '报关单商品附加联', },
  { id: '3', realName: '集装箱', },
  { id: '4', realName: '检验检疫', },
  { id: '5', realName: '放行通知书', },
  { id: '6', realName: '报关单', },
]

// // 获取角色列表
 
 
// const resetForm = () => {
//   props.dialogRoles.show = false
// }

// defineExpose({ getList })
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
