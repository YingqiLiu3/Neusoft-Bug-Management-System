<template>
    <!-- model-value 实现单向绑定 -->
    <el-dialog
    :model-value="dialogVisible"
    title="添加Bug"
    width="500"
    :before-close="handleClose"
    @close="handleClose"
    >
    <el-form :model="form" ref="formRef" :rules="rules" label-width="auto" style="max-width: 600px">
    <el-form-item label="Bug名称" prop="bugName">
      <el-input v-model="form.bugName" />
    </el-form-item>
    <el-form-item label="Bug描述" prop="bugContent">
      <el-input v-model="form.bugContent" />
    </el-form-item>
    <!-- <el-form-item label="Bug创建人" prop="bugBuilder">
      <el-input v-model="form.bugBuilder" />
    </el-form-item> -->
    <el-form-item label="所属项目" prop="bugProject">
    <el-select v-model="form.bugProject" filterable placeholder="请选择" @change="initGetCases">
        <el-option
          v-for="item in projectOptions"
          :key="item.projectId"
          :label="item.projectName"
          :value="item.projectId">
        </el-option>
      </el-select>
      </el-form-item>
    <el-form-item label="所属用例" prop="testCaseId">
    <el-select v-model="form.testCaseId" filterable placeholder="请选择" @change="getModule">
      <el-option
        v-for="item in testCaseOptions"
        :key="item.testCaseId"
        :label="item.testCaseName"
        :value="item.testCaseId">
      </el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="Bug优先度">
      <el-radio-group v-model="form.bugGrade" size="medium">
        <el-radio border label="高"></el-radio>
        <el-radio border label="中"></el-radio>
        <el-radio border label="低"></el-radio>
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
import { defineEmits } from 'vue';
import { ref } from 'vue';
import  {createBugs} from '@/api/bugs'
import {getProjects} from '@/api/projects'
import {getTestCases} from '@/api/usecases'
import { useStore } from 'vuex';

const store = useStore()

const queryForm = ref({
  query: '',
})

// 接收父组件传来的数据
const emits = defineEmits(['update:modelValue','initGetBugs', 'queryForm'])
const handleClose = () => {
    emits('update:modelValue', false)
}

//  modelvalue是干嘛的////////////////////

// 从父组件传来的表单数据
/*const props = defineProps({
    queryForm:{
        type:queryForm,
        default:()=>0,
    },
    dialogEditVisible:{
        type:Boolean,
        default:()=>false
    }
})*/

const listPages = ref({
  current: 1,
  size: 1000
})

const formRef = ref(null)
// 使用formref.value.validate()进行表单验证
const handleConfirm = ()=>{
    formRef.value.validate(async (valid)=>{
        if(valid){
          console.log('success submit')
          const now = new Date();
          form.value.bugBuilder = store.getters.username
          form.value.bugCreateTime = now.toLocaleDateString() + ' ' + now.toLocaleTimeString();
          console.log('新bug信息', form.value)
          const res = await createBugs(form.value)
          emits('initGetBugs') //重新刷新项目列表
          handleClose()
        }else{
          console.log('error submit')
          return false
        }
    })
}

const projectOptions = ref([])
const projectSelected = ref({})

const initGetProjects = async() => {
  const res = await getProjects(queryForm.value)
  projectOptions.value = res
  console.log('所选项目', projectOptions.value)
}

const testCaseOptions = ref([])
const testCaseSelected = ref({})

const initGetCases = async(val) => {
  projectSelected.value = projectOptions.value.find(({ projectId }) => projectId === val)
  const res = await getTestCases(val, listPages.value)
  form.value.productManager = projectSelected.value.projectCreatedBy
  testCaseOptions.value = res.records
  console.log(testCaseOptions.value)
}

const getModule = (val) => {
  testCaseSelected.value = testCaseOptions.value.find(({ testCaseId }) => testCaseId === val)
  console.log(testCaseSelected.value.moduleId)
  form.value.bugModule = testCaseSelected.value.moduleId
}

const form = ref({
    "bugName": "",       // 项目名称
  "bugContent":"",  //项目描述
  "bugModule": "",
  "bugBuilder": "",  // 创建人
  "productManager": "",
  "bugGrade": "",
  "bugCreateTime":"", // 创建时间(注意这里是string类型)
  "bugSolveTime":"",
  "bugProject": "",
  "testCaseId": "" // 所属用例
})

const rules = ref({
    bugName: [
        { required: true, message: '请输入Bug名称', trigger: 'blur' }
      ],
      bugContent: [
        { required: true, message: '请输入Bug描述', trigger: 'blur' }
      ],
      bugBuilder: [
        { required: true, message: '请输入Bug创建人', trigger: 'blur' }
      ],
      bugProject: [
        { required: true, message: '请选择Bug所属项目', trigger: 'blur' }
      ],
      bugModule: [
        { required: true, message: '请选择Bug所属模块', trigger: 'blur' }
      ],
      testCaseId: [
        { required: true, message: '请选择Bug所属用例', trigger: 'blur' }
      ]
})

initGetProjects()

</script>