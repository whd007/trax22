package trax2;

import java.awt.*;
import javax.swing.*;

public class Main {
	
	public static void main(String args[]){
		JFrame frame = new JFrame();
		Menu menu = new Menu();
		Game game = new Game();
		
		frame.setSize(new Dimension(1200, 800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// x누르면 꺼지도록 설정
		frame.setTitle("team-4");
		frame.setResizable(false);
		frame.setLayout(null);
		
		menu.setBounds(0, 0, 400, 800);
		frame.getContentPane().add(menu);
		
		game.setBounds(400, 0, 1200, 800);
		frame.getContentPane().add(game);
		
		frame.setVisible(true);
	}

}
