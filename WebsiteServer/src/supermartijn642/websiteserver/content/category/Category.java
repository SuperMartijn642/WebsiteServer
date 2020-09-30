package supermartijn642.websiteserver.content.category;

import supermartijn642.websiteserver.FileReader;
import supermartijn642.websiteserver.WebsiteServer;
import supermartijn642.websiteserver.content.Page;
import supermartijn642.websiteserver.content.project.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 01/09/2019 by SuperMartijn642
 */
public class Category {

    public final List<Project> projects = new ArrayList<>();
    public File path;
    public String name;
    public String description;

    private Category(File path, String name, String description){
        this.path = path;
        this.name = name;
        this.description = description;
    }

    public String getIcon(){
        return "/" + new File(WebsiteServer.PATH.getValue()).toURI().relativize(new File(path,"icon.png").toURI()).getPath();
    }

    public String getPath(){
        return "/" + new File(WebsiteServer.PATH.getValue()).toURI().relativize(path.toURI()).getPath();
    }

    private static final String PATH = "category";

    public static final ArrayList<Category> categories = new ArrayList<>();

    public static void loadCategories(){
        File folder = new File(WebsiteServer.PATH.getValue(),PATH);
        folder.mkdirs();
        for(File path : folder.listFiles()){
            if(path.isDirectory())
                loadCategory(path);
        }
    }

    private static void loadCategory(File path){
        File infoFile = new File(path, path.getName() + ".info");
        if(!infoFile.exists()){
            System.out.println("[ERROR] Category '" + path.getName() + "' is missing an info file!");
            return;
        }
        List<String> info = FileReader.getFileContent(infoFile);
        String name = null;
        String description = null;
        for(String line : info){
            if(line.startsWith("name="))
                name = line.substring("name=".length()).trim();
            else if(line.startsWith("description="))
                description = line.substring("description=".length()).trim();
        }
        if(name == null){
            System.out.println("[ERROR] '" + infoFile.getName() + "' is missing a name!");
            return;
        }
        if(description == null){
            System.out.println("[ERROR] '" + infoFile.getName() + "' is missing a description!");
            return;
        }
        Category category = new Category(path, name, description);
        categories.add(category);
        System.out.println("[INFO] Added category '" + name + "'");
    }

}
