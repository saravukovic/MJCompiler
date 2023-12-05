package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

public class CounterVisitor extends VisitorAdaptor {
	
	protected int count;
	
	public int getCount() {
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor {

		public void visit(FormParameterArray fpArray) {
			count++;
		}
		
		public void visit(FormParameter fpVar) {
			count++;
		}	
	}
	
	public static class VarCounter extends CounterVisitor {
		
		public void visit(OneVariable var) {
			count++;
		}
		
		public void visit(ArrayVariable var) {
			count++;
		}
	}
}
