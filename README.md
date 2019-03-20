# KLocalService
本地服务
### AndServer
AndServer是Android平台的Web Server和Web Framework。 它基于编译时注解提供了类似SpringMVC的注解和功能，如果您熟悉SpringMVC，则可以非常快速地掌握它。

### 特性
#### 部署静态网站
#### 使用注解开发Http Api
#### 全局请求拦截器，使用注解，全局多个
#### 全局异常处理器，使用注解，全局唯一
#### 全局消息转换器，使用注解，全局唯一

### 依赖
####### 添加依赖时请替换下述 {version} 字段为 Github上公开的最新版本号。

        dependencies {
            implementation 'com.yanzhenjie.andserver:api:{version}'
            annotationProcessor 'com.yanzhenjie.andserver:processor:{version}'
        }

###  git源码

源码地址： https://github.com/yanzhenjie/AndServer 

文档地址： https://www.yanzhenjie.com/AndServer 

旧版文档： https://www.yanzhenjie.com/AndServer/1.x


个人感觉挺好用的，具体是在局域网下通讯，多个子设备请求一个父设备 。特定场景可使用
