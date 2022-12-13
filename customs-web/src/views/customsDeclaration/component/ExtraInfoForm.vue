<template>
  <div class="finance scroll-y pb-100">
    <el-form
      ref="formRef"
      :model="formData"
      label-width="160px"
      :label-position="labelPosition"
      class="demo-ruleForm ml-3"
    >
               <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="检验检疫受理机关:">
                    <el-input readonly  v-model="formData.orgcodeValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="企业资质:">
                    <el-input readonly  v-model="formData.coplimitsnames" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="领证机关:">
                    <el-input readonly  v-model="formData.vsaorgcodeValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="口岸检验检疫机关:">
                    <el-input readonly  v-model="formData.insporgcodeValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="启运日期:">
                    <el-input readonly  v-model="formData.despdate" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="B/L号:">
                    <el-input readonly  v-model="formData.blno" />
                  </el-form-item>
                </el-col>
              </el-row>


              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="目的地检验检疫机关:">
                    <el-input readonly  v-model="formData.purporgcodeValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="关联号码及理由:">
                    <el-input readonly  v-model="formData.correlationno" />
                  </el-form-item>
                </el-col>

                 <el-col :span="5" class="_el_col" :push="2">
                    <el-input readonly  v-model="formData.correlationreasonflagValue" />
                </el-col>

                   <el-col :span="3" class="_el_col" :push="3">
                   <el-button type="primary" @click="usePersionWin()" >使用人</el-button>
                   </el-col>


              </el-row>


              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="原箱运输:">
                    <el-input readonly  v-model="formData.origboxflagValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="特殊业务标识:">
                    <el-input readonly  v-model="formData.specdeclflagValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="_el_col">
                  <el-form-item label="所需单证:">
                    <el-input readonly  v-model="formData.sxdz" />
                  </el-form-item>
                </el-col>


                  <el-col :span="3" class="_el_col" :push="3">
                   <el-button type="primary" @click="jyjySbysWin()" >检验检疫签证申报要素</el-button>
                   </el-col>
              </el-row>







    </el-form>

     <dialogUsePersionWin ref="dialogUsePersionWinRef" 
       :dialog-good="dialogUsePersionWinData"
       
       :head-id="currentId"
        />

        <dialogJyjySbysWin ref="dialogJyjySbysWinRef" 
       :dialog-good="dialogJyjySbysWinData"
       :form-data="formJyjySbysWin"
       :head-id="currentId"
        />





  </div>
</template>

<script lang="ts" setup>
import type {ElForm} from 'element-plus'
import {Plus, Edit, Delete, Search, Share} from '@element-plus/icons-vue'
import {ElMessage, ElMessageBox} from "element-plus";
import type {UploadFile} from 'element-plus/es/components/upload/src/upload.type'
import {Link} from '@element-plus/icons-vue'

import DialogUsePersionWin from './dialogUsePersionWin.vue'
let formUsePersionWin = ref({})
const dialogUsePersionWinData: Ref<dialogTy> = ref({})
const currentId = ref<string>('')


import DialogJyjySbysWin from './dialogJyjySbysWin.vue'
let formJyjySbysWin = ref({})
const dialogJyjySbysWinData: Ref<dialogTy> = ref({})

 const dialogUsePersionWinRef = ref<InstanceType<typeof DialogUsePersionWin>>()

 const dialogJyjySbysWinRef = ref<InstanceType<typeof DialogJyjySbysWin>>()

// 组件属性
const props = defineProps({
  
   formData: {
    require: true,
    default: null,
    type: Object
  }
  
})
 
const formRef = ref<InstanceType<typeof ElForm>>()



const usePersionWin = () => {
  console.log(999)
  console.log( props.formData)
  const headid =   props.formData.id ;
    // console.log(promiseitmes)
    const firstIsYes = false
    if(headid){           
      currentId.value = headid;
    }
    dialogUsePersionWinData.value = {
    show: true,
    title: '使用人',
    
  }
  dialogUsePersionWinRef.value.getList(headid);

}


const jyjySbysWin = () => {
   formJyjySbysWin.value =   props.formData ;
   const headid =   props.formData.id ;
    // console.log(promiseitmes)
    const firstIsYes = false
    
    dialogJyjySbysWinData.value = {
    show: true,
    title: '检验检疫申报要素',
    
  }
dialogJyjySbysWinRef.value.getList(headid);
}




// const emit = defineEmits(['closeDrawer'])

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






const resetForm = () => {
  formRef.value?.resetFields()
}

// defineExpose({formData, saveForm, submitForm, resetForm})
</script>

<style scoped lang="scss">
._el_col {
  height: 24px !important;
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
