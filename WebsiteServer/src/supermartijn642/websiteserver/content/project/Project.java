package supermartijn642.websiteserver.content.project;

import supermartijn642.websiteserver.FileReader;
import supermartijn642.websiteserver.WebsiteServer;
import supermartijn642.websiteserver.content.category.Category;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 01/09/2019 by SuperMartijn642
 */
public class Project {

    public File path;
    public String name;
    public String description;
    public String source;
    public String curse;
    public String github;
    public Category category;

    private Project(File path, String name, String description, String source, String curse, String github, Category category){
        this.path = path;
        this.name = name;
        this.source = source;
        this.curse = curse;
        this.github = github;
        this.description = description;
        this.category = category;
    }

    public String getIcon(){
        return "/" + new File(WebsiteServer.PATH.getValue()).toURI().relativize(new File(path, "icon.png").toURI()).getPath();
    }

    public String getPath(){
        return "/" + new File(WebsiteServer.PATH.getValue()).toURI().relativize(path.toURI()).getPath();
    }

    public static final ArrayList<Project> projects = new ArrayList<>();

    public static void loadProjects(){
        for(Category category : Category.categories){
            for(File path : category.path.listFiles()){
                if(path.isDirectory())
                    loadProject(category, path);
            }
        }
    }

    private static void loadProject(Category category, File path){
        File infoFile = new File(path, path.getName() + ".info");
        if(!infoFile.exists()){
            System.out.println("[ERROR] Project '" + path.getName() + "' is missing an info file!");
            return;
        }
        List<String> info = FileReader.getFileContent(infoFile);
        String name = null;
        String description = null;
        String source = null;
        String curse = null;
        String download = null;
        for(String line : info){
            if(line.startsWith("name="))
                name = line.substring("name=".length()).trim();
            else if(line.startsWith("description="))
                description = line.substring("description=".length()).trim();
            else if(line.startsWith("github="))
                source = line.substring("github=".length()).trim();
            else if(line.startsWith("download="))
                download = line.substring("download=".length()).trim();
            else if(line.startsWith("curse="))
                curse = line.substring("curse=".length()).trim();
        }
        if(name == null){
            System.out.println("[ERROR] '" + infoFile.getName() + "' is missing a name!");
            return;
        }
        if(description == null){
            System.out.println("[ERROR] '" + infoFile.getName() + "' is missing a description!");
            return;
        }
        Project project = new Project(path, name, description, source, curse, download, category);
        projects.add(project);
        category.projects.add(project);
        System.out.println("[INFO] Added project '" + name + "'");
    }
}
