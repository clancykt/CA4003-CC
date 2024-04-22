import java.io.*;
import java.util.*;
import org.antlr.v4.runtime.tree.*;
import java.util.Arrays;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.RuntimeErrorException;

public class EvalVisitor extends calBaseVisitor<Integer>
{
    Map<String, Integer> memory = new HashMap<>();
    Map<String, Function> function = new HashMap<>();
    SymbolTable symbolTable = new SymbolTable();
    private int tmp = 1;
    private int labels = 1;
    private boolean inFunction = false;

    public EvalVisitor ()
    {
        symbolTable = new SymbolTable();
    }

    String scope = "global";

    @Override
    public Boolean visitProg(calParser.ProgContext ctx) 
    { 
        symbolTable.enterScope();
    }

    // checks semantic "are args of arithmetic op in variables?"
    @Override
    public String visitVar_decl(calParser.Var_declContext ctx) 
    {
        String id = ctx.ID().getText();
        String type = ctx.getType().getText();

        if (type.equalsIgnoreCase("void"))
        {
            throw new RuntimeException(String.format("Error: Variable %s cannot be v o i d", id));

        }
        integer value = null;

        if (inFunction)
        {
            id = "#" + id;

            if (memory.containsKey(id))
            {
                throw new RuntimeException(String.format("Error: Variable already defined", id));

            }
        }
            st.addSymbol(id, type, "local", "variable");

        else 
            {
                if (memory.containsKey(id))
                {
                    throw new RuntimeException(String.format("Error: Variable already defined", id));

                }
                st.addSymbol(id, type, "global", "variable");
            }
            memory.put(id, value);

            return value;
    }

    // checks semantic "are args of arithmetic op int constants?"
    @Override
    public String visitConst_decl(calParser.Const_declContext ctx) 
    {
        String type = ctx.type().getText();
        String id = ctx.id().getText();

        if (type.equalsIgnoreCase("void"))
        {
            throw new RuntimeException(String.format("Error: Constant %s cannot be v o i d", id));
        }

        Integer value = visit(ctx.expression());
        String expr = ctx.expression().getText().split("\\(")[0];
        st.CompareTypeValue(type, expr);

        if (inFunction)
        {
            id = "#" + id;

            if (memory.containsKey(id))
            {
                throw new RuntimeException(String.format("Error: Constant already defined", id));
            }

            st.addSymbol(id, type, "global", "constant");
        }
        memory.put(id, value);
        return value; 
    }

    // checks semantic "is no identifier contained in the same scope?"
    @Override
    public String visitFunc(calParser.FuncContext ctx) 
    { 
        String id = ctx.ID().getText();
        String type = ctx.type().getText();
        scope = "global";
        String IDPointer = symbolTable.checkIfPresent(id, scope); 

        if (IDPointer = "global")
        {
            Error("Error: function already defined!");
        }

        Symbol symbol = new Symbol (id, type, "null", "null", true);
    }

    @Override 
    public Boolean visitType(assignParser.TypeContext ctx) 
    { 
        return visitChildren(ctx); 
    }

    public Integer visitNemp_paralst(calParser.Nemp_paralstContext ctx) 
    { 
       String id = ctx.ID().getText();
        String type = ctx.type().getText();
        Symbol symbol = new Symbol(id, type, "null", "null", false);
        symbolTable.insertToSymbolTable(id, symbol, scope);
        visit(ctx.nemp_parameter_list());
        return type;
    }

    @Override
    public String visitNemp_arglst(calParser.Nemp_arglstContext ctx) 
    { 
        return visitChildren(ctx); 

    }

    @Override
    public Integer visitAssignStm(calParser.AssignStmContext ctx) 
    { 
        String id = ctx.ID().getText();
        Integer value = visit(ctx.expression());

        if (inFunction)
            id = "#" + id;
        if (st.getSymbol(id).equals("constant"))
        {
            throw new RuntimeException("Error");
        }

        memory.put(id, value);
        System.out.println(" ");
        return value; 
    }

    // checks semantic "does every function have the correct nuum of args?"
    @Override
    public String visitArglst(calParser.ArglstContext ctx) 
    { 
        visit(ctx.arg_list());
        return " ";
    }
}