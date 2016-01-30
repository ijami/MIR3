package crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import author.Author;

public class AuthorParser {
	
	private static final int TIMEOUT = 30000;
	
	Document doc;
	String url, baseUrl;
	HashSet<String> nextUrls;
	

	public AuthorParser(String url) {
		this.url = url;
		String[] tmp = url.split("\\/");
		this.baseUrl = tmp[0] + "//" + tmp[2] + "/";
		nextUrls = new HashSet<String>();
	}
	
	public Author pars(){
		
		
		try {
			doc = Jsoup.connect(url).timeout(TIMEOUT).get();
		} catch (IOException e) {
			System.err.println("Connecting to url failed.");
			e.printStackTrace();
			return null;
		}
		
		Author author = new Author();		
		parsAuthors(author);
		
		if(isFirstPage()){
			author.setName(parsName());
			author.setUrl(url);
			author.setId(parsID());
			int numberOfPages = getNumberOfPages();
			for (int i = 0; i < numberOfPages - 1; i++) {
				String u = url + "/publications/" + (i + 2);
				AuthorParser parser = new AuthorParser(u);
				Author a = parser.pars();
				for (Entry<Long, Integer> id : a.getAuthorMap().entrySet()) {
					author.addAuthor(id.getKey(), id.getValue());
				}
			}
			author.setNextUrls(nextUrls);
		}
		
		return author;
	}
	
	private boolean isFirstPage(){
		return url.split("\\/").length == 5; 
	}

	private int getNumberOfPages() {
		Elements elements = doc.getElementsByClass("navi-page-link");
		int s = 0;
		if(elements.size() > 0)
			s = Integer.parseInt(elements.get(elements.size() - 1).text());
		return s;
		
	}

	private void parsAuthors(Author author) {
		Elements elements = doc.getElementsByClass("ga-publications-authors");
		for (Element element : elements) {
			String authorUrl = baseUrl + element.attr("href");
			author.addAuthor(parsID(authorUrl));
			if(nextUrls.size() < 10){
				nextUrls.add(authorUrl);
			}
		}
	}

	private String parsName() {
		String name = doc.getElementsByClass("profile-header-name").get(0).child(0).child(0).text();
		return name;
	}

	private Long parsID() {
		return Long.parseLong(url.split("\\/")[4].split("_")[0]);
	}
	
	private Long parsID(String url1) {
		return Long.parseLong(url1.split("\\/")[4].split("_")[0]);
	}
}
