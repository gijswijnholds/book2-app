package app.util;

import static app.util.GitHubConstants.JAVA_BASE_DIR;
import static app.util.GitHubConstants.JAVA_LANG;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PathUtilsTest {

    @Test
    public void testCreateNameFromPath() throws Exception {
        String input = JAVA_BASE_DIR + "/ch02/v1/javaMethod.java";
        String result = PathUtils.createNameFromPath(input);
        assertEquals("v1-javaMethod.java", result);
    }

    @Test
    public void testCreatePathFromName() throws Exception {
        String input = "v1-javaMethod.java";
        String result = PathUtils.createPathFromName(input, 2, JAVA_LANG);
        assertEquals(JAVA_BASE_DIR + "/ch02/v1/javaMethod.java", result);
    }

    @Test
    public void testPathUtilsIsomorphicity() throws Exception {
        String input = JAVA_BASE_DIR + "/ch03/v2/hello/bye.java";
        String result = PathUtils.createPathFromName(PathUtils.createNameFromPath(input), 3, JAVA_LANG);
        String input2 = "v2-hello-bye.java";
        String result2 = PathUtils.createNameFromPath(PathUtils.createPathFromName(input2, 3, JAVA_LANG));
        assertEquals(input, result);
        assertEquals(input2, result2);
    }
}
