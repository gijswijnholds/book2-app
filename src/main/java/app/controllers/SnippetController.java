package app.controllers;

import static app.Application.snippetUrlDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import app.snippet.SnippetReference;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class SnippetController {

    public static Route serveOneSnippetPage = (Request request, Response response) -> {

        Map<String, Object> model = new HashMap<>();
        String language = request.params(":language");
        int chapter = Integer.parseInt(request.params(":chapter"));
        String name = request.params(":name");
        model.put("language", language);
        model.put("chapter", chapter);
        model.put("name", name);
        //    SnippetReference ref = new SnippetReference();
        //  snippetUrlDao.getSnippet(snippetRef);
        return ViewUtil.render(model, Path.Template.ONE_SNIPPET);
    };

    public static Route serveAllSnippetsPage = (Request request, Response response) -> {

        Map<String, Object> model = new HashMap<>();
        String language = request.params(":language");
        List<SnippetReference> snippetRefs = snippetUrlDao.getSnippetRefsByLang(language);

        Map<Integer, List<Map<String, String>>> snippetNamesGrouped = snippetRefs.stream()
            .collect(Collectors.groupingBy(SnippetReference::getChapter,
                Collectors.mapping(SnippetController::getNameAndPath, Collectors.toList())));

        model.put("language", language);
        model.put("snippets", snippetNamesGrouped);

        return ViewUtil.render(model, Path.Template.ALL_SNIPPETS);
    };

    private static Map<String, String> getNameAndPath(SnippetReference ref) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", ref.getFileName());
        map.put("path", ref.getPath());
        return map;
    }

}
