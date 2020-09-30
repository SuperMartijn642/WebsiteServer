package supermartijn642.httpserver.event;

import supermartijn642.httpserver.HttpServerSocket;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class SocketBindEvent {

    private HttpServerSocket socket;
    private int port;

    public SocketBindEvent(HttpServerSocket socket, int port){
        this.socket = socket;
        this.port = port;
    }

    public HttpServerSocket getSocket(){
        return this.socket;
    }

    public int getPort(){
        return this.port;
    }
}
