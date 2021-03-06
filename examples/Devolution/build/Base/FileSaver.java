package Base;/* Java */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
/* JDOM */
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;



/**
 * Ein Visitor um einheitlich Dateien zu speichern.
 * 
 * @author Marcel Jaeschke
 */
public class FileSaver {
	/**
	 * Sichert ein XML-Dokument in einer Datei und legt, wenn gewünscht, die
	 * Dateistruktur auf.
	 * 
	 * @param xml Das XML-Dokument.
	 * @param file Die Zieldatei.
	 * @param mkdir Flag welches angibt ob Verzeichnisse angelegt werden sollen,
	 *          wenn sie fehlen.
	 * @return TRUE wenn die Datei erfolgreich gesichert werden konnte, ansonsten
	 *         FALSE.
	 * @throws SaveException Is thrown if there was an error during a saving
	 *           process.
	 */
	public static boolean save ( Document xml, File file, boolean mkdir ) throws SaveException {
		XMLOutputter outputter = new XMLOutputter( Format.getRawFormat() );
		try {
			if ( file.isDirectory() ) { throw new SaveException( String.format( "The passed path doesn't point to a file.\n['%s']\n", file ) ); }
			outputter.output( xml, new FileWriter( file ) );
			return true;
		} catch ( FileNotFoundException e ) {
			if ( mkdir ) {
				file.getParentFile().mkdirs();
				// System.out.printf( "%s: The structure of the file system was
				// created.\n", file );
				return FileSaver.save( xml, file, false );
			}
			throw new SaveException( String.format( "The file doesn't exist and can't be created.\n['%s']\n", file ) );
		} catch ( Exception e ) {
			throw new SaveException( String.format( "There was an error during a saving process.\n['%s']\n.\n['%s']\n", file ) );
		}
	}
}