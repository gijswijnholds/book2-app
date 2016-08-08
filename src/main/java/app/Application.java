package app;

import static spark.Spark.get;
import static spark.Spark.port;

import java.io.IOException;

import app.controllers.HelloController;
import app.controllers.IndexController;
import app.controllers.SnippetController;
import app.snippet.SnippetUrlDao;
import spark.Spark;


public class Application {

    public static SnippetUrlDao snippetUrlDao;

    public static void main(String[] args) throws IOException {

        // SnippetFetcher fetcher = new SnippetFetcher();
        Spark.staticFileLocation("/public");
        port(getHerokuAssignedPort());

        snippetUrlDao = new SnippetUrlDao(true);


        get("/index", (request, response) -> {
            return IndexController.serveHomePage();
        });

        get("/hello", (request, response) -> {
            return HelloController.serveHelloPage();
        });

        get("/snippets/:language", (request, response) -> {
            return SnippetController.serveAllSnippetsPage(request, response);
        });

        get("/snippets/:language/:chapter/:name", (request, response) -> {
            return SnippetController.serveOneSnippetPage(request, response);
        });

        //    get("*", ViewUtil.notFound);

    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public String easterEgg() {
        return "hallo";
    }

}
