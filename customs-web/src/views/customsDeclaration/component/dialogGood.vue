<template>
    <div>
    
        <el-dialog v-model="dialogGood.show" :title="dialogGood.title" width="60%" center>
    
    
    
            <template #header>
    
              <div class="clearfix">
    
                <span>商品详情</span>
    
              </div>
</template>
         
        <el-form
      ref="formRef"
      :model="formData"
      label-width="120px"
      :label-position="labelPosition"
      class="demo-ruleForm ml-3"
    >
               <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="商品序号:">
                    <el-input readonly v-model="formData.gno" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="备案序号:">
                    <el-input readonly v-model="formData.contritem" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="商品编号:">
                    <el-input readonly v-model="formData.codets" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="检验检疫名称:">
                    <el-input readonly v-model="formData.ciqname" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="商品名称:">
                    <el-input readonly  v-model="formData.gname" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="规格型号:">
                    <el-input readonly  v-model="formData.gmodel" />
                  </el-form-item>
                </el-col>
              </el-row>


              <el-row>
                <el-col :span="5" class="_el_col">
                  <el-form-item label="成交数量:">
                    <el-input readonly v-model="formData.gqty" />
                  </el-form-item>
                </el-col>
                <el-col :span="4" class="_el_col">
                  <el-form-item label="成交单位:">
                    <el-input readonly v-model="formData.gunitValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="_el_col">
                  <el-form-item label="成交单价:">
                    <el-input readonly v-model="formData.declprice" />
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="_el_col">
                  <el-form-item label="成交总价:">
                    <el-input readonly v-model="formData.decltotal" />
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="_el_col">
                  <el-form-item label="币制:">
                    <el-input readonly v-model="formData.tradecurrValue" />
                  </el-form-item>  
                </el-col>
              </el-row>


               <el-row>
                <el-col :span="5" class="_el_col">
                  <el-form-item label="法定数量:">
                    <el-input readonly v-model="formData.firstqty" />
                  </el-form-item>
                </el-col>
                <el-col :span="4" class="_el_col">
                  <el-form-item label="法定单位:">
                    <el-input readonly v-model="formData.firstunitValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="_el_col">
                  <el-form-item label="版本号:">
                    <el-input readonly v-model="formData.exgversion" />
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="_el_col">
                  <el-form-item label="货号:">
                    <el-input readonly v-model="formData.exgno" />
                  </el-form-item>
                </el-col>
                <el-col :span="5" class="_el_col">
                  <el-form-item label="最终目的国:">
                    <el-input  v-if="dialogGood.ieflag =='I'"  readonly v-model="formData.destinationcountryValue" />
                     <el-input  v-else  readonly v-model="formData.origincountryValue" />
                  </el-form-item>
                </el-col>
              </el-row>


              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="第二数量:">
                    <el-input readonly v-model="formData.secondqty" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="第二单位:">
                    <el-input readonly v-model="formData.secondunitValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="原产国(地区):">
                   
                    <el-input  v-if="dialogGood.ieflag =='I'"  readonly v-model="formData.origincountryValue" />
                     <el-input  v-else  readonly v-model="formData.destinationcountryValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="原产地区:">
                    <el-input readonly v-model="formData.origplacecodeValue " />
                  </el-form-item>
                </el-col>
              </el-row>


              <el-row>
                <el-col :span="8" class="_el_col">
                  <el-form-item label="境内目的地:">
                    <el-input readonly v-model="formData.districtcodeValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="7" class="_el_col" :push="1">        
                    <el-input readonly v-model="formData.destcodeValue" />
                </el-col>
                <el-col :span="8" class="_el_col">
                  <el-form-item label="征免方式:">
                    <el-input readonly v-model="formData.dutymodeValue" />
                  </el-form-item>
                </el-col> 
              </el-row>


               <el-row>
                <el-col :span="18" class="_el_col">
                  <el-form-item label="检验检疫货物规格:">
                    <el-input readonly v-model="formData.jyjyggxh" />
                  </el-form-item>
                </el-col>
              </el-row>


                <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="货物属性:">
                    <el-input readonly  v-model="formData.goodsattrname" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="用途:">
                    <el-input readonly  v-model="formData.purposeValue" />
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
const tableData: any = ref(null)
const loading = false
// 角色总数
const total = ref(null)

onMounted(() => {
    getList()
})

// // 查询配置 [pageNum 当前页数, pageSize 每页大小]
const listQuery: any = ref({
    page: 1,
    limit: 10
})

// //定义组件的属性
const props = defineProps({
    formData: {
    require: true,
    default: null,
    type: Object
  },
    dialogGood: {
        require: true,
        default: null,
        type: Object as PropType < dialogTy >
    }
})


// // const store = useStore()

// const handleSizeChange = () => {
//   //  if(!props.headId){
//   //     return;
//   //  }

//   }

// const getLogList2 = () => {
//   // listQuery.page = 1
//   // getLogList()
// }

// // 获取角色列表
const getList = () => {
    // console.log(123)
    //  console.log(props.headId)
    if (!props.headId) {
        return;
    }
    let params = Object.assign(deepClone(listQuery._rawValue), {
        headid: props.headId

    })
    getLogList(params)
        .then((response) => {
            tableData.value = response.data.items
            total.value = response.data.total
        })
        .catch((response) => {})
}
// /**
//  * 分页大小修改处理
//  */
const handleSizeChange = () => {
    listQuery.page = 1
    getList()
}
/**
 * 查询框处理
 */
const handleFilter = () => {
    listQuery.page = 1
    getList()
}


// const resetForm = () => {
//   props.dialogRoles.show = false
// }

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
