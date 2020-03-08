package models;

import java.util.ArrayList;

public class Tag {
    private String tagName;

    private ArrayList<SubTag> subTags;

    public Tag(String tagName, ArrayList<SubTag> subTags) {
        this.tagName = tagName;
        this.subTags = subTags;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public ArrayList<SubTag> getSubTags() {
        return subTags;
    }

    public void setSubTags(ArrayList<SubTag> subTags) {
        this.subTags = subTags;
    }

    public void addSubTag(SubTag subTag){
        this.subTags.add(subTag);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagName='" + tagName + '\'' +
                System.lineSeparator() +
                ", subTags=" + subTags +
                System.lineSeparator() +
                '}';
    }
}
