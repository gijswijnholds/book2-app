package app;

import static spark.Spark.get;
import static spark.Spark.port;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.snippet.Snippet;
import app.snippet.SnippetUrlDao;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;


public class Application {


    public static void main(String[] args) {

        // SnippetFetcher fetcher = new SnippetFetcher();
        Spark.staticFileLocation("/public");

        port(getHerokuAssignedPort());
        // get("/snippets/:chapter", );
        get("/hello", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("intro", "Hello Sylvan and Zeeger, did you like this snippet: ");
            SnippetUrlDao snippetUrlDao = new SnippetUrlDao();
            Snippet snippet = snippetUrlDao.getSnippet(snippetUrlDao.getSnippetRefs().get(0));

            String code = snippet.getCode();
            List<String> codes = new ArrayList<String>();
            codes.add(code);
            codes.add(code);
            model.put("codes", codes); //snippetUrlDao.getUrls()); // snippetDao.getAllSnippets().iterator().next().getCode());

            return new ModelAndView(model, "/velocity/hello/test.vm");
        }, new VelocityTemplateEngine());
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
