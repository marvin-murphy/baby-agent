package com.henrylin.myagent.demo.invoke;
// 导入所需的 Hutool 类
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 测试http请求 跑不起来
 */
public class HttpAiInvoke {
    public static void main(String[] args) {
        // 构建请求体 JSON
        JSONObject requestBody = JSONUtil.createObj()
                .set("model", "qwen-plus")
                .set("input", JSONUtil.createObj()
                        .set("messages", JSONUtil.createArray()
                                .add(JSONUtil.createObj()
                                        .set("role", "system")
                                        .set("content", "You are a helpful assistant"))
                        ))
                .set("parameters", JSONUtil.createObj()
                        .set("result_format", "message"));

        // 发送 HTTP 请求
        String result = HttpRequest.post("https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation")
                .header("Authorization", "Bearer " + System.getenv("DASHSCOPE_API_KEY"))
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .execute()
                .body();

        // 解析响应结果（如果需要）
        JSONObject response = JSONUtil.parseObj(result);
    }
}
