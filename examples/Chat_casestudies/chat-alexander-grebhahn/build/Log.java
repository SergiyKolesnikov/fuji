
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;




public class Log{

	String name = "_default";
	
	String logRoot = "E:\\";
	
	FileWriter logWriter;
	
	public Log(String fileName){
		this.name = fileName;
		
		try {
			logWriter = new FileWriter(new File(logRoot+name+".txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	public void log(Message msg) {
		String content ="";
		Iterator it = msg.getComponents().entrySet().iterator();
		while(it.hasNext()){
			Object entry = it.next();
			content += ((Entry)entry).getKey()+": "+((Entry)entry).getValue()+";  ";
			
		}
		content +="\n";
		try {
			logWriter.append(content);
			logWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}