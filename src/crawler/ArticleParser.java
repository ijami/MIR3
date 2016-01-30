package crawler;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import article.Article;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ArticleParser {

	private static final int TIMEOUT = 30000;

	Document doc;
	String url, baseUrl;
	JsonArray citedInJsonArray, refrenceJsonArray;

	public ArticleParser(String url) {
		this.url = url;
		String[] tmp = url.split("\\/");
		this.baseUrl = tmp[0] + "//" + tmp[2] + "/";
	}

	public void fetchCitedInJson() {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet firstGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(firstGet);
			Header[] headers = response.getHeaders("Set-Cookies");
			HttpGet httpGet = new HttpGet(
					"https://www.researchgate.net/"
							+ "publicliterature.PublicationIncomingCitationsList.html?dbw=true&"
							+ "publicationUid="
							+ this.parsID()
							+ "&showCitationsSorter=true&"
							+ "showAbstract=true&showType=true&showPublicationPreview=true&"
							+ "swapJournalAndAuthorPositions=false&limit=100000");
			httpGet.setHeaders(headers);
			httpGet.setHeader("authority", "www.researchgate.net");
			httpGet.setHeader("accept", "application/json");
			httpGet.setHeader("referer", url);
			httpGet.setHeader("x-requested-with", "XMLHttpRequest");
			httpGet.setHeader(
					"user-agent",
					"Mozilla/5.0 (Macintosh;Intel Mac OS X 10_11_2) "
							+ "AppleWebKit/537.36(KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			response = httpClient.execute(httpGet);
			Scanner inp = new Scanner(response.getEntity().getContent());
			String content = inp.nextLine();
			citedInJsonArray = new JsonParser().parse(content)
					.getAsJsonObject().getAsJsonObject("result")
					.getAsJsonObject("data").getAsJsonArray("citationItems");
			inp.close();

		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void fetchRefrenceJson() {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet firstGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(firstGet);
			Header[] headers = response.getHeaders("Set-Cookies");
			HttpGet httpGet = new HttpGet(
					"https://www.researchgate.net/"
							+ "publicliterature.PublicationCitationsList.html?publicationUid="
							+ this.parsID() + "&showCitationsSorter=true&"
							+ "showAbstract=true&showType=true&"
							+ "showPublicationPreview=true&"
							+ "swapJournalAndAuthorPositions=false&limit=100000");
			httpGet.setHeaders(headers);
			httpGet.setHeader("authority", "www.researchgate.net");
			httpGet.setHeader("accept", "application/json");
			httpGet.setHeader("referer", url);
			httpGet.setHeader("x-requested-with", "XMLHttpRequest");
			httpGet.setHeader(
					"user-agent",
					"Mozilla/5.0 (Macintosh;Intel Mac OS X 10_11_2) "
							+ "AppleWebKit/537.36(KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			response = httpClient.execute(httpGet);
			Scanner inp = new Scanner(response.getEntity().getContent());
			String content = inp.nextLine();
			refrenceJsonArray = new JsonParser().parse(content)
					.getAsJsonObject().getAsJsonObject("result")
					.getAsJsonObject("data").getAsJsonArray("citationItems");
			inp.close();

		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Long[] parsCitedInIDs() {
		int size = citedInJsonArray.size();
		Long[] tmp = new Long[size];
		int i = 0;
		for (JsonElement jsonElement : citedInJsonArray) {
			if (jsonElement.getAsJsonObject().getAsJsonObject("data")
					.has("url")) {
				Long id = jsonElement.getAsJsonObject().getAsJsonObject("data")
						.get("publicationUid").getAsLong();
				tmp[i] = id;
				i++;
			}
		}
		Long[] ans = new Long[i];
		for (int j = 0; j < i; j++) {
			ans[j] = tmp[j];
		}
		return ans;
	}

	public String[] parsCitedInUrls() {
		int size = citedInJsonArray.size();
		String[] tmp = new String[size];
		int i = 0;
		for (JsonElement jsonElement : citedInJsonArray) {
			if (jsonElement.getAsJsonObject().getAsJsonObject("data")
					.has("url")) {
				String url = jsonElement.getAsJsonObject()
						.getAsJsonObject("data").get("url").getAsString();
				tmp[i] = baseUrl + url;
				i++;
			}
		}
		String[] ans = new String[i];
		for (int j = 0; j < i; j++) {
			ans[j] = tmp[j];
		}
		return ans;
	}

	public Long[] parsRefrenceIDs() {
		int size = refrenceJsonArray.size();
		Long[] tmp = new Long[size];
		int i = 0;
		for (JsonElement jsonElement : refrenceJsonArray) {
			if (jsonElement.getAsJsonObject().getAsJsonObject("data")
					.has("url")) {
				Long id = jsonElement.getAsJsonObject().getAsJsonObject("data")
						.get("publicationUid").getAsLong();
				tmp[i] = id;
				i++;
			}
		}
		Long[] ans = new Long[i];
		for (int j = 0; j < i; j++) {
			ans[j] = tmp[j];
		}
		return ans;
	}

	public String[] parsRefrenceUrls() {
		int size = refrenceJsonArray.size();
		String[] tmp = new String[size];
		int i = 0;
		for (JsonElement jsonElement : refrenceJsonArray) {
			if (jsonElement.getAsJsonObject().getAsJsonObject("data")
					.has("url")) {
				String url = jsonElement.getAsJsonObject()
						.getAsJsonObject("data").get("url").getAsString();
				tmp[i] = baseUrl + url;
				i++;
			}
		}
		String[] ans = new String[i];
		for (int j = 0; j < i; j++) {
			ans[j] = tmp[j];
		}
		return ans;
	}

	public Article pars() {
		try {
			doc = Jsoup.connect(this.url).timeout(TIMEOUT).get();
		} catch (IOException e) {
			System.err.println("Connecting to url failed.");
			e.printStackTrace();
			return null;
		}

		Article article = new Article();

		article.setUrl(url);
		article.setId(parsID());
		article.setName(parsName());
		article.setAuthors(parsAuthors());
		article.setAbstraction(parsAbstract());
		fetchCitedInJson();
		fetchRefrenceJson();
		article.setCitedInUrls(parsCitedInUrls());
		article.setCitedInIDs(parsCitedInIDs());
		article.setRefrenceIDs(parsRefrenceIDs());
		article.setRefrenceUrls(parsRefrenceUrls());

		return article;
	}

	private String parsAbstract() {
		Elements abstract_element = doc.getElementsByClass("pub-abstract");
		if (abstract_element.get(0).child(0).child(0)
				.hasClass("js-expander-container"))
			return abstract_element.get(0).child(0).child(0).child(1).text();
		else
			return abstract_element.get(0).child(0).child(1).text();
	}

	private String parsName() {
		Elements titles = null;
		if (doc.getElementsByClass("pub-title").size() > 0)
			titles = doc.getElementsByClass("pub-title");
		return titles.get(0).text();
	}

	private String[] parsAuthors() {
		Elements authors_elements = doc.getElementsByClass("author-item");
		int size = authors_elements.size();
		String[] authors = new String[size];

		for (int i = 0; i < size; i++) {
			Element e = authors_elements.get(i);
			authors[i] = e.getElementsByClass("item-name").get(0).child(0)
					.text();
		}
		return authors;
	}

	private long parsID() {
		return Long.parseLong(url.split("\\/")[4].split("_")[0]);
	}

}
