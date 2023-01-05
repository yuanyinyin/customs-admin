<template>
    <div class="goodsset-container scroll-y p">
        <el-card shadow="hover" :body-style="{ padding: '20px 20px 5px  20px ' }">
        <!-- <el-card> -->
       <div class="head-container">
          <el-form :inline="true" :model="listQuery" class="demo-form-inline">
            <el-form-item label="税则代码">
             <el-input
                    v-model="listQuery.hscode"
                    placeholder="税则代码"
                    style="width: 200px;margin-right:10px; "  clearable
            />
            </el-form-item>
            <el-form-item label="商品名称">
              <el-input v-model="listQuery.goodsName" placeholder="商品名称" clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="success" :icon="Search" @click="handleFilter">查询</el-button>
              <el-button type="primary" :icon="Plus" @click="handleAdd">添加</el-button>
              <el-button type="warning" :icon="Delete" @click="handleCancel1">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
                <el-table
                  v-model:data="tableData"
                  v-loading="loading"
                  stripe
                  style="width: 100%"
                  fit
                  border
                  highlight-current-row @selection-change="handleSelectionChange1"  :selectData1="selectData1"
                >
                <el-table-column type="selection" align="center" width="50" />
                <el-table-column type="index" label="序号" align="center" width="55" >
                  <template #default="scope">
                    <span>{{ (listQuery.page -1) * listQuery.limit + scope.$index +1 }}</span>
                  </template>
                </el-table-column>

                <el-table-column prop="hscode" label="税则代码" align="center"/>
                <el-table-column prop="goodsName" label="商品名称" align="center"/>
                  <el-table-column prop="ieflag" label="进出口标识" align="center">
                    <template #default="scope">
                      <el-tag v-if="scope.row.ieflag=== `I`" type="success">进口</el-tag>
                      <el-tag v-if="scope.row.ieflag  === `E`" type="warning">出口</el-tag>
                    </template>
                  </el-table-column>
                <el-table-column prop="createUser" label="操作员" align="center"/>
                <el-table-column label="操作" align="center">
                  <template #default="scope">
                  <el-button
                    v-for="(item, index) in btnInRows"
                    v-show="checkBtnShow(item.option, scope.row)"
                    :key="index"
                    :type="item.type"
                    @click="handleClick(item.option, scope.row)"
                  >
                    {{ item.text }}
                  </el-button>
                </template>
                </el-table-column>
              </el-table>
                  <div class="block columnCC mt-2">
            <el-pagination
              v-show="total > 0"
              v-model:currentPage="listQuery.page"
              v-model:page-size="listQuery.limit"
              :page-sizes="[10, 25, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="handleSizeChange"
              @current-change="getKeyGoods"
            >
            </el-pagination>
          </div>
          </el-card>
          <el-dialog v-model="tablemodel"  :dataTmp="dataTmp" :dataname="dataname" :datatype="datatype"
            width="1500px"
            align-center>
            <div class="head-container">
          <el-form :inline="true" :model="listQueryD" class="demo-form-inline" :rules="rules" >
            <el-form-item label="税则代码" prop="codets">
             <el-input
                    v-model="listQueryD.codets"
                    placeholder="税则代码"
                    style="width: 200px;margin-right:10px; "
                    class="filter-item" clearable
            />
            </el-form-item>
            <el-form-item label="商品名称" prop="gname">
              <el-input v-model="listQueryD.gname" placeholder="商品名称" clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="success" :icon="Search" @click="handleFilterD">查询</el-button>
              <el-button type="primary" :icon="Plus" @click="handleAddD">添加</el-button>
            </el-form-item>
          </el-form>
        </div>
            <el-table :data="tableDataD"  fit
                border
                height="500px"
                highlight-current-row @selection-change="handleSelectionChange"  :selectData="selectData" >
            <el-table-column type="selection" align="center" width="50" />
            <el-table-column type="index" label="序号" align="center" width="55"/>

            <el-table-column property="CODETS" label="税则代码" />
            <el-table-column property="GNAME" label="商品名称" />
              <el-table-column prop="IEFLAG" label="进出口标识" align="center">
                <template #default="scope">
                  <el-tag v-if="scope.row.IEFLAG=== `I`" type="success">进口</el-tag>
                  <el-tag v-if="scope.row.IEFLAG  === `E`" type="warning">出口</el-tag>
                </template>
              </el-table-column>
            </el-table>
            <div class="block columnCC mt-2">
            <el-pagination
              v-show="pageTotal > 0"
              v-model:currentPage="listQueryD.page"
              v-model:page-size="listQueryD.limit"
              :page-sizes="[10, 25]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="pageTotal"
              @size-change="handleSizeChangeD"
              @current-change="getGoodsD"
            >
            </el-pagination>
          </div>
        </el-dialog>

    </div>
    </template>

    <script setup lang="ts">
    import { Plus } from '@element-plus/icons-vue'
    import { Search,Delete } from '@element-plus/icons-vue'
    import { dialogTy } from '~/dialog'
    import {Ref} from 'vue'
    import {cancelGoods,cancelKeyGoods,addGoods} from "@/api/countSystemStatistics";

    import {ElMessage, ElMessageBox,ElTable} from 'element-plus'
    const store = useStore()

    // table显示数组
    const tableData: any = ref(null)
    const tableDataD: any = ref(null)
    const Tableref: any = ref(null)
    const loading = false
    const dataTmp = ref(null)
    const dataname = ref(null)
    const datatype = ref(null)
    const selectData = ref(null)
    const selectData1 = ref(null)
    const tablemodel = ref(false)
    // 总数
    const total = ref(null)
    const pageTotal = ref(0)

    // 查询配置 [pageNum 当前页数, pageSize 每页大小]
    const listQuery: any = ref({
      page: 1,
      limit: 10,
       })
    onMounted(() => {
      getKeyGoods()

    })
