<!--
 * @FileDescription: 主页面- 修改用户密码对话框
 * @Author: Zhou, Zhongqi
 * @Date:
 * @LastEditors: Yang, Botao
 * @LastEditTime: 2022-03-01 22w10
 -->

<template>
  <div>
    <el-dialog
      title="修改用户密码"
      v-model="changePasswordDialog.show"
      center
      width="40%"
    >
      <el-form ref="refForm" :model="formData" :rules="rules" label-width="100px">

        <el-form-item label="原密码" prop="oldPass">
          <el-input v-model="formData.oldPass" type="password" placeholder="请输入原密码" style="width:90%;" />
        </el-form-item>
        <el-form-item label="新密码" prop="newPass">
          <el-input v-model="formData.newPass" type="password" placeholder="请输入新密码" style="width:90%;" />
        </el-form-item>
        <el-form-item label="确认新密码" prop="newPassAgain">
          <el-input v-model="formData.newPassAgain" type="password" placeholder="请再次输入新密码" style="width:90%;" />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="props.changePasswordDialog.show = false">取 消</el-button>
          <el-button type="primary" @click="changePass()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>

import { PropType } from "vue/dist/vue";
import { dialogTy } from "~/dialog";

import { changeUserPassword, verifyUserPassword } from "@/api/user";
import { ElMessage } from "element-plus/es";

const store = useStore()

//定义组件的属性
const props = defineProps({
  changePasswordDialog: {
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

const refForm: any = ref(null)

// 校验密码强度是否过低
const validatePassword = (rule, value, callback) => {
  if (value !== '') {
    // 校验密码强度
    const num = /^.*[0-9]+.*/
    const low = /^.*[a-z]+.*/
    const up = /^.*[A-Z]+.*/
    const spe = /^.*[^a-zA-Z0-9]+.*/
    const passLength = value.length > 7 && value.length < 21
    const initPass = /^123456aB/
    // ————————————————
    // 版权声明：本文为CSDN博主「吖」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
    // 原文链接：https://blog.csdn.net/qq_43721016/article/details/120053208
    if(initPass.test(value)) {
      callback(new Error('新密码中请不要包含初始密码'))
    } else if (props.formData.oldPass === value) {
      callback(new Error('新密码中请不要与原密码完全相同'))
    } else if (num.test(value) && (low.test(value) || up.test(value)) && passLength) {
      callback()
    } else {
      callback(new Error('密码需要8-20个字符，必须包含数字、字母（区分大小写），可以包含特殊字符。'))
    }
  } else callback(new Error('请输入新密码'))
}
const verifyOldPass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户旧密码'))
  } else {
    // 是否在失去焦点时立即校验旧密码是否正确
    const verifyImmediately = true
    if (verifyImmediately) {
      // 数据库中查询登录名
      verifyUserPassword(props.formData).then(response => {
        if (response.data === 'incorrect') {
          callback(new Error('密码错误，请重新输入'))
        } else {
          callback()
        }
      })
    } else {
      validatePassword(rule, value, callback)
    }
  }
}

// 校验新密码再次输入是否一致
const validateNewPassAgain = (rule, value, callback) => {
  if (value === props.formData.newPass) {
    callback()
  } else {
    callback(new Error('与之前输入的密码不一致，请重新输入'))
  }
}

const rules = ref({
  oldPass: [{ required: true, validator: verifyOldPass, trigger: 'blur' }],
  newPass: [{ required: true, validator: validatePassword, trigger: 'blur' }],
  newPassAgain: [{ required: true, validator: validateNewPassAgain, trigger: 'blur' }]
})

// ’确定‘按钮修改密码
const changePass = () => {
  refForm.value.validate((valid) => {
    if (valid) {
      /**
       * function changeUserPassword(data)
       * @param data [用户密码数据集]
       */
      changeUserPassword(props.formData).then(res => {
        props.changePasswordDialog.show = false
        ElMessage({message: '修改密码成功，请重新登录！', type: 'success'})
        var t
        clearTimeout(t)
        console.log('3秒后登出')
        t = setTimeout(function() {
          store.dispatch('user/logout').then(() => {
            //此处reload清空路由和重置部分状态
            location.reload()
          })
        }, 3000)
      })
    }
    else return false
  })
}

</script>

<style>
</style>
