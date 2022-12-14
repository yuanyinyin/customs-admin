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
      </el-col>
    </el-form-item>

<!--    <el-divider content-position="left">自启动设置</el-divider>
    <el-form-item label="是否开机启动">
      <el-switch
        v-model="value"
        size="large"
        active-text="是"
        inactive-text="否"
      />
    </el-form-item>-->
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
const size = ref('large')
const labelPosition = ref('right')

const setForm = reactive({
  dir_path:'',
})

function onSubmit() {
  console.log('submit!')
}

const value = ref(true)

let selectDir = () =>
  {
      ipcRenderer.invoke('controller.example.selectFolder', '').then(r => {
      setForm.dir_path = r;
    })
}

</script>


