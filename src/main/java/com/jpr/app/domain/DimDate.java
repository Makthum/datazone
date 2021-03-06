package com.jpr.app.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "dimdate")
public class DimDate {

	@Id
	private int date_id;

	@Temporal(TemporalType.DATE)
	private Date date;

	private Long timestamp;
	private String weekend;
	private String day_of_week;
	private String month;
	private int month_day;
	private int year;
	private int week_starting_monday;

	public int getDate_id() {
		return date_id;
	}

	public void setDate_id(int date_id) {
		this.date_id = date_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getWeekend() {
		return weekend;
	}

	public void setWeekend(String weekend) {
		this.weekend = weekend;
	}

	public String getDay_of_week() {
		return day_of_week;
	}

	public void setDay_of_week(String day_of_week) {
		this.day_of_week = day_of_week;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getMonth_day() {
		return month_day;
	}

	public void setMonth_day(int month_day) {
		this.month_day = month_day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getWeek_starting_monday() {
		return week_starting_monday;
	}

	public void setWeek_starting_monday(int week_starting_monday) {
		this.week_starting_monday = week_starting_monday;
	}

}
