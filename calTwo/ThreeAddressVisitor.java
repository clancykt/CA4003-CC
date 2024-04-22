import java.util.*;

public class ThreeAddressVisitor implements calVisitor
{

  String label = "L1";
  String prevLabel;
  int curTempCount = 0;
  int paramCount = 0;
  int labelCount = 1;
  HashMap<String,Vector<AddressCode>> addrCode = new HashMap<String,Vector<AddressCode>>();
  HashMap<String,String> jumpLabelMap = new HashMap<String,String>();

	public Object visit(SimpleNode node, Object data)
	{
		node.childrenAccept(this, data);
    		return null;
	}
	
  public Object visit(ASTProgram node, Object data)
  {
    System.out.println("*******************************");
    System.out.println("****** IR 3-address code*******");
    System.out.println("*******************************\n");
    node.childrenAccept(this,data);
    Set keys = addrCode.keySet();
    Iterator iter = keys.iterator();
    int count = 1;
    while(iter.hasNext())
    {
      String s = (String)iter.next();
      Vector<AddressCode> v = (Vector<AddressCode>)addrCode.get(s);
      System.out.println(s);
      for(int i =0; i < v.size(); i++)
      {
        v.get(i).printAddrCode();
      }
    } 
  	return null;
  }
  
  public Object visit(ASTvarDecl node, Object data)
  {
    return null;
  }
  
  public Object visit(ASTconstDecl node, Object data)
  {
    Vector<AddressCode> temp = addrCode.get(label);
    if(temp == null)
      temp = new Vector<AddressCode>();
    int numChildren = node.jjtGetNumChildren();
    for(int i = 0; i < numChildren; i=i+3)
    {
      AddressCode decl = new AddressCode(
          "=", 
          (String)node.jjtGetChild(i).jjtAccept(this,null), 
          (String)node.jjtGetChild(i+2).jjtAccept(this,null)
        );
      temp.add(decl);
    }
    addrCode.put(label,temp);
  	return null;
  }
  
  public Object visit(ASTFunctionDecl node, Object data)
  {
    prevLabel = label;
    label = "L"+(labelCount+1);

    jumpLabelMap.put((String)node.jjtGetChild(1).jjtAccept(this,null) , label);

    node.childrenAccept(this,data);

    Vector<AddressCode> temp = addrCode.get(label);
    if(temp == null)
      temp = new Vector<AddressCode>();

    AddressCode retrun = new AddressCode(
        "return", 
        ""
      );
    temp.add(retrun);
    addrCode.put(label,temp);

    label = prevLabel;
    labelCount++;

  	return null;
  }
  
  public Object visit(ASTfunctionBody node, Object data)
  {
    node.childrenAccept(this,data);

  	return null;
  }
  
  public Object visit(ASTtype node, Object data)
  {
  	return null;
  }
  
  public Object visit(ASTParams node, Object data)
  {
  	return null;
  }
  
  public Object visit(ASTReturn node, Object data)
  {
  	return null;
  }
  
  public Object visit(ASTmain node, Object data)
  {
    prevLabel = label;
    label = "L"+(labelCount+1);

    node.childrenAccept(this,data);

    label = prevLabel;
    labelCount++;
  	return null;
  }

  public Object visit(ASTAssign node, Object data)
  {
    Vector<AddressCode> temp = addrCode.get(label);
    if(temp == null)
      temp = new Vector<AddressCode>();
    AddressCode decl = new AddressCode(
        "=", 
        (String)node.jjtGetChild(0).jjtAccept(this,null),
        (String)node.jjtGetChild(1).jjtAccept(this,null)
      );
    temp.add(decl);
    addrCode.put(label,temp);
    return null;

  }

  public Object visit(ASTcondition node, Object data)
  {
    node.childrenAccept(this,data);
  	return null;
  }
  public Object visit(ASTidentList node, Object data)
  {
  	return null;
  }
  public Object visit(ASTargList node, Object data)
  {
    Vector<AddressCode> temp = addrCode.get(label);
    if(temp == null)
      temp = new Vector<AddressCode>();

    paramCount++;
    String paramSetName = "param"+paramCount;
    for(int i = 0; i < node.jjtGetNumChildren(); i++)
    {
      AddressCode param = new AddressCode(
          paramSetName,
          (String)node.jjtGetChild(i).jjtAccept(this,null)  
        );  
      temp.add(param);
    }
    addrCode.put(label,temp);
    return paramSetName;
  }
  
  public Object visit(ASTId node, Object data)
  {
  	return ((Token)node.jjtGetValue()).image;
  }
  
  public Object visit(ASTnum node, Object data)
  {
  	return ((Token)node.jjtGetValue()).image;
  }
  
  public Object visit(ASTbool node, Object data)
  {
  	return ((Token)node.jjtGetValue()).image;
  }
  
  public Object visit(ASTbinaryArithOp node, Object data)
  {
  	return ((Token)node.jjtGetValue()).image;
  }
  
  public Object visit(ASTcompOp node, Object data)
  {
  	return null;
  }
  
  public Object visit(ASTreal node, Object data)
  {
  	return ((Token)node.jjtGetValue()).image;
  }
  
  public Object visit(ASTfunctionCall node, Object data)
  {
    Vector<AddressCode> temp = addrCode.get(label);
    if(temp == null)
      temp = new Vector<AddressCode>();

    AddressCode call = new AddressCode(
        "call",
        "",
        (String)node.jjtGetChild(0).jjtAccept(this,null),
        (String)node.jjtGetChild(1).jjtAccept(this,null)
      ); 

    temp.add(call);

    AddressCode gt = new AddressCode(
        "goto",
        jumpLabelMap.get((String)node.jjtGetChild(0).jjtAccept(this,null)),
        ""
      );
    temp.add(gt);

    addrCode.put(label,temp);
  	return null;
  }
}