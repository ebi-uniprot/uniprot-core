/*
Node[; Node...].

OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi;
OC   Mammalia; Eutheria; Euarchontoglires; Primates; Catarrhini; Hominidae;
OC   Homo.

*/

parser grammar OcLineParser;

options { tokenVocab=OcLineLexer;  superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtParser;}

oc_oc: OC_HEADER oc (SEMI_COLON (SPACE|CHANGE_OF_LINE) oc)* DOT_NEW_LINE;

oc: OcWord DOT? (SPACE OcWord DOT?)*;
