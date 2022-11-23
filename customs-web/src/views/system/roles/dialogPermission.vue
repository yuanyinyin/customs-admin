<!--
 * @FileDescription: 角色管理页面- 授予用户角色身份对话框
 * @Author: yyy
 * @Date: 2022-02-14 22w08
 * @LastEditors:
 * @LastEditTime:
 -->
<template>
  <div>
    <el-dialog
        v-model="permissionsDialog.show"
        :title="permissionsDialog.title"
        width="450px"
        center
        @open="openDialog"
        @close="closeDialog"
        destroy-on-close
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        :modal-append-to-body="false"
    >
      <el-tree
          ref="treeRef"
          :data="data"
          default-expand-all
          show-checkbox
          node-key="id"
          :default-checked-keys="defaultKey"
          :props="defaultProps"
          :check-strictly="true"
      />
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeDialog">取消</el-button>
          <el-button type="primary" @click="giveRules">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import {getAllmenus, listRoleMenuTreeSq} from '@/api/roles'
import {PropType} from 'vue'
import {dialogTy} from '~/dialog'

const store = useStore()

//定义组件的属性
const props = defineProps({
  permissionsDialog: {
    require: true,
    default: null,
    type: Object as PropType<dialogTy>
  }
})

let data = ref([])
const treeRef: any = ref(null)
let defaultKey = ref([])
const defaultProps = {
  label: 'title'
}
const openDialog = () => {
  getAllmenus().then((res) => {
    data.value = JSON.parse(res.data)
  })
  let id = props.permissionsDialog.option
  listRoleMenuTreeSq(id).then((res) => {
    const arr = res.data
    defaultKey.value = arr
    console.log(arr)
  })
}

const emit = defineEmits(['giveRules'])
const giveRules = () => {
  const keys = [...treeRef.value.getCheckedKeys(), ...treeRef.value.getHalfCheckedKeys()]
  console.log(keys)
  let idStr = keys.join(',')
  if (idStr === '') {
    idStr = ' '
  }
  emit('giveRules', idStr)
}

const closeDialog = () => {
  props.permissionsDialog.show = false
  defaultKey.value = []
}
</script>
