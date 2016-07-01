package app.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

public class GitHubConnector {
    public static final String USER_NAME = "oreillymedia";
    public static final String REPOSITORY_NAME = "building_maintainable_software";

    private static String token;

    public GitHubConnector() {
        token = System.getenv().get("OAUTH_TOKEN");
    }

    public GHRepository connectToGHRepository() throws IOException {
        return GitHub.connectUsingOAuth(token).getUser(USER_NAME).getRepository(REPOSITORY_NAME);
    }

    public String getFileContents(String path) throws IOException {
        GHRepository repo = connectToGHRepository();
        GHContent fileContents = repo.getFileContent(path);
        return castInputStream(fileContents.read());
    }

    private String castInputStream(InputStream iStream) throws IOException {
        return CharStreams.toString(new InputStreamReader(iStream, Charsets.UTF_8));
    }

    public List<String> getFileUrls() throws IOException {
        GHRepository repo = connectToGHRepository();
        List<String> paths = repo.getTreeRecursive("master", 1).getTree().stream()
            .filter(e -> e.getType().equals("blob")).map(e -> e.getPath()).collect(Collectors.toList());
        return paths;
    }
}
