import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.application.Platform;

public class GranPanel extends JPanel {

	JComboBox<String> timeBox;
	EvaluationTool gui;

	GranPanel(EvaluationTool gui){
		this.gui = gui;
		new JPanel(new BorderLayout());
		setVisible(true);
		init();
	}

	void init(){
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Time Granularity",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		String[] timeGranularities = {"Hour", "Day", "Week"};

		timeBox = new JComboBox<String>(timeGranularities);
		timeBox.setSelectedIndex(1);
		timeBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JComboBox<String> cb = (JComboBox) e.getSource();
				final int item = cb.getSelectedIndex();
					switch (item) {
					case 1:
						gui.setSDF("yyyy-MM-dd");
						break;
					case 2:
						gui.setSDF("yyyy-ww");
						break;
					default:
						gui.setSDF("yyyy-MM-dd : HH");
						break;
					}
					
					if(gui.dataLoaded != false){
						try {
							gui.updateGraphs();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
					
			}
		});

		add(timeBox);
	}
}

