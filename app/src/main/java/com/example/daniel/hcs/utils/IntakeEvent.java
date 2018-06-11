package com.example.daniel.hcs.utils;

public class IntakeEvent {

    private Long id;
    private Long serverId;
    private Long intakeId;
    private Long pillId;
    private Boolean taken;

    public IntakeEvent(Long id, Long serverId, Long intakeId, Long pillId, Boolean taken) {
        this.id = id;
        this.serverId = serverId;
        this.intakeId = intakeId;
        this.pillId = pillId;
        this.taken = taken;
    }

    public IntakeEvent(Long serverId, Long intakeId, Long pillId, Boolean taken) {
        this.serverId = serverId;
        this.intakeId = intakeId;
        this.pillId = pillId;
        this.taken = taken;
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

    public Long getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(Long intakeId) {
        this.intakeId = intakeId;
    }

    public Long getPillId() {
        return pillId;
    }

    public void setPillId(Long pillId) {
        this.pillId = pillId;
    }

    public Boolean getTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }
}
