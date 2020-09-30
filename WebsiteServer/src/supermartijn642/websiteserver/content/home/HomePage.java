package supermartijn642.websiteserver.content.home;

import supermartijn642.websiteserver.FileReader;
import supermartijn642.websiteserver.WebsiteServer;
import supermartijn642.websiteserver.content.Page;
import supermartijn642.websiteserver.content.project.Project;
import supermartijn642.websiteserver.content.project.ProjectSection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created 02/09/2019 by SuperMartijn642
 */
public class HomePage extends Page {

    public static final String PATH = "home";
    public static final String FILE_NAME = "home.html";
    public static final File folder = new File(WebsiteServer.PATH.getValue(),PATH);

    public List<ProjectSection> sections = new ArrayList<>();

    @Override
    protected void load(){
        for(Project project : Project.projects){
            this.sections.add(new ProjectSection(project));
        }
    }

    @Override
    protected String formatContent(){
        List<String> content = FileReader.getFileContent(new File(WebsiteServer.PATH.getValue(),PATH + "/" + FILE_NAME));
        HashMap<String,String> vars = new HashMap<>();
        List<String> lines = new ArrayList<>();
        for(int a = 0; a < this.sections.size(); a++)
            lines.add(this.sections.get(a).format(a % 2 == 0));
        vars.put("sections",FileReader.combineLines(lines));
        FileReader.formatVars(content, vars);
        return FileReader.combineLines(content);
    }

    @Override
    protected String getPath(){
        return PATH;
    }

    @Override
    protected String getTitle(){
        return "SuperMartijn642's awesome website!";
    }

    @Override
    protected String getDescription(){
        return "SuperMartijn642's awesome website!";
    }
}
