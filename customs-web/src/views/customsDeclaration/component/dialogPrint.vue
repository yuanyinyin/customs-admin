<template>
    <div>
    
        <el-dialog v-model="dialogGood.show" :title="dialogGood.title" width="24%" center>
            <template #header>
    
              <div class="clearfix">
    
                <span>选择打印类型</span>
    
              </div>
</template>
         
        <el-form
      ref="formRef"
      :model="formData"
      label-width="20px"
      :label-position="labelPosition"
       class="demo-form-inline"  size="small"
    >  <el-form-item>

                
                   <el-checkbox-group v-model="checkList"  >
          <el-checkbox
            v-for="(item, index) in tenderGroup"
            :key="index"
            :label="item.id"
          >{{ item.realName }}
          </el-checkbox>
        </el-checkbox-group>
       

         </el-form-item>      
 
              
    </el-form>
       
        <template #footer>
           <!-- <el-row>
                <el-col :span="24" class="_el_col"> -->
        <span class="dialog-footer" size="small">
          <el-button @click="resetForm" size="small">取消</el-button>
          <el-button type="primary" @click="handlePrint()" size="small">确定</el-button>
          
        </span>
        <!-- </el-col>
              
              </el-row> -->
      </template>


    <br>
     <br>
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
    // getList()
})

const formRef: any = ref(null)  
const resetForm = () => {
  props.dialogGood.show = false
  formRef.value.resetFields()
}
import {printDec } from "@/api/qpDec";
import {ElMessage} from 'element-plus'
import { useRouter } from 'vue-router';
 const router = useRouter()

const handlePrint = () => {
     let params ;
    
   if(checkList && checkList.value.length == 0){
      ElMessage({
            message: '请选择至少一种打印类型',
            type: 'error',
            showClose: true,
            offset: 50
          });
          return;
   }
  //  return;  
   params = {
     id:props.formData.id ,
     printType:checkList.value.join(","),
     ieFlag: props.formData.ieFlag,
     cusType: ''
   }


   printDec(params).then(res => {
 
     var resp = res.data
     if(resp.res == 'success'){
        let _data = JSON.parse(resp.data)


         var a = document.createElement('a')
        //  a.setAttribute('href', baseUrl.value + '/file/downLoad/' + file.id)
         let printUrl = 'http://172.16.24.197:8090/common-print/bgdPtFile/dowloadFile?path=' + _data.fileNameWithPath + '&isPrewView=1&fileName=fileName'
          // a.setAttribute('href', 'http://172.16.24.197:8090/common-print/bgdPtFile/dowloadFile?path=printTemp/2022/12/13/WI2010231002_1670893249952.pdf&isPrewView=1&fileName=1.pdf')
        //  a.setAttribute('href', printUrl)

        //  document.body.appendChild(a)
        //  a.click()
        // window.location.href = printUrl

        window.open(printUrl, '_self')

        //   router.push({
        //     name: '报关单打印',
        //   path: 'customsPrint',
        //   url: printUrl
        // //  query: pramSelf,
        // })
        




         props.dialogGood.show = false
         formRef.value.resetFields()

     }else{ 
        ElMessage({
            message: resp.res,
            type: 'error',
            showClose: true,
            offset: 50
          });
     }
    // let blob = new Blob([res.data], {type: 'application/octet-stream'});
    // let url = URL.createObjectURL(blob);
    // const link = document.createElement('a'); //创建a标签
    // link.href = url;
    // link.download = '报关单'+ getDateTime() +'.xlsx'; //重命名文件
    // link.click();
    // URL.revokeObjectURL(url);
  })
    

}




const checkList = ref([])
// // 查询配置 [pageNum 当前页数, pageSize 每页大小]


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


//定义变量
const tenderGroup = [
  { id: 'bgdHdd', realName: '报关单核对单',},
  { id: 'goodsFjy', realName: '报关单商品附加联', },
  { id: 'jzx', realName: '集装箱', },
  { id: 'jyjy', realName: '检验检疫', },
  { id: 'fxtzs', realName: '放行通知书', },
  { id: 'bgdm', realName: '报关单', },
]



// // 获取角色列表
 
 
// const resetForm = () => {
//   props.dialogRoles.show = false
// }

// defineExpose({ getList })
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
