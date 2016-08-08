package app.controllers;

import static app.Application.snippetUrlDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.snippet.Snippet;
import app.util.Path;
import app.util.ViewUtil;

public class HelloController {

    public static String serveHelloPage() {
 
        Map<String, Object> model = new HashMap<>();
        model.put("intro", "Hello Sylvan and Zeeger, did you like this snippet: ");
        Snippet snippet = snippetUrlDao.getSnippet(snippetUrlDao.getSnippetRefs().get(0));

        String code = snippet.getCode();
        List<String> codes = new ArrayList<String>();
        codes.add(code);
        codes.add(code);
        model.put("codes", codes); //snippetUrlDao.getUrls()); // snippetDao.getAllSnippets().iterator().next().getCode());

        return ViewUtil.render(model, Path.Template.HELLO);
    }
}
