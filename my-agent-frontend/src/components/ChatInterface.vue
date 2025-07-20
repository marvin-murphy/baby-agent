<template>
  <div class="chat-container">
    <!-- 顶部导航栏 -->
    <div class="chat-header">
      <div class="header-left">
        <el-button @click="goHome" type="text" class="back-btn">
          <el-icon><ArrowLeft /></el-icon>
        </el-button>
        <h3>{{ title }}</h3>
      </div>
      <div class="chat-id">会话ID: {{ chatId }}</div>
    </div>

    <!-- 聊天消息区域 -->
    <div class="chat-messages" ref="messagesContainer">
      <div 
        v-for="(message, index) in messages" 
        :key="index" 
        :class="['message', message.type]"
      >
        <div class="message-avatar">
          <img :src="message.avatar" :alt="message.sender" />
        </div>
        <div class="message-content">
          <div class="message-header">
            <span class="message-sender">{{ message.sender }}</span>
            <span class="message-time">{{ formatTime(message.timestamp) }}</span>
          </div>
          <div class="message-text">{{ message.content }}</div>
        </div>
      </div>
      
      <!-- 正在输入指示器 -->
      <div v-if="isTyping" class="message ai typing-indicator">
        <div class="message-avatar">
          <img :src="aiAvatar" alt="AI" />
        </div>
        <div class="message-content">
          <div class="message-header">
            <span class="message-sender">{{ aiName }}</span>
            <span class="message-time">正在输入...</span>
          </div>
          <div class="typing-dots">
            <span></span>
            <span></span>
            <span></span>
          </div>
        </div>
      </div>
    </div>

    <!-- 输入区域 -->
    <div class="chat-input">
      <div class="input-container">
        <el-input
          v-model="inputMessage"
          placeholder="在这里输入您的消息..."
          @keyup.enter="sendMessage"
          :disabled="isLoading"
          class="message-input"
          rows="1"
          type="textarea"
          autosize
        />
        <el-button 
          type="primary" 
          @click="sendMessage"
          :disabled="isLoading || !inputMessage.trim()"
          class="send-btn"
        >
          <el-icon><Promotion /></el-icon>
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, nextTick, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { generateChatId } from '../utils/chatUtils'

export default {
  name: 'ChatInterface',
  props: {
    title: {
      type: String,
      required: true
    },
    apiEndpoint: {
      type: String,
      required: true
    },
    aiName: {
      type: String,
      default: 'AI助手'
    },
    aiAvatar: {
      type: String,
      default: '/ai-avatar.png'
    },
    stepByStep: {
      type: Boolean,
      default: false
    }
  },
  setup(props) {
    const router = useRouter()
    const messages = ref([])
    const inputMessage = ref('')
    const isLoading = ref(false)
    const isTyping = ref(false)
    const messagesContainer = ref(null)
    const chatId = ref('')

    const userAvatar = '/user-avatar.png'

    // 生成聊天室ID
    onMounted(() => {
      chatId.value = generateChatId()
    })

    const goHome = () => {
      router.push('/')
    }

    const formatTime = (timestamp) => {
      return new Date(timestamp).toLocaleTimeString('zh-CN', {
        hour: '2-digit',
        minute: '2-digit'
      })
    }

    const scrollToBottom = () => {
      nextTick(() => {
        if (messagesContainer.value) {
          messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
        }
      })
    }

    const addMessage = (content, type, sender) => {
      messages.value.push({
        content,
        type,
        sender,
        timestamp: Date.now(),
        avatar: type === 'user' ? userAvatar : props.aiAvatar
      })
      scrollToBottom()
    }

    const sendMessage = async () => {
      if (!inputMessage.value.trim() || isLoading.value) return

      const message = inputMessage.value.trim()
      inputMessage.value = ''
      
      // 添加用户消息
      addMessage(message, 'user', '我')
      
      isLoading.value = true
      isTyping.value = true

      try {
        // 构建请求URL
        const url = new URL(props.apiEndpoint, 'http://localhost:8123')
        url.searchParams.append('message', message)
        
        // 如果是恋爱大师应用，添加chatId参数
        if (props.apiEndpoint.includes('my_app')) {
          url.searchParams.append('chatId', chatId.value)
        }

        // 创建SSE连接
        const eventSource = new EventSource(url.toString())
        
        let aiResponse = ''
        let aiMessageIndex = -1
        let isNormalEnd = false // 标记是否正常结束

        eventSource.onmessage = (event) => {
          const data = event.data
          
          if (data === '[DONE]') {
            isNormalEnd = true // 标记为正常结束
            eventSource.close()
            isLoading.value = false
            isTyping.value = false
            return
          }

          if (props.stepByStep) {
            // 步骤模式：每个步骤单独显示为一个气泡
            if (data && data.trim()) {
              isTyping.value = false
              addMessage(data, 'ai', props.aiName)
            }
          } else {
            // 累加模式：将内容累加到同一个气泡中
            // 如果是第一次接收到数据，创建AI消息
            if (aiMessageIndex === -1) {
              isTyping.value = false
              addMessage('', 'ai', props.aiName)
              aiMessageIndex = messages.value.length - 1
            }

            // 累加响应内容
            aiResponse += data
            messages.value[aiMessageIndex].content = aiResponse
          }
          
          scrollToBottom()
        }

        eventSource.onerror = (error) => {
          console.error('SSE连接错误:', error)
          eventSource.close()
          isLoading.value = false
          isTyping.value = false
          
          // 只有在非正常结束的情况下才显示错误消息
          if (!isNormalEnd) {
            if (props.stepByStep || aiMessageIndex === -1) {
              addMessage('抱歉，连接出现问题，请稍后重试。', 'ai', props.aiName)
            }
          }
        }

      } catch (error) {
        console.error('发送消息失败:', error)
        isLoading.value = false
        isTyping.value = false
        addMessage('抱歉，发送消息失败，请稍后重试。', 'ai', props.aiName)
      }
    }

    return {
      messages,
      inputMessage,
      isLoading,
      isTyping,
      messagesContainer,
      chatId,
      goHome,
      formatTime,
      sendMessage
    }
  }
}
</script>

