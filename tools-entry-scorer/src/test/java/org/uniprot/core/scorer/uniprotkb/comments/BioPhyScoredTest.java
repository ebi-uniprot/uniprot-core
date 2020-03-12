package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;

class BioPhyScoredTest extends CommentScoreTestBase {
    @Test
    void shouldAbsorpScore20() throws Exception {
        String line = "BIOPHYSICOCHEMICAL PROPERTIES:\n" + "Absorption:\n" + "  Abs(max)=~480 nm;";
        verify(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, line, 2.0);
    }

    @Test
    void shouldpDDepScore20() throws Exception {
        String line =
                "BIOPHYSICOCHEMICAL PROPERTIES:\n" + "pH dependence:\n" + "  Optimum pH is 8.;";
        verify(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, line, 2.0);
    }

    @Test
    void shouldScore220() throws Exception {
        String line =
                "BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "Kinetic parameters:\n"
                        + "  KM=0.7 uM for Boc-Gln-Arg-Arg-MCA {ECO:0000313|EMBL:EOP66756.1};\n"
                        + "  KM=3.9 uM for Boc-Leu-Arg-Arg-MCA {ECO:0000313|EMBL:EOP66756.1};\n"
                        + "  KM=2.8 uM for Boc-Gly-Arg-Arg-MCA {ECO:0000313|EMBL:EOP66756.1};\n"
                        + "  KM=10.0 uM for Boc-Leu-Lys-Arg-MCA {ECO:0000313|EMBL:EOP66756.1};\n"
                        + "  KM=26.8 uM for Boc-Gly-Lys-Arg-MCA {ECO:0000313|EMBL:EOP66756.1};\n"
                        + "  KM=58.0 uM for Boc-Leu-Thr-Arg-MCA {ECO:0000313|EMBL:EOP66756.1};\n"
                        + "  KM=84.5 uM for Boc-Leu-Gly-Arg-MCA {ECO:0000313|EMBL:EOP66756.1};\n"
                        + "  KM=88.3 uM for Boc-Gln-Gly-Arg-MCA {ECO:0000313|EMBL:EOP66756.1};\n"
                        + "  Vmax=12.8 nmol/sec/mg enzyme with Z-Arg-Arg-MCA as substrate;\n"
                        + "  Note=The highest catalytic efficiency is observed for Boc-Leu-\n"
                        + "  Lys-Arg-MCA. {ECO:0000313|EMBL:EOP66756.1};\n"
                        + "pH dependence:\n"
                        + "  Optimum pH is 4.0. Inactive below pH 3.0 and above pH 6.5. The\n"
                        + "  half-life (t1/2) values for activity loss at 30 degrees Celsius\n"
                        + "  are 150 min at pH 6.6, 5.5 min at pH 6.8 and 14 min at pH 2.4.;\n"
                        + "Temperature dependence:\n"
                        + "  Remains fully active after heating at 50 degrees Celsius and pH\n"
                        + "  4.0 for 10 min. Retains 65% of its activity after heating at 55\n"
                        + "  degrees Celsius for 10 min. The half-life value for loss of\n"
                        + "  activity at 60 degrees Celsius and pH 4.0 is 3.5 min. {ECO:0000313|EMBL:EOP66756.1};";
        verify(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, line, 22.0);
    }

    @Test
    void shouldRedoxScore20() throws Exception {
        String line =
                "BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "Redox potential:\n"
                        + "  E(0) is about +178 mV.;";
        verify(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, line, 2.0);
    }

    @Test
    void shouldTempDepScore20() throws Exception {
        String line =
                "BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "Temperature dependence:\n"
                        + "  Optimum temperature is 70 degrees Celsius. Inactive at 37\n"
                        + "  degrees Celsius.;";
        verify(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, line, 2.0);
    }

    @Test
    void shouldScore40() throws Exception {
        String line =
                "BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "Kinetic parameters:\n"
                        + "  KM=25 uM for AdoMet;\n"
                        + "  Vmax=25.2 uM/h/mg enzyme;\n"
                        + "  Note=The KM for ATP is much higher than 30 uM.;";
        verify(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, line, 4.0);
    }

    @Test
    void shouldAScore220() throws Exception {
        String line =
                "BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "Kinetic parameters:\n"
                        + "  KM=0.7 uM for Boc-Gln-Arg-Arg-MCA;\n"
                        + "  KM=3.9 uM for Boc-Leu-Arg-Arg-MCA;\n"
                        + "  KM=2.8 uM for Boc-Gly-Arg-Arg-MCA;\n"
                        + "  KM=10.0 uM for Boc-Leu-Lys-Arg-MCA;\n"
                        + "  KM=26.8 uM for Boc-Gly-Lys-Arg-MCA;\n"
                        + "  KM=58.0 uM for Boc-Leu-Thr-Arg-MCA;\n"
                        + "  KM=84.5 uM for Boc-Leu-Gly-Arg-MCA;\n"
                        + "  KM=88.3 uM for Boc-Gln-Gly-Arg-MCA;\n"
                        + "  Vmax=12.8 nmol/sec/mg enzyme with Z-Arg-Arg-MCA as\n"
                        + "  substrate;\n"
                        + "  Note=The highest catalytic efficiency is observed for Boc-Leu-Lys-Arg-MCA.;\n"
                        + "pH dependence:\n"
                        + "  Optimum pH is 4.0. Inactive below pH 3.0 and above pH 6.5. The\n"
                        + "  half-life (t1/2) values for activity loss at 30 degrees Celsius\n"
                        + "  is 150 min at pH 6.6, 5.5 min at pH 6.8 and 14 min at pH\n"
                        + "  2.4.;\n"
                        + "Temperature dependence:\n"
                        + "  Remains fully active after heating at 50 degrees Celsius and pH\n"
                        + "  4.0 for 10 min. Retains 65% of its activity after heating at 55\n"
                        + "  degrees Celsius for 10 min. The half-life value for loss of\n"
                        + "  activity at 60 degrees Celsius and pH 4.0 is 3.5 min.;";

        verify(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, line, 22.0);
    }
}
