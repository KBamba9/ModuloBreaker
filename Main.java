package moduloBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj = new JFrame(); //object operator JFrame
		MyCanvas gamePlay = new MyCanvas();
		obj.setBounds(10,10,700,600); //setting boundaries
		obj.setTitle("Modulo Breaker"); 
		obj.setResizable(false); //not resizable
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// now have created a JFrame and set its properties
		obj.add(gamePlay); 
		obj.setVisible(true);
	}

	}
