import { createApp } from 'vue'
import App from './App.vue'
const app = createApp(App)
import router from './router'
import '@/styles/index.scss'
//import vuex
import store from './store'
app.use(store)

//import element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//import zhCn from 'element-plus/es/locale/lang/zh-cn'
//app.use(ElementPlus, { size: 'small', locale: zhCn })

import settings from '@/settings'
import zh from 'element-plus/es/locale/lang/zh-cn'
import en from 'element-plus/es/locale/lang/en'
const lang = { zh, en }
app.use(ElementPlus, {
  size: localStorage.getItem('size') || settings.defaultSize,
  locale: lang[localStorage.getItem('language') || settings.defaultLanguage]
})
//i18n
import i18n from '@/lang'
app.use(i18n)

//svg-icon
import 'virtual:svg-icons-register'
import svgIcon from '@/icons/SvgIcon.vue'
app.component('SvgIcon', svgIcon)

//import global directive
import directive from '@/directive'
directive(app)

//import router  intercept
import './permission'

//element svg icon
import ElSvgIcon from "@/components/ElSvgIcon.vue"
app.component("ElSvgIcon",ElSvgIcon)

//error log  collection
import errorLog from '@/hooks/useErrorLog'
errorLog()

//electron ipcRenderer
import IpcRenderer from '@/utils/ipcRenderer'
app.use(IpcRenderer)

app.use(router).mount('#app')
