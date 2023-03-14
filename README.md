## 一. 框架简介

本框架基于睿阳RSP开发框架，集成工作流引擎 [Flowable](https://tkjohn.github.io/flowable-userguide/) 6.7.2。框架包含工作流涉及的组件，需要按文档集成到你现有的项目中，适用于进行过程中，需要集成工作流的项目。
## 二. 集成插件

### 1. 下载插件

下载 [工作流插件](https://192.168.1.30/risun/java-web/-/archive/flowable-plugin/java-web-flowable-plugin.zip)

### 2. 集成操作

解压下载后的 flowable.zip。（可以将project-flowable 修改为 [项目]-flowable）

- 将`project-flowable`加入工程，并修改`pom.xml`

``` xml
<parent>
    <groupId>com.risun</groupId>
    <artifactId>[项目]</artifactId>
    <version>3.0.1</version>
</parent>

<dependency>
    <groupId>com.risun</groupId>
    <artifactId>[项目]-framework</artifactId>
</dependency>
```

- 修改项目根目录下的 `pom.xml`
``` xml
<!-- 流程管理-->
<dependencyManagement>
  <dependencies>
    <dependency>
        <groupId>com.risun</groupId>
        <artifactId>project-flowable</artifactId>
        <version>${project.version}</version>
    </dependency>
  </dependencies>
</dependencyManagement>

<modules>
  <module>project-flowable</module>
</modules>
```

- 修改`<项目>-admin`目录下的 `pom.xml`
``` xml
<!-- 流程管理-->
<dependencies>
  <dependency>
      <groupId>com.risun</groupId>
      <artifactId>project-flowable</artifactId>
  </dependency>
</dependencies>
```

- 修改 `application.yml`，添加配置

  注意： 当项目库中已经生成`flowable`相关表之后，将`database-schema-update`设置为`false`
``` yml
## 工作流配置
flowable:
  database-schema-update: true
```

- 添加前端依赖
``` bash
npm i vkbeautify
npm i form-gen-parser
npm i workflow-bpmn-modeler
npm i diagram-js
```

- 将`ui`下的文件加入到`[项目]-ui`模块下（已存在的文件进行覆盖）

  - 将`styles`文件夹复制到`[项目]-ui/src/assets`下
  - 将`Process`文件夹复制到`[项目]-ui/src/components`下
  - 将`generator`文件夹复制到`[项目]-ui/src/utils`下
  - 将`flowable`文件夹复制到`[项目]-ui/src/views`下
  - 将`api/flowable`文件夹复制到`[项目]-ui/src/api`下
  - 将`build`文件夹复制到`[项目]-ui/src/views/tool`下
  - 在`<项目>-ui/src/router/index.js`中，添加如下内容
    ``` js
    // export const constantRoutes 的最后，添加
    {
      path: '/flowable',
      component: Layout,
      hidden: true,
      children: [
        {
          path: 'definition/model',
          component: () => import('@/views/flowable/definition/model'),
          name: 'Model',
          meta: { title: '流程设计', activeMenu: '/flowable/definition' }
        }
      ]
    },
    {
      path: '/tool',
      component: Layout,
      hidden: true,
      children: [{
        path: 'build/index',
        component: () => import('@/views/tool/build/index'),
        name: 'FormBuild',
        meta: {
          title: '表单配置',
          icon: ''
        }
      }]
    }
    ```
  
- 执行数据库脚本

   `sql/flowable-schema.sql`、`sql/flowable-data.sql`
