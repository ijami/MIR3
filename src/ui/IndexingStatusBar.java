package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import search.Indexer;

public class IndexingStatusBar extends Thread {

	private int total;
	private int counter;
	private JFrame frame;
	private Container content;
	private JProgressBar progressBar;
	private final int width = 500;
	private final int height = 100;
	String folder;
	
	public int getCounter() {
		return counter;
	}
	
	public int getTotal() {
		return total;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public IndexingStatusBar(String name, int total, String path) {
		this.total = total;
		counter = 0;
		frame = new JFrame(name);
	    frame.setSize(width, height);
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    folder = path;
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
		this.paint();
		System.out.println(total);
		Indexer indexer = new Indexer();
		try {
			indexer.clientStartUp(null);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		indexer.indexCorpus(folder, this);
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
