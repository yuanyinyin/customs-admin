<template>
  <div>
    <el-dialog v-model="dialogMenu.show" :title="dialogMenu.title" width="40%" center @open="openFun">
      <el-form ref="refMenuForm" :model="formData" label-width="80px" :rules="rules">
        <el-form-item label="菜单类型">
          <el-radio-group v-model="formData.type">
            <el-radio-button label="1" :disabled="formData.type != '1' && dialogMenu.option == 'edit'">
              目录
            </el-radio-button>
            <el-radio-button label="2" :disabled="formData.type != '2' && dialogMenu.option == 'edit'">
              菜单
            </el-radio-button>
            <el-radio-button label="3" :disabled="formData.type != '3' && dialogMenu.option == 'edit'">
              按钮
            </el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="formData.type != 3" label="设置图标" prop="icon" style="width: 50%">
          <el-input v-model="formData.icon" placeholder="请设置图标"/>
          <e-icon-picker v-model="formData.icon" :options="options" style="width: 76%"/>
          <icon-selector v-model="formData.icon" placeholder="请输入菜单图标"/>
        </el-form-item>
        <div v-if="formData.type != 3" class="flex" style="display: flex">
          <el-form-item label="是否可见">
            <el-radio-group v-model="formData.hidden">
              <el-radio-button label="1">是</el-radio-button>
              <el-radio-button label="0">否</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="目录标题" style="margin-left: 15px" prop="title">
            <el-input v-model="formData.title" placeholder="请输入标题"/>
          </el-form-item>
        </div>
        <div v-if="formData.type != 3" class="flex" style="display: flex">
          <el-form-item label="路由地址" prop="path">
            <el-input v-model="formData.path" placeholder="路由地址(/path)"/>
          </el-form-item>
          <el-form-item label="菜单排序" style="margin-left: 15px" prop="sort">
            <el-input-number v-model="formData.sort" placeholder="请排序" controls-position="right"/>
          </el-form-item>
        </div>
        <div v-if="formData.type == 2" class="flex" style="display: flex">
          <el-form-item label="组件名称" prop="name">
            <el-input v-model="formData.name" placeholder="请输入组件name"/>
          </el-form-item>
          <el-form-item label="组件路径" style="margin-left: 15px" prop="component">
            <!-- <el-input v-model="formData.component" placeholder="请输入组件路径" /> -->
            <el-select v-model="formData.component" filterable placeholder="选择组件">
              <el-option v-for="(item, index) in viewCompenents" :key="index" :label="item" :value="item"/>
            </el-select>
          </el-form-item>
        </div>
        <div v-if="formData.type == 3" class="flex" style="display: flex">
          <el-form-item label="按钮名称" prop="title">
            <el-input v-model="formData.title" placeholder="请输入按钮名称"/>
          </el-form-item>
          <el-form-item label="权限标识" style="margin-left: 15px" prop="permissions">
            <el-input v-model="formData.permissions" placeholder="请输入权限标识"/>
          </el-form-item>
        </div>
        <div class="flex" style="display: flex">
          <el-form-item label="上级类目" prop="pid">
            <el-cascader :props="propsMenu" v-model="formData.pid" :options="allMenu" clearable/>
          </el-form-item>
          <el-form-item v-if="formData.type == 3" label="按钮排序" prop="sort" style="margin-left: 15px">
            <el-input-number v-model="formData.sort" placeholder="请排序" controls-position="right"/>
          </el-form-item>
        </div>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetForm">取消</el-button>
          <el-button type="primary" @click="handleSubmitAdd()">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import {dialogTy} from '~/dialog' //引入变量类型
import {PropType} from 'vue'
import {getMoveRouter} from '@/api/menu'
import IconSelector from '@/components/IconSelector/index.vue'

const viewsModules: any = import.meta.glob('../../../views/**/*.{vue,tsx}')

const viewCompenents: Array<string> = ['Layout'].concat(
    Object.keys(viewsModules).map((item) => {
      return item.replace('../../../views/', '').replace('.vue', '')
    })
)

const propsMenu = {
  multiple: false,
  checkStrictly: true,
  label: 'title',
  value: 'id'
}

//定义组件的属性
const props = defineProps({
  dialogMenu: {
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

//定义变量
const options = ref({
  FontAwesome: false,
  ElementUI: true,
  eIcon: false, // 自带的图标，来自阿里妈妈
  eIconSymbol: false, // 是否开启彩色图标
  addIconList: [],
  removeIconList: []
})

const rules = ref({
  title: [{required: true, message: '请输入标题', trigger: 'blur'}],
  icon: [{required: true, message: '请设置图标', trigger: 'blur'}],
  sort: [{required: true, message: '请输入排序编号', trigger: 'blur'}],
  name: [{required: true, message: '请输入组件name', trigger: 'blur'}],
  path: [{required: true, message: '请输入组件路径', trigger: 'blur'}],
  // icon: [{ required: true, message: '请选择图标', trigger: 'change' }],
  pid: [{required: true, message: '请选择上级类目', trigger: 'change'}],
  component: [{required: true, message: '请输入组件路径', trigger: 'blur'}],
  permissions: [{required: true, message: '请输入权限标识', trigger: 'blur'}]
})

watch(
    () => props.formData.type,
    () => {
      if (props.formData.type === 1) {
        props.formData.redirect = 'noRedirect' // 建议 必须是 noRedirect
        props.formData.alwaysShow = '1' // 设置成1 意思是总是显示父级，如果没这个字段或者这个字段为0 意思就是当只有一个子菜单的时候，不显示父级
        props.formData.component = 'Layout' // 必须是 Layout
        props.formData.nest = '0'
      } else {
        props.formData.redirect = ''
        props.formData.alwaysShow = ''
        if (props.dialogMenu.option === 'add') {
          props.formData.component = ''
        }
      }
      if (props.formData.type === '3') {
        props.formData.nest = '0'
      }
    }
)

const allMenu = ref(null)
const refMenuForm: any = ref(null)

const openFun = () => {
  getMoveRouter().then((res) => {
    let menuBack = res.data
    menuBack.push({
      id: '0',
      title: '顶级目录'
    })
    if (props.dialogMenu.option === 'add') {
      refMenuForm.value.resetFields()
    } else {
      if (props.formData.type === 2) {
        // 同级不能绑定上下级关系
        res.data.forEach((item) => {
          if (item.children) {
            item.children.forEach((ele) => {
              ele.disabled = true
            })
          }
        })
      }
    }
    allMenu.value = menuBack
  })
}

const emit = defineEmits(['handleSubmitAdd', 'handleSubmitEdit'])

const handleSubmitAdd = () => {
  refMenuForm.value.validate((valid: any) => {
    if (valid) {
      if (props.dialogMenu.option === 'add') {
        emit('handleSubmitAdd')
      } else {
        emit('handleSubmitEdit')
      }
    } else {
      return false
    }
  })
}

const resetForm = () => {
  props.dialogMenu.show = false
  refMenuForm.value.resetFields()
}
</script>

<style lang="scss" scoped></style>
