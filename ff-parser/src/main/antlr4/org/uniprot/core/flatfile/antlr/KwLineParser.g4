/*
KW   Activator; Complete proteome; Reference proteome; Transcription;
KW   Transcription regulation.
*/

parser grammar KwLineParser;

options { tokenVocab=KwLineLexer; superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtParser;}

kw_kw:   kw_line+;

kw_line: KW_HEAD keyword (SEMICOLON (CHANGE_OF_LINE|SPACE) keyword)*
         DOT NEW_LINE;

keyword: keyword_v ((SPACE|CHANGE_OF_LINE)evidence)?;
keyword_v: WORD ((CHANGE_OF_LINE|SPACE) WORD)*;

evidence: LEFT_B  EV_TAG (EV_SEPARATOR (SPACE|CHANGE_OF_LINE) EV_TAG)* RIGHT_B;
