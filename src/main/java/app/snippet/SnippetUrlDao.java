package app.snippet;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.common.collect.ImmutableList;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GHRepository;
import com.google.common.io.CharStreams;
import com.google.common.base.Charsets;

public class SnippetUrlDao {

    public static final String USER_NAME = "oreillymedia";
    public static final String REPOSITORY_NAME = "building_maintainable_software";
    public List<GHContent> snippetUrls;

    public SnippetUrlDao() throws IOException {
        snippetUrls = initializeSnippetUrls();
    }

    public List<GHContent> initializeSnippetUrls() throws IOException {
      GHRepository repo = GitHub.connectAnonymously().getUser(USER_NAME).getRepository(REPOSITORY_NAME);
      return repo.getDirectoryContent("src");
    }

    public String getUrls() throws IOException {
      String result = "";
      for (GHContent c : snippetUrls) {
        result += castInputStream(c.read());
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
