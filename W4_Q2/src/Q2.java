import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Q2 {
	
		
		public static void main (String[] args){
		Drawingcircle dice = new Drawingcircle("Drawing Fractal circles");
		dice.init();
		}
		
	}

	class Drawingcircle extends JFrame {
		
		 private Graphics2D gg;

		public Drawingcircle(String title){
			super (title);
		}
		
		public void init(){
			
			
			GridBagLayout layout = new GridBagLayout();
	        setLayout(layout);
			
			Circle mainpanel = new Circle(6);
			mainpanel.setBorder(new LineBorder(Color.BLACK));
			
			// add the panel to the container
			add(mainpanel, new GBC(0, 0, 3, 4).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10));
	        pack();	
			
	        // setting up the display
	        this.setSize(1200, 750);
	        this.setLocation(150, 150);
//	        this.setLocationRelativeTo(null);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setVisible(true);
	        
		}// end of init method

		
		
		class Circle extends JPanel{
			
			int number_of_recursion;
			
			
			public Circle(int recursion_number){

				this.number_of_recursion = recursion_number;
				
			}// end of contractor
			
			protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            gg = (Graphics2D) g;
	            gg.setColor(Color.green);
	           // gg.drawLine(0, 325,2000, 325);
	            
	            DrawingCircles(gg,600,325,200,Color.green,0);
	        }
		// 
			void DrawingCircles(Graphics g, int x, int y, int r,Color color,int currentrecursion){
				
				// increasing the number of recursion
				currentrecursion ++; 
				gg.setColor(color);
				gg.drawOval(x-r,y-r, r*2, r*2);
				gg.fillOval(x-r, y-r, r*2, r*2);
				Color newcolor = color.darker();
				// checking the number of recursion time
				if (currentrecursion < number_of_recursion){
				DrawingCircles(gg,x,y, r/3,newcolor,currentrecursion);
				DrawingCircles(gg,x-(2*r),y, r/3,newcolor,currentrecursion);
				DrawingCircles(gg,x+(2*r),y, r/3,newcolor,currentrecursion);
				}// end of if statement	
			}// end of DrawingCircles
			
		}// end of Circle class
		
}// end of DrawingDice class

