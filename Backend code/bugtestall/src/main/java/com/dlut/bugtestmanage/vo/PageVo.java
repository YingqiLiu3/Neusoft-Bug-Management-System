package com.dlut.bugtestmanage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {
    private List records;
    private Long total;
    private Long size;
    private Long current;
    private Long pages;
}

/*
 * {
 *     "code": 200,
 *     "msg": "Success",
 *     "data": {
 *         "records": [
 *             {
 *                 "userId": null,
 *                 "username": "test",
 *                 "password": null,
 *                 "email": null,
 *                 "phoneNumber": null,
 *                 "roleId": 2,
 *                 "projectId": null,
 *                 "token": null,
 *                 "status": null,
 *                 "role": null
 *             },
 *             {
 *                 "userId": null,
 *                 "username": "dev",
 *                 "password": null,
 *                 "email": null,
 *                 "phoneNumber": null,
 *                 "roleId": 3,
 *                 "projectId": null,
 *                 "token": null,
 *                 "status": null,
 *                 "role": null
 *             },
 *             {
 *                 "userId": null,
 *                 "username": "test_1_1",
 *                 "password": null,
 *                 "email": null,
 *                 "phoneNumber": null,
 *                 "roleId": 2,
 *                 "projectId": null,
 *                 "token": null,
 *                 "status": null,
 *                 "role": null
 *             },
 *             {
 *                 "userId": null,
 *                 "username": "test_1_2",
 *                 "password": null,
 *                 "email": null,
 *                 "phoneNumber": null,
 *                 "roleId": 2,
 *                 "projectId": null,
 *                 "token": null,
 *                 "status": null,
 *                 "role": null
 *             },
 *             {
 *                 "userId": null,
 *                 "username": "test_1_3",
 *                 "password": null,
 *                 "email": null,
 *                 "phoneNumber": null,
 *                 "roleId": 2,
 *                 "projectId": null,
 *                 "token": null,
 *                 "status": null,
 *                 "role": null
 *             },
 *             {
 *                 "userId": null,
 *                 "username": "test_1_4",
 *                 "password": null,
 *                 "email": null,
 *                 "phoneNumber": null,
 *                 "roleId": 2,
 *                 "projectId": null,
 *                 "token": null,
 *                 "status": null,
 *                 "role": null
 *             },
 *             {
 *                 "userId": null,
 *                 "username": "test_1_5",
 *                 "password": null,
 *                 "email": null,
 *                 "phoneNumber": null,
 *                 "roleId": 2,
 *                 "projectId": null,
 *                 "token": null,
 *                 "status": null,
 *                 "role": null
 *             },
 *             {
 *                 "userId": null,
 *                 "username": "test_2_1",
 *                 "password": null,
 *                 "email": null,
 *                 "phoneNumber": null,
 *                 "roleId": 2,
 *                 "projectId": null,
 *                 "token": null,
 *                 "status": null,
 *                 "role": null
 *             },
 *             {
 *                 "userId": null,
 *                 "username": "test_2_2",
 *                 "password": null,
 *                 "email": null,
 *                 "phoneNumber": null,
 *                 "roleId": 2,
 *                 "projectId": null,
 *                 "token": null,
 *                 "status": null,
 *                 "role": null
 *             },
 *             {
 *                 "userId": null,
 *                 "username": "test_2_3",
 *                 "password": null,
 *                 "email": null,
 *                 "phoneNumber": null,
 *                 "roleId": 2,
 *                 "projectId": null,
 *                 "token": null,
 *                 "status": null,
 *                 "role": null
 *             }
 *         ],
 *         "total": 42,
 *         "size": 10,
 *         "current": 1,
 *         "orders": [],
 *         "optimizeCountSql": true,
 *         "searchCount": true,
 *         "countId": null,
 *         "maxLimit": null,
 *         "pages": 5
 *     }
 * }
 */
