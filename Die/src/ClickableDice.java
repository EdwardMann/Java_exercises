/*
       Shows a pair of dice that are rolled when the user clicks on the
       applet.  It is assumed that the applet is 100-by-100 pixels.  A clicking
       sound is played when the dice are rolled.
    */
    
    import java.awt.*;
    import java.awt.event.*;
    import java.applet.*;
    
    public class ClickableDice extends Applet implements MouseListener {
    
       int die1 = 4;  // The values shown on the dice.
       int die2 = 3;
       
    
       public void init() {
              // To initialize the applet, register the applet to listen
              // for mouse events on itself.  Also set a light blue
              // background color.
          addMouseListener(this);
          setBackground( new Color(200,200,255) );
       }
       
    
       public void paint(Graphics g) {
              // The paint method draws a blue border and then
              // draws the two dice.
          g.setColor( Color.blue );
          g.drawRect(0,0,99,99);
          g.drawRect(1,1,97,97);
          drawDie(g, die1, 10, 10);
          drawDie(g, die2, 55, 55);
       }
       
    
       void drawDie(Graphics g, int val, int x, int y) {
             // Draw a die with upper left corner at (x,y).  The die is
             // 35 by 35 pixels in size.  The val parameter gives the
             // value showing on the die (that is, the number of dots).
          g.setColor(Color.white);
          g.fillRect(x, y, 35, 35);
          g.setColor(Color.black);
          g.drawRect(x, y, 34, 34);
          if (val > 1)  // upper left dot
             g.fillOval(x+3, y+3, 9, 9);
          if (val > 3)  // upper right dot
             g.fillOval(x+23, y+3, 9, 9);
          if (val == 6) // middle left dot
             g.fillOval(x+3, y+13, 9, 9);
          if (val % 2 == 1) // middle dot (for odd-numbered val's)
             g.fillOval(x+13, y+13, 9, 9);
          if (val == 6) // middle right dot
             g.fillOval(x+23, y+13, 9, 9);
          if (val > 3)  // bottom left dot
             g.fillOval(x+3, y+23, 9, 9);
          if (val > 1)  // bottom right dot
             g.fillOval(x+23, y+23, 9,9);
       }
    
    
       void roll() {
             // Roll the dice by randomizing their values.  Tell the
             // system to repaint the applet, to show the new values.
             // Also, play a clicking sound to give the user more feedback.
          die1 = (int)(Math.random()*6) + 1;
          die2 = (int)(Math.random()*6) + 1;
          play(getCodeBase(), "click.au");
          repaint();
       }
       
    
       public void mousePressed(MouseEvent evt) {
             // When the user clicks the applet, roll the dice.
          roll();
       }
       
    
       public void mouseReleased(MouseEvent evt) { }
       public void mouseClicked(MouseEvent evt) { }
       public void mouseEntered(MouseEvent evt) { }
       public void mouseExited(MouseEvent evt) { }
       
    
    } // end class ClickableDice