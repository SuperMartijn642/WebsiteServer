package supermartijn642.websiteserver;

import supermartijn642.httpserver.HttpSocket;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class PageFormatter {

    public static void sendPage(File file, HttpSocket socket){
        List<String> content = format(FileReader.getFileContent(file));
        socket.writeResponse(new HttpPageResponse(content));
    }

    private static List<String> format(List<String> content){
        for(int a = 0; a < content.size(); a++){
            content.set(a, formatLine(content.get(a),new HashMap<>()));
        }
        return content;
    }

    private static String formatLine(String line, HashMap<String,String> vars){
        int startIndex = line.indexOf("%%");
        if(startIndex < 0)
            return line;
        startIndex += 2;
        int endIndex = line.substring(startIndex).indexOf("%%") + startIndex;
        if(endIndex < startIndex)
            return line;
        int indexAnd = line.substring(startIndex, endIndex).indexOf('&') + startIndex;
        if(indexAnd < startIndex)
            return line;
        String command = line.substring(startIndex,indexAnd);
        StringTokenizer tokenizer = new StringTokenizer(line.substring(indexAnd, endIndex), "&");
        ArrayList<String> params = new ArrayList<>();
        while(tokenizer.hasMoreTokens())
            params.add(tokenizer.nextToken().trim());
        return formatLine(line.substring(0, startIndex - 2) + format(command, params,vars) + line.substring(endIndex + 2), vars);
    }

    private static String format(String command, List<String> params, HashMap<String,String> vars){
        if(command.equalsIgnoreCase("paste") && params.size() > 0){
            List<String> fileContent = FileReader.getFileContent(new File(WebsiteServer.PATH.getValue() + File.separator + params.get(0)));
            HashMap<String,String> newVars = new HashMap<>(vars);
            for(int a = 1; a < params.size(); a++){
                int index = params.get(a).indexOf("::");
                if(index > 0)
                    newVars.put(params.get(a).substring(0,index),params.get(a).substring(index + 2));
            }
            StringBuilder newLine = new StringBuilder();
            fileContent.forEach(s -> newLine.append(formatLine(s, newVars)));
            return newLine.toString();
        }else if(command.equalsIgnoreCase("var") && params.size() == 1){
            return vars.get(params.get(0));
        }
        return "UNKNOWN COMMAND";
    }

}
