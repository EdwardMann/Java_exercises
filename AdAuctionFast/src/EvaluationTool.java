import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class EvaluationTool {

	DecimalFormat fiveDecimals = new DecimalFormat("#.00000");
	DecimalFormat noDecimals = new DecimalFormat("#");
    KeyMetricsPanel metrics;
    FilterPanel filterPanel;
    TogglePanel togglePanel;
    GranPanel granPanel;
    ChartsPanel graphpane;
    
    private JFrame frame;
    private Controller controller;
    boolean dataLoaded;

    /**
     * Create the application.
     */
    public EvaluationTool(Controller controller) {
        this.controller = controller;
        init();
        initJMenu();
        initFilters();
        initGraph();
        initMetrics();
        initToggle();
        dataLoaded = false;
        frame.setVisible(true);
    }


	/**
     * Set up JMenu
     */

    private void initJMenu() {
        // Creates menu bar and adds items to it
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        // Opens a File Chooser to select the 3 csv files
        JMenuItem mnLoadCsvFile = new JMenuItem("Load CSV File");
        mnLoadCsvFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					new MyFileChooser(controller,EvaluationTool.this, new File("/Users/tomivall/Documents/workspace/adauction24/code"));
				} catch (InterruptedException | IOException e1) {
					e1.printStackTrace();
				}
            	dataLoaded = true;
            }
        });
        mnFile.add(mnLoadCsvFile);

        JMenuItem save = new JMenuItem("Save Graph");
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	if(dataLoaded == true){
                    save((Container) graphpane.getSelectedComponent());
            	} else {
            		showError();
            	}
            }
        });
        mnFile.add(save);

        JMenuItem saveKey = new JMenuItem("Save Metrics");
        saveKey.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
            	if(dataLoaded == true){
            		saveMetrics();
            	} else {
            		showError();
            	}
                
            }
        });
        mnFile.add(saveKey);

        // implementing print functionality
        JMenuItem print = new JMenuItem("Print");
        print.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	if (dataLoaded == true){
            		new Print((Container) graphpane.getSelectedComponent());
            	} else {
            		showError();
            	}
            	
            }
        });
        mnFile.add(print);

        JMenu histogram = new JMenu("Histogram");
        JMenuItem createhistogram = new JMenuItem("Create");
        createhistogram.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                if(dataLoaded == true){
                    createHistogram();
                } else {
                    showError();
                }
            }

            
        });
        histogram.add(createhistogram);
        menuBar.add(histogram);
       
        JMenu mnEdit = new JMenu("Edit");
        
        JMenu editGraph = new JMenu("Graph Style");
        
        JMenuItem blue = new JMenuItem("Blue");
        JMenuItem black = new JMenuItem("Black");
        JMenuItem red = new JMenuItem("Red");
        JMenuItem green = new JMenuItem("Green");
        JMenuItem yellow = new JMenuItem("Yellow");
        JMenuItem purple = new JMenuItem("Purple");

        blue.addActionListener(new StyleListener("#00d2ff"));
        black.addActionListener(new StyleListener("#000000"));
        red.addActionListener(new StyleListener("#FF0000"));
        green.addActionListener(new StyleListener("#7FFF00"));
        yellow.addActionListener(new StyleListener("#FFE600"));
        purple.addActionListener(new StyleListener("#9B30FF"));
        
        editGraph.add(blue);
        editGraph.add(black);
        editGraph.add(red);
        editGraph.add(green);
        editGraph.add(yellow);
        editGraph.add(purple);

        mnEdit.add(editGraph);
        menuBar.add(mnEdit);
        
        JMenu help = new JMenu("Help");
        JMenuItem guide = new JMenuItem("Guide");
        guide.addActionListener(new ActionListener(){
            
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.OPEN)) {
                            desktop.open(new File("AdDashboardUserGuide.pdf"));
                        } else {
                            System.out.println("Open is not supported");
                        }} catch (IOException | IllegalArgumentException e1) {
                            System.out.println("Open file failed");
                        }
                }
        });
        help.add(guide);
        menuBar.add(help);
        
    }

    protected void createHistogram() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        JFrame frame = new JFrame("Click Cost Histogram");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(700, 500);
        xAxis.setLabel("Cost Division");
        yAxis.setLabel("Frequency");
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        XYChart.Series series = new XYChart.Series();

        for (Entry<String, Double> entry : controller.getHistogramPairs().entrySet())
            series.getData().add(new XYChart.Data(entry.getKey().concat(" - ").concat(String.valueOf(Integer.parseInt(entry.getKey()) + 1)), entry.getValue()));

        series.setName("Cost Over Cost Division");
        barChart.getData().add(series); 
        barChart.setBarGap(1);
        barChart.setCategoryGap(0);

        Scene scene = new Scene(barChart, 700, 500);
        fxPanel.setScene(scene);
        frame.setVisible(true);
}

    protected void showError() {
    	JOptionPane.showMessageDialog(frame,
			    "You must load the CSV files first",
			    "CSV error",
			    JOptionPane.ERROR_MESSAGE);
	}


	/**
     * Filter panel
     */
    private void initFilters() {
        JPanel background = (JPanel) frame.getContentPane();
        filterPanel = new FilterPanel(EvaluationTool.this);
        background.add(filterPanel, BorderLayout.WEST);
    }

    /**
     * Initialise the contents of the frame.
     */
    private void init() {
        frame = new JFrame();
        frame.setTitle("Evaluation Tool");
        frame.setBounds(0, 0, 1440, 820);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String buttons[] = { "Yes", "No" };
                int result = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, buttons, buttons[1]);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    
    private void initGraph(){
        JPanel background = (JPanel) frame.getContentPane();
        graphpane = new ChartsPanel(EvaluationTool.this);
        background.add(graphpane, BorderLayout.CENTER);
    }
    /**
     * Add the key metrics panel
     */
    private void initMetrics(){
        JPanel background = (JPanel) frame.getContentPane();
        metrics = new KeyMetricsPanel();
        background.add(metrics, BorderLayout.EAST);
    }
    
    //Action listener needs to be added to this
    private void initToggle(){
    	togglePanel = new TogglePanel(this);
    	metrics.add(togglePanel, BorderLayout.SOUTH);

        granPanel = new GranPanel(EvaluationTool.this);
    	metrics.add(granPanel);
    }

   
    
    public void updateKeyMetrics() {
        Map<Metric, Double> metricsMap = controller.getKeyMetrics();
        metrics.getImpressions().setText(noDecimals.format(metricsMap.get(Metric.NumberOfImpressions)));
        metrics.getClicks().setText(noDecimals.format(metricsMap.get(Metric.NumberOfClicks)));
        metrics.getUniques().setText(noDecimals.format(metricsMap.get(Metric.NumberOfUniques)));
        metrics.getBouncesPages().setText(noDecimals.format(metricsMap.get(Metric.NumberOfBouncesPagesViewed)));
        metrics.getBouncesTime().setText(noDecimals.format(metricsMap.get(Metric.NumberOfBouncesTime)));
        metrics.getConversions().setText(noDecimals.format(metricsMap.get(Metric.NumberOfConversions)));
        metrics.getCosts().setText(new DecimalFormat("#.##").format(metricsMap.get(Metric.TotalCost)));
        metrics.getCTR().setText(fiveDecimals.format(metricsMap.get(Metric.CTR)));
        metrics.getCPA().setText(fiveDecimals.format(metricsMap.get(Metric.CPA)));
        metrics.getCPC().setText(fiveDecimals.format(metricsMap.get(Metric.CPC)));
        metrics.getCPM().setText(fiveDecimals.format(metricsMap.get(Metric.CPM)));
        metrics.getBounceRateTime().setText(fiveDecimals.format(metricsMap.get(Metric.BounceRateTime)));
        metrics.getBounceRate().setText(fiveDecimals.format(metricsMap.get(Metric.BounceRatePagesViewed)));
    }

    public void updateGraphs() throws ParseException {
        Map<String, Double> graphdata;
            for (Metric m : Metric.values()) {
            	switch(m){
	        	case CPC:
	        	case CTR:
	        	case CPM:
	        	case BounceRateTime:
	        	case BounceRatePagesViewed:
	        	case CPA:
	        	case NumberOfBouncesPagesViewed:
	        	case NumberOfBouncesTime:
	        		break;
				default:
					graphdata = controller.getGraphData(m);
					graphpane.updateChart(m, graphdata);
            	}
            }
    }
    /**
     * Used to save the container to a BufferedImage which is then saved to a specified path
     * using a file chooser
     * @param c
     */
    private void save(Container c){
    	new SaveGraph(c);

    }
    
    private void saveMetrics(){
    	new SaveMetrics (controller, filterPanel);
    }

	public void enableButtons() {
		filterPanel.apply.setEnabled(true);
		TogglePanel.apply.setEnabled(true);
	}
	
	private void changeStyle(String s){
		for(JFXPanel panel : graphpane.panels.values()){
			Node nodeLine = panel.getScene().lookup(".default-color0.chart-series-line");
			nodeLine.setStyle("-fx-stroke:" + s + ";");
		}
	}

    class StyleListener implements ActionListener {

        String style;

        StyleListener(String style) {
            this.style = style;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            changeStyle(style);
        }
    }


	public void resetLogs() {
		controller.resetLogs();
	}


    public void filterLogs(Filter filter) {
        controller.filterLogs(filter);
        
    }


    public void setSDF(String string) {
        controller.setSDF(string);
    }


    public int getMinSeconds() {
        return controller.getMinSeconds();
    }


    public int getMinPages() {
        return controller.getMinPages();
    }


    public void setMinSeconds(int defaultTime) {
        controller.setMinSeconds(defaultTime);        
    }


    public void setMinPages(int defaultPage) {
        controller.setMinPages(defaultPage);
    }
}
