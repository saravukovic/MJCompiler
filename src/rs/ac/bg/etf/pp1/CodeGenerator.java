package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	// private int varCount;

	// private int paramCnt;

	private int mainPc;
	
	private Obj array2 = null;

	private ArrayList<Obj> classMethods = new ArrayList<Obj>();

	private Stack<ArrayList<Integer>> andCondList = new Stack<ArrayList<Integer>>(),
			orCondList = new Stack<ArrayList<Integer>>(), elseEndAdr = new Stack<ArrayList<Integer>>(),
			breakAddr = new Stack<ArrayList<Integer>>();

	private Stack<Integer> forEachSkipAdr = new Stack<Integer>(), loopStartAdr = new Stack<Integer>(), elementFoundJumpAdr = new Stack<Integer>(), skipFindAny = new Stack<Integer>();
	
	private Stack<Integer> findAndReplaceStartAdr = new Stack<Integer>(), findAndReplaceEndAdr = new Stack<Integer>(), elementFoundJumpAdrSAR = new Stack<Integer>();

	private boolean classMeth = false;

	private boolean returnFound = false;

	public int getMainPc() {
		return mainPc;
	}

	/********************************
	 * visit methods for Class *
	 ********************************/

	public void visit(ClassNameExt name) {
		classMeth = true;
	}

	public void visit(ClassNameNoExt name) {
		classMeth = true;
	}

	public void visit(ClassDecl decl) {
		classMeth = false;
	}

