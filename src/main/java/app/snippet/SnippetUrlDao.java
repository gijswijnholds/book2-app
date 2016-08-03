package app.snippet;

import static app.util.GitHubConstants.CSHARP_LANG;
import static app.util.GitHubConstants.JAVA_BASE_DIR;
import static app.util.GitHubConstants.JAVA_LANG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.kohsuke.github.GHContent;

import com.google.common.collect.ImmutableList;

import app.util.FilterUtils;
import app.util.GitHubConnector;

public class SnippetUrlDao {

    private static final Snippet mockSnippet = new Snippet(1, "mocker",
        "public mocker() {\n return \" You have been mocked! \"; }");
    
    private static ImmutableList<SnippetReference> snippetRefs;

    private GitHubConnector connector = new GitHubConnector();

    public List<GHContent> snippetUrls;

    public SnippetUrlDao(boolean connect) throws IOException {
        if (connect) {
            snippetRefs = ImmutableList.copyOf(buildSnippetRefs());
        } else {
            List<SnippetReference> mockList = new ArrayList<SnippetReference>();
            mockList.add(new SnippetReference(JAVA_LANG, 1, JAVA_BASE_DIR + "/ch01/mockSnippet.java"));
            snippetRefs = ImmutableList.copyOf(mockList);
        }
    }

    private List<SnippetReference> buildSnippetRefs() throws IOException {
        List<String> urls = connector.getFileUrls();

        List<String> javaUrls = FilterUtils.filterByChapterExistence(FilterUtils.filterByLang(urls, ".java"));
        List<String> csharpUrls = FilterUtils.filterByChapterExistence(FilterUtils.filterByLang(urls, ".cs"));

        List<SnippetReference> javaRefs = javaUrls.stream().map(u -> makeSnippetRef(JAVA_LANG, u))
            .collect(Collectors.toList());
        List<SnippetReference> csharpRefs = csharpUrls.stream().map(u -> makeSnippetRef(CSHARP_LANG, u))
            .collect(Collectors.toList());

        List<SnippetReference> allRefs = new ArrayList<SnippetReference>();
        allRefs.addAll(javaRefs);
        allRefs.addAll(csharpRefs);
        return allRefs;
    }

    private int getChapterNumber(String ch) {
        String string = ch.replaceAll("\\D+", "");
        int result = Integer.parseInt(string);
        return result;
    }

    public List<SnippetReference> getSnippetRefs() {
        return snippetRefs;
    }

    private SnippetReference makeSnippetRef(String language, String path) {
        String chapter = Arrays.stream(path.split("/")).filter(p -> p.startsWith("ch")).findFirst().get();
        int chapterNumber = getChapterNumber(chapter);
        SnippetReference result = new SnippetReference(language, chapterNumber, path);
        return result;
    }

    public Snippet getSnippet(SnippetReference snippetRef) {
        Snippet result;
        try {
            String code = connector.getFileContents(snippetRef.getPath());
            result = new Snippet(snippetRef.getChapter(), snippetRef.getFileName(), code);
        }
        catch (IOException e) {
            result = mockSnippet;
        }
        return result;
    }
    
    public Snippet getMockSnippet() {
        return mockSnippet;
    }

}
