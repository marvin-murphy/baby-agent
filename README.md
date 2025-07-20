# My Agent - 超级 AI 智能体系统

## 📖 项目简介

My Agent 是一个基于 Spring Boot 3.x 和 Vue.js 3.x 构建的全栈 AI 代理系统。该系统集成了阿里云大模型 (DashScope)，提供了强大的 AI 对话、工具调用和 RAG (检索增强生成) 功能，旨在为用户提供智能、高效的 AI 助手服务。

## ✨ 主要特性

- 🤖 **多种 AI 代理**：支持通用助手 MyManus 和专业领域助手 (如恋爱咨询)
- 🛠️ **丰富的工具集**：文件操作、PDF 生成、网络搜索、网络爬虫、资源下载等
- 📚 **RAG 知识库**：内置恋爱相关文档，支持上下文增强的智能问答
- 💬 **流式聊天**：支持 SSE 流式响应，提供实时对话体验
- 🎨 **现代化前端**：基于 Vue 3 + Element Plus 的响应式聊天界面
- 📊 **API 文档**：集成 Swagger UI 和 Knife4j，提供完整的 API 文档
- 🔄 **会话管理**：支持多会话并发，带有文件持久化的聊天记忆

## 🏗️ 技术架构

### 后端技术栈
- **框架**: Spring Boot 3.4.5
- **Java 版本**: Java 21
- **AI 集成**: 阿里云 DashScope SDK + Spring AI
- **数据库**: PostgreSQL (PgVector 向量存储)
- **文档**: Swagger UI + Knife4j
- **构建工具**: Maven

### 前端技术栈
- **框架**: Vue.js 3.x
- **UI 组件**: Element Plus
- **路由**: Vue Router 4.x
- **HTTP 客户端**: Axios
- **构建工具**: Vite

## 🚀 快速开始

### 环境要求

- Java 21+
- Node.js 16+
- Maven 3.6+
- PostgreSQL 12+ (如需使用向量存储功能)

### 后端启动

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd my-agent
   ```

2. **配置环境变量**
   
   创建 `src/main/resources/application-local.yml` 文件，配置阿里云 API Key：
   ```yaml
   spring:
     ai:
       dashscope:
         api-key: YOUR_DASHSCOPE_API_KEY
   ```

3. **启动后端服务**
   ```bash
   # 使用 Maven 启动
   ./mvnw spring-boot:run
   
   # 或者先编译再运行
   ./mvnw clean package
   java -jar target/my-agent-0.0.1-SNAPSHOT.jar
   ```

4. **访问 API 文档**
   - Swagger UI: http://localhost:8123/api/swagger-ui.html
   - Knife4j: http://localhost:8123/api/doc.html

### 前端启动

1. **进入前端目录**
   ```bash
   cd my-agent-frontend
   ```

2. **安装依赖**
   ```bash
   npm install
   ```

3. **启动开发服务器**
   ```bash
   npm run dev
   ```

4. **访问前端应用**
   
   前端应用将在 http://localhost:5173 上运行

### 生产环境部署

1. **构建前端**
   ```bash
   cd my-agent-frontend
   npm run build
   ```

2. **构建后端**
   ```bash
   ./mvnw clean package -DskipTests
   ```

3. **运行生产版本**
   ```bash
   java -jar target/my-agent-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```

## 🔧 配置说明

### 应用配置

主要配置文件位于 `src/main/resources/application.yml`：

```yaml
server:
  port: 8123                    # 服务端口
  servlet:
    context-path: /api          # API 基础路径

spring:
  application:
    name: my-agent
  profiles:
    active: local               # 激活的配置文件
```

### AI 模型配置

在 `application-local.yml` 中配置阿里云 DashScope：

```yaml
spring:
  ai:
    dashscope:
      api-key: ${DASHSCOPE_API_KEY}
      chat:
        model: qwen-plus         # 可选择不同的模型
