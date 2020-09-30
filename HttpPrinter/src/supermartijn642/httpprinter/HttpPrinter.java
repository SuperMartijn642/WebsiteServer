package supermartijn642.httpprinter;

import supermartijn642.httpserver.HttpServerSocket;
import supermartijn642.httpserver.HttpServerSocketListener;
import supermartijn642.httpserver.event.SocketAcceptEvent;
import supermartijn642.httpserver.event.SocketBindEvent;
import supermartijn642.httpserver.event.SocketCloseEvent;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class HttpPrinter implements HttpServerSocketListener {

    public static void main(String[] args){
        HttpServerSocket server = new HttpServerSocket();
        server.bind(80);
        server.addListener(new HttpPrinter());
        server.startChecking();
    }

    @Override
    public void onBind(SocketBindEvent event){}

    @Override
    public void onAccept(SocketAcceptEvent event){
        for(String s : event.getRequest().getLines())
            System.out.println(s);
    }

    @Override
    public void onClose(SocketCloseEvent event){

    }
}
