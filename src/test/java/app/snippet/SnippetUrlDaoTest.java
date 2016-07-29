package app.snippet;

import static app.snippet.SnippetUrlDao.CSHARP_LANG;
import static app.snippet.SnippetUrlDao.JAVA_LANG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SnippetUrlDaoTest {

    private List<List<SnippetReference>> getTestData() {
        SnippetReference ref1 = new SnippetReference("java", 2, "chapter1/snippet1.java");
        SnippetReference ref2 = new SnippetReference("java", 3, "chapter3/snippet2.java");
        SnippetReference ref3 = new SnippetReference("java", 2, "chapter2/snippet3.java");
        SnippetReference ref4 = new SnippetReference(CSHARP_LANG, 2, "chapter2/snippet3.cs");
        List<SnippetReference> list1 = new ArrayList<SnippetReference>();
        List<SnippetReference> list2 = new ArrayList<SnippetReference>();
        List<SnippetReference> list3 = new ArrayList<SnippetReference>();
        list1.add(ref1);
        list1.add(ref2);
        list2.add(ref1);
        list2.add(ref3);
        list3.add(ref1);
        list3.add(ref4);
        List<List<SnippetReference>> testData = new ArrayList<List<SnippetReference>>();
        testData.add(list1);
        testData.add(list2);
        testData.add(list3);
        return testData;
    }

    @Test
    public void getSnippetRefsByChapterNoOtherChaptersTest() {
        List<SnippetReference> list = getTestData().get(0);
        SnippetUrlDao testDao = new SnippetUrlDao(true);

        List<SnippetReference> resultList = testDao.getSnippetRefsByChapter(list, 2);
        assertNotEquals(list, resultList);
    }

    @Test
    public void getSnippetRefsByChapterNoSnippetsLostTest() {
        List<SnippetReference> list = getTestData().get(1);
        SnippetUrlDao testDao = new SnippetUrlDao(true);

        List<SnippetReference> resultList = testDao.getSnippetRefsByChapter(list, 2);
        assertEquals(list, resultList);
    }

    @Test
    public void getSnippetRefsByLangNoOtherLanguagesTest() {
        List<SnippetReference> list = getTestData().get(2);
        SnippetUrlDao testDao = new SnippetUrlDao(true);

        List<SnippetReference> resultList = testDao.getSnippetRefsByLang(list, JAVA_LANG);
        assertNotEquals(list, resultList);
    }
}
