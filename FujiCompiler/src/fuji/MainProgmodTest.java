package fuji;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.ParseException;

public class MainProgmodTest {
    public static void main(String[] args) {

        /*
         * Definiere ganz normal fuji-Optionen und gebe unbedingt "-progmode"
         * an. Der Pfad zur feautures-Datei muss weggelassen werden.
         */
        String[] fujiOptions = new String[] { "-progmode", "-basedir",
                "examples/GPL", "-d", "examples/GPL/classes" };

        /*
         * Stelle die Liste von Features zusammen, die Komponiert werden sollen.
         */
        List<String> featuresList = new ArrayList<String>();
        featuresList.add("Base");
        featuresList.add("UndirectedWithEdges");
        featuresList.add("WeightedWithEdges");
        featuresList.add("DFS");
        featuresList.add("MSTKruskal");
        featuresList.add("Cycle");
        featuresList.add("Connected");
        featuresList.add("Number");
        featuresList.add("TestProg");

        try {
            /*
             * Initialisiere eine Instanz von Main mit den Optionen und der
             * Featureauswahl.
             */
            Main m = new Main(fujiOptions, featuresList);
            
            /* Bereite die composition vor. */
            Composition composition = m.getComposition(m);
            
            /* Führe die composition durch. */
            m.processAST(composition);
        } catch (WrongArgumentException e) {

        } catch (ParseException e) {

        } catch (IOException e) {

        } catch (FeatureDirNotFoundException e) {

        } catch (SyntacticErrorException e) {

        } catch (SemanticErrorException e) {

        } catch (CompilerWarningException e) {

        } catch (CompositionErrorException e) {

        }
    }
}
