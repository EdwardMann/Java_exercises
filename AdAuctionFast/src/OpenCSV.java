import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Douglas on 22/02/17.
 */
public class OpenCSV {
	private File clickLog, impressionLog, serverLog;
	SimpleDateFormat sdf;

	public OpenCSV(File log1, File log2, File log3) throws IOException, ParseException {
	    // handles files being out of order
		for (File file : new File[] { log1, log2, log3 }) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			if (line.equals("Date,ID,Click Cost")){
				this.clickLog = file;
			}
			else if (line.equals("Date,ID,Gender,Age,Income,Context,Impression Cost")){
				this.impressionLog = file;
			}
			else if (line.equals("Entry Date,ID,Exit Date,Pages Viewed,Conversion")){
				this.serverLog = file;
			}
			else{
			    br.close();
			    throw new IOException("Invalid Column Headers in file: " + file.getName());
			}
			br.close();
		}		

		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		this.loadClickLog();
		this.loadImpressionLog();
		this.loadServerLog();
	}

	private void loadClickLog() throws IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader(this.clickLog));
		String line = br.readLine(); // Reading header, Ignoring

		DataInterface.clicks = new ArrayList<>();

		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split(",");

			Date date = sdf.parse(fields[0]);
			Double id = Double.parseDouble(fields[1]);
			double clickCost = Double.parseDouble(fields[2]);

			DataInterface.clicks.add(new ClickItem(date, id, clickCost));
		}
		DataInterface.clicksBackup = DataInterface.clicks;
		br.close();
	}

	private void loadImpressionLog() throws IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader(this.impressionLog));
		String line = br.readLine(); // Reading header, Ignoring

		DataInterface.impressions = new ArrayList<>();

		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split(",");

			Date date = sdf.parse(fields[0]);
			Double id = Double.parseDouble(fields[1]);
			String gender = fields[2];
			String age = fields[3];
			String income = fields[4];
			String context = fields[5];
			double impressionCost = Double.parseDouble(fields[6]);

			DataInterface.impressions.add(new ImpressionItem(date, id, gender, age, income, context, impressionCost));
		}
		DataInterface.impressionsBackup = DataInterface.impressions;
		br.close();
	}

	private void loadServerLog() throws IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader(this.serverLog));
		String line = br.readLine(); // Reading header, Ignoring

		DataInterface.servers = new ArrayList<>();

		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split(",");

			Date entryDate = sdf.parse(fields[0]);
			Double id = Double.parseDouble(fields[1]);
			Date exitDate = null;
			if (!fields[2].equals("n/a")) {
				exitDate = sdf.parse(fields[2]);
			} else {
				exitDate = sdf.parse("3000-1-01 00:00:00"); // not much has
															// live underwater
			}
			int pagesViewed = Integer.parseInt(fields[3]);
			String conversion = fields[4];

			DataInterface.servers.add(new ServerItem(entryDate, id, exitDate, pagesViewed, conversion));
		}
		DataInterface.serversBackup = DataInterface.servers;
		br.close();
	}
}
