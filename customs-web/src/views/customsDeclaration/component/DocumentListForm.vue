<template>
  <div class="finance scroll-y pb-100">
    <el-table  height="240" v-model:data="tableData" v-loading="loading" stripe style="width: 100%" size="small">
          <el-table-column type="selection" align="center" width="50" />
          <el-table-column type="index" label="序号" align="center" width="55">
            <template #default="scope">
              <span>{{  scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="docucode" label="单证代码" align="center">
          </el-table-column>
          <el-table-column prop="certcode" label="单证编号" align="center">
          </el-table-column>
           <el-table-column prop="docucode" label="随附单证代码" align="center" :formatter="formatterCode" >
          </el-table-column>
          <el-table-column prop="certcode" label="随附单证编号" align="center">
          </el-table-column>
          
        </el-table>


    <br>
        <br>
            <br>

<!-- <el-form
      ref="formRef"
      :model="formData"
      label-width="160px"
      :label-position="labelPosition"
      class="demo-ruleForm ml-3"
    >
               <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="随附单证代码:">
                    <el-input />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="随附单证编号:">
                    <el-input />
                  </el-form-item>
                </el-col>
              </el-row>

</el-form> -->

  </div>
</template>

<script lang="ts" setup>
import type {ElForm} from 'element-plus'
import {Plus, Edit, Delete, Search, Share} from '@element-plus/icons-vue'
import {ElMessage, ElMessageBox} from "element-plus";
import type {UploadFile} from 'element-plus/es/components/upload/src/upload.type'
import {Link} from '@element-plus/icons-vue'

// const tableData: any = ref(null)


const docucodes = [
  // { value: '0', label: '一般报关单', },
  // { value: '1', label: '转关提前报关单', },

 { value: 'y' , label: '出口许可证(边境小额贸易)',} ,
 { value: 'z' , label: '古生物化石出境批件',} ,
 { value: '0' , label: '反制措施排除代码',} ,
 { value: '1' , label: '进口许可证',} ,
 { value: '2' , label: '两用物项和技术进口许可证',} ,
 { value: '3' , label: '两用物项和技术出口许可证',} ,
 { value: '4' , label: '出口许可证',} ,
 { value: '5' , label: '纺织品临时出口许可证',} ,
 { value: '6' , label: '旧机电产品禁止进口',} ,
 { value: '7' , label: '自动进口许可证',} ,
 { value: '8' , label: '禁止出口商品',} ,
 { value: '9' , label: '禁止进口商品',} ,
 { value: 'A' , label: '检验检疫',} ,
 { value: 'B' , label: '电子底账',} ,
 { value: 'D' , label: '毛坯钻石进出境检验',} ,
 { value: 'E' , label: '濒危物种允许出口证明书',} ,
 { value: 'F' , label: '濒危物种允许进口证明书',} ,
 { value: 'G' , label: '两用物项和技术出口许可证(定向)',} ,
 { value: 'H' , label: '港澳OPA纺织品证明',} ,
 { value: 'I' , label: '麻醉药品精神药物进出口准许证',} ,
 { value: 'J' , label: '黄金及黄金制品进出口准许证',} ,
 { value: 'K' , label: '深加工结转申请表',} ,
 { value: 'L' , label: '药品进出口准许证',} ,
 { value: 'M' , label: '密码产品和设备进口许可证',} ,
 { value: 'O' , label: '自动进口许可证(新旧机电产品)',} ,
 { value: 'P' , label: '固体废物进口许可证',} ,
 { value: 'Q' , label: '进口药品通关单',} ,
 { value: 'R' , label: '进口兽药通关单',} ,
 { value: 'S' , label: '农药进出口登记管理放行通知单',} ,
 { value: 'U' , label: '合法捕捞产品通关证明',} ,
 { value: 'V' , label: '人类遗传资源材料出口、出境证明',} ,
 { value: 'X' , label: '有毒化学品环境管理放行通知单',} ,
 { value: 'Y' , label: '原产地证明',} ,
 { value: 'Z' , label: '赴境外加工光盘进口备案证明',} ,
 { value: 'a' , label: '保税核注清单',} ,
 { value: 'b' , label: '进口广播电影电视节目带(片)提取单',} ,
 { value: 'c' , label: '内销征税联系单',} ,
 { value: 'd' , label: '援外项目任务通知函',} ,
 { value: 'e' , label: '关税配额外优惠税率进口棉花配额证',} ,
 { value: 'f' , label: '音像制品（成品）进口批准单',} ,
 { value: 'g' , label: '技术出口合同登记证',} ,
 { value: 'h' , label: '核增核扣表',} ,
 { value: 'i' , label: '技术出口许可证',} ,
 { value: 'k' , label: '民用爆炸物品进出口审批单',} ,
 { value: 'm' , label: '银行调运人民币现钞进出境证明',} ,
 { value: 'n' , label: '音像制品（版权引进）批准单',} ,
 { value: 'q' , label: '国别关税配额证明',} ,
 { value: 'r' , label: '预归类标志',} ,
 { value: 's' , label: '适用ITA税率的商品用途认定证明',} ,
 { value: 't' , label: '关税配额证明',} ,
 { value: 'v' , label: '自动进口许可证(加工贸易)',} ,
 { value: 'x' , label: '出口许可证(加工贸易)',} 
  
  
]

const formatterCode = (row) => {
  if(!row.docucode){
    return
  }
   let result = docucodes.filter(
    (item) => item.value  == row.docucode
  )
  if(result && result.length > 0){
    return result[0].label
  }

  
}



// 组件属性
const props = defineProps({
  // current: {
  //   type: Object,
  //   default: null,
  //   require: false
  // },
  // currentId: {
  //   type: Number,
  //   default: null,
  //   require: false
  // },
tableData: {
    require: false,
    default: null,
    type: Array
  }
 
})
const formData = ref({
  reimburseSubArr: [],
  payAmountNumber: 0,
  payAmountCapital: '零元整',
  applyStatus: 'store',
  saveFiles: {
    reimburseFile: []
  },
  audit: {
    signatureId: 0,
    status: '',
    reason: ''
  }
})
const formRef = ref<InstanceType<typeof ElForm>>()

const emit = defineEmits(['closeDrawer'])

// const initData = () => {
//   if (props.ope !== "add" && props.currentId) {
//     showOne(props.currentId).then(res => {
//       if (res.code == 200) {
//         formData.value = res.data
//         // formData.value.count = formData.value.reimburseSubArr.length
//         let fileArr = []
//         //文件可以有多个
//         let files = res.data.reimburseFile
//         if (files) {
//           files.forEach(fileData => {
//             let fileJson = {
//               id: fileData.id,
//               name: fileData.fileName,
//               url: fileData.fileUrl
//             }
//             fileArr.push(fileJson)
//           })
//           reimburseFile.value = fileArr
//           formData.value.saveFiles = {reimburseFile: fileArr}
//           fileArr = []
//         }
//       } else {
//         ElMessage(({message: '数据获取失败！' + res.data, type: 'error'}))
//       }
//     })
//   } else {
//     formData.value.reimburseSubArr = []
//   }
// }
onMounted(() => {
  // initData()
})

/** 附件上传部分 */

/** 附件上传 结束 */

// 表单保存


//表单提交

const resetForm = () => {
  formRef.value?.resetFields()
}

// defineExpose({formData, saveForm, submitForm, resetForm})
</script>

<style scoped lang="scss">
// ._el_col {
//   height: 24px !important;
// }
.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-tabs__content {
  padding-top: 30px;
  border: 1px solid #e4e7ed;
  border-top: none;
}

.el-tabs__header {
  margin: 0;
}

.pb-100 {
  padding-bottom: 100px;
}

.upload-demo {
  width: 60%;
}
</style>
