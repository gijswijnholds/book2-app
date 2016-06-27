package app.snippet;

public class SnippetReference {

    private final String language;
    private final int chapter;
    private final String path;

    public SnippetReference(String language, int chapter, String path) {
        this.language = language;
        this.chapter = chapter;
        this.path = path;
    }

    public String getLanguage() {
        return language;
    }

    public int getChapter() {
        return chapter;
    }

    public String getPath() {
        return path;
    }
}
