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
		this.setTitle("記分板");
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
		
		JLabel one= new JLabel("第一名:"+a[0]+'分');
		JLabel two= new JLabel("第二名:"+a[1]+'分');
		JLabel three= new JLabel("第三名:"+a[2]+'分');
		JLabel four= new JLabel("第四名:"+a[3]+'分');
		JLabel five= new JLabel("第五名:"+a[4]+'分');
		one.setForeground(Color.white);
		one.setBounds(40,50,300,80);
		one.setFont(new Font("標楷體",  Font.BOLD, 30));
		
		two.setForeground(Color.white);
		two.setBounds(40,90,300,80);
		two.setFont(new Font("標楷體",  Font.BOLD, 30));

		three.setForeground(Color.white);
		three.setBounds(40,130,300,80);
		three.setFont(new Font("標楷體",  Font.BOLD, 30));

		four.setForeground(Color.white);
		four.setBounds(40,170,300,80);
		four.setFont(new Font("標楷體",  Font.BOLD, 30));

		five.setForeground(Color.white);
		five.setBounds(40,210,300,80);
		five.setFont(new Font("標楷體",  Font.BOLD, 30));
		p.repaint();
		
		
		p.add(one);
		p.add(two);
		p.add(three);
		p.add(four);
		p.add(five);
		repaint();
		
	}
}
