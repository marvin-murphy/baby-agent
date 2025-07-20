package com.henrylin.myagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PgVectorVectorStoreConfigTest {

    @Resource
    private VectorStore vectorStore;

    @Test
    void pgVectorVectorStore() {
        List<Document> documents = List.of(
                new Document("索博c44有什么用？拖拉机啊，坑车手啊", Map.of("meta1", "meta2")),
                new Document("索博c44的最大升级是从推杆式悬架转变为拉杆式悬架"),
                new Document("索博F1车队在卡塔尔站得到全年唯一的4个积分", Map.of("meta2", "meta2"))
        );
        vectorStore.add(documents);
        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder().query("索博c44性能怎么样").topK(3).build());
        Assertions.assertNotNull(results);
    }
}