import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveMetrics {

	Controller controller;
	FilterPanel filterpanel;
	DecimalFormat df = new DecimalFormat("#.00000");
	
	public SaveMetrics(Controller controller, FilterPanel filterpanel){
		this.controller = controller;
		this.filterpanel = filterpanel;
		initSave ();
	}

	private void initSave (){
        Map<Metric, Double> metricsMap = controller.getKeyMetrics();
    	JFileChooser jfc = new JFileChooser("Save");
        jfc.setFileFilter(new FileNameExtensionFilter("TXT (*.txt)", "txt"));
        jfc.showSaveDialog(null);
		try {
			PrintWriter pw = new PrintWriter(jfc.getSelectedFile().getPath()+".txt");
			pw.println(Metric.NumberOfImpressions  + "  "+  (metricsMap.get(Metric.NumberOfImpressions).intValue()));
			pw.println(Metric.NumberOfClicks + "  "+ (metricsMap.get(Metric.NumberOfClicks).intValue()));
			pw.println(Metric.NumberOfUniques  + "  "+ (metricsMap.get(Metric.NumberOfUniques).intValue()));
			pw.println(Metric.NumberOfBouncesPagesViewed+ "  "+ (metricsMap.get(Metric.NumberOfBouncesPagesViewed).intValue()));
			pw.println(Metric.NumberOfBouncesTime  + "  "+ (metricsMap.get(Metric.NumberOfBouncesTime).intValue()));
			pw.println(Metric.NumberOfConversions + "  "+ (metricsMap.get(Metric.NumberOfConversions).intValue()));
			pw.println(Metric.TotalCost  + "   "+ df.format(metricsMap.get(Metric.TotalCost)));
			pw.println(Metric.CTR  + "   "+  df.format(metricsMap.get(Metric.CTR)));
			pw.println(Metric.CPA + "   "+  df.format(metricsMap.get(Metric.CPA)));
			pw.println(Metric.CPC  + "   "+ df.format(metricsMap.get(Metric.CPC)));
			pw.println(Metric.CPM  + "   "+  df.format(metricsMap.get(Metric.CPM)));
			pw.println(Metric.BounceRateTime  + "   "+  df.format(metricsMap.get(Metric.BounceRateTime)));
			pw.println(Metric.BounceRatePagesViewed  + "   "+ df.format(metricsMap.get(Metric.BounceRatePagesViewed)));
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
