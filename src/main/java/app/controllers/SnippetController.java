package app.controllers;

import static app.Application.snippetUrlDao;
import static app.util.GitHubConstants.CSHARP_LANG;
import static app.util.GitHubConstants.JAVA_LANG;
import static app.util.SnippetReferenceUtils.getSnippetRefsByLang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import app.snippet.Snippet;
import app.snippet.SnippetReference;
import app.util.Path;
import app.util.PathUtils;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;

public class SnippetController {

    public static String serveOneSnippetPage(Request request, Response response) {

        Map<String, Object> model = new HashMap<>();
        String language = request.params(":language");
        int chapter = Integer.parseInt(request.params(":chapter"));
        String name = request.params(":name");
        SnippetReference ref = new SnippetReference(language, chapter,
            PathUtils.createPathFromName(name, chapter, language));
        Snippet snippet = snippetUrlDao.getSnippet(ref);
        model.put("language", language);
        model.put("language-display", StringUtils.capitalize(language));
        model.put("chapter", chapter);
        model.put("name", name);
        model.put("code", snippet.getCode());
        //    SnippetReference ref = new SnippetReference();
        //  snippetUrlDao.getSnippet(snippetRef);
        return ViewUtil.render(model, Path.Template.ONE_SNIPPET);
    }

    public static String serveAllSnippetsPage(Request request, Response response) {

        Map<String, Object> model = new HashMap<>();
        String language = request.params(":language");
        if (!(language.equals(JAVA_LANG) || language.equals(CSHARP_LANG))) {
            return ViewUtil.notFound(response);
        }

        List<SnippetReference> snippetRefs = getSnippetRefsByLang(snippetUrlDao.getSnippetRefs(),
            language);

        Map<Integer, List<Map<String, String>>> snippetNamesGrouped = snippetRefs.stream()
            .collect(Collectors.groupingBy(SnippetReference::getChapter,
                Collectors.mapping(SnippetController::getNameAndUrl, Collectors.toList())));

        model.put("language", language);
        model.put("snippets", snippetNamesGrouped);

        return ViewUtil.render(model, Path.Template.ALL_SNIPPETS);
    }

    private static Map<String, String> getNameAndUrl(SnippetReference ref) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", ref.getFileName());
        String url = ref.getLanguage() + "/" + ref.getChapter() + "/" + ref.getRefName();
        map.put("url", url);
        return map;
    }

}
