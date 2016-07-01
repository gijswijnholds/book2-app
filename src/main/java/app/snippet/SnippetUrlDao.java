package app.snippet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharStreams;

import app.util.FilterUtils;

public class SnippetUrlDao {

    public static final String USER_NAME = "oreillymedia";
    public static final String REPOSITORY_NAME = "building_maintainable_software";
    public static final String JAVA_BASE_DIR = "src/java/eu/sig/training";
    public static final String CSHARP_BASE_DIR = "src/csharp/eu/sig/training";
    public static final String JAVA_LANG = "Java";
    public static final String CSHARP_LANG = "C#";

    private static ImmutableList<SnippetReference> snippetRefs;
    private static String token;

    public List<GHContent> snippetUrls;

    public SnippetUrlDao() throws IOException {
        token = System.getenv().get("OAUTH_TOKEN");
        snippetRefs = ImmutableList.copyOf(buildSnippetRefs());
    }

    public String getToken() {
        return token;
    }
    private List<SnippetReference> buildSnippetRefs() throws IOException {
        List<String> urls = buildUrls();

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



    private List<String> buildUrls() throws IOException {
        GHRepository repo = connectToGHRepository();
        List<String> paths = repo.getTreeRecursive("master", 1).getTree().stream()
            .filter(e -> e.getType().equals("blob")).map(e -> e.getPath()).collect(Collectors.toList());
        return paths;
    }

    private GHRepository connectToGHRepository() throws IOException {
        return GitHub.connectUsingOAuth(token).getUser(USER_NAME)
            .getRepository(REPOSITORY_NAME);
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

    public Snippet getSnippet(SnippetReference snippetRef) throws IOException {
        GHRepository repo = connectToGHRepository();
        GHContent fileContents = repo.getFileContent(snippetRef.getPath());
        String code = castInputStream(fileContents.read());
        Snippet result = new Snippet(snippetRef.getChapter(), snippetRef.getFileName(), code);
        return result;
    }

    public String castInputStream(InputStream iStream) throws IOException {
      return CharStreams.toString(new InputStreamReader(iStream, Charsets.UTF_8));
    }
}
