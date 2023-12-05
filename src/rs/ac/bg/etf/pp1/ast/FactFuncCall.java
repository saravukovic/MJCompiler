// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class FactFuncCall extends Factor {

    private Designator Designator;
    private FunctionCalled FunctionCalled;
    private ActParsList ActParsList;

    public FactFuncCall (Designator Designator, FunctionCalled FunctionCalled, ActParsList ActParsList) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.FunctionCalled=FunctionCalled;
        if(FunctionCalled!=null) FunctionCalled.setParent(this);
        this.ActParsList=ActParsList;
        if(ActParsList!=null) ActParsList.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public FunctionCalled getFunctionCalled() {
        return FunctionCalled;
    }

    public void setFunctionCalled(FunctionCalled FunctionCalled) {
        this.FunctionCalled=FunctionCalled;
    }

    public ActParsList getActParsList() {
        return ActParsList;
    }

    public void setActParsList(ActParsList ActParsList) {
        this.ActParsList=ActParsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(FunctionCalled!=null) FunctionCalled.accept(visitor);
        if(ActParsList!=null) ActParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(FunctionCalled!=null) FunctionCalled.traverseTopDown(visitor);
        if(ActParsList!=null) ActParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(FunctionCalled!=null) FunctionCalled.traverseBottomUp(visitor);
        if(ActParsList!=null) ActParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactFuncCall(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FunctionCalled!=null)
            buffer.append(FunctionCalled.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsList!=null)
            buffer.append(ActParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactFuncCall]");
        return buffer.toString();
    }
}
