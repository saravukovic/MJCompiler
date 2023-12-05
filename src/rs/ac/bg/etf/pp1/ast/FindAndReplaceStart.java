// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class FindAndReplaceStart implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Designator Designator;
    private Designator Designator1;
    private Expr Expr;
    private FindAndReplaceIter FindAndReplaceIter;

    public FindAndReplaceStart (Designator Designator, Designator Designator1, Expr Expr, FindAndReplaceIter FindAndReplaceIter) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.Designator1=Designator1;
        if(Designator1!=null) Designator1.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.FindAndReplaceIter=FindAndReplaceIter;
        if(FindAndReplaceIter!=null) FindAndReplaceIter.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public Designator getDesignator1() {
        return Designator1;
    }

    public void setDesignator1(Designator Designator1) {
        this.Designator1=Designator1;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public FindAndReplaceIter getFindAndReplaceIter() {
        return FindAndReplaceIter;
    }

    public void setFindAndReplaceIter(FindAndReplaceIter FindAndReplaceIter) {
        this.FindAndReplaceIter=FindAndReplaceIter;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(Designator1!=null) Designator1.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(FindAndReplaceIter!=null) FindAndReplaceIter.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(Designator1!=null) Designator1.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(FindAndReplaceIter!=null) FindAndReplaceIter.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(Designator1!=null) Designator1.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(FindAndReplaceIter!=null) FindAndReplaceIter.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FindAndReplaceStart(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator1!=null)
            buffer.append(Designator1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FindAndReplaceIter!=null)
            buffer.append(FindAndReplaceIter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FindAndReplaceStart]");
        return buffer.toString();
    }
}
