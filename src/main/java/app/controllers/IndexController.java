package app.controllers;

import java.util.HashMap;

import org.eclipse.jetty.http.HttpStatus;

import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class IndexController {

    public static Route serveHomePage = (Request request, Response response) -> {
        response.status(HttpStatus.OK_200);
        return ViewUtil.render(new HashMap<>(), Path.Template.INDEX);
    };
}
