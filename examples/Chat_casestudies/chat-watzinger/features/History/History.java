import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class History {
		private FileWriter writer;
		private SimpleDateFormat formatter = new SimpleDateFormat(
				"dd-MM-yyyy-hh:mm:ss");

		public History(File file) throws IOException {
			writer = new FileWriter(file);
		}

		public synchronized void append(Message msg) {

			try {
				writer.write(formatter.format(Calendar.getInstance().getTime())
						+ ": " + msg.toString() + "\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}