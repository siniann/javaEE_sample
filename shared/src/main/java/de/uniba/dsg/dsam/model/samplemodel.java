package de.uniba.dsg.dsam.model;

import java.io.Serializable;

public class samplemodel implements Serializable {
    private int id;
    private String name;

    public samplemodel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
