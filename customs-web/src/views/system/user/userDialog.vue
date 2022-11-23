<!--
 * @FileDescription: 用户管理页面- 修改用户信息对话框
 * @Author: Zhou, Zhongqi
 * @Date:
 * @LastEditors: Yang, Botao
 * @LastEditTime: 2022-02-28 22w10
 -->
<template>
  <div>
    <el-dialog v-model="userDialog.show" :title="userDialog.title" width="30%" center>
      <el-form ref="refUserForm" :model="formData" :rules="rules" label-width="80px">
        <el-form-item v-if="userDialog.option === 'edit'" v-show="false" label="用户id" prop="id"/>
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="formData.realName" placeholder="姓名" style="width: 90%"/>
        </el-form-item>
        <el-form-item label="登录账号" prop="userName">
          <el-input v-model="formData.userName" placeholder="登录账号" style="width: 90%"/>
        </el-form-item>

        <!--密码编辑框显示条件-->
        <el-form-item v-if="userDialog.option === 'add'" label="登录密码" prop="userPasswordAdd">
          <el-input v-model="formData.userPassword" type="password" placeholder="登录密码" style="width: 90%" />
        </el-form-item>
        <!-- <el-form-item v-if="userDialog.option === 'edit'" label="登录密码" prop="userPasswordEdit">
          <el-input v-model="formData.userPassword" type="password" placeholder="********" style="width: 90%" />
        </el-form-item> -->

        <el-form-item label="手机号码" prop="telephone">
          <el-input v-model="formData.telephone" placeholder="手机号码" type="phone" style="width: 90%"/>
        </el-form-item>

        <el-form-item label="部门" prop="deptId" >
           <el-tree-select style="width: 90%"
          v-model="formData.deptId"
          :data="deptData" :props="propsDept"
          :label="propsDept.label"
          :value="propsDept.value"
          check-strictly
          :render-after-expand="false"
        />
        </el-form-item>


        <el-form-item label="职务" prop="job">
          <el-input v-model="formData.job" placeholder="职务"  style="width: 90%"/>
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
import {verifyUserName} from '@/api/user'
import {getDept} from "@/api/dept";

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

const propsDept = {
  label: 'deptName',
  value: 'id'
}

const deptData = ref([])
onMounted(() => {
  getDeptList()
})

const getDeptList = () => {
  getDept().then((response) => {
        deptData.value = response.data
      })
      .catch((res) => {
    })
}

const validateRealName = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入姓名'))
  } else {
    /**
     * 验证姓名：
     * 汉字 即 /^[\u4E00-\u9FA5]$/
     * 名字长度 2~10位（根据使用场景改变）即 {2,10}
     * 可能有译名 ·汉字 即 (·[\u4E00-\u9FA5]{2,10}){0,2}
     */
    const isRealName = /^[\u4E00-\u9FA5]{2,10}(·[\u4E00-\u9FA5]{2,10}){0,2}$/
    if (!isRealName.test(value)) {
      callback(new Error('不符合姓名格式'))
    } else {
      callback()
    }
  }
}

// 校验账号名是否已存在
const validateUserNameRepeat = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入登录账号'))
  } else {
    // 数据库中查询登录名
    verifyUserName(props.formData).then((response) => {
      if (response.data === 'exists') {
        callback(new Error('该登录用户名已经存在，不能重复'))
      } else {
        callback()
      }
    })
  }
}

// 校验密码强度是否过低
const validatepassword = (rule, value, callback) => {
  if (props.formData.userPassword == undefined) {
    callback()
  } else if (props.formData.userPassword === '') {
    callback(new Error('请输入登录密码'))
  } else {
    // 校验密码强度
    const num = /^.*[0-9]+.*/
    const low = /^.*[a-z]+.*/
    const up = /^.*[A-Z]+.*/
    const spe = /^.*[^a-zA-Z0-9]+.*/
    const passLength = props.formData.userPassword.length > 7 && props.formData.userPassword.length < 21
    if (num.test(props.formData.userPassword) && (low.test(props.formData.userPassword) || up.test(props.formData.userPassword)) && passLength) {
      callback()
    } else {
      callback(new Error('密码需要8-20个字符，必须包含小写字母、大写字母、数字和特殊字符。'))
    }
  }
}

// 校验手机号格式
const validateTelephone = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入手机号码'))
  } else {
    // 验证手机号码
    const isPhone = /^(13[0-9]|14[014-9]|15[0-35-9]|16[25-7]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
    if (!isPhone.test(value)) {
      callback(new Error('不符合手机号码格式'))
    } else {
      callback()
    }
  }
}

const rules = ref({
  realName: [{required: true, validator: validateRealName, trigger: 'blur'}],
  userName: [{required: true, validator: validateUserNameRepeat, trigger: 'blur'}],
  telephone: [{required: true, validator: validateTelephone, trigger: 'blur'}]
})

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
}
</script>
