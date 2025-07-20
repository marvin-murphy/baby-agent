<template>
  <div class="home-container">
    <div class="header">
      <h1>AI 智能体应用中心</h1>
      <p>选择您想要使用的AI应用</p>
    </div>
    
    <div class="app-grid">
      <div class="app-card" @click="goToLoveChat">
        <div class="app-icon love-icon">💕</div>
        <h3>AI 恋爱大师</h3>
        <p>专业的恋爱咨询和情感指导助手</p>
        <el-button type="primary" class="enter-btn">进入应用</el-button>
      </div>
      
      <div class="app-card" @click="goToManusChat">
        <div class="app-icon manus-icon">🤖</div>
        <h3>AI 超级智能体</h3>
        <p>全能型AI助手，可以处理各种复杂任务</p>
        <el-button type="primary" class="enter-btn">进入应用</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  methods: {
    goToLoveChat() {
      this.$router.push('/love-chat')
    },
    goToManusChat() {
      this.$router.push('/manus-chat')
    }
  }
}
</script>

<style scoped>
.home-container {
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.header {
  text-align: center;
  color: white;
  margin-bottom: 60px;
}

.header h1 {
  font-size: 3rem;
  margin-bottom: 10px;
  font-weight: 600;
}

.header p {
  font-size: 1.2rem;
  opacity: 0.9;
}

.app-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 40px;
  max-width: 800px;
  width: 100%;
}

.app-card {
  background: white;
  border-radius: 20px;
  padding: 40px 30px;
  text-align: center;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  cursor: pointer;
}

.app-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 30px 60px rgba(0, 0, 0, 0.15);
}

.app-icon {
  font-size: 4rem;
  margin-bottom: 20px;
}

.love-icon {
  color: #ff6b6b;
}

.manus-icon {
  color: #4ecdc4;
}

.app-card h3 {
  font-size: 1.5rem;
  margin-bottom: 15px;
  color: #333;
}

.app-card p {
  color: #666;
  line-height: 1.6;
  margin-bottom: 30px;
}

.enter-btn {
  width: 120px;
  height: 40px;
  border-radius: 20px;
  font-weight: 600;
}
</style> 