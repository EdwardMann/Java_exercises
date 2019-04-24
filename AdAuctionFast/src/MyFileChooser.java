
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFileChooser extends JFileChooser {
	Controller controller;
	EvaluationTool evaluationTool;
	private File clickLog, impressionLog, serverLog;
	
    public MyFileChooser(Controller controller, EvaluationTool evaluationTool, File file) throws InterruptedException, IOException {
    	this.controller = controller;
    	this.evaluationTool = evaluationTool;
    	setCurrentDirectory(file);
    	chooseFiles();
   }
    
    private void chooseFiles() throws InterruptedException, IOException{
    	
		FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV (*.csv)", "csv");
		this.addChoosableFileFilter(csvFilter);
		this.removeChoosableFileFilter(this.getAcceptAllFileFilter());
		this.setMultiSelectionEnabled(true);
		this.setApproveButtonText("Select");
		while (true) {
			int returnVal = this.showOpenDialog(null);
			File[] csvs = this.getSelectedFiles();
			if (csvs.length == 3) {
				try {
					controller.openCSV(csvs[0], csvs[1], csvs[2]);
					evaluationTool.updateKeyMetrics();
					evaluationTool.updateGraphs();
				} catch (IOException ioe) {
					System.err.println(ioe.getMessage());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				evaluationTool.enableButtons();
				break;
			} else if (returnVal == 1) { // if no items were selected
				break;
			} else {
				JOptionPane.showMessageDialog(this, "Please select 3 files");
			}
		}
    }
}
