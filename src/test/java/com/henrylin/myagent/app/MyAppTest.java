package com.henrylin.myagent.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyAppTest {

    @Autowired
    public MyApp myApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();

        // 第一轮
        String message = "你好，我是Charles Leclerc";
        String answer = myApp.doChat(message, chatId);
        // 第二轮
        message = "我想让另一半Cassandra更爱我";
        answer = myApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 第三轮
        message = "我的另一半叫什么来着？帮我回忆一下";
        answer = myApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是Charles Leclerc, 我想让另一半Cassandra更爱我，但我不知道怎么做";
        MyApp.MyReport myReport = myApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(myReport);
    }

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但婚后关系不太亲密怎么办？";
        String s = myApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(s);
    }

    @Test
    void doChatWithTools() {
        // 测试联网搜索问题的答案
        // testMessage("周末想带一位初学者玩跑车浪漫旅7，推荐几个简单的赛道？");

        // 测试网页抓取：恋爱案例分析
        // testMessage("最近和对象吵架了，看看编程导航网站（codefather.cn）的其他情侣是怎么解决矛盾的？");

        // 测试资源下载：图片下载
        testMessage("直接下载一张法拉利F1赛车图片为文件");

        // 测试文件操作：保存用户档案
        // testMessage("保存我的恋爱档案为文件");

        // 测试 PDF 生成
        // testMessage("生成一份‘F1观赛计划’PDF，包含门票预订、活动流程和车手清单");
    }

    private void testMessage(String message) {
        String chatId = UUID.randomUUID().toString();
        String answer = myApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithMcp() {
        String chatId = UUID.randomUUID().toString();
        // 测试地图 MCP
        String message = "我的另一半居住在上海静安区，请帮我找到 5 公里内合适的约会地点";
        String answer = myApp.doChatWithMcp(message, chatId);
        Assertions.assertNotNull(answer);
    }

}