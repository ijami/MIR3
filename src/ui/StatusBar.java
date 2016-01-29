package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class StatusBar {

	private int total;
	private int counter;
	private JFrame frame;
	private Container content;
	private JProgressBar progressBar;
	private final int width = 500;
	private final int height = 100;
	
	public int getCounter() {
		return counter;
	}
	
	public int getTotal() {
		return total;
	}
	
	public StatusBar(String name, int total) {
		this.total = total;
		counter = 0;
		frame = new JFrame(name);
	    frame.setSize(width, height);
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	
	public void progress() {
		counter ++;
		progressBar.setValue(100 * counter / total);
		if (counter == total) {
			frame.dispose();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		StatusBar sb = new StatusBar("indexing", 140);
		sb.paint();
		for (int i = 0; i < 140; i++) {
			sb.progress();
			Thread.sleep(100);
		}
	}
	
}
