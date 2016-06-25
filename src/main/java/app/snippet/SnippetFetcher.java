package app.snippet;

import java.io.IOException;
import java.io.InputStreamReader;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GitHub;
import com.google.common.io.CharStreams;
import com.google.common.base.Charsets;

public class SnippetFetcher {

    public static final String USER_NAME = "oreillymedia";
    public static final String REPOSITORY_NAME = "building_maintainable_software";

    public String getSnippet(int chapter, String name) throws IOException {
        GHContent fileContents = GitHub.connectAnonymously().getUser(USER_NAME).getRepository(REPOSITORY_NAME).getFileContent("src/java/eu/sig/training/ch0" + chapter + "/" + name + ".java");
        String result = CharStreams.toString(new InputStreamReader(fileContents.read(), Charsets.UTF_8));
        return result;
    }
}
