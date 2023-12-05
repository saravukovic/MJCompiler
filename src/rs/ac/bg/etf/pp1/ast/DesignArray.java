// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class DesignArray extends Designator {

    private ArrayDes ArrayDes;
    private Expr Expr;

    public DesignArray (ArrayDes ArrayDes, Expr Expr) {
        this.ArrayDes=ArrayDes;
        if(ArrayDes!=null) ArrayDes.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public ArrayDes getArrayDes() {
        return ArrayDes;
    }

    public void setArrayDes(ArrayDes ArrayDes) {
        this.ArrayDes=ArrayDes;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrayDes!=null) ArrayDes.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayDes!=null) ArrayDes.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayDes!=null) ArrayDes.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignArray(\n");

        if(ArrayDes!=null)
            buffer.append(ArrayDes.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignArray]");
        return buffer.toString();
    }
}
