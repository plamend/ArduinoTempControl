package net.wtfitio.arduinotempcontrol.arduinotempcontrol.Classes;

import java.io.Serializable;

/**
 * Created by plamend on 2/17/14.
 */
public class inputObject implements Serializable {
    private String name;
    private String description;
    private String sendUrl;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public String getSendUrl() {
        return sendUrl;
    }
}
