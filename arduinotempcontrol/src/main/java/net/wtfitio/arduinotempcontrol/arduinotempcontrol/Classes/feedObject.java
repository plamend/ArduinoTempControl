package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes;

import java.io.Serializable;

/**
 * Created by plamend on 2/17/14.
 */
public class feedObject  implements Serializable{
    private int id;
    private String name;
    private String tag;
    private String pub;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }
    public String setPub(){
        return pub;
    }

}