// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class ErrClassVarDeclarSEMI extends ClassVarDecl {

    public ErrClassVarDeclarSEMI () {
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
        buffer.append("ErrClassVarDeclarSEMI(\n");

        buffer.append(tab);
        buffer.append(") [ErrClassVarDeclarSEMI]");
        return buffer.toString();
    }
}
