package supermartijn642.websiteserver.content.project;

import supermartijn642.websiteserver.FileReader;
import supermartijn642.websiteserver.WebsiteServer;
import supermartijn642.websiteserver.content.Page;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created 9/20/2019 by SuperMartijn642
 */
public class ProjectPage extends Page {

    public static final String FILE_NAME = "common/project.html";

    private final Project project;

    public ProjectPage(Project project){
        this.project = project;
    }

    @Override
    protected void load(){
    }

    @Override
    protected String formatContent(){
        List<String> content = FileReader.getFileContent(new File(WebsiteServer.PATH.getValue(),FILE_NAME));
        HashMap<String,String> vars = new HashMap<>();
        vars.put("name",this.project.name);
        vars.put("description",this.project.description);
        FileReader.formatVars(content, vars);
        return FileReader.combineLines(content);
    }

    @Override
    protected String getPath(){
        return this.project.getPath();
    }

    @Override
    protected String getTitle(){
        return "SuperMartijn642's " + this.project.name;
    }

    @Override
    protected String getDescription(){
        return this.project.description;
    }
}
