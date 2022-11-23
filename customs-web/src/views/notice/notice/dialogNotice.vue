<template>
  <div>
    <el-dialog v-model="dialogNotice.show" :title="dialogNotice.title" width="50%" center>
      <el-form ref="refNoticeForm" :model="formData" :rules="rules" label-width="80px">
        <el-form-item label="通知类别" prop="tips">
          <el-input v-model="formData.tips" placeholder="通知类别" maxlength="4" type="text" show-word-limit style="width:200px"/>
        </el-form-item>
         <el-form-item label="通知标题" prop="title">
           <el-input v-model="formData.title" placeholder="通知标题" maxlength="50" show-word-limit type="text"/>
         </el-form-item>
        <el-form-item label="通知内容" prop="title">
          <el-input v-model="formData.text" placeholder="通知内容" maxlength="255" show-word-limit type="textarea"/>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetForm">取 消</el-button>
          <el-button v-if="dialogNotice.option === 'add'" type="primary" :disabled="isDisabled.add" @click="addNotice()">确 定</el-button>
          <el-button v-if="dialogNotice.option === 'edit'" type="primary" @click="editNotice()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import {dialogTy} from '~/dialog'
import {PropType} from 'vue'

//定义组件的属性
const props = defineProps({
  dialogNotice: {
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

const refNoticeForm: any = ref(null)

const isDisabled = ref({
  add:false
});

const rules = ref({
  tips: [{required: true, message: '请输入通知类别', trigger: 'blur'}],
  title: [{required: true, message: '请输入通知标题', trigger: 'blur'}],
  text: [{required: true, message: '请输入通知内容', trigger: 'blur'}]
})

const emits = defineEmits(['handleSubmitAdd', 'handleSubmitEdit'])


const addNotice = () => {
  refNoticeForm.value.validate((valid: any) => {
    if (valid) {
      isDisabled.value.add=true;
      if (props.dialogNotice.option === 'add') {
        emits('handleSubmitAdd')
      }
      isDisabled.value.add=false;
    } else {
      return false
    }
  })
}

const editNotice = () => {
  refNoticeForm.value.validate((valid: any) => {
    if (valid) {
      if (props.dialogNotice.option === 'edit') {
        emits('handleSubmitEdit')
      }
    } else {
      return false
    }
  })
}

const resetForm = () => {
  props.dialogNotice.show = false
  refNoticeForm.value.resetFields()
}
</script>
