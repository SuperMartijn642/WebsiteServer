package supermartijn642.websiteserver.content;

import supermartijn642.websiteserver.FileReader;
import supermartijn642.websiteserver.WebsiteServer;

import java.io.File;
import java.util.List;

/**
 * Created 02/09/2019 by SuperMartijn642
 */
public class NotFoundPage extends Page {

    private static final String FILE_NAME = "not-found.html";

    @Override
    protected void load(){
    }

    @Override
    protected String formatContent(){
        List<String> content = FileReader.getFileContent(new File(WebsiteServer.PATH.getValue(), FILE_NAME));
        return FileReader.combineLines(content);
    }

    @Override
    protected String getPath(){
        return "not_found";
    }

    @Override
    protected String getTitle(){
        return "Not Found :(";
    }

    @Override
    protected String getDescription(){
        return "404 Not Found";
    }
}
