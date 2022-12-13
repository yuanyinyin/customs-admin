<template>
  <div class="login-main">
    <div class="login-header">
      <div class="lh-main">
        <img class="lh-logo" src="@/assets/main/logo.png" alt="">
        <div class="lh-line"></div>
        <div class="lh-name">登录</div>
      </div>
    </div>
    <div class="login-container">
      <el-form ref="refloginForm" class="login-form" :model="formInline" :rules="formRules">
        <h2 class="lf-title">用户登录</h2>
        <div class="lf-main">

          <div class="lf-item">
            <el-form-item prop="username" :rules="formRules.isNotNull">
              <el-input v-model="formInline.username" placeholder="请输入账号">
                <template #prefix>
                  <svg-icon class="el-input__icon" icon-class="wm-user"/>
                </template>
              </el-input>
            </el-form-item>
          </div>

          <div class="lf-item">
            <el-form-item prop="password" :rules="formRules.isNotNull">
              <el-input
                :key="passwordType"
                ref="refPassword"
                v-model="formInline.password"
                :type="passwordType"
                name="password"
                placeholder="请输入密码"
                @keyup.enter="handleLogin"
              >
                <template #prefix>
                  <svg-icon class="el-input__icon" icon-class="wm-password"/>
                </template>
                <template #suffix>
                      <span class="show-pwd" @click="showPwd">
                        <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
                      </span>
                </template>
              </el-input>
            </el-form-item>
          </div>

          <div class="lf-item">
            <el-form-item prop="vcode" :rules="formRules.isNotNull">
              <el-input v-model="formInline.vcode" placeholder="验证码">
                <template #prefix>
                  <svg-icon class="el-input__icon" icon-class="wm-vcode"/>
                </template>
              </el-input>
            </el-form-item>
            <img class="lf-vcode" src="@/assets/main/vcode.png" alt="">
            <el-link class="lf-link" type="default">换一张</el-link>
          </div>

          <div class="lf-item">
            <el-button :loading="loading" type="primary" class="login-btn" size="default" @click.prevent="handleLogin">登 录</el-button>
          </div>
<!--          <div class="lf-item lf-tip">忘记密码？</div>-->
          <div class="lf-item">
            <div class="lf-center">
              还没有账号？<el-link type="primary" @click.native="goRegister">去注册</el-link>
            </div>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import settings from '@/settings'
import {Search} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'
import {ObjTy} from '~/common'

const baseUrl = ref(import.meta.env.VITE_APP_BASE_URL)

const formRules = useElement().formRules

let formInline = reactive({
  username: '',
  password: '',
})
let state: ObjTy = reactive({
  otherQuery: {},
  redirect: undefined
})

/* listen router change  */
const route = useRoute()
let getOtherQuery = (query: any) => {
  return Object.keys(query).reduce((acc: any, cur: any) => {
    if (cur !== 'redirect') {
      acc[cur] = query[cur]
    }
    return acc
  }, {})
}

watch(
  () => route.query,
  (query) => {
    if (query) {
      state.redirect = query.redirect
      state.otherQuery = getOtherQuery(query)
    }
  },
  {immediate: true}
)

/*
 *  login relative
 * */
let loading = ref(false)
let tipMessage = ref('')
const store = useStore()
const refloginForm: any = ref(null)
let handleLogin = () => {
  refloginForm.value.validate((valid: any) => {
    if (valid) {
      loginReq()
    } else {
      return false
    }
  })
}

//use the auto import from vite.config.js of AutoImport
const router = useRouter()
let loginReq = () => {
  loading.value = true
  store
    .dispatch('user/login', formInline)
    .then(() => {
      ElMessage({message: '登录成功', type: 'success'})
      router.push({path: state.redirect || '/', query: state.otherQuery})
    })
    .catch((res) => {
      tipMessage.value = res.msg
      useCommon()
        .sleep(30)
        .then(() => {
          loading.value = false
        })
    })
}

/*
 *  password show or hidden
 * */
let passwordType = ref('password')
const refPassword: any = ref(null)
let showPwd = () => {
  if (passwordType.value === 'password') {
    passwordType.value = ''
  } else {
    passwordType.value = 'password'
  }
  nextTick(() => {
    refPassword.value.focus()
  })
}


let goRegister = () => {
  router.push({path: '/registerAgree' || '/', query: state.otherQuery})
}

</script>

<style lang="scss" scoped>
$fontColor: #4B5058;
.login-main{
  height: 100vh;
  width: 100%;
  min-height: 600px;
  background: url('@/assets/main/login_bg.jpg') no-repeat center center;
  background-size: cover;
  display: flex;
  flex-direction: column;
}

.login-header{
  flex: 0 0 80px;
  background-color: #fff;
  display: flex;
  justify-content: center;
}

.lh-main{
  width: 1200px;
  height: 100%;
  display: flex;
  align-items: center;
}

.lh-logo{
  display: inline-block;
  height: 50px;
  vertical-align: top;
}

.lh-line{
  height: 50px;
  width: 1px;
  background-color: #707070;
  margin: 0 30px;
}

.lh-name{
  color: $fontColor;
  font-size: 20px;
}

.login-container{
  flex: 1 1 auto;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.login-form{
  padding: 40px;
  margin-left: 40%;
  background-color: rgba($color: #ffffff, $alpha: 0.95);
  box-shadow: 0 10px 10px rgba($color: #000000, $alpha: 0.01);
}

.lf-title{
  padding-bottom: 30px;
  color: #2882FE;
  font-size: 24px;
  text-align: center;
}

.lf-main{
  width: 360px;
  display: flex;
  flex-direction: column;
}

.lf-item{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 0;

  .el-form-item{
    flex: 1 1 auto;
  }

  .el-input {
    height: 38px;
    border: 0px;
    font-size: 14px;
    input {
      height: 38px;
    }
  }

  .el-input__icon {
    height: 100%;
    width: 14px;
    display: flex;
    align-items: center;
    padding: 0 !important;
    margin-left: 2px;
  }
}

.lf-vcode{
  display: inline-block;
  width: 120px;
  height: 38px;
  border-radius: 2px;
  margin: 0px 5px 18px;
}

.lf-link{
  width: 70px;
  text-align: center;
  margin-bottom: 18px;
}

.login-btn {
  width: 100%;
  height: 38px;
  font-size: 16px;
}

.lf-tip{
  color: #666;
}

.lf-center{
  width: 100%;
  text-align: center;
  color: #666;
}
</style>

