package app;

import static spark.Spark.get;
import static spark.Spark.port;

import app.snippet.SnippetDao;

public class Application {

    public static SnippetDao snippetDao;

    public static void main(String[] args) {
        snippetDao = new SnippetDao();

        port(getHerokuAssignedPort());
        get("/hello", (req, res) -> "Hello Sylvan and Zeeger, did you like this snippet: "
            + snippetDao.getAllSnippets().iterator().next().getCode());

    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}