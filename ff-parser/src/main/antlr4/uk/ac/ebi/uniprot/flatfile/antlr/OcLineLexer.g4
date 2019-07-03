/*
Node[; Node...].

OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi;
OC   Mammalia; Eutheria; Euarchontoglires; Primates; Catarrhini; Hominidae;
OC   Homo.

*/

lexer grammar OcLineLexer;

options { superClass=uk.ac.ebi.uniprot.flatfile.antlr.AbstractUniProtLexer; }

OC_HEADER : 'OC   '   ->pushMode(OC_CONTENT);

mode OC_CONTENT;
CHANGE_OF_LINE: '\nOC   ';
SPACE: ' ';
SEMI_COLON: ';';
DOT_NEW_LINE: '.\n'     ->popMode;
DOT: '.';

OcWord: OL ((OL|DOT)* OL)?;
fragment OL : ~[ \.;\r\n\t];


