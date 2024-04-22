import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams;
import java.io.FileInputStream;
import java.io.InputStream;
//import org.antlr.v4.runtime.misc.ParseCancellationException;
//import org.antlr.v4.runtime.DefaultErrorStrategy;

public class calTwo {

    public static void main (String[] args) throws Exception {

        String inputFile = null; // langFile will be a program written in cal

        if (args.length > 0) {
            inputFile = args[0];
        }
        // langFile takes arg if cmd line args not equal to zero

        InputStream is = System.in; // reading from stdin

        if (inputFile != null) {
            is = new FileInputStream(inputFile);

        try {
            calLexer lexer = new calLexer (CharStreams.fromStream(is));
            //lexer.removeErrorListeners();
            //lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    calParser parser = new calParser(tokens);
                    //parser.removeErrorListeners();
                    //parser.addErrorListeners(ThrowingErrorListener.INSTANCE);
                    ParseTree tree = parser.prog();

                    System.out.println("Parsed Successfully! ...");

                    //calTwoEvalVisitor vis = new calTwoEvalVisitor ();
                    //vis.visit(tree);
        }

        catch (Exception e)
        {
            System.out.println("Parse Unsuccessful :( ...");
        }

    }
}
}