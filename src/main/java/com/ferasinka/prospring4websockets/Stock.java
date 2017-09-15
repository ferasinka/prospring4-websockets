package com.ferasinka.prospring4websockets;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String DATE_FORMAT = "MMM dd yyyy HH:mm:ss";
	private String code;
	private double price;
	private LocalDateTime date = LocalDateTime.now();
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
	
	public Stock() {
	}
	
	public Stock(String code, double price) {
		this.code = code;
		this.price = price;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public String getDateFormatted() {
		return dateTimeFormatter.format(date);
	}
}
