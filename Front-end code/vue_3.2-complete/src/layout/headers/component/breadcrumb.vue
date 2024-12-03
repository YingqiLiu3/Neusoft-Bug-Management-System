<template>
    <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item v-for="(item,index) in breadcrumbList" :key="index">
            <span v-if="index!=breadcrumbList.length-1" @click="handleRedirect(item.path)">{{item.name}}</span>
            <span v-else>{{item.name}}</span>
        </el-breadcrumb-item>
    </el-breadcrumb>

</template>

<script setup>
//useRoute:只能获取当前路由信息，不能修改
//useRouter:可以获取当前路由信息，也可以修改路由信息
import { useRoute ,useRouter} from 'vue-router';
import {watch,ref} from 'vue';

const route = useRoute();
const router = useRouter();
// route.matched 获取路由信息

const breadcrumbList = ref([])

const initbreadcrumbList = ()=>{
    breadcrumbList.value = route.matched
    //console.log(route.matched)
}

const handleRedirect = (path)=>{
    router.push(path)
}

// 监听路由变化
// deep:true 监听对象内部属性的变化
// immediate:true 立即执行
watch(route,()=>{
    initbreadcrumbList()
},{deep:true,immediate:true})
</script>