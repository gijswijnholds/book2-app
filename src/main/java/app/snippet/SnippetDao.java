package app.snippet;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class SnippetDao {

    private final List<Snippet> snippets = ImmutableList.of(
        new Snippet(1, "getRectangle", "public int getRectangle(int x, int y) { return x*y; }"),
        new Snippet(2, "calculateProduct", "public int calculateProduct(int x, int y) { return x*y; }"));

    public Iterable<Snippet> getAllSnippets() {
        return snippets;
    }

    public Snippet getSnippetById(int chapter, String name) {
        return snippets.stream().filter(b -> b.getChapter() == chapter && b.getName().equals(name)).findFirst()
            .orElse(null);
    }

}
