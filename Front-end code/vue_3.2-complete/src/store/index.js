// vuex 用来组织管理组件的状态，存储在全局
//index.js是入口文件，用来创建store实例，并导出模块

import { createStore } from 'vuex'
import app from './modules/app.js'
import getters from './getters.js'
// state：存储数据
// mutations：同步操作
// actions：异步操作
// getters：计算属性
// modules：模块化,将store分割成模块，每个模块拥有自己的state、mutation、action、getter，甚至是嵌套子模块

export default createStore({
  
  modules: {
    app
  },
  getters
})
