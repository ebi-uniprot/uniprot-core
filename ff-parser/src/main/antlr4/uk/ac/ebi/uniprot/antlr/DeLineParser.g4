parser grammar DeLineParser;

options { tokenVocab=DeLineLexer;}

de_de: rec_name? alt_name*  alt_allergen? alt_biotech? alt_cdantigen* alt_inn* sub_name*
       included_de* contained_de* flags?;

rec_name: DE_RECNAME_START full_name (CONTINUE_OF_NAME short_name)* (CONTINUE_OF_NAME ec)*;
sub_name: DE_SUBNAME_START full_name (CONTINUE_OF_NAME ec)*;
alt_name: DE_ALTNAME_START (
            alt_name_1 | alt_name_2 | alt_name_3
          ) ;

alt_name_1 : full_name (CONTINUE_OF_NAME short_name)* (CONTINUE_OF_NAME ec)*           ;
alt_name_2 : short_name (CONTINUE_OF_NAME short_name)* (CONTINUE_OF_NAME ec)*           ;
alt_name_3 : ec (CONTINUE_OF_NAME ec)*           ;

alt_allergen:    ALTNAME_ALLERGEN name_value  END_OF_NAME;
alt_biotech:     ALTNAME_BIOTECH name_value  END_OF_NAME;
alt_cdantigen:   ALTNAME_CD_ANTIGEN name_value  END_OF_NAME;
alt_inn:         ALTNAME_INN name_value  END_OF_NAME;

flags: DE_FLAGS_START flag_value (SEMICOLON SPACE flag_value)? END_OF_NAME;
flag_value: (PRECURSOR | FRAGMENT |FRAGMENTS ) (SPACE evidence)?;

contained_de: DE_CONTAIN  sub_rec_name? sub_alt_name*  sub_alt_allergen? sub_alt_biotech? sub_alt_cdantigen* sub_alt_inn* sub_sub_name*   ;
included_de:  DE_INCLUDE sub_rec_name? sub_alt_name*  sub_alt_allergen? sub_alt_biotech? sub_alt_cdantigen* sub_alt_inn* sub_sub_name*  ;

sub_rec_name: SUB_DE_RECNAME_START full_name (SUB_CONTINUE_OF_NAME short_name)* (SUB_CONTINUE_OF_NAME ec)*;
sub_sub_name: SUB_DE_SUBNAME_START full_name (SUB_CONTINUE_OF_NAME ec)*;
sub_alt_name: SUB_DE_ALTNAME_START (
            sub_alt_name_1 | sub_alt_name_2 | sub_alt_name_3
          ) ;

sub_alt_name_1 : full_name (SUB_CONTINUE_OF_NAME short_name)* (SUB_CONTINUE_OF_NAME ec)*           ;
sub_alt_name_2 : short_name (SUB_CONTINUE_OF_NAME short_name)* (SUB_CONTINUE_OF_NAME ec)*           ;
sub_alt_name_3 : ec (SUB_CONTINUE_OF_NAME ec)*   ;

sub_alt_allergen:   SUB_ALTNAME_ALLERGEN name_value  END_OF_NAME;
sub_alt_biotech:    SUB_ALTNAME_BIOTECH name_value  END_OF_NAME;
sub_alt_cdantigen:  SUB_ALTNAME_CD_ANTIGEN name_value  END_OF_NAME;
sub_alt_inn:        SUB_ALTNAME_INN name_value  END_OF_NAME;

full_name: FULL name_value END_OF_NAME;
short_name: SHORT name_value  END_OF_NAME;
ec: EC EC_NAME_VALUE (SPACE evidence)? END_OF_NAME;

name_value : NAME_VALUE;

evidence: LEFT_B  EV_TAG (EV_SEPARATOR SPACE EV_TAG)* RIGHT_B;
