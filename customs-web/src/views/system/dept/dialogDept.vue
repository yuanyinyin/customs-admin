<template>
  <div>
    <el-dialog v-model="dialogDept.show" :title="dialogDept.title" width="40%" center @open="openFun">
      <el-form ref="refDeptForm" :model="formData" label-width="80px" :rules="rules">
          <el-form-item label="上级部门" prop="parentId">
            <el-cascader :props="propsDept" v-model="formData.parentId" :options="allDept" clearable/>
          </el-form-item>
          <el-form-item label="部门名称" prop="deptName">
            <el-input v-model="formData.deptName" placeholder="请输入部门名称" style="width: 200px"/>
          </el-form-item>

          <el-form-item label="显示排序" prop="orderNum">
            <el-input-number v-model="formData.orderNum" controls-position="right" :min="0" />
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button type="primary" @click="handleSubmitAdd">确 定</el-button>
            <el-button @click="resetForm">取 消</el-button>
          </div>
        </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import {dialogTy} from '~/dialog' //引入变量类型
import {PropType} from 'vue'
import {getDept} from "@/api/dept";

const propsDept = {
  multiple: false,
  checkStrictly: true,
  label: 'deptName',
  value: 'id'
}

//定义组件的属性
const props = defineProps({
  dialogDept: {
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

const rules = ref({
  parentId: [{required: true, message: '请输入上级部门', trigger: 'blur'}],
  deptName: [{required: true, message: '请输入部门名称', trigger: 'blur'}],
  orderNum: [{required: true, message: '请输入排序编号', trigger: 'blur'}]
})

const allDept = ref(null)
const refDeptForm: any = ref(null)

const openFun = () => {
  getDept().then((response=>{
    let deptBack = response.data
    deptBack.push({
      id: '0',
      deptName: '顶级部门'
    })
    allDept.value = deptBack
  }))



}

const emit = defineEmits(['handleSubmitAdd', 'handleSubmitEdit'])

const handleSubmitAdd = () => {
  refDeptForm.value.validate((valid: any) => {
    if (valid) {
      if (props.dialogDept.option === 'add') {
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
  props.dialogDept.show = false
  refDeptForm.value.resetFields()
}



</script>

<style lang="scss" scoped></style>
