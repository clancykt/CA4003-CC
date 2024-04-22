import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams;
import java.io.FileInputStream;
import java.io.InputStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.DefaultErrorStrategy;

public class cal {

    public static void main(String[] args) throws Exception; 
    {
            String inputFile = null;
            if (args.length > 0)
                inputFile = args[0];

            InputStream is = System.in;
            if (inputFile != null)
                is = new FileInputStream(inputFile);

            calLexer lexer = new calLexer(CharStreams.fromStream(is));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            calParser parser = new calParser(tokens);

            //ParseTree tree = parser.prog ();
            //System.out.println (tree.toStringTree(parser));

            //calDisplayVisitor calVis = new calDisplayVisitor ();
            //calVis.visit (tree);

            parser.setErrorHandler(new DefaultErrorStrategy() {

                @Override
                public void recover(Parser check, RecognitionException store) {
                    for (ParserRuleContext cxt = check.getContext(); cxt != null; cxt = cxt
                            .getParent()) {
                        cxt.exception = store;
                    }

                    throw new ParseCancellationException(store);

                }

                @Override
                public Token recoverInline(Parser check) {
                    InputMismatchException store = new InputMismatchException(check);
                    for (ParserRuleContext cxt = check.getContext(); cxt != null; cxt = cxt
                            .getParent()) {
                        cxt.exception = store;
                    }

                    throw new ParseCancellationException(store);
                }
            });

            try {
                ParseTree tree = parser.prog();
                System.out.println("Parsed Successfully! ....");
            } catch (Exception store) {
                System.out.println(store);
                System.out.println("Parse Unsuccessfull :( ...");
            }
          }

    }
