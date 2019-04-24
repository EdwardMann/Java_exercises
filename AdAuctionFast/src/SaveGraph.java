import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveGraph {
	Container c;
	
	public SaveGraph (Container c){
		this.c = c;
		initSave();
	}
	
	private void initSave(){
		JFileChooser jfc = new JFileChooser("Save");
        jfc.setFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));
        jfc.showSaveDialog(null);
        BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(),BufferedImage.SCALE_SMOOTH);
        c.paint(im.getGraphics());
        File file = jfc.getSelectedFile();
        if (file == null){
            return;
        }
        String path = file.getPath()+".png";
        try {
            ImageIO.write(im, "PNG", new File(path));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
	}
}
