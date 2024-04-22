import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable
{
    
    public Stack<String> undoStack; 
    public Map<String, Symbol> globalScopeTable;
    public Map<String, Symbol> localScopeTable;

    // will map a symbol to a scope
    public Map<String, Map<String, Symbol>> symbolTable; 

    public SymbolTable()
    {
        globalScopeTable = new HashMap<String, Symbol>();
        localScopeTable = new HashMap<String, Symbol>();
        symbolTable = new HashMap<String, Map<String, Symbol>>();
        symbolTable.put("global", globalScopeTable);
        symbolTable.put("local", localScopeTable);
        undoStack = new Stack<String>();

    }

    // this adds symbols to the table
    public void insertToSymbolTable(String identifier, Symbol symbol, String scope)
    { 
        //Symbol symbol = new Symbol(identifier, type);
        if(scope == "local"){
            // this is for if it is already contained in the map
            if(localScopeTable.containsKey(identifier))
            {
                //Symbol symbol = localScopeTable.get(identifier);
                if(symbol.getDeclaration().equalsIgnoreCase("VAR"))
                {
                    localScopeTable.replace(identifier, symbol);
                }
                else{
                    System.out.println("Can't update a Const");
                }
            }
        }
        if(scope == "global")
        {
            // this is for if it is already contained in the map
            if(globalScopeTable.containsKey(identifier))
            { 
                //Symbol symbol = localScopeTable.get(identifier);
                if(symbol.getDeclaration().equalsIgnoreCase("VAR")){

                    globalScopeTable.replace(identifier, symbol);
                }
                else{
                    System.out.println("Can't update a Const");
                }
            }
        }

        if(scope == "global" && !globalScopeTable.containsKey(identifier))
        {
            globalScopeTable.put(identifier, symbol);
        }

        else
        {
            localScopeTable.put(identifier, symbol);
        }
    }

    // this method clears localScopeTable existing mappings 
    // if a special char is given in parameters this method is invoked
    public void destroyScope()
    {
        localScopeTable.clear();
        
    }

    public void clearUndoStack(String specialCharacter)
    {
        while(undoStack.pop() != specialCharacter)
        {
            undoStack.pop();
        }
    }

    public Symbol getSymbol(String id, String scope)
    {
        if(scope == "global")
        {
            return globalScopeTable.get(id);
        }

        else
        {
            return localScopeTable.get(id);
        }
    }

    public String getScope(String id)
    {
        if(globalScopeTable.containsKey(id))
        {
            return "global";
        }

        else if(localScopeTable.containsKey(id))
        {
            return "local";
        }

        return "id not defined";
    }

    // basis of logic - checks the scope of a symbol & assigns a pointer to it
    // if a scope contains a symbol this method is invoked
    public String checkIfPresent(String id, String scope)
    {
        if(scope == "local" && localScopeTable.containsKey(id))
        {
            return "local";
        }

        if(scope == "global" && globalScopeTable.containsKey(id))
        {

            return "global";
        }

        else
        {
            return "null";
        }
    }


    public static void main(String[] args) 
    {
    }

}