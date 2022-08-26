lexer grammar FtLineLexer;

options { superClass=org.uniprot.core.flatfile.antlr.RememberLastTokenLexer; }

tokens{FT_HEADER, NEW_LINE, CHANGE_OF_LINE, LEFT_B, SPACE, DOUBLE_QUOTE, COMA}

@members {
    private boolean inVarSeq=false;
}

//FT_KEY:
//      'INIT_MET'|'SIGNAL'|'PROPEP'|'TRANSIT'|'CHAIN'|'PEPTIDE'|'TOPO_DOM'|'TRANSMEM'|
//      'INTRAMEM'|'DOMAIN'|'REPEAT'|'CA_BIND'|'ZN_FING'|'DNA_BIND'|'NP_BIND'|'REGION'|
//      'COILED'|'MOTIF'|'COMPBIAS'|'ACT_SITE'|'METAL'|'BINDING'|'SITE'|'NON_STD'|
//      'MOD_RES'|'LIPID'|'CARBOHYD'|'DISULFID'|'CROSSLNK'|'VARIANT'|'MUTAGEN'|'UNSURE'|
//      'CONFLICT'|'NON_CONS'|'NON_TER'|'HELIX'|'STRAND'|'TURN'
//       ;
      
FT_KEY_OTHERS: 'NON_STD'|'MOD_RES'|'NON_CONS'|'NON_TER'|'HELIX'|'STRAND'|'TURN'|'UNSURE'; 
      
FT_KEY_INIT_MET:  'INIT_MET' {inVarSeq=false;};
FT_KEY_SIGNAL:  'SIGNAL' {inVarSeq=false;};
FT_KEY_PROPEP:  'PROPEP' {inVarSeq=false;};       
FT_KEY_TRANSIT:  'TRANSIT' {inVarSeq=false;};
FT_KEY_CHAIN:  'CHAIN' {inVarSeq=false;};
FT_KEY_PEPTIDE:  'PEPTIDE' {inVarSeq=false;};        
FT_KEY_TOPO_DOM:  'TOPO_DOM' {inVarSeq=false;};     
FT_KEY_TRANSMEM:  'TRANSMEM' {inVarSeq=false;};  

FT_KEY_INTRAMEM:  'INTRAMEM' {inVarSeq=false;};
FT_KEY_DOMAIN:  'DOMAIN' {inVarSeq=false;};
FT_KEY_REPEAT:  'REPEAT' {inVarSeq=false;};
FT_KEY_CA_BIND:  'CA_BIND' {inVarSeq=false;};       
FT_KEY_ZN_FING:  'ZN_FING' {inVarSeq=false;};
FT_KEY_DNA_BIND:  'DNA_BIND' {inVarSeq=false;};
FT_KEY_NP_BIND:  'NP_BIND' {inVarSeq=false;};        
FT_KEY_REGION:  'REGION' {inVarSeq=false;};

FT_KEY_COILED:  'COILED' {inVarSeq=false;};
FT_KEY_MOTIF:  'MOTIF' {inVarSeq=false;};     
FT_KEY_COMPBIAS:  'COMPBIAS' {inVarSeq=false;};
FT_KEY_ACT_SITE:  'ACT_SITE' {inVarSeq=false;};
FT_KEY_METAL:  'METAL' {inVarSeq=false;};  
FT_KEY_BINDING:  'BINDING' {inVarSeq=false;};
FT_KEY_SITE:  'SITE' {inVarSeq=false;};  
FT_KEY_LIPID:  'LIPID' {inVarSeq=false;};
FT_KEY_CARBOHYD:  'CARBOHYD' {inVarSeq=false;};   
FT_KEY_DISULFID:  'DISULFID' {inVarSeq=false;};  
FT_KEY_CROSSLNK:  'CROSSLNK' {inVarSeq=false;};
FT_KEY_VARIANT:  'VARIANT' {inVarSeq=false;};   
FT_KEY_MUTAGEN:  'MUTAGEN' {inVarSeq=false;};  
FT_KEY_CONFLICT:  'CONFLICT' {inVarSeq=false;};
  
