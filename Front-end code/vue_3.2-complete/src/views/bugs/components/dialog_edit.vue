<template>
    <!-- model-value 实现单向绑定 -->
    <el-dialog
    :model-value="dialogEditVisible"
    title="编辑Bug"
    width="1000"
    :before-close="handleClose"
    @close="handleClose"
    >
    <el-form :model="form" ref="formRef" :rules="rules" label-width="auto" style="max-width: 600px">
      <el-form-item label="Bug名称" prop="bugName">
        <el-input v-model="form.bugName" :disabled="role == '开发工程师'"/>
      </el-form-item>
      <el-form-item label="Bug描述" prop="bugContent">
        <el-input v-model="form.bugContent" :disabled="role == '开发工程师'"/>
      </el-form-item>
      <el-form-item label="Bug创建人" prop="bugBuilder">
        <el-input v-model="form.bugBuilder" :disabled="true"/>
      </el-form-item>
      <el-form-item label="Bug创建时间" prop="bugCreateTime">
        <el-input v-model="form.bugCreateTime" :disabled="true"/>
      </el-form-item>
      <el-form-item label="最近修改时间" prop="bugSolvedTime">
        <el-input v-model="form.bugSolvedTime" :disabled="true"/>
      </el-form-item>
      <el-form-item label="Bug优先度">
      <el-radio-group v-model="form.bugGrade" size="medium" @change="gradeChange">
        <el-radio border label="高" :disabled="role == '开发工程师'"></el-radio>
        <el-radio border label="中" :disabled="role == '开发工程师'"></el-radio>
        <el-radio border label="低" :disabled="role == '开发工程师'"></el-radio>
      </el-radio-group>
      </el-form-item>
      <el-form-item label="所属模块" prop="moduleName">
        <el-input v-model="moduleName" :disabled="true"/>
      </el-form-item>
      <el-form-item label="所属用例" prop="testCaseId">
        <!--出于易读性，这里本来应该显示用例的名字而非id，然后后端并没有写完-->
        <el-input v-model="form.testCaseId" :disabled="true"/>
      </el-form-item>
      <el-form-item label="用例步骤" prop="testCaseSteps">
        <el-input v-model="testCaseSteps" :disabled="true"/>
      </el-form-item>
      <el-form-item label="Bug状态">
      <el-radio-group v-model="form.bugState" size="medium" @change="stateChange" class="radio-group">
        <el-radio border label="新创建" :disabled="role == '开发工程师'"></el-radio>
        <el-radio border label="待修改" :disabled="role != '开发工程师'"></el-radio>
        <el-radio border label="修改中" :disabled="role != '开发工程师'"></el-radio>
        <el-radio border label="已修改" :disabled="role != '开发工程师'"></el-radio>
        <el-radio border label="待协商"></el-radio>
        <el-radio border label="已完成" :disabled="role == '开发工程师'"></el-radio>
        <el-radio border label="暂不修改" :disabled="role != '开发工程师'"></el-radio>
        <el-radio border label="永不修改" :disabled="role != '开发工程师'"></el-radio>
      </el-radio-group>
      </el-form-item>
    </el-form>
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
import { defineEmits, defineProps, watch } from 'vue';
import { ref } from 'vue';
import  {createBugs, submitBug, resolveBug} from '@/api/bugs'
import {getSingleModule} from '@/api/projects'
import {getTestCases} from '@/api/usecases'
import { useStore } from 'vuex';

const form = ref({
  "bugId":"",
  "bugName": "",       // bug名称
  "bugContent":"",  // bug描述
  "bugModule": "", // bug所属模块
  "bugBuilder": "",  // 创建人
  "productManager":"",
  "bugGrade": "", // bug等级
  "bugCreateTime":"", // 创建时间(注意这里是string类型)
  "bugSolvedTime":"", // bug解决时间
  "bugProject": "",
  "testCaseId": "", // 测试用例Id
  "bugState": ""
})

const role = localStorage.getItem('role')
const testCaseSteps = ref('这里应该描述用例步骤，然而测试用例部分并没有后端') // 这里应该描述用例步骤，然而测试用例部分并没有后端

// 从父组件传来的表单数据
const props = defineProps({
    dialogEditTable:{
        tpye:Object,
        default:()=>{}
    },
    bugId:{
        type:Number,
        default:()=>0,
    },
    dialogEditVisible:{
        type:Boolean,
        default:()=>false
    }
})


const bugId = ref()
bugId.value = props.bugId
console.log('bugId',bugId.value)

// 监听父组件传来的数据(仅限于用例中的数据)，并更改
watch(()=>props.dialogEditTable, (newVal)=>{
    form.value = newVal
},{deep:true,immediate:true
})

const store = useStore()


// 接收父组件传来的数据
const emits = defineEmits(['update:modelValue','initGetBugs'])
const handleClose = () => {
    emits('update:modelValue', false)
}
// modelvalue是干嘛的来着///////////////////////////

const moduleName = ref('')
const getModuleName = async() => {
  const res = ref(await getSingleModule(form.value.bugModule))
  console.log(res.value.moduleName)
  moduleName.value = res.value.moduleName
}

const stateChange = (val) => {
  form.value.bugState = val
  console.log('changeState', form.value.bugState)
}

const gradeChange = (val) => {
  form.value.bugGrade = val
  console.log('changeGrade', form.value.bugGrade)
}

const formRef = ref(null)
// 使用formref.value.validate()进行表单验证
const handleConfirm = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
          //console.log('success submit')
          const now = new Date()
          if (form.value.bugState === "已修改" || form.value.bugState === "永不修改") {
            //console.log('try resolve')
            // const rez = await resolveBug(bugId.value)
            form.value.bugSolvedTime = now.toLocaleDateString() + ' ' + now.toLocaleTimeString()
            //console.log('sucess resolve')
          }
          await submitBug(bugId.value, form.value)
          //console.log('sucess resolvezckjzxkljclsjadl')
          emits('initGetBugs') //重新刷新项目列表
          handleClose()
        } else {
          console.log('error submit')
          return false
        }
    })
}

const listPages = ref({
  size: 1000,
  current: 1
})

const testCaseSelected = ref()

const getCaseSteps = async() => {
  const res = await getTestCases(form.value.bugProject, listPages.value)
  console.log('res:', res)
  res.value = res.records
  testCaseSelected.value = res.value.find(({ testCaseId }) => testCaseId === form.value.testCaseId)
  console.log(testCaseSelected.value)
  testCaseSteps.value = testCaseSelected.value.testSteps
}

const rules = ref({
    bugName: [
        { required: true, message: '请输入Bug名称', trigger: 'blur' }
      ],
      bugContent: [
        { required: true, message: '请输入Bug描述', trigger: 'blur' }
      ],
})
getCaseSteps()
getModuleName()

</script>

<style>  
.radio-group {  
    display: flex;  
    flex-wrap: wrap; /* 允许换行 */  
}  

.radio-group .el-radio {  
    width: 30%; /* 每个单选框占据一半的宽度，即四个一行 */  
    box-sizing: border-box; /* 确保内边距和边框计算在宽度内 */  
}  
</style>  