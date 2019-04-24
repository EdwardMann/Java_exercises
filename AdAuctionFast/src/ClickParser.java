import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class ClickParser implements Runnable {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		
		ArrayList<ClickItem> clickList;
		File clickLog;
		
		ClickParser(File log){
			this.clickLog = log;
			clickList = new ArrayList<ClickItem>();
		}
		
		public void run() {
			CsvParserSettings settings = new CsvParserSettings();
			
			settings.setHeaderExtractionEnabled(true);
			
			CsvParser parser = new CsvParser(settings);
			
			try {
				parser.beginParsing(new FileReader(this.clickLog.getAbsolutePath()));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String[] row;
			System.out.println(
					"Clicks started at: " + (new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())));

			while((row = parser.parseNext()) != null){
				try {
					clickList.add(new ClickItem(sdf.parse(row[0]), row[1], Double.parseDouble(row[2])));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println(
					"Clicks finished at: " + (new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())));
			
		}
		
		public ArrayList<ClickItem> getClicks(){
			return clickList;
		}
		
	}