package com.npcweb.domain.response;

import org.springframework.beans.factory.annotation.Autowired;

import com.npcweb.domain.Project;

public class ProjectResponse {
	long pid;
	String leader;
	String pname, tname, content, type, process;
	String startDate, endDate;
	
	public ProjectResponse(Project p) {
		this.pid = p.getPid();
		this.leader = String.valueOf(p.getLeader());
		this.pname = p.getPname();
		this.tname = p.getTname();
		this.content = p.getContent();
		this.type = p.getType();
		this.process = p.getProcess();
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
	public String getContent() {
		return content;
	}
	public String getType() {
		return type;
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
	
	public void setNickname(String leader) {
		this.leader = leader;
	}
}
