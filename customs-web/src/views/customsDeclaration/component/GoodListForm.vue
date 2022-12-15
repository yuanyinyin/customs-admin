<template>
  <div class="finance scroll-y pb-100">
    <el-table  height="240"  v-model:data="tableData" v-loading="loading" stripe style="width: 100%" @row-dblclick="showDetail">
          <el-table-column  prop="gno" type="gno" label="商品序号" align="center" width="80">
            <!-- <template #default="scope">
              <span>{{  scope.$index + 1 }}</span>
            </template> -->
          </el-table-column>
          <el-table-column prop="codets" label="商品编号" align="center">
            <template  #default="scope">
                
                 <a class="text_self_blue" href="javascript:;" @click="showDetail(scope.row)">{{scope.row.codets}}</a>
                
             </template>


          </el-table-column>
          <el-table-column prop="gname" label="商品名称" align="center">
          </el-table-column>
           <el-table-column prop="manualno" label="检验检疫名称" align="center">
          </el-table-column>
          <el-table-column prop="contritem" label="备案序号" align="center">
          </el-table-column>
           <el-table-column prop="gmodel" label="规格型号" align="center">
          </el-table-column>
          <el-table-column prop="destinationcountryValue" label="原产国" align="center">
          </el-table-column>
           <el-table-column prop="origincountryValue" label="目的国" align="center">
          </el-table-column>
          <el-table-column prop="gqty" label="数量" align="center">
          </el-table-column>
          <el-table-column prop="declprice" label="单价" align="center">
          </el-table-column>
          <el-table-column prop="tradecurrValue" label="币制" align="center">
          </el-table-column>
          <el-table-column prop="decltotal" label="总价" align="center">
          </el-table-column>
          <el-table-column prop="dutymodeValue" label="征免" align="center">
          </el-table-column>
         
        </el-table>

          <DialogGood ref="dialogGoodRef" 
       :dialog-good="dialogData"
       :form-data="formGood"
        />



  </div>
</template>

<script lang="ts" setup>
import type {ElForm} from 'element-plus'
import {Plus, Edit, Delete, Search, Share} from '@element-plus/icons-vue'
import {ElMessage, ElMessageBox} from "element-plus";
import type {UploadFile} from 'element-plus/es/components/upload/src/upload.type'
import {Link} from '@element-plus/icons-vue'

import DialogGood from './dialogGood.vue'

const dialogData: Ref<dialogTy> = ref({})//ts的规范写法，定义变量的类型


let formGood = ref({})//这是相对自由点的写法，不定义类型

const tableData2: any = ref(null)


// 组件属性
const props = defineProps({
  tableData: {
    require: false,
    default: null,
    type: Array
  }
 
})

const formRef = ref<InstanceType<typeof ElForm>>()

const emit = defineEmits(['closeDrawer'])


onMounted(() => {
})

/** 附件上传部分 */

/** 附件上传 结束 */

// 表单保存


//表单提交

const resetForm = () => {
  formRef.value?.resetFields()
}

const showDetail = (row) => {
  console.log(123)
  console.log(row)
  console.log(row.destcodeValue)
  
   dialogData.value = {
    show: true,
    title: '商品详情',
    
  }
  formGood.value = row
}






</script>

<style scoped lang="scss">
// ._el_col {
//   height: 24px !important;
// }
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

.text_self_blue {
  color: var(--el-color-primary);
}

</style>
