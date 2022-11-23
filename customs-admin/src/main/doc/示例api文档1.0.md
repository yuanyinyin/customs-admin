OA系统 API 文档

| 版本 | 内容                                   | 更新日期  |
| ---- | -------------------------------------- | --------- |
| V1.0 | 进行初步接口设计                       | 2022.2.9 |
| V1.1 | 投标管理模块接口设计                       | 2022.2.17 |
| V1.2 | 合同管理模块接口设计                       | 2022.2.21 |
| V1.3 | 客户管理模块接口设计                       | 2022.3.1 |
| V1.4 | 我的待办/已办接口设计                       | 2022.3.11 |
| V1.5 | 日志管理接口设计                       | 2022.3.14 |

[TOC]

## 基础项

### 接口基础配置

* 请求协议: http/https
* 域名前缀: 目前是ip
* 版本前缀: /dev-api
* 请求地址: http://localhost:9528/dev-api
* 请求头配置:

```json
{
  "Content-Type": "application/json;charset=utf-8",
  "authToken": "token"
}
```

### HTTP 状态码说明

* 200 Success: 请求成功, 处理成功。
* 300 Handle error: 处理数据失败。
* 401 Unauthorized: 身份认证(authentication), 用户未认证, 请求失败。
* 403 Forbidden: 授权(authorization), 用户无权限访问该资源, 请求失败。
* 404 Not Found: 请求资源不存在。
* 500 Internal Server Error: 服务器内部发生错误。

### 接口格式

一个接口主要包含以下几点基础信息:

1. 接口描述: 接口的描述信息
2. 请求地址: 不包含版本和域名前缀的接口地址
3. 请求方法: HTTP 请求方法
4. 请求参数: 请求参数的具体说明(表格形式)

| 参数  | 类型   | 必须 | 描述     |
| ----- | ------ | ---- | -------- |
| param | String | 是   | 请求参数 |

5. 请求示例(可选): JSON 格式的请求示例

```json
{
  "userName": "test",
  "password": "test"
}
```

6. 响应参数: 响应参数的具体说明(表格形式)

| 参数    | 类型   | 描述                        |
| ------- | ------ | --------------------------- |
| code    | Number | 状态码, 200: 成功, 其它: 失败 |
| data    | Object | 数据实体                    |
| message | String | 请求失败时的消息描述        |

7. 响应示例(可选): JSON 格式的响应示例
  * 请求成功

  ```json
  {
    "code": 200,
    "message": "处理成功",
    "data": {}
  }
  ```
  * 请求失败

  ```json
  {
    "code": 1,
    "message": "请求失败！",
    "data": {}
  }
  ```

## 登录登出

### 用户登录

* 接口描述: 用户登录
* 请求地址: /login/validate
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| password | String | 是   | 密码   |
| userName | String | 是   | 用户名 |

* 响应示例

```json
{
  "code": 0,
  "message": "处理成功",
  "data": "klasdghasdhjas"
}
```

* data

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| token | String | 用户登录凭证 |

### 用户登出

* 接口描述: 用户登出
* 请求地址: /login/logout
* 请求方法: get
* 请求参数: 无
* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

## 投标管理

### 投标申请

* 接口描述: 经办人发起申请
* 请求地址: /bid/create
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| projectName | String | 是   | 项目名称   |
| purchaseName | String | 是   | 采购单位名称 |
| payDate | String | 是   | 付款日期 yyyy-MM-dd |
| payType | String | 是   | 付款类型 1-报名费 2-投标保证金 3-报名费+投标保证金 |
| returnSituation | String | 是   | 报名费回票情况 1-已回票 2-未回票 |
| refundSituation | String | 是   | 投标保证金退款情况 1-已退款 2-未退款 |
| status | String | 是   | 提交状态 0-暂存 1-提交 |
| registeryJson | String | 否   | 报名费付款申请json |
| marginJson | String | 否   | 投标保证金付款申请json |

* registeryJson和marginJson字段说明

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| payReason | String | 是   | 付款事由   |
| receiveUnit | String | 否   | 收款单位 |
| bankAccount | String | 否   | 银行账号 |
| bankDepositary | String | 否   | 开户银行 |
| payAmountCapital | String | 是   | 付款金额（大写） |
| payAmountNumber | Number | 是   | 付款金额（数字） |
| payWay | String | 否   | 付款方式 1-电汇 2-网银转账 3-银行转账支票 4-现金 |
| payWayDesc | String | 否   | 付款方式备注 |

* 请求示例

```json
{
  "projectName": "项目1",
  "purchaseName": "采购单位1",
  "payDate": "2022-02-18",
  "payType": "1",
  "returnSituation": "1",
  "refundSituation": "1",
  "status": "1",
  "registeryJson": {
    "payReason": "付款事由123",
    "receiveUnit": "收款单位123",
    "bankAccount": "银行账户123",
    "bankDepositary": "开户银行123",
    "payAmountCapital": "壹仟元",
    "payAmountNumber": "1000",
    "payWay": "1",
    "payWayDesc": "1"
  },
  "marginJson": {}
}
```

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 投标信息修改

* 接口描述: 对申请信息进行修改
* 请求地址: /bid/update/:id
* 请求方法: put
* 请求参数: 同create方法

* 请求示例

```json
{
  "projectName": "项目1",
  "purchaseName": "采购单位1",
  "payDate": "",
  "payType": "0",
  "returnSituation": "1",
  "refundSituation": "1",
  "status": "1",
  "registeryJson": {
    "payReason": "付款事由123",
    "receiveUnit": "收款单位123",
    "bankAccount": "银行账户123",
    "bankDepositary": "开户银行123",
    "payAmountCapital": "壹仟元",
    "payAmountNumber": "1000",
    "payWay": "1",
    "payWayDesc": "1"
   },
  "marginJson": {}
}
```

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 投标信息查看

