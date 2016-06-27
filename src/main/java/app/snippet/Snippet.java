package app.snippet;

public class Snippet {
    private final int chapter;
    private final String name;
    private final String code;

    public Snippet(int chapter, String name, String code) {
        this.chapter = chapter;
        this.name = name;
        this.code = code;
    }

    public int getChapter() {
        return chapter;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}
