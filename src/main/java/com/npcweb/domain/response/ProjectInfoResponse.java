package com.npcweb.domain.response;

import com.npcweb.domain.Project;

public class ProjectInfoResponse {
	long pid;
	String leader, type;
	String pname, tname, process, content;
	String startDate, endDate;
	
	public ProjectInfoResponse(Project p) {
		this.pid = p.getPid();
		this.leader = String.valueOf(p.getLeader());
		this.pname = p.getPname();
		this.tname = p.getTname();
		this.process = p.getProcess();
		this.content = p.getContent();
		this.type = p.getType();
		this.startDate = p.getFormattedStartDate();
		this.endDate = p.getFormattedEndDate();
	}
	
	public long getPid() {
		return pid;
	}
	public String getLeader() {
		return leader;
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
	public String getType() {
		return type;
	}
	public String getContent() {
		return content;
	}
	public void setNickname(String leader) {
		this.leader = leader;
	}
}
