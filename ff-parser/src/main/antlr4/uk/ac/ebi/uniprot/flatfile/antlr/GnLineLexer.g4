/*
GN   Name=<name>; Synonyms=<name1>[, <name2>...]; OrderedLocusNames=<name1>[, <name2>...];
GN   ORFNames=<name1>[, <name2>...];
//each and every name need to have an evidence.
--Constrains not checked.
*/

lexer grammar GnLineLexer;

options { superClass=uk.ac.ebi.uniprot.flatfile.antlr.RememberLastTokenLexer; }

GN_HEADER: 'GN   ';
CHANGE_OF_LINE: '\nGN   ';
GN_NAME_SEPARATOR: '\nGN   and' NEWLINE;

NAME : 'Name='                 -> pushMode(GENE_NAME_MODE);
SYNONYMS : 'Synonyms='         -> pushMode(GENE_NAME_MODE);
ORFNAMES : 'ORFNames='         -> pushMode(GENE_NAME_MODE);
OLNAMES: 'OrderedLocusNames='  -> pushMode(GENE_NAME_MODE);
SPACE: ' ';
NEWLINE : '\n';

mode GENE_NAME_MODE;
SEMICOLON: ';'                    -> popMode;
SPACE_GN: ' '                     -> type (SPACE);
COMA: ',';
CHANGE_OF_LINE_GN: '\nGN   '      {setType(CHANGE_OF_LINE);replaceChangeOfLine();};
LEFT_BRACKET: '{'                 -> pushMode(EVIDENCE_MODE);
GL_WORD: GL ((GL|COMA|SEMICOLON)* GL)?;
fragment GL: ~[ ,;{\n\r\t];

mode EVIDENCE_MODE;
EV_SEPARATOR: ',';
EV_SPACE: ' '                      -> type (SPACE);
EV_CHANGE_OF_LINE: '\nGN   '       -> type(CHANGE_OF_LINE);
EV_TAG : ECO_TAG;//| EV_TAG_OLD;
//EV_TAG : ECO_TAG| EV_TAG_OLD;
//fragment EV_TAG_OLD : ('EI'|'EA'|'EP'|'EC') [1-9][0-9]*;
fragment ECO_TAG: ECO_TAG_EV (ECO_TAG_SOURCE)?;
//the new evidence ECO type.
fragment ECO_TAG_EV : 'ECO:'[0-9]* ;
fragment ECO_TAG_SOURCE: '|'(~[ ,}\n\r\t]|EV_CHANGE_OF_LINE)+;
RIGHT_BRACKET : '}'                                        -> popMode;

//This is to handle in {} can appear inside the name.
NON_EV_TAG: NO_EV_TAG_L (NO_EV_TAG_L|','|'|'|' ')*        {!getText().startsWith("EI") &&!getText().startsWith("EA") &&!getText().startsWith("EP") &&!getText().startsWith("EC")}?;
fragment NO_EV_TAG_L: ~[ |,}\n\r\t];
