<template>
  <div>
    <el-dialog v-model="dialogLine.show" :title="dialogLine.title" width="40%" center @open="initData" @close="resetForm" destroy-on-close>
      <div style="height:500px">
        <el-scrollbar>
          <div>
            <el-form ref="refLineForm" :model="formData" label-width="100px">
              <el-form-item label="坐标" prop="position">
                <baidu-map :msg="formData.position" style="width:400px;height:200px;"></baidu-map>
              </el-form-item>
              <el-form-item label="点位名称" prop="name">
                <el-input v-model="formData.name" disabled style="width: 200px"/>
              </el-form-item>
              <el-form-item label="所在地" prop="area">
                <el-input v-model="formData.area" disabled style="width: 200px"></el-input>
              </el-form-item>
              <el-form-item label="巡查负责人" prop="patrolPersonList">
                <el-table :data="formData.patrolPersonList" :show-header="false" style="width: 50%;" :border="true">
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
import {queryLineById, updateStatus} from "@/api/line";
import BaiduMap from "@/views/lineCollect/baiduMap.vue"

const fileList = ref<UploadUserFile []>([])
const refLineForm: any = ref(null)
const props = defineProps({
  dialogLine: {
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
const lines = ref([])

const audit = (flag) => {
  if(flag == 2){
    updateStatus(props.formData.id,flag).then(res => {
      props.dialogLine.show = false
      ElMessage({message: '审核已通过', type: 'success'})
      emit('audit')
    })
  }
  if(flag == 3){
    updateStatus(props.formData.id,flag).then(res => {
      props.dialogLine.show = false
      ElMessage({message: '已更新审核状态', type: 'success'})
      emit('audit')
    })
  }
}

const emit = defineEmits(['audit'])

//初始化信息 查询照片，点位
const initData = () => {
  if(props.formData.id){
    //点位
    if (props.formData.lineId) {
      queryLineById({Id:props.formData.lineId}).then((response) => {
        lines.value.push({
          name: response.data.name,
          id: response.data.id
        })
      })
    } else {
      lines.value = []
    }
  }
}

const resetForm = () => {
  props.dialogLine.show = false
  refLineForm.value.resetFields()
  lines.value=[]
}
</script>

<style>
</style>
