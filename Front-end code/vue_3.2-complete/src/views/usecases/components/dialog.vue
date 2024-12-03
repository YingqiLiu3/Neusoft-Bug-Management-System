<template>
  <!-- model-value 实现单向绑定 -->
  <el-dialog
  :model-value="dialogVisible"
  title="添加用例"
  width="500"
  :before-close="handleClose"
  @close="handleClose"
  >
  <el-form :model="form" ref="formRef" :rules="rules" label-width="auto" style="max-width: 600px">
  <el-form-item label="用例名称" prop="testCaseName">
    <el-input v-model="form.testCaseName" />
  </el-form-item>
  <el-form-item label="用例目的" prop="testPurpose">
    <el-input v-model="form.testPurpose" />
  </el-form-item>
  <el-form-item label="前置条件" prop="preconditions">
    <el-input v-model="form.preconditions" />
  </el-form-item>
  <el-form-item label="用例步骤" prop="testSteps">
    <el-input v-model="form.testSteps" />
  </el-form-item>
  <el-form-item label="预期结果" prop="expectedResult">
    <el-input v-model="form.expectedResult" />
  </el-form-item>
  <el-form-item label="所属项目" prop="projectId">
  <el-select v-model="form.projectId" filterable placeholder="请选择" @change="initGetModules">
      <el-option
        v-for="item in projectOptions"
        :key="item.projectId"
        :label="item.projectName"
        :value="item.projectId">
      </el-option>
    </el-select>
    </el-form-item>
  <el-form-item label="所属模块" prop="moduleId">
  <el-select v-model="form.moduleId" filterable placeholder="请选择" >
    <el-option
      v-for="item in moduleOptions"
      :key="item.moduleId"
      :label="item.moduleName"
      :value="item.moduleId">
    </el-option>
   </el-select>
   </el-form-item>
    <el-form-item label="用例优先度" prop="priority">
    <el-radio-group v-model="form.priority" size="medium">
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
import  {createTestCase} from '@/api/usecases'
import {getProjects, getModules} from '@/api/projects'
import { useStore } from 'vuex';

const store = useStore()

const queryForm = ref({
query: '',
})

// 接收父组件传来的数据
const emits = defineEmits(['update:modelValue','initGetTestCases', 'queryForm'])
const handleClose = () => {
  emits('update:modelValue', false)
}

//  modelvalue是干嘛的////////////////////

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
        form.value.creator = store.getters.username
        console.log('新用例信息', form.value)
        const res = await createTestCase(form.value)
        emits('initGetTestCases') //重新刷新项目列表
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

const moduleOptions = ref([])

const initGetModules = async(val) => {
projectSelected.value = projectOptions.value.find(({ projectId }) => projectId === val)
form.value.productManager = projectSelected.value.projectCreatedBy
const res = await getModules(val, listPages.value)
moduleOptions.value = res.records
}

const form = ref({
  "creator": "",  // 创建人
  "expectedResult":"", // 用例目的
  "moduleId": "", // bug所属模块
  "preconditions":"", // 用例前置条件
  "priority": "", // 用例优先度
  "projectId": "",
  "testCaseName": "", // 用例名称
  "testPurpose":"",  // 用例目的
  "testSteps": "", // 测试步骤
})

const rules = ref({
    testCaseName: [
      { required: true, message: '请输入用例名称', trigger: 'blur' }
    ],
    testPurpose: [
      { required: true, message: '请输入用例目的', trigger: 'blur' }
    ],
    testSteps: [
      { required: true, message: '请输入用例步骤', trigger: 'blur' }
    ],
    preconditions: [
      { required: true, message: '请输入前置条件', trigger: 'blur' }
    ],
    expetedResult: [
      { required: true, message: '请输入预期结果', trigger: 'blur' }
    ],
    priority: [
      { required: true, message: '请选择用例优先级', trigger: 'blur' }
    ],
    projectId: [
      { required: true, message: '请选择项目', trigger: 'blur' }
    ],
    moduleId: [
      { required: true, message: '请选择模块', trigger: 'blur' }
    ],
})

initGetProjects()

</script>