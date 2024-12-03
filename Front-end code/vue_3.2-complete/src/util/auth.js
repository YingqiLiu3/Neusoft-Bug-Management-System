import { TOKEN_TIME } from "./constant";
import { TOKEN_TIME_VALUE } from "./constant";

//登录设置时间

export const setTokenTime = () => {
    localStorage.setItem(TOKEN_TIME, Date.now());
}

// 获取登录的时间
export const getTokenTime = () => {
    return localStorage.getItem(TOKEN_TIME);
}

// 判断是否过期
export const diffTokenTime = () => {
    const currentTime = Date.now();
    const tokenTime = getTokenTime();
    return currentTime - tokenTime > TOKEN_TIME_VALUE;
}

