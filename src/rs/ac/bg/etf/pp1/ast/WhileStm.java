// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class WhileStm extends Statement {

    private WhileCondition WhileCondition;
    private Condition Condition;
    private WhileStart WhileStart;
    private Statement Statement;
    private WhileLoopEnd WhileLoopEnd;

    public WhileStm (WhileCondition WhileCondition, Condition Condition, WhileStart WhileStart, Statement Statement, WhileLoopEnd WhileLoopEnd) {
        this.WhileCondition=WhileCondition;
        if(WhileCondition!=null) WhileCondition.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.WhileStart=WhileStart;
        if(WhileStart!=null) WhileStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.WhileLoopEnd=WhileLoopEnd;
        if(WhileLoopEnd!=null) WhileLoopEnd.setParent(this);
    }

    public WhileCondition getWhileCondition() {
        return WhileCondition;
    }

    public void setWhileCondition(WhileCondition WhileCondition) {
        this.WhileCondition=WhileCondition;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public WhileStart getWhileStart() {
        return WhileStart;
    }

    public void setWhileStart(WhileStart WhileStart) {
        this.WhileStart=WhileStart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public WhileLoopEnd getWhileLoopEnd() {
        return WhileLoopEnd;
    }

    public void setWhileLoopEnd(WhileLoopEnd WhileLoopEnd) {
        this.WhileLoopEnd=WhileLoopEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(WhileCondition!=null) WhileCondition.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(WhileStart!=null) WhileStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(WhileLoopEnd!=null) WhileLoopEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(WhileCondition!=null) WhileCondition.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(WhileStart!=null) WhileStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(WhileLoopEnd!=null) WhileLoopEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(WhileCondition!=null) WhileCondition.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(WhileStart!=null) WhileStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(WhileLoopEnd!=null) WhileLoopEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WhileStm(\n");

        if(WhileCondition!=null)
            buffer.append(WhileCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileStart!=null)
            buffer.append(WhileStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileLoopEnd!=null)
            buffer.append(WhileLoopEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WhileStm]");
        return buffer.toString();
    }
}
