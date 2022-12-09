<template>
  <!-- <div class="roles-container scroll-y p"> -->
  <el-container>
    <!-- 顶栏 -->
    <!-- <el-header height="24px">
      <h2>报关单详情</h2>
    </el-header> -->
    <!-- 嵌套容器 -->
    <el-container>
      <el-main>
        <!-- 第一列栅格布局 -->
        <el-row  class="_row1">
          <!-- <el-col :span="12" class="col1"></el-col> -->
          <el-col :span="24" class="col2">
            <el-form :model="formData" label-width="160px">
              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="报关/转关:">
                   <el-input  v-model="formData.decltrnrelValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="申报地海关:">
                    <el-input  v-model="formData.custommasterValue" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="统一编号:">
                   <el-input  v-model="formData.seqno" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="预录入编号:">
                   <el-input  v-model="formData.preentryid" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="海关编号:">
                   <el-input  v-model="formData.entryid" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="进境关别:">
                   <el-input  v-model="formData.ieportValue" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="备案号:">
                   <el-input  v-model="formData.manualno" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="合同协议号:">
                   <el-input  v-model="formData.contrno" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="进口日期:">
                   <el-input  v-model="formData.iedate" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="申报日期:">
                   <el-input  v-model="formData.declarationdata" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="5" class="_el_col" >
                  <el-form-item label="境内收发货人:">
                   <el-input  v-model="formData.tradecodescc" />
                  </el-form-item>
                </el-col>
                <el-col :span="3" class="_el_col" :push="1">        
                   <el-input  v-model="formData.tradecode" />         
                </el-col>
                <el-col :span="3" class="_el_col" :push="2">              
                   <el-input  v-model="formData.consigneecode" />         
                </el-col>
                <el-col :span="10" class="_el_col" :push="3">                
                   <el-input  v-model="formData.tradename" />
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="境外收发货人:">
                   <el-input  v-model="formData.overseasconsignorcode" />
                  </el-form-item>
                </el-col>
              
                <el-col :span="11" class="_el_col" :push="1">                
                   <el-input  v-model="formData.overseasconsignorename" />
                </el-col>
              </el-row>

              <el-row>
                
                <el-col :span="6" class="_el_col">
                  <el-form-item label="消费使用单位:">
                   <el-input  v-model="formData.ownercodescc" />
                  </el-form-item>
                </el-col>
                <el-col :span="3" class="_el_col">        
                   <el-input  v-model="formData.ownercode" />         
                </el-col>
                <el-col :span="3" class="_el_col">              
                   <el-input  v-model="formData.ownerciqcode" />         
                </el-col>
                <el-col :span="12" class="_el_col">                
                   <el-input  v-model="formData.ownername" />
                </el-col>
              </el-row>

              <el-row>
            
                <el-col :span="6" class="_el_col">
                  <el-form-item label="申报单位:">
                   <el-input  v-model="formData.agentcodescc" />
                  </el-form-item>
                </el-col>
                <el-col :span="3" class="_el_col">        
                   <el-input  v-model="formData.agentcode" />         
                </el-col>
                <el-col :span="3" class="_el_col">              
                   <el-input  v-model="formData.declregno" />         
                </el-col>
                <el-col :span="12" class="_el_col">                
                   <el-input  v-model="formData.agentname" />
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="运输方式:">
                   <el-input  v-model="formData.trafmodeValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="运输工具名称:">
                   <el-input  v-model="formData.trafname" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="航次号:">
                   <el-input  v-model="formData.voyno" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="提运单号:">
                   <el-input  v-model="formData.billno" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="监管方式:">
                   <el-input  v-model="formData.trademodeValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="征免性质:">
                   <el-input  v-model="formData.cutmodeValue" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="许可证号:">
                   <el-input  v-model="formData.licenseno" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="启运国(地区):">
                   <el-input  v-model="formData.tradecountryValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="经停港:">
                   <el-input  v-model="formData.distinateportValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="成交方式:">
                   <el-input  v-model="formData.transmodeValue" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="4" class="_el_col">
                  <el-form-item label="运费:">
                   <el-input  v-model="formData.feemarkValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="1" class="_el_col">         
                   <el-input  v-model="formData.feerate" />
                </el-col>
                <el-col :span="1" class="_el_col">
                   <el-input  v-model="formData.feecurrValue" />
                </el-col>

                <el-col :span="4" class="_el_col">
                  <el-form-item label="保费:">
                   <el-input  v-model="formData.insurmarkValue" />
                  </el-form-item>
                </el-col>
                 <el-col :span="1" class="_el_col">         
                   <el-input  v-model="formData.insurrate" />
                </el-col>
                <el-col :span="1" class="_el_col">
                   <el-input  v-model="formData.insurcurrValue" />
                </el-col>


                <el-col :span="4" class="_el_col">
                  <el-form-item label="杂费:">
                   <el-input  v-model="formData.othermarkValue" />
                  </el-form-item>
                </el-col>
                 <el-col :span="1" class="_el_col">         
                   <el-input  v-model="formData.otherrate" />
                </el-col>
                <el-col :span="1" class="_el_col">
                   <el-input  v-model="formData.othercurrValue" />
                </el-col>


                <el-col :span="6" class="_el_col">
                  <el-form-item label="件数:">
                   <el-input  v-model="formData.packno" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="包装种类:">
                   <el-input  v-model="formData.wraptypeValue" />
                  </el-form-item>
                </el-col>
                
                <el-col :span="6" class="_el_col">
                  <el-form-item label="毛重(KG):">
                   <el-input  v-model="formData.grosswet" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="净重(KG):">
                   <el-input  v-model="formData.netwt" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="贸易国别(地区):">
                   <el-input  v-model="formData.tradeareacodeValue" />
                  </el-form-item>
                </el-col>
                
                <el-col :span="6" class="_el_col">
                  <el-form-item label="集装箱数:">
                   <el-input  v-model="formData.deccontainernum" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="随附单证:">
                   <el-input  v-model="formData.sfdz" />
                  </el-form-item>
                </el-col>
              </el-row>


              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="入境口岸:">
                   <el-input  v-model="formData.entyportcodeValue" />
                  </el-form-item>
                </el-col>
                
                <el-col :span="12" class="_el_col">
                  <el-form-item label="货物存放地点:">
                   <el-input  v-model="formData.goodsplace" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="启运港:">
                   <el-input  v-model="formData.despportcodeValue" />
                  </el-form-item>
                </el-col>
              </el-row>


              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="报关单类型:">
                   <el-input  v-model="formData.entrytypeValue" />
                  </el-form-item>
                </el-col>
                
                <el-col :span="11" class="_el_col">
                  <el-form-item label="备注:">
                   <el-input  v-model="formData.notes" />
                  </el-form-item>
                </el-col>

                <el-col :span="5" class="_el_col" :push="2">
                  
                   <el-button type="primary" @click="otherThing()" >其他事项确认</el-button>
                  
                </el-col>
              </el-row>

               <el-row>
                <!-- <el-col :span="6" class="_el_col">
                  
                </el-col> -->
                
                <el-col :span="18" class="_el_col">
                  <el-form-item label="标记唛码:">
                   <el-input  v-model="formData.markno" />
                  </el-form-item>
                </el-col>

                <el-col :span="5" class="_el_col" :push="1">
                  
                   <el-button type="primary" @click="bussinessOpt()" >业务选项</el-button>
                 
                </el-col>


              </el-row>


            </el-form>
          </el-col>
        </el-row>
        <!-- 第二列布局 -->
        <el-row class="_row2">
          <el-col :span="24" class="col3">
            <el-tabs v-model="activeName" type="card" class="tabs" @tab-click="handleTabClick">
              <el-tab-pane label="补充信息" name="extra">
                <ExtraInfoForm
                  ref="ExtraInfoFormRef"
                  :current-id="currentId"
                  :current="current"
                  :form-data="formDataExtra"
                  @close-drawer="closeDrawer"
                />
              </el-tab-pane>
              <el-tab-pane label="商品列表" name="good">
                <GoodListForm ref="GoodListFormRef"  :table-data="formDataGood"/>
              </el-tab-pane>
              <el-tab-pane label="集装箱详情" name="container" >
            <ContainerListForm
              ref="ContainerListFormRef" :table-data="formDataContainer"
            />
          </el-tab-pane>

          <el-tab-pane label="单证信息" name="document">
            <DocumentListForm
              ref="DocumentListFormRef" :table-data="formDataDocument"
            />
          </el-tab-pane>

           <el-tab-pane label="备案详情" name="record">
            <RecordForm
              ref="RecordFormRef"  :form-data="formDataRecord"
            />
              </el-tab-pane>
            </el-tabs>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
    <!-- 底栏 -->
    <!-- <el-footer height="30px">&copy;究极死胖兽 2019</el-footer> -->
    <!-- <el-footer >&copy;究极死胖兽 2019</el-footer> -->
  </el-container>

   <DialogOtherThing ref="dialogOtherThingRef" 
       :dialog-good="dialogOtherThingData"
       :form-data="formOtherThing"
        />


 <DialogBussinessOpt ref="dialogBussinessOptRef" 
       :dialog-good="dialogBussinessOptData"
       :form-data="formBussinessOpt"
        />







  <!-- </div> -->