let handleAdd = () => {
    tablemodel.value=true;
}
const rules = ref({
  customNo: [{required: true, message: '请输入海关十位编码', trigger: 'blur'}],
      orgName: [{required: true, message: '请输入商品名称', trigger: 'blur'}],
    })

const  multipleSelection=[];
const  multipleSelection1=[];
const handleCancel1 = () => {
    if (selectData1.value==null||selectData1.value=='') {
        ElMessage({ message: '请选择一条记录', type: 'error' })
        return;
    }
    if (selectData1.value.length==0) {
        ElMessage({ message: '请选择一条记录', type: 'error' })
        return;
    }
    let Ids1 = []
    console.log(123,selectData1.value)
    let ids2 = ""
    let ids3 = ""
    selectData1.value.forEach((e:any,index:any) => {
    //    Ids1.push(e.id)
       if (index == 0) {
        console.log(1223,e)
            ids2 = e.id
            ids3 = e.goodsName
        }
        else {
            ids2 += "," + e.id
            ids3 += "," + e.goodsName
        }
     })
  ElMessageBox.confirm('取消重点商品设置,'+ids3+', 是否继续?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {

    let param={ids:ids2}
    cancelGoods(param)
    .then((res) => {
        if (res && res.code === 200) {
            ElMessage({ message: '取消重点商品设置成功', type: 'success' })
            // todo 无感刷新
            tablemodel.value=false;
            getKeyGoods();
        } else {
        ElMessage({ message: res.message, type: 'error' })
        }
    })
  })
}
let handleAddD = () => {
    if (selectData.value==null||selectData1.value=='') {
        ElMessage({ message: '请选择一条记录', type: 'error' })
        return;
    }
    if (selectData.value.length==0) {
        ElMessage({ message: '请选择一条记录', type: 'error' })
        return;
    }
    let Ids = []
    selectData.value.forEach(e => {
       Ids.push(e)
     })
  addGoods(Ids)
    .then((res) => {
        if (res && res.code === 200) {
            ElMessage({ message: '重点商品设置成功', type: 'success' })
            // todo 无感刷新
            tablemodel.value=false;
            getKeyGoods();
        } else {
        ElMessage({ message: res.message, type: 'error' })
        }
    })
    .catch((e) => {})
}
let handleSelectionChange1 = (val) => {
		// 假设取出 id 字段
		val.forEach(item => {
			const id = item.id
			// 判断数组中是否包含这个 id
			if ( multipleSelection1.indexOf(id) == -1) {
				 multipleSelection1.push(id)
			}
		})
        selectData1.value=val
}
let handleSelectionChange = (val) => {
		// 假设取出 id 字段
		val.forEach(item => {
			const id = item.id
			// 判断数组中是否包含这个 id
			if ( multipleSelection.indexOf(id) == -1) {
				 multipleSelection.push(id)
			}
		})
        selectData.value=val
}
// 选择表格行
const handleSelection = (selection, row) => {
	console.log(1111,row);
	console.log(4141244,selection.length);
}
/**查询部分 */
const listQueryD = reactive({
  codets: '',
  gname: '',
  page: 1,
  limit: 10
})
const getGoodsD = () => {
  if(listQueryD.codets==''||listQueryD.codets==''||listQueryD.gname==''||listQueryD.gname==null){
    ElMessage({ message: '请填写查询条件', type: 'warning' })
    return;
  }
    let params={
      codets: listQueryD.codets,
      gname: listQueryD.gname,
      page: listQueryD.page,
      limit: listQueryD.limit,
    }
  store
    .dispatch('countSystemStatistics/getGoodsList',params)
    .then((response) => {
      tableDataD.value = response.items
      pageTotal.value = response.total

    })
    .catch((response) => {})
}

    // 列表展现已经设置重点商品的名单
