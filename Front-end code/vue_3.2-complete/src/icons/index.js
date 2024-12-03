import SvgIcon from '../components/SvgIcon';

// 调用webpack的require.context方法，第一个参数为文件夹路径，第二个参数为是否查询子目录，第三个参数为匹配文件的正则表达式来创建对象，
// 包含了该文件夹下所有svg文件
const svgRequired = require.context('./svg', false, /\.svg$/);

/**
 * svgRequired.keys() 返回一个包含所有匹配文件路径的数组。
 * forEach 方法遍历这个数组并调用 svgRequired(item) 来加载每个 SVG 文件。
 * 这样可以确保所有的 SVG 文件都被正确引入。
 */
svgRequired.keys().forEach((item) => svgRequired(item))

// 向app中注册SvgIcon组件，这样就可以在全局使用<svg-icon>标签了
// 这个标签的属性icon对应的是svg文件名，比如<svg-icon icon="home" />，会渲染home.svg文件
export default app =>{
  app.component('svg-icon', SvgIcon);
}

/**
 * export default 表示该模块有一个默认导出，允许在其他模块中以任意名称导入该导出。
 * 这意味着这个模块可以被引入，而不需要使用花括号（{}）。
 */

/**
 * 在 JavaScript 中，=> 是箭头函数（Arrow Function）的语法，用于定义一个函数。
 * 箭头函数是一种更简洁的函数表达式，其语法可以在某些情况下使代码更易读。
 * 简洁语法：箭头函数不需要 function 关键字，且如果函数体只有一个表达式，可以省略大括号和 return 关键字。
 * 绑定 this：箭头函数不会创建自己的 this 上下文，它会从外层上下文中捕获 this。
 * 1：const a => { return 2 + a; }: 错误，缺少函数名称，正确写法应为2。
 * 2：const add = a => { return 2 + a; }: 正确，有效的箭头函数。
 * 3：const add = (a) => { return 2 + a; }: 正确，有效的箭头函数。
 * 4：const add = a => ({ return 2 + a; }): 错误，使用了错误的返回对象的语法，不能在圆括号内使用 return。
 * 4的正确写法应为：const add = a => ({  result: 2 + a // 以对象形式返回  });
 */