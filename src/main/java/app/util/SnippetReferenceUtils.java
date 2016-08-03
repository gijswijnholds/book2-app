package app.util;

import java.util.List;
import java.util.stream.Collectors;

import app.snippet.SnippetReference;

public class SnippetReferenceUtils {

    public static List<SnippetReference> getSnippetRefsByChapter(List<SnippetReference> refs, int chapter) {
        return refs.stream().filter(r -> r.getChapter() == chapter).collect(Collectors.toList());
    }

    public static List<SnippetReference> getSnippetRefsByLang(List<SnippetReference> refs, String lang) {
        return refs.stream().filter(r -> r.getLanguage().equals(lang)).collect(Collectors.toList());
    }
}
