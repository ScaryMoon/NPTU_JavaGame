package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrameScore extends JFrame{
	JPanel p = new JPanel();
	FrameScore(){
		p.setPreferredSize(new Dimension(400,400));
		p.setBackground(new Color(60,60,60));
		p.setLayout(null);
		this.setPreferredSize(new Dimension(400,400));
		this.setTitle("�O���O");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setResizable(false);
		this.add(p);
		this.pack();
		this.setLocation(1350, 500);
		this.setLayout(null);
	    this.setVisible(true);
	}
	public void ShowUp(int a[]){
		p.removeAll();
		p.validate();
		
		JLabel one= new JLabel("�Ĥ@�W:"+a[0]+'��');
		JLabel two= new JLabel("�ĤG�W:"+a[1]+'��');
		JLabel three= new JLabel("�ĤT�W:"+a[2]+'��');
		JLabel four= new JLabel("�ĥ|�W:"+a[3]+'��');
		JLabel five= new JLabel("�Ĥ��W:"+a[4]+'��');
		one.setForeground(Color.white);
		one.setBounds(40,50,300,80);
		one.setFont(new Font("�з���",  Font.BOLD, 30));
		
		two.setForeground(Color.white);
		two.setBounds(40,90,300,80);
		two.setFont(new Font("�з���",  Font.BOLD, 30));

		three.setForeground(Color.white);
		three.setBounds(40,130,300,80);
		three.setFont(new Font("�з���",  Font.BOLD, 30));

		four.setForeground(Color.white);
		four.setBounds(40,170,300,80);
		four.setFont(new Font("�з���",  Font.BOLD, 30));

		five.setForeground(Color.white);
		five.setBounds(40,210,300,80);
		five.setFont(new Font("�з���",  Font.BOLD, 30));
		p.repaint();
		
		
		p.add(one);
		p.add(two);
		p.add(three);
		p.add(four);
		p.add(five);
		repaint();
		
	}
}
