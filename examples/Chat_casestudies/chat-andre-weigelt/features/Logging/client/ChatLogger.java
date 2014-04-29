package client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import common.TextMessage;

public class ChatLogger {
	private static final Logger LOGGER = Logger.getLogger(ChatLogger.class
			.getName());
	final String LOGFILE = "D:/logfile.txt";

	private FileHandler fileHandler;

	public ChatLogger() {
		super();
		setFormattedFileHandler(LOGFILE);
	}

	public void logMessage(TextMessage msg) {
		LOGGER.info("Von: " + msg.getSender() + " - " + msg.getContent());
	}

	private void setFormattedFileHandler(String file) {
		try {
			fileHandler = new FileHandler(file);
			fileHandler.setFormatter(new Formatter() {

				@Override
				public String format(LogRecord record) {
					// TODO Auto-generated method stub
					long milisec = record.getMillis();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"dd.MM.yyy HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(milisec);

					String date = sdf.format(cal.getTime());

					return date + " -- " + record.getMessage() + "\n";
				}
			});
			;
			LOGGER.addHandler(fileHandler);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
