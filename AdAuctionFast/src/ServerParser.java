import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class ServerParser implements Runnable {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	ArrayList<ServerItem> serverList;
	
	File serverLog;
	
	ServerParser(File log){
		this.serverLog = log;
		serverList = new ArrayList<>();
	}
	@Override
	public void run() {
		CsvParserSettings settings = new CsvParserSettings();
		
		settings.setHeaderExtractionEnabled(true);
		
		CsvParser parser = new CsvParser(settings);
		
		try {
			parser.beginParsing(new FileReader(this.serverLog.getAbsolutePath()));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		String[] row;
		System.out.println(
				"Servers started at: " + (new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())));

		while((row = parser.parseNext()) != null){
			if(row[2].equals("n/a")){
				try {
					serverList.add(new ServerItem(sdf.parse(row[0]), row[1], new Date() ,Integer.parseInt(row[3]),row[4]));
				} catch (NumberFormatException | ParseException e) {
					e.printStackTrace();
				}
			} else {
				try {
					serverList.add(new ServerItem(sdf.parse(row[0]), row[1], sdf.parse(row[2]) ,Integer.parseInt(row[3]),row[4]));
				} catch (NumberFormatException | ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println(
				"Servers finished at: " + (new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())));
	}
	
	public ArrayList<ServerItem> getServerList(){
		return serverList;
	}
	
}