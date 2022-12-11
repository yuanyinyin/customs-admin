<!--
 -->

<template>
  <div>
    <el-dialog
      title="企业基本信息"
      v-model="showDepInfoDialog.show"
      center
      width="40%"
      @open="openFun"
    >
      <el-form ref="refForm" :model="formData" :rules="rules" label-width="160px">
        <el-form-item label="单位全称" prop="ORG_NAME_CN">
          <el-input v-model="formData.ORG_NAME_CN" type="input" placeholder="请输入单位全称" style="width:90%;" />
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="UNION_NO">
          <el-input v-model="formData.UNION_NO" type="input" placeholder="请输入统一社会信用代码" style="width:90%;" />
        </el-form-item>
        <el-form-item label="所在地区" prop="AREA_NAME">
          <el-input v-model="formData.AREA_NAME" type="input" placeholder="请输入所在地区" style="width:90%;" />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="props.showDepInfoDialog.show = false">返 回</el-button>
<!--          <el-button type="primary" @click="changePass()">确 定</el-button>-->
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>

import { PropType } from "vue/dist/vue";
import { dialogTy } from "~/dialog";
import {getNtPtlLoginDep} from "@/api/login";

// import { changeUserPassword, verifyUserPassword } from "@/api/user";
// import { ElMessage } from "element-plus/es";
// import {getNtPtlLoginUser} from '@/api/login';

const formRules = useElement().formRules;//自定义校验规则  继承自useElement.ts  父继承自element-plus

//定义组件的属性
const props = defineProps({
  showDepInfoDialog: {
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

const rules = ref({
  // oldPass: [{ required: true, validator: verifyOldPass, trigger: 'blur' }],
  // newPass: [{ required: true, validator: validatePassword, trigger: 'blur' }],
  // newPassAgain: [{ required: true, validator: validateNewPassAgain, trigger: 'blur' }],
})

// ’确定‘按钮修改密码
// const changePass = () => {
//   refForm.value.validate((valid) => {
//     if (valid) {
//       /**
//        * function changeUserPassword(data)
//        * @param data [用户密码数据集]
//        */
//       changeUserPassword(props.formData).then(res => {
//         props.showDepInfoDialog.show = false
//         // ElMessage({message: '修改密码成功，请重新登录！', type: 'success'})
//         ElMessage({message: '修改信息成功，请重新登录！', type: 'success'})
//         var t
//         clearTimeout(t)
//         console.log('3秒后登出')
//         t = setTimeout(function() {
//           store.dispatch('user/logout').then(() => {
//             //此处reload清空路由和重置部分状态
//             location.reload()
//           })
//         }, 3000)
//       })
//     }
//     else return false
//   })
// }

// const formData = ref([])
//查询用户信息
const openFun = () => {
  getNtPtlLoginDep().then((res) => {
    Object.assign(props.formData, res.data);
    // formData.value = res.data;
  })
}

</script>

<style>
</style>
