// generated with ast extension for cup
// version 0.8
// 9/8/2023 14:48:13


package rs.ac.bg.etf.pp1.ast;

public class Method extends MethodDecl {

    private MethDeclTypeAndName MethDeclTypeAndName;
    private FormParsList FormParsList;
    private VarDeclList VarDeclList;
    private StatementList StatementList;

    public Method (MethDeclTypeAndName MethDeclTypeAndName, FormParsList FormParsList, VarDeclList VarDeclList, StatementList StatementList) {
        this.MethDeclTypeAndName=MethDeclTypeAndName;
        if(MethDeclTypeAndName!=null) MethDeclTypeAndName.setParent(this);
        this.FormParsList=FormParsList;
        if(FormParsList!=null) FormParsList.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethDeclTypeAndName getMethDeclTypeAndName() {
        return MethDeclTypeAndName;
    }

    public void setMethDeclTypeAndName(MethDeclTypeAndName MethDeclTypeAndName) {
        this.MethDeclTypeAndName=MethDeclTypeAndName;
    }

    public FormParsList getFormParsList() {
        return FormParsList;
    }

    public void setFormParsList(FormParsList FormParsList) {
        this.FormParsList=FormParsList;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethDeclTypeAndName!=null) MethDeclTypeAndName.accept(visitor);
        if(FormParsList!=null) FormParsList.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethDeclTypeAndName!=null) MethDeclTypeAndName.traverseTopDown(visitor);
        if(FormParsList!=null) FormParsList.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethDeclTypeAndName!=null) MethDeclTypeAndName.traverseBottomUp(visitor);
        if(FormParsList!=null) FormParsList.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Method(\n");

        if(MethDeclTypeAndName!=null)
            buffer.append(MethDeclTypeAndName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsList!=null)
            buffer.append(FormParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Method]");
        return buffer.toString();
    }
}
