## 1 登录

**请求URL：** 

- ` http://localhost:8081/user/login `

**请求方式：**
- POST 

**参数：** 

| 参数名 | 必选 | 类型   | 说明     |
| :----- | :--- | :----- | -------- |
| id     | 是   | string | 用户账号 |
| pass   | 是   | string | 密码     |


**返回示例**

``` 
返回res :
  -1 : 账号密码错误
   0 ：用户已经在线
   1 ：登录成功

```
## 2 获取用户好友，群信息

**请求URL：** 

- ` http://localhost:8081/user/mine `

**请求方式：**
- GET 

**参数说明：**

这个方法是在成功登录后，使用websocket进行连接后台服务器，并回去好友，群信息。

因为登录时后台会保存一个session，所以不用携带参数。

**返回示例**

``` 
{
	"code": 0,        //0表示成功，其它表示失败
	"msg": "成功",     //信息
	"data": {
	     //我的信息
		"mine": {
			"id": 2,             //我的ID
			"username": "小单",     //我的昵称
			"sign": "留取丹心照汗青",     "在深邃的编码世界，做一枚轻盈的纸飞机" //我的签名
			"status": "online",     //在线状态 online：在线、hide：隐身
			"avatar": "a.jpg",  //我的头像
			"sex": 1             //我的性别
		},
		//好友列表
		"friend": [{
			"id": 3,                //分组ID
			"groupname": "闺蜜",     //好友分组名		         
			"list": [{        //分组下的好友列表
				"id": 1,   
				"username": "小华",
				"sign": "人生自古谁无死",
				"status": "hide",
				"avatar": "头像图片地址",
				"sex": 1
			}]
		}],
		 //群组列表
		"group": [{
			"id": 11,               //群id
			"groupname": "群组一",   //群名称 
			"notice": "公告1",       //群公告
			"avatar": "头像图片地址", //群头像
			"user_id": 1             //群主id/用户id 
		}]
	}
}

```

## 3 获取群员信息

**请求URL：** 

- ` http://localhost:8081/user/mermber `

**请求方式：**
- GET 

**参数：** 

| 参数名  | 必选 | 类型   | 说明 |
| :------ | :--- | :----- | ---- |
| groupid | 是   | string | 群id |


**返回示例**

``` 
{
	"code": 0,        //0表示成功，其它表示失败
	"msg": "成功！",   //信息
	//群员信息
	"data": {
		"list": [{
			"id": 1,
			"username": "【群主】小华",
			"sign": "人生自古谁无死",
			"status": "hide",
			"avatar": "",
			"sex": 1
		}, {
			"id": 2,
			"username": "小单",
			"sign": "留取丹心照汗青",
			"status": "online",
			"avatar": "",
			"sex": 1
		}, {
			"id": 4,
			"username": "小迪",
			"sign": "独创然而涕下",
			"status": "hide",
			"avatar": "",
			"sex": 1
		}]
	}
}

```

## 4 单聊