package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

public class CrawlerUI {

	JFrame f;
	JLayeredPane jlp;
	JLabel error;
	JLabel urlLabel;
	JLabel numberLabel;
	JLabel indeg;
	JLabel outdeg;
	JTextField urls;
	JTextField number;
	JTextField indegtext;
	JTextField outdegtext;
	JLabel jl;
	JButton home;
	JButton crawl;
	JButton authorsCrawl;
	Color optionColor = new Color(100, 100, 200);

	int settingsH = 270;

	public void gui() {
		initFrame();
		initLayeredPane();
		setFrameBackground();
		initURLInput();
		initNumberInput();
		initINDEGinput();
		initOUTDEGinput();
		setLogo();
		setHomeButton();
		setCrawlButton();
		setAuthorsCrawlButton();
		frameRepaint();
	}


	private void initOUTDEGinput() {
		initOUTDEGTextInput();
		initOUTDEGLabel();
		jlp.add(outdeg, new Integer(1));
		jlp.add(outdegtext, new Integer(1));
	}

	private void initOUTDEGTextInput() {
		outdegtext = new JTextField();
		outdegtext.setFont(new Font("Tahoma", 21, 22));
		outdegtext.setBounds(939, 190, 50, 40);
		outdegtext.setBorder(
				BorderFactory.createCompoundBorder(outdegtext.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		outdegtext.setText("10");
	}

	private void initOUTDEGLabel() {
		outdeg = new JLabel("Out-degree");
		outdeg.setBounds(840, 190, 150, 40);
		outdeg.setBackground(new Color(0, 160, 230));
		outdeg.setFont(new Font("Tahoma", 15, 16));
		outdeg.setBorder(null);
//		urlLabel.addMouseListener(new MakeIndexListener(urlLabel, query));
	}


	private void initINDEGinput() {
		initINDEGTextInput();
		initINDEGLabel();
		jlp.add(indeg, new Integer(1));
		jlp.add(indegtext, new Integer(1));
	}

	private void initINDEGTextInput() {
		indegtext = new JTextField();
		indegtext.setFont(new Font("Tahoma", 21, 22));
		indegtext.setBounds(645, 190, 50, 40);
		indegtext.setBorder(
				BorderFactory.createCompoundBorder(indegtext.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		indegtext.setText("10");
	}

	private void initINDEGLabel() {
		indeg = new JLabel("In-degree");
		indeg.setBounds(550, 190, 150, 40);
		indeg.setBackground(new Color(0, 160, 230));
		indeg.setFont(new Font("Tahoma", 15, 16));
		indeg.setBorder(null);
//		urlLabel.addMouseListener(new MakeIndexListener(urlLabel, query));
	}


	private void setHomeButton() {
		home = new JButton("");
		home.setIcon(new ImageIcon("home.jpg"));
		home.setBounds(1090, 5, 60, 60);
		home.setOpaque(false);
		home.setContentAreaFilled(false);
		home.setBorderPainted(false);
		home.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				f.dispose();
				new GUI().run();
			}
		});
		jlp.add(home, new Integer(2));

	}

	public static void main(String[] args) {
		new CrawlerUI().gui();
	}

	private void initFrame() {
		f = new JFrame();
		f.setSize(1175, 615);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setResizable(true);
	}

	private void initLayeredPane() {
		jlp = new JLayeredPane();
	}

	private void setFrameBackground() {
		JLabel jl2 = new JLabel();
		jl2.setBounds(0, 0, 1175, 615);
		jl2.setIcon(new ImageIcon("Back.png"));
		jlp.add(jl2, new Integer(0));
	}

	private void initURLInput() {
		initURLTextInput();
		initURLLabel();
		jlp.add(urlLabel, new Integer(1));
		jlp.add(urls, new Integer(1));
	}

	private void initURLTextInput() {
		urls = new JTextField();
		urls.setFont(new Font("Tahoma", 21, 22));
		urls.setBounds(350, 140, 640, 40);
		urls.setBorder(
				BorderFactory.createCompoundBorder(urls.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}

	private void initURLLabel() {
		urlLabel = new JLabel("Starting URLs");
		urlLabel.setBounds(200, 140, 150, 40);
		urlLabel.setBackground(new Color(0, 160, 230));
		urlLabel.setFont(new Font("Tahoma", 15, 16));
		urlLabel.setBorder(null);
//		urlLabel.addMouseListener(new MakeIndexListener(urlLabel, query));
	}

	private void initNumberInput() {
		initNumberTextInput();
		initNumberLabel();
		jlp.add(numberLabel, new Integer(1));
		jlp.add(number, new Integer(1));
	}

	private void initNumberTextInput() {
		number = new JTextField();
		number.setFont(new Font("Tahoma", 21, 22));
		number.setBounds(350, 190, 80, 40);
		number.setBorder(
				BorderFactory.createCompoundBorder(number.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		number.setText("1000");
	}

	private void initNumberLabel() {
		numberLabel = new JLabel("Number of URLS ");
		numberLabel.setBounds(200, 190, 150, 40);
		numberLabel.setBackground(new Color(0, 160, 230));
		numberLabel.setFont(new Font("Tahoma", 15, 16));
		numberLabel.setBorder(null);
//		indexButton.addMouseListener(new SearchButtonListener(indexButton, query, this));
	}
	
	public void setCrawlButton () {
		crawl = new JButton("CRAWL IT!");
		crawl.setBounds(200, 240, 790, 40);
		crawl.setBackground(Color.BLUE);
		crawl.setFont(new Font("Tahoma", 15, 16));
		crawl.addMouseListener(new CrawlButtonListener(crawl, urls, number, indegtext, outdegtext));
		jlp.add(crawl, new Integer(2));
	}
	
	public void setAuthorsCrawlButton () {
		authorsCrawl = new JButton("CRAWL AUTHORS!");
		authorsCrawl.setBounds(200, 290, 790, 40);
		authorsCrawl.setBackground(Color.RED);
		authorsCrawl.setFont(new Font("Tahoma", 15, 16));
		authorsCrawl.addMouseListener(new CrawlAuthorsButtonListener(crawl, urls, number, indegtext, outdegtext));
		jlp.add(authorsCrawl, new Integer(2));
	}
	

	private void setLogo() {
		jl = new JLabel(new ImageIcon("google - Copy.png"));
		jl.setBounds(400, 20, 350, 130);
		jlp.add(jl, new Integer(1));
	}

	private void frameRepaint() {
		f.setBackground(new Color(253, 254, 255));
		f.setLayeredPane(jlp);
		f.setVisible(true);
		f.repaint();
	}

	public static int removeLayer(JLayeredPane pane, int layer) {
		Component[] comps = pane.getComponentsInLayer(layer);
		System.out.println(comps.length);
		for (int x = 0; x < comps.length; x++) {
			pane.remove(pane.getIndexOf(comps[x]));
		}
		return comps.length;
	}

}
class CrawlButtonListener implements MouseListener {

	JButton jb;
	JTextField urls;
	JTextField number;
	JTextField ind;
	JTextField outd;

	public CrawlButtonListener(JButton jbb, JTextField jtff1, JTextField jtff2, 
			JTextField jtff3, JTextField jtff4) {
		jb = jbb;
		urls = jtff1;
		number = jtff2;
		ind = jtff3;
		outd = jtff4;
	}

	int height = 220;

	@Override
	public void mouseClicked(MouseEvent arg0) {
		CrawlingStatusBar progressBar = new CrawlingStatusBar("Crawling", 
				Integer.parseInt(number.getText()), urls.getText(), 
				Integer.parseInt(ind.getText()), Integer.parseInt(outd.getText()));
		progressBar.start();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		jb.setText("CRAWL IT!");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}


class CrawlAuthorsButtonListener implements MouseListener {

	JButton jb;
	JTextField urls;
	JTextField number;
	JTextField ind;
	JTextField outd;

	public CrawlAuthorsButtonListener(JButton jbb, JTextField jtff1, JTextField jtff2, 
			JTextField jtff3, JTextField jtff4) {
		jb = jbb;
		urls = jtff1;
		number = jtff2;
		ind = jtff3;
		outd = jtff4;
	}

	int height = 220;

	@Override
	public void mouseClicked(MouseEvent arg0) {
		AuthorCrawlerStatusBar progressBar = new AuthorCrawlerStatusBar("Authors Crawling", 
				Integer.parseInt(number.getText()), urls.getText(), 
				Integer.parseInt(ind.getText()), Integer.parseInt(outd.getText()));
		progressBar.start();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		jb.setText("CRAWL AUTHORS!");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
