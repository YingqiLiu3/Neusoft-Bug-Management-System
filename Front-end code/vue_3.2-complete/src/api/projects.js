import request from './request'

export const getProjects = (params) => {
  return request({
    url: '/projects',
    params
  })
}

export const createProjects = (data)=>{
  return request({
    url: '/projects/create',
    method: 'post',
    data
  })
}

export const submitProjects = (data) => {
  return request({
    url: '/projects/submit',
    method: 'put',
    data
  })
}

export const StatusChange = (projectId, projectStatus) => {
  return request({
    url: `/projects/statusSwitch/${projectId}`,
    method: 'put',
    data:{
      projectId,
      projectStatus
    }
  })
}

export const getModules = (projectId,params) =>{
  return request({
    url: `/modules/list/${projectId}`,
    method: 'get',
    params
  })
}

export const createModules = (data)=>{
  return request({
    url: '/modules/add',
    method: 'post',
    data
  })
}

export const getSingleModule = (moduleId) => {
  return request({
    url: `/modules/detail/${moduleId}`,
    method: 'get'
  })
}

export const deleteModule = (moduleId) => {
  return request({
    url: `/modules/delete/${moduleId}`,
    method: 'delete'
  })
}

export const getUsers = (params) =>{
  return request({
    url: '/user/showAllUser',
    method: 'get',
    params
  })
}

export const addUsers = (params, data) => {
  return request({
    url: '/projects/addMember',
    method: 'post',
    params,
    data
  })
}

export const deleteMember = (userId, projectId) => {
  return request({
    url: '/projects/deleteMember',
    method: 'delete',
    data:{
      userId,
      projectId
    }
  })
}

export const getMembers = (params) => {
  return request({
    url: '/projects/memberList',
    method: 'get',
    params
  })
}

export const deleteProject = (projectId) => {
  return request({
    url: `/projects/delete/${projectId}`,
    method: 'delete',

  })
}