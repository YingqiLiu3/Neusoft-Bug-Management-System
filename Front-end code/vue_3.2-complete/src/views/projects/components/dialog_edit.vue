<template>
    <!-- model-value 实现单向绑定 -->
    <el-dialog
    :model-value="dialogEditVisible"
    title="编辑项目"
    width="1000"
    :before-close="handleClose"
    @close="handleClose"
    >
    <el-form :model="form" ref="formRef" :rules="rules" label-width="auto" style="max-width: 600px">
    <el-form-item label="项目名称" prop="projectName">
      <el-input v-model="form.projectName" />
    </el-form-item>
    <el-form-item label="项目描述" prop="projectDescription">
      <el-input v-model="form.projectDescription" />
    </el-form-item>
    <!-- <el-form-item label="项目创建人" prop="projectCreatedBy">
      <el-input v-model="form.projectCreatedBy" />
    </el-form-item> -->
  </el-form>
    <div style="display: flex; justify-content: center; font-weight: bold;">
        <span>项目模块表</span>
    </div>
    <div style="display: flex; justify-content: flex-end;">
        <el-button type="primary" @click="handleDialog">添加项目模块</el-button>
    </div>
    <div style="display: flex; justify-content: center; font-weight: bold;">
      <el-pagination
      @current-change="handleCurrentChangeModule"
      :current-page="currentPageModule"
      layout="prev, pager, next"
      :page-size="10"
      :total="currentTotalModule">
    </el-pagination>
    </div>
        <el-table :data="tableData" stripe style="width: 100%" >
        <el-table-column  v-for="(item,index) in options" :key="index" :label="item.label" :prop="item.props">
            
            <template #default="{row}" v-if="item.props === 'action'">
            <el-button type="danger" :icon="Delete" circle @click="confirmDeleteModule(row)" />
            </template>
        </el-table-column>
        </el-table>
    <br/>
    <div style="display: flex; justify-content: center; font-weight: bold;">
        <span>项目人员表</span>
    </div>
    <div style="display: flex; justify-content: flex-end;">
        <el-button type="primary" @click="handleDialogUser">添加项目人员</el-button>
    </div>
    <div style="display: flex; justify-content: center; font-weight: bold;">
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      layout="prev, pager, next"
      :page-size="10"
      :total="currentTotal">
    </el-pagination>
    </div>
        <el-table :data="userList" stripe style="width: 100%">
        <el-table-column  v-for="(item,index) in useroptions" :key="index" :label="item.label" :prop="item.props">
            
            <template #default="{row}" v-if="item.props === 'action'">
            <el-button type="danger" :icon="Delete" circle @click="confirmDeleteMember(row)" />
            </template>
        </el-table-column>
        </el-table>
    <span v-if="dialogModuleVisible">
    <DialogMuduleEdit v-model="dialogModuleVisible" :projectId="projectId" @initGetModules="initGetModules"></DialogMuduleEdit></span>
    <span v-if="dialogUserVisible">
    <DialogUserEdit v-model="dialogUserVisible" @initGetMembers="initGetMembers" :projectId="projectId" :dialogUserVisible="dialogUserVisible"></DialogUserEdit></span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleConfirm">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>
  
</template>

<script setup>
// 为了处理父组件传来的数据
import {Delete} from '@element-plus/icons-vue'
import { defineEmits, defineProps, watch } from 'vue';
import { ref } from 'vue';
import  {createProjects, getModules, getMembers, submitProjects, deleteModule, deleteMember} from '@/api/projects'
import { useStore } from 'vuex';
import {options} from './options'
import {useroptions} from './useroptions'
import DialogMuduleEdit from './dialog_module/dialog_module.vue'
import DialogUserEdit from './dialog_user/dialog_user.vue'
import { ElMessageBox } from 'element-plus';

console.log("testtest",useroptions)
console.log("testtest",options)
const form = ref({
    "projectName": "",       // 项目名称
  "projectDescription":"",  //项目描述
  "projectCreatedBy": "",  // 创建人
  "projectCreatedTime":""//创建时间(注意这里是string类型)
})

// 从父组件传来的表单数据
const props = defineProps({
    dialogEditTable:{
        tpye:Object,
        default:()=>{}
    },
    projectId:{
        type:Number,
        default:()=>0,
    },
    dialogEditVisible:{
        type:Boolean,
        default:()=>false
    }
})


const projectId = ref()
projectId.value = props.projectId
console.log('projectId',projectId.value)

// 监听父组件传来的数据(仅限于项目中的数据)，并更改
watch(()=>props.dialogEditTable, (newVal)=>{
    form.value = newVal
},{deep:true,immediate:true
})

const store = useStore()


// 接收父组件传来的数据
const emits = defineEmits(['update:modelValue','initProjectList'])
const handleClose = () => {
    emits('update:modelValue', false)
}

