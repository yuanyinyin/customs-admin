<template>
<div >
  <!-- <el-dialog  class="abow_dialog" append-to-body   custom-class="abow_dialog"  draggable  v-model="dialogMore.show" :title="dialogMore.title"    > -->
    <el-container >
      <el-main>
        <el-row  class="_row1">
          <el-col :span="24" class="col2">
            <el-form :model="formData" label-width="160px" size="small">
              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="报关/转关:">
                   <!-- <el-input readonly   v-model="formData.decltrnrel" /> -->
                    <el-select v-model="formData.decltrnrel" placeholder=""   style="width:100%"  disabled>
            <el-option
              v-for="item in sourceOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="申报地海关:">
                    <el-input readonly  v-model="formData.custommasterValue" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="统一编号:">
                   <el-input readonly  v-model="formData.seqno" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="预录入编号:">
                   <el-input readonly  v-model="formData.preentryid" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="海关编号:">
                   <el-input readonly  v-model="formData.entryid" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item v-if="formData.ieflag =='I'" label="进境关别:">
                   <el-input readonly  v-model="formData.ieportValue" />
                  </el-form-item>
                  <el-form-item v-else  label="出境关别:">
                   <el-input readonly  v-model="formData.ieportValue" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="备案号:">
                   <el-input readonly  v-model="formData.manualno" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="合同协议号:">
                   <el-input readonly  v-model="formData.contrno" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item v-if="formData.ieflag =='I'" label="进口日期:">
                   <el-input readonly  v-model="formData.iedate" />
                  </el-form-item>
                  <el-form-item  v-else label="出口日期:">
                   <el-input readonly  v-model="formData.iedate" />
                  </el-form-item>

                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="申报日期:">
                   <el-input readonly  v-model="formData.declarationdata" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="5" class="_el_col" >
                  <el-form-item label="境内收发货人:">
                   <el-input readonly  v-model="formData.tradecodescc" />
                  </el-form-item>
                </el-col>
                <el-col :span="3" class="_el_col" :push="1">        
                   <el-input readonly  v-model="formData.tradecode" />         
                </el-col>
                <el-col :span="3" class="_el_col" :push="2">              
                   <el-input readonly  v-model="formData.consigneecode" />         
                </el-col>
                <el-col :span="10" class="_el_col" :push="3">                
                   <el-input readonly  v-model="formData.tradename" />
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="境外收发货人:">
                   <el-input readonly  v-model="formData.overseasconsignorcode" />
                  </el-form-item>
                </el-col>
              
                <el-col :span="11" class="_el_col" :push="1">                
                   <el-input readonly  v-model="formData.overseasconsignorename" />
                </el-col>
              </el-row>

              <el-row>
                
                <el-col :span="6" class="_el_col">
                  <el-form-item v-if="formData.ieflag =='I'" label="消费使用单位:">
                   <el-input readonly  v-model="formData.ownercodescc" />
                  </el-form-item>
                  <el-form-item v-else label="生产销售单位:">
                   <el-input readonly  v-model="formData.ownercodescc" />
                  </el-form-item>
                </el-col>
                <el-col :span="3" class="_el_col">        
                   <el-input readonly  v-model="formData.ownercode" />         
                </el-col>
                <el-col :span="3" class="_el_col">              
                   <el-input readonly  v-model="formData.ownerciqcode" />         
                </el-col>
                <el-col :span="12" class="_el_col">                
                   <el-input readonly  v-model="formData.ownername" />
                </el-col>
              </el-row>

              <el-row>
            
                <el-col :span="6" class="_el_col">
                  <el-form-item label="申报单位:">
                   <el-input readonly  v-model="formData.agentcodescc" />
                  </el-form-item>
                </el-col>
                <el-col :span="3" class="_el_col">        
                   <el-input readonly  v-model="formData.agentcode" />         
                </el-col>
                <el-col :span="3" class="_el_col">              
                   <el-input readonly  v-model="formData.declregno" />         
                </el-col>
                <el-col :span="12" class="_el_col">                
                   <el-input readonly  v-model="formData.agentname" />
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="运输方式:">
                   <el-input readonly  v-model="formData.trafmodeValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="运输工具名称:">
                   <el-input readonly  v-model="formData.trafname" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="航次号:">
                   <el-input readonly  v-model="formData.voyno" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="提运单号:">
                   <el-input readonly  v-model="formData.billno" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="监管方式:">
                   <el-input readonly  v-model="formData.trademodeValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="征免性质:">
                   <el-input readonly  v-model="formData.cutmodeValue" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="许可证号:">
                   <el-input readonly  v-model="formData.licenseno" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item v-if="formData.ieflag =='I'" label="启运国(地区):">
                   <el-input readonly  v-model="formData.tradecountryValue" />
                  </el-form-item>
                  <el-form-item v-else label="运抵国(地区):">
                   <el-input readonly  v-model="formData.tradecountryValue" />
                  </el-form-item>


                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item v-if="formData.ieflag =='I'" label="经停港:">
                   <el-input readonly  v-model="formData.distinateportValue" />
                  </el-form-item>
                  <el-form-item v-else label="指运港:">
                   <el-input readonly  v-model="formData.distinateportValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="成交方式:">
                   <el-input readonly  v-model="formData.transmodeValue" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="4" class="_el_col">
                  <el-form-item label="运费:">
                   <el-input readonly  v-model="formData.feemarkValue" />
                  </el-form-item>
                </el-col>
                <el-col :span="1" class="_el_col">         
                   <el-input readonly  v-model="formData.feerate" />
                </el-col>
                <el-col :span="1" class="_el_col">
                   <el-input readonly  v-model="formData.feecurrValue" />
                </el-col>

                <el-col :span="4" class="_el_col">
                  <el-form-item label="保费:">
                   <el-input readonly  v-model="formData.insurmarkValue" />
                  </el-form-item>
                </el-col>
                 <el-col :span="1" class="_el_col">         
                   <el-input readonly  v-model="formData.insurrate" />
                </el-col>
                <el-col :span="1" class="_el_col">
                   <el-input readonly  v-model="formData.insurcurrValue" />
                </el-col>


                <el-col :span="4" class="_el_col">
                  <el-form-item label="杂费:">
                   <el-input readonly  v-model="formData.othermarkValue" />
                  </el-form-item>
                </el-col>
                 <el-col :span="1" class="_el_col">         
                   <el-input readonly  v-model="formData.otherrate" />
                </el-col>
                <el-col :span="1" class="_el_col">
                   <el-input readonly  v-model="formData.othercurrValue" />
                </el-col>


                <el-col :span="6" class="_el_col">
                  <el-form-item label="件数:">
                   <el-input readonly  v-model="formData.packno" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="包装种类:">
                   <el-input readonly  v-model="formData.wraptypeValue" />
                  </el-form-item>
                </el-col>
                
                <el-col :span="6" class="_el_col">
                  <el-form-item label="毛重(KG):">
                   <el-input readonly  v-model="formData.grosswet" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="净重(KG):">
                   <el-input readonly  v-model="formData.netwt" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="贸易国别(地区):">
                   <el-input readonly  v-model="formData.tradeareacodeValue" />
                  </el-form-item>
                </el-col>
                
                <el-col :span="6" class="_el_col">
                  <el-form-item label="集装箱数:">
                   <el-input readonly  v-model="formData.deccontainernum" />
                  </el-form-item>
                </el-col>
                <el-col :span="12" class="_el_col">
                  <el-form-item label="随附单证:">
                   <el-input readonly  v-model="formData.sfdz" />
                  </el-form-item>
                </el-col>
              </el-row>


              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item v-if="formData.ieflag =='I'" label="入境口岸:">
                   <el-input readonly  v-model="formData.entyportcodeValue" />
                  </el-form-item>

                  <el-form-item v-else label="离境口岸:">
                   <el-input readonly  v-model="formData.entyportcodeValue" />
                  </el-form-item>


                </el-col>
                
                <el-col :span="12" class="_el_col">
                  <el-form-item label="货物存放地点:">
                   <el-input readonly  v-model="formData.goodsplace" />
                  </el-form-item>
                </el-col>
                <el-col :span="6" class="_el_col">
                  <el-form-item v-if="formData.ieflag =='I'" label="启运港:">
                   <el-input readonly  v-model="formData.despportcodeValue" />
                  </el-form-item>
                </el-col>
              </el-row>


              <el-row>
                <el-col :span="6" class="_el_col">
                  <el-form-item label="报关单类型:">
                   <el-input readonly  v-model="formData.entrytypeValue" />
                  </el-form-item>
                </el-col>
                
                <el-col :span="11" class="_el_col">
                  <el-form-item label="备注:">
                   <el-input readonly  v-model="formData.notes" />
                  </el-form-item>
                </el-col>

                <el-col :span="5" class="_el_col" :push="2">
                  
                   <el-button type="primary" @click="otherThing()" >其他事项确认</el-button>
                  
                </el-col>
              </el-row>

               <el-row>
                <el-col :span="18" class="_el_col">
                  <el-form-item label="标记唛码:">
                   <el-input readonly  v-model="formData.markno" />
                  </el-form-item>
                </el-col>

                <el-col :span="5" class="_el_col" :push="1">
                  
                   <el-button type="primary" @click="bussinessOpt()" >业务选项</el-button>
                 
                </el-col>


              </el-row>


            </el-form>
          </el-col>
        </el-row>
        <el-row class="_row2">
          <el-col :span="24" class="col3">
            <el-tabs v-model="activeName" type="card" class="tabs" @tab-click="handleTabClick">
              <el-tab-pane label="检验检疫信息" name="extra">
                <ExtraInfoForm
                  
               
                  :form-data="formDataExtra"
                
                />
              </el-tab-pane>
              <el-tab-pane label="商品信息" name="good">
                <GoodListForm   :table-data="formDataGood"   :ie-flag="formDataGoodIeFlag"/>
              </el-tab-pane>
              <el-tab-pane label="集装箱信息" name="container" >
            <ContainerListForm
              :table-data="formDataContainer"
            />
          </el-tab-pane>

          <el-tab-pane label="随附单证信息" name="document">
            <DocumentListForm
               :table-data="formDataDocument"
            />
          </el-tab-pane>

           <el-tab-pane label="其他信息" name="record">
            <RecordForm
                :form-data="formDataRecord"
            />
              </el-tab-pane>
            </el-tabs>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
 

   <DialogOtherThing ref="dialogOtherThingRef" 
       :dialog-good="dialogOtherThingData"
       :form-data="formOtherThing"
        />


 <DialogBussinessOpt ref="dialogBussinessOptRef" 
       :dialog-good="dialogBussinessOptData"
       :form-data="formBussinessOpt"
        />

  <!-- </el-dialog> -->

 </div>  
