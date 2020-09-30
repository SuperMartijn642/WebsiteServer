package supermartijn642.httpserver;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class HttpSocket {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private HttpRequest request;

    HttpSocket(Socket socket){
        this.socket = socket;
        try{
            this.socket.setSoTimeout(100);
        }catch(Exception e){e.printStackTrace();}
        if(this.socket.isConnected()){
            try{
                this.inputStream = socket.getInputStream();
                this.outputStream = socket.getOutputStream();
            }catch(Exception e){e.printStackTrace();}
        }
    }

    public SocketAddress getLocalAddress(){
        return this.socket.getLocalSocketAddress();
    }

    public SocketAddress getRemoteAddress(){
        return this.socket.getRemoteSocketAddress();
    }

    HttpRequest readRequest(){
        ArrayList<String> request = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.inputStream));
        try{
            String line;
            while((line = reader.readLine()) != null)
                request.add(line);
        }catch(SocketTimeoutException ignore){
        }catch(IOException e){
            e.printStackTrace();
        }
        if(request.size() == 0)
            return null;
        return this.request = new HttpRequest(request);
    }

    public boolean writeResponse(HttpResponse response){
        synchronized(this.outputStream){
            response.writeResponse(this.outputStream);
        }
        this.close();
        return true;
    }

    public HttpRequest getRequest(){
        return this.request;
    }

    public void close(){
        try{
            this.inputStream.close();
        }catch(Exception e){e.printStackTrace();}
        try{
            this.outputStream.close();
        }catch(Exception e){e.printStackTrace();}
    }

}
