package supermartijn642.websiteserver;

import supermartijn642.httpserver.HttpFileResponse;
import supermartijn642.httpserver.HttpNotFoundResponse;
import supermartijn642.httpserver.HttpServerSocket;
import supermartijn642.httpserver.HttpServerSocketListener;
import supermartijn642.httpserver.event.SocketAcceptEvent;
import supermartijn642.httpserver.event.SocketBindEvent;
import supermartijn642.httpserver.event.SocketCloseEvent;

import java.io.File;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class WebsiteServer implements HttpServerSocketListener {

    private static final Config.IntegerEntry PORT = Config.addEntry(new Config.IntegerEntry("port","8000","The port the server socket will be bound to."));
    public static final Config.StringEntry PATH = Config.addEntry(new Config.StringEntry("port","./files","The folder for files that can be requested."));
    private static final Config.StringEntry DEFAULT_PAGE = Config.addEntry(new Config.StringEntry("port","home.html","The default page for the website."));
    private static final Config.StringEntry NOT_FOUND_PAGE = Config.addEntry(new Config.StringEntry("port","not_found.html","The page that will be send when a file can't be found."));
    private static final Config.StringEntry FAVICON_PATH = Config.addEntry(new Config.StringEntry("favicon","favicon.ico","The file that will be sent when the favicon is requested."));

    public static void main(String[] args){
        init();
    }

    public static void init(){
        Config.initConfig();
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
    public void onBind(SocketBindEvent event){}

    @Override
    public void onAccept(SocketAcceptEvent event){
        System.out.println("request!!!!!!!!!!!!!!!!!!!");
        if(!event.getRequest().getRequestType().equalsIgnoreCase("get")){
            event.getSocket().close();
            return;
        }
        String path = event.getRequest().getRequestPath();
        if(path.equals("/"))
            path = DEFAULT_PAGE.getValue();
        else if(path.equalsIgnoreCase("/favicon.ico"))
            path = FAVICON_PATH.getValue();
        else if(path.lastIndexOf('.') - path.lastIndexOf('/') <= 0)
            path = path + ".html";
        File file = new File(PATH.getValue() + File.separator + path);
        if(!file.exists() && path.endsWith(".html"))
            file = new File(PATH.getValue() + File.separator + NOT_FOUND_PAGE.getValue());
        if(file.exists()){
            if(file.getPath().endsWith(".html")){
                System.out.println("Sending page: " + file.getPath());
                PageFormatter.sendPage(file,event.getSocket());
            }
            else{
                System.out.println("Sending file: " + file.getPath());
                event.getSocket().writeResponse(new HttpFileResponse(file));
            }
        }
        else{
            System.out.println("Can't find file: " + file.getPath());
            event.getSocket().writeResponse(new HttpNotFoundResponse());
        }
    }

    @Override
    public void onClose(SocketCloseEvent event){}
}
