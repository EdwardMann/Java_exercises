import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class TogglePanel extends JPanel{
	private EvaluationTool gui;
	static JButton apply;
	private SpinnerModel pagesViewedModel;
	private SpinnerModel timeSpentModel;
	private final int maximumTimeBounce = 120; //maximum amount of seconds a bounce can be registered as
	private final int maximumPagesBounce = 10; //maximum amount of pages a bounce can be registered as
	private int defaultTime;
	private int defaultPage;
	
	public TogglePanel(EvaluationTool gui){
		this.gui = gui;
		defaultTime = gui.getMinSeconds();
		defaultPage = gui.getMinPages();
		initPanel();
	}

	void initPanel(){
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Bounce Rate",
			TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		//JComboBox
		String[] bounceRates = new String[]{"Seconds Spent", "Pages Viewed"};
		JComboBox<String> jcb = new JComboBox<String>(bounceRates);
		add(jcb);
		
		//JSpinner
		timeSpentModel = new SpinnerNumberModel(defaultTime, 1, maximumTimeBounce, 1);
		pagesViewedModel = new SpinnerNumberModel(defaultPage, 1, maximumPagesBounce, 1);
		JSpinner spinner = new JSpinner(timeSpentModel);
		centerSpinner(spinner);
		add(spinner);

		//JButton
		apply = new JButton("Apply");
		apply.setEnabled(false);
		add(apply);
		
		//Changes the filters to match the bounce rate specified 
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jcb.getSelectedItem().equals(bounceRates[0])){ //time spent
				    defaultTime = (Integer) spinner.getValue();
                    gui.setMinSeconds(defaultTime);
				}else if(jcb.getSelectedItem().equals(bounceRates[1])){ //pages viewed
					defaultPage = (Integer) spinner.getValue();
                    gui.setMinPages(defaultPage);
				}
				gui.updateKeyMetrics();
			}
		});
		
		
		//ActionListener for JComboBox
		jcb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jcb.getSelectedItem().equals(bounceRates[0])){ //time spent
					defaultTime = (Integer) spinner.getValue();
					spinner.setModel(pagesViewedModel);
				}else if(jcb.getSelectedItem().equals(bounceRates[1])){ //pages viewed
					defaultPage = (Integer) spinner.getValue();
					spinner.setModel(timeSpentModel);
				}
				centerSpinner(spinner);
			}
		});
		
	}
	
	void centerSpinner(JSpinner spinner){
		JFormattedTextField txt = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		txt.setColumns(4);
		txt.setHorizontalAlignment(JTextField.CENTER);
	}
}
