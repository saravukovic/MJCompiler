// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class Vars extends VarDecls {

    private VarDecls VarDecls;
    private Variable Variable;

    public Vars (VarDecls VarDecls, Variable Variable) {
        this.VarDecls=VarDecls;
        if(VarDecls!=null) VarDecls.setParent(this);
        this.Variable=Variable;
        if(Variable!=null) Variable.setParent(this);
    }

    public VarDecls getVarDecls() {
        return VarDecls;
    }

    public void setVarDecls(VarDecls VarDecls) {
        this.VarDecls=VarDecls;
    }

    public Variable getVariable() {
        return Variable;
    }

    public void setVariable(Variable Variable) {
        this.Variable=Variable;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDecls!=null) VarDecls.accept(visitor);
        if(Variable!=null) Variable.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDecls!=null) VarDecls.traverseTopDown(visitor);
        if(Variable!=null) Variable.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDecls!=null) VarDecls.traverseBottomUp(visitor);
        if(Variable!=null) Variable.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Vars(\n");

        if(VarDecls!=null)
            buffer.append(VarDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Variable!=null)
            buffer.append(Variable.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Vars]");
        return buffer.toString();
    }
}
