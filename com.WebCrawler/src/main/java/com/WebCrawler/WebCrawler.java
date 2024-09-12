package com.WebCrawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class WebCrawler 
{
	   private final URLManager urlManager = new URLManager();
	    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

	    public void startCrawling(String startUrl, int depth) {
	        urlManager.addUrl(startUrl);
	        for (int i = 0; i < 10; i++) {
	            executorService.submit(new CrawlerWorker(urlManager, depth));
	        }
	        executorService.shutdown();
	    }

	    public static void main(String[] args) {
	        WebCrawler webCrawler = new WebCrawler();
	        webCrawler.startCrawling("http://example.com", 4);
	    }
}
