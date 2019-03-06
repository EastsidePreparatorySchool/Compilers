// Generated from c:\Users\gmein\Documents\GitHub\eps\AP-Compilers\Compilers\src\lol\LOLcode.g4 by ANTLR 4.7.1

   package lol;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LOLcodeParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LOLcodeVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#literal_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral_value(LOLcodeParser.Literal_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(LOLcodeParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#vartype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVartype(LOLcodeParser.VartypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#foldable_arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFoldable_arg(LOLcodeParser.Foldable_argContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#sum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSum(LOLcodeParser.SumContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#diff}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiff(LOLcodeParser.DiffContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#product}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProduct(LOLcodeParser.ProductContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#quotient}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuotient(LOLcodeParser.QuotientContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#bigger}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBigger(LOLcodeParser.BiggerContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#smallr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSmallr(LOLcodeParser.SmallrContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#min}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMin(LOLcodeParser.MinContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#max}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMax(LOLcodeParser.MaxContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#mod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMod(LOLcodeParser.ModContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#not}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot(LOLcodeParser.NotContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#same}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSame(LOLcodeParser.SameContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#and}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(LOLcodeParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#either}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEither(LOLcodeParser.EitherContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#one}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOne(LOLcodeParser.OneContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#all}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAll(LOLcodeParser.AllContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#any}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAny(LOLcodeParser.AnyContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#concat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConcat(LOLcodeParser.ConcatContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#cast}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCast(LOLcodeParser.CastContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#var_ref}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_ref(LOLcodeParser.Var_refContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(LOLcodeParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#var_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_decl(LOLcodeParser.Var_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#var_rvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_rvalue(LOLcodeParser.Var_rvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#var_assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_assignment(LOLcodeParser.Var_assignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#arg_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg_decl(LOLcodeParser.Arg_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#more_args_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMore_args_decl(LOLcodeParser.More_args_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#args_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs_decl(LOLcodeParser.Args_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#return_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_type(LOLcodeParser.Return_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#func_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_decl(LOLcodeParser.Func_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#return_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_statement(LOLcodeParser.Return_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#naked_arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNaked_arg(LOLcodeParser.Naked_argContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg(LOLcodeParser.ArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#more_args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMore_args(LOLcodeParser.More_argsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(LOLcodeParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#func_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_call(LOLcodeParser.Func_callContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#loop_action}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop_action(LOLcodeParser.Loop_actionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#loop_condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop_condition(LOLcodeParser.Loop_conditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#loop_end}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop_end(LOLcodeParser.Loop_endContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop(LOLcodeParser.LoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#aswitch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAswitch(LOLcodeParser.AswitchContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#acase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAcase(LOLcodeParser.AcaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#adefault}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdefault(LOLcodeParser.AdefaultContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#anif}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnif(LOLcodeParser.AnifContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#then}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThen(LOLcodeParser.ThenContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#elseif}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseif(LOLcodeParser.ElseifContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#anelse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnelse(LOLcodeParser.AnelseContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#ifthenelseifelse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfthenelseifelse(LOLcodeParser.IfthenelseifelseContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#input}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInput(LOLcodeParser.InputContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#output_arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutput_arg(LOLcodeParser.Output_argContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#output_args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutput_args(LOLcodeParser.Output_argsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#output}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutput(LOLcodeParser.OutputContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#statement_affecting_it}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_affecting_it(LOLcodeParser.Statement_affecting_itContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#core_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCore_statement(LOLcodeParser.Core_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#inner_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInner_statement(LOLcodeParser.Inner_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#top_level_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTop_level_statement(LOLcodeParser.Top_level_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#separator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeparator(LOLcodeParser.SeparatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(LOLcodeParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#top_level_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTop_level_block(LOLcodeParser.Top_level_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link LOLcodeParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(LOLcodeParser.ProgramContext ctx);
}