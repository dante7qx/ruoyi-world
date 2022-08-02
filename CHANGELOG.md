## 1.0.0 (2022-08-01)

### 更新依赖

-  fastjson2	2.0.9 ==> 2.0.10

### 修复问题

- ImagePreview组件，无数据时报错

### 新增特性

- 后端

  - 添加导出word工具，Poi-tl 1.10.4
  - 添加word转pdf工具，aspose-word 15.8.0
  - 添加word转pdf工具类，Word2PdfUtil，测试类Word2PdfUtilTests
  - 添加Guava工具类库，Guava 31.1-jre
  - 添加工具类库，Hutool 5.8.4
  - 添加汉字转拼音工具类，pinyin4j 2.5.0，PinyinUtil
  - 添加 lombok
  - 使用undertow替代tomcat容器
  - 集成redisson实现redis分布式锁
  - 集成jsencrypt实现密码加密传输方式
  - 修改代码生成器，将列表页面和详情页面分离，便于组件化开发


- 前端
  - 添加 nanoid 4.0.0





