package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes;

import java.io.Serializable;

/**
 * Created by plamend on 2/17/14.
 */
public class feedObject  implements Serializable{
    private int id;
    private String name;
    private String tag;
    private boolean pub;
    private String getUrl;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPub(boolean pub) {
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
    public boolean setPub(){
        return pub;
    }

    public void setGetUrl(String getUrl) {
        this.getUrl = getUrl;
    }

    public String getGetUrl() {
        return getUrl;
    }
}
