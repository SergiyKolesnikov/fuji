package fuji;

import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;

import org.apache.commons.cli.ParseException;

import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.io.UnsupportedModelException;
import de.ovgu.featureide.fm.core.io.guidsl.GuidslReader;

import AST.Problem;
import AST.Program;

public class MainProgmodTest {
    public static void main(String[] args) {

        /*
         * Definiere fuji-Optionen und gebe unbedingt "-progmode" an. Der Pfad
         * zur feautures-Datei muss weggelassen werden.
         */
        // String[] fujiOptions = new String[] { "-progmode",
        // "-d", "examples/GPL/classes", "-basedir", "examples/GPL"};

        /*
         * Definiere fuji-Optionen und gebe unbedingt "-progmode" an. Der Pfad
         * zur Feauture-Model-Datei muss weggelassen werden.
         */
        // String[] fujiOptions = new String[] { "-progmode", "-noerr",
        // "-compstrategy", "family", "-typechecker", "-basedir",
        // "examples/GPL" };
        String[] fujiOptions = new String[] { "-progmode", "-noerr",
                "-compstrategy", "family", "-typechecker", "-basedir",
                "tests_typechecker/FieldAccess/01" };

        /*
         * Stelle die Liste von Features zusammen, die Komponiert werden sollen.
         */
        // List<String> featuresList = new ArrayList<String>();
        // featuresList.add("Base");
        // featuresList.add("UndirectedWithEdges");
        // featuresList.add("WeightedWithEdges");
        // featuresList.add("DFS");
        // featuresList.add("MSTKruskal");
        // featuresList.add("Cycle");
        // featuresList.add("Connected");
        // featuresList.add("Number");
        // featuresList.add("TestProg");

        /*
         * Lese das Featuremodel ein.
         */
        try {
            // File guidsl_file = new File("examples/GPL/GPL.model");
            File guidsl_file = new File(
                    "tests_typechecker/FieldAccess/01/model.m");
            FeatureModel featureModel = new FeatureModel();
            GuidslReader reader = new GuidslReader(featureModel);
            reader.readFromFile(guidsl_file);

            /*
             * Initialisiere eine Instanz von Main mit den Optionen und der
             * Featureauswahl.
             */
            // Main m = new Main(fujiOptions, null, featuresList);

            /*
             * Initialisiere eine Instanz von Main mit den Optionen und dem
             * Featuremodel.
             */
            Main fuji = new Main(fujiOptions, featureModel, null);

            /* Bereite die composition vor. */
            Composition composition = fuji.getComposition(fuji);
            Program ast = composition.composeAST();

            /* Führe die composition durch. */
            // m.processAST(ast);

            /* Führe den typecheck durch. */
            fuji.typecheckAST(ast);

            /* Gebe die Warnungen aus. */
            for (Problem warn : fuji.getWarnings()) {
                System.out.println("File name: " + warn.fileName());
                System.out.println("Line: " + warn.line());
                System.out.println("Message: " + warn.message());
            }

            /* Gebe die Fehler aus. */
            for (Problem err : fuji.getErrors()) {
                if (err.line() == -1) {
                    // ein Hack, aber es geht zzt. nicht anders.
                    String[] s = err.fileName().split(":");
                    System.out.println("File name: " + s[0]);
                    System.out.println("Line: " + s[1]);
                } else {
                    System.out.println("File name: " + err.fileName());
                    System.out.println("Line: " + err.line());
                }
                System.out.println("Message: " + err.message());
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeatureDirNotFoundException e) {
            e.printStackTrace();
        } catch (SyntacticErrorException e) {
            e.printStackTrace();
        } catch (SemanticErrorException e) {
            e.printStackTrace();
        } catch (CompilerWarningException e) {
            e.printStackTrace();
        } catch (CompositionErrorException e) {
            e.printStackTrace();
        } catch (UnsupportedModelException e) {
            e.printStackTrace();
        }
    }
}
