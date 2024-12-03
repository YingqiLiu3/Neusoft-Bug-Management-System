import request from './request'

export const getTestCases = (projectId, params) => {
    return request({
        url: `/testcases/${projectId}`,
        params
    })
}

export const createTestCase = (data) => {
    return request({
        url: '/testcases/create',
        method: 'post',
        data
    })
}

export const submitTestCase = (testCaseId, data) => {
    return request({
        url: `/testcases/${testCaseId}`,
        method: 'put',
        data
    })
}

export const deleteTestCase = (testCaseId) => {
    return request({
        url: `/testcases/${testCaseId}`,
        method: 'delete'
    })
}