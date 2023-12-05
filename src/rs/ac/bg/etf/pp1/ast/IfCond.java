// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class IfCond extends IFCondition {

    private Condition Condition;
    private CheckOrCond CheckOrCond;

    public IfCond (Condition Condition, CheckOrCond CheckOrCond) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.CheckOrCond=CheckOrCond;
        if(CheckOrCond!=null) CheckOrCond.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public CheckOrCond getCheckOrCond() {
        return CheckOrCond;
    }

    public void setCheckOrCond(CheckOrCond CheckOrCond) {
        this.CheckOrCond=CheckOrCond;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(CheckOrCond!=null) CheckOrCond.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(CheckOrCond!=null) CheckOrCond.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(CheckOrCond!=null) CheckOrCond.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfCond(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CheckOrCond!=null)
            buffer.append(CheckOrCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfCond]");
        return buffer.toString();
    }
}
