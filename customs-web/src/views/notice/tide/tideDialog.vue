
<template>
  <div>
    <el-dialog v-model="userDialog.show" :title="userDialog.title" width="30%" center>
      <el-form ref="refUserForm" :model="formData" :rules="rules" label-width="80px">
        <el-form-item v-if="userDialog.option === 'edit'" v-show="false" label="id" prop="id"/>
        <el-form-item label="涨潮时间" prop="riseTime">
           <el-date-picker
                v-model="formData.riseTime"
                type="datetime"
                placeholder="涨潮时间"
                format="YYYY-MM-DD HH:mm:ss"
                 style="width: 90%"
            />
        </el-form-item>
        <el-form-item label="退潮时间" prop="fallTime">
          <el-date-picker
                v-model="formData.fallTime"
                type="datetime"
                placeholder="退潮时间"
                format="YYYY-MM-DD HH:mm:ss"
                style="width: 90%"
            />
        </el-form-item>
        <el-form-item label="涨潮时间" prop="riseTime2">
           <el-date-picker
                v-model="formData.riseTime2"
                type="datetime"
                placeholder="涨潮时间"
                format="YYYY-MM-DD HH:mm:ss"
                 style="width: 90%"
            />
        </el-form-item>
       
        <el-form-item label="退潮时间" prop="fallTime2">
          <el-date-picker
                v-model="formData.fallTime2"
                type="datetime"
                placeholder="退潮时间"
                format="YYYY-MM-DD HH:mm:ss"
                style="width: 90%"
            />
        </el-form-item>

      </el-form>


      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetForm">取 消</el-button>
          <el-button v-if="userDialog.option === 'add'" type="primary" :disabled="isDisabled.add" @click="addUser()">确 定</el-button>
          <el-button v-if="userDialog.option === 'edit'" type="primary" :disabled="isDisabled.edit" @click="editUser()">确 定</el-button>
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

const refUserForm: any = ref(null)

const rules = ref({
   riseTime: [{required: true, message: '请选择涨潮时间', trigger: 'blur'}],
  fallTime: [{required: true, message: '请选择退潮时间', trigger: 'blur'}],
  riseTime2: [{required: true, message: '请选择涨潮时间', trigger: 'blur'}],
  fallTime22: [{required: true, message: '请选择退潮时间', trigger: 'blur'}]
})

const isDisabled = ref({
  add:false,
  edit:false
});

const emits = defineEmits(['userHandleSubmitAdd', 'userHandleSubmitEdit'])

const addUser = () => {

 refUserForm.value.validate((valid: any) => {
    if (valid) {
      isDisabled.value.add=true;
      if (props.userDialog.option === 'add') {
        emits('userHandleSubmitAdd')
      }
      isDisabled.value.add=false;
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
}
</script>
