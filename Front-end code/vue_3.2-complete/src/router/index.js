import { createRouter, createWebHashHistory } from 'vue-router'


// 导入路由实例，注册路由实例

const routes = [
  {
    path: '/login',
    name: 'login', 
    component: () => import(/* webpackChunkName: "about" */ '../views/login/index.vue')
  },
  {
    path: '/regist',
    name: 'regist',
    component: () => import(/* webpackChunkName: "about" */ '../views/regist/index.vue')
  },
  {
    path: '/',
    name: '首页',
    component: () => import(/* webpackChunkName: "about" */ '../layout/index.vue'),
    redirect:'/projects', //重定向
    children:[
      {
        path:'usecases',
        name:'用例管理',
        component:()=> import('@/views/usecases/index.vue')
      },
      {
        path:'roles',
        name:'roles',
        component:()=> import('@/views/roles/index.vue')
      },
      {
        path:'projects',
        name:'项目管理',
        component:()=> import('@/views/projects/index.vue')
      },
      {
        path:'computed',
        name:'统计管理',
        component:()=> import('@/views/computed/index.vue')
      },
      {
        path:'bugs',
        name:'BUG管理',
        component:()=> import('@/views/bugs/index.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
