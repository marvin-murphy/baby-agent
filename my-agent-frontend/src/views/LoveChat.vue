<template>
  <ChatInterface
    title="AI 恋爱大师 💕"
    api-endpoint="/api/ai/my_app/chat/sse"
    ai-name="恋爱大师"
    ai-avatar="/love-ai-avatar.png"
  />
</template>

<script>
import ChatInterface from '../components/ChatInterface.vue'

export default {
  name: 'LoveChat',
  components: {
    ChatInterface
  }
}
</script> 