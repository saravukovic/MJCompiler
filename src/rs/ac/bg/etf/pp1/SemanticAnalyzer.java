package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean errorDetected = false;
	int printCallCount = 0;
	int formParsCntInCurrMeth = 0;
	Obj currentMethod = null;
	Struct currentVarType = null;
	Type currClassVarType = null;
	Obj currentClass = null;
	Struct currentExpr = null;
	Struct currentCondType = null;
	boolean returnFound = false;
	boolean classMethod = false;
	boolean inLoop = false;
	ArrayList<Struct> listOfFacts = new ArrayList<Struct>();
	Stack<ArrayList<Struct>> actualParsStack = new Stack<ArrayList<Struct>>();

	int nVars;

	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	/********************************
	 * private functions
	 ********************************/
	private void checkAndAddSingleConstant(SingleConstDecl singleConst, Struct type, int line) {

		String name = singleConst.getConstName();

		Obj constNode = singleConst.obj = Tab.find(name);

		if (constNode == Tab.noObj) {

			Constant c = singleConst.getConstant();

			if (!c.struct.compatibleWith(type)) {
				report_error("Semanticka greska na liniji " + line + ": konstanta i tip konstante se ne poklapaju.",
						null);
				return;
			}

			constNode = Tab.insert(Obj.Con, name, type);

			switch (type.getKind()) {
			case Struct.Bool:
				constNode.setAdr((((BoolConstant) c).getVal() == true) ? 1 : 0);
				break;
			case Struct.Int:
				constNode.setAdr(((IntConstant) c).getVal());
				break;
			default:
				constNode.setAdr(((CharConstant) c).getVal());
				break;
			}

			constNode.setLevel(0);

			report_info("Definisana je konstanta " + name + " cija je vrednost = " + constNode.getAdr(), singleConst);

			singleConst.obj = constNode;

		} else {
			report_error("Semanticka greska na liniji " + line + ": konstanta sa imenom " + name + " vec postoji",
					null);
		}

	}

	private void checkActualParamsOfFuncCall(String funcName, int funcCallLine) {

		Obj funcDecl = Tab.find(funcName);

		Collection<Obj> formParsWithLocalVars = funcDecl.getLocalSymbols();
		ArrayList<Struct> actPars = actualParsStack.peek();
		ArrayList<Obj> formPars = new ArrayList<Obj>();

		for (Obj formPar : formParsWithLocalVars) {
			formPars.add(formPar);
			if (formPars.size() == funcDecl.getLevel())
				break;
		}

		if (formPars.size() != actPars.size()) {
			report_error(
					"Semanticka greska na liniji " + funcCallLine
							+ ": broj argumenata funkcije se mora poklapati sa brojem formalnih parametara funcije!",
					null);
		} else {
			int i = 0;
			for (Obj formPar : formPars) {
				Struct fpStruct = formPar.getType();
				Struct apStruct = actPars.get(i);
				if (!StructExt.compatibleWithForAssignment(fpStruct, apStruct) && !funcName.equalsIgnoreCase("len")) {
					report_error(
							"Semanticka greska na liniji " + funcCallLine
									+ ": tipovi formalnog i stvarnog argumenta funkcije moraju biti kompatibilni!",
							null);
					break;
				} else if(funcName.equalsIgnoreCase("len")) {
					apStruct.assignableTo(fpStruct);
				}
				i++;
			}
		}
	}

	/********************************
	 * visit methods for PROGRAM
	 ********************************/
	public void visit(Program program) {
		Obj main = Tab.find("main");
		int mainKind = main.getKind();
		Struct mainType = main.getType();
		int mainArg = main.getLevel();

		if (mainKind != Obj.Meth || mainType != Tab.noType || mainArg != 0) {
			report_error("Metoda main mora postojati kao globalna void funkcija, bez argumenata.", program);
		}

		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}

	public void visit(ProgramName programName) {
		programName.obj = Tab.insert(Obj.Prog, programName.getProgName(), Tab.noType);
		Tab.openScope();
	}

	/********************************
	 * visit methods for CONSTANTS
	 ********************************/
	public void visit(CharConstant constCharValue) {
		constCharValue.struct = Tab.charType;
	}

	public void visit(IntConstant constNumValue) {
		constNumValue.struct = Tab.intType;
	}

	public void visit(BoolConstant constBoolValue) {
		constBoolValue.struct = TabExt.boolType;
	}

	public void visit(SingleConstDecl constDecl) {
		checkAndAddSingleConstant(constDecl, currentVarType, constDecl.getLine());
	}

	/********************************
	 * visit methods for VARIABLES
	 ********************************/
	
	public void visit(ArrayVariable array) {
		String name = array.getName();

		Obj arrayNode = Tab.find(name);

		if (arrayNode == Tab.noObj) {

			arrayNode = Tab.insert(Obj.Var, name, new Struct(Struct.Array, currentVarType));

		} else {
			report_error("Semanticka greska na liniji " + array.getLine() + ": promenljiva sa imenom " + name
					+ " vec postoji", null);
		}
	}

	public void visit(OneVariable var) {
		String name = var.getName();

		Obj varNode = Tab.find(name);

		if (varNode == Tab.noObj) {

			varNode = Tab.insert(Obj.Var, name, currentVarType);

			report_info("Definisana je promenljiva " + name, var);

		} else {
			report_error(
					"Semanticka greska na liniji " + var.getLine() + ": promenljiva sa imenom " + name + " vec postoji",
					null);
		}
	}

	/********************************
	 * visit methods for METHODS
	 ********************************/

	public void visit(MethodType type) {
		type.struct = type.getType().struct;
	}

	public void visit(MethodVoid type) {
		type.struct = Tab.noType;
	}

	public void visit(MethDeclTypeAndName methDecl) {

		String name = methDecl.getMethName();
		Struct type = methDecl.getTypeOrVoid().struct;

		Obj methNode = Tab.find(name);

		if (methNode == Tab.noObj) {

			methNode = Tab.insert(Obj.Meth, name, type);

			report_info("Obradjuje se funkcija " + methDecl.getMethName(), methDecl);

			Tab.openScope();

			if (classMethod) {
				Tab.insert(Obj.Var, "this", currentClass.getType());
			}

			methDecl.obj = currentMethod = methNode;
			formParsCntInCurrMeth = 0;

		} else {
			report_error("Semanticka greska na liniji " + methDecl.getLine() + ": funkcija sa imenom " + name
					+ " je vec deklarisana", null);
			methDecl.obj = currentMethod = Tab.noObj;
		}

	}

	public void visit(Method method) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semanticka greska na liniji " + method.getLine() + ": funkcija " + currentMethod.getName()
					+ " nema return iskaz!", null);
		}

		if (classMethod)
			formParsCntInCurrMeth++;
		currentMethod.setLevel(formParsCntInCurrMeth);
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();

		returnFound = false;
		currentMethod = null;
	}

	/********************************
	 * visit methods for FormParams *
	 ********************************/
	public void visit(FormParameterArray array) {
		String arrayName = array.getFormParName();
		Obj arrayNode = Tab.find(arrayName);
		Obj currScopeArrNode = Tab.currentScope.findSymbol(arrayName);

		if (Tab.noObj == arrayNode || (Tab.noObj != arrayNode && currScopeArrNode == null)) {
			Tab.insert(Obj.Var, arrayName, new Struct(Struct.Array, array.getType().struct));
			report_info("Definisan je formalni argument " + arrayName, array);
			formParsCntInCurrMeth++;
		} else {
			report_error("Semanticka greska na liniji " + array.getLine() + ": argument sa imenom " + arrayName
					+ " vec postoji", null);
		}
	}

	public void visit(FormParameter var) {
		String varName = var.getFormParName();
		Obj varNode = Tab.find(varName);
		Obj currScopeVarNode = Tab.currentScope.findSymbol(varName);

		if (Tab.noObj == varNode || (Tab.noObj != varNode && currScopeVarNode == null)) {
			Tab.insert(Obj.Var, varName, var.getType().struct);
			report_info("Definisan je formalni argument " + varName, var);
			formParsCntInCurrMeth++;
		} else {
			report_error(
					"Semanticka greska na liniji " + var.getLine() + ": argument sa imenom " + varName + " vec postoji",
					null);
		}
	}

	/********************************
	 * visit methods for CLASSES *
	 ********************************/
	public void visit(ClassNameNoExt className) {

		String name = className.getName();

		Obj classNode = Tab.find(name);

		if (classNode == Tab.noObj) {

			className.struct = new Struct(Struct.Class);

			currentClass = classNode = Tab.insert(Obj.Type, name, className.struct);

			Tab.openScope();

			report_info("Obradjuje se klasa " + name, className);
			
			Tab.insert(Obj.Var, "this", classNode.getType());

			classMethod = true;
		} else {
			report_error(
					"Semanticka greska na liniji " + className.getLine() + ": klasa sa imenom " + name + " vec postoji",
					null);
			currentClass = Tab.noObj;
			Tab.openScope();
		}

	}

	public void visit(ClassNameExt className) {

		String name = className.getName();
		Type classExtType = className.getType();
		String extTypeName = classExtType.getTypeName();

		Obj classNode = Tab.find(name);

		if (classNode == Tab.noObj) {

			Obj classExtTypeNode = Tab.find(extTypeName);

			if (classExtTypeNode == Tab.noObj) {
				report_error("Semanticka greska na liniji " + className.getLine() + ": klasa sa imenom " + extTypeName
						+ " ne postoji", null);
				currentClass = Tab.noObj;
				Tab.openScope();
				return;
			} else if (classExtTypeNode.getType().getKind() != Struct.Class) {
				report_error("Semanticka greska na liniji " + className.getLine()
						+ ": tip iz kog pokusava da se izvede klada, " + extTypeName + " nije klasa", null);
				currentClass = Tab.noObj;
				Tab.openScope();
				return;
			}

			className.struct = new Struct(Struct.Class, classExtType.struct);

			currentClass = Tab.insert(Obj.Type, name, className.struct);

			Tab.openScope();

			Tab.insert(Obj.Var, "this", classNode.getType());

			report_info("Obradjuje se klasa " + name, className);

			classMethod = true;
		} else {
			report_error(
					"Semanticka greska na liniji " + className.getLine() + ": klasa sa imenom " + name + " vec postoji",
					null);
			currentClass = Tab.noObj;
			Tab.openScope();
		}

	}

	public void visit(ClassVarDeclType classVarDeclType) {
		currClassVarType = classVarDeclType.getType();
	}

	public void visit(ArrayClassVariable array) {
		String name = array.getName();

		Obj arrayNode = Tab.find(name);

		if (arrayNode == Tab.noObj) {

			arrayNode = Tab.insert(Obj.Fld, name, new Struct(Struct.Array, currClassVarType.struct));

			report_info("Definisan je niz " + name, array);

		} else {
			report_error("Semanticka greska na liniji " + array.getLine() + ": promenljiva sa imenom " + name
					+ " vec postoji", null);
		}
	}

	public void visit(OneClassVariable var) {
		String name = var.getName();

		Obj varNode = Tab.find(name);

		if (varNode == Tab.noObj) {

			varNode = Tab.insert(Obj.Fld, name, currClassVarType.struct);

			report_info("Definisana je promenljiva " + name, var);

		} else {
			report_error(
					"Semanticka greska na liniji " + var.getLine() + ": promenljiva sa imenom " + name + " vec postoji",
					null);
		}
	}

	public void visit(ClassMethodType method) {
		classMethod = true;
	}

	public void visit(ClassDecl classDecl) {
		Tab.chainLocalSymbols(currentClass.getType());
		Tab.closeScope();
		currentClass = null;
		currClassVarType = null;
		classMethod = false;
	}

	/********************************
	 * visit methods for TYPE *
	 ********************************/

	public void visit(Type type) {
		String typeName = type.getTypeName();

		Obj typeNode = Tab.find(typeName);

		if (Tab.noObj == typeNode) {
			report_error("Semanticka greska na liniji " + type.getLine() + ": tip " + typeName + " ne postoji", null);
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
				currentVarType = type.struct;
			} else {
				report_error("Semanticka greska na liniji " + type.getLine() + ": naziv " + typeName + " nije tip",
						null);
				type.struct = Tab.noType;
			}
		}
		currentVarType = type.struct;
	}

	/******************************************
	 * visit methods for DesignatorStatements *
	 ******************************************/

	public void visit(DesignAssign assign) {
		Designator des = assign.getDesignator();
		Expr expr = assign.getExpr();
		int desKind = des.obj.getKind();
		Struct desType = des.obj.getType();
		
		if(desType.getKind() == Struct.Array) {
			desType = desType.getElemType();
		}

		if (desKind != Obj.Var && desKind != Obj.Elem && desKind != Obj.Fld) {
			report_error("Semanticka greska na liniji " + assign.getLine() + ": " + des.obj.getName()
					+ " mora biti promenljiva, element niza ili polje objekta.", null);
			return;
		}

		if (!StructExt.compatibleWithForAssignment(desType, expr.struct)) {
			report_error("Semanticka greska na liniji " + assign.getLine() + ": tipovi moraju biti kompatibilni.",
					null);
			return;
		}

	}

	public void visit(DesignFuncCall funcCall) {
		Obj funcCallNode = funcCall.getDesignator().obj;
		int kind = funcCallNode.getKind();

		if (kind != Obj.Meth) {
			report_error("Semanticka greska na liniji " + funcCall.getLine() + ": "
					+ funcCall.getDesignator().obj.getName() + " mora biti globalna funkcija ili metoda klase.", null);
			return;
		}

		if (Tab.noType == funcCallNode.getType()) {
			report_error("Semanticka greska " + funcCallNode.getName()
					+ " ne moze se koristiti u izrazima jer nema povratnu vrednost ", funcCall);
			return;
		}

		report_info("Pronadjen poziv funkcije " + funcCallNode.getName() + " na liniji " + funcCall.getLine(), null);

		checkActualParamsOfFuncCall(funcCall.getDesignator().obj.getName(), funcCall.getLine());

		actualParsStack.pop();
	}

	public void visit(FunctionCalled f) {
		actualParsStack.add(new ArrayList<Struct>());
	}

	public void visit(DesignInc designInc) {
		Obj desObj = designInc.getDesignator().obj;
		int desKind = desObj.getKind();

		if (desKind != Obj.Var && desKind != Obj.Elem && desKind != Obj.Fld) {
			report_error("Semanticka greska na liniji " + designInc.getLine() + ": " + desObj.getName()
					+ " mora biti promenljiva, element niza ili polje objekta.", null);
			return;
		}

		if (desObj.getType().getKind() != Struct.Int) {
			report_error("Semanticka greska na liniji " + designInc.getLine() + ": " + desObj.getName()
					+ " mora biti tipa int.", null);
			return;
		}
	}

	public void visit(DesignDec designDec) {
		Obj desObj = designDec.getDesignator().obj;
		int desKind = desObj.getKind();

		if (desKind != Obj.Var && desKind != Obj.Elem && desKind != Obj.Fld) {
			report_error("Semanticka greska na liniji " + designDec.getLine() + ": " + desObj.getName()
					+ " mora biti promenljiva, element niza ili polje objekta.", null);
			return;
		}

		if (desObj.getType().getKind() != Struct.Int) {
			report_error("Semanticka greska na liniji " + designDec.getLine() + ": " + desObj.getName()
					+ " mora biti tipa int.", null);
			return;
		}
	}

	/*********************************
	 * visit methods for Statement *
	 *********************************/

	public void visit(ReadStm readStm) {
		Obj desObj = readStm.getDesignator().obj;
		int desKind = desObj.getKind();

		if (desKind != Obj.Var && desKind != Obj.Elem && desKind != Obj.Fld) {
			report_error("Semanticka greska na liniji " + readStm.getLine() + ": " + desObj.getName()
					+ " mora biti promenljiva, element niza ili polje objekta.", null);
			return;
		}

		if (desObj.getType().getKind() != Struct.Int && desObj.getType().getKind() != Struct.Char
				&& desObj.getType().getKind() != Struct.Bool) {
			report_error("Semanticka greska na liniji " + readStm.getLine() + ": " + desObj.getName()
					+ " mora biti tipa int, char ili bool.", null);
			return;
		}
	}

	public void visti(PrintStm printStm) {
		int exprKind = printStm.getExpr().struct.getKind();

		if (exprKind != Struct.Int && exprKind != Struct.Char && exprKind != Struct.Bool) {
			report_error(
					"Semanticka greska na liniji " + printStm.getLine() + ": izraz mora biti tipa int, char ili bool.",
					null);
			return;
		}
	}

	public void visit(ReturnStm returnStm) {
		returnFound = true;
		if (currentMethod == null) {
			report_error("Greska na liniji " + returnStm.getLine()
					+ " : return metoda se mora nalaziti unutar globalne funkcije ili metode klase.", null);
		}

		Struct currMethType = currentMethod.getType();
		if (!currMethType.compatibleWith(returnStm.getExpr().struct)) {
			report_error("Greska na liniji " + returnStm.getLine() + " : "
					+ "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije "
					+ currentMethod.getName(), null);
		}
	}
	
	public void visit(WhileCondition whileCond) {
		if (currentCondType.getKind() != Struct.Bool) {
			report_error("Greska na liniji " + whileCond.getLine() + " : " + "tip uslova mora biti BOOL!", null);
		}
	}
	
	public void visit(WhileStart whileStart) {
		inLoop = true;
	}

	public void visit(WhileLoopEnd end) {
		inLoop = false;
	}

	public void visit(IfCond ifCond) {
		if (currentCondType.getKind() != Struct.Bool) {
			report_error("Greska na liniji " + ifCond.getLine() + " : " + "tip uslova mora biti BOOL!", null);
		}
	}

	public void visit(BreakStm breakStm) {
		if (!inLoop) {
			report_error("Greska na liniji " + breakStm.getLine() + " : "
					+ "naredba break moze da se nadje samo u okviru while petlje", null);
		}
	}

	public void visit(ContinueStm continueStm) {
		if (!inLoop) {
			report_error("Greska na liniji " + continueStm.getLine() + " : "
					+ "naredba continue moze da se nadje samo u okviru while petlje", null);
		}
	}

	public void visit(ForEachEnd forEachEnd) {
		inLoop = false;
	}
	
	public void visit(ForEachStart forEachStart) {
		inLoop = true;
		Designator des = forEachStart.getDesignator();
		String iter = forEachStart.getIdent();
		Obj iterNode = Tab.find(iter);

		forEachStart.obj = iterNode;
		
		if (iterNode == Tab.noObj) {
			report_error("Semanticka greska na liniji " + forEachStart.getLine() + ": " + iter
					+ " nije prethodno deklarisana promenljiva.", null);
			return;
		}
		
		if (des.obj == Tab.noObj) {
			report_error("Niz na liniji " + forEachStart.getLine() + ": " + iter
					+ " nije prethodno deklarisan.", null);
			return;
		}

		if (iterNode.getType().getKind() != des.obj.getType().getElemType().getKind()) {
			report_error("Semanticka greska na liniji " + forEachStart.getLine() + ": " + iter
					+ " mora biti istog tipa kao i elementi niza.", null);
			forEachStart.obj = Tab.noObj;
			return;
		}

		if (des.obj.getType().getKind() != Struct.Array) {
			report_error("Semanticka greska na liniji " + forEachStart.getLine()
					+ ": promenljiva sa leve strane jednakosti mora biti niz.", null);
			return;
		}
		
	}

	public void visit(FindAnyStm findAnyStm) {
		Designator leftDes = findAnyStm.getDesignator();

		if (leftDes.obj.getType().getKind() != Struct.Bool) {
			report_error("Semanticka greska na liniji " + findAnyStm.getLine() + ": " + leftDes.obj.getName()
					+ " mora biti tipa bool.", null);
			return;
		}
	}
	
	public void visit(FindAnyStart findAnyStart) {
		Designator des = findAnyStart.getDesignator();
		
		if (des.obj.getType().getKind() != Struct.Array  || des.obj.getType().getElemType().getKind() == Struct.Class) {
			report_error("Semanticka greska na liniji " + findAnyStart.getLine() + ": " + des.obj.getName()
					+ " mora biti niz ugradjenog tipa.", null);
			return;
		}
	}
	
	public void visit(FindAndReplaceIter iter) {
		Obj iterNode = Tab.find(iter.getIdent());
		iter.obj = iterNode;
	}
	
	public void visit(FindAndReplaceStart stm) {
		Designator rightDes = stm.getDesignator1();
		Designator leftDes = stm.getDesignator();
		Obj iterNode = stm.getFindAndReplaceIter().obj;
		
		if (rightDes.obj.getType().getKind() != Struct.Array || rightDes.obj.getType().getElemType().getKind() == Struct.Class || 
			leftDes.obj.getType().getKind() != Struct.Array || leftDes.obj.getType().getElemType().getKind() == Struct.Class) {
			report_error("Semanticka greska na liniji " + stm.getLine() + ": " + rightDes.obj.getName() + " i " + leftDes.obj.getName()
					+ " moraju biti nizovi ugradjenog tipa.", null);
			return;
		}
		
		if (iterNode == Tab.noObj) {
			report_error("Semanticka greska na liniji " + stm.getLine() + ": " + iterNode.getName()
					+ " nije prethodno deklarisana promenljiva.", null);
			return;
		}
		
		if (iterNode.getType().getKind() != rightDes.obj.getType().getElemType().getKind()) {
			report_error("Semanticka greska na liniji " + stm.getLine() + ": " + iterNode.getName()
					+ " mora biti istog tipa kao i elementi niza.", null);
			return;
		}
	}

	/*********************************
	 * visit methods for Designators *
	 *********************************/
	public void visit(Design var) {
		String varName = var.getDesignName();
		Obj varNode = Tab.find(varName);

		if (Tab.noObj == varNode) {
			report_error("Semanticka greska na liniji " + var.getLine() + ": promenljiva " + varName + " ne postoji",
					null);
			var.obj = Tab.noObj;
		}
		var.obj = varNode;
		if (varNode.getKind() == Obj.Meth) {
			report_info("Pristup funkciji " + varName, var);
		} else {
			report_info("Pristup promenljivoj " + varName, var);
		}
	}

	public void visit(DesignField field) {
		String className = field.getDesignator().obj.getName();
		String fieldName = field.getFieldName();

		Obj classNode = Tab.find(className);

		if (Tab.noObj == classNode) {
			report_error("Semanticka greska na liniji " + field.getLine() + ": klasa " + classNode + " ne postoji",
					null);
			field.obj = Tab.noObj;
			return;
		} else if (classNode.getType().getKind() != Struct.Class) {
			report_error("Semanticka greska na liniji " + field.getLine() + ": tip " + classNode + " nije klasa", null);
			field.obj = Tab.noObj;
			return;
		}

		Collection<Obj> classMembers = classNode.getType().getMembers();

		for (Obj mem : classMembers) {
			if (mem.getName().equals(fieldName)) {
				if (mem.getKind() == Obj.Meth) {
					report_info("Poziva se metod " + fieldName + " klase " + className, field);
					field.obj = mem;
				} else {
					report_info("Pristupa se polju " + fieldName + " klase " + className, field);
					field.obj = new Obj(Obj.Fld, fieldName, mem.getType());
				}
				return;
			}
		}

		report_error("Semanticka greska na liniji " + field.getLine() + ": polje/metoda " + fieldName
				+ " ne postoji u okviru klase " + className, field);
		field.obj = Tab.noObj;
	}

	public void visit(DesignArray array) {
		Expr expr = array.getExpr();
		int exprType = expr.struct.getKind();
		Obj arrayNode = array.getArrayDes().getDesignator().obj;


		if (arrayNode.getType().getKind() != Struct.Array) {
			report_error("Semanticka greska na liniji " + array.getLine() + ": " + arrayNode.getName() + " nije niz.", array);
			array.obj = Tab.noObj;
			return;
		}

		if (exprType != Struct.Int) {
			report_error("Semanticka greska na liniji " + array.getLine() + ": " + exprType + " nije tipa int.", array);
			array.obj = Tab.noObj;
			return;
		}
		arrayNode = new Obj(Obj.Elem, arrayNode.getName(), arrayNode.getType().getElemType());
		array.obj = arrayNode;
		report_info("Pristup clanu niza " + arrayNode.getName(), array);
	}

	/*********************************
	 * visit methods for Condition *
	 *********************************/

	public void visit(CondFacts cond) {
		Expr exprL = cond.getExpr();
		Expr exprR = cond.getExpr1();
		Relop relOp = cond.getRelop();

		if (!exprL.struct.compatibleWith(exprR.struct)) {
			report_error(
					"Semanticka greska na liniji " + cond.getLine() + ": tipovi promenljivih moraju biti kompatibilni.",
					null);
			currentCondType = Tab.noType;
			return;
		}

		if (exprL.struct.getKind() == Struct.Array || exprL.struct.getKind() == Struct.Class) {
			if (!(relOp.getClass() == EqualTo.class) && !(relOp.getClass() == NotEqualTo.class)) {
				report_error("Semanticka greska na liniji " + cond.getLine()
						+ ": uz nizove i strukture mogu da se koriste samo operacije == i !=.", null);
				currentCondType = Tab.noType;
				return;
			}
		}
		currentCondType = new Struct(Struct.Bool);
	}

	public void visit(OneCondFact cond) {
		Expr expr = cond.getExpr();
		currentCondType = expr.struct;
	}

	public void visit(Condition cond) {
		cond.struct = new Struct(Struct.Bool);
	}

	/*********************************
	 * visit methods for Expression *
	 *********************************/

	public void visit(NegativeExpression expr) {
		if (expr.getTerm().struct.getKind() != Struct.Int) {
			report_error("Semanticka greska na liniji " + expr.getLine() + ": izraz mora biti tipa int!", expr);
			expr.struct = Tab.noType;
			return;
		}
		expr.struct = Tab.intType;
	}
	
	public void visit(PlusTerm termList) {
		Expr tl = termList.getExpr();
		Term t = termList.getTerm();
		if (t.struct != Tab.intType || tl.struct != Tab.intType) {
			report_error("Semanticka greska na liniji " + termList.getLine() + ": oba operanda moraju biti tipa int",
					termList);
			termList.struct = Tab.noType;
			return;
		}
		termList.struct = Tab.intType;

	}

	public void visit(OneTerm term) {
		term.struct = term.getTerm().struct;
	}

	/*********************************
	 * visit methods for Term *
	 *********************************/

	public void visit(OneFactor term) {
		term.struct = term.getFactor().struct;
	}

	public void visit(TermMul term) {
		Struct t = term.getTerm().struct;
		Factor f = term.getFactor();
		if (t != Tab.intType || f.struct != Tab.intType) {
			report_error("Semanticka greska na liniji " + term.getLine() + ": oba operanda moraju biti tipa int",
					term);
			term.struct = Tab.noType;
			return;
		}
		term.struct = Tab.intType;
	}

	/*********************************
	 * visit methods for Factor *
	 *********************************/

	public void visit(FactFuncCall funcCall) {
		Designator func = funcCall.getDesignator();
		String funcName = func.obj.getName();

		Obj funcNode = Tab.find(funcName);

		if (Tab.noObj == funcNode) {
			report_error("Semanticka greska na liniji " + funcCall.getLine() + ": funkcija " + funcName
					+ " nije deklarisana.", funcCall);
			funcCall.struct = Tab.noType;
			return;
		}

		if (funcNode.getKind() != Obj.Meth) {
			report_error("Semanticka greska na liniji " + funcCall.getLine() + ": " + funcNode + " nije funkcija.",
					funcCall);
			funcCall.struct = Tab.noType;
			return;
		}

		funcCall.struct = funcNode.getType();

		report_info("Pronadjen poziv funkcije " + funcNode.getName() + " na liniji " + funcCall.getLine(), null);

		checkActualParamsOfFuncCall(funcCall.getDesignator().obj.getName(), funcCall.getLine());

		actualParsStack.pop();
	}

	public void visit(FactNum number) {
		number.struct = Tab.intType;
	}

	public void visit(FactChar character) {
		character.struct = Tab.charType;
	}

	public void visit(FactBool b) {
		b.struct = TabExt.boolType;
	}

	public void visit(FactNewArray newArray) {
		Expr expr = newArray.getExpr();
		Struct exprStruct = expr.struct;

		if (exprStruct.getKind() != Struct.Int) {
			report_error(
					"Semanticka greska na liniji " + newArray.getLine() + ": " + " izraz izmedju [] mora biti int tipa",
					newArray);
			newArray.struct = Tab.noType;
			return;
		}

		newArray.struct = new Struct(Struct.Array, newArray.getType().struct);
	}

	public void visit(FactNewObject newObject) {
		Struct objStruct = newObject.getType().struct;

		if (objStruct.getKind() != Struct.Class) {
			report_error("Semanticka greska na liniji " + newObject.getLine() + ": " + newObject.getType().getTypeName()
					+ " nije klasa.", newObject);
			newObject.struct = Tab.noType;
			return;
		}

		newObject.struct = objStruct;
	}

	public void visit(FactDesign des) {
		des.struct = des.getDesignator().obj.getType();
	}
	
	public void visit(FactExpr expr) {
		expr.struct = expr.getExpr().struct;
	}

	/****************************
	 * visit methods for ActPar *
	 ****************************/

	public void visit(ActPar actPar) {
		actualParsStack.peek().add(actPar.getExpr().struct);
	}

	public boolean errorDetected() {
		return errorDetected;
	}

}
