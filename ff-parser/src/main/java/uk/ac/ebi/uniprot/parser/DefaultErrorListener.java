package uk.ac.ebi.uniprot.parser;

import org.antlr.v4.runtime.*;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 23/10/2013
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class DefaultErrorListener extends BaseErrorListener {


    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine, String msg, RecognitionException e) {

        String inputString = "";
        if (offendingSymbol instanceof CommonToken){
            CommonToken t = (CommonToken) offendingSymbol;
            CharStream inputStream = t.getInputStream();
            inputString=inputStream.toString();
        }

//        String ruleName = "";
//        String parsedSoFar = "";
//
//        if (e!=null){
//            RuleContext ctx = e.getCtx();
//            if (ctx!=null && ctx instanceof ParserRuleContext){
//                ParserRuleContext pp = (ParserRuleContext) ctx;
//                parsedSoFar = pp.getText();
//                ruleName = pp.getClass().getSimpleName().toLowerCase();
//                if (ruleName.endsWith("context")){
//                    ruleName  = ruleName.substring(0, ruleName.length()-"context".length()) ;
//                }
//            }
//        }

        throw new ParseException(msg, inputString, line, charPositionInLine, e);
    }

}
