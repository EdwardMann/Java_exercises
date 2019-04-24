import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class Print{
	Container c;
	private Image cachedImage;
	
	public Print(Container graph){
		this.c = graph;
		printGraph();
	}
	
	public double getScaleFactorToFit(Dimension original, Dimension toFit) {
		double dScale = 1d;
		if (original != null && toFit != null) {
			double dScaleWidth = getScaleFactor(original.width, toFit.width);
			double dScaleHeight = getScaleFactor(original.height, toFit.height);

			dScale = Math.min(dScaleHeight, dScaleWidth);
		}
		return dScale;
	}
	public double getScaleFactor(int iMasterSize, int iTargetSize) {
		double dScale = 1;
		if (iMasterSize > iTargetSize) {
			dScale = (double) iTargetSize / (double) iMasterSize;
		} else {
			dScale = (double) iTargetSize / (double) iMasterSize;
		}
		return dScale;
	}
	private void printGraph(){
		
        BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_RGB);
        c.paint(im.getGraphics());

        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(new Printable() {

            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex != 0) {
                    return NO_SUCH_PAGE;
                }
                
                int width = (int)Math.round(pageFormat.getImageableWidth());
                int height = (int)Math.round(pageFormat.getImageableHeight());
               
                double scaleFactor = getScaleFactorToFit(new Dimension(im.getWidth(), im.getHeight()), new Dimension(width, height));

                int imageWidth = (int)Math.round(im.getWidth() * scaleFactor);
                int imageHeight = (int)Math.round(im.getHeight() * scaleFactor);

                cachedImage = im.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);

                graphics.drawImage(cachedImage, 0, 100, imageWidth, imageHeight, null);
                return PAGE_EXISTS;
            }

			
        });
        if(printJob.printDialog()){
            try {
                printJob.print();
            } catch (PrinterException e1) {
                e1.printStackTrace();
            }
        }
	}
}
