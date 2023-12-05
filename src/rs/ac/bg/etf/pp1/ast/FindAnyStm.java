// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class FindAnyStm extends Statement {

    private Designator Designator;
    private FindAnyStart FindAnyStart;

    public FindAnyStm (Designator Designator, FindAnyStart FindAnyStart) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.FindAnyStart=FindAnyStart;
        if(FindAnyStart!=null) FindAnyStart.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public FindAnyStart getFindAnyStart() {
        return FindAnyStart;
    }

    public void setFindAnyStart(FindAnyStart FindAnyStart) {
        this.FindAnyStart=FindAnyStart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(FindAnyStart!=null) FindAnyStart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(FindAnyStart!=null) FindAnyStart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(FindAnyStart!=null) FindAnyStart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FindAnyStm(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FindAnyStart!=null)
            buffer.append(FindAnyStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FindAnyStm]");
        return buffer.toString();
    }
}
