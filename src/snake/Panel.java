package snake;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	boolean gamemode = true;
    boolean VisiableSnake = false;
    boolean keyinput = true;
    boolean Stop = false;
    int SCREEN_WIDTH = 760, SCREEN_HEIGHT = 760, UNIT_SIZE = 40, GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    //save history score
    int savescoretimes = 0, savescore[], historyscore[] = {0,0,0,0,0};
    
    int speed, second, bodyParts, score, appleX, appleY;
    final int x[] = new int[GAME_UNITS], y[] = new int[GAME_UNITS] , Rank[] = {0,0,0,0,0 };

    FrameScore ScorePanel = new FrameScore();
    

    Random random;
    char direction;
    Timer timer, clock;
    TimerTask task, task2;

    Panel() {
    	Initialize();
        ScorePanel.setVisible(false);

        this.addKeyListener(new MyKeyAdapter());
        this.setBackground(new Color(117, 111, 111));
        this.setFocusable(true);
        StartPanel();
    }

    //Key control
    public class MyKeyAdapter extends KeyAdapter {
    	@Override
        public void keyPressed(KeyEvent e) {
    		if (VisiableSnake == true && keyinput == true) {
    			keyinput = false;
			    switch(e.getKeyCode()) {
			    case KeyEvent.VK_LEFT:
			    	if(direction != 'R' && gamemode == true) { direction = 'L'; }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L' && gamemode == true) { direction = 'R'; }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D' && gamemode == true) { direction = 'U'; }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U' && gamemode == true) { direction = 'D'; }
                    break;
                case KeyEvent.VK_SPACE:
                    gamemode = !gamemode;
                    Stop = !Stop;
                    keyinput = true;
                    break;
                }
            }
        }
    }

    //Initialize
    public void Initialize() {
    	speed = 150;
        bodyParts = 1;
        score = 0;
        appleX = 0;
        appleY = 0;
        direction = 'R';
        for (int i = 0 ; i < GAME_UNITS ; i++) {
        	x[i] = 0;
        	y[i] = 0;
        }
    }

    //Game running
    public void GameTimer() {
    	if (VisiableSnake == true) {
    		RemovePanel();
            timer = new Timer();
            clock = new Timer();
            random = new Random();
            this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
            this.setBackground(Color.black);
            this.setFocusable(true);
            //newApple();
            second = 0;
            task = new TimerTask() {
            	public void run() {
            		if(gamemode == true) {
            			move();
                        checkApple();
                        checkCollisions();
                        repaint();
                        keyinput = true;
                    } else if(gamemode == false && Stop == true) {
                	    repaint();
                    } else { timer.cancel(); }
                }
            };
            //Add clock ==========================================================================
            task2 = new TimerTask() {
            	public void run() {
            		if(gamemode == true) {
            			second++;
                        //System.out.println(second);
                    } else { clock.cancel(); }
                }
            };
            //====================================================================================
            timer.scheduleAtFixedRate(task, 0, speed);
            clock.scheduleAtFixedRate(task2, 0, 1000);
	    } else { RemovePanel(); }
    }

    //Draw snake、apple
    public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (VisiableSnake == true) {
		    if(gamemode == true && Stop == false) {
			    //Draw line
                for(int i = 0 ; i < SCREEN_HEIGHT / UNIT_SIZE ; i++) {
            	    g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                    g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                }
                //Draw apple
                g.setColor(Color.red);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
                //Draw snake
                for(int i = 0 ; i < bodyParts ; i++) {
            	    if(i == 0) {
            		    g.setColor(Color.green);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                	    g.setColor(new Color(45, 180, 0));
                	    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }   
                }
                //Draw score information
                g.setColor(Color.red);
                g.setFont(new Font(null, Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + score, (SCREEN_WIDTH - metrics.stringWidth("Score: " + score)) / 2, g.getFont().getSize());
            } else if (gamemode == false && Stop == true) {
        	    g.setColor(Color.red);
                g.setFont(new Font("Times New Roman", Font.BOLD, 60));
                FontMetrics metrics2 = getFontMetrics(g.getFont());
                g.drawString("Paused", (SCREEN_WIDTH - metrics2.stringWidth("Paused")) / 2, SCREEN_HEIGHT / 2);
            } else { gameOver(g); }
	    }
    }

    //Check snake eat apple
    public void checkApple() {
	    if((x[0] == appleX) && (y[0] == appleY)) {
	   	    bodyParts++;
            score++;
            newApple();
        }
    }

    //Update apple position
    public void newApple() {
	    appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }
 
    //Snake's move and direction
    public void move() {
	    for(int i = bodyParts ; i > 0 ; i--) {
		    x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction) {
        case 'U':
            y[0] = y[0] - UNIT_SIZE;
            break;
        case 'D':
            y[0] = y[0] + UNIT_SIZE;
            break;
        case 'L':
            x[0] = x[0] - UNIT_SIZE;
            break;
        case 'R':
            x[0] = x[0] + UNIT_SIZE;
            break;
        }
    }

    //Checks if head collides with body
    public void checkCollisions() {
	    for(int i = bodyParts ; i > 0 ; i--) {
		    if((x[0] == x[i]) && (y[0] == y[i])) {
            gamemode = false;
            }
        }
        if(x[0] < 0) { gamemode = false; }
        if(x[0] > SCREEN_WIDTH - 1) { gamemode = false; }
        if(y[0] < 0) { gamemode = false; }
        if(y[0] > SCREEN_HEIGHT - 1) { gamemode = false; }
    }

    //Restart information show
    public void gameOver(Graphics g) {
	    //Score
        g.setColor(Color.red);
        g.setFont(new Font(null, Font.BOLD, 40));
        g.drawString("Score: " + score, (SCREEN_WIDTH / 2) - (160 / 2), g.getFont().getSize());
        //Add second==========================================================================
        g.setFont(new Font(null, Font.BOLD, 30));
        g.drawString("Second: " + second + "s", (SCREEN_WIDTH / 2) - (160 / 2), SCREEN_HEIGHT / 2+80);

        //Record score =======================================================================
//        savescoretimes++;
//        savescore = new int[savescoretimes];
//        for (int i = 0 ; i <= savescoretimes - 1 ; i++) {
//    	    if (i < savescoretimes - 1) {
//    		    savescore[i] = historyscore[i];
//	        } else if (i == savescoretimes - 1) {
//	    	    historyscore = new int[savescoretimes];
//		        savescore[i] = score;
//	        }
//        }
//        for (int i = 0 ; i <= savescoretimes - 1 ; i++) {
//    	    historyscore[i] = savescore[i];
//	        System.out.print("Times" + (i + 1) + " score: " + historyscore[i] + "  ");
//        }
//            System.out.print("\n");
        for (int i=0 ; i <5 ;i++) {
        	if (score > historyscore[i]) {
        		historyscore[i]=score;
        		break;
        	}
        }                

        ScorePanel.ShowUp(historyscore);
        ScorePanel.setVisible(true);
        //====================================================================================

        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        gamemode = false;
        VisiableSnake = false;
        Initialize();
        JButton restart = new JButton("返回開始");
        restart.setBackground(Color.black);
        restart.setForeground(Color.RED);
        restart.setFont(new Font("標楷體", Font.CENTER_BASELINE, 30));
        restart.setBorder(null);
        restart.setBounds((SCREEN_WIDTH / 2) - (160 / 2), (SCREEN_HEIGHT / 3 * 2) + 50, 160, 50);
        restart.setContentAreaFilled(false);
        restart.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    		    RemovePanel();
                StartPanel();
            }});
        this.add(restart);
    }

    //Start information show
    public void StartPanel() {
	    JButton start1 = new JButton("開始遊玩");
        this.setLayout(null);
        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        this.setBackground(new Color(117, 111, 111));
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        start1.setBackground(new Color(176, 164, 164));
        start1.setFont(new Font("標楷體", Font.CENTER_BASELINE, 30));
        start1.setBorder(null);
        start1.setBounds((SCREEN_WIDTH / 2) - (160 / 2), (SCREEN_HEIGHT / 3 * 2) + 50, 160, 50);
        start1.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    		    VisiableSnake = true;
                gamemode = true;
                GameTimer();
                newApple();
                ScorePanel.setVisible(false);
            }});
        this.add(start1);
    }

    //Remove Panel
    public void RemovePanel() {
	    this.removeAll();
        this.validate();
        this.repaint();
    }
	

}
//package snake;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.FontMetrics;
//import java.awt.Graphics;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.util.Random;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import javax.swing.JButton;
//import javax.swing.JPanel;
//
//@SuppressWarnings("serial")
//public class Panel extends JPanel {
// boolean gamemode = true;
// boolean VisiableSnake = false;
// boolean Keyinput=true;
// boolean Stop=false;
// int SCREEN_WIDTH = 800;
// int SCREEN_HEIGHT = 800;
// int UNIT_SIZE = 40;
// int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
// final int x[] = new int[GAME_UNITS];
// final int y[] = new int[GAME_UNITS];
//
// int delay, bodyParts, score, appleX, appleY;
// Random random;
// char direction;
// Timer timer;
// TimerTask task;
//
// Panel() {
//  Initialize();
//  this.addKeyListener(new MyKeyAdapter());
//  this.setBackground(new Color(117,111,111));
//  this.setFocusable(true);
//  StartPanel();
// }
// public class MyKeyAdapter extends KeyAdapter{
//  @Override
//  public void keyPressed(KeyEvent e) {
//   if (VisiableSnake == true &&Keyinput == true) {
//    switch(e.getKeyCode()) {
//    case KeyEvent.VK_LEFT:
//     if(direction != 'R' && gamemode == true) {
//      direction = 'L';
//      Keyinput =false;
//     }
//     break;
//    case KeyEvent.VK_RIGHT:
//     if(direction != 'L'&& gamemode == true) {
//      direction = 'R';
//      Keyinput =false;
//     }
//     break;
//    case KeyEvent.VK_UP:
//     if(direction != 'D'&& gamemode == true) {
//      direction = 'U';
//      Keyinput =false;
//     }
//     break;
//    case KeyEvent.VK_DOWN:
//     if(direction != 'U'&& gamemode == true) {
//      direction = 'D';
//      Keyinput =false;
//     }
//     break;
//    case KeyEvent.VK_SPACE:
//     gamemode = !gamemode;
//     Stop=!Stop;
//     System.out.println("gamemode:"+gamemode);
//     break;
//    }
//   }
//  }
// }
//
//
// //Initialize
// public void Initialize() {
//  delay = 100;
//  bodyParts = 5;
//  score = 0;
//  appleX = 0;
//  appleY = 0;
//  direction = 'R';
//  Keyinput=true;
//  for (int i = 0 ; i < GAME_UNITS ; i++) {
//   x[i] = 0;
//   y[i] = 0;
//  }
// }
//
// //Game running
// public void GameTimer() {
//  if (VisiableSnake == true) {
//	  RemovePanel();
//	  timer = new Timer();
//	  random = new Random();
//	  this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
//	  this.setBackground(Color.black);
//	  this.setFocusable(true);
//      //newApple();
//
//      task = new TimerTask() {
//      public void run() {
//     if(gamemode == true) {
//      move();
//      checkApple();
//      checkCollisions();
//      repaint();
//      Keyinput=true;
//     } 
//     else if(gamemode == false && Stop == true) {repaint();} 
//     else {timer.cancel();}
//    }
//      };
//   timer.scheduleAtFixedRate(task,0,delay);
//  } else {
//   RemovePanel();
//  }
// }
//
// //Draw snake、apple
// public void paintComponent(Graphics g) {
//  super.paintComponent(g);
//  if (VisiableSnake == true) {
//   if(gamemode == true && Stop==false) {
//    //Draw line
//    for(int i = 0 ; i < SCREEN_HEIGHT/UNIT_SIZE ; i++) {
//     g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//     g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//    }
//
//    //Draw apple
//    g.setColor(Color.red);
//    g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
//
//    //Draw snake
//    for(int i = 0; i< bodyParts;i++) {
//     if(i == 0) {
//      g.setColor(Color.green);
//      g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
//     } else {
//      g.setColor(new Color(45,180,0));
//      g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
//     }   
//    }
//
//    //Draw score information
//    g.setColor(Color.red);
//    g.setFont(new Font(null,Font.BOLD, 40));
//    FontMetrics metrics = getFontMetrics(g.getFont());
//    g.drawString("Score: " + score, (SCREEN_WIDTH - metrics.stringWidth("Score: " + score))/2, g.getFont().getSize());
//   }
//   else if (gamemode == false && Stop==true) {
//	   g.setColor(Color.red);
//	   g.setFont( new Font("Times New Roman",Font.BOLD, 60));
//	   FontMetrics metrics2 = getFontMetrics(g.getFont());
//	   g.drawString("Paused", (SCREEN_WIDTH - metrics2.stringWidth("Paused"))/2, SCREEN_HEIGHT/2);
//   }
//   else {
//    gameOver(g);
//   }
//  }
// }
//
// //Check snake eat apple
// public void checkApple() {
//  if((x[0] == appleX) && (y[0] == appleY)) {
//   bodyParts++;
//   score++;
//   newApple();
//  }
// }
//
// //Update apple position
// public void newApple(){
//  appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
//  appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
// }
// 
// //Snake's move and direction
// public void move(){
//  for(int i = bodyParts;i>0;i--) {
//   x[i] = x[i-1];
//   y[i] = y[i-1];
//  }
//  
//  switch(direction) {
//  case 'U':
//   y[0] = y[0] - UNIT_SIZE;
//   break;
//  case 'D':
//   y[0] = y[0] + UNIT_SIZE;
//   break;
//  case 'L':
//   x[0] = x[0] - UNIT_SIZE;
//   break;
//  case 'R':
//   x[0] = x[0] + UNIT_SIZE;
//   break;
//  }
// }
// 
// //Checks if head collides with body
// public void checkCollisions() {
//  for(int i = bodyParts;i>0;i--) {
//   if((x[0] == x[i])&& (y[0] == y[i])) {
//    gamemode = false;
//   }
//  }
//  if(x[0] < 0) {
//   gamemode = false;
//  }
//  if(x[0] >= SCREEN_WIDTH) {
//   gamemode = false;
//  }
//  if(y[0] < 0) {
//   gamemode = false;
//  }
//  if(y[0] >= SCREEN_HEIGHT) {
//   gamemode = false;
//  }
// }
// 
// //Restart information show
// public void gameOver(Graphics g) {
//  //Score
//  g.setColor(Color.red);
//  g.setFont( new Font(null,Font.BOLD, 40));
//  FontMetrics metrics1 = getFontMetrics(g.getFont());
//  g.drawString("Score: " + score, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + score))/2, g.getFont().getSize());
//  //Game Over text
//  g.setColor(Color.red);
//  g.setFont( new Font("Ink Free",Font.BOLD, 75));
//  FontMetrics metrics2 = getFontMetrics(g.getFont());
//  g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
//  gamemode = false;
//  VisiableSnake=false;
//  Initialize();
//  JButton restart = new JButton("返回開始");
//  restart.setBackground(Color.black);
//  restart.setForeground(Color.RED);
//  restart.setFont(new Font("標楷體",Font.CENTER_BASELINE, 30));
//  restart.setBorder(null);
//  restart.setBounds(SCREEN_WIDTH/3+50,SCREEN_HEIGHT/3*2,160,50);
//  restart.setContentAreaFilled(false);
//  restart.addActionListener(new ActionListener() {
//        public void actionPerformed(ActionEvent e)
//        {
//         RemovePanel();
//         StartPanel();
//        }});
//  this.add(restart);
// }
//
// //Start information show
// public void StartPanel() {
//  JButton start1 = new JButton("開始遊玩");
//  this.setLayout(null);
//  this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
//  this.setBackground(new Color(117,111,111));
//  this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
//
//  start1.setBackground(new Color(176,164,164));
//  start1.setFont(new Font("標楷體",Font.CENTER_BASELINE, 30));
//  start1.setBorder(null);
//  start1.setBounds(SCREEN_WIDTH/3+50,SCREEN_HEIGHT/3*2,160,50);
//  start1.addActionListener(new ActionListener() {
//        public void actionPerformed(ActionEvent e)
//        {
//         VisiableSnake=true;
//         gamemode = true;
//         GameTimer();
//         newApple();
//        }});
//  this.add(start1);
// }
//
// //Remove Panel
// public void RemovePanel() {
//  this.removeAll();
//  this.validate();
//  this.repaint();
// }
//}