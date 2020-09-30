package supermartijn642.httpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class HttpFileResponse extends HttpResponse {

    private File file;

    public HttpFileResponse(File file){
        super(200,"OK");
        this.file = file;
        this.setField("content-type",getContentType(this.file.getPath()));
    }

    @Override
    public void writeContent(OutputStream stream){
        try{
            FileInputStream inputStream = new FileInputStream(this.file);
            stream.write(("content-length: " + inputStream.available() + "\n\n").getBytes());
            inputStream.close();
            Files.copy(this.file.toPath(),stream);
        }catch(Exception e){e.printStackTrace();}
    }

    private static String getContentType(String path){
        String extension = "";
        int index = path.lastIndexOf('.');
        if(index >= 0 && index > path.lastIndexOf(File.separatorChar))
            extension = path.substring(index + 1);
        if(extension.isEmpty())
            return "application/octet-stream";
        if(extension.equalsIgnoreCase("bmp"))
            return "image/bmp";
        if(extension.equalsIgnoreCase("css"))
            return "text/css";
        if(extension.equalsIgnoreCase("csv"))
            return "text/csv";
        if(extension.equalsIgnoreCase("doc"))
            return "application/msword";
        if(extension.equalsIgnoreCase("docx"))
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        if(extension.equalsIgnoreCase(".gz"))
            return "application/gzip";
        if(extension.equalsIgnoreCase(".gif"))
            return "image/gif";
        if(extension.equalsIgnoreCase("html"))
            return "text/html";
        if(extension.equalsIgnoreCase("ico"))
            return "image/vnd.microsoft.icon";
        if(extension.equalsIgnoreCase("ics"))
            return "text/calendar";
        if(extension.equalsIgnoreCase("jar"))
            return "application/java-archive";
        if(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg"))
            return "image/jpeg";
        if(extension.equalsIgnoreCase("js"))
            return "text/javascript";
        if(extension.equalsIgnoreCase("json"))
            return "application/json";
        if(extension.equalsIgnoreCase("mp3"))
            return "audio/mpeg";
        if(extension.equalsIgnoreCase("mpeg"))
            return "video/mpeg";
        if(extension.equalsIgnoreCase("otf"))
            return "font/otf";
        if(extension.equalsIgnoreCase("png"))
            return "image/png";
        if(extension.equalsIgnoreCase("pdf"))
            return "application/pdf";
        if(extension.equalsIgnoreCase("php"))
            return "appliction/php";
        if(extension.equalsIgnoreCase("rar"))
            return "application/x-rar-compressed";
        if(extension.equalsIgnoreCase("ttf"))
            return "font/ttf";
        if(extension.equalsIgnoreCase("txt"))
            return "text/plain";
        if(extension.equalsIgnoreCase("wav"))
            return "audio/wav";
        if(extension.equalsIgnoreCase("zip"))
            return "application/zip";
        if(extension.equalsIgnoreCase("7z"))
            return "application/x-7z-compressed";
        return "application/octet-stream";
    }
}
