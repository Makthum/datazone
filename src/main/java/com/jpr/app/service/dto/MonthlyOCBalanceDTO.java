package com.jpr.app.service.dto;

import java.util.Date;

public class MonthlyOCBalanceDTO {
	
	private Date date;
	private int opBalance;
	private int clBalance;
	private int scrapReceived;
	private int scrapIssued;
	public int getOpBalance() {
		return opBalance;
	}
	public void setOpBalance(int opBalance) {
		this.opBalance = opBalance;
	}
	public int getClBalance() {
		return clBalance;
	}
	public void setClBalance(int clBalance) {
		this.clBalance = clBalance;
	}
	public int getScrapReceived() {
		return scrapReceived;
	}
	public void setScrapReceived(int scrapReceived) {
		this.scrapReceived = scrapReceived;
	}
	public int getScrapIssued() {
		return scrapIssued;
	}
	public void setScrapIssued(int scrapIssued) {
		this.scrapIssued = scrapIssued;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
