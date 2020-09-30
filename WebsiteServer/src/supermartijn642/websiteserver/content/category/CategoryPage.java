package supermartijn642.websiteserver.content.category;

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
 * Created 9/20/2019 by SuperMartijn642
 */
public class CategoryPage extends Page {

    public static final String FILE_NAME = "common/category.html";

    private final Category category;
    public List<ProjectSection> sections = new ArrayList<>();

    public CategoryPage(Category category){
        this.category = category;
    }

    @Override
    protected void load(){
        for(Project project : this.category.projects){
            this.sections.add(new ProjectSection(project));
        }
    }

    @Override
    protected String formatContent(){
        List<String> content = FileReader.getFileContent(new File(WebsiteServer.PATH.getValue(),FILE_NAME));
        HashMap<String,String> vars = new HashMap<>();
        vars.put("name",this.category.name);
        vars.put("description",this.category.description);
        List<String> lines = new ArrayList<>();
        for(int a = 0; a < this.sections.size(); a++)
            lines.add(this.sections.get(a).format(a % 2 == 0));
        vars.put("sections",FileReader.combineLines(lines));
        FileReader.formatVars(content, vars);
        return FileReader.combineLines(content);
    }

    @Override
    protected String getPath(){
        return this.category.getPath();
    }

    @Override
    protected String getTitle(){
        return "SuperMartijn642's " + this.category.name;
    }

    @Override
    protected String getDescription(){
        return this.category.description;
    }
}
