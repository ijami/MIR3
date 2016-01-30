package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class GUI {
    
	private JFrame startFrame;
	private JLayeredPane jlp;
	private final int startFrameWidth = 1175;
	private final int startFrameHeight = 615;
	JLabel error;

	public void run() {
		
		startFrame = new JFrame();
		startFrame.setSize(startFrameWidth, startFrameHeight);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setLocationRelativeTo(null);
		startFrame.setResizable(true);
		jlp = new JLayeredPane();
		JLabel jl = new JLabel();
		jl.setBounds(0, 0, startFrameWidth, startFrameHeight);
		jl.setIcon(new ImageIcon("start.png"));
		jlp.add(jl, new Integer(1));

		JButton bt = new JButton(""), bt2 = new JButton(""), bt3 = new JButton(""), bt4 = new JButton("");
		bt.setBounds(272, 271, 209, 98);
		bt.setVisible(true);
		jlp.add(bt, new Integer(2));
		bt2.setBounds(272, 373, 209, 98);
		bt2.setVisible(true);
		jlp.add(bt2, new Integer(2));
		bt3.setBounds(484, 271, 209, 198);
		bt3.setVisible(true);
		jlp.add(bt3, new Integer(2));
		bt4.setBounds(697, 271, 209, 198);
		bt4.setVisible(true);
		jlp.add(bt4, new Integer(2));
		bt.setOpaque(false);
		bt.setContentAreaFilled(false);
		bt.setBorderPainted(false);
		bt2.setOpaque(false);
		bt2.setContentAreaFilled(false);
		bt2.setBorderPainted(false);
		bt3.setOpaque(false);
		bt3.setContentAreaFilled(false);
		bt3.setBorderPainted(false);
		bt4.setOpaque(false);
		bt4.setContentAreaFilled(false);
		bt4.setBorderPainted(false);

		startFrame.setLayeredPane(jlp);
		startFrame.setVisible(true);
		jlp.setVisible(true);

		
		bt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				startFrame.dispose();
				new CrawlerUI().gui();
			}
		});
		bt2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				startFrame.dispose();
				new PagerankUI().gui();
			}
		});
		bt3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				startFrame.dispose();
				new SearchUI().gui();
			}
		});
		bt4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				startFrame.dispose();
			}
		});


		startFrame.repaint();
		
	}

	public static void main(String[] args) {
		new GUI().run();
	}

}