</template>

<script setup lang="ts">
import ExtraInfoForm from './component/ExtraInfoForm.vue'
import GoodListForm from './component/GoodListForm.vue'
import ContainerListForm from './component/ContainerListForm.vue'
import DocumentListForm from './component/DocumentListForm.vue'
import RecordForm from './component/RecordForm.vue'
import {showOne } from "@/api/qpDec";








const route = useRoute()

const store = useStore()
//

const current = ref({})
const currentId = ref<number>(0)

// table显示角色数组
const formData: any = ref({})
let formDataExtra = ref({})//这是相对自由点的写法，不定义类型
let formDataGood = ref()//这是相对自由点的写法，不定义类型
let formDataContainer = ref()//这是相对自由点的写法，不定义类型
let formDataDocument = ref()//这是相对自由点的写法，不定义类型
let formDataRecord = ref({})//这是相对自由点的写法，不定义类型


//ONE
import DialogOtherThing from './component/dialogOtherThing.vue'
let formOtherThing = ref({})
const dialogOtherThingData: Ref<dialogTy> = ref({})


import DialogBussinessOpt from './component/dialogBussinessOpt.vue'
let formBussinessOpt = ref({})
const dialogBussinessOptData: Ref<dialogTy> = ref({})



const activeName = ref('extra')
onMounted(() => {
  nextTick(() => {
    //跳转直接进行审批详情页
    // if (route.query.headId) {
    //   console.log(666)
    //   console.log(route.query.headId)
      // getData(route.query.headId);
      getData("4028b881827b43cd01827b4b0bd40e38");
    // }
  })
})


