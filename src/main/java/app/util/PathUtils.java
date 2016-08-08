package app.util;

import static app.util.GitHubConstants.CSHARP_BASE_DIR;
import static app.util.GitHubConstants.CSHARP_LANG;
import static app.util.GitHubConstants.JAVA_BASE_DIR;
import static app.util.GitHubConstants.JAVA_LANG;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BinaryOperator;
public class PathUtils {
    private static BinaryOperator<String> concatWithHyphen = (s1, s2) -> s1 + "-" + s2;
    private static BinaryOperator<String> concatWithSlash = (s1, s2) -> s1 + "/" + s2;

    public static String createNameFromPath(String path) {
        String[] splitString = path.split("/");
        Optional<String> optResult = Arrays.stream(splitString).skip(6).reduce(concatWithHyphen);
        String result = optResult.get();
        return result;
    }

    public static String createPathFromName(String name, int chapter, String language) {
        String baseDir = "";
        if (language.equals(JAVA_LANG)) {
            baseDir = JAVA_BASE_DIR;
        }
        if (language.equals(CSHARP_LANG)) {
            baseDir = CSHARP_BASE_DIR;
        }
        String chapterPath = "ch";
        if (chapter < 10) {
            chapterPath += "0" + Integer.toString(chapter);
        }
        else {
            chapterPath += Integer.toString(chapter);
        }
        String dir = baseDir + "/" + chapterPath;
        String result = Arrays.stream(name.split("-")).reduce(dir, concatWithSlash);
        return result;
    }
}
