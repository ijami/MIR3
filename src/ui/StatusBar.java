package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

public class StatusBar extends Thread {

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
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void setTotal(int total) {
		this.total = total;
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
	}
	
	@Override
	public void run() {
		this.paint();
		System.out.println(total);
		while (counter < total) {
			System.out.println("counter = " + counter);
			System.out.println("prog = " + progressBar.getValue());
			SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                	new SwingWorker<Void,Void>() {
                		   protected Void doInBackground() throws Exception {
                		   progressBar.setValue(100 * counter / total);
                		   return null;
                		 };
                		 }.execute();                }
              });
			frame.repaint();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		frame.dispose();
	}

	public static void main(String[] args) throws InterruptedException {
		JFrame jf = new JFrame("dare dahane ehsan");
		jf.setVisible(true);
		jf.repaint();
		StatusBar sb = new StatusBar("indexing", 0);
		sb.setTotal(140);
		sb.start();
		for (int i = 0; i < 140; i++) {
			sb.progress();
			Thread.sleep(100);
		}
	}
	
}