* 接口描述: 查看某条投标记录详情
* 请求地址: /bid/showOne/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": {
    "projectName": "项目1",
    "purchaseName": "采购单位1",
    "payDate": "",
    "payType": "1",
    "returnSituation": "1",
    "refundSituation": "1",
    "status": "1",
    "registeryJson": {
       "payReason": "付款事由123",
       "receiveUnit": "收款单位123",
       "bankAccount": "银行账户123",
       "bankDepositary": "开户银行123",
       "payAmountCapital": "壹仟元",
       "payAmountNumber": "1000",
       "payWay": "1",
       "payWayDesc": "1"
     },
    "marginJson": {},
    "checkProcess": [
        {
           "examineStatus": "1",
           "examinePerson": "市场部负责人",
           "examineDept": "市场部",
           "examineTime": "2022-02-17 13:53:00",
           "reason": ""
        },
        {
           "examineStatus": "2",
           "examinePerson": "财务部负责人",
           "examineDept": "财务部",
           "examineTime": "2022-02-17 13:58:00",
           "reason": "不通过理由11"
        }
    ]
  }
}
```

* checkProcess说明：审核流程的记录(按照时间递增顺序返回)

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| examineStatus | String | 审核状态 1-审核通过 2-审核不通过 |
| examinePerson | String | 审核人 |
| examineDept | String | 审核部门 |
| examineTime | String | 审核时间 |
| reason | String | 审核不通过理由 |

### 投标信息删除

* 接口描述: 对暂存的投标记录进行删除
* 请求地址: /bid/delete/:id
* 请求方法: delete
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 投标申请列表

* 接口描述: 分页查看投标申请列表
* 请求地址: /bid/listPage
* 请求方法: get
* 请求参数

| 参数          | 类型   | 必须 | 描述   |
|-------------| ------ | ---- | ------ |
| limit       | Number | 是   | 每页几条   |
| page        | Number | 是   | 第几页 |
| projectName | String | 否   | 项目名称 |
| status      | String | 否   | 审核状态  |
| startDate  | String | 否   | 申请开始日期 |
| endDate  | String | 否   | 申请结束日期 |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"items": [{
            "id": 1,
			"projectName": "项目1",
			"purchaseName": "采购单位1",
			"payDate": "",
			"payType": "1",
			"returnSituation": "1",
			"refundSituation": "1",
			"status": "1"
		}],
		"total": 1
	}
}
```
* items说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| projectName | String | 项目名称 |
| purchaseName | String | 采购单位名称 |
| payDate | String | 付款日期 |
| payType | String | 付款类型 1-报名费 2-投标保证金 3-报名费+投标保证金 |
| returnSituation | String | 报名费回票情况 1-已回票 2-未回票 |
| refundSituation | String | 投标保证金退款情况 1-已退款 2-未退款 |
| status | String | 提交状态 0-暂存 1-提交 2-部门负责人审核通过 3-部门负责人审核不通过 4-财务审核通过 5-财务审核不通过 6-总经理审核通过 7-总经理审核不通过 8-董事长审核通过 9-董事长审核不通过 |

### 投标审核状态变更

* 接口描述: 对发起的投标记录进行审核
* 请求地址: /bid/updateStatus/:id
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| status | Number | 是   | 审核状态 1-审核通过 2-审核不通过  |
| reason | String | 否   | 审核不通过理由 |

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 财务付款确认

* 接口描述: 财务对董事长审核通过的投标记录进行付款确认
* 请求地址: /bid/updateConfirm/:id
* 请求方法: post
* 请求参数： 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 付款申请单打印

* 接口描述: 财务对董事长审核通过的付款申请单进行打印
* 请求地址: /bid/print/:id
* 请求方法: get
* 请求参数： 无

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"registeryJson": {
			"payReason": "付款事由123",
			"receiveUnit": "收款单位123",
			"bankAccount": "银行账户123",
			"bankDepositary": "开户银行123",
			"payAmountCapital": "壹仟元",
			"payAmountNumber": "1000",
			"payWay": "1",
			"applyDept": "市场部",
			"chairman": "刘总",
			"generarManager": "陈总",
			"finance": "财务",
			"applyPerson": "张三",
			"applyDate": "2022-02-17"
		},
		"marginJson": {}
	}
}
```

* registeryJson说明：报名费；marginJson说明：保证金；

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| payReason | String | 付款事由 |
| receiveUnit | String | 收款单位 |
| bankAccount | String | 银行账号 |
| bankDepositary | String | 开户银行 |
| payAmountCapital | String | 付款金额（大写） |
| payAmountNumber | String | 付款金额（数字） |
| payWay | String | 付款方式 1-电汇 2-网银转账 3-银行转账支票 4-现金 |
| applyDept | String | 申请部门 |
| chairman | String | 董事长 |
| generarManager | String | 总经理 |
| finance | String | 财务 |
| applyPerson | String | 经手人 |
| applyDate | String | 申请日期 yyyy-MM-dd |

## 合同管理

### 合同修订发起

* 接口描述: 经办人发起合同修订申请
* 请求地址: /contract/create
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| type | String | 是   | 合同类型, 1-销售合同 2-分包合同 3-其他合同  |
| masterContractId | String | 否   | 主销售合同id |
| contractName | String | 是   | 合同名称 |
| projectId | Number | 是   | 投标申请id |
| projectName | String | 是   | 项目名称 |
| clientId | Number | 否   | 客户id |
| clientName | String | 是   | 客户名称 |
| totalAmount | Number | 是   | 合同总金额 |
| isFileTemplate | String | 是   | 是否招标文件模板 1-是 2-否 |
| revisionStatus | String | 是   | 修订状态(0-暂存 1-提交修订) |

* 请求示例

```json
{
  "type": "1",
  "projectName": "项目名称",
  "clientName": "客户名称",
  "totalAmount": "100000",
  "isFileTemplate": "1",
  "revisionStatus": "1"
}
```

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 文件上传

* 接口描述: 文件上传
* 请求地址: /file/upload
* 请求方法: post
* Content-Type: multipart/form-data
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| file | Blob | 是   | 合同附件(支持多格式) |

* 请求示例

```json
{
  "file(Blob)": "文件流"
}
```

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 销售合同查询

* 接口描述: 查询分包合同关联的销售合同列表
* 请求地址: /contract/selectByKey
* 请求方法: get
* 请求参数： 

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| contractName | String | 否   | 合同名称  |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": [
        {
          "contractId": 1,
          "contractNumber": "20220001",
          "contractName": "1"
        },
        {
          "contractId": 2,
          "contractNumber": "20220002",
          "contractName": "2"
        }
    ]
}
```

