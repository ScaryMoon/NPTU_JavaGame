package snake;

import javax.swing.JFrame;

public class Frame extends JFrame {
	public Panel p = new Panel();
	
	Frame() {
		this.setTitle("G03_Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(p.getSize());
		System.out.println(this.getSize());
		this.setResizable(false);
		this.add(p);
		this.pack();
		this.setLocationRelativeTo(null);
		p.setLayout(null);
		this.setLayout(null);
	    this.setVisible(true);
	}
}


//package snake;

//import java.awt.BorderLayout;
//
//import javax.swing.JFrame;
//
//public class Frame extends JFrame {
//	public Panel p = new Panel();
////	public Panel2 top = new Panel2();
//	Frame() {
//		this.setTitle("G03_Snake");
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		this.setLayout(new BorderLayout());
////		this.add(top, BorderLayout.NORTH);
////		this.setSize(p.getSize());
//		this.setResizable(true);
//		this.add(p);
//		this.pack();
//		this.setLocationRelativeTo(null);
//		p.setLayout(null);
//		System.out.println(this.getSize());
//		this.setLayout(null);
//	    this.setVisible(true);
//	}
//}
