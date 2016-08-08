package app.controllers;

import java.util.HashMap;

import app.util.Path;
import app.util.ViewUtil;

public class IndexController {

    public static String serveHomePage() {
        return ViewUtil.render(new HashMap<>(), Path.Template.INDEX);
    }
}
