
import java.util.*;
import javax.swing.JTabbedPane;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

public class ChartsPanel extends JTabbedPane{
    Map<Metric, JFXPanel> panels = new HashMap<Metric, JFXPanel>();
    EvaluationTool gui;
    
    
    public ChartsPanel(EvaluationTool gui){
    	this.gui = gui;
        for (Metric m : Metric.values()){
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
					 JFXPanel panel = new JFXPanel();
			         panel.setScene(new Scene(GraphFactory.getLineChart(m , null)));
			         panels.put(m, panel);
			         this.addTab(m.toString(), panel);
        	}
        	
        }
        
    }
    
    public void updateChart(Metric metric, Map<String, Double> graphdata){
        JFXPanel panel = panels.get(metric);
        panel.setScene(new Scene(GraphFactory.getLineChart(metric, graphdata)));
    }
    
}