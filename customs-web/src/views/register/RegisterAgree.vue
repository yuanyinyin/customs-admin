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

  <div class="agreeDiv">
    <div class="agreeTitle">注册协议</div>
    <div class="fgx"></div>
    <div class="agreeContent">
      <div class="detail">这是第一行</div>
      <div class="detail">这是第二行</div>
      <div class="detail">这是第三行</div>
      <div class="detail">这是第四行</div>
      <div class="detail">【特别提醒】</div>
      <div class="detail"><a @click="wmbTip" style="color: #20a0ff">&laquo外贸帮服务条款&raquo</a></div>
      <div class="detail"><a @click="ysTip" style="color: #20a0ff">&laquo隐私声明&raquo</a></div>
    </div>
    <div>
      <el-button type="warning" class="agree-btn" size="default" @click.prevent="handleAgree">
        同 意 协 议
      </el-button>
    </div>
  </div>


</template>

<script setup lang="ts">
import { ElMessage, ElMessageBox } from 'element-plus'
import {ObjTy} from '~/common'

const baseUrl = ref(import.meta.env.VITE_APP_BASE_URL)


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

const router = useRouter()
let handleAgree = () => {
  router.push({path: '/register' || '/', query: state.otherQuery})
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

.agreeDiv {
  width: 1200px;
  margin: 20px auto;
  border: 1px solid #6c757d;
  .agreeTitle {
    font-size: 24px;
    color: #4d5154;
    margin: 10px 60px;
    font-weight: bold;
  }
  .fgx{
    margin: 10px 60px;
    border-bottom: 1px solid #afb3b6;
  }

  .agreeContent{
    margin: 10px 60px;
    font-size: 16px;
    color: #7c848c;

    .detail {
      margin-bottom: 20px;
    }
  }
}

.agree-btn {
  width: 15%;
  text-align: center;
  height: 46px;
  line-height: 46px;
  font-size: 20px;
  border-radius: 0px;
  margin: 20px auto 10px auto;
  display: flex;
  background-image: linear-gradient(#F3C475FF, #ee9f1a,#EE9F1AFF);
}
</style>

