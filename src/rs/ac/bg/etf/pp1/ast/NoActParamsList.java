// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class NoActParamsList extends ActParsList {

    public NoActParamsList () {
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
        buffer.append("NoActParamsList(\n");

        buffer.append(tab);
        buffer.append(") [NoActParamsList]");
        return buffer.toString();
    }
}
