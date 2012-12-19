package cx.lehmann.zip;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class JarFileScannerTest {

    /**
     * @param args
     * @throws IOException 
     */

    @Test
    public void testScan() throws IOException {
        assertEquals(Arrays.asList("etsy_browse.story", "etsy_cart.story"),
                JarFileScanner.scanJar("src/test/resources/classes.zip", "**/*.story", "**/*_search.story"));
    }

}
