package supermartijn642.websiteserver.content.navbar;

import supermartijn642.websiteserver.FileReader;
import supermartijn642.websiteserver.WebsiteServer;
import supermartijn642.websiteserver.content.Category;
import supermartijn642.websiteserver.content.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created 01/09/2019 by SuperMartijn642
 */
public class Navbar {

    private List<INavbarItem> itemsStart = new ArrayList<>();
    private List<INavbarItem> itemsEnd = new ArrayList<>();

    public Navbar addStart(INavbarItem item){
        this.itemsStart.add(item);
        return this;
    }

    public Navbar addEnd(INavbarItem item){
        this.itemsEnd.add(item);
        return this;
    }

    public String format(){
        List<String> content = FileReader.getFileContent(new File(Navbar.folder, FILE_NAME));
        HashMap<String,String> vars = new HashMap<>();
        List<String> lines = new ArrayList<>();
        this.itemsStart.forEach(i -> lines.add(i.format()));
        vars.put("start",FileReader.combineLines(lines));
        lines.clear();
        this.itemsEnd.forEach(i -> lines.add(i.format()));
        vars.put("end",FileReader.combineLines(lines));
        FileReader.formatVars(content, vars);
        return FileReader.combineLines(content);
    }

    private static final String PATH = "common/navbar";
    private static final String FILE_NAME = "navbar.html";

    public static File folder;
    public static String navbar;

    public static void loadNavbar(){
        folder = new File(WebsiteServer.PATH.getValue(),PATH);
        if(!new File(folder,FILE_NAME).exists()){
            System.out.println("[ERROR] File '" + WebsiteServer.PATH.getValue() + "/" + PATH + "/" + FILE_NAME + "' is missing!");
            return;
        }
        Navbar navbar = new Navbar();
        for(Category category : Category.categories){
            NavbarDropdown dropdown = new NavbarDropdown(category.name,category.getPath(),category.getIcon());
            for(Project project : category.projects){
                NavbarItem item = new NavbarItem(project.name,project.getPath(),project.getIcon());
                dropdown.add(item);
            }
            navbar.addStart(dropdown);
        }
        Navbar.navbar = navbar.format();
    }

}