package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import article.ArticleStorage;
import crawler.ArticleCrawler;
import pagerank.ArticlePageRanker;

public class PagerankStatusBar extends Thread {

	double alpha;
	double treshold;
	private JFrame frame;
	private Container content;
	private JProgressBar progressBar;
	private final int width = 500;
	private final int height = 100;
	String folder;
	int counter;
	
	public int getCounter() {
		return counter;
	}
	
	
	public JFrame getFrame() {
		return frame;
	}
	
	
	public PagerankStatusBar(String name, double tresh, double alpha) {
		this.alpha = alpha;
		treshold = tresh;
		frame = new JFrame(name);
	    frame.setSize(width, height);
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    counter = 0;
	}
	
	public void paint() {
		content = frame.getContentPane();
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		Border border = BorderFactory.createTitledBorder("Indexing");
	    progressBar.setBorder(border);
	    progressBar.setForeground(Color.GREEN);
	    content.add(progressBar, BorderLayout.CENTER);
	    frame.setVisible(true);
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public void progress() {
		counter ++;
	}
	
	@Override
	public void run() {
		ArticlePageRanker ap = new ArticlePageRanker(
				new ArticleStorage("C:/Users/rafie/Desktop/articles").getArticlesFromRepo(), this);
		paint();
		ap.pageRank(alpha, treshold);
		frame.dispose();
	}

	public static void main(String[] args) throws InterruptedException {
		JFrame jf = new JFrame("dare dahane ehsan");
		jf.setVisible(true);
		jf.repaint();
//		IndexingStatusBar sb = new IndexingStatusBar("indexing", 0);
//		sb.setTotal(140);
//		sb.start();
//		for (int i = 0; i < 140; i++) {
//			sb.progress();
//			Thread.sleep(100);
//		}
	}
	
}
