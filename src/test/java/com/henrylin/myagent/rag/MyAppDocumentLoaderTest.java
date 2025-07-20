package com.henrylin.myagent.rag;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyAppDocumentLoaderTest {

    @Resource
    private MyAppDocumentLoader myAppDocumentLoader;

    @Test
    void loadMarkdowns() {
        myAppDocumentLoader.loadMarkdowns();
    }
}