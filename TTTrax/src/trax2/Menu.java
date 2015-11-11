package trax2;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.sun.javafx.tk.Toolkit.Task;

import sun.util.resources.cldr.es.TimeZoneNames_es_419;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Menu extends JPanel{
	private JButton start;
	private JButton End;
	private JButton re;
	private static TextArea Logmsg;
	private static JLabel Time1;
	private static boolean gamestart=true;
	private static double elapsedTime = 0.00;
	private static boolean isLoop;
	private Timer time;
	
	public Menu(){

		this.setBackground(Color.white);
		this.setLayout(null);
		start = new JButton(new ImageIcon("src/trax2/play.png"));//버튼들 크기 조정 요함
		start.setBackground(Color.white);
		start.setBorderPainted(false);
		
		start.addActionListener(new MenuListeners());
		start.setText("start");
		start.setBounds(30 ,16,81,81);
		add(start);
		
		re = new JButton(new ImageIcon("src/trax2/back.png"));
		re.setBackground(Color.white);
		re.setBorderPainted(false);
		re.addActionListener(new MenuListeners());
		re.setText("re");
		re.setBounds(155, 16, 80, 80);
		add(re);
		
		
		End = new JButton(new ImageIcon("src/trax2/exit.png"));
		End.setBackground(Color.white);
		End.setBorderPainted(false);
		End.addActionListener(new MenuListeners());
		End.setText("End");
		End.setBounds(275, 16, 80, 80);
		add(End);
		
		Logmsg = new TextArea("",0,0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		
		Logmsg.setBounds(25, 100, 350, 400);
		//Logmsg.setPreferredSize(new Dimension(375,500));
		add(Logmsg);
		
		Time1 = new JLabel(elapsedTime + "seconds");
		Time1.setFont(new Font("Arial", Font.BOLD, 36));
		Time1.setHorizontalAlignment(Time1.CENTER);
		Time1.setBounds(25, 500, 350, 100);
		Time1.setPreferredSize(new Dimension(375, 100));
		add(Time1);
		
		//Drawbackground component = new Drawbackground();// 배경화면 왜 적용안되는지..
		//add(component);
	}
	
	public static void writeLog(String str){	//로그기능
		Logmsg.append(str);
		
	}
	
	
	
	
	public static void chagetime(double elapsedt){
		Time1.setText(String.format("%.2f", elapsedt) + " seconds");
		if(elapsedt==10000.0){
			Time1.setText(elapsedTime + " seconds");
		}
	}
	
	
		
	private class MenuListeners implements ActionListener { 		//start end 기능

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand()=="End"){
				
				time.stttop();
				
			}
			if(e.getActionCommand()=="start"){
				Timer time = new Timer();
				Logmsg.setText("start\n");
				time.start();
				Game.startgame();
			}
			if(e.getActionCommand()=="re"){
				Logmsg.setText("restart\n");
				Menu m = new Menu();
			}
			
		}

	}

}