FT_KEY_VAR_SEQ:  'VAR_SEQ' {inVarSeq=true;};

FT_HEADER : 'FT   ';
FT_SPOT:'.';
NEW_LINE: '\n';
FT_HEADER_2:          'FT                   ';
DOT : '.';
SPACE: ' ';

FT_LOCATION_2: ((('<' | '>' | '?') ?  ([1-9][0-9]*)) | '?')     ;
FT_LOCATION_ISO: FT_LOCATION_LT+;  
fragment FT_LOCATION_LT:  [0-9a-zA-Z\-\:\?];

FT_SEMICOLON: ':';

FT_ISOFORM_WORD: FT_ISOFORM_LT+;
fragment FT_ISOFORM_LT : [0-9a-zA-Z\-];

SPACES: SPACE_+;
fragment SPACE_: ' ';
FT_LIGAND:          '\nFT                   /ligand="'                         ->  pushMode( FT_LIGAND_MODE );
FT_LIGAND_ID:       '\nFT                   /ligand_id="'                      ->  pushMode( FT_LIGAND_ID_MODE);
FT_LIGAND_LABEL:    '\nFT                   /ligand_label="'                   ->  pushMode( FT_LIGAND_LABEL_MODE);
FT_LIGAND_NOTE:     '\nFT                   /ligand_note="'                    ->  pushMode( FT_LIGAND_NOTE_MODE);
FT_LIGAND_PT:       '\nFT                   /ligand_part="'                    ->  pushMode( FT_LIGAND_PT_MODE );
FT_LIGAND_PT_ID:    '\nFT                   /ligand_part_id="'                 ->  pushMode( FT_LIGAND_PT_ID_MODE);
FT_LIGAND_PT_LABEL: '\nFT                   /ligand_part_label="'              ->  pushMode( FT_LIGAND_PT_LABEL_MODE);
FT_LIGAND_PT_NOTE:  '\nFT                   /ligand_part_note="'               ->  pushMode( FT_LIGAND_PT_NOTE_MODE);
FT_NOTE:            '\nFT                   /note="'                           ->  pushMode( FT_NOTE_MODE );
FT_EVIDENCE:        '\nFT                   /evidence="'                       -> pushMode( FT_EVIDENCE_MODE );
FT_ID:              '\nFT                   /id="'                             -> pushMode(FT_ID_MODE);
FT_NEW_LINE_: '\n'                                                             -> type (NEW_LINE), popMode;

