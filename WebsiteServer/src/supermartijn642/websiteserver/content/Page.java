package supermartijn642.websiteserver.content;

import supermartijn642.websiteserver.FileReader;
import supermartijn642.websiteserver.WebsiteServer;
import supermartijn642.websiteserver.content.category.Category;
import supermartijn642.websiteserver.content.category.CategoryPage;
import supermartijn642.websiteserver.content.home.HomePage;
import supermartijn642.websiteserver.content.navbar.Navbar;
import supermartijn642.websiteserver.content.project.Project;
import supermartijn642.websiteserver.content.project.ProjectPage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created 02/09/2019 by SuperMartijn642
 */
public abstract class Page {

    private static final String PATH = "common/base.html";

    protected abstract void load();

    protected abstract String formatContent();

    protected abstract String getPath();

    protected abstract String getTitle();

    protected abstract String getDescription();

    public String format(){
        List<String> content = FileReader.getFileContent(new File(WebsiteServer.PATH.getValue(), PATH));
        HashMap<String,String> vars = new HashMap<>();
        vars.put("header", Header.format(this.getTitle(),this.getDescription()));
        vars.put("navbar", Navbar.navbar);
        vars.put("content", this.formatContent());
        FileReader.formatVars(content, vars);
        return FileReader.combineLines(content);
    }

    private static final Map<Page,String> pages = new HashMap<>();
    private static Page home;
    private static Page not_found;

    public static void loadPages(){
        home = new HomePage();
        not_found = new NotFoundPage();
        pages.put(home, null);
        pages.put(not_found, null);

        for(Category category : Category.categories)
            pages.put(new CategoryPage(category), null);
        for(Project project : Project.projects)
            pages.put(new ProjectPage(project), null);

        for(Page page : pages.keySet())
            page.load();
        for(Map.Entry<Page,String> entry : pages.entrySet())
            entry.setValue(entry.getKey().format());
    }

    public static String getPage(String path){
        if(path.equals("/"))
            path = WebsiteServer.DEFAULT_PAGE.getValue();
        File file = new File(WebsiteServer.PATH.getValue(), path);
        try{
            for(Map.Entry<Page,String> entry : pages.entrySet()){
                if(file.toPath().equals(new File(WebsiteServer.PATH.getValue(), entry.getKey().getPath()).toPath())){
                    return entry.getValue();
                }
            }
        }catch(Exception e){e.printStackTrace();}
        return pages.get(not_found);
    }

}