</template>

<script setup lang="ts">
import ExtraInfoForm from './component/ExtraInfoForm.vue'
import GoodListForm from './component/GoodListForm.vue'
import ContainerListForm from './component/ContainerListForm.vue'
import DocumentListForm from './component/DocumentListForm.vue'
import RecordForm from './component/RecordForm.vue'
import {showOne } from "@/api/qpDec";

import { dialogTy } from '~/dialog'
import { PropType } from 'vue'



const props = defineProps({
    
    dialogMore: {
        require: false,
        default: null,
        type: Object as PropType < dialogTy >
    },
    
})







const route = useRoute()

const store = useStore()
//

const current = ref({})
const currentId = ref<number>(0)

// table显示角色数组
const formData: any = ref({})
let formDataExtra = ref({})//这是相对自由点的写法，不定义类型
let formDataGood = ref()//这是相对自由点的写法，不定义类型
let formDataGoodIeFlag = ref()//这是相对自由点的写法，不定义类型
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
    if (route.query.headId) {
      // console.log(666)
      // console.log(route.query.headId)
      getData(route.query.headId);
      // getData("4028b881827b43cd01827b4b0bd40e38");
    }

    //
    // window.onresize = () => {
		// 	    return (() => {
		// 	      this.setDialogWidth()
		// 	    })()
		// 	  }



  })
})

