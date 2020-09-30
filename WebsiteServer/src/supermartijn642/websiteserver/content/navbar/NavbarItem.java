package supermartijn642.websiteserver.content.navbar;

import supermartijn642.websiteserver.FileReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created 01/09/2019 by SuperMartijn642
 */
public class NavbarItem implements INavbarItem {

    private static final String ITEM_NAME = "navbar-item.html";

    private String name;
    private String href;
    private String icon;

    public NavbarItem(String name, String href, String icon){
        this.name = name;
        this.href = href;
        this.icon = icon;
    }

    @Override
    public String format(){
        List<String> content = FileReader.getFileContent(new File(Navbar.folder,ITEM_NAME));
        HashMap<String, String> vars = new HashMap<>();
        vars.put("name",this.name);
        vars.put("href",this.href);
        vars.put("icon",this.icon);
        FileReader.formatVars(content, vars);
        return FileReader.combineLines(content);
    }

}
