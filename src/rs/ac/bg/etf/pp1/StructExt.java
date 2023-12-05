package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class StructExt extends Struct {

	public StructExt(int kind) {
		super(kind);
		// TODO Auto-generated constructor stub
	}

	public StructExt(int kind, Struct elemType) {
		super(kind);
		super.setElementType(elemType);
	}

	public StructExt(int kind, SymbolDataStructure members) {
		super(kind, members);
		// TODO Auto-generated constructor stub
	}
	
	public static boolean compatibleWithForAssignment(Struct first, Struct second) {
		return first.equals(second) || first == Tab.nullType && second.isRefType()
				|| second == Tab.nullType && first.isRefType() || first == second.getElemType();
	}

}
