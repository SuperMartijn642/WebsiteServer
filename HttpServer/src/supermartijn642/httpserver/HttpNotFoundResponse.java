package supermartijn642.httpserver;

import java.io.OutputStream;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class HttpNotFoundResponse extends HttpResponse {

    public HttpNotFoundResponse(){
        super(404,"NOT FOUND");
    }

    @Override
    public void writeContent(OutputStream stream){

    }
}
