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
import com.google.common.io.CharStreams;

public class SnippetUrlDao {

    public static final String USER_NAME = "oreillymedia";
    public static final String REPOSITORY_NAME = "building_maintainable_software";
    public static final String JAVA_BASE_DIR = "src/java/eu/sig/training";
    public static final String CSHARP_BASE_DIR = "src/csharp/eu/sig/training";
    public static final String JAVA_LANG = "Java";
    public static final String CSHARP_LANG = "C#";

    private static List<SnippetReference> snippetRefs;

    public List<GHContent> snippetUrls;

    public SnippetUrlDao() throws IOException {
        snippetRefs = buildSnippetRefs();
    }

    private List<SnippetReference> buildSnippetRefs() throws IOException {
        List<SnippetReference> refs = new ArrayList<SnippetReference>();
        refs.addAll(buildSnippetRefsForLanguage(JAVA_LANG, JAVA_BASE_DIR));
        refs.addAll(buildSnippetRefsForLanguage(CSHARP_LANG, CSHARP_BASE_DIR));
        return refs;
    }

    private List<SnippetReference> buildSnippetRefsForLanguage(String language, String baseDir) throws IOException {
        GHRepository repo = GitHub.connectAnonymously().getUser(USER_NAME).getRepository(REPOSITORY_NAME);
        List<String> dirContents = repo.getDirectoryContent(baseDir).stream().map(d -> d.getName())
            .collect(Collectors.toList());

        return null;
    }

    public List<GHContent> initializeSnippetUrls() throws IOException {
      GHRepository repo = GitHub.connectAnonymously().getUser(USER_NAME).getRepository(REPOSITORY_NAME);
        return repo.getDirectoryContent(JAVA_BASE_DIR);
    }

    public String getUrls() {
      String result = "";
      for (GHContent c : snippetUrls) {
            result += c.getType() + c.getPath() + "\n"; // castInputStream(c.read());
      }
      return result;
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
