// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class ClassVarDeclar extends ClassVarDecl {

    private ClassVarDeclType ClassVarDeclType;
    private ClassVarDecls ClassVarDecls;

    public ClassVarDeclar (ClassVarDeclType ClassVarDeclType, ClassVarDecls ClassVarDecls) {
        this.ClassVarDeclType=ClassVarDeclType;
        if(ClassVarDeclType!=null) ClassVarDeclType.setParent(this);
        this.ClassVarDecls=ClassVarDecls;
        if(ClassVarDecls!=null) ClassVarDecls.setParent(this);
    }

    public ClassVarDeclType getClassVarDeclType() {
        return ClassVarDeclType;
    }

    public void setClassVarDeclType(ClassVarDeclType ClassVarDeclType) {
        this.ClassVarDeclType=ClassVarDeclType;
    }

    public ClassVarDecls getClassVarDecls() {
        return ClassVarDecls;
    }

    public void setClassVarDecls(ClassVarDecls ClassVarDecls) {
        this.ClassVarDecls=ClassVarDecls;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassVarDeclType!=null) ClassVarDeclType.accept(visitor);
        if(ClassVarDecls!=null) ClassVarDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassVarDeclType!=null) ClassVarDeclType.traverseTopDown(visitor);
        if(ClassVarDecls!=null) ClassVarDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassVarDeclType!=null) ClassVarDeclType.traverseBottomUp(visitor);
        if(ClassVarDecls!=null) ClassVarDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassVarDeclar(\n");

        if(ClassVarDeclType!=null)
            buffer.append(ClassVarDeclType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassVarDecls!=null)
            buffer.append(ClassVarDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassVarDeclar]");
        return buffer.toString();
    }
}
