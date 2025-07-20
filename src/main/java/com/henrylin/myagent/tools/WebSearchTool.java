package com.henrylin.myagent.tools;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebSearchTool {

    // SearchAPI 的搜索接口地址
    private static final String SEARCH_API_URL = "https://www.searchapi.io/api/v1/search";

    private final String apiKey;

    public WebSearchTool(String apiKey) {
        this.apiKey = apiKey;
    }

    @Tool(description = "Search for information from Baidu Search Engine")
    public String searchWeb(
            @ToolParam(description = "Search query keyword") String query) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("q", query);
        paramMap.put("api_key", apiKey);
        paramMap.put("engine", "google");
        try {
            String response = HttpUtil.get(SEARCH_API_URL, paramMap);
            // 取出返回结果的前 5 条
            JSONObject jsonObject = JSONUtil.parseObj(response);
            // 提取 organic_results 部分
            JSONArray organicResults = jsonObject.getJSONArray("organic_results");

            // API可能返回不足5条结果，取实际数量和5之间的最小值
            int limit = Math.min(organicResults.size(), 5);
            List<Object> objects = organicResults.subList(0, limit);

            // 拼接搜索结果为字符串
            String result = objects.stream().map(obj -> {
                JSONObject tmpJSONObject = (JSONObject) obj;
                // 按需提取你真正需要的字段
                String title = tmpJSONObject.getStr("title");
                String link = tmpJSONObject.getStr("link");
                String snippet = tmpJSONObject.getStr("snippet");
                // 组合成一个更简洁、有用的字符串
                return String.format("Title: %s\nLink: %s\nSnippet: %s", title, link, snippet);
            }).collect(Collectors.joining("\n\n---\n\n")); // 每个结果之间用分隔符隔开，更易读

            return result;
        } catch (Exception e) {
            // 建议打印更详细的错误日志，方便调试
            // e.printStackTrace();
            return "Error searching: " + e.getMessage();
        }
    }
}
