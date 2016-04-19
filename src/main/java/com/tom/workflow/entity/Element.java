package com.tom.workflow.entity;

/**
 * Created by tom on 16/4/18.
 */
public class Element {
    private String id;
    private String text;
    private String textEn;
    private String tagName;
    private String valueId;

    public Element(String id, String text, String textEn, String tagName, String valueId) {
        this.id = id;
        this.text = text;
        this.textEn = textEn;
        this.tagName = tagName;
        this.valueId = valueId;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", textEn='" + textEn + '\'' +
                ", tagName='" + tagName + '\'' +
                ", valueId='" + valueId + '\'' +
                '}';
    }
}
