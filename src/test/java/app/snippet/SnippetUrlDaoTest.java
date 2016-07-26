package app.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SnippetUrlDaoTest {

    @Test
    public void getSnippetRefsByChapterTest() throws IOException {
        SnippetUrlDao testDao = new SnippetUrlDao();
        SnippetReference ref1 = new SnippetReference("java", 2, "chapter1/snippet1.java");
        SnippetReference ref2 = new SnippetReference("java", 3, "chapter3/snippet2.java");
        SnippetReference ref3 = new SnippetReference("java", 2, "chapter2/snippet3.java");
        List<SnippetReference> list1 = new ArrayList<SnippetReference>();
        List<SnippetReference> list2 = new ArrayList<SnippetReference>();
        list1.add(ref1);
        list1.add(ref2);
        list2.add(ref1);
        list2.add(ref3);
        List<SnippetReference> resultList1 = testDao.getSnippetRefsByChapter(list1, 2);
        List<SnippetReference> resultList2 = testDao.getSnippetRefsByChapter(list2, 2);
        assertNotEquals(list1, resultList1);
        assertEquals(list2, resultList2);
    }

}
