package supermartijn642.websiteserver.content.navbar;

import supermartijn642.websiteserver.FileReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created 01/09/2019 by SuperMartijn642
 */
public class NavbarIcon implements INavbarItem {

    private static final String ICON_NAME = "navbar-icon.html";

    private String href;
    private String icon;

    public NavbarIcon(String href, String icon){
        this.href = href;
        this.icon = icon;
    }

    @Override
    public String format(){
        List<String> content = FileReader.getFileContent(new File(Navbar.folder, ICON_NAME));
        HashMap<String, String> vars = new HashMap<>();
        vars.put("href",this.href);
        vars.put("icon",this.icon);
        FileReader.formatVars(content, vars);
        return FileReader.combineLines(content);
    }
}
