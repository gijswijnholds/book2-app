package app.snippet;

import static app.util.GitHubConstants.CSHARP_BASE_DIR;
import static app.util.GitHubConstants.CSHARP_LANG;
import static app.util.GitHubConstants.JAVA_BASE_DIR;
import static app.util.GitHubConstants.JAVA_LANG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import app.util.SnippetReferenceUtils;

public class SnippetUrlDaoTest {

    private List<List<SnippetReference>> getTestData() {
        SnippetReference ref1 = new SnippetReference(JAVA_LANG, 2, JAVA_BASE_DIR + "/ch01/snippet1.java");
        SnippetReference ref2 = new SnippetReference(JAVA_LANG, 3, JAVA_BASE_DIR + "/ch03/snippet2.java");
        SnippetReference ref3 = new SnippetReference(JAVA_LANG, 2, JAVA_BASE_DIR + "/ch02/snippet3.java");
        SnippetReference ref4 = new SnippetReference(CSHARP_LANG, 2, CSHARP_BASE_DIR + "/ch02/snippet3.cs");
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
        //    SnippetUrlDao testDao = new SnippetUrlDao();

        List<SnippetReference> resultList = SnippetReferenceUtils.getSnippetRefsByChapter(list, 2);
        assertNotEquals(list, resultList);
    }

    @Test
    public void getSnippetRefsByChapterNoSnippetsLostTest() {
        List<SnippetReference> list = getTestData().get(1);
        //    SnippetUrlDao testDao = new SnippetUrlDao();

        List<SnippetReference> resultList = SnippetReferenceUtils.getSnippetRefsByChapter(list, 2);
        assertEquals(list, resultList);
    }

    @Test
    public void getSnippetRefsByLangNoOtherLanguagesTest() {
        List<SnippetReference> list = getTestData().get(2);
        //  SnippetUrlDao testDao = new SnippetUrlDao();

        List<SnippetReference> resultList = SnippetReferenceUtils.getSnippetRefsByLang(list, JAVA_LANG);
        assertNotEquals(list, resultList);
    }
}