* data说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| contractId | Number | 合同Id |
| contractNumber | String | 合同编号 |
| contractName | String | 合同名称 |

### 投标申请列表(不分页)

* 接口描述: 查看投标申请列表
* 请求地址: /bid/list
* 请求方法: get
* 请求参数

| 参数          | 类型   | 必须 | 描述   |
|-------------| ------ | ---- | ------ |
| projectName | String | 否   | 项目名称（模糊查询） |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": [{
		"id": 1,
		"projectName": "项目1"
	}]
}
```
* data说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | id |
| projectName | String | 项目名称 |


### 合同修订删除

* 接口描述: 对暂存的合同修订申请进行删除
* 请求地址: /contract/delete/:id
* 请求方法: delete
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 合同修订信息查看

* 接口描述: 查看某条合同修订详情
* 请求地址: /contract/showRevisionOne/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": {
    "type": "2",
    "masterContractId": 1,
    "masterContractName": "主合同名称",
    "contractNumber": "20220001",
    "contractName": "合同名称",
    "projectName": "项目名称",
    "clientName": "客户名称",
    "totalAmount": "1000000",
    "isFileTemplate": "1",
    "revisionStatus": "1",
    "revisionProcess": [
        {
           "examineStatus": "1",
           "examinePerson": "市场部负责人",
           "examineDept": "市场部",
           "examineTime": "2022-02-17 13:53:00",
           "reason": ""
        },
        {
           "examineStatus": "2",
           "examinePerson": "财务部负责人",
           "examineDept": "财务部",
           "examineTime": "2022-02-17 13:58:00",
           "reason": "退回原因11"
        }
    ]
  }
}
```

* revisionProcess说明：修订流程的记录(按照时间递增顺序返回)

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| examineStatus | String | 修订状态 1-修订完成 2-退回 |
| examinePerson | String | 修订人 |
| examineDept | String | 修订部门 |
| examineTime | String | 修订时间 |
| reason | String | 退回原因 |

### 文件查看

* 接口描述: 查看某条合同修订文件详情
* 请求地址: /file/getFileList/:id(合同修订记录id)
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": [
        {
           "id": 1,
           "type": "1",
           "fileName": "合同.docx",
           "fileUrl": "/home/nteport/fileUploadPath/contract/a2ec5c92-2197-4a65-9941-4698904a488b.docx"
        }
    ]
}
```

* data说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | 文件id |
| type | String | 文件类型 1-合同 2-中标通知书 3-招标文件 4-验收报告 5-发票签收单 |
| fileName | String | 文件名称 |
| fileUrl | String | 文件地址 |

### 文件下载

* 接口描述: 查看某条合同修订文件详情
* 请求地址: /file/downLoad/:id(文件id)
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 合同修订通过

* 接口描述: 对合同进行修订
* 请求地址: /contract/update/:id
* 请求方法: put
* 请求参数: 同create方法

* 请求示例

```json
{
  "type": "1",
  "projectName": "项目名称",
  "clientName": "客户名称",
  "totalAmount": "100000",
  "isFileTemplate": "1",
  "revisionStatus": "1"
}
```

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 合同修订退回
   
* 接口描述: 合同修订退回
* 请求地址: /contract/updateRevisionStatus/:id
* 请求方法: post
* 请求参数: 

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| reason | String | 是   | 修订退回原因 |

* 响应示例

```json
{
 "code": 200,
 "message": "处理成功",
 "data": null
}
```

### 合同修订列表

* 接口描述: 分页查看合同修订列表
* 请求地址: /contract/revisionListPage
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| limit | Number | 是   | 每页几条   |
| page | Number | 是   | 第几页 |
| contractName | String | 否   | 合同名称 |
| revisionStatus | String | 否   | 修订状态  |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"items": [{
			"id": 1,
			"type": "1",
			"contractNumber": "20220001",
			"contractName": "合同名称",
			"projectName": "项目名称",
			"clientName": "客户名称",
			"totalAmount": "100000",
			"createUser": "经办人",
			"revisionStatus": "1"
		}],
		"total": 1
	}
}
```
* items说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| type | String | 合同类型, 1-销售合同 2-分包合同 3-其他合同 |
| contractNumber | String | 合同编号 |
| contractName | String | 合同名称 |
| projectName | String | 项目名称 |
| clientName | String | 客户名称 |
| totalAmount | String | 合同总金额 |
| createUser | String | 经办人 |
| revisionStatus | String | 修订状态  |
| checkStatus | String | 审核状态 |

### 合同审核列表

