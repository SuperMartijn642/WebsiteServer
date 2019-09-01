package supermartijn642.websiteserver;

import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created 8/31/2019 by SuperMartijn642
 */
public class Config {

    private static final String PATH = "./serverconfig.txt";

    private static ArrayList<Entry> entries = new ArrayList<>();

    public static <T extends Entry> T addEntry(T entry){
        entries.add(entry);
        return entry;
    }

    public static void initConfig(){
        readConfig();
        File file = new File(PATH);
        file.getParentFile().mkdirs();
        BufferedWriter writer;
        try{
            writer = new BufferedWriter(new FileWriter(file));
        }catch(Exception e){e.printStackTrace(); return;}
        ArrayList<String> content = new ArrayList<>();
        for(Entry entry : entries){
            for(Object s : entry.description)
                content.add("# " + s);
            content.add("# Default: " + entry.defaultValue);
            content.add(entry.name + ": " + (entry.value == null ? entry.defaultValue : entry.value));
            content.add("");
        }
        try{
            for(String line : content){
                writer.write(line);
                writer.newLine();
            }
        }catch(Exception e){e.printStackTrace();}
        try{
            writer.close();
        }catch(Exception ignore){}
        for(Entry entry : entries)
            if(entry.value == null)
                entry.read(entry.defaultValue);
    }

    private static void readConfig(){
        File file = new File(PATH);
        if(!file.exists())
            return;
        try{
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null){
                line = line.trim();
                if(!line.isEmpty() && !line.startsWith("#")){
                    int index = line.indexOf(':');
                    if(index > 0){
                        String name = line.substring(0, index);
                        for(Entry entry : entries){
                            if(entry.name.equalsIgnoreCase(name)){
                                entry.read(line.substring(index + 1).trim());
                                break;
                            }
                        }
                    }
                }
            }
            reader.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public abstract static class Entry<T> {
        protected String name;
        protected ArrayList<String> description = new ArrayList<>();
        protected String defaultValue;
        protected T value;

        public Entry(String name, String defaultValue, String... description){
            this.name = name;
            this.defaultValue = defaultValue;
            this.description.addAll(Arrays.asList(description));
        }

        protected abstract void read(String value);

        public T getValue(){
            return this.value;
        }
    }

    public static class StringEntry extends Entry<String> {

        public StringEntry(String name, String defaultValue, String... description){
            super(name, defaultValue, description);
        }

        @Override
        protected void read(String value){
            this.value = value;
        }
    }

    public static class IntegerEntry extends Entry<Integer> {

        public IntegerEntry(String name, String defaultValue, String... description){
            super(name, defaultValue, description);
        }

        @Override
        protected void read(String value){
            try{
                this.value = Integer.parseInt(value);
            }catch(Exception ignore){}
        }
    }
}
