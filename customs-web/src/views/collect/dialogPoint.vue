<template>
  <div>
    <el-dialog v-model="dialogPoint.show" :title="dialogPoint.title" width="40%" center @open="initData" @close="resetForm" destroy-on-close>
      <div style="height:500px">
        <el-scrollbar>
          <div>
            <el-form ref="refPointForm" :model="formData" label-width="100px">
              <el-form-item label="点位坐标" prop="position">
                <baidu-map :msg="formData.position" style="width:400px;height:200px;"></baidu-map>
              </el-form-item>
              <el-form-item label="点位名称" prop="name">
                <el-input v-model="formData.name" disabled style="width: 200px"/>
              </el-form-item>
              <el-form-item label="点位类型" prop="type">
                <el-select v-model="formData.type" disabled style="width: 200px" >
                  <el-option label="码头" value="1"></el-option>
                  <el-option label="渔港" value="2"></el-option>
                  <el-option label="船闸" value="3"></el-option>
                  <el-option label="船舶临时停靠点" value="4"></el-option>
                  <el-option label="其他走私风险点" value="5"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="现场图片" prop="photoId">
                <el-upload
                  :action="baseUrl + '/file/upload?type='+ formData.id"
                  list-type="picture-card"
                  accept=".jpg,.jpeg,.png,.bmp,.JPG,.JPEG,.PBG,.BMP"
                  :on-success="handleUploadSuccess"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove"
                  :auto-upload="true"
                  :headers="headerReq"
                  :file-list="fileList"
                  disabled
                >
                  <el-icon><Plus /></el-icon>
                </el-upload>
              </el-form-item>
              <el-form-item label="所在地" prop="area">
                <el-input v-model="formData.area" disabled style="width: 200px"></el-input>
              </el-form-item>
              <el-form-item label="沿江/沿海" prop="along">
                <el-select v-model="formData.along" disabled style="width: 200px">
                  <el-option label="沿江" value="1"></el-option>
                  <el-option label="沿海" value="2"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="性质" prop="properties" v-if="formData.type == 1">
                <el-select v-model="formData.properties" disabled style="width: 200px;">
                  <el-option label="民营" value="1"></el-option>
                  <el-option label="国有" value="2"></el-option>
                  <el-option label="合资" value="3"></el-option>
                  <el-option label="个体" value="4"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="类别" prop="category" v-if="formData.type == 1">
                <el-select v-model="formData.category" disabled style="width: 200px">
                  <el-option label="危化品" value="1"></el-option>
                  <el-option label="通用散杂货" value="2"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="类别" prop="category" v-if="formData.type == 2">
                <el-select v-model="formData.category" disabled style="width: 200px">
                  <el-option label="开放式" value="1"></el-option>
                  <el-option label="封闭式" value="2"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="类别" prop="category" v-if="formData.type == 3">
                <el-select v-model="formData.category" disabled style="width: 200px">
                  <el-option label="水闸" value="1"></el-option>
                  <el-option label="船闸" value="2"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="码头数" prop="dockNum" v-if="formData.type == 2">
                <el-input v-model="formData.dockNum" disabled style="width: 200px;"/>
              </el-form-item>
              <el-form-item label="泊位数" prop="berthNum" v-if="formData.type == 1">
                <el-input v-model="formData.berthNum" disabled style="width: 200px"/>
              </el-form-item>
              <el-form-item label="可停靠船舶数" prop="berthNum" v-if="formData.type == 2 || formData.type == 3">
                <el-input v-model="formData.berthNum" disabled style="width: 200px"/>
              </el-form-item>
              <el-form-item label="吊机情况" prop="crane" v-if="formData.type == 1 || formData.type == 2 || formData.type == 4">
                <el-select v-model="formData.crane" disabled style="width: 200px">
                  <el-option label="有吊机" value="1"></el-option>
                  <el-option label="无吊机" value="2"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="最大吨级" prop="maxTon" v-if="formData.type == 1 || formData.type == 3 || formData.type == 4">
                <el-input v-model="formData.maxTon" disabled style="width: 200px"/>
              </el-form-item>
              <el-form-item label="是否通航" prop="whetherSail" v-if="formData.type == 3">
                <el-select v-model="formData.whetherSail" disabled style="width: 200px">
                  <el-option label="是" value="1"></el-option>
                  <el-option label="否" value="2"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="日均进闸船舶数量" prop="averageBoats" v-if="formData.type == 3">
                <el-input v-model="formData.averageBoats" disabled style="width: 200px;"/>
              </el-form-item>
              <el-form-item label="用途" prop="useFor" v-if="formData.type == 4">
                <el-input v-model="formData.useFor" disabled type="textarea"/>
              </el-form-item>
              <el-form-item label="走私风险等级" prop="riskLevel">
                <el-select v-model="formData.riskLevel" disabled style="width: 200px">
                  <el-option label="低" value="1"></el-option>
                  <el-option label="中" value="2"></el-option>
                  <el-option label="高" value="3"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="风险原因" prop="riskReason" v-if="formData.riskLevel != 1">
                <el-input v-model="formData.riskReason" disabled type="textarea"/>
              </el-form-item>
              <el-form-item label="监管举措" prop="manageMeasure" v-if="formData.riskLevel != 1">
                <el-input v-model="formData.manageMeasure" disabled type="textarea"/>
              </el-form-item>
              <el-form-item label="主要负责人" prop="maintainPersonList">
                <el-table :data="formData.maintainPersonList" :show-header="false" style="width: 50%;" :border="true">
                  <el-table-column prop="name" label="姓名"></el-table-column>
                  <el-table-column prop="job" label="职务"></el-table-column>
                  <el-table-column prop="telephone" label="电话"></el-table-column>
                </el-table>
              </el-form-item>
              <el-form-item label="监管负责人" prop="supervisePersonList" v-if="formData.riskLevel != 1">
                <el-table :data="formData.supervisePersonList" :show-header="false" style="width: 50%;" :border="true">
                  <el-table-column prop="name" label="姓名"></el-table-column>
                  <el-table-column prop="job" label="职务"></el-table-column>
                  <el-table-column prop="telephone" label="电话"></el-table-column>
                </el-table>
              </el-form-item>
            </el-form>
          </div>
        </el-scrollbar>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" v-if="formData.status == 1" @click="audit(2)">审核通过</el-button>
          <el-button type="info" v-if="formData.status == 1"  @click="audit(3)">审核不通过</el-button>
          <el-button @click="resetForm">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import {PropType} from "vue";
