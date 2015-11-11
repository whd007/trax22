package trax2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Timer extends Thread {
     private static double elapsedTime = 0.00;
     private static boolean isLoop=true;
    
     /**
      * 타이머 생성자
      */
    public Timer() {
                     
            //start();
            System.out.println("timer ready");
     }
    
    static void stttop(){
    	isLoop = false;
    	elapsedTime = 0.00;
    	 Menu.chagetime(10000.0);
        }
      
     public void run() {
    	 System.out.println("timer running");
      while(true) {
      
       if(isLoop) {
        elapsedTime += 0.01;
            Menu.chagetime(elapsedTime);
       }
            
            try {
                   sleep(10);
            }
            catch (InterruptedException e) {
                   System.out.println("InterruptedException!");
            }
      }
     }
    
    
}