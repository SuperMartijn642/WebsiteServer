package supermartijn642.httpserver.event;

import supermartijn642.httpserver.HttpRequest;
import supermartijn642.httpserver.HttpServerSocket;
import supermartijn642.httpserver.HttpSocket;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class SocketAcceptEvent {

    private HttpServerSocket server;
    private HttpSocket socket;
    private HttpRequest request;

    public SocketAcceptEvent(HttpServerSocket server, HttpSocket socket, HttpRequest request){
        this.server = server;
        this.socket = socket;
        this.request = request;
    }

    public HttpServerSocket getServerSocket(){
        return this.server;
    }

    public HttpSocket getSocket(){
        return this.socket;
    }

    public HttpRequest getRequest(){
        return this.request;
    }
}
