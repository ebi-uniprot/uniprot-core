parser grammar SsLineParser;

options { tokenVocab=SsLineLexer;}

ss_ss:  STAR_LINE
        (source_section | internal_section |(source_section internal_section));

source_section:  SS_START_LINE source_section_line+;
source_section_line:  STAR_STAR_SOURCE  SOURCE_TEXT  LINE_END;

internal_section: IS_START_LINE internal_section_detail?;
    

internal_section_detail:
 ((evidence_line+)|(internal_annotation_line+)|(evidence_line+ internal_annotation_line+)|(internal_annotation_line+ evidence_line+)|(internal_annotation_line+ evidence_line+ internal_annotation_line+));
evidence_line : EV_TOPIC ev_id EV_SEPARATOR ev_db EV_SEPARATOR
            (ev_attr_1? EV_SEPARATOR)? ev_attr_2? EV_SEPARATOR
            EV_DATE LINE_END;
ev_id:   EV_WORD;
ev_db:   EV_WORD;
ev_attr_1:   EV_WORD;
ev_attr_2:   EV_WORD;

internal_annotation_line: STAR_STAR TOPIC SPACE
       IA_TEXT LINE_END;







