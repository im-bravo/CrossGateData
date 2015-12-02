package cg.data.script;

import java.io.File;
import java.io.FileReader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import cg.data.script.antlr.GMSVBaseListener;
import cg.data.script.antlr.GMSVLexer;
import cg.data.script.antlr.GMSVParser;
import cg.data.script.antlr.GMSVParser.BlockContext;

public class Syscall {
	
	public static void main(String[] args) {
//		ANTLRInputStream in = new ANTLRInputStream("total 1/rblock total");
		try {
			ANTLRInputStream in = new ANTLRInputStream(new FileReader(new File("D:/file/workspace/CrossGateResource/npc/EV_aoki_6515.txt")));
			GMSVLexer lexer = new GMSVLexer(in);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			GMSVParser parse = new GMSVParser(tokens);
			BlockContext context = parse.block();
			context.enterRule(new GMSVBaseListener(){
	
				@Override
				public void enterBlock(BlockContext ctx) {
					super.enterBlock(ctx);
					System.out.println(ctx.getText());
	//				System.out.println(ctx.ID());
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}