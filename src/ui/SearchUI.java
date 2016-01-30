package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import article.Article;
import search.Searcher;

public class SearchUI {

	JFrame f;
	JLayeredPane jlp;
	JLabel error;
	JButton indexButton;
	JButton searchButton;
	JTextField query;
	JLabel jl;
	JPanel settingsPanel;
	JPanel authorPanel;
	JPanel namePanel;
	JPanel abstractPanel;
	JTextField authorW;
	JTextField nameW;
	JTextField abstractW;
	JButton home;
	Color optionColor = new Color(100, 100, 200);

	int settingsH = 270;

	public void gui() {
		initFrame();
		initLayeredPane();
		setFrameBackground();
		initDirectoryInput();
		initQueryInput();
		setLogo();
		frameRepaint();
		initPanelTitle();
		initPanelAuthor();
		initPanelName();
		initPanelAbstract();
		setHomeButton();
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

	private void initPanelAbstract() {
		settingsPanel = new JPanel();
		settingsPanel.setBounds(15, settingsH, 165, 35);
		settingsPanel.setBackground(new Color(249, 186, 11));
		settingsPanel.setVisible(true);
		addSettings();
	}

	private void initPanelName() {
		namePanel = new JPanel();
		namePanel.setBounds(15, settingsH + 40, 165, 35);
		namePanel.setBackground(new Color(234, 67, 53));
		namePanel.setVisible(true);

		JLabel nameLabel = new JLabel("name         ");
		nameLabel.setFont(new Font("Tahoma", 16, 16));
		namePanel.add(nameLabel);

		nameW = new JTextField();
		nameW.setText("1");
		nameW.setFont(new Font("Tahoma", 16, 16));
		nameW.setColumns(3);
		nameW.setVisible(true);
		namePanel.add(nameW);

		jlp.add(namePanel, new Integer(1));
	}

	private void initPanelAuthor() {
		authorPanel = new JPanel();
		authorPanel.setBounds(15, settingsH + 80, 165, 35);
		authorPanel.setBackground(new Color(66, 133, 244));
		authorPanel.setVisible(true);

		JLabel authorLabel = new JLabel("author       ");
		authorLabel.setFont(new Font("Tahoma", 16, 16));
		authorPanel.add(authorLabel);

		authorW = new JTextField();
		authorW.setText("1");
		authorW.setFont(new Font("Tahoma", 16, 16));
		authorW.setColumns(3);
		authorW.setVisible(true);
		authorPanel.add(authorW);

		jlp.add(authorPanel, new Integer(1));
	}

	private void initPanelTitle() {
		abstractPanel = new JPanel();
		abstractPanel.setBounds(15, settingsH + 120, 165, 35);
		abstractPanel.setBackground(new Color(52, 168, 83));
		abstractPanel.setVisible(true);

		JLabel abstractLabel = new JLabel("abstract     ");
		abstractLabel.setFont(new Font("Tahoma", 16, 16));
		abstractPanel.add(abstractLabel);

		abstractW = new JTextField();
		abstractW.setText("1");
		abstractW.setFont(new Font("Tahoma", 16, 16));
		abstractW.setColumns(3);
		abstractW.setVisible(true);
		abstractPanel.add(abstractW);
		jlp.add(abstractPanel, new Integer(1));
	}

	private void addSettings() {
		JLabel weights = new JLabel("Score Setting");
		weights.setFont(new Font("Tahoma", 18, 18));
		weights.setSize(195, 20);
		weights.setVisible(true);
		settingsPanel.add(weights);
		settingsPanel.repaint();
		jlp.add(settingsPanel, new Integer(1));

		f.repaint();
	}

	public static void main(String[] args) {
		new SearchUI().gui();
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

	private void initDirectoryInput() {
		initDirectoryTextInput();
		initDirectoryButtonInput();
		jlp.add(indexButton, new Integer(1));
		jlp.add(query, new Integer(1));
	}

	private void initDirectoryTextInput() {
		query = new JTextField();
		query.setFont(new Font("Tahoma", 21, 22));
		query.setBounds(200, 140, 640, 40);
		query.setBorder(
				BorderFactory.createCompoundBorder(query.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}

	private void initDirectoryButtonInput() {
		indexButton = new JButton("Index");
		indexButton.setBounds(850, 140, 120, 40);
		indexButton.setBackground(new Color(0, 160, 230));
		indexButton.setFont(new Font("Tahoma", 15, 16));
		indexButton.setBorder(null);
		indexButton.addMouseListener(new MakeIndexListener(indexButton, query));
	}

	private void initQueryInput() {
		initQueryTextInput();
		initQueryButtonInput();
		jlp.add(searchButton, new Integer(1));
		jlp.add(query, new Integer(1));
	}

	private void initQueryTextInput() {
		query = new JTextField();
		query.setFont(new Font("Tahoma", 21, 22));
		query.setBounds(200, 190, 640, 40);
		query.setBorder(
				BorderFactory.createCompoundBorder(query.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}

	private void initQueryButtonInput() {
		searchButton = new JButton("Search");
		searchButton.setBounds(850, 190, 120, 40);
		searchButton.setBackground(new Color(0, 160, 230));
		searchButton.setFont(new Font("Tahoma", 15, 16));
		searchButton.setBorder(null);
		searchButton.addMouseListener(new SearchButtonListener(searchButton, query, this));
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

class MakeIndexListener implements MouseListener {

	JButton jb;
	JTextField jtf;
	StatusBar bar;

	public MakeIndexListener(JButton jbb, JTextField jtff) {
		jb = jbb;
		jtf = jtff;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		IndexingStatusBar sb = new IndexingStatusBar("indexing", 992, jtf.getText());
		sb.start();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		jb.setText("Make Index");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}

class SearchButtonListener implements MouseListener {

	JButton jb;
	JTextField jtf;
	SearchUI ui;

	public SearchButtonListener(JButton jbb, JTextField jtff, SearchUI uii) {
		jb = jbb;
		jtf = jtff;
		ui = uii;
	}

	int height = 220;

	@Override
	public void mouseClicked(MouseEvent arg0) {
		SearchUI.removeLayer(ui.jlp, 2);
		ui.f.repaint();
		String query = jtf.getText();
		Searcher searcher = new Searcher(10);
		try {
			searcher.clientStartUp(null);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Article[] articles = searcher.search(query, Double.parseDouble(ui.nameW.getText()),
				Double.parseDouble(ui.abstractW.getText()), Double.parseDouble(ui.authorW.getText()));
		for (int i = 0; i < 10 && i < articles.length; i++) {
			Article article = articles[i];
			JLabel id = new JLabel(Integer.toString(i + 1));
			id.setFont(new Font("Tahoma", 14, 15));
			id.setBounds(200, height + 30 * i, 20, 100);
			JLabel index = new JLabel(Long.toString(article.getId()));
			index.setFont(new Font("Tahoma", 14, 15));
			index.setBounds(240, height + 30 * i, 150, 100);
			JLabel title = new JLabel(article.getName());
			title.setFont(new Font("Tahoma", 14, 15));
			title.setBounds(340, height + 30 * i, 600, 100);
			// JLabel author = new JLabel();
			// author.setFont(new Font("Tahoma", 14, 15));
			// author.setBounds(720, 300 + 30 * i, 150, 100);
			// JLabel show = new JLabel("+Show");
			// show.setFont(new Font("Tahoma", 14, 15));
			// show.setBounds(880, 300 + 30 * i, 100, 100);
			// show.setBackground(new Color(50, 50, 50));
			// show.setCursor(new Cursor(Cursor.HAND_CURSOR));
			// show.addMouseListener(new ShowButtonListener(ui,
			// Integer.parseInt(docu.get("id"))));
			// ui.jlp.add(author, 3);
			ui.jlp.add(id, 3);
			ui.jlp.add(title, 3);
			ui.jlp.add(index, 3);
			// ui.jlp.add(show);
			ui.jlp.setLayer(id, 2);
			// ui.jlp.setLayer(author, 2);
			ui.jlp.setLayer(index, 2);
			ui.jlp.setLayer(title, 2);
			// ui.jlp.setLayer(show, 2);
		}
		ui.f.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		jb.setText("Search");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
