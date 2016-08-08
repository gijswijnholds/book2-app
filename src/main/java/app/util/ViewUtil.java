package app.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpStatus;

import spark.ModelAndView;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

public class ViewUtil {

    public static String render(Map<String, Object> model, String templatePath) {
        VelocityTemplateEngine engine = new VelocityTemplateEngine();
        return engine.render(new ModelAndView(model, templatePath));
    }

    public static String notFound(Response response) {
        response.status(HttpStatus.NOT_FOUND_404);
        return render(new HashMap<>(), Path.Template.NOT_FOUND);
    }
}
