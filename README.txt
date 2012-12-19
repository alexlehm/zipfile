add support for searching stories inside jar files, e.g. when running from a
"single" jar (take a look at my example executable-jar in jbehave-core how to
do that)

[this is just a first shot at the problem, if there is enough interest for this,
maybe we can include it in jbehave]

to include support for jars as well as normal classpath (when running inside eclipse)
the following code can be used:

    protected List<String> storyPaths() {
        URL codeLocation = codeLocationFromClass(this.getClass());
        if(new File(codeLocation.getFile()).isDirectory()) {
            return new StoryFinder().findPaths(codeLocation, "**/*.story",
                    "**/excluded*.story");
        } else {
            try {
                // the jar name has a ! at the end
                if(codeLocation.toString().endsWith("!")) {
                    codeLocation=new URL(StringUtils.removeEnd(codeLocation.toString(),"!"));
                }
                return JarFileScanner.scanJar(codeLocation, "**/*.story",
                        "**/excluded*.story");
            } catch (IOException e) {
                throw new CodeLocations.InvalidCodeLocation(codeLocation.toString());
            }
        }
    }

for any questions, send me a mail as Alexander Lehmann alexlehm@gmail.com
or take a look at the jira issue on https://jira.codehaus.org/browse/JBEHAVE-870

