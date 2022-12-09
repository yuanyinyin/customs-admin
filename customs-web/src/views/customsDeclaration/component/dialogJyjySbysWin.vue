<template>
    <div>
    
        <el-dialog  v-model="dialogGood.show" :title="dialogGood.title" width="45%" center>
            <template #header>
    
              <div class="clearfix">
    
                <span>检验检疫申报要素</span>
    
              </div>
            </template>

            <el-table  height="300" v-model:data="tableData" v-loading="loading" stripe style="width: 100%">
          <el-table-column type="selection" align="center" width="50" />
          <el-table-column type="index" label="序号" align="center" width="55">
            <template #default="scope">
              <span>{{  scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="appCertCode" label="证书代码" align="center">
          </el-table-column>
          <el-table-column prop="appCertCode_value" label="证书名称" align="center">
          </el-table-column>
           <el-table-column prop="applOri" label="正本数量" align="center">
          </el-table-column>
           <el-table-column prop="applCopyQuan" label="副本数量" align="center">
          </el-table-column>
           
        </el-table>

      <br>

      <el-form
      ref="formRef"
      :model="formData"
      label-width="210px"
      :label-position="labelPosition"
      class="demo-ruleForm ml-3"
     >
              <el-row>
                <el-col :span="24" class="_el_col">
                  <el-form-item label="境内收发货人名称(外文):">
                     <el-input  v-model="formData.domesticconsigneeename" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="_el_col">
                  <el-form-item label="境外收发货人名称(中文):">
                     <el-input  v-model="formData.overseasconsignorcname" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="_el_col">
                  <el-form-item label="境外发货人地址:">
                     <el-input  v-model="formData.overseasconsignoraddr" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="_el_col">
                  <el-form-item label="卸毕日期:">
                     <el-input  v-model="formData.cmpldschrgdt" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24" class="_el_col">
                  <el-form-item label="商品英文名称:">
                     <el-input  v-model="formData.declgoodsenames" />
                  </el-form-item>
                </el-col>
              </el-row>


               
    </el-form>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { dialogTy } from '~/dialog'
import { PropType } from 'vue'
import { getLogList } from "@/api/qpDec";
import { parseTime, deepClone } from '@/utils/dateTime'

import { listUser } from "@/api/qpDec";

const tableData: any = ref(null)
// const tableData: any = ref(null)
const loading = false
// 角色总数
const total = ref(null)

onMounted(() => {
    // getList()
    initData();
})

// // 查询配置 [pageNum 当前页数, pageSize 每页大小]
const listQuery: any = ref({
    page: 1,
    limit: 10
})

const dataOrigin = 
[
{'appCertCode':'11','appCertCode_value':'品质证书','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'12','appCertCode_value':'重量证书','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'13','appCertCode_value':'数量证书','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'14','appCertCode_value':'兽医卫生证书','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'15','appCertCode_value':'健康证书','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'16','appCertCode_value':'卫生证书','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'17','appCertCode_value':'动物卫生证书','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'18','appCertCode_value':'植物检疫证书','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'19','appCertCode_value':'熏蒸/消毒证书','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'20','appCertCode_value':'出境货物换证凭单','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'21','appCertCode_value':'入境货物检验检疫证明（申请出具）','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'22','appCertCode_value':'出境货物不合格通知单','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'23','appCertCode_value':'集装箱检验检疫结果单','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'24','appCertCode_value':'入境货物检验检疫证明（申请不出具）','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'94','appCertCode_value':'电子底账','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'95','appCertCode_value':'入境货物调离通知单	','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'96','appCertCode_value':'出境货物检验检疫工作联系单','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'98','appCertCode_value':'其他单','applOri':'1','applCopyQuan':'2'},
{'appCertCode':'99','appCertCode_value':'其他证书	','applOri':'1','applCopyQuan':'2'}
]


// //定义组件的属性
const props = defineProps({
    headId: {
    type: String,
    default: '',
    require: true
  },
    dialogGood: {
        require: true,
        default: null,
        type: Object as PropType < dialogTy >
    },
    formData: {
    require: true,
    default: null,
    type: Object
  },
})

//定义变量
// const tenderGroup = [
//   { id: '1', realName: '税单无纸化',},
//   { id: '2', realName: '自主报税', },
//   { id: '3', realName: '水运中转', },
//   { id: '4', realName: '自报自缴', },
//   { id: '5', realName: '担保验放', },
//   { id: '6', realName: '查验分流', },
// ]
    
const  initData = () =>{
  tableData.value = dataOrigin;
}
// // 获取角色列表
const getList = () => {
    // console.log(123)
    //  console.log(props.headId)
    if (!props.headId) {
        return;
    }
    let params =  {
        headid: props.headId

    }
    listUser(params)
        .then((response) => {
            tableData.value = response.data.items
            // total.value = response.data.total
        })
        .catch((response) => {})
}
 
defineExpose({ getList })
</script>

<style scoped lang="scss">
._el_col {
  height: 16px !important;
}

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
