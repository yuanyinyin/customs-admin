<template>
  <el-form
    ref="form"
    :model="setForm"
    label-width="auto"
    :label-position="labelPosition"
    :size="size"
  >
    <el-divider content-position="left">报文目录设置</el-divider>
    <el-form-item label="报关单报文目录">
      <el-col :span="11">
        <el-input v-model="setForm.dir_path"/>
      </el-col>
      <el-col :span="11">
        <el-button  size="large" type="primary" @click="selectDir">选取文件夹</el-button>
        <el-button size="large" type="primary" @click="autoGet">自动获取路径</el-button>
      </el-col>
    </el-form-item>
    <el-form-item>
    </el-form-item>
        <el-divider content-position="left">自启动设置</el-divider>
    <el-form-item label="是否开机启动">
      <el-switch
        v-model="setForm.startSet"
        size="large"
        active-text="是"
        inactive-text="否"
      />
    </el-form-item>

    <el-form-item>
    </el-form-item>
    <el-form-item>
    </el-form-item>
    <el-form-item >
      <el-button type="primary" @click="onSubmit">保存</el-button>
      <el-button>取消</el-button>
    </el-form-item>

  </el-form>
</template>

<style>
.el-radio-group {
  margin-right: 12px;
}

.one-block-1 {
  font-size: 16px;
  padding-top: 10px;
}
</style>

<script lang="ts" setup>
import ipcRenderer from "@/utils/ipcRenderer";
import {ElMessage} from "element-plus";
const size = ref('large')
const labelPosition = ref('right')

const setForm = reactive({
  dir_path: '',
  startSet:true,
})

onMounted(() => {
  init();
})
const init = () => {
  queryDir();
  queryStartSet();
}

let queryDir =() =>{
  ipcRenderer.invoke('controller.client.getBgdFileDir', '').then(res => {
    setForm.dir_path = res;
  })
}

let queryStartSet = () => {
  ipcRenderer.invoke('controller.client.getStartSetState', '').then(res => {
      setForm.startSet = res;
  })
}

let autoGet = () => {
  ipcRenderer.invoke('controller.client.autoGetReportDir', '').then(res => {
    if(res&& res.flag){
      setForm.dir_path = res.fileDir;
      ElMessage({message: '自动获取路径成功,请点击保存！', type: 'success'})
    }else{
      ElMessage({message: res.msg, type: 'error'})
    }
  })
}

let onSubmit = () => {
    if (setForm.dir_path.length == 0) {
      ElMessage({message: '请选择报文目录', type: 'warn'})
    }
  const params = {
    bgd_path: setForm.dir_path,
    startSet: setForm.startSet,
  }
  ipcRenderer.invoke('controller.client.saveAllSet', params).then(res => {
    ElMessage({message: "保存成功", type: 'success'})
    ipcRenderer.invoke('controller.client.restartConfirm', '').then(res => {
    })
  })

}

const startSet = ref(true)

let selectDir = () =>
  {
      ipcRenderer.invoke('controller.client.selectFolder', '').then(r => {
      setForm.dir_path = r;
    })
}

</script>