* 接口描述: 分页查看合同审核列表
* 请求地址: /contract/checkListPage
* 请求方法: get
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| limit | Number | 是   | 每页几条   |
| page | Number | 是   | 第几页 |
| contractName | String | 否   | 合同名称 |
| checkStatus | String | 否   | 审核状态  |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"items": [{
			"id": 1,
			"type": "1",
			"contractNumber": "20220001",
			"contractName": "合同名称",
			"projectName": "项目名称",
			"clientName": "客户名称",
			"totalAmount": "100000",
			"createUser": "经办人",
			"checkStatus": "1"
		}],
		"total": 1
	}
}
```
* items说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| type | String | 合同类型, 1-销售合同 2-分包合同 3-其他合同 |
| contractNumber | String | 合同编号 |
| contractName | String | 合同名称 |
| projectName | String | 项目名称 |
| clientName | String | 客户名称 |
| totalAmount | String | 合同总金额 |
| createUser | String | 经办人 |
| checkStatus | String | 审核状态 |

### 合同审核信息查看

* 接口描述: 查看某条合同审核详情
* 请求地址: /contract/showCheckOne/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": {
    "type": "2",
    "masterContractId": "",
    "contractNumber": "20220001",
    "contractName": "合同名称",
    "projectName": "项目名称",
    "clientName": "客户名称",
    "totalAmount": "1000000",
    "isFileTemplate": "1",
    "checkStatus": "1",
    "checkProcess": [
        {
           "examineStatus": "1",
           "examinePerson": "市场部负责人",
           "examineDept": "市场部",
           "examineTime": "2022-02-17 13:53:00",
           "reason": ""
        },
        {
           "examineStatus": "2",
           "examinePerson": "财务部负责人",
           "examineDept": "财务部",
           "examineTime": "2022-02-17 13:58:00",
           "reason": "退回原因11"
        }
    ]
  }
}
```

* checkProcess说明：修订流程的记录(按照时间递增顺序返回)

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| examineStatus | String | 审核状态 1-修订完成 2-退回 |
| examinePerson | String | 审核人 |
| examineDept | String | 审核部门 |
| examineTime | String | 审核时间 |
| reason | String | 退回原因 |

### 合同审核

* 接口描述: 对合同进行审核
* 请求地址: /contract/updateCheckStatus/:id
* 请求方法: post
* 请求参数: 

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| status | Number | 是   | 审核状态 1-审核通过 2-审核不通过  |
| reason | String | 否   | 审核不通过原因 |
    
* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 合同审批表打印

* 接口描述: 办公室对董事长审核通过的合同审批表打印
* 请求地址: /contract/print/:id
* 请求方法: get
* 请求参数： 无

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"contractNumber": "",
        "contractName": "",
        "applyDept": "",
        "createUser": "",
        "clientName": "",
        "totalAmount": "",
        "type": "销售合同/分包合同",
        "deptHead": {
          "reason": "审核通过",
          "chargePerson": "部门长",
          "createTime": "2022年2月24日"
        },
        "finance": {
          "reason": "审核通过",
          "chargePerson": "财务",
          "createTime": "2022年2月24日"
        },
        "office": {
          "reason": "审核通过",
          "chargePerson": "办公室",
          "createTime": "2022年2月24日"
        },
        "generarManager": {
           "reason": "审核通过",
           "chargePerson": "总经理",
           "createTime": "2022年2月24日"
         },
        "chairman": {
          "reason": "审核通过",
          "chargePerson": "董事长",
          "createTime": "2022年2月24日"
        }
	}
}
```

* data说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| contractNumber | String | 合同编号 |
| contractName | String | 合同名称 |
| applyDept | String | 申请部门 |
| createUser | String | 承办人 |
| clientName | String | 对方联系人 |
| totalAmount | String | 合同金额 |
| type | String | 合同类别 |
| deptHead | String | 部门意见 |
| finance | String | 综合管理部（财务）意见 |
| office | String | 综合管理部意见 |
| generarManager | String | 副总经理意见 |
| chairman | String | 董事长／总经理审批意见 |

### 合同归档列表

* 接口描述: 分页查看合同归档列表
* 请求地址: /contract/archiveListPage
* 请求方法: get
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| limit | Number | 是   | 每页几条   |
| page | Number | 是   | 第几页 |
| type | String | 否   | 合同类别 1-销售合同 2-分包合同 |
| contractName | String | 否   | 合同名称 |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"items": [{
			"id": 1,
			"type": "1",
			"contractNumber": "20220001",
			"contractName": "合同名称",
			"projectName": "项目名称",
			"clientName": "客户名称",
			"totalAmount": "100000",
			"marginTotal": "100000",
			"backTotal": "100000",
			"chargePerson": "负责人",
			"childContract": [{
				"id": 2,
				"type": "2",
				"contractNumber": "20220002",
				"contractName": "分包合同1",
				"projectName": "项目名称",
				"clientName": "客户名称",
				"totalAmount": "100000",
				"marginTotal": "100000",
				"backTotal": "100000",
				"chargePerson": "负责人",
				"childContract": []
			}]
		}],
		"total": 1
	}
}
```
* items说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | id |
| type | String | 合同类型, 1-销售合同 2-分包合同 3-其他合同 |
| contractNumber | String | 合同编号 |
| contractName | String | 合同名称 |
| projectName | String | 项目名称 |
| clientName | String | 客户名称 |
| totalAmount | Number | 合同总金额 |
| marginTotal | Number | 销售合同-开票总额/分包合同-回票金额 |
| backTotal | Number | 销售合同-回款总额/分包合同-付款金额 |
| chargePerson | String | 销售合同-商务人员/分包合同-项目经理 |
| childContract | Array | 分包合同列表 |

### 合同详情

