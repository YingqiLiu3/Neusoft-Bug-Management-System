// 路由守卫，必须登录才能访问主页

import router from './index'
import store from '../store'

const whiteList = ['/login','/regist'] // 不需要登录的页面

router.beforeEach((to, from, next) => {
  if(store.getters.token) {
    if (to.path === '/login') {
        next('/')
    } else {
        next()
    }
  } else {
    if (whiteList.includes(to.path)) {
        next()
    } else {
        next('/login')
    }
  }
})