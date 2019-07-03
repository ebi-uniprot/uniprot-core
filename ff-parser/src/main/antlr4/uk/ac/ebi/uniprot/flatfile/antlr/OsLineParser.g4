parser grammar OsLineParser;

options { tokenVocab=OsLineLexer; superClass=uk.ac.ebi.uniprot.flatfile.antlr.AbstractUniProtParser;}

os_os : OS_HEADER os_line END;

os_line: OS_LINE  ((CHANGE_OF_LINE|DOT_CHANGE_OF_LINE) OS_LINE)*;
