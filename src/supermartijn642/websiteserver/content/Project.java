package supermartijn642.websiteserver.content;

import supermartijn642.websiteserver.FileReader;

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
    public Category category;

    private Project(File path, String name, String description, Category category){
        this.path = path;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getIcon(){
        return this.getPath() + "/icon.png";
    }

    public String getPath(){
        return this.path.getPath();
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
        for(String line : info){
            if(line.startsWith("name="))
                name = line.substring("name=".length() + 1).trim();
            else if(line.startsWith("description="))
                description = line.substring("description=".length() + 1).trim();
        }
        if(name == null){
            System.out.println("[ERROR] '" + infoFile.getName() + "' is missing a name!");
            return;
        }
        if(description == null){
            System.out.println("[ERROR] '" + infoFile.getName() + "' is missing a description!");
            return;
        }
        Project project = new Project(path, name, description, category);
        projects.add(project);
        category.projects.add(project);
        System.out.println("[INFO] Added project '" + name + "'");
    }
}
