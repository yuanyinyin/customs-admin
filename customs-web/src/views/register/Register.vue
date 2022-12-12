<template>
  <!-- 顶部蓝色条 -->
  <div class="topHead"></div>
  <!-- 标题 -->
  <div class="topTitleDiv">
    <div class="topTitleSub">
      <span class="imgSpan"><img src="/rm/favicon.ico"></span>
      <span class="wmbFont">外<span style="margin-left: 2px;">贸</span><span style="margin-left: 2px;">帮</span></span>
      <span class="sxFont">|</span>
      <span class="zcFont">账户注册</span>
    </div>
    <div class="bottomTitleSub">
      <div class="bottomTitleDiv">
        <span class="svg"><svg-icon class="el-input__icon" icon-class="international"/></span>
        <span class="qyzcFont">企业账户注册</span>
      </div>
    </div>
  </div>

  <!-- 表单 -->
  <div class="register-container columnCC">
    <el-form ref="refRegisterForm" class="register-form" :model="formInline" :rules="formRules"
             label-position="right" label-width="145px">
      <el-form-item label="单位全称" prop="orgNameCn" :rules="formRules">
        <el-input v-model="formInline.orgNameCn" placeholder="单位全称"></el-input>
      </el-form-item>

      <el-form-item label="统一社会信用代码" prop="unionNo" :rules="formRules">
        <el-input v-model="formInline.unionNo" placeholder="统一社会信用代码"></el-input>
      </el-form-item>

      <el-form-item label="所在地区" prop="areaName" :rules="formRules">
        <el-select v-model="formInline.areaName" placeholder="请选择所在地区" style="width: 100%;" @change="selectArea($event)">
          <el-option v-for="area in areaData" :key="area.id" :label="area.name" :value="area"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="管理员账户名" prop="userName" :rules="formRules">
        <el-input v-model="formInline.userName" placeholder="管理员账户名"></el-input>
      </el-form-item>

      <el-form-item label="真实姓名" prop="realName" :rules="formRules.isNotNull">
        <el-input v-model="formInline.realName" placeholder="真实姓名"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password" :rules="formRules">
        <el-input v-model="formInline.password" type="password" placeholder="密码"></el-input>
      </el-form-item>

      <el-form-item label="确认密码" prop="rePassword" :rules="formRules">
        <el-input v-model="formInline.rePassword" type="password" placeholder="确认密码"></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="telephone" :rules="formRules">
        <el-input v-model="formInline.telephone" placeholder="手机号"></el-input>
      </el-form-item>

      <el-form-item label="手机验证码" prop="verifyCode" :rules="formRules">
        <el-input v-model="formInline.verifyCode" placeholder="手机验证码" style="width: 50%;" @blur="blurCheckCode"></el-input>
        <el-button style="margin-left: 10px;width: 46%;height: 36px;" :disabled="!show" @click="sendVerifyCode">
          获取验证码
          <span v-show="!show" class="count">({{count}}s)</span>
        </el-button>

      </el-form-item>

      <el-form-item :label="'组织机构代码\n附件图片上传'" prop="" style="white-space: pre-line!important;">
        <!--  action是后端文件上传路径    -->
        <el-upload
          v-model:file-list="fileList"
          :action="baseUrl + '/file/upload'"
          multiple
          :on-preview="handlePreview"
          :on-success="handleUploadSuccess"
          :on-remove="handleRemove"
          :on-exceed="handleExceed"
          style="margin-top: 10px;"
        >
          <el-button :icon="Link" style="height: 36px;width: 174px;">
            附件上传
          </el-button>
        </el-upload>
      </el-form-item>

      <el-checkbox v-model="agreeFlag">创建网站账号的同时，我同意遵守：
        <a @click="wmbTip" style="color: #20a0ff">&laquo外贸帮服务条款&raquo</a>
        及
        <a @click="ysTip" style="color: #20a0ff">&laquo隐私声明&raquo</a>
      </el-checkbox>

      <el-button :loading="loading" type="warning" class="register-btn" size="default" @click.prevent="handleRegister" :disabled = "!agreeFlag">
        提 交
      </el-button>
      <el-button :loading="loading" type="warning" class="register-btn" size="default" @click.prevent="initForm">
        赋 值
      </el-button>
    </el-form>
  </div>
</template>

<script setup lang="ts">

import {Link} from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {ObjTy} from '~/common'
import {changeUserPassword, verifyUserName} from "@/api/user";
import {registerDepUser} from "@/api/login";

const baseUrl = ref(import.meta.env.VITE_APP_BASE_URL)
//勾选协议标志
const agreeFlag = ref(false)

