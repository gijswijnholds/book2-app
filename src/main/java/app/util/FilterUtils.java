package app.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class FilterUtils {

    public static List<String> filterByChapterExistence(List<String> list) {
        return list.stream().filter(u -> containsChapterString(u)).collect(Collectors.toList());
    }

    private static Boolean containsChapterString(String u) {
        List<String> checkList = Arrays.stream(u.split("/")).filter(p -> p.startsWith("ch"))
            .collect(Collectors.toList());
        return !checkList.isEmpty();
    }

    public static List<String> filterByLang(List<String> list, String langSuffix) {
        return list.stream().filter(u -> u.endsWith(langSuffix)).collect(Collectors.toList());
    }

}
