package org.uniprot.core.flatfile.parser.impl.ft;

import java.util.regex.Pattern;

public class FtLineConverterUtil {
    private static String CARBOHYD_DESC = "([NOSC]-linked) ([^;]+)((; )(.+))?";
    public static final Pattern CARBOHYD_DESC_PATTERN = Pattern.compile(CARBOHYD_DESC);

    private static String VAR_SEQ_DESC =
            "(Missing|(([A-Z ]+)(( )?-\\>( )?)([A-Z ]+)))(( \\(in )(.+)(\\)))";
    public static final Pattern VAR_SEQ_DESC_PATTERN = Pattern.compile(VAR_SEQ_DESC);

    private static String VARIANT_DESC =
            "(Missing|(([A-Z]+)( -\\> )([A-Z]+)))((( )?\\()(.+)?(\\)))?";
    public static final Pattern VAIANT_DESC_PATTERN = Pattern.compile(VARIANT_DESC);

    private static String MUTAGEN_DESC = "(Missing|(([A-Z ]+)(-\\>)([ A-Z\\,]+)))((: )(.+))";
    public static final Pattern MUTAGEN_DESC_PATTERN = Pattern.compile(MUTAGEN_DESC);

    private static String CONFLICT_DESC =
            "(Missing|(([A-Z]+)(( )?-\\>( )?)([A-Z]+)))(( \\(in Ref\\. )(.+)(\\)))";
    public static final Pattern CONFLICT_DESC_PATTERN = Pattern.compile(CONFLICT_DESC);
}