const otherThing = () => {
  // console.log(123)
  // console.log(formData)
    const promiseitmes =   formData.value.promiseitmes ;
    // console.log(promiseitmes)
    var firstIsYes = false
    if(promiseitmes){
        var vsArr = promiseitmes.split("");
            for(var i = 0; i < vsArr.length; i++){
                if (vsArr[i] !="1" || vsArr[i] !="0"){
                    vsArr[i] = "9";
                }
                if (i ==0 && vsArr[i] == "0") {
                    firstIsYes=true;
                }
            }
            if (firstIsYes) {
                vsArr[1] = "0";  
            }
            var obj = {data1 : vsArr[0] 
                       ,data2: vsArr[1] 
                       ,data3: vsArr[2]
              };
              formOtherThing.value = obj;
    }
    dialogOtherThingData.value = {
    show: true,
    title: '其他事项确认',
    
  }


}

const bussinessOpt = (headId) => {
  const entrytype =   formData.value.entrytype ;
    // console.log(promiseitmes)
    const firstIsYes = false
    if(entrytype){
      var obj = {}
            if(entrytype == "M"){
               obj.checkList = ['1','4','5']
            }else {
               obj.checkList = ['1','2']
            }
           
            formBussinessOpt.value = obj;
    }
    dialogBussinessOptData.value = {
    show: true,
    title: '业务事项',
    
  }

}




// 获取角色列表
const getData = (headId) => {
 
  showOne({
    "headId": headId
  }) 
    .then((res) => {
      if (res && res.code === 200) {
         const dataT = res.data;
         console.log(dataT)
         formData.value = dataT;
        //  console.log(dataT.trafmodeValue)
        //  console.log(dataT.agentcode)
         formDataExtra.value  = dataT;
        //  console.log(888)
        //  console.log(dataT.qpDecLists)
         formDataGood.value = dataT.qpDecLists
         formDataContainer.value = dataT.qpDecContainerList
         formDataDocument.value = dataT.qpDecLicenseDocuList
         formDataRecord.value = dataT.qpDecFreeTxt
      } else {
        ElMessage({ message: res.message, type: 'error' })
      }
    })
    .catch(() => {})

}



const ExtraInfoFormFormRef = ref<InstanceType<typeof ExtraInfoForm>>()

const GoodListFormRef = ref<InstanceType<typeof GoodListForm>>()

const ContainerListFormRef = ref<InstanceType<typeof ContainerListForm>>()
const DocumentListFormRef = ref<InstanceType<typeof DocumentListForm>>()

const RecordFormRef = ref<InstanceType<typeof RecordForm>>()







const handleTabClick = (tab) => {
  if (currentId?.value > 0) {
    switch (tab.props.name) {
      case 'extra':
        // showAuditProcess(currentId.value).then(res => {
        //   if (res.code == 200) {
        //     ExtraInfoFormFormRef.value.showProcess(res.data)
        //   } else {
        //     ElMessage({message: '审核流程获取出错', type: 'error'})
        //     console.log(res.message)
        //   }
        // })
        break
      case 'good':
        // showPayProcess(currentId.value).then(res => {
        //   if (res.code == 200) {
        //     GoodListFormRef.value.showProcess(res.data)
        //   } else {
        //     ElMessage({message: '收付款流程获取出错', type: 'error'})
        //     console.log(res.message)
        //   }
        // })
        break
      default:
    }
  }
}

/**
 * 新增
 */
const dialogData: Ref<dialogTy> = ref({}) //ts的规范写法，定义变量的类型
// let formData = ref({})//这是相对自由点的写法，不定义类型
</script>


<style scoped lang="scss">
._el_col {
  height: 8px !important;
}
._row1{
   height: 510px !important;
   overflow: auto;
}
._row2{
  //  height: 260px !important;
   overflow: auto;
}
.el-col {
  // height: 30px;
  // background-color: #409EFF;
}
.el-header {
  // background-color: #409EFF;
  // color: white;
}
.el-footer {
  background-color: #909399;
  color: black;
  text-align: center;
}
.el-aside {
  // background-color: chartreuse;
}
.el-main {
  // background-color: darkkhaki;
}
.el-col {
  // height: 50%;
}
.col1 {
  // background-color: teal;
}
.col2 {
  // background-color: tomato;
}
.col3 {
  // background-color: white;
}
</style>


