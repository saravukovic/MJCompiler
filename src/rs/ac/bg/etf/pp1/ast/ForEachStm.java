// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class ForEachStm extends Statement {

    private ForEachStart ForEachStart;
    private ForEachStmStart ForEachStmStart;
    private Statement Statement;
    private ForEachEnd ForEachEnd;

    public ForEachStm (ForEachStart ForEachStart, ForEachStmStart ForEachStmStart, Statement Statement, ForEachEnd ForEachEnd) {
        this.ForEachStart=ForEachStart;
        if(ForEachStart!=null) ForEachStart.setParent(this);
        this.ForEachStmStart=ForEachStmStart;
        if(ForEachStmStart!=null) ForEachStmStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ForEachEnd=ForEachEnd;
        if(ForEachEnd!=null) ForEachEnd.setParent(this);
    }

    public ForEachStart getForEachStart() {
        return ForEachStart;
    }

    public void setForEachStart(ForEachStart ForEachStart) {
        this.ForEachStart=ForEachStart;
    }

    public ForEachStmStart getForEachStmStart() {
        return ForEachStmStart;
    }

    public void setForEachStmStart(ForEachStmStart ForEachStmStart) {
        this.ForEachStmStart=ForEachStmStart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ForEachEnd getForEachEnd() {
        return ForEachEnd;
    }

    public void setForEachEnd(ForEachEnd ForEachEnd) {
        this.ForEachEnd=ForEachEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForEachStart!=null) ForEachStart.accept(visitor);
        if(ForEachStmStart!=null) ForEachStmStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ForEachEnd!=null) ForEachEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForEachStart!=null) ForEachStart.traverseTopDown(visitor);
        if(ForEachStmStart!=null) ForEachStmStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ForEachEnd!=null) ForEachEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForEachStart!=null) ForEachStart.traverseBottomUp(visitor);
        if(ForEachStmStart!=null) ForEachStmStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ForEachEnd!=null) ForEachEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForEachStm(\n");

        if(ForEachStart!=null)
            buffer.append(ForEachStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForEachStmStart!=null)
            buffer.append(ForEachStmStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForEachEnd!=null)
            buffer.append(ForEachEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForEachStm]");
        return buffer.toString();
    }
}
