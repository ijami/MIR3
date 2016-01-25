package article;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.google.gson.Gson;

public class ArticleStorage {
	
	String path;
	File articleFolder;
	
	public ArticleStorage(String path) {
		this.path = path;
		articleFolder = new File(path);
		if(!articleFolder.exists())
			articleFolder.mkdirs();
	}
	
	public void saveArticle(Article article){
		String serializedArticle = getJson(article);
		saveJsonToFile(serializedArticle, path + article.getId() + ".json");
	}
	
	private void saveJsonToFile(String serializedArticle, String fileName) {
		try {
			PrintWriter writer = new PrintWriter(new File(fileName));
			writer.println(serializedArticle);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String getJson(Article article){
		Gson gson = new Gson();
		String serializedArticle = gson.toJson(article);
		return serializedArticle;
	}
	
	public int numberOfArticles(){
		return articleFolder.listFiles().length;
	}

}
