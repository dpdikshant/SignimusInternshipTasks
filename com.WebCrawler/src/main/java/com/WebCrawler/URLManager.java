package com.WebCrawler;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class URLManager {
	
	private final Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());
    private final ConcurrentLinkedQueue<String> urlQueue = new ConcurrentLinkedQueue<>();

    public void addUrl(String url) {
        if (!visitedUrls.contains(url)) {
            urlQueue.add(url);
        }
    }

    public String getUrl() {
        return urlQueue.poll();
    }

    public void markVisited(String url) {
        visitedUrls.add(url);
    }

    public boolean isEmpty() {
        return urlQueue.isEmpty();
    }
    
}

