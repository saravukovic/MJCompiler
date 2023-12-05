// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class MatchedIfElseStatement extends Statement {

    private IfStart IfStart;
    private IFCondition IFCondition;
    private Statement Statement;
    private ElseStart ElseStart;
    private Statement Statement1;

    public MatchedIfElseStatement (IfStart IfStart, IFCondition IFCondition, Statement Statement, ElseStart ElseStart, Statement Statement1) {
        this.IfStart=IfStart;
        if(IfStart!=null) IfStart.setParent(this);
        this.IFCondition=IFCondition;
        if(IFCondition!=null) IFCondition.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ElseStart=ElseStart;
        if(ElseStart!=null) ElseStart.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
    }

    public IfStart getIfStart() {
        return IfStart;
    }

    public void setIfStart(IfStart IfStart) {
        this.IfStart=IfStart;
    }

    public IFCondition getIFCondition() {
        return IFCondition;
    }

    public void setIFCondition(IFCondition IFCondition) {
        this.IFCondition=IFCondition;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ElseStart getElseStart() {
        return ElseStart;
    }

    public void setElseStart(ElseStart ElseStart) {
        this.ElseStart=ElseStart;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfStart!=null) IfStart.accept(visitor);
        if(IFCondition!=null) IFCondition.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ElseStart!=null) ElseStart.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfStart!=null) IfStart.traverseTopDown(visitor);
        if(IFCondition!=null) IFCondition.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ElseStart!=null) ElseStart.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfStart!=null) IfStart.traverseBottomUp(visitor);
        if(IFCondition!=null) IFCondition.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ElseStart!=null) ElseStart.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedIfElseStatement(\n");

        if(IfStart!=null)
            buffer.append(IfStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IFCondition!=null)
            buffer.append(IFCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElseStart!=null)
            buffer.append(ElseStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedIfElseStatement]");
        return buffer.toString();
    }
}
