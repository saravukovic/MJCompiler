// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class Conditions extends Condition {

    private Condition Condition;
    private CheckAndCond CheckAndCond;
    private CondTerm CondTerm;

    public Conditions (Condition Condition, CheckAndCond CheckAndCond, CondTerm CondTerm) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.CheckAndCond=CheckAndCond;
        if(CheckAndCond!=null) CheckAndCond.setParent(this);
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public CheckAndCond getCheckAndCond() {
        return CheckAndCond;
    }

    public void setCheckAndCond(CheckAndCond CheckAndCond) {
        this.CheckAndCond=CheckAndCond;
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(CheckAndCond!=null) CheckAndCond.accept(visitor);
        if(CondTerm!=null) CondTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(CheckAndCond!=null) CheckAndCond.traverseTopDown(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(CheckAndCond!=null) CheckAndCond.traverseBottomUp(visitor);
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Conditions(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CheckAndCond!=null)
            buffer.append(CheckAndCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Conditions]");
        return buffer.toString();
    }
}
