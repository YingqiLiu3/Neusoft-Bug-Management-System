main.js程序入口文件，初始化vue示例，并引入需要使用的插件和各种公共组件
App.vue是项目的主组件，页面入口文件，所有页面都在APP.vue下进行切换，负责构建定义及页面组件归集

store文件夹：管理状态，用于存放Vuex状态管理相关的文件。Vuex是一个专为Vue.js应用设计的状态管理模式。store文件夹通常包括以下几个部分：
--state：定义应用的状态
--mutations：定义修改状态的方法
--actions：定义业务逻辑和异步操作
--getters：定义从状态中派生出的一些状态

babel.config.js babel配置文件，告诉项目采用什么标准编译 比如将es6编译成es5（有些浏览器不兼容）

package.json 项目配置文件，比如项目名称，版本，依赖，脚本等

package-lock.json 锁定依赖版本，防止依赖版本变动(卸载了重新下载，版本可能不同)

public 静态资源文件夹，比如图片，css等

src 源代码文件夹，比如js，vue等

vue.config.js vue配置文件，比如代理，打包路径等

.browserlistrc 浏览器兼容配置文件

.eslintrc.js eslint配置文件，比如语法检查，代码风格等
.editorconfig 编辑器配置文件，比如代码格式化，缩进等