const getKeyGoods = () => {

    store
    .dispatch('countSystemStatistics/getKeyGoods', listQuery._rawValue)
    .then((response) => {
      console.log('eee',response)
      tableData.value = response.items
      total.value = response.total
    })
    .catch((response) => {})
  // console.log(`current page: ${val}`)
}
    /**
     * 分页大小修改处理
     */
    const handleSizeChange = () => {
      listQuery.page = 1
      getKeyGoods()
    }
    const handleSizeChangeD = () => {
      listQueryD.page = 1
      getGoodsD()
    }
    /**
     * 查询框处理
     */
    const handleFilter = () => {
      listQuery.page = 1
      getKeyGoods()
    }
    const handleFilterD= () => {
      listQueryD.page = 1
      getGoodsD()
    }
 /**按钮部分 */
const btnInRows = ref(
  [
    {
      text: '取消重点商品',
      type: 'text',
      option: 'cancel',
    },
  ]
)
// const btnInRowsD = ref(
//   [
//     {
//       text: '重点商品',
//       type: 'text',
//       option: 'add',
//     },
//   ]
// )
const current = ref(-1)
//处理按钮的点击事件
const handleClick = (op: string, ...restPara: any) => {
  console.log(restPara)
  if (op == 'cancel') {
    handleCancelGoods(restPara[0])
  }
}
 //按钮显示条件
const checkBtnShow = (option:any, row:any) => {

  return true
}
const handleClickD = (op: string, ...restPara: any) => {
  console.log(restPara)
  if (op == 'add') {
    handleAddCompay(restPara[0])
  }
}
 //按钮显示条件
const checkBtnShowD = (option:any, row:any) => {

  return true
}
const handleCancelGoods = (row) => {
  ElMessageBox.confirm('取消重点商品设置,'+row.goodsName+', 是否继续?', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    cancelKeyGoods(row.id)
      .then(res => {
        ElMessage({ message: '取消重点商品设置成功', type: 'success' })
        // todo 无感刷新
        getKeyGoods()
      })
  }).catch(() => {

  })
}


    </script>

    <style scoped lang="scss"></style>

