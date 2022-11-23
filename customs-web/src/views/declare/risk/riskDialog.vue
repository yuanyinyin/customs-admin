
<template>
  <div>
    <el-dialog v-model="userDialog.show" :title="userDialog.title" width="60%" center @open="initData" @close="resetForm">
      <el-form ref="refUserForm" :model="formData" :rules="rules" label-width="80px">
        <el-form-item v-if="userDialog.option === 'edit'" v-show="false" label="id" prop="id"/>
        <el-form-item v-if="userDialog.option === 'detail'"  label="上报日期" prop="reportTime">
          <el-input v-model="formData.reportTime" placeholder="上报日期" :disabled="userDialog.option  == 'detail'" style="width: 300px;"/>
        </el-form-item>
        <el-form-item label="隐患地点" prop="pointId">
           <el-select :disabled="userDialog.option  == 'detail'" style="width: 300px;"
              v-model="formData.pointId"
              filterable
              remote
              reserve-keyword
              clearable
              placeholder="请输入点位，搜索后选择"
              :remote-method="queryPoint"
              :loading="loading"
            >
              <el-option
                v-for="item in points"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
        </el-form-item>
        <el-form-item  label="风险类别" prop="type">
           <el-select :disabled="userDialog.option  == 'detail'" v-model="formData.type" placeholder="请选风险类别" style="width: 300px;">
            <el-option
              v-for="item in typeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="现场图片" prop="photoId">
             <el-upload
              :action="baseUrl + '/file/upload?type=21'"
              list-type="picture-card"
              accept=".jpg,.jpeg,.png,.bmp,.JPG,.JPEG,.PBG,.BMP"
              
              :on-success="handleUploadSuccess"
              :on-preview="handlePictureCardPreview"  
              :on-remove="handleRemove"
              :auto-upload="true"
              :headers="headerReq"
              :disabled="userDialog.option  == 'detail'" 
              :file-list="fileList"
            >
              <el-icon><Plus /></el-icon>
            </el-upload>
        </el-form-item>

        <el-dialog  v-model="dialogVisible">
          <el-image  :src="dialogImageUrl" alt="Preview Image" />
        </el-dialog>

        <el-form-item label="具体情况" prop="situation">
          <el-input v-model="formData.situation" placeholder="详情"  :rows="10" type="textarea" :disabled="userDialog.option  == 'detail'"/>
        </el-form-item>

         <el-form-item label="备注" prop="memo">
          <el-input v-model="formData.memo" placeholder="备注（最多100字）" :disabled="userDialog.option  == 'detail'" />
        </el-form-item>
        <el-form-item label="处置情况" prop="result">
          <el-input v-model="formData.result" placeholder="处置情况" :disabled="userDialog.option  == 'detail'" :v-show="userDialog.option  != 'add'" />
        </el-form-item>
      </el-form>


      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetForm">取 消</el-button>
          <el-button v-if="userDialog.option === 'add'" type="primary" @click="addUser()">确 定</el-button>
          <el-button v-if="userDialog.option === 'edit'" type="primary" @click="editUser()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import {dialogTy} from '~/dialog'
import {PropType} from 'vue'
import {queryPointByName,queryPointById} from "@/api/point";
import {getToken} from "@/utils/auth";
import type { UploadProps, UploadUserFile } from 'element-plus'
import { Plus } from '@element-plus/icons-vue';



//定义组件的属性
const props = defineProps({
  userDialog: {
    require: true,
    default: null,
    type: Object as PropType<dialogTy>
  },
  formData: {
    require: true,
    default: null,
    type: Object
  }
})

const loading = ref(false)
const points = ref([])
const typeOptions = [
  { value: '0', label: '一般', },
  { value: '1', label: '较大风险', },
]

const baseUrl = ref(import.meta.env.VITE_APP_BASE_URL)
const headerReq = ref({"authToken": getToken()})
const fileList = ref<UploadUserFile []>([])
const dialogImageUrl = ref('')
const dialogVisible = ref(false)

const refUserForm: any = ref(null)

const rules = ref({
   pointId: [{required: true, message: '请选择地点', trigger: 'blur'}],
   type: [{required: true, message: '请选风险类别', trigger: 'blur'}]
})


const queryPoint = (query: string) => {
  console.log(query)
  if (query) {
    loading.value = true
    queryPointByName({pointName:query}).then((response) => {
      loading.value = false
      points.value = response.data
    })
  } else {
    points.value = []
  }
}

const handleUploadSuccess = (response, file, fileList) => {
  if(props.formData.photoId){
    props.formData.photoId += response.data+',';
  }else{
    props.formData.photoId = response.data+',';
  }
  
  // fileList.value.push({
  //           name: ruleForm.photo,
  //           url: baseUrl.value + '/file/downLoad/' + ruleForm.photo
  //         })
}
const handlePreview = (file: UploadFile) => {
  var a = document.createElement('a')
  if(file.id){
    a.setAttribute('href', baseUrl.value + '/file/downLoad/' + file.id)
  } else {
    // a.setAttribute('href', baseUrl.value + '/file/downLoad/' + ruleForm.returnFileId)
  }
  document.body.appendChild(a)
  a.click()
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  console.log('预览');
  dialogImageUrl.value = uploadFile.url!
  dialogVisible.value = true
}

const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
  if(uploadFile.response===undefined){
    props.formData.photoId=props.formData.photoId.replace(uploadFile.name+',','') ;
  }else{
    props.formData.photoId=props.formData.photoId.replace(uploadFile.response.data+',','') ;
  }
}

const emits = defineEmits(['userHandleSubmitAdd', 'userHandleSubmitEdit'])

const addUser = () => {
  refUserForm.value.validate((valid: any) => {
    if (valid) {
      if (props.userDialog.option === 'add') {
        emits('userHandleSubmitAdd')
      }
    } else {
      return false
    }
  })
}

const editUser = () => {
  refUserForm.value.validate((valid: any) => {
    if (valid) {
      if (props.userDialog.option === 'edit') {
        emits('userHandleSubmitEdit')
      }
    } else {
      return false
    }
  })
}

const resetForm = () => {
  props.userDialog.show = false
  refUserForm.value.resetFields()
  fileList.value=[]
  points.value=[]
}


// onMounted(() => {
//     initData();
// })

//初始化信息 查询照片，点位
const initData = () => {
  if(props.formData.id){
    //点位
    if (props.formData.pointId) {
      queryPointById({Id:props.formData.pointId}).then((response) => {
        // points.value = response.data

         points.value.push({
           name: response.data.name,
           id: response.data.id
        })
      })
    } else {
      points.value = []
    }

    //照片
    if(props.formData.photoId){
      let photoids = props.formData.photoId.split(',');
      for(var i = 0;i<photoids.length;i++){
        if(""!=photoids[i]){
          fileList.value.push({
            name: photoids[i],
            url: baseUrl.value + '/file/downLoad/' + photoids[i]
          })
        }
      }
    }
  }

}
</script>
