// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class FunctionDeclList extends FuncDeclList {

    private FuncDeclList FuncDeclList;
    private FuncDecl FuncDecl;

    public FunctionDeclList (FuncDeclList FuncDeclList, FuncDecl FuncDecl) {
        this.FuncDeclList=FuncDeclList;
        if(FuncDeclList!=null) FuncDeclList.setParent(this);
        this.FuncDecl=FuncDecl;
        if(FuncDecl!=null) FuncDecl.setParent(this);
    }

    public FuncDeclList getFuncDeclList() {
        return FuncDeclList;
    }

    public void setFuncDeclList(FuncDeclList FuncDeclList) {
        this.FuncDeclList=FuncDeclList;
    }

    public FuncDecl getFuncDecl() {
        return FuncDecl;
    }

    public void setFuncDecl(FuncDecl FuncDecl) {
        this.FuncDecl=FuncDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FuncDeclList!=null) FuncDeclList.accept(visitor);
        if(FuncDecl!=null) FuncDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FuncDeclList!=null) FuncDeclList.traverseTopDown(visitor);
        if(FuncDecl!=null) FuncDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FuncDeclList!=null) FuncDeclList.traverseBottomUp(visitor);
        if(FuncDecl!=null) FuncDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FunctionDeclList(\n");

        if(FuncDeclList!=null)
            buffer.append(FuncDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FuncDecl!=null)
            buffer.append(FuncDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FunctionDeclList]");
        return buffer.toString();
    }
}
