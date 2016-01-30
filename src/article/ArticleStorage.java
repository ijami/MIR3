package article;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.google.gson.Gson;

public class ArticleStorage {
	
	String path;
	File articleFolder;
	Article[] articleRepo;
	
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

	public String getJson(Article article){
		Gson gson = new Gson();
		String serializedArticle = gson.toJson(article);
		return serializedArticle;
	}

	public Article getArticleFromJson(String json){
		Gson gson = new Gson();
		Article article = gson.fromJson(json, Article.class);
		return article;
	}
	
	public int numberOfArticles(){
		return articleFolder.listFiles().length;
	}
	
	public Article[] getArticlesFromRepo(){
		File[] files = articleFolder.listFiles();
		articleRepo = new Article[files.length];
		BufferedReader inp;
		String json = null;
		for (int i = 0; i < files.length; i ++) {
			try {
				inp = new BufferedReader(new FileReader(files[i]));
				json = inp.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			articleRepo[i] = getArticleFromJson(json);
		}
		return articleRepo;
	}

}
