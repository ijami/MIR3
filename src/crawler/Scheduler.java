package crawler;

import java.util.ArrayList;
import java.util.HashSet;

public class Scheduler {
	
	ArrayList<String> urlQueue;
	HashSet<String> urlRepository;
	
	public Scheduler() {
		urlQueue = new ArrayList<String>();
		urlRepository = new HashSet<String>();
	}
	
	public void addUrl(String url){
		if(!urlRepository.contains(url)){
			urlQueue.add(url);
			urlRepository.add(url);
		}	
	}

	public String nextUrl() {
		return urlQueue.remove(0);
	}
	
}
