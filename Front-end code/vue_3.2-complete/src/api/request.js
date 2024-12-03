import axios from "axios";
import {ElMessage} from "element-plus";
//导入util定义的判断token过期的方法
import { diffTokenTime } from "@/util/auth";
import store from "@/store";

/**
 * 使用 Axios 库创建 HTTP 客户端，并且包含请求和响应拦截器的配置
 * 这些拦截器用于处理请求的配置和响应的处理，通常用于添加认证信息、处理错误等
 */

const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL,
  // 所有请求的基础 URL，通常从环境变量中读取，baseURL: '/api'
  timeout: 5000,
});

//注册一个请求拦截器，为发送的请求设置请求头token
service.interceptors.request.use((config)=>{
  if(localStorage.getItem('token')){
    if(diffTokenTime()){
      //退出登录
      store.dispatch('app/logout'); // 调用 Vuex 的 logout 方法退出登录
      return Promise.reject(new Error("token已过期，请重新登录"));
    }
  }
  /*const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL,
  // baseURL: '/api',
  timeout: 5000,
});*/
  config.headers['token'] = localStorage.getItem('token');
  // config.headers['token'] = 'eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoi6aG555uu57uP55CGIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3Mjg0NTU0MTMsImV4cCI6MTcyODU0MTgxM30.Sq-eH2nSl5hrdEuP6z9A6nGQERER77MvzvqWo2qAnYk'
  //console.log(config)
  return config;
},error=>{
   return Promise.reject(new Error(error));
}
)

//注册一个响应拦截器，来拦截服务器发送的响应，并进行处理响应状态
service.interceptors.response.use((response)=>{
  //console.log(response)
  const {code,msg,data} = response.data;
  if(code === 200 || code === 201 || code === 3){
    //ElMessage(msg)
    
    return data;
  }else{
    //element plus 消息提示
    ElMessage.error(msg);
    return Promise.reject(msg);
  }
},error=>{
  //连响应都失败了，如网络错误
   error.response && ElMessage.error(error.response.data);
  //return Promise.reject(new Error(error.response))
}
)

export default service;

/**
 * Promise 是一种用于表示异步操作最终完成（或失败）及其结果值的 JavaScript 对象
 * 它提供了一种方式，使得处理异步操作更为直观和灵活
 */