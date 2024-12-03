<template>
<div class="regist-container content-container">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="auto" style="max-width: 800px;" class="regist-form">
        <div class="title-container">
                <h3 class="title">用户注册</h3>
        </div>
        <el-form-item label="用户名"  prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="电话号码" prop="phoneNumber">
        <el-input v-model="form.phoneNumber" placeholder="请输入电话号码" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="form.password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="确认密码" prop="password_repeat">
        <el-input type="password" v-model="form.password_repeat" placeholder="请再次输入密码" />
        </el-form-item>
        <el-form-item label="角色" prop="role">
        <el-radio-group v-model="form.role" >
            <el-radio value="项目经理">项目经理</el-radio>
            <el-radio value="测试工程师">测试工程师</el-radio>
            <el-radio value="开发工程师">开发工程师</el-radio>
        </el-radio-group>

        <div class="regist-button">  
          <el-button type="primary" class="button-wrapper" @click="onSubmit">创建</el-button>
          <el-button class="button-wrapper" @click="onCancle">取消</el-button>  
        </div> 
        </el-form-item>
    </el-form>
</div>

</template>

<script setup>
import {ref} from 'vue'
import router from '../../router/index.js';
import { regist } from '@/api/login.js';
import {ElMessage} from "element-plus";

const form = ref({
  username: '',
  email: '',
  phoneNumber: '',
  password: '',
  password_repeat: '',
  role: '',
})

const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
  ],
  phoneNumber: [
    { required: true, message: '请输入电话号码', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
  ],
  password_repeat:[
    { required: true, message: '请再次输入密码', trigger: 'blur' },
  ],
  role: [
    { required: true, trigger: 'blur' },
  ],
})

const formRef = ref(null)

const onSubmit = () => {
  formRef.value.validate(async (valid) => {
    if(valid){
      console.log(form.value.password)
      console.log(form.value.password_repeat)
      if(form.value.password === form.value.password_repeat){
        const res = await regist(form.value)
        console.log(res)
        //router.replace('/login')
      }else{
        ElMessage.error('两次输入的密码不一致')
      }
    }else{
      ElMessage.error('创建失败')
    }
  })
}
const onCancle = () => {
  console.log('cancel')
  router.replace('/login')
}

</script>

<style scoped>
.regist-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  background-color: #fff; /* 设置边框为整个页面那么大 */
}

.content-container {
  max-width: 100%;
  padding: 0px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #fff;

  .title-container {
    position: relative;
    margin-top: -150px; /* 使用负值向上移动 */

    .title {
      font-size: 26px;
      color: rgb(50, 134, 202);
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }

    ::v-deep .lang-select {
      position: absolute;
      top: 4px;
      right: 0;
      background-color: white;
      font-size: 22px;
      padding: 4px;
      border-radius: 4px;
      cursor: pointer;
    }
  }

  .regist-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;

    ::v-deep .el-form-item {    
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
    }

    ::v-deep .el-input {
      width: 89%;
      left: 20px;

      .el-input__wrapper {
        background: transparent;
        z-index: 1;
      }

      .input {
        background:transparent;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: white;
        height: 40px;
        caret-color: white;
      }
    }
    
    .regist-button {  
      display: flex;  /* 使用 Flexbox */  
      flex-direction: row; /* 确保按钮水平排列 */  
      justify-content: space-between; /* 均匀分布按钮 */  
      width: 100%; /* 使容器宽度填满父元素 */  
      margin-top: 20px; /* 按钮上方之间的间隔 */  

      .button-wrapper {  
        width: 40%; /* 按钮的宽度，可以根据需要稍微缩小以避免溢出 */  
        box-sizing: border-box;  
      }  
    }   
  }
}
</style>