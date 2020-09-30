package supermartijn642.websiteserver;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created 01/09/2019 by SuperMartijn642
 */
public class FileReader {

    public static List<String> getFileContent(File file){
        if(!file.exists()){
            System.out.println("[ERROR] Can't find file: " + file.getPath());
            return Collections.emptyList();
        }
        List<String> content = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
            String line;
            while((line = reader.readLine()) != null)
                content.add(line.trim());
        }catch(Exception e){e.printStackTrace();}
        return content;
    }

    public static List<String> formatVars(List<String> lines, Map<String,String> vars){
        for(int a = 0; a < lines.size(); a++)
            for(Map.Entry<String,String> entry : vars.entrySet())
                lines.set(a,lines.get(a).replace("%%" + entry.getKey() + "%%", entry.getValue()));
        return lines;
    }

    public static String combineLines(List<String> lines){
        StringBuilder result = new StringBuilder();
        lines.forEach(result::append);
        return result.toString();
    }
}
