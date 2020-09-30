package supermartijn642.httpserver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class HttpRequest {

    private final String type;
    private final String path;
    private final String http;
    private final ArrayList<String> lines = new ArrayList<>();
    private final HashMap<String,String> fields = new HashMap<>();

    HttpRequest(List<String> lines){
        StringTokenizer tokenizer = new StringTokenizer(lines.get(0));
        this.type = tokenizer.nextToken();
        this.path = tokenizer.nextToken();
        this.http = tokenizer.nextToken();
        for(String line : lines){
            this.lines.add(line);
            int index = line.indexOf(": ");
            if(index > 0)
                this.fields.put(line.substring(0, index), line.substring(index + 2));
        }
    }

    @NotNull
    public String getRequestType(){
        return this.type;
    }

    @NotNull
    public String getRequestPath(){
        return this.path;
    }

    @NotNull
    public String getRequestHttpVersion(){
        return this.http;
    }

    @NotNull
    public List<String> getLines(){
        return this.lines;
    }

    @Nullable
    public String getField(String field){
        return this.fields.get(field);
    }

}
