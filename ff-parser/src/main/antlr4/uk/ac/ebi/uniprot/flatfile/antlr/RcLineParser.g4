parser grammar RcLineParser;

options { tokenVocab=RcLineLexer; }

rc_rc: RC_HEADER rc (rc_separator rc)* NEW_LINE;

rc : rc_token rc_text SEMICOLON;

rc_separator: SPACE | CHANGE_OF_LINE;


rc_text: rc_value ((rc_value_separator rc_value)*);
                // (AND rc_separator rc_value))? ;

rc_value_separator: COMA (SPACE|CHANGE_OF_LINE);
rc_value: rc_value_v ((SPACE|CHANGE_OF_LINE) evidence)?;
rc_value_v: WORD ((SPACE|CHANGE_OF_LINE) WORD)*;


rc_token: STRAIN| PLASMID | TRANSPOSON |TISSUE;
evidence: LEFT_B  EV_TAG (EV_SEPARATOR (SPACE|CHANGE_OF_LINE) EV_TAG)* RIGHT_B;
