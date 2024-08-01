package com.WebCrawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class URLExtractor {
	
	   public static Set<String> extract(String content) {
	        Set<String> urls = new HashSet<>();
	        Document doc = Jsoup.parse(content);
	        Elements links = doc.select("a[href]");
	        for (Element link : links) {
	            urls.add(link.attr("abs:href"));
	        }
	        return urls;
	    }

}
