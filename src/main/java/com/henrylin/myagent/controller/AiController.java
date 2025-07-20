package com.henrylin.myagent.controller;

import com.henrylin.myagent.agent.MyManus;
import com.henrylin.myagent.app.MyApp;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private MyApp myApp;

    @Resource
    private ToolCallback[] allTools;

    @Resource
    private ChatModel dashscopeChatModel;

    /**
     * 同步返回
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/my_app/chat/sync")
    public String doChatWithMyAppSync(String message, String chatId) {
        return myApp.doChat(message, chatId);
    }

    /**
     * 异步返回
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/my_app/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithLoveAppSSE(String message, String chatId) {
        return myApp.doChatByStream(message, chatId);
    }

    @GetMapping("/my_app/chat/sse/emitter")
    public SseEmitter doChatWithLoveAppSseEmitter(String message, String chatId) {
        // 创建一个超时时间较长的 SseEmitter
        SseEmitter emitter = new SseEmitter(180000L);
        // 获取 Flux 数据流并直接订阅
        myApp.doChatByStream(message, chatId)
                .subscribe(
                        // 处理每条消息
                        chunk -> {
                            try {
                                emitter.send(chunk);
                            } catch (IOException e) {
                                emitter.completeWithError(e);
                            }
                        },
                        emitter::completeWithError,
                        emitter::complete
                );
        return emitter;
    }

    /**
     * 流式调用 Manus
     *
     * @param message
     * @return
     */
    @GetMapping("/manus/chat")
    public SseEmitter doChatWithManus(String message) {
        MyManus myManus = new MyManus(allTools, dashscopeChatModel);
        return myManus.runStream(message);
    }

}
