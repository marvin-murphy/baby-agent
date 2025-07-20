<template>
  <ChatInterface
    title="AI 超级智能体 🤖"
    api-endpoint="/api/ai/manus/chat"
    ai-name="超级智能体"
    ai-avatar="/manus-ai-avatar.png"
    :step-by-step="true"
  />
</template>

<script>
import ChatInterface from '../components/ChatInterface.vue'

export default {
  name: 'ManusChat',
  components: {
    ChatInterface
  }
}
</script> 