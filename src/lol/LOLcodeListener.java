// Generated from c:\Users\gmein\Documents\GitHub\eps\AP-Compilers\Compilers\src\lol\LOLcode.g4 by ANTLR 4.7.1

   package lol;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LOLcodeParser}.
 */
public interface LOLcodeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#literal_value}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_value(LOLcodeParser.Literal_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#literal_value}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_value(LOLcodeParser.Literal_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(LOLcodeParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(LOLcodeParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#vartype}.
	 * @param ctx the parse tree
	 */
	void enterVartype(LOLcodeParser.VartypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#vartype}.
	 * @param ctx the parse tree
	 */
	void exitVartype(LOLcodeParser.VartypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#foldable_arg}.
	 * @param ctx the parse tree
	 */
	void enterFoldable_arg(LOLcodeParser.Foldable_argContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#foldable_arg}.
	 * @param ctx the parse tree
	 */
	void exitFoldable_arg(LOLcodeParser.Foldable_argContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#sum}.
	 * @param ctx the parse tree
	 */
	void enterSum(LOLcodeParser.SumContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#sum}.
	 * @param ctx the parse tree
	 */
	void exitSum(LOLcodeParser.SumContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#diff}.
	 * @param ctx the parse tree
	 */
	void enterDiff(LOLcodeParser.DiffContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#diff}.
	 * @param ctx the parse tree
	 */
	void exitDiff(LOLcodeParser.DiffContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#product}.
	 * @param ctx the parse tree
	 */
	void enterProduct(LOLcodeParser.ProductContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#product}.
	 * @param ctx the parse tree
	 */
	void exitProduct(LOLcodeParser.ProductContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#quotient}.
	 * @param ctx the parse tree
	 */
	void enterQuotient(LOLcodeParser.QuotientContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#quotient}.
	 * @param ctx the parse tree
	 */
	void exitQuotient(LOLcodeParser.QuotientContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#bigger}.
	 * @param ctx the parse tree
	 */
	void enterBigger(LOLcodeParser.BiggerContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#bigger}.
	 * @param ctx the parse tree
	 */
	void exitBigger(LOLcodeParser.BiggerContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#smallr}.
	 * @param ctx the parse tree
	 */
	void enterSmallr(LOLcodeParser.SmallrContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#smallr}.
	 * @param ctx the parse tree
	 */
	void exitSmallr(LOLcodeParser.SmallrContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#min}.
	 * @param ctx the parse tree
	 */
	void enterMin(LOLcodeParser.MinContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#min}.
	 * @param ctx the parse tree
	 */
	void exitMin(LOLcodeParser.MinContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#max}.
	 * @param ctx the parse tree
	 */
	void enterMax(LOLcodeParser.MaxContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#max}.
	 * @param ctx the parse tree
	 */
	void exitMax(LOLcodeParser.MaxContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#mod}.
	 * @param ctx the parse tree
	 */
	void enterMod(LOLcodeParser.ModContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#mod}.
	 * @param ctx the parse tree
	 */
	void exitMod(LOLcodeParser.ModContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#not}.
	 * @param ctx the parse tree
	 */
	void enterNot(LOLcodeParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#not}.
	 * @param ctx the parse tree
	 */
	void exitNot(LOLcodeParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#same}.
	 * @param ctx the parse tree
	 */
	void enterSame(LOLcodeParser.SameContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#same}.
	 * @param ctx the parse tree
	 */
	void exitSame(LOLcodeParser.SameContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#and}.
	 * @param ctx the parse tree
	 */
	void enterAnd(LOLcodeParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#and}.
	 * @param ctx the parse tree
	 */
	void exitAnd(LOLcodeParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#either}.
	 * @param ctx the parse tree
	 */
	void enterEither(LOLcodeParser.EitherContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#either}.
	 * @param ctx the parse tree
	 */
	void exitEither(LOLcodeParser.EitherContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#one}.
	 * @param ctx the parse tree
	 */
	void enterOne(LOLcodeParser.OneContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#one}.
	 * @param ctx the parse tree
	 */
	void exitOne(LOLcodeParser.OneContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#all}.
	 * @param ctx the parse tree
	 */
	void enterAll(LOLcodeParser.AllContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#all}.
	 * @param ctx the parse tree
	 */
	void exitAll(LOLcodeParser.AllContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#any}.
	 * @param ctx the parse tree
	 */
	void enterAny(LOLcodeParser.AnyContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#any}.
	 * @param ctx the parse tree
	 */
	void exitAny(LOLcodeParser.AnyContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#concat}.
	 * @param ctx the parse tree
	 */
	void enterConcat(LOLcodeParser.ConcatContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#concat}.
	 * @param ctx the parse tree
	 */
	void exitConcat(LOLcodeParser.ConcatContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#cast}.
	 * @param ctx the parse tree
	 */
	void enterCast(LOLcodeParser.CastContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#cast}.
	 * @param ctx the parse tree
	 */
	void exitCast(LOLcodeParser.CastContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#var_ref}.
	 * @param ctx the parse tree
	 */
	void enterVar_ref(LOLcodeParser.Var_refContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#var_ref}.
	 * @param ctx the parse tree
	 */
	void exitVar_ref(LOLcodeParser.Var_refContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(LOLcodeParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(LOLcodeParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void enterVar_decl(LOLcodeParser.Var_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void exitVar_decl(LOLcodeParser.Var_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#var_rvalue}.
	 * @param ctx the parse tree
	 */
	void enterVar_rvalue(LOLcodeParser.Var_rvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#var_rvalue}.
	 * @param ctx the parse tree
	 */
	void exitVar_rvalue(LOLcodeParser.Var_rvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#var_assignment}.
	 * @param ctx the parse tree
	 */
	void enterVar_assignment(LOLcodeParser.Var_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#var_assignment}.
	 * @param ctx the parse tree
	 */
	void exitVar_assignment(LOLcodeParser.Var_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#arg_decl}.
	 * @param ctx the parse tree
	 */
	void enterArg_decl(LOLcodeParser.Arg_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#arg_decl}.
	 * @param ctx the parse tree
	 */
	void exitArg_decl(LOLcodeParser.Arg_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#more_args_decl}.
	 * @param ctx the parse tree
	 */
	void enterMore_args_decl(LOLcodeParser.More_args_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#more_args_decl}.
	 * @param ctx the parse tree
	 */
	void exitMore_args_decl(LOLcodeParser.More_args_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#args_decl}.
	 * @param ctx the parse tree
	 */
	void enterArgs_decl(LOLcodeParser.Args_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#args_decl}.
	 * @param ctx the parse tree
	 */
	void exitArgs_decl(LOLcodeParser.Args_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#return_type}.
	 * @param ctx the parse tree
	 */
	void enterReturn_type(LOLcodeParser.Return_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#return_type}.
	 * @param ctx the parse tree
	 */
	void exitReturn_type(LOLcodeParser.Return_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#func_decl}.
	 * @param ctx the parse tree
	 */
	void enterFunc_decl(LOLcodeParser.Func_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#func_decl}.
	 * @param ctx the parse tree
	 */
	void exitFunc_decl(LOLcodeParser.Func_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn_statement(LOLcodeParser.Return_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#return_statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn_statement(LOLcodeParser.Return_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#naked_arg}.
	 * @param ctx the parse tree
	 */
	void enterNaked_arg(LOLcodeParser.Naked_argContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#naked_arg}.
	 * @param ctx the parse tree
	 */
	void exitNaked_arg(LOLcodeParser.Naked_argContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterArg(LOLcodeParser.ArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitArg(LOLcodeParser.ArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#more_args}.
	 * @param ctx the parse tree
	 */
	void enterMore_args(LOLcodeParser.More_argsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#more_args}.
	 * @param ctx the parse tree
	 */
	void exitMore_args(LOLcodeParser.More_argsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(LOLcodeParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(LOLcodeParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#func_call}.
	 * @param ctx the parse tree
	 */
	void enterFunc_call(LOLcodeParser.Func_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#func_call}.
	 * @param ctx the parse tree
	 */
	void exitFunc_call(LOLcodeParser.Func_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#loop_action}.
	 * @param ctx the parse tree
	 */
	void enterLoop_action(LOLcodeParser.Loop_actionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#loop_action}.
	 * @param ctx the parse tree
	 */
	void exitLoop_action(LOLcodeParser.Loop_actionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#loop_condition}.
	 * @param ctx the parse tree
	 */
	void enterLoop_condition(LOLcodeParser.Loop_conditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#loop_condition}.
	 * @param ctx the parse tree
	 */
	void exitLoop_condition(LOLcodeParser.Loop_conditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#loop_end}.
	 * @param ctx the parse tree
	 */
	void enterLoop_end(LOLcodeParser.Loop_endContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#loop_end}.
	 * @param ctx the parse tree
	 */
	void exitLoop_end(LOLcodeParser.Loop_endContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(LOLcodeParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(LOLcodeParser.LoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#aswitch}.
	 * @param ctx the parse tree
	 */
	void enterAswitch(LOLcodeParser.AswitchContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#aswitch}.
	 * @param ctx the parse tree
	 */
	void exitAswitch(LOLcodeParser.AswitchContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#acase}.
	 * @param ctx the parse tree
	 */
	void enterAcase(LOLcodeParser.AcaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#acase}.
	 * @param ctx the parse tree
	 */
	void exitAcase(LOLcodeParser.AcaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#adefault}.
	 * @param ctx the parse tree
	 */
	void enterAdefault(LOLcodeParser.AdefaultContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#adefault}.
	 * @param ctx the parse tree
	 */
	void exitAdefault(LOLcodeParser.AdefaultContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#anif}.
	 * @param ctx the parse tree
	 */
	void enterAnif(LOLcodeParser.AnifContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#anif}.
	 * @param ctx the parse tree
	 */
	void exitAnif(LOLcodeParser.AnifContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#then}.
	 * @param ctx the parse tree
	 */
	void enterThen(LOLcodeParser.ThenContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#then}.
	 * @param ctx the parse tree
	 */
	void exitThen(LOLcodeParser.ThenContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#elseif}.
	 * @param ctx the parse tree
	 */
	void enterElseif(LOLcodeParser.ElseifContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#elseif}.
	 * @param ctx the parse tree
	 */
	void exitElseif(LOLcodeParser.ElseifContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#anelse}.
	 * @param ctx the parse tree
	 */
	void enterAnelse(LOLcodeParser.AnelseContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#anelse}.
	 * @param ctx the parse tree
	 */
	void exitAnelse(LOLcodeParser.AnelseContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#ifthenelseifelse}.
	 * @param ctx the parse tree
	 */
	void enterIfthenelseifelse(LOLcodeParser.IfthenelseifelseContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#ifthenelseifelse}.
	 * @param ctx the parse tree
	 */
	void exitIfthenelseifelse(LOLcodeParser.IfthenelseifelseContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#input}.
	 * @param ctx the parse tree
	 */
	void enterInput(LOLcodeParser.InputContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#input}.
	 * @param ctx the parse tree
	 */
	void exitInput(LOLcodeParser.InputContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#output_arg}.
	 * @param ctx the parse tree
	 */
	void enterOutput_arg(LOLcodeParser.Output_argContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#output_arg}.
	 * @param ctx the parse tree
	 */
	void exitOutput_arg(LOLcodeParser.Output_argContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#output_args}.
	 * @param ctx the parse tree
	 */
	void enterOutput_args(LOLcodeParser.Output_argsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#output_args}.
	 * @param ctx the parse tree
	 */
	void exitOutput_args(LOLcodeParser.Output_argsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#output}.
	 * @param ctx the parse tree
	 */
	void enterOutput(LOLcodeParser.OutputContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#output}.
	 * @param ctx the parse tree
	 */
	void exitOutput(LOLcodeParser.OutputContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#statement_affecting_it}.
	 * @param ctx the parse tree
	 */
	void enterStatement_affecting_it(LOLcodeParser.Statement_affecting_itContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#statement_affecting_it}.
	 * @param ctx the parse tree
	 */
	void exitStatement_affecting_it(LOLcodeParser.Statement_affecting_itContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#core_statement}.
	 * @param ctx the parse tree
	 */
	void enterCore_statement(LOLcodeParser.Core_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#core_statement}.
	 * @param ctx the parse tree
	 */
	void exitCore_statement(LOLcodeParser.Core_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#inner_statement}.
	 * @param ctx the parse tree
	 */
	void enterInner_statement(LOLcodeParser.Inner_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#inner_statement}.
	 * @param ctx the parse tree
	 */
	void exitInner_statement(LOLcodeParser.Inner_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#top_level_statement}.
	 * @param ctx the parse tree
	 */
	void enterTop_level_statement(LOLcodeParser.Top_level_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#top_level_statement}.
	 * @param ctx the parse tree
	 */
	void exitTop_level_statement(LOLcodeParser.Top_level_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#separator}.
	 * @param ctx the parse tree
	 */
	void enterSeparator(LOLcodeParser.SeparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#separator}.
	 * @param ctx the parse tree
	 */
	void exitSeparator(LOLcodeParser.SeparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(LOLcodeParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(LOLcodeParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#top_level_block}.
	 * @param ctx the parse tree
	 */
	void enterTop_level_block(LOLcodeParser.Top_level_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#top_level_block}.
	 * @param ctx the parse tree
	 */
	void exitTop_level_block(LOLcodeParser.Top_level_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link LOLcodeParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(LOLcodeParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link LOLcodeParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(LOLcodeParser.ProgramContext ctx);
}