package com.tom.workflow.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 16/4/18.
 */
public class Panel {
    private String id;
    private String title;
    private String titleEN;
    private String type = "value";

    public Panel(String id, String title, String titleEN) {
        this.id = id;
        this.title = title;
        this.titleEN = titleEN;
    }

    List<Element> elements = new ArrayList<Element>();

    public void addElement(String id, String tagName, String text, String textEN, String valueId){
        elements.add(new Element(id,text, textEN,tagName,valueId));
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public String getTitleEN() {
        return titleEN;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Panel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", titleEN='" + titleEN + '\'' +
                ", type='" + type + '\'' +
                ", elements=" + elements +
                '}';
    }
}
