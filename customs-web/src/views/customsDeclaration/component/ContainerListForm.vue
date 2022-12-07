<template>
  <div class="finance scroll-y pb-100">
    <el-table v-model:data="tableData" v-loading="loading" stripe style="width: 100%">
          <el-table-column type="selection" align="center" width="50" />
          <el-table-column type="index" label="序号" align="center" width="55">
            <template #default="scope">
              <span>{{  scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="entryid" label="集装箱号" align="center">
          </el-table-column>
          <el-table-column prop="tradename" label="集装箱规格" align="center">
          </el-table-column>
           <el-table-column prop="manualno" label="拼箱标识" align="center">
          </el-table-column>

           <el-table-column prop="entryid" label="自重(KG)" align="center">
          </el-table-column>
          <el-table-column prop="tradename" label="商品项号关系" align="center">
          </el-table-column>
          


        </el-table>


          <br>
        <br>
            <br>


<!-- <el-form
      ref="formRef"
      :model="formData"
      label-width="160px"
      :label-position="labelPosition"
      class="demo-ruleForm ml-3"
    >
               <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="集装箱号:">
                    <el-input />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="集装箱规格:">
                    <el-input />
                  </el-form-item>
                </el-col>
              </el-row>

               <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="自重(KG):">
                    <el-input />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="拼箱标识:">
                    <el-input />
                  </el-form-item>
                </el-col>
              </el-row>

               <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="商品项号关系:">
                    <el-input />
                  </el-form-item>
                </el-col>
               
              </el-row>






</el-form> -->

  </div>
</template>

<script lang="ts" setup>
import type {ElForm} from 'element-plus'
import {Plus, Edit, Delete, Search, Share} from '@element-plus/icons-vue'
import {ElMessage, ElMessageBox} from "element-plus";
import type {UploadFile} from 'element-plus/es/components/upload/src/upload.type'
import {Link} from '@element-plus/icons-vue'

const tableData: any = ref(null)


// 组件属性
const props = defineProps({
  current: {
    type: Object,
    default: null,
    require: false
  },
  currentId: {
    type: Number,
    default: null,
    require: false
  },
  formData: {
    require: true,
    default: null,
    type: Object
  }
 
})
const formData = ref({
  reimburseSubArr: [],
  payAmountNumber: 0,
  payAmountCapital: '零元整',
  applyStatus: 'store',
  saveFiles: {
    reimburseFile: []
  },
  audit: {
    signatureId: 0,
    status: '',
    reason: ''
  }
})
const formRef = ref<InstanceType<typeof ElForm>>()

const emit = defineEmits(['closeDrawer'])

// const initData = () => {
//   if (props.ope !== "add" && props.currentId) {
//     showOne(props.currentId).then(res => {
//       if (res.code == 200) {
//         formData.value = res.data
//         // formData.value.count = formData.value.reimburseSubArr.length
//         let fileArr = []
//         //文件可以有多个
//         let files = res.data.reimburseFile
//         if (files) {
//           files.forEach(fileData => {
//             let fileJson = {
//               id: fileData.id,
//               name: fileData.fileName,
//               url: fileData.fileUrl
//             }
//             fileArr.push(fileJson)
//           })
//           reimburseFile.value = fileArr
//           formData.value.saveFiles = {reimburseFile: fileArr}
//           fileArr = []
//         }
//       } else {
//         ElMessage(({message: '数据获取失败！' + res.data, type: 'error'}))
//       }
//     })
//   } else {
//     formData.value.reimburseSubArr = []
//   }
// }
onMounted(() => {
  // initData()
})

/** 附件上传部分 */

/** 附件上传 结束 */

// 表单保存


//表单提交

const resetForm = () => {
  formRef.value?.resetFields()
}

// defineExpose({formData, saveForm, submitForm, resetForm})
</script>

<style >
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
