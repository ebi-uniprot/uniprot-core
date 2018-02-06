lexer grammar TextHelperLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

//this is only used satisfy antlr. not used.
//it shouldn't produce any token in this default mode.
NOTHING: [];
//each helper method in its own mode.

mode MODE_REMOVE_CHANGE_OF_LINE;
REMOVE_CHANGE_OF_LINE_CHANGE_OF_LINE: '\n' ('CC') REMOVE_CHANGE_OF_LINE_SPACE+            {replaceChangeOfLine();};
REMOVE_CHANGE_OF_LINE_SPACE: ' ';
REMOVE_CHANGE_OF_LINE_WORD: REMOVE_CHANGE_OF_LINE_WORD_LETTER+;
REMOVE_CHANGE_OF_LINE_WORD_LETTER: ~[ \n\r\t];

//This is to help parse the ABBR_MIM got in the CC Disease text, which is returned as a whole token.
//It is in the format of"(ABBR) [MIM:INT]:"
mode MODE_CC_DISEASE_ABBR_MIM;
CC_DISEASE_ABBR_MIM_LEFT_BRACKET: '(';
CC_DISEASE_ABBR_MIM_RIGHT_BRACKET: ')';
CC_DISEASE_ABBR_MIM_LEFT_BRACE: '[';
CC_DISEASE_ABBR_MIM_RIGHT_BRACE: ']';
CC_DISEASE_ABBR_MIM_SPACE: ' ';
CC_DISEASE_ABBR_MIM_COLON: ':';
CC_DISEASE_ABBR_MIM_CHANGE_LINE: '\nCC       '  {replaceChangeOfLine();};
CC_DISEASE_ABBR_MIM_MIM: 'MIM:';
CC_DISEASE_ABBR_MIM_VALUE: [1-9][0-9]*;
CC_DISEASE_ABBR_MIM_WORD: CC_DISEASE_ABBR_MIM_WORD_LETTER+;
fragment CC_DISEASE_ABBR_MIM_WORD_LETTER: ~[ ()\[\]:\n\r\t];

//CC_D_PUBMED: '(PubMed:' INTEGER (CC_D_COMA CC_D_SPACE|CC_D_CHANGE_OF_LINE INTEGER) ')' ;
mode MODE_CC_DISEASE_PUBMED;
CC_DISEASE_PUBMED_LEFT_BRACKET: '(PubMed:';
CC_DISEASE_PUBMED_RIGHT_BRACKET: ')';
CC_DISEASE_PUBMED_SPACE: ' ';
CC_DISEASE_PUBMED_COMA: ',';
CC_DISEASE_PUBMED_CHANGE_LINE: '\nCC       ';
CC_DISEASE_PUBMED_ID: [1-9][0-9]*;

mode MODE_VOLUME_PAGE;
VOLUME_PAGE_WORD: VOLUME_PAGE_L+;
VOLUME_PAGE_DASH: '-';
VOLUME_PAGE_COLON: ':';
fragment VOLUME_PAGE_L: ~[ :\n\r\t-];
