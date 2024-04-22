import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.CharStreams;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.antlr.v4.runtime.tree.ParseTree;

public class cal {
    public static void main(String[] args) throws Exception {
        String inputFile = null;

        if (args.length > 0)
            inputFile = args[0];

        InputStream is = System.in;
        if (inputFile != null)
            is = new FileInputStream(inputFile);

        try 
        {
            calLexer lexer = new calLexer(CharStreams.fromStream(is));
            //lexer.removeErrorListeners();
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            calParser parser = new calParser(tokens);
            //parser.removeErrorListeners();
            //String fileName = getFileName(inputFile);
            ParseTree tree = parser.prog();
            System.out.println("Parsed Successfully! ....");
            EvalVisitor eVisitor = new EvalVisitor();
            eVisitor.visit(tree);
            System.out.println(" ");

        }

        catch (Exception e)
        {
            System.out.println(e);
            System.out.println(inputFile + "Parse Unsuccessful :( ...");

        }

    

    }

    
}