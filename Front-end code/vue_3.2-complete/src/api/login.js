import request from './request'

export const login = (data) => {
    return request({
        url:'/user/login',
        method:'post',
        data
    })
}

export const logout = (token) => {
    return request({
        url:'/user/logout',
        method:'post',
        token
    })
}

export const regist = (data) => {
    return request({
        url:'/user/regist',
        method:'post',
        data
    })
}