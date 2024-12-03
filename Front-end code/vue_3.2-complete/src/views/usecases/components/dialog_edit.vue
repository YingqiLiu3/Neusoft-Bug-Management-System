<template>
  <!-- model-value 实现单向绑定 -->
  <el-dialog
  :model-value="dialogEditVisible"
  title="编辑用例"
  width="1000"
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
    <el-form-item label="用例创建人" prop="creator">
      <el-input v-model="form.creator" :disabled="true"/>
    </el-form-item>
    <el-form-item label="用例优先度" prop="priority">
    <el-radio-group v-model="form.priority" size="medium" @change="gradeChange">
      <el-radio border label="高"></el-radio>
      <el-radio border label="中"></el-radio>
      <el-radio border label="低"></el-radio>
    </el-radio-group>
    </el-form-item>
    <el-form-item label="用例前置条件" prop="preconditions">
      <el-input v-model="form.preconditions" />
    </el-form-item>
    <el-form-item label="预期结果" prop="expectedResult">
      <el-input v-model="form.expectedResult" />
    </el-form-item>
    <el-form-item label="用例步骤" prop="testSteps">
      <el-input v-model="form.testSteps" />
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
import  {createCases, submitTestCase} from '@/api/usecases'
import {getSingleModule} from '@/api/projects'
import { useStore } from 'vuex';

const form = ref({
  "creator": "",  // 创建人
  "expectedResult":"", // 用例目的
  "moduleId": "", // bug所属模块
  "preconditions":"", // 用例前置条件
  "priority": "", // 用例优先度
  "projectId": "",
  "testCaseId":"",
  "testCaseName": "", // 用例名称
  "testPurpose":"",  // 用例目的
  "testSteps": "", // 测试步骤
})

// 从父组件传来的表单数据
const props = defineProps({
  dialogEditTable:{
      tpye:Object,
      default:()=>{}
  },
  testCaseId:{
      type:Number,
      default:()=>0,
  },
  dialogEditVisible:{
      type:Boolean,
      default:()=>false
  }
})


const testCaseId = ref()
testCaseId.value = props.testCaseId
console.log('testCaseId',testCaseId.value)

// 监听父组件传来的数据(仅限于用例中的数据)，并更改
watch(()=>props.dialogEditTable, (newVal)=>{
  form.value = newVal
},{deep:true,immediate:true
})

const store = useStore()


// 接收父组件传来的数据
const emits = defineEmits(['update:modelValue','initGetTestCases'])
const handleClose = () => {
  emits('update:modelValue', false)
}
// modelvalue是干嘛的来着///////////////////////////

const gradeChange = (val) => {
form.value.priority = val
console.log('changeGrade', form.value.priority)
}

const formRef = ref(null)
// 使用formref.value.validate()进行表单验证
const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
      if (valid) {
        //console.log('success submit')
        await submitTestCase(testCaseId.value, form.value)
        //console.log('sucess resolvezckjzxkljclsjadl')
        emits('initGetTestCases') //重新刷新项目列表
        handleClose()
      } else {
        console.log('error submit')
        return false
      }
  })
}



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
})

</script>
