import request from './request'

export const getBugs = (projectId, params) => {
    return request({
        url: `/bugs/querry/${projectId}`,
        params
    })
}

export const createBugs = (data) => {
    return request({
        url: '/bugs/create',
        method: 'post',
        data
    })
}

export const deleteBugs = (bugId) => {
    return request({
        url: `/bugs/${bugId}`,
        method: 'delete'
    })
}

export const submitBug = (bugId, data) => {
    return request({
        url: `bugs/${bugId}`,
        method: 'put',
        data
    })
}

/*
export const publishBug = (bugId) => {
    return request({
        url: `bugs/${bugId}/publish`,
        method: 'put'
    })
} */

export const resolveBug = (bugId) => {
    return request({
        url: `bugs/${bugId}/resolve`,
        method: 'put'
    })
}
