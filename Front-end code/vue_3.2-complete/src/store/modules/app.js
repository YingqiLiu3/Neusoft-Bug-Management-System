import {login as loginApi} from '../../api/login.js'
import {logout as logoutApi} from '../../api/login.js'
import router from '../../router/index.js'
import { setTokenTime } from '@/util/auth.js'

/**
 * localStorage 是 Web 存储 API 的一部分，它允许开发者在用户的浏览器中存储键值对数据。这里有几点要说明：
 * 存储机制：localStorage 为浏览器提供了持久化的存储能力，数据即使在页面关闭后也会保留，直到手动删除或清除缓存。
 * 作用域：存储的数据在同一个源（同一域名及端口）下是共享的。
 * 使用方法：
 * localStorage.setItem(key, value)：存储数据。
 * localStorage.getItem(key)：获取存储的数据。
 * localStorage.removeItem(key)：移除指定的 key 及其对应的值。
 * localStorage.clear()：清除所有的 localStorage 数据。
 * 因此，在 Vuex 模块中，使用 localStorage 是为了让用户的认证状态（如 token、用户名等）在页面刷新或关闭时不会丢失。
 */

// Vuex 模块的实现，主要用于管理用户的认证状态和相关信息（如 token、用户名、角色等）
export default{
    namespaced: true,
    // () 中没有任何参数，表示这是一个无参数的箭头函数（Arrow Function）
    state:()=>({
        token: localStorage.getItem('token') || '',   //读取token，没读到就为空
        siderType:true,  //控制汉堡按钮的变量
        username:localStorage.getItem('username') || '',
        role: localStorage.getItem('role') || ''
    }),
    mutations:{
        setToken(state,token){
            state.token = token;
            localStorage.setItem('token',token);
        },
        changeSiderType(state){
            state.siderType = !state.siderType;
            //console.log(state.siderType);
        },
        setUsername(state,username){
            state.username = username;
            localStorage.setItem('username',username);
        },
        setRole(state,role){
            state.role = role;
            localStorage.setItem('role',role);
        }
    },
    /**
     * commit 是 Vuex 提供的一种方法，用于触发 mutations。
     * mutations 是同步的状态变更函数，Vuex 通过 commit 来通知 mutations 去改变状态。
     * 在 actions 函数的参数中（例如 login({ commit }, userInfo)），花括号 {} 的作用是进行解构赋值：
     * 解构赋值：按需从对象中提取值。
     * 这里 { commit } 从传入的 context 对象中提取 commit 方法，等同于 const commit = context.commit;。
     * 目的：方便使用，避免在每次调用时都需要写 context.commit，直接使用 commit 即可。
     */
    actions:{
        login({commit},userInfo){
            return new Promise((resolve,reject)=>{
                //失败了就传入then的第二个参数
                //成功就传入then的第一个参数
                loginApi(userInfo).then(res=>{
                    console.log(res);
                    // commit调用mutations中的方法
                    commit('setToken',res.token);
                    commit('setRole',res.role);
                    setTokenTime();
                    router.replace('/');
                    resolve(res);
                }).catch(err=>{
                    reject(err);
                })
            })
        },
        logout({commit}){
            commit('setToken','');
            localStorage.clear();
            router.replace('/login');
        }
    }
}