* 接口描述: 合同详情查看
* 请求地址: /contract/showOne/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": {
    "id": 1,
    "type": "2",
    "contractNumber": "20220001",
    "contractName": "合同名称",
    "projectName": "项目名称",
    "clientName": "客户名称",
    "totalAmount": "1000000",
    "isFileTemplate": "1"
  }
}
```

### 销售合同开票信息回显

* 接口描述: 销售合同开票信息回显
* 请求地址: /contractBill/showList/:id(合同id)
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": [{
    "id": 1,
    "supplyId": 1,
    "account": "1111",
    "bankName": "开户行名称",
    "billPay": "开票款项1",
    "billTotal": "100000",
    "applyStatus": "1"
  }]
}
```

### 销售合同归档编辑

* 接口描述: 销售合同归档编辑
* 请求地址: /contract/saleArchiveUpdate/:id
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| basicInfo | String | 是   | 基础信息   |
| billingInfo | Array | 是   | 开票申请信息 |

* basicInfo字段说明

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| signingDate | String | 是   | 签订日期 yyyy-MM-dd |
| payPeriod | String | 是   | 收款周期(天) |
| marginTotal | String | 是   | 履约保证金开票总额 |
| backTotal | String | 是   | 回款总额 |
| marginBackDate | String | 是   | 履约保证金退还日期 yyyy-MM-dd |
| implPeriod | String | 是   | 实施周期 |
| maintainDate | String | 是   | 运维日期 yyyy-MM-dd |
| acceptDate | String | 是   | 验收日期 yyyy-MM-dd |
| chargePerson | String | 是   | 商务人员 下拉选择 |
| partyPerson | String | 是   | 甲方联系人 |
| partyTel | String | 是   | 甲方联系方式 |
| implStatus | String | 是   | 实施情况 1-完成 2-实施中 |

* billingInfo字段说明

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| supplyId | String | 是   | 所选供应商id   |
| billPay | String | 是   | 开票款项 |
| billTotal | String | 是   | 开票金额 |

* 请求示例

```json
{
  "basicInfo": {
    "signingDate": "2022-02-22",
    "payDate": "2022-02-22",
    "marginTotal": "100000",
    "backTotal": "100000",
    "marginBackDate": "2022-02-22",
    "implPeriod": "15天",
    "maintainDate": "2022-02-22",
    "acceptDate": "2022-05-22",
    "chargePerson": "商务人员",
    "partyPerson": "甲方联系人",
    "partyTel": "18355555555",
    "implStatus": "2"
  },
  "billingInfo": [{
    "supplyId": 1,
    "billPay": "开票款项1",
    "billTotal": "100000"
  },
  {
    "supplyId": 1,
    "billPay": "开票款项2",
    "billTotal": "200000"
  }]
}
```

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 上传附件

* 接口描述: 上传相关附件
* 请求地址: /file/upload/:id(主合同id)
* 请求方法: post
* Content-Type: multipart/form-data
* 请求参数

| 参数 | 类型 | 必填 | 描述       |
| ---- | ---- | ---- | ---------- |
| file | Blob | 是   | 附件信息 |
| type | String | 是   | 附件类型 2-中标通知书 3-招标文件 4-验收报告 5-发票签收单 |

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 开票申请列表

* 接口描述: 分页查看开票申请列表
* 请求地址: /contractBill/listPage
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| limit | Number | 是   | 每页几条   |
| page | Number | 是   | 第几页 |
| contractName | String | 否   | 合同名称 |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"items": [{
			"id": 1,
			"contractNumber": "20220001",
			"contractName": "合同名称",
			"projectName": "项目名称",
			"clientName": "客户名称",
			"totalAmount": "100000",
			"createUser": "经办人",
            
			"children": [{
					"id": 1,
					"billPay": "开票款项1",
					"billTotal": "100000",
					"toBeBillTotal": "",
					"applyStatus": "1"
				},
				{
					"id": 2,
					"billPay": "开票款项2",
					"billTotal": "200000",
					"toBeBillTotal": "",
					"applyStatus": "1"
				}
			]
		}],
		"total": 1
	}
}
```
* items说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | 记录id |
| contractNumber | String | 合同编号 |
| contractName | String | 合同名称 |
| projectName | String | 项目名称 |
| clientName | String | 客户名称 |
| totalAmount | Number | 合同总金额 |
| billPay | String | 开票款项 |
| billTotal | Number | 开票金额 |
| toBeBillTotal | Number | 待开票金额 |
| createUser | String | 经办人 |

### 开票信息查看

* 接口描述: 查看某条合同开票记录详情
* 请求地址: /contractBill/showOne/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": {
    "id": 1,
    "contractNumber": "20220001",
    "contractName": "合同名称",
    "projectName": "项目名称",
    "clientName": "客户名称",
    "totalAmount": "100000",
    "createUser": "经办人",
    "billingInfo": [
      {
        "id": 1,
        "billPay": "开票款项1",
        "billTotal": "800000",
        "toBeBillTotal": "200000",
        "applyStatus": "1",
        "backStatus": "1"
      } 
    ]
  }
}
```

### 开票审核

* 接口描述: 对提交的开票信息进行审核
* 请求地址: /contractBill/updateApplyStatus/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 回款登记

* 接口描述: 钱到账对回款进行登记
* 请求地址: /contractBack/updateBackStatus/:id
* 请求方法: post
* 请求参数: 

| 参数        | 类型   | 必须 | 描述   |
|-----------| ------ | ---- | ------ |
| backMoney | Number | 是   | 回款金额   |

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 分包合同付款信息回显

