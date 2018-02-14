package com.example.daniel.hcs.utils;

/**
 * Created by Daniel on 2/14/2018.
 */

public class Intake {

    private Long id;
    private Long serverId;
    private Long pillId;
    private String timeOfIntake;

    public Intake(Long id, Long serverId, Long pillId, String timeOfIntake) {
        this.id = id;
        this.serverId = serverId;
        this.pillId = pillId;
        this.timeOfIntake = timeOfIntake;
    }

    public Intake(Long serverId, Long pillId, String timeOfIntake) {
        this.serverId = serverId;
        this.pillId = pillId;
        this.timeOfIntake = timeOfIntake;
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

    public Long getPillId() {
        return pillId;
    }

    public void setPillId(Long pillId) {
        this.pillId = pillId;
    }

    public String getTimeOfIntake() {
        return timeOfIntake;
    }

    public void setTimeOfIntake(String timeOfIntake) {
        this.timeOfIntake = timeOfIntake;
    }
}