//todo 所在地区
/*
江苏省--南通市--市辖区	320601
江苏省--南通市--崇川区	320602
江苏省--南通市--港闸区	320611
江苏省--南通市--通州区	320612
江苏省--南通市--海安县	320621
江苏省--南通市--启东市	320681
江苏省--南通市--如皋市	320682
江苏省--南通市--海门市	320684
江苏省--南通市--如东县	320623
*/
const areaData = ref([
  {"name":"江苏省--南通市--市辖区","id":"320601"},
  {"name":"江苏省--南通市--崇川区","id":"320602"},
  {"name":"江苏省--南通市--港闸区","id":"320611"},
  {"name":"江苏省--南通市--通州区","id":"320612"},
  {"name":"江苏省--南通市--海安县","id":"320621"},
  {"name":"江苏省--南通市--启东市","id":"320681"},
  {"name":"江苏省--南通市--如皋市","id":"320682"},
  {"name":"江苏省--南通市--海门市","id":"320684"},
  {"name":"江苏省--南通市--如东县","id":"320623"},
])
const show = ref(true)
const TIME_COUNT = 60
const timer = ref()
const count = ref()
//todo 发送验证码
const sendVerifyCode = () => {
  if (formInline.telephone == '') {
    ElMessage({message: '请填写手机号', type: 'error'})
  } else {
    if (!timer.value) {
      count.value = TIME_COUNT
      show.value = false
      timer.value = setInterval(() => {
        if (count.value > 0 && count.value <= TIME_COUNT) {
          count.value--
        } else {
          show.value = true
          clearInterval(timer.value) // 清除定时器
          timer.value = null
        }
      }, 1000)
    }

    //todo 这里去发送验证码
  }
}

//todo 失去焦点校验验证码
const blurCheckCode = () => {

}

const fileList = ref([])
const upload = ref()
const handleExceed = (files) => {
  upload.value.clearFiles()
  upload.value.handleStart(files[0])
}
//文件删除
const handleRemove = uploadFile => {
  fileList.value.forEach((v, i) => {
    if (v.uid == uploadFile.uid) {
      fileList.value.splice(i, 1)
    }
  })
}

//文件上传成功
const handleUploadSuccess = (response, file) => {
  file.uid = response.data
}

//文件预览
const handlePreview = uploadFile => {
  var a = document.createElement('a')
  a.setAttribute('href', baseUrl.value + '/file/downLoad/' + uploadFile.uid)
  document.body.appendChild(a)
  a.click()
}

const wmbTip = () => {
  ElMessageBox.alert('这是外贸帮服务条款信息，这是外贸帮服务条款信息这是外贸帮服务条款信息这是外贸帮服务条款信息这是外贸帮服务条款信息这是外贸帮服务条款信息这是外贸帮服务条款信息这是外贸帮服务条款信息', '外贸帮服务条款', {
    confirmButtonText: '确认',
    callback: () => {

    },
  })
}

const ysTip = () => {
  ElMessageBox.alert('这里写隐私声明信息', '隐私声明', {
    confirmButtonText: '确认',
    callback: () => {

    },
  })
}

// 校验账号名是否已存在
const validateUserNameRepeat = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入登录账号'))
  } else {
    // 数据库中查询登录名
    callback()
  }
}

// 校验密码强度是否过低
const validatePassword = (rule, value, callback) => {
  if (formInline.password == undefined) {
    callback()
  } else if (formInline.password === '') {
    callback(new Error('请输入登录密码'))
  } else {
    // 校验密码强度
    const num = /^.*[0-9]+.*/
    const low = /^.*[a-z]+.*/
    const up = /^.*[A-Z]+.*/
    const passLength = formInline.password.length > 7 && formInline.password.length < 21
    if (num.test(formInline.password) && (low.test(formInline.password) || up.test(formInline.password)) && passLength) {
      callback()
    } else {
      callback(new Error('密码需要8-20个字符，必须包含小写、大写字母、数字和特殊字符'))
    }
  }
}

