<template>
    <div>
    
        <el-dialog v-model="dialogDetail.show" :title="dialogDetail.title" width="30%" center>
    
    
    
            <template #header>
    
              <div class="clearfix">
    
                <span>信息详情</span>
    
              </div>
</template>
         
        <el-form
      ref="formRef"
      :model="formData"
      label-width="75px"
      :label-position="labelPosition"
      class="demo-ruleForm ml-3"
    >
               
                  <el-form-item label="类型:">
                   
                    <el-select v-model="formData.type" placeholder="请选择类型">
            <el-option
              v-for="item in types"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
                  </el-form-item>
               

           
                  <el-form-item label="推送人:">
                    <el-input v-model="formData.noticeUserName" />
                  </el-form-item>
                 

               
                  <el-form-item label="是否推送:">
                    <el-select v-model="formData.flag" placeholder="请选择是否推送">
            <el-option
              v-for="item in flags"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
                  </el-form-item>
              
               
                  <el-form-item label="详情:">
                    <el-input v-model="formData.content" :rows="3" type="textarea" />
                  </el-form-item>
                 

                  <el-form-item label="创建时间:">
                    <el-input v-model="formData.createTime" />
                  </el-form-item>
          

    </el-form>
       

    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { dialogTy } from '~/dialog'
import { PropType } from 'vue'
// import { getLogList } from "@/api/qpDec";
import { parseTime, deepClone } from '@/utils/dateTime'
const tableData: any = ref(null)
const loading = false
// 角色总数
const total = ref(null)

onMounted(() => {
    // getList()
})

const types = [
  { value: '1', label: '提醒', },
  { value: '2', label: '通知', },
 
]


const flags = [
  { value: '0', label: '未推送', },
  { value: '1', label: '已推送', },
 
]



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
    dialogDetail: {
        require: true,
        default: null,
        type: Object as PropType < dialogTy >
    }
})

 


// const resetForm = () => {
//   props.dialogRoles.show = false
// }


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
