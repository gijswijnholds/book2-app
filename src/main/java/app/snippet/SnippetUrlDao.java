package app.snippet;

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

    public static final String JAVA_BASE_DIR = "src/java/eu/sig/training";
    public static final String CSHARP_BASE_DIR = "src/csharp/eu/sig/training";
    public static final String JAVA_LANG = "Java";
    public static final String CSHARP_LANG = "C#";

    private static final Snippet mockSnippet = new Snippet(1, "mocker",
        "public mocker() {\n return \" You have been mocked! \"; }");
    
    private static ImmutableList<SnippetReference> snippetRefs;

    private GitHubConnector connector = new GitHubConnector();

    public List<GHContent> snippetUrls;

    public SnippetUrlDao() throws IOException {
        snippetRefs = ImmutableList.copyOf(buildSnippetRefs());
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

    public List<SnippetReference> getSnippetRefsByChapter(List<SnippetReference> refs, int chapter) {
        return refs.stream().filter(r -> r.getChapter() == chapter).collect(Collectors.toList());
    }

    public List<SnippetReference> getSnippetRefsByLang(List<SnippetReference> refs, String lang) {
        return refs.stream().filter(r -> r.getLanguage().equals(lang)).collect(Collectors.toList());
    }
    
    public Snippet getMockSnippet() {
        return mockSnippet;
    }

}
