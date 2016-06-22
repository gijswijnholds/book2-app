package app;

import static spark.Spark.get;
import static spark.Spark.port;

import java.util.HashMap;
import java.util.Map;

import app.snippet.SnippetDao;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;


public class Application {

    public static SnippetDao snippetDao;

    public static void main(String[] args) {
        snippetDao = new SnippetDao();

        Spark.staticFileLocation("/public");

        port(getHerokuAssignedPort());
        get("/hello", (req, res) -> { 
            Map<String, Object> model = new HashMap<>();
            model.put("intro", "Hello Sylvan and Zeeger, did you like this snippet: ");
            model.put("code", snippetDao.getAllSnippets().iterator().next().getCode());

            return new ModelAndView(model, "/velocity/hello/hello.vm");
        }, new VelocityTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}