mode FT_LIGAND_MODE;
FT_LIGAND_DOUBLE_QUOTE : '""'                         {replaceDoubleQuote(); setType(FT_LIGAND_WORD);};
FT_LIGAND_QUOTE : '"'                              -> popMode, type (DOUBLE_QUOTE);
FT_LIGAND_SPACE : ' '                            -> type (SPACE);
FT_LIGAND_COMA : ','                             -> type (COMA);
FT_LIGAND_CHANGE_OF_LINE : '\nFT                   '   {replaceChangeOfLine(inVarSeq); setType(CHANGE_OF_LINE);};
FT_LIGAND_WORD: FT_LIGAND_LT (FT_LIGAND_LT)* ;
fragment FT_LIGAND_LT : ~[ \"\r\n\t];

mode FT_LIGAND_ID_MODE;
FT_LIGAND_ID_DOUBLE_QUOTE : '""'                         {replaceDoubleQuote(); setType(FT_LIGAND_ID_WORD);};
FT_LIGAND_ID_QUOTE : '"'                              -> popMode, type (DOUBLE_QUOTE);
FT_LIGAND_ID_SPACE : ' '                            -> type (SPACE);
FT_LIGAND_ID_COMA : ','                             -> type (COMA);
FT_LIGAND_ID_CHANGE_OF_LINE : '\nFT                   '   {replaceChangeOfLine(inVarSeq); setType(CHANGE_OF_LINE);};
FT_LIGAND_ID_WORD: FT_LIGAND_ID_LT (FT_LIGAND_ID_LT)* ;
fragment FT_LIGAND_ID_LT : ~[ \"\r\n\t];

mode FT_LIGAND_LABEL_MODE;
FT_LIGAND_LABEL_DOUBLE_QUOTE : '""'                         {replaceDoubleQuote(); setType(FT_LIGAND_LABEL_WORD);};
FT_LIGAND_LABEL_QUOTE : '"'                              -> popMode, type (DOUBLE_QUOTE);
FT_LIGAND_LABEL_SPACE : ' '                            -> type (SPACE);
FT_LIGAND_LABEL_COMA : ','                             -> type (COMA);
FT_LIGAND_LABEL_CHANGE_OF_LINE : '\nFT                   '   {replaceChangeOfLine(inVarSeq); setType(CHANGE_OF_LINE);};
FT_LIGAND_LABEL_WORD: FT_LIGAND_LABEL_LT (FT_LIGAND_LABEL_LT)* ;
fragment FT_LIGAND_LABEL_LT : ~[ \"\r\n\t];

mode FT_LIGAND_NOTE_MODE;
FT_LIGAND_NOTE_DOUBLE_QUOTE : '""'                         {replaceDoubleQuote(); setType(FT_LIGAND_NOTE_WORD);};
FT_LIGAND_NOTE_QUOTE : '"'                              -> popMode, type (DOUBLE_QUOTE);
FT_LIGAND_NOTE_SPACE : ' '                            -> type (SPACE);
FT_LIGAND_NOTE_COMA : ','                             -> type (COMA);
FT_LIGAND_NOTE_CHANGE_OF_LINE : '\nFT                   '   {replaceChangeOfLine(inVarSeq); setType(CHANGE_OF_LINE);};
FT_LIGAND_NOTE_WORD: FT_LIGAND_NOTE_LT (FT_LIGAND_NOTE_LT)* ;
fragment FT_LIGAND_NOTE_LT : ~[ \"\r\n\t];

mode FT_LIGAND_PT_MODE;
FT_LIGAND_PT_DOUBLE_QUOTE : '""'                         {replaceDoubleQuote(); setType(FT_LIGAND_PT_WORD);};
FT_LIGAND_PT_QUOTE : '"'                              -> popMode, type (DOUBLE_QUOTE);
FT_LIGAND_PT_SPACE : ' '                            -> type (SPACE);
FT_LIGAND_PT_COMA : ','                             -> type (COMA);
FT_LIGAND_PT_CHANGE_OF_LINE : '\nFT                   '   {replaceChangeOfLine(inVarSeq); setType(CHANGE_OF_LINE);};
FT_LIGAND_PT_WORD: FT_LIGAND_PT_LT (FT_LIGAND_PT_LT)* ;
fragment FT_LIGAND_PT_LT : ~[ \"\r\n\t];

mode FT_LIGAND_PT_ID_MODE;
FT_LIGAND_PT_ID_DOUBLE_QUOTE : '""'                         {replaceDoubleQuote(); setType(FT_LIGAND_PT_ID_WORD);};
FT_LIGAND_PT_ID_QUOTE : '"'                              -> popMode, type (DOUBLE_QUOTE);
FT_LIGAND_PT_ID_SPACE : ' '                            -> type (SPACE);
FT_LIGAND_PT_ID_COMA : ','                             -> type (COMA);
FT_LIGAND_PT_ID_CHANGE_OF_LINE : '\nFT                   '   {replaceChangeOfLine(inVarSeq); setType(CHANGE_OF_LINE);};
FT_LIGAND_PT_ID_WORD: FT_LIGAND_PT_ID_LT (FT_LIGAND_PT_ID_LT)* ;
fragment FT_LIGAND_PT_ID_LT : ~[ \"\r\n\t];

mode FT_LIGAND_PT_LABEL_MODE;
FT_LIGAND_PT_LABEL_DOUBLE_QUOTE : '""'                         {replaceDoubleQuote(); setType(FT_LIGAND_PT_LABEL_WORD);};
FT_LIGAND_PT_LABEL_QUOTE : '"'                              -> popMode, type (DOUBLE_QUOTE);
FT_LIGAND_PT_LABEL_SPACE : ' '                            -> type (SPACE);
FT_LIGAND_PT_LABEL_COMA : ','                             -> type (COMA);
FT_LIGAND_PT_LABEL_CHANGE_OF_LINE : '\nFT                   '   {replaceChangeOfLine(inVarSeq); setType(CHANGE_OF_LINE);};
FT_LIGAND_PT_LABEL_WORD: FT_LIGAND_PT_LABEL_LT (FT_LIGAND_PT_LABEL_LT)* ;
fragment FT_LIGAND_PT_LABEL_LT : ~[ \"\r\n\t];

mode FT_LIGAND_PT_NOTE_MODE;
FT_LIGAND_PT_NOTE_DOUBLE_QUOTE : '""'                         {replaceDoubleQuote(); setType(FT_LIGAND_PT_NOTE_WORD);};
FT_LIGAND_PT_NOTE_QUOTE : '"'                              -> popMode, type (DOUBLE_QUOTE);
FT_LIGAND_PT_NOTE_SPACE : ' '                            -> type (SPACE);
FT_LIGAND_PT_NOTE_COMA : ','                             -> type (COMA);
FT_LIGAND_PT_NOTE_CHANGE_OF_LINE : '\nFT                   '   {replaceChangeOfLine(inVarSeq); setType(CHANGE_OF_LINE);};
FT_LIGAND_PT_NOTE_WORD: FT_LIGAND_PT_NOTE_LT (FT_LIGAND_PT_NOTE_LT)* ;
fragment FT_LIGAND_PT_NOTE_LT : ~[ \"\r\n\t];

mode FT_NOTE_MODE;
FT_NOTE_DOUBLE_QUOTE : '""'                         {replaceDoubleQuote(); setType(FT_NOTE_WORD);};
FT_NOTE_QUOTE : '"'                              -> popMode, type (DOUBLE_QUOTE);
FT_NOTE_SPACE : ' '                            -> type (SPACE);
FT_NOTE_COMA : ','                             -> type (COMA);
FT_NOTE_CHANGE_OF_LINE : '\nFT                   '               {replaceChangeOfLine(inVarSeq); setType(CHANGE_OF_LINE);};
FT_NOTE_WORD: FT_NOTE_LT (FT_NOTE_LT)* ;
fragment FT_NOTE_LT : ~[ \"\r\n\t];

mode FT_EVIDENCE_MODE;
FT_EV_QUOTE : '"'                              -> popMode, type (DOUBLE_QUOTE);
EV_SPACE: ' '                      -> type (SPACE);
EV_SEPARATOR: ',';
EV_CHANGE_OF_LINE: '\nFT                   '  -> type(CHANGE_OF_LINE);
EV_TAG : ECO_TAG;

fragment ECO_TAG: ECO_TAG_EV (ECO_TAG_SOURCE)?;
fragment ECO_TAG_EV : 'ECO:'[0-9]* ;
fragment ECO_TAG_SOURCE: '|'(~[ \",}\n\r\t]|EV_CHANGE_OF_LINE)+;

mode FT_ID_MODE;
fragment INTEGER: [0-9]+;
fragment ID_STARTER:  'VSP_'|'PRO_'|'VAR_'|'VSP_'|'CAR_';
FTID_VALUE: ID_STARTER  INTEGER;
FT_ID_QUOTE : '"'                              -> popMode, type (DOUBLE_QUOTE);