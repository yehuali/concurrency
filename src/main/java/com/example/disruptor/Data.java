package com.example.disruptor;

import java.io.Serializable;

public class Data implements Serializable {

    private static final long serialVersionUID = 4208924442033336873L;
    private Long id;

    private String name;

    public Data(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
