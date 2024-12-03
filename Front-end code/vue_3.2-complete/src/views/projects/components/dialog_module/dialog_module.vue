<template>
    <!-- model-value 实现单向绑定 -->
    <el-dialog
    :model-value="dialogModuleVisible"
    title="添加项目模块"
    width="500"
    :before-close="handleClose"
    @close="handleClose"
    append-to-body
    >
    <el-form :model="form" ref="formRef" :rules="rules" label-width="auto" style="max-width: 600px">
    <el-form-item label="项目模块名称" prop="moduleName">
      <el-input v-model="form.moduleName" />
    </el-form-item>
    <el-form-item label="项目模块描述" prop="moduleDescription">
      <el-input v-model="form.moduleDescription" />
    </el-form-item>
    <!-- <el-form-item label="项目创建人" prop="projectCreatedBy">
      <el-input v-model="form.projectCreatedBy" />
    </el-form-item> -->
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
import  {createModules} from '@/api/projects'
import { useStore } from 'vuex';

const store = useStore()


// 从父组件传来的表单数据
const props = defineProps({
    projectId:{
        type:Number,
        default:()=>0,
    }
})
const projectId = ref()
projectId.value = props.projectId

// 接收父组件传来的数据
const emits = defineEmits(['update:modelValue','initGetModules'])
const handleClose = () => {
    emits('update:modelValue', false)
}

const formRef = ref(null)
// 使用formref.value.validate()进行表单验证
const handleConfirm = ()=>{
    formRef.value.validate(async (valid)=>{
        if(valid){
          console.log('success submit')
          form.value.projectId = projectId.value
          console.log("模块的项目id",projectId.value)
          const res = await createModules(form.value)
          emits('initGetModules') //重新刷新项目列表------------------------------------------需要更改
          handleClose()
        }else{
          console.log('error submit')
          return false
        }
    })
}

const form = ref({
// "moduleID": "",       // 项目模块ID名称
  "moduleName": "",  // 项目模块名称描述
  "moduleDescription": "",  // 项目模块描述
  "projectId": "",  // 项目ID
})

const rules = ref({
    moduleName: [
        { required: true, message: '请输入模块名称', trigger: 'blur' }
      ],
    moduleDescription: [
        { required: true, message: '请输入模块描述', trigger: 'blur' }
      ],
})

</script>