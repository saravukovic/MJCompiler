// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ClassName ClassName;
    private ClassVarDeclList ClassVarDeclList;
    private FuncDeclList FuncDeclList;

    public ClassDecl (ClassName ClassName, ClassVarDeclList ClassVarDeclList, FuncDeclList FuncDeclList) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.ClassVarDeclList=ClassVarDeclList;
        if(ClassVarDeclList!=null) ClassVarDeclList.setParent(this);
        this.FuncDeclList=FuncDeclList;
        if(FuncDeclList!=null) FuncDeclList.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
    }

    public ClassVarDeclList getClassVarDeclList() {
        return ClassVarDeclList;
    }

    public void setClassVarDeclList(ClassVarDeclList ClassVarDeclList) {
        this.ClassVarDeclList=ClassVarDeclList;
    }

    public FuncDeclList getFuncDeclList() {
        return FuncDeclList;
    }

    public void setFuncDeclList(FuncDeclList FuncDeclList) {
        this.FuncDeclList=FuncDeclList;
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
        if(ClassName!=null) ClassName.accept(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.accept(visitor);
        if(FuncDeclList!=null) FuncDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseTopDown(visitor);
        if(FuncDeclList!=null) FuncDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseBottomUp(visitor);
        if(FuncDeclList!=null) FuncDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassVarDeclList!=null)
            buffer.append(ClassVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FuncDeclList!=null)
            buffer.append(FuncDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}
