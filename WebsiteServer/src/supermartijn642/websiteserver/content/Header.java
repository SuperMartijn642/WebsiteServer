package supermartijn642.websiteserver.content;

import supermartijn642.websiteserver.FileReader;
import supermartijn642.websiteserver.WebsiteServer;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created 02/09/2019 by SuperMartijn642
 */
public class Header {

    private static final String PATH = "common/header.html";

    public static String format(String title, String description){
        List<String> content = FileReader.getFileContent(new File(WebsiteServer.PATH.getValue(), PATH));
        HashMap<String,String> vars = new HashMap<>();
        vars.put("title", title);
        vars.put("description",description);
        FileReader.formatVars(content, vars);
        return FileReader.combineLines(content);
    }
}
