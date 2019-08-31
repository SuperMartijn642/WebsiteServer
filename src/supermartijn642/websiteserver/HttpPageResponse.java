package supermartijn642.websiteserver;

import supermartijn642.httpserver.HttpResponse;

import java.io.OutputStream;
import java.util.List;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class HttpPageResponse extends HttpResponse {

    private List<String> content;

    public HttpPageResponse(List<String> content){
        super(200, "OK");
        this.content = content;
        this.setField("content-type", "text/html");
        int size = 0;
        for(String s : content)
            size += s.getBytes().length;
        this.setField("content-length", "" + (size + 8));
    }

    @Override
    public void writeContent(OutputStream stream){
        try{
            stream.write("\n\n".getBytes());
            for(String s : content)
                stream.write((s + "\n").getBytes());
        }catch(Exception e){e.printStackTrace();}
    }
}
