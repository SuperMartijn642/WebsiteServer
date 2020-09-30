package supermartijn642.websiteserver;

import supermartijn642.httpserver.HttpFileResponse;
import supermartijn642.httpserver.HttpNotFoundResponse;
import supermartijn642.httpserver.HttpServerSocket;
import supermartijn642.httpserver.HttpServerSocketListener;
import supermartijn642.httpserver.event.SocketAcceptEvent;
import supermartijn642.httpserver.event.SocketBindEvent;
import supermartijn642.httpserver.event.SocketCloseEvent;
import supermartijn642.websiteserver.content.category.Category;
import supermartijn642.websiteserver.content.Header;
import supermartijn642.websiteserver.content.Page;
import supermartijn642.websiteserver.content.project.Project;
import supermartijn642.websiteserver.content.navbar.Navbar;

import java.io.File;
import java.util.Collections;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class WebsiteServer implements HttpServerSocketListener {

    private static final Config.IntegerEntry PORT = Config.addEntry(new Config.IntegerEntry("port", "80", "The port the server socket will be bound to."));
    public static final Config.StringEntry PATH = Config.addEntry(new Config.StringEntry("path", "./files", "The folder for files that can be requested."));
    public static final Config.StringEntry DEFAULT_PAGE = Config.addEntry(new Config.StringEntry("default_page", "home", "The default page for the website."));
    private static final Config.StringEntry NOT_FOUND_PAGE = Config.addEntry(new Config.StringEntry("not_found", "not_found", "The page that will be send when a file can't be found."));
    private static final Config.StringEntry FAVICON_PATH = Config.addEntry(new Config.StringEntry("favicon", "favicon.ico", "The file that will be sent when the favicon is requested."));

    public static void main(String[] args){
        init();
    }

    public static void init(){
        Config.initConfig();
        Category.loadCategories();
        Project.loadProjects();
        Navbar.loadNavbar();
        Page.loadPages();

        HttpServerSocket socket = new HttpServerSocket();
        socket.bind(PORT.getValue());
        socket.addListener(new WebsiteServer());
        File file = new File(PATH.getValue());
        if(!file.exists() || !file.isDirectory())
            file.mkdirs();
        socket.startCheckingAsync();
        System.out.println("Server has been started!");
    }

    @Override
    public void onBind(SocketBindEvent event){
    }

    @Override
    public void onAccept(SocketAcceptEvent event){
        if(!event.getRequest().getRequestType().equalsIgnoreCase("get")){
            event.getSocket().close();
            return;
        }

        String path = event.getRequest().getRequestPath();

        System.out.println("##################");
        System.out.println("Request for: " + path);

        if(isPage(path)){
            System.out.println("Sending page: " + path);
            event.getSocket().writeResponse(new HttpPageResponse(Collections.singletonList(Page.getPage(path))));
        }else{
            if(path.equalsIgnoreCase("/favicon.ico"))
                path = FAVICON_PATH.getValue();
            File file = new File(PATH.getValue() + File.separator + path);
            if(file.exists()){
                System.out.println("Sending file: " + file.getPath());
                event.getSocket().writeResponse(new HttpFileResponse(file));
            }else{
                System.out.println("Can't find file: " + file.getPath());
                event.getSocket().writeResponse(new HttpNotFoundResponse());
            }
        }
    }

    private static boolean isPage(String path){
        return path.endsWith(".html") || path.endsWith("/") || path.endsWith("\\") || path.lastIndexOf('.') - path.lastIndexOf('/') <= 0;
    }

    @Override
    public void onClose(SocketCloseEvent event){
    }
}
