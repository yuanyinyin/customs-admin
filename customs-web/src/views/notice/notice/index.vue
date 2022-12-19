<template>
  <div class="LogContainer scroll-y">

    <div class="head-container m-1 mr-3 rowSS">
      <span class="filter-item">
         <el-button type="primary" :icon="Plus" @click="handleAdd">新增</el-button>
      </span>
    </div>

    <el-card class="m-1">
      <template #header>
        <div class="clearfix">
          <span>通知公告列表</span>
        </div>
      </template>

      <el-table
        v-loading="loading"
        :data="tableData" stripe style="width: 100%"
      >
        <el-table-column fixed type="index" label="序号" align="center">
          <template #default="scope">
            <span>{{ (listQuery.page - 1) * listQuery.limit + scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="发布时间" align="center">
          <template #default="scope">
            {{parseTime(scope.row.updateTime) }}
          </template>
        </el-table-column>
<!--        <el-table-column prop="tips" label="通知类型" align="center"></el-table-column>-->
        <el-table-column prop="title" label="通知标题" align="center" >
           <template #default="scope">
              <span style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">{{scope.row.title}}</span>
            </template>
        </el-table-column>

        <el-table-column prop="creatorName" label="创建人" align="center"/>

        <el-table-column label="操作" align="center" show-overflow-tooltip min-width="100%">
          <template #default="scope">
            <el-button-group>
              <el-button type="primary" @click="viewDetail(scope.row)">查看详情</el-button>
              <el-button  type="warning" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button  type="danger" @click="handleDelete(scope.row)">删除</el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="block columnCC mt-2">
        <el-pagination
          v-show="total > 0"
          v-model:currentPage="listQuery.page"
          v-model:page-size="listQuery.limit"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleFilter"
          @current-change="getList"
        >
        </el-pagination>
      </div>
    </el-card>
    <dialog-notice :dialog-notice="dialogData" :form-data="formData" @handleSubmitAdd="handleSubmitAdd"
                   @handleSubmitEdit="handleSubmitEdit"/>
  </div>
</template>

<script setup lang="ts">
import {parseTime} from '@/utils/dateTime'
import {Ref} from "vue";
import {dialogTy} from "~/dialog";
import {Search,Plus} from '@element-plus/icons-vue'
import {getNoticeList,addNotice,deleteNotice,updateNotice} from "@/api/notice";
import DialogNotice from '@/views/notice/notice/dialogNotice.vue'
import {ElMessage, ElMessageBox} from 'element-plus'

const loading = false
const store = useStore()
// table显示日志数组
const tableData: any = ref(null)
// 日志总数
const total = ref(null)

const formData = ref({}) //这是相对自由点的写法，不定义类型
const dialogData: Ref<dialogTy> = ref({})//ts的规范写法，定义变量的类型

// 查询配置 [pageNum 当前页数, pageSize 每页大小]
const listQuery: any = ref({
  page: 1,
  limit: 10
})

onMounted(()=>{
  getList()
})

const getList = () =>{
    getNoticeList(listQuery._rawValue)
    .then((response) => {
      tableData.value = response.data.data
      total.value = response.data.total
    })
    .catch((response) => {
    })
}

const handleFilter = () => {
  listQuery.page = 1
  // 刷新日志列表
  getList()
}

/**
 * '新增'按钮触发
 * 空Dialog，标题‘新增通知公告’
 */
const handleAdd = () => {
  dialogData.value = {
    show: true,
    title: '新增通知公告',
    option: 'add'
  }
  formData.value = {
    /** 传向dialog的 空params
     */
    title:'',
    text:''
  }
}

function handleSubmitAdd() {
  addNotice(formData.value).then((res) => {
    dialogData.value.show = false
    ElMessage({message: '新增成功！', type: 'success'})
    // 刷新用户列表
    getList()
  })
}

function handleSubmitEdit() {
  updateNotice(formData.value.id, formData.value).then((res) => {
    dialogData.value.show = false
    ElMessage({message: '更新成功！', type: 'success'})
    // 刷新用户列表
    getList()
  })
}

const viewDetail = (row) => {
    dialogData.value = {
      show: true,
      title: '通知公告详情',
      option: 'detail'
    }
    formData.value = {
      id:row.id,
      title:row.title,
      text:row.text
    }
}

const handleEdit = (row) => {
  dialogData.value = {
    show: true,
    title: '修改',
    option: 'edit'
  }

  formData.value = {
    id: row.id,
    title:row.title,
    text:row.text
  }
}

/**
 * '删除'按钮触发
 */
const handleDelete = (row) => {
  ElMessageBox.confirm('是否确认删除该条数据, 是否继续?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteNotice( row.id)
        .then((response) => {
          ElMessage({message: '删除成功！', type: 'success'})
          getList()
        })
        .catch((response) => {
        })
  }).catch(() => {
  })
}

</script>
