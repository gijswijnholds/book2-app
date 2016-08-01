package app.util;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

public class ViewUtil {

    public static Route notFound = (Request req, Response res) -> {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView(model, Path.Templates.NOT_FOUND);
    };
}
