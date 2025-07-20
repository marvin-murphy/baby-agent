package com.henrylin.myagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileOperationToolTest {

    @Test
    void readFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String filename = "HelloWorld.txt";
        String content = fileOperationTool.readFile(filename);
        Assertions.assertNotNull(content);
    }

    @Test
    void writeFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String filename = "HelloWorld.txt";
        String content = "OK Let's Race";
        String result = fileOperationTool.writeFile(filename, content);
        Assertions.assertNotNull(result);
    }
}