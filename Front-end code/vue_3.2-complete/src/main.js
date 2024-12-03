import { createApp } from 'vue'
// 引入父组件
import App from './App.vue'
import router from './router/index.js'
import store from './store'
import './styles/index.scss'
import SvgIcon from './icons/index.js'
import 'element-plus/dist/index.css'
import './router/permission.js'

//引入Icon组件
import * as ElIcons from '@element-plus/icons-vue'


// createApp创建一个vue应用实例，App是父组件
// use是使用插件，store是vuex插件，router是路由插件
// mount挂载到id为app的元素上
const app =  createApp(App)

// 遍历所有图标，注册为全局组件
for (const iconName in ElIcons) {
  app.component(iconName, ElIcons[iconName])
}

SvgIcon(app)
app.use(store).use(router).mount('#app')
