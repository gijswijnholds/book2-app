package app.snippet;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SnippetReferenceTest {

    @Test
    public void testGetFileName() {
        SnippetReference input = new SnippetReference("java", 2, "src/main/test.java");
        String result = input.getFileName();
        assertEquals("test.java", result);
    }

}
