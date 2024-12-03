<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="11">
      <el-select v-model="projectSelect" filterable placeholder="请选择" @change="initGetTestCases">
        <el-option
          v-for="item in projectOptions"
          :key="item.projectId"
          :label="item.projectName"
          :value="item.projectId">
        </el-option>
      </el-select>
    </el-col>
    <el-col :span="12">
        <el-button type="primary" @click="handleDialog">创建用例</el-button>
      </el-col>
    </el-row>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      layout="prev, pager, next"
      :page-size="10"
      :total="currentTotal">
    </el-pagination>
    <el-table :data="CaseList" stripe style="width: 100%">
      <el-table-column  v-for="(item,index) in options" :key="index" :label="item.label" :prop="item.props">
        <template #default="{row}" v-if="item.props === 'action'">
          <el-button type="danger" :disabled="role == '开发工程师'" :icon="Delete" circle @click="confirmDelete(row)" />
          <el-button type="primary" :disabled="role == '开发工程师'" :icon="Edit" circle  @click="handleDialogEdit(row)"/>
          <el-button type="text" @click="handleDialogView(row)">查看更多</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <!-- 传入子组件的变量和函数，需要在这里传入 -->
  <Dialog v-model="dialogVisible" @initGetTestCases="reGetTestCases" :queryForm="queryForm"></Dialog>
  <!-- 必须等点击编辑按钮之后，才能开始传数据，否则数据传送异步 -->
  <span v-if="dialogEditVisible">  
    <DialogEdit v-model="dialogEditVisible" @initGetTestCases="reGetTestCases" :dialogEditTable="dialogEditTable"
  :testCaseId="testCaseId" :dialogEditVisible="dialogEditVisible"
  ></DialogEdit></span>
  <!-- 必须等点击查看详情按钮之后，才能开始传数据，否则数据传送异步 -->
  <span v-if="dialogViewVisible">  
    <DialogView v-model="dialogViewVisible" @initGetTestCases="reGetTestCases" :dialogViewTable="dialogViewTable"
  :testCaseId="testCaseId" :dialogViewVisible="dialogViewVisible"
  ></DialogView></span>
</template>

<script setup>
import { Search,Delete,Edit } from '@element-plus/icons-vue';
import {ref} from 'vue'
import {getTestCases, deleteTestCase} from '@/api/usecases'
import {getProjects} from '@/api/projects'
import {options} from './options'
import Dialog from './components/dialog.vue'
import DialogEdit from './components/dialog_edit.vue';
import DialogView from './components/dialog_view.vue';
import { ElMessageBox } from 'element-plus';

const queryForm = ref({
  query: '',
})

const role = localStorage.getItem('role')
const tableData = ref([])

// 下拉菜单获取项目列表
const projectSelect = ref()
const projectOptions = ref([])

const initGetProjects = async() => {
  const res = await getProjects(queryForm.value)
  projectOptions.value = res
  console.log('所选项目', projectOptions.value)
}

//控制添加用例的弹窗的显示
const dialogVisible=ref(false)

const handleDialog = () => {
  dialogVisible.value = true
}
//控制编辑用例的弹窗
const dialogEditVisible=ref(false)

// 编辑bug的弹窗中的编辑bug部分
const dialogEditTable = ref({})

//记录用例的ID
const testCaseId = ref()

const handleDialogEdit = async (row) => {
  dialogEditVisible.value = true
  //拿到代理对象的属性，使用JOSN.stringify()方法，将对象转换为字符串，再使用JSON.parse()方法，将字符串转换为对象
  dialogEditTable.value = JSON.parse(JSON.stringify(row))
  console.log(row)
  testCaseId.value = dialogEditTable.value.testCaseId
  // // 发送路由请求，获得？？列表
  // ModulesList.value = await getModules(dialogEditTable.value.testCaseId)
}

const tem = ref({})

const confirmDelete = (row) => {  
  ElMessageBox.confirm('你确定要删除这个测试用例吗？', '确认删除', {  
    confirmButtonText: '确定',  
    cancelButtonText: '取消',  
    type: 'warning',  
  })  
    .then(async () => {  
      await handleDelete(row);  
    })  
    .catch(() => {  
      console.log('删除操作已取消');  
    });  
}

const handleDelete = async(row) => {
  tem.value = JSON.parse(JSON.stringify(row))
  testCaseId.value = tem.value.testCaseId
  console.log(testCaseId.value)
  await deleteTestCase(testCaseId.value)
  console.log('success')
  initGetTestCases(projectId)
}

//控制查看用例的弹窗
const dialogViewVisible=ref(false)
// 编辑bug的弹窗中的编辑bug部分
const dialogViewTable = ref({})
const handleDialogView = async (row) => {
  dialogViewVisible.value = true
  //拿到代理对象的属性，使用JOSN.stringify()方法，将对象转换为字符串，再使用JSON.parse()方法，将字符串转换为对象
  dialogViewTable.value = JSON.parse(JSON.stringify(row))
  console.log(row)
  testCaseId.value = dialogViewTable.value.testCaseId
}

const form = ref({
  size: 10,
  current: 1

})

let currentPage = 1
let currentTotal = 1

let projectId = 0

const handleCurrentChange = (val) => {
  currentPage = val
  initGetTestCases(projectId)
}

const tableDataTestCases = ref([])
const CaseList = ref([])

const initGetTestCases = async(val) => {
  console.log(val)
  projectId = val
  form.value.current = currentPage
  tableDataTestCases.value = await getTestCases(val, form.value)
  console.log(tableDataTestCases.value)
  currentPage = tableDataTestCases.value.current
  currentTotal = tableDataTestCases.value.total
  CaseList.value = tableDataTestCases.value.records
  console.log('初始化用例请求已经发送', CaseList.value)
}

const reGetTestCases = () => {
  initGetTestCases(projectId)
}

initGetProjects()


</script>

<style lang="scss" scoped>
.header{
  padding-bottom:24px;
  box-sizing: border-box;
}
</style>
