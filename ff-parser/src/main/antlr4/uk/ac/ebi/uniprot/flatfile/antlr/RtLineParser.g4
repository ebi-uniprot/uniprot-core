parser grammar RtLineParser;

options { tokenVocab=RtLineLexer;}

rt_rt: RT_START rt_line  RT_ENDING;

rt_line:  rt_part (CHANGE_OF_LINE rt_part)* TITLE_END;

rt_part: (LETTER|TITLE_END)*;
