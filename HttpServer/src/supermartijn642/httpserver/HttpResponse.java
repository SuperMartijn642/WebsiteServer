package supermartijn642.httpserver;

import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public abstract class HttpResponse {

    protected static final String HTTP = "HTTP/1.1";

    protected final int code;
    protected final String message;
    private final HashMap<String,String> fields = new HashMap<>();

    public HttpResponse(int code, String message){
        this.code = code;
        this.message = message;
    }

    public HttpResponse setField(String field, String value){
        this.fields.put(field, value);
        return this;
    }

    public void writeResponse(OutputStream stream){
        try{
            stream.write((HTTP + " " + this.code + " " + this.message + "\n").getBytes());
            for(Map.Entry<String,String> entry : this.fields.entrySet())
                stream.write((entry.getKey() + ": " + entry.getValue() + "\n").getBytes());
            this.writeContent(stream);
        }catch(Exception e){e.printStackTrace();}
    }

    @NotNull
    public abstract void writeContent(OutputStream stream);
}
