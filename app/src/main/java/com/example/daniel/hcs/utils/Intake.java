package com.example.daniel.hcs.utils;

/**
 * Created by Daniel on 2/14/2018.
 */

public class Intake {

    private Long id;
    private Long pillId;
    private String timeOfIntake;

    public Intake(String timeOfIntake) {
        this.timeOfIntake = timeOfIntake;
    }

    public Intake(Long id, Long serverId, Long pillId, String timeOfIntake) {
        this.id = id;
        this.pillId = pillId;
        this.timeOfIntake = timeOfIntake;
    }

    public Intake(Long serverId, Long pillId, String timeOfIntake) {
        this.pillId = pillId;
        this.timeOfIntake = timeOfIntake;
    }

    public Long getId() {
        return id;
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