* 接口描述: 分包合同付款信息回显
* 请求地址: /contractPay/showList/:id(合同id)
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": [{
    "id": 1,
    "supplyId": 1,
    "account": "1111",
    "bankName": "开户行名称",
    "payReason": "付款款项",
    "payAmountCapital": "付款金额（大写）",
    "payAmountNumber": "100000"
  }]
}
```

### 分包合同归档编辑

* 接口描述: 分包合同归档编辑
* 请求地址: /contract/subArchiveUpdate/:id
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| basicInfo | String | 是   | 基础信息   |
| payInfo | Array | 是   | 付款申请信息 |

* basicInfo字段说明

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| signingDate | String | 是   | 签订日期 yyyy-MM-dd |
| payPeriod | String | 是   | 付款周期(天) |
| marginTotal | String | 是   | 履约保证金开票总额 |
| backTotal | String | 是   | 回款总额 |
| marginBackDate | String | 是   | 履约保证金退还日期 yyyy-MM-dd |
| implPeriod | String | 是   | 实施周期 |
| maintainDate | String | 是   | 运维日期 yyyy-MM-dd |
| acceptDate | String | 是   | 验收日期 yyyy-MM-dd |
| chargePerson | String | 是   | 项目经理 |
| partyPerson | String | 是   | 乙方联系人 |
| partyTel | String | 是   | 乙方联系方式 |

* payInfo字段说明

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| supplyId | String | 是   | 所选供应商id   |
| payReason | String | 是   | 付款款项 |
| payAmountCapital | String | 是   | 付款金额（大写） |
| payAmountNumber | Number | 是   | 付款金额（数字） |

* 请求示例

```json
{
	"basicInfo": {
		"signingDate": "2022-02-22",
		"payDate": "2022-02-22",
		"marginTotal": "100000",
		"backTotal": "100000",
		"marginBackDate": "2022-02-22",
		"implPeriod": "15天",
		"maintainDate": "2022-02-22",
		"acceptDate": "2022-05-22",
		"chargePerson": "项目经理",
		"partyPerson": "乙方联系人",
		"partyTel": "18355555555"
	},
	"payInfo": [{
		"supplyId": 1,
		"payReason": "付款款项",
		"payAmountCapital": "付款金额（大写）",
		"payAmountNumber": "100000"
	}]
}
```

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```


### 付款申请列表

* 接口描述: 分页查看付款申请列表
* 请求地址: /contract/payListPage
* 请求方法: get
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| limit | Number | 是   | 每页几条   |
| page | Number | 是   | 第几页 |
| contractName | String | 否   | 合同名称 |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"items": [{
			"id": 1,
			"contractNumber": "20220001",
			"contractName": "合同名称",
			"projectName": "项目名称",
			"clientName": "客户名称",
			"totalAmount": "100000",
			"children": [{
				"payProject": "付款款项",
				"payTotal": "800000",
				"toBePayTotal": "200000",
				"chargePerson": "商务人员",
				"checkStatus": "1"
			}]
		}],
		"total": 1
	}
}
```
* items说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | 记录id |
| contractNumber | String | 合同编号 |
| contractName | String | 合同名称 |
| projectName | String | 项目名称 |
| clientName | String | 客户名称 |
| totalAmount | Number | 合同总金额 |
| payProject | String | 付款款项 |
| payTotal | Number | 付款金额 |
| toBePayTotal | Number | 待付款金额 |
| chargePerson | String | 商务人员 |
| checkStatus | String | 审核状态 1-提交审核 2-部门负责人审核通过 3-部门负责人审核不通过 4-财务审核通过 5-财务审核不通过 6-总经理审核通过 7-总经理审核不通过 8-董事长审核通过 9-董事长审核不通过12-项目经理通过 13-项目经理审核不通过 |

### 付款信息查看

* 接口描述: 查看某条合同付款记录详情
* 请求地址: /contractPay/showOne/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": {
    "id": 1,
    "contractNumber": "20220001",
    "contractName": "合同名称",
    "projectName": "项目名称",
    "clientName": "客户名称",
    "totalAmount": "100000",
    "payProject": "付款款项",
    "payTotal": "800000",
    "toBePayTotal": "200000",
    "chargePerson": "商务人员",
    "checkStatus": "1"
  }
}
```

### 付款申请审核

* 接口描述: 对提交的付款申请进行审核
* 请求地址: /contract/updateCheckStatus/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 付款申请确认

* 接口描述: 对已经付款的合同进行付款确认
* 请求地址: /contract/updatePayStatus/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 回款登记

* 接口描述: 
* 请求地址: /contract/updateBackStatus/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

## 供应商管理

### 供应商列表

* 接口描述: 分页获取供应商信息
* 请求地址: /supply/listPage
* 请求方法: get
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| limit | Number | 是   | 每页几条   |
| page | Number | 是   | 第几页 |
| companyName | String | 否   | 公司名称 |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"items": [{
            "id": 1,
            "companyName": "公司名称",
            "bank": "开户行",
            "account": "账号",
            "bankName": "开户行名称",
            "address": "通讯地址",
            "contactPerson": "联系人",
            "contactTel": "18355555555"
         }],
		"total": 1
	}
}
```
* items说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | id |
| companyName | String | 公司名称 |
| bank | String | 开户行 |
| account | String | 账号 |
| bankName | String | 开户行名称 |
| address | String | 通讯地址 |
| contactPerson | String | 联系人 |
| contactTel | String | 联系电话 |

### 供应商列表（不分页）

* 接口描述: 获取开票信息所需的基础供应商信息
* 请求地址: /supply/list
* 请求方法: get
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| companyName | String | 否   | 公司名称(模糊搜索) |
| bankName | String | 否   | 开户行名称(模糊搜索) |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": [{
		"id": 1,
		"companyName": "公司名称",
		"bank": "开户行",
		"account": "账号",
		"bankName": "开户行名称",
		"address": "通讯地址",
		"contactPerson": "联系人",
		"contactTel": "18355555555"
	}]
}
```
* data说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | id |
| companyName | String | 公司名称 |
| bank | String | 开户行 |
| account | String | 账号 |
| bankName | String | 开户行名称 |
| address | String | 通讯地址 |
| contactPerson | String | 联系人 |
| contactTel | String | 联系电话 |


