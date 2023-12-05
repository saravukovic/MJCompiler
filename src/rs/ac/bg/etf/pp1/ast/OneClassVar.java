// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class OneClassVar extends ClassVarDecls {

    private ClassVariable ClassVariable;

    public OneClassVar (ClassVariable ClassVariable) {
        this.ClassVariable=ClassVariable;
        if(ClassVariable!=null) ClassVariable.setParent(this);
    }

    public ClassVariable getClassVariable() {
        return ClassVariable;
    }

    public void setClassVariable(ClassVariable ClassVariable) {
        this.ClassVariable=ClassVariable;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassVariable!=null) ClassVariable.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassVariable!=null) ClassVariable.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassVariable!=null) ClassVariable.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OneClassVar(\n");

        if(ClassVariable!=null)
            buffer.append(ClassVariable.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OneClassVar]");
        return buffer.toString();
    }
}
