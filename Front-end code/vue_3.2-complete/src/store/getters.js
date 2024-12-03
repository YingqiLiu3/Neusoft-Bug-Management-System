// 方便拿到隔壁app.js中存储的token

export default{
    token:(state)=>state.app.token,
    siderType:(state)=>state.app.siderType,
    username:(state)=>state.app.username,
    role:(state)=>state.app.role
}