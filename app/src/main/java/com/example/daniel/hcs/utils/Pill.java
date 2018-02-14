package com.example.daniel.hcs.utils;

/**
 * Created by Daniel on 2/14/2018.
 */

public class Pill {

    private Long id;
    private Long serverId;
    private String name;
    private String description;
    private Long numberOfIntakes;

    public Pill(Long id, Long serverId, String name, String description, Long numberOfIntakes) {
        this.id = id;
        this.serverId = serverId;
        this.name = name;
        this.description = description;
        this.numberOfIntakes = numberOfIntakes;
    }

    public Pill(Long serverId, String name, String description, Long numberOfIntakes) {
        this.serverId = serverId;
        this.name = name;
        this.description = description;
        this.numberOfIntakes = numberOfIntakes;
    }

    public Long getId() {
        return id;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getNumberOfIntakes() {
        return numberOfIntakes;
    }

    public void setNumberOfIntakes(Long numberOfIntakes) {
        this.numberOfIntakes = numberOfIntakes;
    }
}
