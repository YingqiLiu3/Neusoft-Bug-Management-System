<template>
  <!-- model-value 实现单向绑定 -->
  <el-dialog
      :model-value="dialogViewVisible"
      title="查看Bug"
      width="1000"
      :before-close="handleClose"
      @close="handleClose"
  >
      <el-form :model="form" ref="formRef" :rules="rules" label-width="auto" style="max-width: 600px">
          <el-form-item label="Bug名称" prop="bugName">
              <div>
                {{ form.bugName }}
              </div>
          </el-form-item>
          <el-form-item label="Bug描述" prop="bugContent">
              <div>
                {{ form.bugContent }}
              </div>
          </el-form-item>
          <el-form-item label="Bug创建人" prop="bugBuilder">
              <div>
                {{ form.bugBuilder }}
              </div>
          </el-form-item>
          <el-form-item label="Bug创建时间" prop="bugCreateTime">
              <div>
                {{ form.bugCreateTime }}
              </div>
          </el-form-item>
          <el-form-item label="最近修改时间" prop="bugSolvedTime">
              <div>
                {{ form.bugSolvedTime }}
              </div>
          </el-form-item>
          <el-form-item label="Bug优先度">
              <!-- 查看时，仅显示已选中的内容 -->
              <div v-if="isReadonly">
                {{ getSelectedLabel(form.bugGrade) }}
              </div>
          </el-form-item>
          <el-form-item label="所属模块" prop="moduleName">
              <div>
                {{ form.bugModule }}
              </div>
          </el-form-item>
          <el-form-item label="所属用例" prop="testCaseId">
              <div>
                {{ form.testCaseId }}
              </div>
          </el-form-item>
          <el-form-item label="用例步骤" prop="testCaseSteps">
              <div>
                {{ testCaseSteps }}
              </div>
          </el-form-item>
          <el-form-item label="Bug状态">
            <div v-if="isReadonly">
              <!-- 只显示已选中的状态 -->
              <span>{{ getSelectedState(form.bugState) }}</span>
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
// 处理父组件传来的数据
import { defineEmits, defineProps, watch, ref } from 'vue';
import { getSingleModule } from '@/api/projects';
import { getTestCases } from '@/api/usecases';

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

const testCaseSteps = ref('用例步骤');

// 从父组件传来的表单数据
const props = defineProps({
    dialogViewTable: {
      type: Object,
      default: () => {}
    },
    bugId: {
      type: Number,
      default: () => 0
    },
    dialogViewVisible: {
      type: Boolean,
      default: () => false
    }
})


const bugId = ref()
bugId.value = props.bugId
console.log('bugId',bugId.value)

// 监听父组件传来的数据
watch(() => props.dialogViewTable, (newVal) => {
    form.value = newVal
    }, { deep: true, immediate: true })

// 接收父组件传来的数据
const emits = defineEmits(['update:modelValue','initGetBugs'])
const handleClose = () => {
    emits('update:modelValue', false)
}

const moduleName = ref('')
const getModuleName = async () => {
    const res = await getSingleModule(form.value.bugModule)
    console.log(res.value.moduleName)
    moduleName.value = res.moduleName
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

getCaseSteps()
getModuleName()

</script>

<script>
export default {
  data() {
    return {
      form: {
        bugGrade: '高', // 默认选中的内容
        bugState: '新创建' // 默认选中的状态
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
    },
    getSelectedState(value) {
      // 返回当前选中的bug状态
      const states = {
        '新创建': '新创建',
        '待修改': '待修改',
        '修改中': '修改中',
        '已修改': '已修改',
        '待协商': '待协商',
        '已完成': '已完成',
        '暂不修改': '暂不修改',
        '永不修改': '永不修改'
      };
      return states[value] || '未选择';
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