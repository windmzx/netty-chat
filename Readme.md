# Netty聊天软件

软件分为服务端和客户端



## 客户端

客户端使用JavaFx制作的UI。[Dyleaf-WeChat](https://github.com/Dyleaf/WeChat)UI部分来源于这个项目，感谢原作者。原项目是使用的是BIO的Socket编程使用json字符进行消息的传递。我修改了事件分发代码，并使用Netty进行网络字节流通信并自定义通信协议。

## 服务端

服务端使用Spring进行Bean的管理，使用Netty进行网络通信，使用Spring data jpa进行持久化的维护例如用户的好友关系等。



其中自定义了通信协议。定义基础消息类型之后，每个消息继承基础消息类型。将消息使用fastjson序列化之后协议字节流发给客户端。客户端收到字节流之后按照约定的字段读取消息并反序化为消息包之后交由后续的handler处理。

## 效果演示



登录

![登录](https://mzxstatic.oss-cn-beijing.aliyuncs.com/nettychat/%E7%99%BB%E5%BD%95.gif)

私聊

![私聊](https://mzxstatic.oss-cn-beijing.aliyuncs.com/nettychat/%E7%A7%81%E8%81%8A.gif)

建群

![建群](https://mzxstatic.oss-cn-beijing.aliyuncs.com/nettychat/%E5%88%9B%E5%BB%BA%E7%BE%A4%E8%81%8A.gif)

![群聊](https://mzxstatic.oss-cn-beijing.aliyuncs.com/nettychat/%E7%BE%A4%E8%81%8A.gif)