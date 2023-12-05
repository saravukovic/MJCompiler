// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class BoolConstant extends Constant {

    private Boolean val;

    public BoolConstant (Boolean val) {
        this.val=val;
    }

    public Boolean getVal() {
        return val;
    }

    public void setVal(Boolean val) {
        this.val=val;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BoolConstant(\n");

        buffer.append(" "+tab+val);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BoolConstant]");
        return buffer.toString();
    }
}
