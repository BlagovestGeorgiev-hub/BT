package models;

import java.util.ArrayList;

public class DataHolder {
    private ArrayList<Tag> tags;

    public DataHolder(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public DataHolder() {
        this.tags = new ArrayList<Tag>();
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return "DataHolder{" +
                System.lineSeparator() +
                "tags=" + tags +
                System.lineSeparator() +
                '}';
    }
}
