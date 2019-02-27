/*
GN   Name=<name>; Synonyms=<name1>[, <name2>...]; OrderedLocusNames=<name1>[, <name2>...];
GN   ORFNames=<name1>[, <name2>...];
//each and every name need to have an evidence.
--Constrains not checked.
*/

parser grammar GnLineParser;

options { tokenVocab=GnLineLexer;}

gn_gn: gn_line_block (GN_NAME_SEPARATOR gn_line_block)* NEWLINE;

gn_line_block: GN_HEADER
        one_name ((SPACE|CHANGE_OF_LINE) one_name)*
        ;

one_name: (gene_name|syn_name|orf_name|ol_name);

gene_name: NAME name SEMICOLON;
syn_name: SYNONYMS names SEMICOLON;
orf_name: ORFNAMES names SEMICOLON;
ol_name:  OLNAMES names SEMICOLON;

words: (GL_WORD|word_in_bracket)+ ((SPACE|CHANGE_OF_LINE) (GL_WORD| word_in_bracket)+)*;

word_in_bracket : LEFT_BRACKET NON_EV_TAG RIGHT_BRACKET;

name: words ((SPACE|CHANGE_OF_LINE) evidence)?;
names: name (COMA (SPACE|CHANGE_OF_LINE) name)*;
evidence: LEFT_BRACKET EV_TAG (EV_SEPARATOR (SPACE|CHANGE_OF_LINE) EV_TAG)* RIGHT_BRACKET;





