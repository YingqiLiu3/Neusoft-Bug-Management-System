<template>
    <!-- model-value 实现单向绑定 -->
    <el-dialog
    :model-value="dialogViewVisible"
    title="查看用例"
    width="1000"
    :before-close="handleClose"
    @close="handleClose"
    >
    <el-form :model="form" ref="formRef" :rules="rules" label-width="auto" style="max-width: 600px">
      <el-form-item label="用例名称" prop="testCaseName">
        <div>
            {{ form.testCaseName }}
        </div>
      </el-form-item>
      <el-form-item label="用例目的" prop="testPurpose">
        <div>
            {{ form.testPurpose }}
        </div>
      </el-form-item>
      <el-form-item label="用例创建人" prop="creator">
        <div>
            {{ form.creator }}
        </div>
      </el-form-item>
      <el-form-item label="用例优先度" prop="priority">
        <!-- 查看时，仅显示已选中的内容 -->
        <div v-if="isReadonly">
            {{ getSelectedLabel(form.priority) }}
        </div>
      </el-form-item>
      <el-form-item label="用例前置条件" prop="preconditions">
        <div>
            {{ form.preconditions }}
        </div>
      </el-form-item>
      <el-form-item label="预期结果" prop="expectedResult">
        <div>
            {{ form.expectedResult }}
        </div>
      </el-form-item>
      <el-form-item label="用例步骤" prop="testSteps">
        <div>
            {{ form.testSteps }}
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">关闭</el-button>
      </div>
    </template>
  </el-dialog>
  </template>
  
  <script setup>
  // 为了处理父组件传来的数据
  import { defineEmits, defineProps, watch } from 'vue';
  import { ref } from 'vue';
  import  {createCases, submitTestCase} from '@/api/usecases'
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
    dialogViewTable:{
        tpye:Object,
        default:()=>{}
    },
    testCaseId:{
        type:Number,
        default:()=>0,
    },
    dialogViewVisible:{
        type:Boolean,
        default:()=>false
    }
  })
  
  
  const testCaseId = ref()
  testCaseId.value = props.testCaseId
  console.log('testCaseId',testCaseId.value)
  
  // 监听父组件传来的数据(仅限于用例中的数据)，并更改
  watch(()=>props.dialogViewTable, (newVal)=>{
    form.value = newVal
  },{deep:true,immediate:true
  })
  
  // 接收父组件传来的数据
  const emits = defineEmits(['update:modelValue','initGetTestCases'])
  const handleClose = () => {
    emits('update:modelValue', false)
  }
  
  </script>
  
  <script>
  export default {
    data() {
      return {
        form: {
          bugGrade: '高', // 默认选中的内容
        },
        isReadonly: true // 控制是否是查看状态
      };
    },
    methods: {
      getSelectedLabel: (value) => { 
        // 根据当前选中的值，返回对应的标签
        const labels = {
          '高': '高',
          '中': '中',
          '低': '低'
        };
        return labels[value] || '未选择';
      }
    }
  };
  </script>

  <style>
.no-disabled-style .el-input__inner:disabled {
  background-color: white !important; /* 恢复背景颜色 */
  color: #000000 !important; /* 恢复字体颜色 */
  border-color: #dcdfe6 !important; /* 恢复边框颜色 */
  cursor: not-allowed; /* 保持禁止状态的光标 */
}

</style>