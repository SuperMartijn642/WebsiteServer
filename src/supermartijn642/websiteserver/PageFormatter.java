package supermartijn642.websiteserver;

import supermartijn642.httpserver.HttpSocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class PageFormatter {

    public static void sendPage(File file, HttpSocket socket){
        List<String> content = format(getFileContent(file));
        socket.writeResponse(new HttpPageResponse(content));
    }

    private static List<String> format(List<String> content){
        for(int a = 0; a < content.size(); a++){
            String line = content.get(a);
            int index = line.indexOf("%%paste ");
            int lastIndex = line.lastIndexOf("%%");
            if(index >= 0 && lastIndex >= index + "%%paste ".length()){
                String path = line.substring(index + "%%paste ".length(), lastIndex).trim();
                List<String> fileContent = getFileContent(new File(WebsiteServer.PATH.getValue() + File.separator + path));
                StringBuilder newLine = new StringBuilder(line.substring(0,index));
                fileContent.forEach(newLine::append);
                newLine.append(line.substring(lastIndex + "%%".length()));
                content.set(a,newLine.toString());
            }
        }
        return content;
    }

    private static List<String> getFileContent(File file){
        if(!file.exists()){
            System.out.println("[ERROR] Can't find subpage: " + file.getPath());
            return Collections.emptyList();
        }
        List<String> content = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null)
                content.add(line.trim());
        }catch(Exception e){e.printStackTrace();}
        return content;
    }

}
