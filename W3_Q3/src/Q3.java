import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Q3{
	
	public static void main (String[] args){
	DrawingDice dice = new DrawingDice("Rolling Dice");
	dice.init();
	}
	
}

class DrawingDice extends JFrame {
	
	 private Graphics2D gg;
	 private int newNumber;
	 
	 //// The start value shown on the dice.
	 Random randomNumber = new Random();
	 int die = 3; 
	
	public DrawingDice(String title){
		super (title);
		
	}// end of constructor
	
	public void init(){
		
		
		GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
		
		Die mainpanel = new Die();
		mainpanel.setBorder(new LineBorder(Color.BLACK));

		// defining a button and adding mouse listener
		JButton b1 = new JButton("Roll");
		b1.addMouseListener(new MouseAdapter() {
				@Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    newNumber = (int)(Math.random()*6) + 1;
                    mainpanel.updateVal(newNumber);      
				}
			});
		
		
		add(mainpanel, new GBC(0, 0, 20, 4).setFill(GBC.BOTH).setWeight(100, 100).setInsets(10));
        add(b1, new GBC(0, 5, 1, 1).setAnchor(GBC.CENTER).setWeight(100, 0).setInsets(5,10,20,10));
        pack();
		
		
        this.setSize(600, 600);
        this.setLocation(150, 150);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
	}//end of init method
	
	
	class Die extends JPanel{
		
		public Die(){
			
		}
		
        // The paint method draws a blue border and then
        // draws the two dice.
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            gg = (Graphics2D) g;
//            gg.setColor(randomColor);
            gg.setColor( Color.blue );
//            gg.drawRect(200,160,200,200);
            drawDie(g, die, 210, 200);

        }
        void drawDie(Graphics g, int val, int x, int y) {
            // Draw a die with upper left corner at (x,y).  The die is
            // 35 by 35 pixels in size.  The val parameter gives the
            // value showing on the die (that is, the number of dots).
         g.setColor(Color.white);
         g.fillRoundRect(x, y, 150, 150, 20, 20);
         g.setColor(Color.black);
//         g.drawRect(x, y, 150, 150);
         g.drawRoundRect(x, y, 150, 150, 20, 20);
         if (val > 1)  // upper left dot
            g.fillOval(x+07, y+07, 36, 36);
         if (val > 3)  // upper right dot
            g.fillOval(x+107, y+07, 36, 36);
         if (val == 6) // middle left dot
            g.fillOval(x+07, y+57, 36, 36);
         if (val % 2 == 1) // middle dot (for odd-numbered val's)
            g.fillOval(x+57, y+57, 36, 36);
         if (val == 6) // middle right dot
            g.fillOval(x+107, y+57, 36, 36);
         if (val > 3)  // bottom left dot
            g.fillOval(x+07, y+107, 36, 36);
         if (val > 1)  // bottom right dot
            g.fillOval(x+107, y+107, 36,36);
      }
        void updateVal(int newNumber) {
            // Roll the dice by randomizing their values.  Tell the
            // system to repaint the Die, to show the new values.
            // Also, play a clicking sound to give the user more feedback.
         die = newNumber;
//         play(getCodeBase(), "click.au");
         repaint();
      }
		
	}

}
