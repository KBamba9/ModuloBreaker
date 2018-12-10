package moduloBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MapGenerator {
	public int map [][]; 
	public int brickWidth;
	public int brickHeight;
	
	public MapGenerator(int row, int col) {
		map = new int[row][col]; // instantiate 2d arrays
		for(int r = 0; r < map.length; r++) {
			for(int c = 0; c < map[1].length; c++) {
				map[r][c] = 1; // 1 detects that this particular brick has not been intersected by the ball
			}
		}
		
		brickHeight = 150/row;
		brickWidth = 540/col; 

	}
	public void draw(Graphics2D g) { //when this method is called it will draw bricks on values of 1
		for(int r = 0; r < map.length; r++) {
			for(int c = 0; c < map[0].length; c++) {
				if(map[r][c] > 0) {
					g.setColor(Color.black);
					g.fillRect(c * brickWidth + 80, r * brickHeight + 50, brickWidth, brickHeight);
					
					((Graphics2D) g).setStroke(new BasicStroke(3));
					g.setColor(Color.white);
					g.drawRect(c * brickWidth + 80, r * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;
	}
}
