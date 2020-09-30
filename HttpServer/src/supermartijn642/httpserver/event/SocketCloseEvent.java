package supermartijn642.httpserver.event;

import supermartijn642.httpserver.HttpServerSocket;

import java.net.SocketAddress;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class SocketCloseEvent {

    private HttpServerSocket socket;
    private SocketAddress address;

    public SocketCloseEvent(HttpServerSocket socket, SocketAddress address){
        this.socket = socket;
        this.address = address;
    }

    public HttpServerSocket getSocket(){
        return this.socket;
    }

    /**
     * @return the local address the socket was bound to
     */
    public SocketAddress getAddress(){
        return this.address;
    }
}
