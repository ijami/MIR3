package article;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ArticlesReader {
	
	private String folderPath;
	private File folder;
	private Scanner scanner;
	
	public ArticlesReader(String path) {
		folderPath = path;
		folder = new File(folderPath);
	}
	
	public int getNumberOfArticles () {
		return folder.listFiles().length;
	}
	
	public String getArticleAsJSON (int i) throws FileNotFoundException {
		scanner = new Scanner(folder.listFiles()[i]);
		StringBuilder sb = new StringBuilder();
		while (scanner.hasNextLine()) 
			sb.append(scanner.nextLine());
		return sb.toString();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		ArticlesReader ar = new ArticlesReader("C:/Users/rafie/workspace/MIR3/articles");
		for (int i = 0; i < 3; i++)
			System.out.println(ar.getArticleAsJSON(i));
	}

}
