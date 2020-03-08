package data;

import interfaces.TextParser;
import models.DataHolder;
import models.Product;
import models.SubTag;
import models.Tag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TextParserImpl implements TextParser {

    public static final String SRC_MAIN_RESOURCES_TEXT_DATA_TXT = ".\\src\\main\\resources\\TextData.txt";
    private DataHolder dataHolder;
    private String lastTag;
    private String lastSubTag;

    public TextParserImpl(){
        dataHolder = new DataHolder();
    }

    public void readData(){
        this.dataHolder = new DataHolder();
        try (Stream<String> stream = Files.lines(Paths.get(SRC_MAIN_RESOURCES_TEXT_DATA_TXT))) {
            stream.forEach(l -> {
                if (l.trim().startsWith("<")){
                    Pattern pattern = Pattern.compile("^<(\\w+\\s*?\\S*?)>$");
                    Matcher matcher = pattern.matcher(l);
                    if (matcher.matches()){
                        String group  = matcher.group(1);
                        lastTag = group;
                        dataHolder.addTag(new Tag(group, new ArrayList<>()));
                    }
                }
                if (l.trim().startsWith("_")){
                    Pattern pattern = Pattern.compile("^_(\\w+\\s*?\\S*?)$");
                    Matcher matcher = pattern.matcher(l.trim());
                    if (matcher.matches()){
                        String group  = matcher.group(1);
                        lastSubTag = group;
                        dataHolder.getTags().stream().forEach( t -> {
                            if (t.getTagName().equals(lastTag)){
                                t.getSubTags().add(new SubTag(group, new ArrayList<>()));
                            }
                        });
                    }
                }
                if (l.trim().startsWith("*")){
                    Pattern pattern = Pattern.compile("^\\*(\\w+[\\s*\\w*?]*),(\\d+.\\d+),(\\w+\\/\\w+.\\w+)$");
                    Matcher matcher = pattern.matcher(l.trim());
                    if (matcher.matches()){
                        Product  pr = new Product(matcher.group(1), Double.parseDouble(matcher.group(2)),matcher.group(3));
                        dataHolder.getTags().stream().forEach( t -> {
                            if (t.getTagName().equals(lastTag)){
                                t.getSubTags().forEach(st -> {
                                    if (st.getTagName().equals(lastSubTag)){
                                        st.getProducts().add(pr);
                                    }
                                });
                            }
                        });
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataHolder getData() {
        return dataHolder;
    }
}