<style scoped>
.chat-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #36393f;
}

.chat-header {
  background-color: #2f3136;
  padding: 15px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #202225;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 15px;
}

.back-btn {
  color: #b9bbbe !important;
  font-size: 18px;
}

.back-btn:hover {
  color: #ffffff !important;
}

.chat-header h3 {
  color: #ffffff;
  margin: 0;
  font-size: 16px;
  font-weight: 600;
}

.chat-id {
  color: #b9bbbe;
  font-size: 12px;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

.message {
  display: flex;
  margin-bottom: 20px;
}

.message.user {
  justify-content: flex-end;
}

.message-avatar {
  width: 40px;
  height: 40px;
  margin: 0 15px;
}

.message-avatar img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
}

.message.user .message-avatar {
  order: 2;
}

.message-content {
  max-width: 70%;
  min-width: 0;
}

.message.user .message-content {
  order: 1;
}

.message-header {
  display: flex;
  align-items: baseline;
  gap: 10px;
  margin-bottom: 5px;
}

.message.user .message-header {
  justify-content: flex-end;
}

.message-sender {
  font-weight: 600;
  font-size: 14px;
}

.message.user .message-sender {
  color: #00d4aa;
}

.message.ai .message-sender {
  color: #7289da;
}

.message-time {
  font-size: 12px;
  color: #72767d;
}

.message-text {
  background-color: #40444b;
  padding: 12px 16px;
  border-radius: 18px;
  color: #dcddde;
  line-height: 1.4;
  word-wrap: break-word;
}

.message.user .message-text {
  background-color: #5865f2;
  color: #ffffff;
}

.typing-indicator .typing-dots {
  display: flex;
  gap: 4px;
  padding: 12px 16px;
  background-color: #40444b;
  border-radius: 18px;
}

.typing-dots span {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #72767d;
  animation: typing 1.4s infinite ease-in-out;
}

.typing-dots span:nth-child(1) { animation-delay: -0.32s; }
.typing-dots span:nth-child(2) { animation-delay: -0.16s; }

@keyframes typing {
  0%, 80%, 100% {
    transform: scale(0);
    opacity: 0.5;
  }
  40% {
    transform: scale(1);
    opacity: 1;
  }
}

.chat-input {
  background-color: #2f3136;
  padding: 20px;
  border-top: 1px solid #202225;
}

.input-container {
  display: flex;
  gap: 10px;
  align-items: flex-end;
  max-width: 1200px;
  margin: 0 auto;
}

.message-input {
  flex: 1;
}

:deep(.message-input .el-textarea__inner) {
  background-color: #40444b !important;
  border: 1px solid #202225 !important;
  color: #dcddde !important;
  border-radius: 8px !important;
  padding: 12px 16px !important;
  resize: none !important;
  min-height: 44px !important;
}

:deep(.message-input .el-textarea__inner::placeholder) {
  color: #72767d !important;
}

:deep(.message-input .el-textarea__inner:focus) {
  border-color: #5865f2 !important;
}

.send-btn {
  height: 44px;
  width: 44px;
  border-radius: 8px;
  background-color: #5865f2;
  border: none;
}

.send-btn:hover {
  background-color: #4752c4;
}

.send-btn:disabled {
  background-color: #4f545c;
  color: #6e7681;
}
</style> 