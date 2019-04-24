import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class ImpressionParser implements Runnable {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	ArrayList<ImpressionItem> impressionList;
	
	File impressionLog;
	
	ImpressionParser(File log){
		this.impressionLog = log;
		impressionList = new ArrayList<>(9000000);
	}
	@Override
	public void run() {
		CsvParserSettings settings = new CsvParserSettings();
		
		settings.setHeaderExtractionEnabled(true);
		
		CsvParser parser = new CsvParser(settings);
		
		try {
			parser.beginParsing(new FileReader(this.impressionLog.getAbsolutePath()));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String[] row;
		System.out.println(
				"Impressions started at: " + (new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())));

		while((row = parser.parseNext()) != null){
			try {
				impressionList.add(new ImpressionItem(sdf.parse(row[0]), row[1], row[2], row[3],row[4],row[5], Double.parseDouble(row[6])));
			} catch (NumberFormatException | ParseException e) {
				e.printStackTrace();
			} 
		}
		
		System.out.println(
				"Impressions finished at: " + (new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())));
	}
	
	public ArrayList<ImpressionItem> getImpressions(){
		return impressionList;
	}
	
}