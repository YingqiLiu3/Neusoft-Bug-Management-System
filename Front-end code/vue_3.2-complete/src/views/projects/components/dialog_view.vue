<template>
    <!-- model-value 实现单向绑定 -->
    <el-dialog
    :model-value="dialogViewVisible"
    title="查看项目"
    width="1000"
    :before-close="handleClose"
    @close="handleClose"
    >
    <el-form :model="form" ref="formRef" :rules="rules" label-width="auto" style="max-width: 600px">
    <el-form-item label="项目名称" prop="projectName">
      <div>
        {{ form.projectName }}
      </div>
    </el-form-item>
    <el-form-item label="项目描述" prop="projectDescription">
      <div>
        {{ form.projectDescription }}
      </div>
    </el-form-item>
  </el-form>

    <div style="display: flex; justify-content: center; font-weight: bold;">
        <span>项目模块表</span>
    </div>
    <!-- 翻页 -->
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
            </template>
        </el-table-column>
        </el-table>
    <br/>

    <div style="display: flex; justify-content: center; font-weight: bold;">
        <span>项目人员表</span>
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
            
            <template #default="{row2}" v-if="item.props === 'action'">
            </template>
        </el-table-column>
        </el-table>
    <span v-if="dialogModuleVisible">
    <DialogMuduleEdit v-model="dialogModuleVisible" :projectId="projectId" @initGetModules="initGetModules"></DialogMuduleEdit></span>
    <span v-if="dialogUserVisible">
    <DialogUserEdit v-model="dialogUserVisible" @initGetMembers="initGetMembers" :projectId="projectId" :dialogUserVisible="dialogUserVisible"></DialogUserEdit></span>
    
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">关闭</el-button>
      </div>
    </template>
  </el-dialog>
  
</template>

<script setup>
// 处理父组件传来的数据
import {Delete} from '@element-plus/icons-vue'
import { defineEmits, defineProps, watch } from 'vue';
import { ref } from 'vue';
import  {createProjects, getModules, getMembers, submitProjects, deleteModule} from '@/api/projects'
import { useStore } from 'vuex';
import {options} from './options'
import {useroptions} from './useroptions'
import DialogMuduleEdit from './dialog_module/dialog_module.vue'
import DialogUserEdit from './dialog_user/dialog_user.vue'

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
    dialogViewTable:{
        tpye:Object,
        default:()=>{}
    },
    projectId:{
        type:Number,
        default:()=>0,
    },
    dialogViewVisible:{
        type:Boolean,
        default:()=>false
    }
})


const projectId = ref()
projectId.value = props.projectId
console.log('projectId',projectId.value)

// 监听父组件传来的数据
watch(()=>props.dialogViewTable, (newVal)=>{
    form.value = newVal
},{deep:true,immediate:true
})

const store = useStore()


// 接收父组件传来的数据
const emits = defineEmits(['update:modelValue','initProjectList'])
const handleClose = () => {
    emits('update:modelValue', false)
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

// 初始化时加载数据
if(props.dialogViewVisible){
    initGetModules()
    initGetMembers()
}

</script>

<style>
.no-disabled-style .el-input__inner:disabled {
  background-color: white !important; /* 恢复背景颜色 */
  color: #000000 !important; /* 恢复字体颜色 */
  border-color: #dcdfe6 !important; /* 恢复边框颜色 */
  cursor: not-allowed; /* 保持禁止状态的光标 */
}

</style>