// const setDialogWidth = () => {
// 			    var val = document.body.clientWidth
// 			    const def = 450 // 默认宽度
// 			    if (val < def) {
// 			      this.dialogWidth = '100%'
// 			    } else {
// 			      this.dialogWidth = def + 'px'
// 			    }
// 			  }





const otherThing = () => {
  // console.log(123)
  // console.log(formData)
    const promiseitmes =   formData.value.promiseitmes ;
    
    // console.log(promiseitmes)
    var firstIsYes = false
    if(promiseitmes ){
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

const sourceOptions = [
  { value: '0', label: '一般报关单', },
  { value: '1', label: '转关提前报关单', },
  
]



const bussinessOpt = (headId) => {
  const entrytype =   formData.value.entrytype ;
  const ieflag =   formData.value.ieflag ;


  const bussSw =   formData.value.bussSw ;
  const bussZb =   formData.value.bussZb ;
  const bussSyzz =   formData.value.bussSyzz ;
  const bussZbzj =   formData.value.bussZbzj ;
  const chksurety =   formData.value.chksurety ;
  const chkshunt =   formData.value.chkshunt ;


    // console.log(promiseitmes)
    // const firstIsYes = false
    var tenderGroup = [];
    var checkList = [];
    if(bussSw && '1' == bussSw){
        checkList.push('1')
    }
    if(bussSw && '1' == bussSw){
        checkList.push('2')
    }
    if(bussSw && '1' == bussSw){
        checkList.push('3')
    }
    if(bussSw && '1' == bussSw){
        checkList.push('4')
    }
    if(bussSw && '1' == bussSw){
        checkList.push('5')
    }
   

    if(entrytype && ieflag){
      var obj = {}
      if(ieflag == 'I'){
           tenderGroup = [
                 { id: '1', realName: '税单无纸化',},
                 { id: '2', realName: '自主报税', },
                 { id: '4', realName: '自报自缴', },
                 { id: '5', realName: '担保验放', },
                ]

          // if(entrytype == "M"){
              
          //      obj.checkList = ['1','4','5']
               
          //   }else {
          //      obj.checkList = ['1','2']
          //   }
      }else{
        tenderGroup = [
                  { id: '1', realName: '税单无纸化',},
                  { id: '2', realName: '自主报税', },
                  { id: '3', realName: '水运中转', },
                  { id: '4', realName: '自报自缴', },
                  { id: '5', realName: '担保验放', },
                ]
      }
             obj.checkList = checkList
            obj.tenderGroup = tenderGroup
           
            formBussinessOpt.value = obj;
    }else{
      alert("请确认报关单类型与进出口标志!")
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
      
         formDataExtra.value  = dataT;

         formDataGood.value = dataT.qpDecLists
         formDataGoodIeFlag.value = dataT.ieflag
        //  formDataGood.value = dataT
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





defineExpose({ getData })

const handleTabClick = (tab) => {
  // if (currentId?.value > 0) {
  //   switch (tab.props.name) {
  //     case 'extra':

  //       break
  //     case 'good':
     
  //       break
  //     default:
  //   }
  // }
}

/**
 * 新增
 */
const dialogData: Ref<dialogTy> = ref({}) //ts的规范写法，定义变量的类型
// let formData = ref({})//这是相对自由点的写法，不定义类型
</script>


<style scoped lang="scss">
.el-dialog__wrapper {
    display: flex;
    padding: 24px; //添加上下间隙，可为任意值
    .el-dialog {
        margin: auto !important;
    }
}

.abow_dialog {
  // height:1024px
}
  
// .abow_dialog {
//     display: flex;
//     justify-content: center;
//     align-items: Center;
//     overflow: hidden;
//     .el-dialog {
//         margin: 0 auto !important;
//         height: 60%;
//         overflow: hidden;
//         .el-dialog__body {
//             position: absolute;
//             left: 0;
//             top: 54px;
//             bottom: 0;
//             right: 0;
//             padding: 0;
//             z-index: 1;
//             overflow: hidden;
//             overflow-y: auto;
//         }
//     }
//     }

 

._container{
  //  height: 100% !important;
}
._el_col {
  height: 8px !important;
}
._row1{
   height: 520px !important;
   overflow: auto;
}
._row2{
  //  height: 200px !important;
  // height:calc(100vh - 600px);
  //  overflow: auto;
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