import {dialogTy} from "~/dialog";
import {ElMessage, UploadProps, UploadUserFile} from "element-plus";
import {queryPointById, updateStatus} from "@/api/point";
import BaiduMap from "@/views/collect/baiduMap.vue"
import {getToken} from "@/utils/auth";
import { Plus } from '@element-plus/icons-vue';

const baseUrl = ref(import.meta.env.VITE_APP_BASE_URL)
const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const headerReq = ref({"authToken": getToken()})
const fileList = ref<UploadUserFile []>([])
const refPointForm: any = ref(null)
const props = defineProps({
  dialogPoint: {
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
const points = ref([])

const audit = (flag) => {
  if(flag == 2){
    updateStatus(props.formData.id,flag).then(res => {
      props.dialogPoint.show = false
      ElMessage({message: '审核已通过', type: 'success'})
      emit('audit')
    })
  }
  if(flag == 3){
    updateStatus(props.formData.id,flag).then(res => {
      props.dialogPoint.show = false
      ElMessage({message: '已更新审核状态', type: 'success'})
      emit('audit')
    })
  }
}

const handleUploadSuccess = (response, file, fileList) => {
  if(props.formData.photoId){
    props.formData.photoId += response.data+',';
  }else{
    props.formData.photoId = response.data+',';
  }
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
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

const emit = defineEmits(['audit'])

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

const resetForm = () => {
  props.dialogPoint.show = false
  refPointForm.value.resetFields()
  fileList.value=[]
  points.value=[]
}
</script>

<style>
</style>
