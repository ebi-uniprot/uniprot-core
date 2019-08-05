parser grammar OxLineParser;


options { tokenVocab=OxLineLexer; superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtParser; }

ox_ox: OX_HEADER db tax (evidence)? SEMICOLON END ;

db : XDB ;

tax: TAX;

evidence: LEFT_B  EV_TAG (EV_SEPARATOR (EV_SPACE|EV_CHANGE_OF_LINE)  EV_TAG)* RIGHT_B;