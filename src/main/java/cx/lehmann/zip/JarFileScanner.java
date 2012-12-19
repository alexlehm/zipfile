package cx.lehmann.zip;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.codehaus.plexus.util.SelectorUtils;

public class JarFileScanner {

    private URL pathname;
    private String include;
    private String exclude;

    private JarFileScanner(URL pathname, String include, String exclude) {
        this.pathname=pathname;
        this.include=localPathFormat(include);
        this.exclude=localPathFormat(exclude);
    }

    private List<String> scan() throws IOException {
        JarFile jar=new JarFile(pathname.getFile());
        try {
            List<String> result=new ArrayList<String>();
            Enumeration<JarEntry> en=jar.entries();
            while(en.hasMoreElements()) {
                JarEntry entry=en.nextElement();
                String path=entry.getName();
                String pathLocal=localPathFormat(path);
                if(SelectorUtils.matchPath(include, pathLocal)
                        && !SelectorUtils.matchPath(exclude, pathLocal)) {
                    result.add(path);
                }
            }
            return result;
        }
        finally {
            jar.close();
        }
    }

    // SelectorUtils assumes local path separator for path and pattern
    private String localPathFormat(String path) {
        return path.replace('/', File.separatorChar);
    }

    public static List<String> scanJar(URL pathname, String include, String exclude) throws IOException {
        return new JarFileScanner(pathname, include, exclude).scan();
    }

    public static List<String> scanJar(String pathname, String include, String exclude) throws IOException {
        return scanJar(new File(pathname).toURI().toURL(), include, exclude);
    }

}
