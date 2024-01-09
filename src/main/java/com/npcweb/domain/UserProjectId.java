package com.npcweb.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserProjectId implements Serializable {

    @Column(name = "userNo")
    private long userno;

    @Column(name = "pid")
    private long pid;
    
    public UserProjectId() {}

    public UserProjectId(long userno, long pid) {
        this.userno = userno;
        this.pid = pid;
    }

	public long getUserno() {
		return userno;
	}

	public long getPid() {
		return pid;
	}

	public void setUserno(long userno) {
		this.userno = userno;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}
    
}