// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class FindAndReplaceStm extends Statement {

    private FindAndReplaceStart FindAndReplaceStart;
    private FindAndReplaceExpr FindAndReplaceExpr;
    private FindAndReplaceEnd FindAndReplaceEnd;

    public FindAndReplaceStm (FindAndReplaceStart FindAndReplaceStart, FindAndReplaceExpr FindAndReplaceExpr, FindAndReplaceEnd FindAndReplaceEnd) {
        this.FindAndReplaceStart=FindAndReplaceStart;
        if(FindAndReplaceStart!=null) FindAndReplaceStart.setParent(this);
        this.FindAndReplaceExpr=FindAndReplaceExpr;
        if(FindAndReplaceExpr!=null) FindAndReplaceExpr.setParent(this);
        this.FindAndReplaceEnd=FindAndReplaceEnd;
        if(FindAndReplaceEnd!=null) FindAndReplaceEnd.setParent(this);
    }

    public FindAndReplaceStart getFindAndReplaceStart() {
        return FindAndReplaceStart;
    }

    public void setFindAndReplaceStart(FindAndReplaceStart FindAndReplaceStart) {
        this.FindAndReplaceStart=FindAndReplaceStart;
    }

    public FindAndReplaceExpr getFindAndReplaceExpr() {
        return FindAndReplaceExpr;
    }

    public void setFindAndReplaceExpr(FindAndReplaceExpr FindAndReplaceExpr) {
        this.FindAndReplaceExpr=FindAndReplaceExpr;
    }

    public FindAndReplaceEnd getFindAndReplaceEnd() {
        return FindAndReplaceEnd;
    }

    public void setFindAndReplaceEnd(FindAndReplaceEnd FindAndReplaceEnd) {
        this.FindAndReplaceEnd=FindAndReplaceEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FindAndReplaceStart!=null) FindAndReplaceStart.accept(visitor);
        if(FindAndReplaceExpr!=null) FindAndReplaceExpr.accept(visitor);
        if(FindAndReplaceEnd!=null) FindAndReplaceEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FindAndReplaceStart!=null) FindAndReplaceStart.traverseTopDown(visitor);
        if(FindAndReplaceExpr!=null) FindAndReplaceExpr.traverseTopDown(visitor);
        if(FindAndReplaceEnd!=null) FindAndReplaceEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FindAndReplaceStart!=null) FindAndReplaceStart.traverseBottomUp(visitor);
        if(FindAndReplaceExpr!=null) FindAndReplaceExpr.traverseBottomUp(visitor);
        if(FindAndReplaceEnd!=null) FindAndReplaceEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FindAndReplaceStm(\n");

        if(FindAndReplaceStart!=null)
            buffer.append(FindAndReplaceStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FindAndReplaceExpr!=null)
            buffer.append(FindAndReplaceExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FindAndReplaceEnd!=null)
            buffer.append(FindAndReplaceEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FindAndReplaceStm]");
        return buffer.toString();
    }
}
