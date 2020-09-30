package supermartijn642.websiteserver.content.project;

import supermartijn642.websiteserver.FileReader;
import supermartijn642.websiteserver.content.home.HomePage;
import supermartijn642.websiteserver.content.project.Project;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created 02/09/2019 by SuperMartijn642
 */
public class ProjectSection {

    private static final String SECTION_FILE = "section.html";
    private static final String IMAGE_FILE = "image-column.html";
    private static final String TEXT_FILE = "text-column.html";

    private Project project;

    public ProjectSection(Project project){
        this.project = project;
    }

    public String format(boolean inverted){
        List<String> content = FileReader.getFileContent(new File(HomePage.folder, IMAGE_FILE));
        HashMap<String,String> vars = new HashMap<>();
        vars.put("name", this.project.name);
        vars.put("description", this.project.description);
        vars.put("path", this.project.getPath());
        vars.put("icon", this.project.getIcon());
        FileReader.formatVars(content, vars);
        String image_column = FileReader.combineLines(content);
        content = FileReader.getFileContent(new File(HomePage.folder, TEXT_FILE));
        FileReader.formatVars(content, vars);
        String text_column = FileReader.combineLines(content);

        content = FileReader.getFileContent(new File(HomePage.folder, SECTION_FILE));
        vars.clear();
        vars.put("column-left", inverted ? text_column : image_column);
        vars.put("column-right", inverted ? image_column : text_column);
        FileReader.formatVars(content, vars);
        return FileReader.combineLines(content);
    }
}
