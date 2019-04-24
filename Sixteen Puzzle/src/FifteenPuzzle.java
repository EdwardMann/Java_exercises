
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
 
public class FifteenPuzzle extends JPanel {
    final static int numTiles = 15;
    final static int side = 4;
 
    Random rand = new Random();
    int[] tiles = new int[numTiles + 1];
    int tileSize, blankPos, margin, gridSize;
 
    public FifteenPuzzle() {
        final int dim = 640;
 
        margin = 80;
        tileSize = (dim - 2 * margin) / side;
        gridSize = tileSize * side;
 
        setPreferredSize(new Dimension(dim, dim));
        setBackground(Color.white);
        setForeground(new Color(0x6495ED)); // cornflowerblue 
        setFont(new Font("SansSerif", Font.BOLD, 60));
 
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int ex = e.getX() - margin;
                int ey = e.getY() - margin;
                
 
                if (ex < 0 || ex > gridSize || ey < 0 || ey > gridSize)
                    return;
 
                int c1 = ex / tileSize;
                System.out.println(ex);
                System.out.println(tileSize);
                int r1 = ey / tileSize;
                
                int c2 = blankPos % side;
                int r2 = blankPos / side;
                System.out.println("mouse tile column: "+c1);
                System.out.println("blank tile column: "+c2);
                System.out.println("mouse tile row: "+r1);
                System.out.println("blank tile row: "+r2);
                System.out.println("--------------------------");
 
                if ((c1 == c2 && Math.abs(r1 - r2) == 1)
                        || (r1 == r2 && Math.abs(c1 - c2) == 1)) {
 
                	// clickPos represents position of the clicked tile on the grid
                    int clickPos = r1 * side + c1;
                    // swap the blank position with the coloured tile in the array of tiles
                    tiles[blankPos] = tiles[clickPos];
                    tiles[clickPos] = 0;
                    blankPos = clickPos;
                }
                repaint();
            }
        });
 
        shuffle();
    }
 
    final void shuffle() {
        do {
            reset();
            // don't include the blank space in the shuffle, leave it
            // in the home position
            int n = numTiles;
            while (n > 1) {
            	// r is a random number from 0 to n-1 and is used to select the value
            	// of title[r] so it can be swapped with value of tile[n]
            	// taking into consideration that the value of r could be returned the same multiple times
            	// hence, the same position might be used multiple times and swapped with different tile[n]
                int r = rand.nextInt(n--);
                System.out.println("r is: " + r);
                int tmp = tiles[r];
                System.out.println("temp is " + tmp);
                System.out.println("tile[n] is " + tiles[n]);
                tiles[r] = tiles[n];
                tiles[n] = tmp;
                System.out.println("--------------------------");
            }
        } while (!isSolvable());
    }
 
    void reset() {
//    	System.out.println("this is the length of the tile array: "+ tiles.length);
        for (int i = 0; i < tiles.length; i++){
        	tiles[i] = (i + 1) % tiles.length;
//        	System.out.println("the value of position " + i + " in the tile array is: " + tiles[i]);
        }
            
        blankPos = numTiles;
//        System.out.println("this is the return value of numTiles array: "+ numTiles);
//        System.out.println("--------------------------");
        
    }
 
    /*  Only half the permutations of the puzzle are solvable.
 
        Whenever a tile is preceded by a tile with higher value it counts
        as an inversion. In our case, with the blank space in the home
        position, the number of inversions must be even for the puzzle
        to be solvable.
 
        See also:
        www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
    */
    boolean isSolvable() {
        int countInversions = 0;
        for (int i = 0; i < numTiles; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i])
                    countInversions++;
            }
        }
        return countInversions % 2 == 0;
    }
 
    void drawGrid(Graphics2D g) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0)
                continue;
 
            int r = i / side;
            int c = i % side;
            int x = margin + c * tileSize;
            int y = margin + r * tileSize;
 
            g.setColor(getForeground());
            g.fillRoundRect(x, y, tileSize, tileSize, 25, 25);
            g.setColor(Color.black);
            g.drawRoundRect(x, y, tileSize, tileSize, 25, 25);
            g.setColor(Color.white);
 
            drawCenteredString(g, String.valueOf(tiles[i]), x, y);
        }
    }
 
    void drawCenteredString(Graphics2D g, String s, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        int asc = fm.getAscent();
        int dec = fm.getDescent();
 
        x = x + (tileSize - fm.stringWidth(s)) / 2;
        y = y + (asc + (tileSize - (asc + dec)) / 2);
 
        g.drawString(s, x, y);
    }
 
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
 
        drawGrid(g);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Fifteen Puzzle");
            f.setResizable(false);
            f.add(new FifteenPuzzle(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}