import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class SliderPanel extends JPanel {

	JSlider time;

	SliderPanel(){
		new JPanel(new BorderLayout());
		setVisible(true);
		init();
	}

	void init(){
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Time Granularity",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		time = new JSlider(JSlider.HORIZONTAL,0,100,50);
		time.setSize(600, 100);


		//Create the label table
		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put( 0, new JLabel("Day") );
		labelTable.put( 50, new JLabel("Week") );
		labelTable.put( 100, new JLabel("Month") );
		time.setLabelTable( labelTable );
		
		time.setMajorTickSpacing(50);
		time.setPaintTicks(true);
		time.setSnapToTicks(true);
		time.setPaintLabels(true);
		add(time);
	}
}

