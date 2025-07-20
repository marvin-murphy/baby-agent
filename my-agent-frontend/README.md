# AI 智能体应用前端

这是一个基于 Vue3 的前端项目，包含两个 AI 应用：AI 恋爱大师和 AI 超级智能体。两个应用都采用 Discord 风格的聊天界面，支持实时 SSE 连接。

## 功能特性

- 🏠 **主页导航**：可以在不同的 AI 应用之间切换
- 💕 **AI 恋爱大师**：专业的恋爱咨询和情感指导助手
- 🤖 **AI 超级智能体**：全能型 AI 助手，可以处理各种复杂任务
- 💬 **实时聊天**：基于 SSE 的实时消息推送
- 🎨 **现代化 UI**：Discord 风格的聊天界面

## 技术栈

- **Vue 3** - 响应式前端框架
- **Vue Router** - 单页面应用路由
- **Element Plus** - UI 组件库
- **Axios** - HTTP 客户端
- **Vite** - 构建工具和开发服务器

## 快速开始

### 安装依赖

```bash
npm install
```

### 启动开发服务器

```bash
npm run dev
```

项目将在 `http://localhost:5173` 上运行。

### 构建生产版本

```bash
npm run build
```

### 预览生产版本

```bash
npm run preview
```

## 后端接口

确保后端服务运行在 `http://localhost:8123`，并提供以下接口：

### AI 恋爱大师
- **接口**: `GET /api/ai/my_app/chat/sse`
- **参数**: 
  - `message` (string): 用户消息
  - `chatId` (string): 聊天室ID
- **返回**: SSE 流式响应

### AI 超级智能体
- **接口**: `GET /api/ai/manus/chat`
- **参数**: 
  - `message` (string): 用户消息
- **返回**: SSE 流式响应

## 项目结构

```
src/
├── components/          # 可复用组件
│   └── ChatInterface.vue   # 聊天界面组件
├── views/              # 页面组件
│   ├── Home.vue           # 主页
│   ├── LoveChat.vue       # AI恋爱大师页面
│   └── ManusChat.vue      # AI超级智能体页面
├── router/             # 路由配置
│   └── index.js
├── utils/              # 工具函数
│   └── chatUtils.js       # 聊天相关工具
├── App.vue             # 根组件
└── main.js             # 应用入口
```

## 功能说明

### 主页面
- 展示两个 AI 应用的入口
- 现代化的卡片设计
- 点击即可跳转到对应的聊天页面

### 聊天界面
- Discord 风格的暗色主题
- 用户消息显示在右侧（蓝色）
- AI 消息显示在左侧（灰色）
- 实时的打字指示器
- 自动滚动到最新消息
- 支持回车发送消息

### 技术实现
- 使用 SSE (Server-Sent Events) 实现实时消息推送
- 自动生成唯一的聊天室 ID
- 响应式设计，支持不同屏幕尺寸
- 组件化开发，高度可复用

## 开发说明

如果需要修改后端接口地址，请在对应的页面组件中修改 `api-endpoint` 属性。

如果需要自定义头像，请将图片文件放在 `public` 目录下，并在组件中引用正确的路径。

## 注意事项

1. 确保后端服务正常运行
2. 检查跨域配置是否正确
3. SSE 连接可能受到网络环境影响，建议在稳定的网络环境下使用 