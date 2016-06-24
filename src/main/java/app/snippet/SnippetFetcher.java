package app.snippet;

import java.io.IOException;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GitHub;

public class SnippetFetcher {

    public static final String USER_NAME = "oreillymedia";
    public static final String REPOSITORY_NAME = "building_maintainable_software";

    public GHContent fetchSnippet(int chapter, String name) throws IOException {
        GHContent fileContents = GitHub.connect().getUser(USER_NAME).getRepository(REPOSITORY_NAME)
            .getFileContent("src/java/eu/sig/training/ch0" + chapter + "/" + name + "Level.java");
        return fileContents;
    }
}
