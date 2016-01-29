package article;

import java.util.ArrayList;

public class Article {
	
	
	long id;
	String name;
	String url;
	String abstraction;
	ArrayList<String> authors, citedInUrls, refrenceUrls;
	ArrayList<Long> citedInIDs, refrenceIDs;
	double pageRank;
	
	public Article() {
		authors = new ArrayList<String>();
		refrenceIDs = new ArrayList<Long>();
		citedInIDs = new ArrayList<Long>();
		citedInUrls = new ArrayList<String>();
		refrenceUrls = new ArrayList<String>();
	}
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbstraction() {
		return abstraction;
	}

	public void setAbstraction(String abstraction) {
		this.abstraction = abstraction;
	}


	public void setAuthors(String[] parsAuthors) {
		for (String string : parsAuthors) {
			authors.add(string);
		}
	}
	
	public void setCitedInIDs(Long[] ids){
		for (Long integer : ids) {
			citedInIDs.add(integer);
		}
	}
	
	public void setRefrenceIDs(Long[] ids){
		for (Long long1 : ids) {
			refrenceIDs.add(long1);
		}
		
	}
	
	public void setRefrenceUrls(String[] urls){
		for (String string : urls) {
			refrenceUrls.add(string);
		}
	}
	
	public void setCitedInUrls(String[] urls){
		for (String string : urls) {
			citedInUrls.add(string);
		}
	}
	
	
	
	public ArrayList<String> getCitedInUrls() {
		return citedInUrls;
	}



	public ArrayList<String> getRefrenceUrls() {
		return refrenceUrls;
	}
	

	public ArrayList<Long> getCitedInIDs() {
		return citedInIDs;
	}


	public ArrayList<Long> getRefrenceIDs() {
		return refrenceIDs;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

	public double getPageRank() {
		return pageRank;
	}



	public void setPageRank(double pageRank) {
		this.pageRank = pageRank;
	}



	@Override
	public String toString() {
		String ans = "";
		ans += "ID: " + this.id + "\n";
		ans += "Url: " + this.url + "\n";
		ans += "Name: " + this.name + "\n"; 
		ans += "Abstract: " + this.abstraction + "\n";
		ans += "Authors: " + this.authors + "\n";
		ans += "CitedIn Articles ID: " + this.citedInIDs + "\n";
		ans += "CitedIn Articles Url: " + this.citedInUrls + "\n";
		ans += "Refrences ID: " + this.refrenceIDs + "\n";
		ans += "Refrences Url: " + this.refrenceUrls + "\n";
		ans += "Pagerank: " + this.pageRank;
		return ans;
	}
	
	

}
