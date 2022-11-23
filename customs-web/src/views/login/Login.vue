<template>
  <div class="login-container columnCC">
    <el-form ref="refloginForm" class="login-form" :model="formInline" :rules="formRules">
      <div class="title-container">
        <h3 class="title text-center">{{ settings.title }}</h3>
      </div>
      <el-form-item prop="username" :rules="formRules.isNotNull">
        <el-input v-model="formInline.username" placeholder="用户名">
          <template #prefix>
            <!--   vue3图标使用方式  -->
            <svg-icon style="padding-bottom:5px;" class="el-input__icon" icon-class="user"/>
          </template>
        </el-input>
      </el-form-item>
      <!--<el-form-item prop="password" :rules="formRules.passwordValid">-->
      <el-form-item prop="password" :rules="formRules.isNotNull">
        <el-input
            :key="passwordType"
            ref="refPassword"
            v-model="formInline.password"
            :type="passwordType"
            name="password"
            placeholder="密码"
            @keyup.enter="handleLogin"
        >
          <template #prefix>
            <svg-icon style="padding-bottom:5px;" class="el-input__icon" icon-class="password"/>
          </template>
          <template #suffix>
                <span class="show-pwd" @click="showPwd">
                  <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
                </span>
          </template>
        </el-input>

      </el-form-item>
      <div class="tip-message">{{ tipMessage }}</div>
      <el-button :loading="loading" type="primary" class="login-btn" size="default" @click.prevent="handleLogin">
        登 录
      </el-button>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import settings from '@/settings'
import {Search} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'
import {ObjTy} from '~/common'

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
</script>

<style lang="scss" scoped>
$dark_gray: #889aa4;
.login-container {
  height: 100vh;
  width: 100%;
  background-image: url("../../assets/bg1.png");
  background-size: cover;

  .login-form {
    border-radius: 6px;
    background: #ffffff;
    width: 400px;
    padding: 25px 25px 5px 25px;

    .el-input {
      height: 38px;
      border: 0px;

      input {
        height: 38px;
      }
    }

    .input-icon {
      height: 39px;
      width: 14px;
      margin-left: 2px;
    }
  }

  .login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }

  .login-code {
    width: 33%;
    height: 38px;
    float: right;

    img {
      cursor: pointer;
      vertical-align: middle;
    }
  }

  .el-login-footer {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 1px;
  }

  .login-code-img {
    height: 38px;
  }

  .title-container {
    .title {
      font-size: 22px;
      color: #707070;
      margin: 0px auto 25px auto;
      text-align: center;
      font-weight: bold;
    }
  }
}

.svg-container {
  padding-left: 6px;
  color: $dark_gray;
  text-align: center;
  width: 30px;
}

//错误提示信息
.tip-message {
  color: #e4393c;
  height: 30px;
  margin-top: -12px;
  font-size: 12px;
}

//登录按钮
.login-btn {
  width: 100%;
  margin-bottom: 30px;
}

.show-pwd {
  padding: 6px;
  width: 40px;
  font-size: 16px;
  color: $dark_gray;
}
</style>

