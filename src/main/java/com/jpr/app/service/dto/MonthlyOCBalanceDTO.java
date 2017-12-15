package com.jpr.app.service.dto;

import java.util.Date;

public class MonthlyOCBalanceDTO {

	private Date date;
	private double opBalance;
	private double clBalance;
	private double scrapReceived;
	private double scrapIssued;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getOpBalance() {
		return opBalance;
	}

	public void setOpBalance(double opBalance) {
		this.opBalance = opBalance;
	}

	public double getClBalance() {
		return clBalance;
	}

	public void setClBalance(double clBalance) {
		this.clBalance = clBalance;
	}

	public double getScrapReceived() {
		return scrapReceived;
	}

	public void setScrapReceived(double scrapReceived) {
		this.scrapReceived = scrapReceived;
	}

	public double getScrapIssued() {
		return scrapIssued;
	}

	public void setScrapIssued(double scrapIssued) {
		this.scrapIssued = scrapIssued;
	}

}
