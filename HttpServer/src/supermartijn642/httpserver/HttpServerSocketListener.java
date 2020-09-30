package supermartijn642.httpserver;

import supermartijn642.httpserver.event.SocketAcceptEvent;
import supermartijn642.httpserver.event.SocketBindEvent;
import supermartijn642.httpserver.event.SocketCloseEvent;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public interface HttpServerSocketListener {

    void onBind(SocketBindEvent event);
    void onAccept(SocketAcceptEvent event);
    void onClose(SocketCloseEvent event);
}
