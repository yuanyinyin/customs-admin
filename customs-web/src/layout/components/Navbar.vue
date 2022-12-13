<!--
 * @FileDescription: 主页面- 导航条
 * @Author: Zhou, Zhongqi
 * @Date:
 * @LastEditors: Yang, Botao
 * @LastEditTime: 2022-03-01 22w10
 -->
<template>
  <div class="navbar rowBC">
    <div class="rowSC">
      <hamburger
        v-if="settings.showHamburger"
        :is-active="opened"
        class="hamburger-container"
        @toggleClick="toggleSideBar"
      />
      <breadcrumb class="breadcrumb-container" />
    </div>
    <!--nav title-->
    <div v-if="settings.showNavbarTitle" class="heardCenterTitle">{{ settings.title }}</div>
    <div v-if="settings.ShowDropDown" class="right-menu rowSC">
      <ScreenFull />
      <SizeSelect />
      <LangSelect />

      <el-dropdown trigger="click" size="medium">
        <div class="avatar-wrapper">
          <img
            src="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80"
            class="user-avatar"
          />
          <CaretBottom style="width: 1em; height: 1em; margin-left: 4px" />
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <router-link to="/">
              <el-dropdown-item>{{ $t('navbar.home') }}</el-dropdown-item>
            </router-link>
<!--            <a target="_blank" href="https://github.com/jzfai/vue3-admin-ts">
              <el-dropdown-item>Github</el-dropdown-item>
            </a>
            <a target="_blank" href="https://juejin.cn/post/7036302298435289095">
              <el-dropdown-item>Docs</el-dropdown-item>
            </a>-->

            <!-- 修改当前登录用户的密码 -->
            <el-dropdown-item divided @click="changeUserPassword">
              <span>个人信息</span>
            </el-dropdown-item>

            <!-- 企业信息 -->
            <el-dropdown-item divided @click="showDepInfo">
              <span>企业信息</span>
            </el-dropdown-item>

            <el-dropdown-item divided @click="loginOut">{{ $t('navbar.logOut') }}</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

      <!-- 用户修改密码 Dialog -->
      <ChangePasswordDialog :change-password-dialog="changePasswordDialog" :form-data="formData" />
      <ShowDepInfoDialog :showDepInfoDialog="showDepInfoDialog" :form-data="formData" />
    </div>
  </div>
</template>

<script setup lang="ts">
import SizeSelect from '@/components/SizeSelect/index.vue'
import LangSelect from '@/components/LangSelect/index.vue'
import ScreenFull from '@/components/ScreenFull/index.vue'

import { CaretBottom } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import Breadcrumb from './Breadcrumb'
import Hamburger from './Hamburger'
import { useStore } from 'vuex'
import { Ref } from 'vue'
import { dialogTy } from '~/dialog'
import ChangePasswordDialog from './changePasswordDialog.vue'
import ShowDepInfoDialog from './showDepInfo.vue'

const store = useStore()
const settings = computed(() => {
  return store.state.app.settings
})
const opened = computed(() => {
  return store.state.app.sidebar.opened
})
const toggleSideBar = () => {
  store.commit('app/M_toggleSideBar')
}

/**
 * 用户修改自己密码对话框
 */
const changePasswordDialog: Ref<dialogTy> = ref({
  show: false
}) //ts的规范写法，定义变量的类型

// 密码数据
const formData = ref({}) //这是相对自由点的写法，不定义类型

// '修改密码'按钮触发 标题‘修改用户密码’
const changeUserPassword = () => {
  changePasswordDialog.value = {
    show: true
  }
  formData.value = {
    /** 传向dialog的 params
     * ---------------------
     * | oldPass | newPass |
     * ---------------------
     */
    oldPass: '',
    newPass: ''
  }
}

// 展示企业信息
const showDepInfoDialog: Ref<dialogTy> = ref({
  show: false
}) //ts的规范写法，定义变量的类型

const showDepInfo = () => {
  showDepInfoDialog.value = {
    show: true
  }
  formData.value = {
    /** 传向dialog的 params
     * ---------------------
     * | oldPass | newPass |
     * ---------------------
     */
    // oldPass: '',
    // newPass: ''
  }
}


/*
 * 退出登录
 * */

const loginOut = () => {
  store.dispatch('user/logout').then(() => {
    // ElMessage({ message: '退出登录成功', type: 'success' })
    // proxy.$router.push(`/login?redirect=${proxy.$route.fullPath}`)
    //此处reload清空路由和重置部分状态
    location.reload()
  })
}
</script>

<style lang="scss" scoped>
.navbar {
  height: $navBarHeight;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

//logo
.avatar-wrapper {
  margin-top: 5px;
  position: relative;
  cursor: pointer;

  .user-avatar {
    cursor: pointer;
    width: 40px;
    height: 40px;
    border-radius: 10px;
  }

  .el-icon-caret-bottom {
    cursor: pointer;
    position: absolute;
    right: -20px;
    top: 25px;
    font-size: 12px;
  }
}

//center-title
.heardCenterTitle {
  text-align: center;
  position: absolute;
  top: 50%;
  left: 46%;
  font-weight: 600;
  font-size: 20px;
  transform: translate(-50%, -50%);
}

//drop-down
.right-menu {
  cursor: pointer;
  margin-right: 40px;
}
</style>
