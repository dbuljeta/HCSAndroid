package com.example.daniel.hcs.utils;

public class IntakeEvent {

    private Long id;
    private Long pillId;
    private Long intakeId;
    private Boolean taken;

    public IntakeEvent(Long id, Long pillId, Long intakeId, Boolean taken) {
        this.id = id;
        this.pillId = pillId;
        this.intakeId = intakeId;
        this.taken = taken;
    }

    public IntakeEvent(Long pillId, Long intakeId, Boolean taken) {
        this.pillId = pillId;
        this.intakeId = intakeId;
        this.taken = taken;
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

    public Long getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(Long intakeId) {
        this.intakeId = intakeId;
    }

    public Boolean getTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }
}
