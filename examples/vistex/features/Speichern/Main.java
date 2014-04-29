import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;

/**
 * TODO description
 */
public class Main {
	 protected void saveDialog(File f, String originaltext, String purpose) {

         String text = ((originaltext == null) ? (saveTextField()) : (originaltext));
         fileOnStack = f;


         if(f == null && ((!purpose.equals("txt") || currentFilePath == null))) {
             JFileChooser openThis = new JFileChooser();

             if (purpose.equals("txt")) {
                 openThis.setFileFilter(new txtOnlyFileFilter());
             } else {
                 if (purpose.equals("zipped")) {
                     openThis.setFileFilter(new txtZipOnlyFileFilter());
                 } else {
                     openThis.setFileFilter(new HTMLFileFilter());
                 }
             }

             int returnVal = openThis.showSaveDialog(null);
             if (returnVal == JFileChooser.APPROVE_OPTION) {
                 saveDialog(openThis.getSelectedFile(), text, purpose);
             }
             openThis.setVisible(false);
         } else {

             f = ((f == null && purpose.equals("txt")) ? (currentFilePath) : (f));
             String path = f.getAbsolutePath();
             String file_extention = ".txt";

             if (!purpose.equals("txt")) {
                 if (purpose.equals("zipped")) {
                     file_extention = '.' + name_txtzipped;
                 } else {
                     file_extention = ".html";
                 }
             }

             File name = ((f.getName().endsWith(file_extention)) ? (f) : (new File(path.subSequence(0, ((int) (Math.max(path.indexOf('.'), path.length())))) + file_extention)));
             if(!name.exists() || forceFromStack || (autosave && purpose.equals("txt"))) {
                 try {

                     try {
                     	
                         String content = ((purpose.equals("zipped")) ? (StringZipper.zipString(text)) : (text));
                         iointerface.writeFile(name, content);
                         if (purpose.equals("txt")) {
                             currentFilePath = name;
                             autosave = true;
                         }
                         
                         
                         if (purpose.equals("txt")) {
                             setThisTitle(name.getName());
                             if(Utils.getHash(content) != null) current_hash = (Utils.getHash(content)).toString();
                         }
                         
                      } catch (IOException e) {
                         showError((purpose.equals("zipped")) ? (ErrorGZIPw) : (ErrorIOw));
                     }
                     

                     fileOnStack = null;
                     purposeOnStack = null;
                     txtOnStack = null;
                     forceFromStack = false;
                 } catch (iointerface.tooFewSpaceException e) {
                     showError(ErrorTooLessSpacew);
                 }
             } else {
                 fileOnStack = name;
                 purposeOnStack = purpose;
                 txtOnStack = text;
                 forceFromStack = true;
                 showOverwrite(name.getName());
                 
             }
         }

     }

}