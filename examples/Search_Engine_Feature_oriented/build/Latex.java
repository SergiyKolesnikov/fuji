package LATEX;



import java.io.*;
import java.util.regex.*;

import javax.swing.text.html.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;


import org.apache.lucene.demo.html.HTMLParser;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;





/**
 * Verarbeitet Tex-Dateien.
 * 
 * Dieser ContentHandler verarbeitet Tex-Dateien. 
 * AuÃerdem wird der Dateititel aus dem title Ã¼bernommen.
 * Einzelne Section Subsection etc werden gesondert ermittelt und extrahiert.
 * 
 * @author Mr. Pink
 */
public class Latex extends ContentHandler {
	//Structure Patern
	//****************
	//\part
	//\chapter
	//\section
	//\subsection
	//\subsubsection
	//\paragraph
	//\subparagraph
	private static Pattern partPattern        = Pattern.compile("\\\\part[^{]*\\{", Pattern.DOTALL);
	private static Pattern chapterPartner     = Pattern.compile("\\\\chapter[^{]*\\{", Pattern.DOTALL);
	private static Pattern sectionPartner     = Pattern.compile("\\\\[sub]*section[^{]*\\{", Pattern.DOTALL);
	private static Pattern paragraphPattern   = Pattern.compile("\\\\[sub]*paragraph[^{]*\\{", Pattern.DOTALL);
	private static Pattern[] structurePattern = new Pattern[]{partPattern, chapterPartner, sectionPartner, paragraphPattern};
	
	//TitlePattern
	private static Pattern titlePattern       = Pattern.compile("\\\\title[^\\{]*\\{", Pattern.DOTALL);
	
	//Elemente die entfernt werden sollen
	private String[] remove                   = new String[]{
		"frame",
		"block",
		"itemize",
		"enumerate",
	    "minipage",
	    "document"
	};
	private static Pattern simplecommand      = Pattern.compile("\\\\.[a-z]*[*]?");
	private static Pattern commandAndOption   = Pattern.compile("\\\\.[a-z]*\\[^]]\\]");
	
	
	private static Pattern comment            = Pattern.compile("%.*[\\n,\\r\\n]");
	private static Pattern emptyBr            = Pattern.compile("\\{[ ]*\\}"); 
	private static Pattern signs              = Pattern.compile("[\\[,\\],\\{,\\}]");

	
	/**
	 * Ermittelt die von diesem CH gesetzten indexierten Felder
	 * 
	 * @return  die Namen der Felder
	 */
	public String[] getIndexedFields() {
		return new String[] {"content", "structure", "title"};
	}

	/**
	 * Fragt ab, ob die Klasse fÃ¼r die Ã¼bergebene Datei zustÃ¤ndig ist.
	 * 
	 * @param  filename  der Dateiname ohne Pfad
	 * @return           true, falls ja, sonst false
	 */
	public boolean handles(String filename) {
		return filename.endsWith(".tex");
	}
	
	/**
	 * Wurde das Dokument mit diesem ContentHandler erstellt?
	 * 
	 * @param  doc  das Dokument
	 * @return      true, falls ja (type = tex), sonst false
	 */
	public static boolean isOwnerOfDocument(Document doc) {
		return doc.getField("type").stringValue().equals("tex");
	}

	/**
	 * Indexieren einer TEX Datei.
	 * 
	 * That's where the magic happens. Hier wird eine TEX-Datei eingelesen und
	 * schlussendlich dem Index hinzugefÃ¼gt. Dabei werden Befehle und Ã¼berflÃ¼ssige Elemente 
	 * entfernt. Dementsprechend wird nur der textuelle Inhalt der Datei indexiert.
	 * 
	 * @param filename  Pfad der zu indexierenden Datei
	 * @param writer    der zu verwendende IndexWriter
	 * @return          true wenn die Datei fehlerfrei indexiert werden konnte, sonst false
	 */
	public boolean index(String filename, IndexWriter writer) {
		try {
			String content = Latex.getFileContents(filename);
			

			Document docs = new Document();
			docs.add(indexStructure(new String(content)));
			
			docs.add(new Field("type", "tex", Field.Store.YES, Field.Index.NOT_ANALYZED, Field.TermVector.YES));
			
			String[] splitted = titlePattern.split(new String(content));
			if(splitted.length > 1){
				String title = getContent(splitted[1]);
				docs.add(new Field("title", title, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
			}else{
				docs.add(new Field("title", "Unbenanntes Dokument", Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
			}
			
			content = simplecommand.matcher(content).replaceAll("");
			content = commandAndOption.matcher(content).replaceAll("");
			content = comment.matcher(content).replaceAll("");
			
			for(int rem = 0 ; rem < remove.length ; rem++ ){
					content = content.replaceAll(remove[rem], "");
			}
			content = emptyBr.matcher(content).replaceAll("");
			content = signs.matcher(content).replaceAll("");
			content.trim();
			
			docs.add(new Field("content", content, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));

			ContentHandler.addDefaultFields(docs, filename);
			writer.addDocument(docs);
			return true;

		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
	}
	
	
	/**
	 * Ermittelt die Gliederunselemente der Tex-Datei.
	 * 
	 * Hier werden die Gliederunselemente einer TEX-Datei ermittelt und schlussendlich dem Index hinzugefÃ¼gt. 
	 * Dabei werden Befehle und Ã¼berflÃ¼ssige Elemente entfernt. Dementsprechend wird nur der 
	 * textuelle Inhalt indexiert.
	 * 
	 * @param txt  kompletter zu analysierender Dateiinhalt
	 * @return     structure-Field in dem die Gliederungsinhalte gespeichert wurden
	 */
	public Field indexStructure(String txt) {
		String copy;
		String insert = "";
		for (int pattern = 0 ; pattern < structurePattern.length ; pattern++){
			copy = new String(txt);
			String[] splitted = structurePattern[pattern].split(copy);
				
			for(int i=1; i < splitted.length ; i++){
				String content = getContent(splitted[i]);
				insert += " " + content;
			}
		}
		
		return new Field("structure",  insert, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES);
	}
	
/*	
	public static String readFile(String filename){
		String text = "";
		try {
			BufferedReader input = new BufferedReader(new FileReader(filename));
			String line;
			
			while ((line = input.readLine()) != null) {
				text = text + '\n' + line;
			}
			if (text.endsWith("\n")){
				text = text + "\n";
			}
			
			input.close();
		
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
		return text;
	}
	*/
	
	/**
	 * Ermittelt ersten Parameters eines Befels (zb \subsection{inhalt})
	 * 
	 * Hier wird der erste Parameter eines Befehls ermittelt. Dabei wird die zugehÃ¶rige 
	 * schlieÃende Klammer ermittelt und Ã¼berflÃ¼ssige beinhaltende Befehle entfernt.
	 * 
	 * @param str  zu analysierende Sequenz
	 * @return     1-Parameter-Inhalt der Ã¼bergebenen Sequenz 
	 */
	public String getContent(String str){
		int closeBr = str.indexOf("}");
		int openBr  = str.indexOf("{");
		
		if(closeBr < openBr){
			return str.substring(0, closeBr);
		}
		else{
			String tmp = str;
			while((closeBr > openBr) && (openBr > 0) && (closeBr > 0)){
				tmp = tmp.substring((closeBr + 1));
				
				closeBr = tmp.indexOf("}");
				openBr  = tmp.indexOf("{");
			}
			
			str = str.substring(0,closeBr);
			
			str = simplecommand.matcher(str).replaceAll("");
			str = signs.matcher(str).replaceAll("");
			return str;
		}
	}
}