### 新增供应商

* 接口描述: 新增供应商
* 请求地址: /supply/create
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| companyName | String | 是   | 公司名称   |
| bank | String | 是   | 开户行 |
| account | String | 是   | 账号 |
| bankName | String | 是   | 开户行名称 |
| address | String | 是   | 通讯地址 |
| contactPerson | String | 是   | 联系人 |
| contactTel | String | 是   | 联系电话 |
| remark | String | 否   | 备注 |

* 请求示例

```json
{
  "companyName": "公司名称",
  "bank": "开户行",
  "account": "账号",
  "bankName": "开户行名称",
  "address": "通讯地址",
  "contactPerson": "联系人",
  "contactTel": "18351222222",
  "remark": ""
}
```

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 供应商修改

* 接口描述: 对申请信息进行修改
* 请求地址: /supply/update/:id
* 请求方法: put
* 请求参数: 同create方法

* 请求示例

```json
{
  "companyName": "公司名称",
  "bank": "开户行",
  "account": "账号",
  "bankName": "开户行名称",
  "address": "通讯地址",
  "contactPerson": "联系人",
  "contactTel": "18351222222",
  "remark": ""
}
```

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 供应商信息查看

* 接口描述: 查看某条供应商信息详情
* 请求地址: /supply/showOne/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"companyName": "公司名称",
		"bank": "开户行",
		"account": "账号",
		"bankName": "开户行名称",
		"address": "通讯地址",
		"contactPerson": "联系人",
		"contactTel": "18351222222",
		"remark": ""
	}
}
```

### 供应商信息删除

* 接口描述: 对暂存的投标记录进行删除
* 请求地址: /supply/delete/:id
* 请求方法: delete
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```


## 客户管理

### 客户列表(带分页)

* 接口描述: 分页获取客户列表
* 请求地址: /client/listPage
* 请求方法: get
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| limit | Number | 是   | 每页几条   |
| page | Number | 是   | 第几页 |
| name | String | 否   | 客户名称 |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"items": [{
			"id": 1,
			"name": "客户名称",
			"contactPerson": "联系人",
			"contactTel": "13100000000",
			"followStatus": "跟进中",
			"createTime": "2022-03-01 11:20:01"
		}],
		"total": 1
	}
}
```
* items说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | id |
| name | String | 客户名称 |
| contactPerson | String | 联系人 |
| contactTel | String | 联系电话 |
| followStatus | String | 洽谈进展 |
| createTime | String | 创建时间 |

### 客户列表(不带分页)

* 接口描述: 获取客户列表,合同模块填写客户名称用
* 请求地址: /client/list
* 请求方法: get
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| name | String | 否   | 客户名称,模糊查询 |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": [{
		"id": 1,
		"name": "客户名称",
		"contactPerson": "联系人",
		"contactTel": "13100000000",
		"followStatus": "跟进中",
		"createTime": "2022-03-01 11:20:01"
	}]
}
```
* data说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | id |
| name | String | 客户名称 |
| contactPerson | String | 联系人 |
| contactTel | String | 联系电话 |
| followStatus | String | 洽谈进展 |
| createTime | String | 创建时间 |

### 新增客户

* 接口描述: 新增客户
* 请求地址: /client/create
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| name | String | 是   | 客户名称   |
| contactPerson | String | 是   | 联系人 |
| contactTel | String | 是   | 联系电话 |
| source | String | 是   | 来源 下拉选择 1-自寻 2-推荐 3-其他 |
| chargePerson | String | 是   | 负责人 下拉选择用户(可搜索) |
| level | String | 是   | 客户级别 下拉选择 1-重要 2-一般 |
| followStatus | String | 是   | 跟进状态 |
| remark | String | 否   | 备注 |

* 请求示例

```json
{
  "name": "客户名称",
  "contactPerson": "联系人",
  "contactTel": "13100000000",
  "source": "1",
  "chargePerson": "小test",
  "level": "2",
  "followStatus": "跟进中",
  "remark": ""
}
```

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 客户修改

* 接口描述: 对客户信息进行修改
* 请求地址: /client/update/:id
* 请求方法: put
* 请求参数: 同create方法

* 请求示例

```json
{
  "name": "客户名称",
  "contactPerson": "联系人",
  "contactTel": "13100000000",
  "source": "1",
  "chargePerson": "小test",
  "level": "2",
  "followStatus": "跟进中",
  "remark": ""
}
```

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 客户信息查看

* 接口描述: 查看某条客户信息详情
* 请求地址: /client/showOne/:id
* 请求方法: get
* 请求参数: 无

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"name": "客户名称",
		"contactPerson": "联系人",
		"contactTel": "13100000000",
		"source": "1",
		"chargePerson": "小test",
		"level": "2",
		"followStatus": "跟进中",
		"remark": ""
	}
}
```

### 客户信息删除

* 接口描述: 对客户信息进行删除
* 请求地址: /client/delete/:id
* 请求方法: delete
* 请求参数: 无

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

## 首页--我的待办/已办

### 我的待办/已办列表

* 接口描述: 我的待办/已处理列表
* 请求地址: /activiti/myList
* 请求方法: get
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| limit | Number | 是   | 每页几条   |
| page | Number | 是   | 第几页 |
| type | Number | 是   | 1-待办 2-处理 |
| processType | String | 否   | 类型 下拉选择 tender-投标 contract-合同(待扩充) |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"items": [{
			"id": 1,
			"processType": "tender",
			"title": "Aa提交的投标申请",
			"summary": [{
					"name": "project_name",
					"alias": "项目名称",
					"value": "项目名称01"
				},
				{
					"name": "purchase_name",
					"alias": "采购单位名称",
					"value": "采购单位01"
				},
				{
					"name": "pay_date",
					"alias": "付款日期",
					"value": "2022-03-11"
				}
			],
			"applyPerson": "Aa",
			"applyDate": "2022-03-11",
			"status": "待部门经理审核"
		}],
		"total": 1
	}
}
```

