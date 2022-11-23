<template>
  <div>
    <el-dialog v-model="dialogRisk.show" :title="dialogRisk.title" width="40%" center @close="resetForm">
      <div style="height:500px">
        <el-scrollbar>
          <div>
            <el-form ref="refRiskForm" :model="formData" label-width="100px">
              <el-form-item label="走私风险等级" prop="riskLevel">
                <el-select v-model="formData.riskLevel" style="width: 200px">
                  <el-option label="低" value="1"></el-option>
                  <el-option label="中" value="2"></el-option>
                  <el-option label="高" value="3"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="风险原因" prop="riskReason" v-if="formData.riskLevel != 1">
                <el-input v-model="formData.riskReason" type="textarea"/>
              </el-form-item>
              <el-form-item label="监管举措" prop="manageMeasure" v-if="formData.riskLevel != 1">
                <el-input v-model="formData.manageMeasure" type="textarea"/>
              </el-form-item>
              <el-form-item label="监管负责人" prop="supervisePersonList" v-if="formData.riskLevel != 1">
                <el-table :data="formData.supervisePersonList" :border="true">
                  <el-table-column prop="name" label="姓名">
                    <template #default="scope">
                      <span v-show="!showInput(scope.row)">{{scope.row.name}}</span>
                      <el-input v-show="showInput(scope.row)" v-model="scope.row.name" style="width:100px"/>
                    </template>
                  </el-table-column>
                  <el-table-column prop="job" label="职务">
                    <template #default="scope">
                      <span v-show="!showInput(scope.row)">{{scope.row.job}}</span>
                      <el-input v-show="showInput(scope.row)" v-model="scope.row.job" style="width:100px"/>
                    </template>
                  </el-table-column>
                  <el-table-column prop="telephone" label="电话">
                    <template #default="scope">
                      <span v-show="!showInput(scope.row)">{{scope.row.telephone}}</span>
                      <el-input v-show="showInput(scope.row)" v-model="scope.row.telephone" style="width:100px"/>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作">
                    <template #default="scope">
                      <el-button style="margin-left: 12px" type="text" v-show="!showInput(scope.row)" @click="handleEdit(scope.row)">修改</el-button>
                      <el-button type="text" @click="deletePerson(scope.row)">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <el-button style="width: 100%" @click="onAddItem">增加+</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-scrollbar>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="saveForm">保存</el-button>
          <el-button @click="resetForm">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import {PropType} from "vue";
import {dialogTy} from "~/dialog";
import {ElMessage} from "element-plus";
import {updateRisk} from "@/api/point";

const refRiskForm: any = ref(null)
const props = defineProps({
  dialogRisk: {
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

const emit = defineEmits(['audit'])

const saveForm = () => {
  updateRisk(props.formData).then(res=>{
    props.dialogRisk.show = false
    ElMessage({message: '保存成功', type: 'success'})
    emit('audit')
  })
}

const deletePerson = (row) => {
  var index = props.formData.supervisePersonList.indexOf(props.formData.supervisePersonList[row.id-1])
  props.formData.supervisePersonList.splice(index,1)
}

const onAddItem = () => {
  const newLine = {
    name:'',
    job:'',
    telephone:'',
  }
  props.formData.supervisePersonList.push(newLine)
}

const showInput = (row) => {
  if(row.id == null){
    return true
  }else{
    return row.show
  }
}

const handleEdit = (row) => {
  row.show = true
}
const handleSave = (row) =>{
  row.show = false
}

const resetForm = () => {
  props.dialogRisk.show = false
  refRiskForm.value.resetFields()
}
</script>