const formSubmit = ref({
  "projectId": projectId.value,
  "projectName": "",
  "projectDescription":""
})

const formRef = ref(null)
// 使用formref.value.validate()进行表单验证
const handleConfirm = ()=>{
    formRef.value.validate(async (valid)=>{
        if(valid){
          console.log('success submit')
          const now = new Date();
          form.value.projectCreatedBy = store.getters.username
          form.value.projectCreatedTime = now.toLocaleDateString() + ' ' + now.toLocaleTimeString();
          formSubmit.value.projectName = form.value.projectName
          formSubmit.value.projectDescription = form.value.projectDescription
          console.log(formSubmit.value)
          const res = await submitProjects(formSubmit.value)

          emits('initProjectList') //重新刷新项目列表
          handleClose()
        }else{
          console.log('error submit')
          return false
        }
    })
}



const rules = ref({
    projectName: [
        { required: true, message: '请输入项目名称', trigger: 'blur' }
      ],
      projectDescription: [
        { required: true, message: '请输入项目描述', trigger: 'blur' }
      ],
})

// ____________________________________________________________________________________
// 添加项目模块



const dialogModuleVisible = ref(false)
const handleDialog = () => {
    dialogModuleVisible.value = true
    //console.log('dialogModuleVisible',dialogModuleVisible.value)
}


const formMoudle = ref({
  current: 1,
  size: 10,
})

const currentPageModule = ref()
const currentTotalModule = ref()

//初始化模块列表
// const ModulesList = ref([])

// 项目模块列表
const tableData = ref([])

const initGetModules = async() => {
    // 发送路由请求，获得模块列表
    formMoudle.value.current = currentPageModule.value
    const res = await getModules(projectId.value,formMoudle.value)
    
    // ModulesList.value = tableData.value.records
    console.log(res)
    currentPageModule.value = res.current
    currentTotalModule.value = res.pages
    console.log("总数：",currentTotalModule.value)
    tableData.value = res.records
    console.log('初始化模块请求已经发送', tableData.value)
}



// 项目翻页功能
const handleCurrentChangeModule = (val) => {
  currentPageModule.value = val
  initGetModules()
}

const tem = ref({})
const moduleId = ref()

// 模块删除功能
const handleDeleteModule = async(row) => {
  tem.value = JSON.parse(JSON.stringify(row))
  console.log(tem.value)
  moduleId.value = tem.value.moduleId
  console.log(moduleId.value)
  await deleteModule(moduleId.value)
  initGetModules()
}

const confirmDeleteModule = (row) => {  
  ElMessageBox.confirm('你确定要删除这个模块吗？', '确认删除', {  
    confirmButtonText: '确定',  
    cancelButtonText: '取消',  
    type: 'warning',  
  })  
    .then(async () => {  
      await handleDeleteModule(row);  
    })  
    .catch(() => {  
      console.log('删除操作已取消');  
    });  
}

// ____________________________________________________________________________________
// 添加人员

const dialogUserVisible = ref(false)
const handleDialogUser = () => {
    dialogUserVisible.value = true
    //console.log('dialogModuleVisible',dialogModuleVisible.value)
} 

//初始化人员列表

const formMember = ref({
  pageNum: 1,
  pageSize: 10,
  projectId: projectId.value
})

const tableDataUser = ref([])
const userList = ref([])
let currentPage = 1
let currentTotal = 1
const initGetMembers = async() => {
  formMember.value.pageNum = currentPage
  tableDataUser.value = await getMembers(formMember.value)
  console.log(tableDataUser.value)
  userList.value = tableDataUser.value.records
  currentPage = tableDataUser.value.current
  currentTotal = tableDataUser.value.total
  console.log('初始化用户请求已经发送', typeof tableDataUser.value, tableDataUser.value.records)
  console.log(userList.value)
}

// 人员列表翻页功能
const handleCurrentChange = (val) => {
  currentPage = val
  initGetMembers()
}

const userId = ref()
const tem2 = ref({})
// 移除项目人员
const handleDeleteMember = async(row) => {
  tem2.value = JSON.parse(JSON.stringify(row))
  console.log(tem2.value)
  userId.value = tem2.value.userId
  console.log(userId.value)
  await deleteMember(userId.value, projectId.value)
  initGetMembers()
}

const confirmDeleteMember = (row) => {  
  ElMessageBox.confirm('你确定要移除这个人员吗？', '确认移除', {  
    confirmButtonText: '确定',  
    cancelButtonText: '取消',  
    type: 'warning',  
  })  
    .then(async () => {  
      await handleDeleteMember(row);  
    })  
    .catch(() => {  
      console.log('删除操作已取消');  
    });  
}

if(props.dialogEditVisible){
    initGetModules()
    initGetMembers()
}



</script>