//	/********************************
//	 * visit methods for CONSTANTS *
//	 ********************************/
//
//	public void visit(SingleConstDecl constDecl) {
//		Code.load(constDecl.obj);
//	}

	/********************************
	 * visit methods for METHODS *
	 ********************************/

	public void visit(MethDeclTypeAndName typeAndName) {
		Obj methodNode = typeAndName.obj;
		String name = methodNode.getName();

		if (name.equalsIgnoreCase("main")) {
			mainPc = Code.pc;
		}

		methodNode.setAdr(Code.pc);

		Code.put(Code.enter);
		Code.put(methodNode.getLevel());
		Code.put(methodNode.getLocalSymbols().size());

		if (classMeth) {
			classMethods.add(methodNode);
		}

	}

	public void visit(Method methDecl) {
		if (!returnFound) {
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
		returnFound = false;
	}

	/********************************
	 * visit methods for DesignStmt *
	 ********************************/

	public void visit(DesignAssign desAssign) {
		Code.store(desAssign.getDesignator().obj);
	}

	public void visit(DesignInc desInc) {
		Obj desObj = desInc.getDesignator().obj;
		int desKind = desObj.getKind();

		// ovo se koristi za ucitavanje promenljive (za load(desObj))
		if (desKind == Obj.Elem) {
			Code.put(Code.dup2); /* adresa niza i indeks elementa kom se pristupa */
		} else if (desKind == Obj.Fld) {
			Code.put(Code.dup); /* adresa klase */
		}

		Code.load(desObj); // expr st napunjen u prethodnom if-u dobrim adresama
		Code.loadConst(1); // desObj.vr, 1
		Code.put(Code.add); // desObj.vr + 1
		Code.store(desObj); // prazan expr st
	}

	public void visit(DesignDec desDec) {
		Obj desObj = desDec.getDesignator().obj;
		int desKind = desObj.getKind();
		if (desKind == Obj.Elem) {
			Code.put(Code.dup2); /* adresa niza i indeks elementa kom se pristupa */
		} else if (desKind == Obj.Fld) {
			Code.put(Code.dup); /* adresa klase */
		}

		Code.load(desObj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(desObj);
	}

	public void visit(DesignFuncCall funcCall) {
		int offset = funcCall.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if (funcCall.getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}

	/*******************************
	 * visit methods for Statement *
	 *******************************/

	// cita se sa standardnog ulaza i upisuje se u des
	public void visit(ReadStm readStm) {
		Obj desObj = readStm.getDesignator().obj;

		if (desObj.getType().getKind() == Struct.Char) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}

		Code.store(desObj);
	}

	public void visit(PrintStm printStm) {
		Struct exprStruct = printStm.getExpr().struct;
		NumConstList num = printStm.getNumConstList();
		if (num.getClass() == NoNumConstant.class) {
			if (exprStruct == Tab.charType)
				Code.loadConst(1);
			else
				Code.loadConst(5); // sirina reci koja se cita ?????
		}

		if (exprStruct == Tab.charType)
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}

	public void visit(NumConst num) {
		Code.loadConst(num.getNumber());
	}

	public void visit(ReturnStm returnStm) {
		returnFound = true;
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(NoReturnValueStm returnStm) {
		returnFound = true;
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(WhileStm whileStm) {
		
		// skoci i proveri opet uslove - adrese za skokove kod njih su sad vec
		// popunjene, tako da ako while petlja ne treba vise da se izvrsava, skocice se
		// na adresu posle nje
		// ovo je ipak mozda visak - razmisliti
		Code.putJump(loopStartAdr.pop());

		for (int list : andCondList.peek()) {
			Code.fixup(list);
		}

		for (int list : breakAddr.peek()) {
			Code.fixup(list);
		}

		orCondList.pop();
		andCondList.pop();
		breakAddr.pop();
	}

	// ovde treba da se skoci sa svih ispunjenih uslova koji se or-uju
	public void visit(WhileStart whileStart) {
		for (int list : orCondList.peek()) {
			Code.fixup(list);
		}
	}

	public void visit(WhileCond whileCond) {
		loopStartAdr.push(Code.pc);
		orCondList.push(new ArrayList<Integer>());
		andCondList.push(new ArrayList<Integer>());
		breakAddr.push(new ArrayList<Integer>());
	}

	public void visit(BreakStm bs) {
		Code.putJump(0);
		breakAddr.peek().add(Code.pc - 2);
	}

	public void visit(ContinueStm cs) {
		Code.putJump(loopStartAdr.peek());
	}

	public void visit(IfStart ifStart) {
		orCondList.push(new ArrayList<Integer>());
		andCondList.push(new ArrayList<Integer>());
		elseEndAdr.push(new ArrayList<Integer>());
	}

	// ako ima else-a, sa kraja Then-a se skace na kraj Else-a
	// na pocetak else-a se skace ako nije ispunjen neki od and uslova
	public void visit(ElseStart elseStart) {
		Code.putJump(0);

		elseEndAdr.peek().add(Code.pc - 2);
		
		for (int list : andCondList.peek()) {
			Code.fixup(list);
		}
	}

	// na kraj then-a se skace kada izraz koji se 'and-uje' nije tacan, a nema
	// else-a
	public void visit(UnmatchedIf unmIf) {
		for (int list : andCondList.peek()) {
			Code.fixup(list);
		}
		
		orCondList.pop();
		andCondList.pop();
		elseEndAdr.pop();
	}
	
	// na kraj else-a se skace sa kraja then-a
	public void visit(MatchedIfElseStatement ifElse) {
		
		for (int list : elseEndAdr.peek()) {
			Code.fixup(list);
		}
		
		orCondList.pop();
		andCondList.pop();
		elseEndAdr.pop();
	}

	public void visit(ForEachStart forEachStart) {
		Obj desArrayNode = forEachStart.getDesignator().obj;

		breakAddr.push(new ArrayList<Integer>());

		Code.load(desArrayNode); // expr_st: addr
		Code.loadConst(-1); // expr_st: addr -1 (jer ide posle +1, pa da bi prvi put bila nula)

		loopStartAdr.push(Code.pc);

		Code.loadConst(1);
		Code.put(Code.add); // inkrementiraj index

		Code.put(Code.dup); // expr_st: addr i i
		Code.load(desArrayNode); // expr_st: addr i i addr
		Code.put(Code.arraylength); // expr_st: addr i i len

		// proveri da li je index manji od broja elemenata u nizu, skoci ako nije
		Code.putFalseJump(Code.lt, 0); // expr_st: addr i
		forEachSkipAdr.push(Code.pc - 2);
		Code.put(Code.dup2); // expr_st: addr i addr i

		// ucitaj element niza
		if (desArrayNode.getType().getElemType().getKind() == Struct.Char) {
			Code.put(Code.baload);
		} else {
			Code.put(Code.aload);
		} // expr_st: addr i elem

		// storuj ucitan element niza u iterator
		Code.store(forEachStart.obj); // expr_st: addr i !!!!!!!! pop-ovati !!!!!
	}

	public void visit(ForEachEnd forEachEnd) {
		Code.putJump(loopStartAdr.peek());

		Code.fixup(forEachSkipAdr.peek());

		for (int list : breakAddr.peek()) {
			Code.fixup(list);
		}
	}

	public void visit(ForEachStm fes) {
		breakAddr.pop();
		loopStartAdr.pop();
		forEachSkipAdr.pop();
		Code.put(Code.pop); // pop zaostale adrese i indeksa
		Code.put(Code.pop);
	}

	public void visit(FindAnyStart findAnyStart) {

		Obj desArrayNode = findAnyStart.getDesignator().obj;

		// expr_st: expr
		Code.loadConst(-1); // expr_st: expr -1 (jer ide posle +1, pa da bi prvi put bila nula)

		loopStartAdr.push(Code.pc);

		Code.loadConst(1);
		Code.put(Code.add); // inkrementiraj index // expr_st: expr i

		Code.put(Code.dup2); // expr_st: expr i expr i
		Code.load(desArrayNode); // expr_st: expr i expr i addr
		Code.put(Code.arraylength); // expr_st: expr i expr i len

		// proveri da li je index manji od broja elemenata u nizu, skoci ako nije
		Code.putFalseJump(Code.lt, 0); // expr_st: expr i expr
		skipFindAny.push(Code.pc - 2);
		Code.put(Code.dup2); // expr_st: expr i expr i expr
		Code.put(Code.pop); // expr_st: expr i expr i
		Code.load(desArrayNode); // expr_st: expr i expr i addr
		Code.put(Code.dup_x1); // expr_st: expr i expr addr i addr
		Code.put(Code.pop);  // expr_st: expr i expr addr i
		
		// ucitaj element niza
		if (desArrayNode.getType().getElemType().getKind() == Struct.Char) {
			Code.put(Code.baload);
		} else {
			Code.put(Code.aload);
		} // expr_st: expr i expr elem

		Code.putFalseJump(Code.ne, 0); // expr_st: expr i 
		elementFoundJumpAdr.push(Code.pc - 2);
		Code.putJump(loopStartAdr.peek());

	}
	
	public void visit(FindAnyStm findAnyStm) {
		
		Code.fixup(elementFoundJumpAdr.peek());
		Code.loadConst(1); //ovde se skace ako je nadjen element niza koji je jednak expr
		Code.putJump(0); //preskoci pushovanje nule; jmp zauzima 3 bajta i push(0) 1 bajt, pa zato +4
		int currPc = Code.pc - 2;
		Code.fixup(skipFindAny.peek());
		Code.loadConst(0); //ovde se skace ako nije nadjen element niza koji je jednak expr
		Code.fixup(currPc);
		Code.store(findAnyStm.getDesignator().obj); //storuj u levi designator
		
		loopStartAdr.pop();
		elementFoundJumpAdr.pop();
		skipFindAny.pop();
		
		Code.put(Code.pop); // pop zaostali expr_st : expr i expr
		Code.put(Code.pop);
		
	}
	
	public void visit(FindAndReplaceStart stm) {

		Obj desArrayNode = stm.getDesignator1().obj;
		Obj desArray2Node = array2 = stm.getDesignator().obj;
		
		//array2 mora da se napravi pre nego sto se u njega nesto upise
		Code.load(desArrayNode);
		Code.put(Code.arraylength);
		Code.put(Code.newarray);
		Code.put(1);
		Code.store(desArray2Node);

		// expr_st: expr1
		Code.loadConst(-1); // expr_st: expr1 -1 (jer ide posle +1, pa da bi prvi put bila nula)

		findAndReplaceStartAdr.push(Code.pc);

		Code.loadConst(1);
		Code.put(Code.add); // inkrementiraj index // expr_st: expr1 i
		
		Code.put(Code.dup2); // expr_st: expr1 i expr1 i
		Code.load(desArrayNode); // expr_st: expr1 i expr1 i addr
		Code.put(Code.arraylength); // expr_st: expr1 i expr1 i len
		
		Code.putFalseJump(Code.lt, 0); // expr_st: expr1 i expr1
		findAndReplaceEndAdr.push(Code.pc - 2);
		Code.put(Code.dup2); // expr_st: expr1 i expr1 i expr1
		Code.put(Code.pop); // expr_st: expr1 i expr1 i
		Code.load(desArrayNode); // expr_st: expr1 i expr1 i addr
		Code.put(Code.dup_x1); // expr_st: expr1 i expr1 addr i addr
		Code.put(Code.pop); // expr_st: expr1 i expr1 addr i
		
		// ucitaj element niza
		if (desArrayNode.getType().getElemType().getKind() == Struct.Char) {
			Code.put(Code.baload);
		} else {
			Code.put(Code.aload);
		} // expr_st: expr1 i expr1 elem
		
		Code.put(Code.dup2); // expr_st: expr1 i expr1 elem expr1 elem
		
		Code.put(Code.dup); // expr_st: expr1 i expr1 elem expr1 elem elem
		
		Code.store(stm.getFindAndReplaceIter().obj); // expr_st: expr1 i expr1 elem expr1 elem 
		
		//skoci ako su expr i element isti
		Code.putFalseJump(Code.eq, 0); // expr_st: expr1 i expr1 elem
		elementFoundJumpAdrSAR.push(Code.pc - 2);
	}

	public void visit(FindAndReplaceEnd stm) {
		//ovde skoci ako jesu isti
		/////////////////////////ovde je potencijalno greska//////////////////////////
		// expr_st: expr1 i expr1 elem expr2
		Code.put(Code.dup_x2); // expr_st: expr1 i expr2 expr1 elem expr2
		Code.put(Code.pop); // expr_st: expr1 i expr2 expr1 elem
		Code.put(Code.pop); // expr_st: expr1 i expr2 expr1
		Code.put(Code.pop); // expr_st: expr1 i expr2
		
		Code.put(Code.dup2); // expr_st: expr1 i expr2 i expr2
		Code.load(array2); // expr_st: expr1 i expr2 i expr2 addr
		Code.put(Code.dup_x2); // expr_st: expr1 i expr2 addr i expr2 addr
		Code.put(Code.pop); // expr_st: expr1 i expr2 addr i expr2
		
		if (array2.getType().getElemType().getKind() == Struct.Char) {
			Code.put(Code.bastore);
		} else {
			Code.put(Code.astore);
		} // expr_st: expr1 i expr2
		
		Code.put(Code.pop); // expr_st: expr1 i
		Code.putJump(findAndReplaceStartAdr.peek());
		
		// ako expr i elem nisu isti, storuj u niz2[i]
		Code.fixup(elementFoundJumpAdrSAR.peek()); 
		Code.put(Code.dup_x1); // expr_st: expr1 i elem expr1 elem
		Code.put(Code.pop); // expr_st: expr1 i elem expr1
		Code.put(Code.pop); // expr_st: expr1 i elem
		Code.put(Code.dup2); // expr_st: expr1 i elem i elem
		Code.load(array2); // expr_st: expr1 i elem i elem addr
		Code.put(Code.dup_x2); // expr_st: expr1 i elem addr i elem addr
		Code.put(Code.pop); // expr_st: expr1 i elem addr i elem
		
		if (array2.getType().getElemType().getKind() == Struct.Char) {
			Code.put(Code.bastore);
		} else {
			Code.put(Code.astore);
		} // expr_st: expr1 i elem
		
		Code.put(Code.pop);
		Code.putJump(findAndReplaceStartAdr.peek());
	}
	
	public void visit(FindAndReplaceStm stm) {
		Code.fixup(findAndReplaceEndAdr.peek());
		
		findAndReplaceStartAdr.pop();
		elementFoundJumpAdrSAR.pop();
		findAndReplaceEndAdr.pop();
	}
	
	/********************************
	 * visit methods for Designator *
	 ********************************/

	public void visit(Design var) {
		Obj varNode = var.obj;
		SyntaxNode varPar = var.getParent();

		// ako se pristupa polju klase ili metodi klase, mora da se stavi this, a this
		// je nulti lokalni parametar
		if (varNode.getKind() == Obj.Fld) {
			if (varPar.getClass() == DesignatorStatement.class || varPar.getClass() == FactDesign.class) {
				Code.put(Code.load_n + 0);
			}
		} else if (varNode.getKind() == Obj.Meth && classMethods.contains(varNode)) {
			Code.put(Code.load_n + 0);
		}

		// sem gore navedenog, design se moze jos javiti kao lokalna ili globalna
		// promenljiva, ili naziv funkcije koja nije deo klase i u tom slucaju se nista
		// ne radi

	}

	public void visit(DesignField field) {
		Code.load(field.getDesignator().obj);
	}
	
	//ovo je dodato da bi &arr bila dublje u sintaksnom stablu, pa onda nemam problem sa astore!
	public void visit(ArrayDes array) {
		Code.load(array.getDesignator().obj);
	}

	/**************************
	 * visit methods for Cond *
	 **************************/

	public void visit(CondFacts cond) {

		// condFact se nalazi samom kao deo AND izraza, a kada se proveravaju AND
		// izrazi, skace se pri prvom nailasku na netacan izraz
		Relop operation = cond.getRelop();
		if (operation.getClass() == EqualTo.class) {
			Code.putFalseJump(Code.eq, 0); // 0 se stavlja sada kada se ne zna u okviru cega se proverava uslov, pa se
											// posle patch-uje
		} else if (operation.getClass() == NotEqualTo.class) {
			Code.putFalseJump(Code.ne, 0);
		} else if (operation.getClass() == Greater.class) {
			Code.putFalseJump(Code.gt, 0);
		} else if (operation.getClass() == GreaterOrEq.class) {
			Code.putFalseJump(Code.ge, 0);
		} else if (operation.getClass() == Less.class) {
			Code.putFalseJump(Code.lt, 0);
		} else if (operation.getClass() == LessOrEq.class) {
			Code.putFalseJump(Code.le, 0);
		}

		andCondList.peek().add(Code.pc - 2); // dodati mesto na kojem treba popuniti adresu za skakanje
	}

	public void visit(OneCondFact cond) {
		// za skakanje se mora proveriti uslov, a uslov da bi se proverio, moraju se
		// koristiti dva operanda - u ovom slucaju expr i 1, jer je 1 true
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, 0);
		andCondList.peek().add(Code.pc - 2);
	}

	// ovo se nalazi posle svakog niza AND izraza
	public void visit(CheckAndCond cond) {
		Code.putJump(0); // kada imamo or, onda se u then granu skace vec ako je jedan tacan uslov
		orCondList.peek().add(Code.pc - 2);

		for (int list : andCondList.peek()) {
			Code.fixup(list); // kad se zavrsi niz provera uslova koji se 'and-uju', onda mogu da se patchuju
								// svi skokovi trenutnom pc adresom (skace se na proveru nekih drugih uslova,
								// koji se 'or-uju')
		}
		andCondList.peek().clear();
	}

	// ovo se nalazi posle celog condition-a **proveriti da se nisam mozda zeznula**
	public void visit(IFCondition cond) {
		for (int list : orCondList.peek()) {
			Code.fixup(list); // kad se zavrsi niz provera uslova koji se 'or-uju', onda mogu da se patchuju
								// svi skokovi trenutnom pc adresom, jer se skace odmah tu, u then granu
		}
		orCondList.peek().clear();
	}

	/**************************
	 * visit methods for Expr *
	 **************************/

	// kad se dovde dodje, na steku se vec nalaze vrednosti nad kojim treba da se
	// obave operacije

public void visit(NegativeExpression negExpr) {
		Code.put(Code.neg);
	}

	public void visit(PlusTerm addExpr) {
		Addop op = addExpr.getAddop();
		if (op.getClass() == Plus.class) {
			Code.put(Code.add);
		} else if (op.getClass() == Minus.class) {
			Code.put(Code.sub);
		}
	}

	public void visit(TermMul mulExpr) {
		Mulop op = mulExpr.getMulop();
		if (op.getClass() == Mul.class) {
			Code.put(Code.mul);
		} else if (op.getClass() == Div.class) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}

	/****************************
	 * visit methods for Factor *
	 ****************************/

	public void visit(FactFuncCall funcCall) {
		String funcCalledName = funcCall.getDesignator().obj.getName();
		
		if ("len".equalsIgnoreCase(funcCalledName)) {
    		Code.put(Code.arraylength);
    		return;
    	}
    	else if ("ord".equalsIgnoreCase(funcCalledName)) {
    		return;
    	}
    	else if ("chr".equalsIgnoreCase(funcCalledName)) {
    		return;
    	}
		
		int offset = funcCall.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	public void visit(FactDesign des) {
		Code.load(des.getDesignator().obj);
	}

	public void visit(FactNum number) {
		Code.loadConst(number.getN1());
	}

	public void visit(FactChar character) {
		Code.loadConst(character.getC1());
	}

	public void visit(FactBool b) {
		int val;
		val = (b.getB1() == true) ? 1 : 0;
		Code.loadConst(val);
	}

	public void visit(FactNewArray newArray) {

		Code.put(Code.newarray);
		if (newArray.getType().struct.getKind() == Struct.Char) {
			Code.put(0);
		} else
			Code.put(1);
	}

	public void visit(FactNewObject newObj) {
		Code.put(Code.new_);
		Code.put(newObj.getType().struct.getNumberOfFields() * 4);
		// svako polje je 4 bajta, tako da je size broj polja * 4
	}
}
