package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.BiophysicochemicalProperties;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.EvidencedString;
import org.uniprot.core.uniprot.comment.*;

class CcBPCPConverterTest {
    private final CcLineConverter converter = new CcLineConverter(null, null);

    @Test
    void testBPCP() {
        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CC.CCTopicEnum.BIOPHYSICOCHEMICAL_PROPERTIES);
        BiophysicochemicalProperties wr = new BiophysicochemicalProperties();
        wr.getKms()
                .add(
                        new EvidencedString(
                                "0.3913 uM for FAM fluorophore-coupled RNA substrate and a quencher-coupled DNA primer",
                                new ArrayList<String>()));
        wr.getVmaxs()
                .add(new EvidencedString("0.000197 umol/sec/ug enzyme", new ArrayList<String>()));
        cc1.setObject(wr);
        ccLineO.getCcs().add(cc1);
        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(1, comments.size());

        Comment comment1 = comments.get(0);
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment1.getCommentType());
        assertTrue(comment1 instanceof BPCPComment);

        BPCPComment wcomment = (BPCPComment) comment1;
        KineticParameters kp = wcomment.getKineticParameters();
        assertEquals(1, kp.getMaximumVelocities().size());
        assertEquals(1, kp.getMichaelisConstants().size());
        MaximumVelocity maxV = kp.getMaximumVelocities().get(0);
        MichaelisConstant mc = kp.getMichaelisConstants().get(0);
        assertEquals(
                "FAM fluorophore-coupled RNA substrate and a quencher-coupled DNA primer",
                mc.getSubstrate());
        assertEquals(MichaelisConstantUnit.MICRO_MOL, mc.getUnit());
        assertEquals(0.3913, mc.getConstant(), 0.0001);
        assertEquals("umol/sec/ug", maxV.getUnit());
        assertEquals("enzyme", maxV.getEnzyme());
        assertEquals(0.000197, maxV.getVelocity(), 0.000001);
    }

    @Test
    void testBioPhyChem2() {
        // CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
        // CC       Kinetic parameters:
        // CC         KM=71 uM for ATP;
        // CC         KM=98 uM for ADP;
        // CC       pH dependence:
        // CC         Optimum pH is 7.75;
        // CC       Temperature dependence:
        // CC         Optimum temperature is 65 degrees Celsius. Protected from
        // CC         thermal inactivation by ATP;
        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CC.CCTopicEnum.BIOPHYSICOCHEMICAL_PROPERTIES);
        BiophysicochemicalProperties wr = new BiophysicochemicalProperties();
        wr.getKms().add(new EvidencedString("71 uM for ATP", new ArrayList<String>()));
        wr.getKms().add(new EvidencedString("98 uM for ADP", new ArrayList<String>()));
        wr.getPhDependence()
                .add(new EvidencedString("Optimum pH is 7.75", new ArrayList<String>()));
        String tempDe =
                "Optimum temperature is 65 degrees Celsius. Protected from "
                        + "thermal inactivation by ATP";
        wr.getTemperatureDependence().add(new EvidencedString(tempDe, new ArrayList<String>()));

        cc1.setObject(wr);
        ccLineO.getCcs().add(cc1);

        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(1, comments.size());

        Comment comment1 = comments.get(0);
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment1.getCommentType());
        assertTrue(comment1 instanceof BPCPComment);

        BPCPComment wcomment = (BPCPComment) comment1;
        assertEquals(false, wcomment.getAbsorption() != null);
        assertTrue(wcomment.getPhDependence() != null);
        assertFalse(wcomment.getRedoxPotential() != null);
        assertTrue(wcomment.getTemperatureDependence() != null);
        KineticParameters kp = wcomment.getKineticParameters();
        assertEquals(0, kp.getMaximumVelocities().size());
        assertEquals(2, kp.getMichaelisConstants().size());
        List<MichaelisConstant> mcs = kp.getMichaelisConstants();
        MichaelisConstant mc1 = mcs.get(0);
        assertEquals(71.0, mc1.getConstant(), 0.0001);
        assertEquals(MichaelisConstantUnit.MICRO_MOL, mc1.getUnit());
        assertEquals("ATP", mc1.getSubstrate());

        MichaelisConstant mc3 = mcs.get(1);
        assertEquals(98, mc3.getConstant(), 0.0001);
        assertEquals(MichaelisConstantUnit.MICRO_MOL, mc3.getUnit());
        assertEquals("ADP", mc3.getSubstrate());
        assertEquals("Optimum pH is 7.75", wcomment.getPhDependence().getTexts().get(0).getValue());
        assertEquals(
                "Optimum temperature is 65 degrees Celsius. Protected from thermal inactivation by ATP",
                wcomment.getTemperatureDependence().getTexts().get(0).getValue());
    }

    @Test
    void testBioPhyChem() {
        // CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
        // CC       Absorption:\n" +
        // CC         Abs(max)=3 nm;\n" +
        // CC         Note=foo bar foo bar\n"
        // CC       Kinetic parameters:
        // CC         KM=71 uM for ATP;
        // CC         KM=98 uM for ADP;
        // CC         KM=1.5 mM for acetate;
        // CC         KM=0.47 mM for acetyl phosphate;
        // CC       Temperature dependence:
        // CC         Optimum temperature is 65 degrees Celsius. Protected from
        // CC         thermal inactivation by ATP;
        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CC.CCTopicEnum.BIOPHYSICOCHEMICAL_PROPERTIES);
        BiophysicochemicalProperties wr = new BiophysicochemicalProperties();
        wr.getKms().add(new EvidencedString("71 uM for ATP", new ArrayList<String>()));
        wr.getKms().add(new EvidencedString("98 uM for ADP", new ArrayList<String>()));
        wr.getKms().add(new EvidencedString("1.5 mM for acetate", new ArrayList<String>()));
        wr.getKms()
                .add(new EvidencedString("0.47 mM for acetyl phosphate", new ArrayList<String>()));
        String temDe =
                "Optimum temperature is 65 degrees Celsius. Protected from "
                        + "thermal inactivation by ATP";
        wr.getTemperatureDependence().add(new EvidencedString(temDe, new ArrayList<String>()));

        wr.setBsorptionAbs(new EvidencedString("3 nm", new ArrayList<>()));
        wr.getBsorptionNote().add(new EvidencedString("foo bar foo bar", new ArrayList<String>()));
        cc1.setObject(wr);
        ccLineO.getCcs().add(cc1);

        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(1, comments.size());

        Comment comment1 = comments.get(0);
        assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment1.getCommentType());
        assertTrue(comment1 instanceof BPCPComment);

        BPCPComment wcomment = (BPCPComment) comment1;
        assertTrue(wcomment.getAbsorption() != null);
        assertFalse(wcomment.getPhDependence() != null);
        assertFalse(wcomment.getRedoxPotential() != null);
        assertTrue(wcomment.getTemperatureDependence() != null);
        KineticParameters kp = wcomment.getKineticParameters();
        assertEquals(0, kp.getMaximumVelocities().size());
        assertEquals(4, kp.getMichaelisConstants().size());
        List<MichaelisConstant> mcs = kp.getMichaelisConstants();
        MichaelisConstant mc1 = mcs.get(0);
        assertEquals(71.0, mc1.getConstant(), 0.0001);
        assertEquals(MichaelisConstantUnit.MICRO_MOL, mc1.getUnit());
        assertEquals("ATP", mc1.getSubstrate());

        MichaelisConstant mc3 = mcs.get(2);
        assertEquals(1.5, mc3.getConstant(), 0.0001);
        assertEquals(MichaelisConstantUnit.MILLI_MOL, mc3.getUnit());
        assertEquals("acetate", mc3.getSubstrate());
        assertEquals(
                "Optimum temperature is 65 degrees Celsius. Protected from thermal inactivation by ATP",
                wcomment.getTemperatureDependence().getTexts().get(0).getValue());
        Absorption ab = wcomment.getAbsorption();
        assertEquals(3, ab.getMax());
        assertEquals("foo bar foo bar", ab.getNote().getTexts().get(0).getValue());
    }
}
