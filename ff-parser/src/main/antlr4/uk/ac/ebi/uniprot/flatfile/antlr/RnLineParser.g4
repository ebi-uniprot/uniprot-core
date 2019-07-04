parser grammar RnLineParser;


options { tokenVocab=RnLineLexer; superClass=uk.ac.ebi.uniprot.flatfile.antlr.AbstractUniProtParser; }


rn_rn: RN_HEADER LEFT_BRACKET rn_number RIGHT_BRACKET ((SPACE | CHANGE_OF_LINE)evidence)? NEWLINE;

rn_number: INTEGER;
evidence: LEFT_B  EV_TAG (EV_SEPARATOR (SPACE|CHANGE_OF_LINE) EV_TAG)* RIGHT_B;
