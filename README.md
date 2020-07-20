# imchat
1 基本实现的功能目标

- 单聊
- 群聊
- 添加，移除好友
- 添加，移除群聊
- 添加分组（好友分组）
- 修改签名，并实时同步
- 在线状态实时同步
- 离线消息推送



扩展功能：

- 文件传输
- 语音聊天
- 视频聊天
- 历史记录查询
- 朋友圈功能

2 技术栈

后端：SpringBoot + Mybatis + Netty + Websocket + Mysql + Rabbitmq



前端：vue



# 快速部署

1、clone 项目到本地 ：[https://github.com/zpsGo/imchat.git](https://github.com/zpsGo/imchat.git)

2、导入数据库脚本，脚本链接：[https://www.yuque.com/ksnf97/skd3h9](https://www.yuque.com/ksnf97/skd3h9)

3、修改项目application.yml相关的数据库链接信息，保证可以连接自己的数据库

4、提前准备好 RabbitMQ，在项目的 application.yml 文件中将 RabbitMQ 的配置改为自己的 

5、在 IntelliJ IDEA 中打开 imchat项目，启动项目即可。

**OK，至此，服务端就启动成功了，此时我们直接在地址栏输入** **在浏览器中输入 http://localhost:8080/swagger-ui.html 便可以访问后端接口。**