```

## 📁 项目结构

```
my-agent/
├── src/main/java/com/henrylin/myagent/
│   ├── agent/                  # AI 代理实现
│   │   ├── BaseAgent.java     # 代理基类
│   │   ├── MyManus.java       # 通用 AI 助手
│   │   ├── ReActAgent.java    # ReAct 模式代理
│   │   └── ToolCallAgent.java # 工具调用代理
│   ├── app/                   # 应用层
│   │   └── MyApp.java         # 主应用逻辑
│   ├── controller/            # 控制器层
│   │   └── AiController.java  # AI 接口控制器
│   ├── tools/                 # 工具集
│   │   ├── FileOperationTool.java
│   │   ├── PDFGenerationTool.java
│   │   ├── WebSearchTool.java
│   │   └── ...
│   ├── rag/                   # RAG 功能
│   │   ├── MyAppDocumentLoader.java
│   │   ├── QueryRewriter.java
│   │   └── ...
│   └── chatmemory/           # 聊天记忆管理
│       └── FileBasedChatMemory.java
├── src/main/resources/
│   ├── document/              # 知识库文档
│   └── application.yml        # 配置文件
└── my-agent-frontend/         # 前端项目
    ├── src/
    │   ├── components/        # Vue 组件
    │   ├── views/            # 页面视图
    │   └── utils/            # 工具函数
    └── package.json
```

## 🎯 核心功能

### 1. AI 代理系统

- **MyManus**: 全能 AI 助手，支持工具调用和复杂任务处理
- **恋爱大师**: 专业的恋爱咨询助手，基于 RAG 知识库提供专业建议

### 2. 工具集成

| 工具名称 | 功能描述 |
|---------|---------|
| FileOperationTool | 文件读写操作 |
| PDFGenerationTool | PDF 文档生成 |
| WebSearchTool | 网络搜索功能 |
| WebScrapingTool | 网页内容抓取 |
| ResourceDownloadTool | 资源文件下载 |
| TerminateTool | 会话终止控制 |

### 3. RAG 知识库

内置恋爱相关知识库，包含：
- 单身阶段常见问题和建议
- 恋爱期间问题解答
- 已婚生活指导

### 4. 聊天界面

- 多会话管理
- 流式响应显示
- 消息历史记录
- 响应式设计

## 🔌 API 接口

### 主要接口

- `GET /api/ai/my_app/chat/sync` - 同步聊天接口
- `GET /api/ai/my_app/chat/sse` - 流式聊天接口
- `GET /api/ai/manus/chat/sync` - Manus 同步聊天
- `GET /api/ai/manus/chat/sse` - Manus 流式聊天

### 请求参数

| 参数名 | 类型 | 必需 | 描述 |
|-------|------|------|------|
| message | String | 是 | 用户消息内容 |
| chatId | String | 是 | 会话 ID |

## 🧪 测试

### 运行单元测试

```bash
./mvnw test
```

### 运行特定测试类

```bash
./mvnw test -Dtest=MyManusTest
```

## 📝 开发指南

### 添加新的 AI 代理

1. 继承 `BaseAgent` 或其子类
2. 实现必要的抽象方法
3. 在 `AiController` 中添加对应接口
4. 配置相关的系统提示词

### 添加新工具

1. 创建工具类，使用 `@Component` 注解
2. 实现工具的具体功能
3. 在 `ToolRegistration` 中注册工具
4. 添加相应的测试用例

### 扩展 RAG 功能

1. 在 `src/main/resources/document/` 目录添加文档
2. 配置文档加载器 `MyAppDocumentLoader`
3. 调整向量存储配置

## 🤝 贡献指南

1. Fork 本项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 📞 联系方式

如有问题或建议，也先别联系

## 🙏 致谢

感谢以下开源项目的支持：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring AI](https://spring.io/projects/spring-ai)
- [Vue.js](https://vuejs.org/)
- [Element Plus](https://element-plus.org/)
- [阿里云 DashScope](https://help.aliyun.com/product/2400395.html)
