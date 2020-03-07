package data;

import models.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TextParser {

    private HashMap<String, HashMap<String, ArrayList<Product>>> textObjs;
    private String lastTag;
    private String lastSubTag;

    public TextParser(){
        textObjs = new HashMap<>();
    }

    public void ReadFile(){
        try (Stream<String> stream = Files.lines(Paths.get(".\\src\\main\\resources\\TextData.txt"))) {
            stream.forEach(l -> {
                if (l.trim().startsWith("<")){
                    Pattern pattern = Pattern.compile("^<(\\w+\\s*?\\S*?)$");
                    Matcher matcher = pattern.matcher(l);
                    if (matcher.matches()){
                        String group  = matcher.group(1);
                        lastTag = group;
                        textObjs.put(group, new HashMap<>());
                    }
                }
                if (l.trim().startsWith("_")){
                    Pattern pattern = Pattern.compile("^_(\\w+\\s*?\\S*?)$");
                    Matcher matcher = pattern.matcher(l.trim());
                    if (matcher.matches()){
                        String group  = matcher.group(1);
                        lastSubTag = group;
                        textObjs.get(lastTag).put(lastSubTag, new ArrayList<>());
                    }
                }
                if (l.trim().startsWith("*")){
                    Pattern pattern = Pattern.compile("^\\*(\\w+[\\s*\\w*?]*),(\\d+.\\d+),(\\w+\\/\\w+.\\w+)$");
                    Matcher matcher = pattern.matcher(l.trim());
                    if (matcher.matches()){
                        Product  pr = new Product(matcher.group(1), Double.parseDouble(matcher.group(2)),matcher.group(3));
                        System.out.println(pr);
                        textObjs.get(lastTag).get(lastSubTag).add(pr);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
