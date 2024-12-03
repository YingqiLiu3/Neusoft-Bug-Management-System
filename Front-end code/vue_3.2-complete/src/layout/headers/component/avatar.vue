<template>
      <el-dropdown>
        <el-avatar
        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
      />
        <template #dropdown>
        <el-dropdown-menu>
            <el-dropdown-Item>{{username}}</el-dropdown-item>
            <el-dropdown-item @click="Logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
        </template>
    </el-dropdown>

</template>

<script setup>
import { useStore } from 'vuex';
import {logout}  from '@/api/login'
import router from '@/router/index.js'

const username = localStorage.getItem('username')
const store = useStore()
const Logout = async() => {
    const res = await logout(localStorage.getItem('token'))
    console.log(res)
    store.commit('app/setToken','')
    localStorage.clear();
    router.replace('/login')
}
</script>