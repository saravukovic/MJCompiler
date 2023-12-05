// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class FuncDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ClassMethodDeclList ClassMethodDeclList;

    public FuncDecl (ClassMethodDeclList ClassMethodDeclList) {
        this.ClassMethodDeclList=ClassMethodDeclList;
        if(ClassMethodDeclList!=null) ClassMethodDeclList.setParent(this);
    }

    public ClassMethodDeclList getClassMethodDeclList() {
        return ClassMethodDeclList;
    }

    public void setClassMethodDeclList(ClassMethodDeclList ClassMethodDeclList) {
        this.ClassMethodDeclList=ClassMethodDeclList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassMethodDeclList!=null) ClassMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassMethodDeclList!=null) ClassMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassMethodDeclList!=null) ClassMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FuncDecl(\n");

        if(ClassMethodDeclList!=null)
            buffer.append(ClassMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FuncDecl]");
        return buffer.toString();
    }
}
