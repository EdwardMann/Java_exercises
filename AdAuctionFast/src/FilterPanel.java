import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

public class FilterPanel extends JPanel {
	
	Date startDate, endDate;
	
	JCheckBox less24, to34, to44;
	JCheckBox to54, plus55;

	JCheckBox male;
	JCheckBox female;

	JCheckBox low;
	JCheckBox med;
	JCheckBox high;

	JCheckBox shopping;
	JCheckBox blog;
	JCheckBox news;
	JCheckBox social;

	JButton apply, reset;
	UtilDateModel model, model2;

	JDatePickerImpl datePicker, datePicker2;
//	SimpleDateFormat parser = new SimpleDateFormat("yyyy/MM/dd");
	
	ArrayList<JCheckBox> ageGroup, genderGroup, incomeGroup, contextGroup;
	EvaluationTool gui;

	FilterPanel(EvaluationTool gui){
		this.gui = gui;
		instantiateVars();
		init();
	}

	void instantiateVars(){
		//Predefined dates in case none selected
		startDate = new GregorianCalendar(2015,01,01).getTime();
		endDate = new GregorianCalendar(2015,04,01).getTime();
		
		// Age Group
		less24 = new JCheckBox("<25");
		to34  = new JCheckBox("25-34");
		to44 = new JCheckBox("35-44");
		to54 = new JCheckBox("45-54");
		plus55 = new JCheckBox(">54");
		
		ageGroup = new ArrayList<JCheckBox>();
		ageGroup.add(less24);
		ageGroup.add(to34);
		ageGroup.add(to44);
		ageGroup.add(to54);
		ageGroup.add(plus55);

		male = new JCheckBox("Male");
		female = new JCheckBox("Female");
		
		genderGroup = new ArrayList<JCheckBox>();
		genderGroup.add(male);
		genderGroup.add(female);

		low = new JCheckBox("Low");
		med = new JCheckBox("Medium");
		high = new JCheckBox("High");
		
		incomeGroup = new ArrayList<JCheckBox>();
		incomeGroup.add(low);
		incomeGroup.add(med);
		incomeGroup.add(high);

		shopping = new JCheckBox("Shopping");
		blog = new JCheckBox("Blog");
		news = new JCheckBox("News");
		social = new JCheckBox("Social");
		
		contextGroup = new ArrayList<JCheckBox>();
		contextGroup.add(shopping);
		contextGroup.add(blog);
		contextGroup.add(news);
		contextGroup.add(social);


		apply = new JButton("Apply");
		apply.setEnabled(false);
		apply.setPreferredSize(new Dimension(20,20));
		apply.addActionListener(new UpdateListener(FilterPanel.this));
		reset = new JButton("Reset");
		reset.setPreferredSize(new Dimension(20,20));
		
		reset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					reset();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});

		// defining date picker objects
		model = new UtilDateModel();
		model2 = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
	}

	void init(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		GridBagLayout layout = new GridBagLayout();
//		setLayout(layout);
		JXTaskPaneContainer taskPane = new JXTaskPaneContainer();
		JXTaskPane age = new JXTaskPane("Age");
		JXTaskPane gender = new JXTaskPane("Gender");		
		JXTaskPane income = new JXTaskPane("Income");		
		JXTaskPane context = new JXTaskPane("Context");	
		JXTaskPane dateFilter = new JXTaskPane("Date");
		JLabel label1 = new JLabel("Select Start Date:");
		JLabel label2 = new JLabel("Select End Date:");

		gender.setLayout(layout);
		income.setLayout(layout);
		context.setLayout(layout);
		age.setLayout(layout);
		dateFilter.setLayout(layout);
		

		age.add(less24,new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		age.add(to34,new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		age.add(to44,new GBC(0, 1).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		age.add(to54,new GBC(1, 1).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		age.add(plus55,new GBC(0, 2).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		
		gender.add(male,new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		gender.add(female,new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		
		income.add(low,new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		income.add(med,new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		income.add(high,new GBC(0, 1).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		
		context.add(shopping,new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		context.add(news,new GBC(0, 1).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		context.add(social,new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		context.add(blog,new GBC(1, 1).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 0, 0));
		
		dateFilter.add(label1,new GBC(0, 0).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(10, 0, 0, 0));
		dateFilter.add(datePicker,new GBC(0, 1).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 5, 0));
		dateFilter.add(label2,new GBC(0, 2).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(10, 0, 0, 0));
		dateFilter.add(datePicker2,new GBC(0, 3).setFill(GBC.HORIZONTAL).setWeight(0.01, 0.25).setInsets(0, 0, 5, 0));
		
		taskPane.add(age);
		taskPane.add(gender);
		taskPane.add(income);
		taskPane.add(context);
		taskPane.add(dateFilter);
		
		taskPane.add(apply);
		taskPane.add(reset);

		add(taskPane);
	}
	
	public Border drawborder(String titleName) {
		TitledBorder title;
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		title = BorderFactory.createTitledBorder(loweredetched, titleName);
		title.setTitleJustification(TitledBorder.LEFT);
		title.setTitleFont(new Font("Verdana", Font.BOLD, 12));
		return title;
	}

	   public Date getChosenStartDate() {
	        if (model.getValue() != null) {
	            Calendar date = Calendar.getInstance();
	            date.set(Calendar.YEAR, model.getYear());
	            date.set(Calendar.MONTH, model.getMonth());
	            date.set(Calendar.DAY_OF_MONTH, model.getDay());
	            return date.getTime();
	        } else
	        	return null;
	    }

	    public Date getChosenEndDate() {
	    	if (model2.getValue() != null) {
	            Calendar date = Calendar.getInstance();
	            date.set(Calendar.YEAR, model2.getYear());
	            date.set(Calendar.MONTH, model2.getMonth());
	            date.set(Calendar.DAY_OF_MONTH, model2.getDay());
	            return date.getTime();
	        } else 
	            return null;
	    }
	    
	public ArrayList<String> getChosenContexts() {
			return getSelectedButtonText(contextGroup);
	}

	public ArrayList<String> getChosenIncomes() {
			return getSelectedButtonText(incomeGroup);
	}

	public ArrayList<String> getChosenAges() {
			return getSelectedButtonText(ageGroup);
	}

	public ArrayList<String> getChosenGenders() {
			return getSelectedButtonText(genderGroup);
	}
	
	public void reset() throws ParseException{
		clearSelected(contextGroup); clearSelected(genderGroup);
		clearSelected(incomeGroup); clearSelected(ageGroup);
		if(gui.dataLoaded != false){
			gui.resetLogs();
			gui.updateGraphs();
			gui.updateKeyMetrics();
		}
	}

	public void clearSelected(ArrayList<JCheckBox> buttonGroup){
	    for (JCheckBox box : buttonGroup){
	        box.setSelected(false);
	    }
	}
	
	public ArrayList<String> getSelectedButtonText(ArrayList<JCheckBox> buttonGroup) {
	    ArrayList<String> selected = new ArrayList<String>();
        for (JCheckBox box : buttonGroup) {
            if (box.isSelected()) {
                selected.add(box.getText());
            }
        }
        return selected;
    }

    // Collects all filter options and updates the graphs and metrics
    class UpdateListener implements ActionListener {

        FilterPanel fPanel;

        public UpdateListener(FilterPanel fPanel) {
            this.fPanel = fPanel;
        }
        
        public void actionPerformed(ActionEvent e) {
            Date startDate = fPanel.getChosenStartDate();
            Date endDate = fPanel.getChosenEndDate();
            ArrayList<String> gender = fPanel.getChosenGenders();
            ArrayList<String> ageGroup = fPanel.getChosenAges();
            ArrayList<String> income = fPanel.getChosenIncomes();
            ArrayList<String> context = fPanel.getChosenContexts();
            
            Filter filter = new Filter(startDate, endDate, ageGroup, gender, income, context);
            
            gui.filterLogs(filter);

            gui.updateKeyMetrics();
			try {
				gui.updateGraphs();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
        }
    }

    class DateLabelFormatter extends AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}
