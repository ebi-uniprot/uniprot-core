package org.uniprot.core.uniprotkb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class InternalLineTypeTest {

    @Nested
    class getPosition {
        @Test
        void cl() {
            assertEquals(1, InternalLineType.CL.getPosition());
        }

        @Test
        void cp() {
            assertEquals(2, InternalLineType.CP.getPosition());
        }

        @Test
        void cx() {
            assertEquals(3, InternalLineType.CX.getPosition());
        }

        @Test
        void dg() {
            assertEquals(5, InternalLineType.DG.getPosition());
        }

        @Test
        void dr() {
            assertEquals(4, InternalLineType.DR.getPosition());
        }

        @Test
        void GO() {
            assertEquals(6, InternalLineType.GO.getPosition());
        }

        @Test
        void EV() {
            assertEquals(7, InternalLineType.EV.getPosition());
        }

        @Test
        void HA() {
            assertEquals(8, InternalLineType.HA.getPosition());
        }

        @Test
        void HR() {
            assertEquals(9, InternalLineType.HR.getPosition());
        }

        @Test
        void HW() {
            assertEquals(10, InternalLineType.HW.getPosition());
        }

        @Test
        void HU() {
            assertEquals(11, InternalLineType.HU.getPosition());
        }

        @Test
        void HP() {
            assertEquals(12, InternalLineType.HP.getPosition());
        }

        @Test
        void ID() {
            assertEquals(13, InternalLineType.ID.getPosition());
        }

        @Test
        void IS() {
            assertEquals(14, InternalLineType.IS.getPosition());
        }

        @Test
        void NI() {
            assertEquals(15, InternalLineType.NI.getPosition());
        }

        @Test
        void PM() {
            assertEquals(16, InternalLineType.PM.getPosition());
        }

        @Test
        void SO() {
            assertEquals(17, InternalLineType.SO.getPosition());
        }

        @Test
        void YY() {
            assertEquals(18, InternalLineType.YY.getPosition());
        }

        @Test
        void ZA() {
            assertEquals(19, InternalLineType.ZA.getPosition());
        }

        @Test
        void ZB() {
            assertEquals(20, InternalLineType.ZB.getPosition());
        }

        @Test
        void ZC() {
            assertEquals(21, InternalLineType.ZC.getPosition());
        }

        @Test
        void ZR() {
            assertEquals(22, InternalLineType.ZR.getPosition());
        }

        @Test
        void ZZ() {
            assertEquals(23, InternalLineType.ZZ.getPosition());
        }

        @Test
        void ET() {
            assertEquals(24, InternalLineType.ET.getPosition());
        }

        @Test
        void PE() {
            assertEquals(25, InternalLineType.PE.getPosition());
        }

        @Test
        void RU() {
            assertEquals(26, InternalLineType.RU.getPosition());
        }

        @Test
        void TX() {
            assertEquals(27, InternalLineType.TX.getPosition());
        }

        @Test
        void PROSITE() {
            assertEquals(28, InternalLineType.PROSITE.getPosition());
        }

        @Test
        void UP() {
            assertEquals(29, InternalLineType.UP.getPosition());
        }

        @Test
        void ZD() {
            assertEquals(23, InternalLineType.ZD.getPosition());
        }
    }

    @ParameterizedTest
    @EnumSource(InternalLineType.class)
    void displayNameIs_enumName(InternalLineType enm) {
        assertEquals(enm.toString(), enm.getDisplayName());
    }
}