const validatePasswordRepeat = (rule, value, callback) => {
  if (value !== formInline.password) {
    callback(new Error('确认密码和密码不一致'))
  }else{
    callback();
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

// const formRules = useElement().formRules
const formRules = ref({
  isNotNull: [{required: true, message: '该字段不能为空', trigger: 'blur'}],
  // userName: [{required: true, validator: validateUserNameRepeat, trigger: 'blur'}],
  // telephone: [{required: true, validator: validateTelephone, trigger: 'blur'}],
  userPasswordAdd: [{required: true, validator: validatePassword, trigger: 'blur'}],
  length18: [{min: 18, max: 18, message: '长度为18个字符', trigger: 'blur'}],
  validPasswordRepeat:[{required: true, validator: validatePasswordRepeat, trigger: 'blur'}],

  orgNameCn: [{required: true, message: '该字段不能为空', trigger: 'blur',validator: validateOrgNameCn}],
  unionNo:[
    {required: true, message: '该字段不能为空', trigger: 'blur'},
    {min: 18, max: 18, message: '长度为18个字符', trigger: 'blur'}
  ],
  areaName:[{required: true, message: '该字段不能为空', trigger: 'blur'}],
  userName: [{required: true, validator: validateUserNameRepeat, trigger: 'blur'}],
  realName: [{required: true, message: '该字段不能为空', trigger: 'blur'}],
  password:[
    {required: true, message: '该字段不能为空', trigger: 'blur'},
    {required: true, validator: validatePassword, trigger: 'blur'}
  ],
  rePassword:[
    {required: true, message: '该字段不能为空', trigger: 'blur'},
    {required: true, validator: validatePasswordRepeat, trigger: 'blur'}
  ],
  telephone: [{required: true, validator: validateTelephone, trigger: 'blur'}],
  verifyCode: [{required: true, message: '该字段不能为空', trigger: 'blur'}],
})
//表单数据
let formInline = reactive({
  orgNameCn: '',
  unionNo: '',
  areaName: '',
  areaCode: '',
  telephone: '',
  userName: '',
  realName: '',
  password: '',
  rePassword: '',
  verifyCode: ''
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


let loading = ref(false)
let tipMessage = ref('')
const store = useStore()
const refRegisterForm: any = ref(null)
let handleRegister = () => {
  refRegisterForm.value.validate((valid: any) => {
    console.log(valid);
    if (valid) {
      registerReq()
    } else {
      return false
    }
  })
}

//use the auto import from vite.config.js of AutoImport
const router = useRouter()
let registerReq = () => {
  loading.value = true
  //todo 这里是注册请求，请求体是表单内容
  // store
  //   .dispatch('login/login', formInline)
  //   .then(() => {
  //     ElMessage({message: '注册成功', type: 'success'})
  //   })
  //   .catch((res) => {
  //     tipMessage.value = res.msg
  //     useCommon()
  //       .sleep(30)
  //       .then(() => {
  //         loading.value = false
  //       })
  //   })
  registerDepUser(formInline).then(res => {
    console.log(res);
    if (res.code==200){
      ElMessage({message: '注册成功，请登录！', type: 'success'})
      // store.dispatch('user/logout').then(() => {
      //   //此处reload清空路由和重置部分状态
      //   location.reload()
      // })
      window.setTimeout(function(){
        router.push({path: state.redirect || '/', query: state.otherQuery})
      },1000);
    }else{
      ElMessage({message: '注册失败，请联系管理员！', type: 'fail'})
      loading.value = false;
    }
  })
}

const selectArea = (item) => {
  console.log(item);
  console.log(formInline);
  formInline.areaCode = item.id
  formInline.areaName = item.name
}

let initForm = () => {
  formInline.orgNameCn='testorg0001';
  formInline.unionNo='111111111111111111';
  formInline.userName='testuser0001';
  formInline.realName='testuser0001';
  formInline.password='88888888a';
  formInline.rePassword='88888888a';
  formInline.telephone='18811111111';
  formInline.verifyCode='123';
}

</script>

<style lang="scss" scoped>
$dark_gray: #889aa4;
.columnCC {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.topHead {
  width: 100%;
  height: 8px;
  background-color: #007bff;
}

.topTitleDiv {
  width: 100%;
  height: 140px;
  border-bottom: 1px solid #bbb9b9;
  .topTitleSub {
    line-height: 110px;
    height: 110px;
    .imgSpan {
      vertical-align:middle;
      //margin-left: 30%;
      margin-left: 30%;
    }
    img {
      width: 50px;
    }
    .wmbFont {
      font-weight: bold;
      font-size: 28px;
      line-height: 120px;
      height: 12vh;
      margin-left: 10px;
      color: #434649;
    }
    .sxFont {
      margin-left: 20px;
      height: 4vh;
      font-size: 40px;
      color: #969698;
    }
    .zcFont {
      margin-left: 24px;
      font-size: 18px;
      color: #717172;
    }
  }

  .bottomTitleSub {
    width: 300px;
    height: 30px;
    border-bottom: 2px solid #007bff;
    margin: 0 auto;
    .bottomTitleDiv {
      width: 150px;
      margin: 0 auto;
      font-size: 16px;
      cursor: pointer;
      .svg {
        color: #007bff;
      }
      .qyzcFont {
        margin-left: 10px;
      }
    }
  }
}

.register-container{
  width: 100%;
  margin: 0px auto;
  .register-form {
    //border-radius: 6px;
    border: 0;
    background: #ffffff;
    width: 550px;
    padding: 25px 25px 5px 25px;

    .el-input {
      height: 36px;
      border: 0px;

      input {
        height: 36px;
      }
    }
  }
}

.register-btn {
  width: 30%;
  text-align: center;
  height: 46px;
  line-height: 46px;
  font-size: 20px;
  border-radius: 0px;
  margin: 20px auto 25px auto;
  display: flex;
  background-image: linear-gradient(#F3C475FF, #ee9f1a,#EE9F1AFF);
}
</style>

