<template>
  <div class="icon-selector">
    <div class="icon-item-s">
      <component :is="ElSvg[value]" />
    </div>

    <el-popover v-model:visible="visible" placement="right" :width="400" trigger="click">
      <template #reference>
        <span class="icon-selector-btn">点击选择图标</span>
      </template>

      <div class="icon-selector-prop-wrap">
        <div
          v-for="(v, k) in state.eleIconList"
          :key="k"
          :label="v"
          :value="v"
          class="icon-item"
          @click="handleClick(v)"
        >
          <component :is="ElSvg[v]" />
        </div>
      </div>
    </el-popover>
  </div>
</template>

<script setup lang="ts">
import * as ElSvg from '@element-plus/icons-vue'
const emit = defineEmits(['update:modelValue'])

//定义组件的属性
const props = defineProps({
  modelValue: {
    require: true,
    default: null,
    type: String
  }
})
const visible = ref(false)
const value = ref('')

watch(
  () => props.modelValue,
  (val) => {
    value.value = val
  },
  { immediate: true }
)

const state: any = reactive({
  eleIconList: []
})

// 初始化获取 css 样式，获取 element plus 自带 svg 图标，增加了 element 前缀，使用时：elementAim
const getElementPlusIconfont = () => {
  return new Promise((resolve, reject) => {
    nextTick(() => {
      const icons = ElSvg as any
      const sheetsIconList: Array<string> = []
      for (const i in icons) {
        sheetsIconList.push(`element${icons[i].name}`)
      }
      if (sheetsIconList.length > 0) resolve(sheetsIconList)
      else reject('未获取到值，请刷新重试')
    })
  })
}

// 初始化数据
const initFontIconData = async () => {
  await getElementPlusIconfont().then((res: any) => {
    state.eleIconList = res.map(function (it) {
      return it.replace('element', '')
    })
  })
}

// 获取当前点击的 icon 图标
const handleClick = (v: any) => {
  value.value = v
  emit('update:modelValue', v)
}

// 页面加载时
onMounted(() => {
  // 判断默认进来是什么类型图标，进行 tab 回显
  initFontIconData()
})
</script>

<style lang="scss">
.icon-selector {
  display: flex;
  align-items: center;
  justify-content: center;
  align-content: center;

  .icon-item-s {
    height: 32px;
    width: 32px;

    .icon {
      width: 24px;
      height: 24px;
    }
  }
}

.icon-item {
  line-height: 32px;
  height: 32px;
  width: 32px;
  text-align: center;
  .icon {
    width: 24px;
    height: 24px;
    cursor: pointer;
  }
}

.icon-selector-prop-wrap {
  // height: 200px;
  overflow: auto;
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  align-content: space-between;
}
.icon-selector-btn {
  cursor: pointer;
  color: #a8abb2;
}
</style>
