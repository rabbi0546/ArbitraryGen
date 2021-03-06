package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.ILexer;
import cc.suitalk.arbitrarygen.statement.WhileStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class WhileStatementParser extends BaseStatementParser {

	public WhileStatementParser() {
		super("while");
	}

	@Override
	public WhileStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "while".equals(curWord.value)) {
				WhileStatement whileStm = new WhileStatement();
				whileStm.setPrefixWord(curWord);
				Word word = nextWord(reader, lexer);
				whileStm.setWordLeftBracket(word);
//				whileStm.setCommendBlock(getCommendStr());
				Expression condition = Util.extractExpressionFromBlacket(reader, lexer, word, this);
				if (condition == null) {
					throw new RuntimeException("extract expression from blacket failed.");
				}
//				whileStm.setWordRightBracket(word);
				whileStm.setConditionExpression(condition);
				word = getLastWord();
				Util.getAndAttachCodeBlock(reader, lexer, word, whileStm, this);
				word = getLastWord();
				return whileStm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
