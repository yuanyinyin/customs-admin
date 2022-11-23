<template>
  <div>
    <el-dialog v-model="dialogRoles.show" :title="dialogRoles.title" width="30%" center>
      <el-form ref="refRolesForm" :model="formData" label-width="80px" :rules="rules">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="formData.roleName" placeholder="角色名称" style="width:90%;"/>
        </el-form-item>
        <el-form-item label="角色编码" prop="roleCode">
          <el-input v-model="formData.roleCode" placeholder="角色编码" style="width:90%;"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetForm">取消</el-button>
          <el-button type="primary" @click="handleSubmitAdd()">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import {dialogTy} from '~/dialog'
import {PropType} from 'vue'
import {verifyRoleCode} from "@/api/roles";

//定义组件的属性
const props = defineProps({
  dialogRoles: {
    require: true,
    default: null,
    type: Object as PropType<dialogTy>
  },
  formData: {
    require: true,
    default: null,
    type: Object
  },
})

const validateCodeRepeat = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入角色编码'))
  } else {
    verifyRoleCode(value, props.formData.id).then(response => {
      if (response.data === 'exists') {
        callback(new Error('角色编码已经存在，不能重复'))
      } else {
        callback()
      }
    })
  }
}

const rules = ref({
  roleName: [{required: true, message: '请输入角色名称', trigger: 'blur'}],
  roleCode: [{required: true, validator: validateCodeRepeat, trigger: 'blur'}]
})

const refRolesForm: any = ref(null)
const store = useStore()
const emit = defineEmits(['handleSubmitAdd', 'handleSubmitEdit'])

const handleSubmitAdd = () => {
  refRolesForm.value.validate((valid: any) => {
    if (valid) {
      if (props.dialogRoles.option === 'add') {
        emit('handleSubmitAdd')
      } else {
        emit('handleSubmitEdit')
      }
    } else {
      return false
    }
  })
}

const resetForm = () => {
  props.dialogRoles.show = false
  refRolesForm.value.resetFields()
}
</script>
