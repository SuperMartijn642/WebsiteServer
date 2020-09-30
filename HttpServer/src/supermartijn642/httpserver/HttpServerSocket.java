package supermartijn642.httpserver;

import supermartijn642.httpserver.event.SocketAcceptEvent;
import supermartijn642.httpserver.event.SocketBindEvent;
import supermartijn642.httpserver.event.SocketCloseEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class HttpServerSocket {

    private ServerSocket socket;
    private final ArrayList<HttpServerSocketListener> listeners = new ArrayList<>();
    private boolean isBound = false;
    private boolean isChecking = false;
    private boolean isCheckingAsync = false;
    private boolean shouldStopChecking = false;

    public HttpServerSocket(){
        try{
            this.socket = new ServerSocket();
            this.socket.setSoTimeout(1000);
        }catch(IOException e){e.printStackTrace();}
    }

    public SocketAddress getLocalAddress(){
        return this.socket.getLocalSocketAddress();
    }

    public boolean isBound(){
        return this.isBound;
    }

    /**
     * @return true if bound succesfully
     */
    public boolean bind(int port){
        if(this.isBound)
            return false;
        try{
            this.socket.bind(new InetSocketAddress("0.0.0.0", port));
        }catch(IOException e){e.printStackTrace(); return false;}
        this.isBound = true;
        SocketBindEvent event = new SocketBindEvent(this, port);
        synchronized(this.listeners){
            for(HttpServerSocketListener listener : this.listeners)
                listener.onBind(event);
        }
        return true;
    }

    /**
     * @return true if bound succesfully
     */
    public boolean bind(String hostname, int port){
        if(this.isBound)
            return false;
        try{
            this.socket.bind(new InetSocketAddress(hostname, port));
        }catch(IOException e){e.printStackTrace(); return false;}
        this.isBound = true;
        SocketBindEvent event = new SocketBindEvent(this, port);
        synchronized(this.listeners){
            for(HttpServerSocketListener listener : this.listeners)
                listener.onBind(event);
        }
        return true;
    }

    /**
     * @return true if checking either in sync or async
     */
    public boolean isChecking(){
        return this.isChecking || this.isCheckingAsync;
    }

    public boolean isCheckingAsync(){
        return this.isCheckingAsync;
    }

    /**
     * The socket must be connected first and must not be already checking.
     * @return false if socket is not connected or socket is already checking
     */
    public boolean startChecking(){
        if(!this.isBound || this.isChecking || this.isCheckingAsync)
            return false;
        this.isChecking = true;
        while(!this.shouldStopChecking)
            check();
        this.shouldStopChecking = false;
        this.isChecking = false;
        return true;
    }

    /**
     * The socket must be connected first and must not be already checking.
     * @return false if socket is not connected or socket is already checking
     */
    public boolean startCheckingAsync(){
        if(!this.isBound || this.isChecking || this.isCheckingAsync)
            return false;
        this.isCheckingAsync = true;
        new Thread(() -> {
            while(!shouldStopChecking)
                this.check();
            shouldStopChecking = false;
            isCheckingAsync = false;
        }).start();
        return true;
    }

    private void check(){
        try{
            final HttpSocket socket = new HttpSocket(this.socket.accept());
            new Thread(() -> {
                socket.readRequest();
                if(socket.getRequest() == null){
                    socket.close();
                    return;
                }
                SocketAcceptEvent event = new SocketAcceptEvent(this, socket, socket.getRequest());
                synchronized(this.listeners){
                    for(HttpServerSocketListener listener : this.listeners)
                        listener.onAccept(event);
                }
            }).start();
        }catch(SocketTimeoutException ignore){}catch(Exception e){e.printStackTrace();}
    }

    /**
     * stops all checking, both in sync and async
     * @return false if the socket was not being checked
     */
    public boolean stopChecking(){
        if(!this.isChecking && !this.isCheckingAsync)
            return false;
        this.shouldStopChecking = true;
        return true;
    }

    public boolean close(){
        if(!this.isBound)
            return false;
        SocketAddress address = this.getLocalAddress();
        try{
            if(this.isChecking || this.isCheckingAsync)
                this.shouldStopChecking = true;
            while(this.isCheckingAsync || this.isChecking)
                Thread.sleep(10);
            this.socket.close();
            this.isBound = false;
        }catch(Exception e){e.printStackTrace(); return false;}
        SocketCloseEvent event = new SocketCloseEvent(this, address);
        synchronized(this.listeners){
            for(HttpServerSocketListener listener : this.listeners)
                listener.onClose(event);
        }
        return true;
    }

    /**
     * @return true if the listener was not yet registered
     */
    public boolean addListener(HttpServerSocketListener listener){
        synchronized(this.listeners){
            if(this.listeners.contains(listener))
                return false;
            this.listeners.add(listener);
        }
        return true;
    }

    /**
     * @return true if the listener was registered
     */
    public boolean removeListener(HttpServerSocketListener listener){
        synchronized(this.listeners){
            return this.listeners.remove(listener);
        }
    }

}
