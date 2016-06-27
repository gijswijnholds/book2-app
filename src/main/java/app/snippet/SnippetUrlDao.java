package app.snippet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharStreams;

public class SnippetUrlDao {

    public static final String USER_NAME = "oreillymedia";
    public static final String REPOSITORY_NAME = "building_maintainable_software";
    public static final String JAVA_BASE_DIR = "src/java/eu/sig/training";
    public static final String CSHARP_BASE_DIR = "src/csharp/eu/sig/training";
    public static final String JAVA_LANG = "Java";
    public static final String CSHARP_LANG = "C#";

    private static ImmutableList<SnippetReference> snippetRefs; // = ImmutableList.copyOf(buildSnippetRefs());
    private static ImmutableList<String> urls;

    public List<GHContent> snippetUrls;

    public SnippetUrlDao() throws IOException {
        urls = ImmutableList.copyOf(buildUrls());
        // snippetRefs = ImmutableList.copyOf(buildSnippetRefs());
    }

    private List<String> buildUrls() throws IOException {
        GHRepository repo = GitHub.connectUsingOAuth("3bd471601beb629a87d3f81f2445deb1cd59deb6")
            .getUser(USER_NAME).getRepository(REPOSITORY_NAME);
        List<String> paths = repo.getTreeRecursive("master", 1).getTree().stream()
            .filter(e -> e.getType().equals("blob")).map(e -> e.getPath()).collect(Collectors.toList());
        return paths;
    }

    private List<SnippetReference> buildSnippetRefs() throws IOException {
        List<SnippetReference> refs = new ArrayList<SnippetReference>();
        refs.addAll(buildSnippetRefsForLanguage(JAVA_LANG, JAVA_BASE_DIR));
        refs.addAll(buildSnippetRefsForLanguage(CSHARP_LANG, CSHARP_BASE_DIR));
        return refs;
    }

    private List<SnippetReference> buildSnippetRefsForLanguage(String language, String baseDir) throws IOException {
        GHRepository repo = GitHub.connectAnonymously().getUser(USER_NAME).getRepository(REPOSITORY_NAME);

        List<GHContent> dirContents = repo.getDirectoryContent(baseDir);
        List<SnippetReference> refs = new ArrayList<SnippetReference>();
        
        for (GHContent content : dirContents) {
            String dirName = content.getName();
            int chapterNumber = getChapterNumber(dirName);
            List<SnippetReference> chapterSnippets = buildSnippetRefsForChapter(repo, language, chapterNumber,
                baseDir + "/" + dirName);
            refs.addAll(chapterSnippets);
        }
        return refs;
    }


    private List<SnippetReference> buildSnippetRefsForChapter(GHRepository repo, String language, int chapterNumber,
        String baseDir) throws IOException {
        List<GHContent> dirContents = repo.getDirectoryContent(baseDir);
        List<SnippetReference> refs = new ArrayList<SnippetReference>();

        for (GHContent content : dirContents) {
            if (content.isFile()) {
                refs.add(new SnippetReference(language, chapterNumber, baseDir + "/" + content.getName()));
            }
            if (content.isDirectory()) {
                refs.addAll(
                    buildSnippetRefsForChapter(repo, language, chapterNumber, baseDir + "/" + content.getName()));
            }
        }

        return refs;
    }

    private int getChapterNumber(String ch) {
        String string = ch.replaceAll("\\D+", "");
        int result = Integer.parseInt(string);
        return result;
    }

    public List<GHContent> initializeSnippetUrls() throws IOException {
      GHRepository repo = GitHub.connectAnonymously().getUser(USER_NAME).getRepository(REPOSITORY_NAME);
        return repo.getDirectoryContent(JAVA_BASE_DIR);
    }

    public String getSnippetRefs() {
        String result = "";
        for (SnippetReference ref : snippetRefs) {
            result += ref.toString() + "\n";
        }
        return result;
    }

    public String getUrls() {
      String result = "";
        for (String url : urls) {
            result += url + "\n"; // castInputStream(c.read());
      }
      return result;
    }

    public String getTest() {
        return " test";
    }

    public String getSnippet(int chapter, String name) throws IOException {
        GHContent fileContents = GitHub.connectAnonymously().getUser(USER_NAME).getRepository(REPOSITORY_NAME).getFileContent("src/java/eu/sig/training/ch0" + chapter + "/" + name + ".java");
        String result = castInputStream(fileContents.read());
        return result;
    }

    public String castInputStream(InputStream iStream) throws IOException {
      return CharStreams.toString(new InputStreamReader(iStream, Charsets.UTF_8));
    }
}
