package author;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Author {
	String name;
	Long id;
	String url;
	HashMap <Long, Integer> authorMap; 
	HashSet<String> nextUrls;

	public Author() {
		authorMap = new HashMap<Long, Integer>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void addAuthor(Long authorID){
		authorMap.put(authorID, authorMap.getOrDefault(authorID, 0) + 1);
	}
	public HashSet<String> getNextUrls() {
		return nextUrls;
	}
	public void setNextUrls(HashSet<String> nextUrls) {
		this.nextUrls = nextUrls;
	}
	public HashMap<Long, Integer> getAuthorMap() {
		return authorMap;
	}
	public void setAuthorMap(HashMap<Long, Integer> authorMap) {
		this.authorMap = authorMap;
	}
	public void addAuthor(Long authorID, Integer value) {
		authorMap.put(authorID, authorMap.getOrDefault(authorID, 0) + value);
	}
	
	@Override
	public String toString() {
		String ans = "";
		ans += "ID: " + this.id + "\n";
		ans += "Name: " + this.name + "\n";
		ans += "Url: " + this.url + "\n";
		ans += "Authors: " + this.authorMap + "\n";
		ans += "NextUrls: " + this.nextUrls + "\n";
		return ans;
	}
}
