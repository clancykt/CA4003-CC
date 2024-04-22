import java.io.*;
import java.util.*;
import org.antlr.v4.runtime.tree.*;

public class TADVis extends calBaseVisitor<Boolean>
{
	SymbolTable symbolTable = new SymbolTable();
	private int tmp = 1;
    private int labels = 1;

    @Override
    public Boolean visitProg(calParser.ProgContext ctx) 
    { 
    	return visitChildren(ctx); 
    }

    @Override 
    public Boolean visitVar_decl(calParser.Var_declContext ctx) 
    { 
    	return visitChildren(ctx); 
    }

    @Override 
    public Boolean visitConst_decl(calParser.Const_declContext ctx) 
    {
    	return visitChildren(ctx); 
    }

    @Override 
    public Boolean visitFunc(calParser.FuncContext ctx) 
    { 
    	return visitChildren(ctx); 
    }

    @Override 
    public Boolean visitNemp_paralst(calParser.Nemp_paralstContext ctx) 
    { 
    	return visitChildren(ctx); 
    }

    @Override 
    public Boolean visitNemp_arglst(calParser.Nemp_arglstContext ctx) 
    { 
    	return visitChildren(ctx); 
    }

    @Override 
    public Boolean visitAssignStm(calParser.AssignStmContext ctx) 
    { 
    	return visitChildren(ctx); 
    }

}