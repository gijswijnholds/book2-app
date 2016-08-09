package app.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FilterUtilsTest {

    @Test
    public void testFilterByChapterExistence() {
        List<String> input = new ArrayList<String>();
        input.add("hello/this/is/a/test/and/this/should/fail");
        input.add("another/test/but/this/should/work/ch05/");
        input.add("yet/anocher/chtest/which/should/fail");
        List<String> result = FilterUtils.filterByChapterExistence(input);
        assertEquals("another/test/but/this/should/work/ch05/", result.get(0));
        assertEquals(1, result.size());

    }

    @Test
    public void testFilterByLang() {
        String lang = "java";
        List<String> input = new ArrayList<String>();
        input.add("shouldWork.java");
        input.add("shouldFail.ext");
        List<String> result = FilterUtils.filterByLang(input, lang);
        assertEquals("shouldWork.java", result.get(0));
        assertEquals(1, result.size());
    }

}