* items说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | 主键id |
| processType | String | 流程类型 |
| title | String | 标题 |
| summary | Array | 摘要 |
| applyPerson | String | 发起人 |
| applyDate | String | 发起时间 |
| status | String | 流程状态 |


### 待办/处理详情
* 接口描述: 待办详情查询
* 请求地址: /activiti/showOne/:id
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| type | String | 是   | 哪种类型的信息详情 tender-投标 contract-合同(待扩充)   |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"id": 1,
		"processInstanceId": "207511",
		"processDefinitionKey": "tender",
		"infoList": [{
				"label": "基础信息",
				"key": "basicInfo",
				"info": [{
						"name": "projectName",
						"alias": "项目名称",
						"value": "项目名称01"
					},
					{
						"name": "purchaseName",
						"alias": "采购单位名称",
						"value": "采购单位01"
					},
					{
						"name": "payDate",
						"alias": "付款日期",
						"value": "2022-03-11"
					},
					{
						"name": "payType",
						"alias": "付款类型",
						"value": "投标保证金"
					}
				]
			},
			{
				"label": "报名费付款申请单",
				"key": "registeryPay",
				"info": [{
						"name": "payReason",
						"alias": "付款事由",
						"value": "付款事由报名费"
					},
					{
						"name": "receiveUnit",
						"alias": "收款单位",
						"value": "收款单位"
					},
					{
						"name": "bankAccount",
						"alias": "银行账号",
						"value": "11111"
					},
					{
						"name": "bankDepositary",
						"alias": "开户银行",
						"value": "招商银行"
					}
				]
			},
			{
				"label": "投标保证金付款申请单",
				"key": "marginPay",
				"info": [{
						"name": "payReason",
						"alias": "付款事由",
						"value": "付款事由投标保证金"
					},
					{
						"name": "receiveUnit",
						"alias": "收款单位",
						"value": "收款单位"
					},
					{
						"name": "bankAccount",
						"alias": "银行账号",
						"value": "11111"
					},
					{
						"name": "bankDepositary",
						"alias": "开户银行",
						"value": "招商银行"
					}
				]
			}
		]
	}
}
```

* data说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | 主键id |
| processInstanceId | String | 流程id |
| processDefinitionKey | String | 流程类型 |
| infoList | Array | 详情的具体内容 |

### 待办审核

* 接口描述: 审核代办信息
* 请求地址: /activiti/completeTaskCommon
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| processDefinitionKey | String | 是   | 哪种类型的信息详情   |
| processInstanceId | String | 是   | 流程id   |
| businessId | String | 是   | 主键id   |
| status | String | 是   | 1-审核通过 2-审核不通过  |
| reason | String | 否   | 不通过原因,审核不通过必填   |

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": null
}
```

### 审核流程

* 接口描述: 审核流程获取
* 请求地址: /activiti/processInfo
* 请求方法: get
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| instanceId | String | 是   | 流程id   |

* 响应示例

```json
{
  "code": 200,
  "message": "处理成功",
  "data": [
      {
         "examineStatus": "1",
         "examinePerson": "张三",
         "examineTime": "2022-02-17 13:53:00",
         "reason": ""
      },
      {
         "examineStatus": "2",
         "examinePerson": "李四",
         "examineTime": "2022-02-17 13:58:00",
         "reason": "不通过理由11"
      }
  ]
}
```

* data说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| examineStatus | String | 审核状态 1-通过 2-不通过 |
| examinePerson | String | 发起人/审核人 |
| examineTime | String | 审核时间 |
| reason | String | 审核不通过原因 |

## 日志管理

### 写日志

* 接口描述: 公司成员对每日、每周、每月的工作内容进行书面汇报
* 请求地址: /dailyRecord/create
* 请求方法: post
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| type | String | 是   | 日志类型 1-日报 2-周报 3-月报   |
| recordDate | String | 是   | 日志日期 默认当天   |
| title | String | 是   | 日志标题 |
| contentDone | String | 是   | 已完成 |
| contentUndone | String | 否   | 未完成 |
| contentCoordination | String | 否   | 需协调内容 |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": null
}
```


### 我收到的/我发出的日志列表

* 接口描述: 根据权限查看我收到的/我发出的日志列表
* 请求地址: /dailyRecord/receiveList
* 请求方法: get
* 请求参数

| 参数     | 类型   | 必须 | 描述   |
| -------- | ------ | ---- | ------ |
| limit | Number | 是   | 每页几条   |
| page | Number | 是   | 第几页 |
| type | String | 是   | 1-我收到的 2-我发出的 |
| contentDone | String | 否   | 已完成日志内容的模糊搜索 |

* 响应示例

```json
{
	"code": 200,
	"message": "处理成功",
	"data": {
		"items": [{
			"id": 1,
			"title": "张三的日报",
			"contentDone": "已完成内容123",
			"contentUnDone": "未完成内容456",
			"contentCoordination": "需协调内容789",
			"recordDate": "2022年03月14日 星期一",
			"createTime": "2022-03-14 18:01:02"
		}],
		"total": 1
	}
}
```

* items说明：

| 参数  | 类型   | 描述         |
| ----- | ------ | ------------ |
| id | Number | 主键id |
| title | String | 日志标题 |
| contentDone | String | 已完成内容 |
| contentUnDone | Array | 未完成内容 |
| contentCoordination | String | 需协调内容 |
| recordDate | String | 日志日期 |
| createTime | String | 创建时间 yyyy-MM-dd HH:mm:ss |