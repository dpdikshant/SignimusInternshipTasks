package com.WebCrawler;

import java.util.Set;

public class CrawlerWorker implements Runnable {
	
	private final URLManager urlManager;
    private final int depth;

    public CrawlerWorker(URLManager urlManager, int depth) {
        this.urlManager = urlManager;
        this.depth = depth;
    }

    @Override
    public void run() {
        while (!urlManager.isEmpty() && depth > 0) {
            String url = urlManager.getUrl();
            if (url != null) {
                try {
                    String content = URLFetcher.fetch(url);
                    Set<String> urls = URLExtractor.extract(content);
                    for (String extractedUrl : urls) {
                        urlManager.addUrl(extractedUrl);
                    }
                    urlManager.markVisited(url);
                } catch (Exception e) {
                    System.err.println("Error fetching URL: " + url);
                }
            }
        }
    }
	
	
	
	

}
