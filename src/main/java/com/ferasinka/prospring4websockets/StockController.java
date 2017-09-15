package com.ferasinka.prospring4websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class StockController {
	private TaskScheduler taskScheduler;
	private SimpMessagingTemplate simpMessagingTemplate;
	private List<Stock> stocks = new ArrayList<>();
	private Random random = new Random(System.currentTimeMillis());
	
	public StockController() {
		stocks.add(new Stock("VMW", 1.00));
		stocks.add(new Stock("EMC", 1.00));
		stocks.add(new Stock("GOOG", 1.00));
		stocks.add(new Stock("IBM", 1.00));
	}
	
	@MessageMapping("/addStock")
	public void addStock(Stock stock) throws Exception {
		stocks.add(stock);
		broadcastUpdatedPrices();
	}
	
	@Autowired
	public void setTaskScheduler(TaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}
	
	@Autowired
	public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}
	
	private void broadcastUpdatedPrices() {
		for (Stock stock : stocks) {
			stock.setPrice(stock.getPrice() + (getUpdatedStockPrice() * stock.getPrice()));
			stock.setDate(LocalDateTime.now());
		}
		
		simpMessagingTemplate.convertAndSend("/topic/price", stocks);
	}
	
	private double getUpdatedStockPrice() {
		double priceChange = random.nextDouble() * 5.0;
		
		if (random.nextInt(2) == 1) {
			priceChange = -priceChange;
		}
		
		return priceChange / 100.0;
	}
	
	@PostConstruct
	private void broadcastTimePeriodically() {
		taskScheduler.scheduleAtFixedRate(this::broadcastUpdatedPrices, 1000);
	}
}
