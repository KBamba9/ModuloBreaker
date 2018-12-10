package moduloBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.Timer;
import sun.audio.*;
import javax.swing.JPanel;

public class MyCanvas extends JPanel implements KeyListener, ActionListener {
	
	private boolean play = false;
	private int score = 0; 
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 4;
	 
	private int playerX = 350;
	
	private int ballposX = 420;
	private int ballposY = 310;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	InputStream in;
	AudioStream as;
	
	private MapGenerator map;

	private boolean menu = true;
	private boolean english = false;
	private boolean spanish = false;
	
	private boolean LevelTwo = false;
	private boolean LevelOneOver = false;
	private boolean LevelTwoOver = false;
	private boolean LevelThree = false;
	private boolean LevelThreeOver = false;
	
	
	public MyCanvas() {
		map = new MapGenerator(3,7);
		addKeyListener(this); 
		setFocusable(true); 
		setFocusTraversalKeysEnabled(false);
		//This is your implied object.  What this means is our class here is called Gameplay and you are calling the instance gameplay that is created.  This only works
		//Because you have implemented ActionListener, which means you have a ActionPerformed methods
		timer = new Timer(delay, this);  
		timer.start();
		playIt("files/soundtrack.wav");
	}
	
	public void playIt (String soundtrack) {
		try {
			//InputStream in = new FileInputStream(soundtrack);
			in = new FileInputStream(soundtrack);
			//AudioStream as = new AudioStream(in);
			as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void playIT (String soundeffect) {
		try {
			InputStream in = new FileInputStream(soundeffect);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	

	public void paint(Graphics g) { //draw different shapes (slider, ball, tiles)
		//menu
		if (menu == true && english == false && spanish == false) {
			g.setColor(Color.black);
			g.fillRect(1,1,700,600);
			
			g.setColor(Color.blue);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("MODULO BREAKER", 165,50);
			
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 40)); //setting font properties
			g.drawString("Press Left or Right Arrow Key to Start!", 10, 300);
			g.drawString("Hit Shift to Restart Game", 130, 500);
			
			g.setColor(Color.blue);
			g.drawRect(120, 375, 135, 30);
			
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("English = 1", 130, 400);
			
			g.setColor(Color.blue);
			g.drawRect(370, 375, 145, 30);
			
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Español = 3", 380, 400);
			
			
		
		} else if (menu == true && english == true) {
			g.setColor(Color.blue);
			g.fillRect(1,1,700,600);
			
			g.setColor(Color.black);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("MODULO BREAKER", 165,50);
			
			g.setColor(Color.black);
			g.setFont(new Font("serif", Font.BOLD, 40)); //setting font properties
			g.drawString("Press Left or Right Arrow Key to Start!", 10, 300);
			g.drawString("Hit Shift to Restart Game", 130, 500);
			
			g.setColor(Color.white);
			g.drawRect(290, 375, 100, 30);
			
			g.setColor(Color.black);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("English", 300, 400);

		
		} else if (menu == true && spanish == true) {
			g.setColor(Color.white);
			g.fillRect(1,1,700,600);
			
			g.setColor(Color.blue);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("MODULO BREAKER", 165,50);
			
			g.setColor(Color.black);
			g.setFont(new Font("serif", Font.BOLD, 27)); //setting font properties
			g.drawString("¡Pulse la Tecla de Flecha Izquierda o Derecha para Iniciar!",5, 300);
			g.drawString("¡Pulse Mayús para Reiniciar el Juego!", 120, 500);
			
			g.setColor(Color.blue);
			g.drawRect(290, 375, 100, 30);
			
			g.setColor(Color.black);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Español", 300, 400);
		}
		
		else {
		// background
		g.setColor(Color.white);
		g.fillRect(1, 1, 697, 592);
		
		//drawing map
		map.draw((Graphics2D)g); 
		
		// borders
		g.setColor(Color.blue);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(697, 0, 3, 592);
		//not adding a border at the bottom side because we want the game to end when the ball hits the ground
		
		// scores
		
		g.setColor(Color.blue);
		g.setFont(new Font("serif", Font.BOLD, 25)); //setting font properties
		g.drawString("Score/Puntaje = "+score, 450, 30);

		
		
		// the paddle
		g.setColor(Color.blue);
		g.fillRect(playerX, 550, 100, 15);
		
		// the ball
		g.setColor(Color.blue);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if(totalBricks <= 0 && LevelOneOver == true) { // When Level One is over
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.blue);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("7 + 3 [Mod 4]?", 230,200);
				
			g.setFont(new Font("serif", Font.BOLD, 25));	
			g.drawString("Type in Your Answer!/Escriba Su Respuesta!", 100,250);
		}
		
		if(totalBricks <= 0 && LevelTwoOver == true) { // When Level Two is over
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.blue);
			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("2 - 5 [Mod 8]?", 230,200);
				
			g.setFont(new Font("serif", Font.BOLD, 25));	
			g.drawString("Type in Your Answer/Escriba Su Respuesta!", 100,250);
		}
		if(totalBricks <= 0 && LevelThreeOver == true) { // When Level Three is over
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setFont(new Font("serif", Font.BOLD, 45));	
			g.drawString("You Win!/Tú Ganas!", 100,250);
		}
		
		if(ballposY >= 580) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.blue);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Game Over!/Fin Del Juego!", 200,300);
				
			g.setFont(new Font("serif", Font.BOLD, 25));	
			g.drawString("Press Enter to Restart!/Pulse Intro para Reinicir!", 75,250);
		}	
		g.dispose();	
	}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//timer.start(); // will be automatically called
		if(play) {
			play = true;
			menu = false;
			
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 15))) { //intersection with paddle
				ballYdir = -ballYdir;
				
			}
			
		 for(int r = 0; r < map.map.length; r++) {
				for(int c = 0; c < map.map[0].length; c++) { // first map is line 32 of this class, second map is line 9 of MG class
					if(map.map[r][c] > 0) {
						int brickX = c * map.brickWidth + 80 ;
						int brickY = r * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
		
						if(ballRect.intersects(brickRect)) { // ball's intersection with bricks
							playIT("files/soundeffect.wav");
							map.setBrickValue(0,r,c);
							totalBricks--;
							score += 5;
							ballXdir = -1;
							ballYdir = -ballYdir;
														
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) 
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
					}
				}
			}
			ballposX += ballXdir;
			ballposY += ballYdir;
			
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY > 0) {
				ballYdir = -ballYdir;
		}
			if(ballposX > 670) {
				ballXdir = -ballXdir;
			}
		LevelOneOver = true;
		}
		
		
		if(play && LevelTwo == true) {
			LevelTwo = true;
				if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 15))) { //intersection with paddle
				ballYdir = -ballYdir;
			}
			
				for(int r = 0; r < map.map.length; r++) {
					for(int c = 0; c < map.map[1].length; c++) { // first map is line 32 of this class, second map is line 9 of MG class
						if(map.map[r][c] > 0) {
							int brickX = c * map.brickWidth + 80 ;
							int brickY = r * map.brickHeight + 50;
							int brickWidth = map.brickWidth;
							int brickHeight = map.brickHeight;
							
							Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
							Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
			
							if(ballRect.intersects(brickRect)) { // ball's intersection with bricks
								map.setBrickValue(0,r,c);
								totalBricks--;
								
								score += 10;
								ballXdir = -1;
								ballYdir = -3;
								
								
								if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) 
									ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
								}
						}
					}
				}
				
				ballposX += ballXdir;
				ballposY += ballYdir;
				if(ballposX < 0) {
					ballXdir = -ballXdir;
				}
				if(ballposY > 0) {
					ballYdir = -ballYdir;
			}
				if(ballposX > 670) {
					ballXdir = -ballXdir;
				}
				LevelTwoOver = true;
			}
			
		if(play && LevelThree == true) {
			LevelThree = true;
			LevelTwo = false;
				if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 15))) { //intersection with paddle
				ballYdir = -ballYdir;
			}
			
				for(int r = 0; r < map.map.length; r++) {
					for(int c = 0; c < map.map[1].length; c++) { // first map is line 32 of this class, second map is line 9 of MG class
						if(map.map[r][c] > 0) {
							int brickX = c * map.brickWidth + 80 ;
							int brickY = r * map.brickHeight + 50;
							int brickWidth = map.brickWidth;
							int brickHeight = map.brickHeight;
							
							Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
							Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
			
							if(ballRect.intersects(brickRect)) { // ball's intersection with bricks
								map.setBrickValue(0,r,c);
								totalBricks--;
								
								score += 20;
								ballXdir = -1;
								ballYdir = -4;
								
								
								if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) 
									ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
								}
						}
					}
				}
				
				ballposX += ballXdir;
				ballposY += ballYdir;
				if(ballposX < 0) {
					ballXdir = -ballXdir;
				}
				if(ballposY > 0) {
					ballYdir = -ballYdir;
			}
				if(ballposX > 670) {
					ballXdir = -ballXdir;
				}
				LevelThreeOver = true;
			}
		
			repaint(); // will recall the whole paint method
		}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
		

	@Override
	public void keyPressed(KeyEvent e) { // detects when the right and left keys are pressed
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
		menu = false;
			if(playerX >= 600) {
				playerX = 600;
			} else {
				moveRight(); 
			}
			AudioPlayer.player.stop(as);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			menu = false;
			if(playerX <= 0) {
				playerX = 0;
			} else {
				moveLeft(); 
			}
			AudioPlayer.player.stop(as);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_1) {
			menu = true;
			english = true;
			spanish = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_3) {
			menu = true;
			spanish = true;
			english = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			AudioPlayer.player.stop(as);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				LevelTwo = false;
				LevelThree = false;
				ballposX = 420;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				delay = 4;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				repaint();
			}
		}
	
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
			if(play) {
				play = true;
				LevelTwo = false;
				LevelThree = false;
				ballposX = 420;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				delay = 4;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				repaint();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_2) {
			if(!play) {
				play = true;
				LevelTwo = true;
				LevelThree = false;
				ballposX = 420;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -3;
				playerX = 310;
				delay = 3;
				score = 155;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				repaint();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_5) {
			if(!play) {
				play = true;
				LevelThree = true;
				LevelTwo = false;
				ballposX = 420;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -4;
				playerX = 415;
				delay = 2;
				score = 310;
				totalBricks = 18;
				map = new MapGenerator(3,6);
				repaint();
			}
		}
	}
	
	public void moveRight() {
		play = true;
		playerX += 40;
		
		if (LevelTwo == true) {
			playerX += 30;
		}
		if (LevelThree == true) {
			playerX += 20;
		}
	}
	public void moveLeft() {
		play = true;
		playerX -= 40;
		
		if (LevelTwo == true) {
			playerX -= 30;
		}
		if (LevelThree == true) {
			playerX -= 20;
		}
	}

} //this class will be the panel in which we run the game (in JFrame), KeyListener moves the pad and ActionListener moves ball
