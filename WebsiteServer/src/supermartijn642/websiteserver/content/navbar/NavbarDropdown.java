package supermartijn642.websiteserver.content.navbar;

import supermartijn642.websiteserver.FileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created 01/09/2019 by SuperMartijn642
 */
public class NavbarDropdown implements INavbarItem {

    private static final String DROPDOWN_NAME = "navbar-dropdown.html";

    private List<NavbarItem> items = new ArrayList<>();
    private String name;
    private String href;
    private String icon;

    public NavbarDropdown(String name, String href, String icon){
        this.name = name;
        this.href = href;
        this.icon = icon;
    }

    public NavbarDropdown add(NavbarItem item){
        this.items.add(item);
        return this;
    }

    public NavbarDropdown addAll(Collection<NavbarItem> items){
        this.items.addAll(items);
        return this;
    }

    @Override
    public String format(){
        List<String> content = FileReader.getFileContent(new File(Navbar.folder, DROPDOWN_NAME));
        HashMap<String,String> vars = new HashMap<>();
        vars.put("name", this.name);
        vars.put("href", this.href);
        vars.put("icon", this.icon);
        List<String> lines = new ArrayList<>();
        this.items.forEach(i -> lines.add(i.format()));
        vars.put("content",FileReader.combineLines(lines));
        FileReader.formatVars(content, vars);
        return FileReader.combineLines(content);
    }
}
