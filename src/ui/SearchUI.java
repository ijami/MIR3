package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

public class SearchUI {

	JFrame f;
	JLayeredPane jlp;
	JLabel error;
	JButton indexButton;
	JTextField query;
	JLabel jl;

	public void gui() {
		initFrame();
		initLayeredPane();
		setFrameBackground();
		initDirectoryInput();
		setLogo();
		frameRepaint();
	}

	public static void main(String[] args) {
		new SearchUI().gui();
	}

	private void initFrame () {
		f = new JFrame();
		f.setSize(1175, 615);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setResizable(true);
	}
	
	private void initLayeredPane () {
		jlp = new JLayeredPane();
	}

	private void setFrameBackground() {
		JLabel jl2 = new JLabel();
		jl2.setBounds(0, 0, 1175, 615);
		jl2.setIcon(new ImageIcon("Back.png"));
		jlp.add(jl2, new Integer(0));
	}
	
	private void initDirectoryInput () {
		initDirectoryTextInput();
		initDirectoryButtonInput();
		jlp.add(indexButton, new Integer(1));
		jlp.add(query, new Integer(1));
	}
	
	private void initDirectoryTextInput () {
		query = new JTextField();
		query.setFont(new Font("Tahoma", 21, 22));
		query.setBounds(200, 220, 640, 40);
		query.setBorder(BorderFactory.createCompoundBorder(
		        query.getBorder(), 
		        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}
	
	private void initDirectoryButtonInput () {
		indexButton = new JButton("Search");
		indexButton.setBounds(850, 220, 120, 40);
		indexButton.setBackground(new Color(0, 160, 230));
		indexButton.setFont(new Font("Tahoma", 15, 16));
		indexButton.setBorder(null);
	}
	
	private void setLogo () {
		jl = new JLabel(new ImageIcon("google - Copy.png"));
		jl.setBounds(400, 60, 350, 130);
		jlp.add(jl, new Integer(1));
	}

	private void frameRepaint() {
		f.setBackground(new Color(253, 254, 255));
		f.setLayeredPane(jlp);
		f.setVisible(true);
		f.repaint();
	}
	
}


class MakeIndexListener implements MouseListener {

	JButton jb;
	JTextField jtf;
	UI ui;

	public MakeIndexListener(JButton jbb, JTextField jtff, UI uii) {
		jb = jbb;
		jtf = jtff;
		ui = uii;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		search.Indexer indexer = new search.Indexer();
//		indexer.
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		jb.setText("Really?");
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		jb.setText("Make Index");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		jb.setText("Oh Yes!");
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		jb.setText("Wait!");
	}

}


