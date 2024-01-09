package com.npcweb.dto;

import com.npcweb.domain.Project;

public class ProjectResponse {
	long pid;
	String pname, tname, process;
	String startDate, endDate;
	
	public ProjectResponse(Project p) {
		this.pid = p.getPid();
		this.pname = p.getPname();
		this.tname = p.getTname();
		this.process = p.getProcess();
		this.startDate = p.getFormattedStartDate();
		this.endDate = p.getFormattedEndDate();
	}
	
	public long getPid() {
		return pid;
	}
	public String getPname() {
		return pname;
	}
	public String getTname() {
		return tname;
	}
	public String getProcess() {
		return process;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
}
