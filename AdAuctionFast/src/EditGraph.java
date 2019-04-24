import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.sun.prism.paint.Color;

public class EditGraph extends JMenu{
	
	EditGraph(String name, ChartsPanel charts){
		super(name);
		this.charts = charts;
		init();
		applyListeners();
	}
	
	JMenuItem style1, style2, style3, style4, style5;
	ChartsPanel charts;
	
	private void init(){
		style1 = new JMenuItem("Style 1");
		style2 = new JMenuItem("Style 2");
		style3 = new JMenuItem("Style 3");
		style4 = new JMenuItem("Style 4");
		style5 = new JMenuItem("Style 5");

		add(style1);
		add(style2);
		add(style3);
		add(style4);
		add(style5);
		
		//While not functioning
		style1.setEnabled(false);
		style2.setEnabled(false);
		style3.setEnabled(false);
		style4.setEnabled(false);
		style5.setEnabled(false);

	}
	
	private void applyListeners(){
		style2.addActionListener(new StyleListener("Style1.css"));
		style2.addActionListener(new StyleListener("Style2.css"));
		style3.addActionListener(new StyleListener("Style3.css"));
		style4.addActionListener(new StyleListener("Style4.css"));
		style5.addActionListener(new StyleListener("Style5.css"));
	}
	
	class StyleListener implements ActionListener{
		
		String style;

		StyleListener(String style){
			this.style = style;
		}
		
		public void actionPerformed(ActionEvent e) {
			charts.updateChartStyle(style);
		}
		
	}
	
	
}
