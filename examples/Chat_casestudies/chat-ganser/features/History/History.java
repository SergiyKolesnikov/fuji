import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * Logs text messages.
 */
public final class History {

    private History() {
    }

    /**
     * Logs the given TestMessage. Fails if setup() hasn't called first.
     *
     * @param msg the text message to log.
     * @throws IOException gets thrown if an io error occurs while writing to
     * the log file.
     */
    public static synchronized void log(final TextMessage msg)
	    throws IOException {

	String logFilePath = System.getProperty("user.home") + "/chat.log";
	File logFile = new File(logFilePath);
	
	BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));

	String time = DateFormat.getDateTimeInstance().format(new Date());

	final String sep = " ; ";

	StringBuilder sb = new StringBuilder();
	sb.append(time);
	sb.append(sep);
	sb.append(msg.getContent());

	System.out.println(sb.toString());
	writer.write(sb.toString());
	writer.newLine();
	writer.flush();
	writer.close();
    }
}
