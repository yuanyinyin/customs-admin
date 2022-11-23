
<template>
  <div>
    <el-dialog v-model="userDialog.show" :title="userDialog.title" width="50%" center>
      <el-form ref="refUserForm" :model="formData" :rules="rules" label-width="80px">
        <el-form-item v-if="userDialog.option === 'edit'" v-show="false" label="id" prop="id"/>
        <el-form-item v-if="userDialog.option === 'detail'"  label="发布日期" prop="declareDate">
          <el-input v-model="formData.declareDate" placeholder="发布日期" :disabled="userDialog.option  == 'detail'"/>
        </el-form-item>
        <el-form-item label="发布详情" prop="detail">
          <el-input v-model="formData.detail" placeholder="详情"  :rows="10" type="textarea" :disabled="userDialog.option  == 'detail'"/>
        </el-form-item>
        <el-form-item label="责任部门" prop="deptName">
          <el-input v-model="formData.deptName" placeholder="详情"   :disabled="userDialog.option  == 'detail'"/>
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-input v-model="formData.startDate" placeholder="详情"  :disabled="userDialog.option  == 'detail'"/>
        </el-form-item>
        <el-form-item label="结束每期" prop="endDate">
          <el-input v-model="formData.endDate" placeholder="详情"  :disabled="userDialog.option  == 'detail'"/>
        </el-form-item>
         <!-- <el-form-item label="交办人员" prop="personList">
          <el-select v-model="formData.personList"  clearable  multiple style="width: 90%" size="large" :disabled="userDialog.option  == 'detail'">
              <el-option
                v-for="item in personList"
                :key="item.id"
                :label="item.realName"
                :value="item.id"
              >
                <span style="float: left;line-height:34px;">{{ item.realName }}</span>
                <span style="float: right;color: grey;line-height:34px;" >{{ item.deptName }}</span>
              </el-option>
            </el-select>
         </el-form-item> -->
<!-- 
         <el-form-item label="交办人员" prop="personList">
          <el-tree-select v-model="formData.personList" :data="personList" 
          :render-after-expand="false" multiple show-checkbox style="width: 90%" size="large" :disabled="userDialog.option  == 'detail'"/>
         </el-form-item> -->

      
      </el-form>


      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetForm">取 消</el-button>
          <el-button v-if="userDialog.option === 'add'" type="primary" :disabled="isDisabled.add" @click="addUser()">确 定</el-button>
          <el-button v-if="userDialog.option === 'edit'" type="primary" @click="editUser()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import {dialogTy} from '~/dialog'
import {PropType} from 'vue'
import {getAllPerson} from "@/api/task";

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

const isDisabled = ref({
  add:false 
});

const rules = ref({
   detail: [{required: true, message: '请输入详情', trigger: 'blur'}],
   personList: [{required: true, message: '请选择交办人员', trigger: 'blur'}]
})

const personList  = ref([])

const emits = defineEmits(['userHandleSubmitAdd', 'userHandleSubmitEdit'])

onMounted(() => {
  getPersonList()
})

const getPersonList = () => {
  getAllPerson().then((response) => {
        personList.value = response.data
      })
      .catch((res) => {
    })
}

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
