package org.uniprot.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class MoleculeWeightTest {

    @Nested
    class getWeight {
        @Test
        void A() {
            assertEquals(710788, MoleculeWeight.A.getWeight());
        }

        @Test
        void B() {
            assertEquals(1146532, MoleculeWeight.B.getWeight());
        }

        @Test
        void C() {
            assertEquals(1031388, MoleculeWeight.C.getWeight());
        }

        @Test
        void D() {
            assertEquals(1150886, MoleculeWeight.D.getWeight());
        }

        @Test
        void E() {
            assertEquals(1291155, MoleculeWeight.E.getWeight());
        }

        @Test
        void F() {
            assertEquals(1471766, MoleculeWeight.F.getWeight());
        }

        @Test
        void G() {
            assertEquals(570519, MoleculeWeight.G.getWeight());
        }

        @Test
        void H() {
            assertEquals(1371411, MoleculeWeight.H.getWeight());
        }

        @Test
        void I() {
            assertEquals(1131594, MoleculeWeight.I.getWeight());
        }

        @Test
        void K() {
            assertEquals(1281741, MoleculeWeight.K.getWeight());
        }

        @Test
        void L() {
            assertEquals(1131594, MoleculeWeight.L.getWeight());
        }

        @Test
        void M() {
            assertEquals(1311926, MoleculeWeight.M.getWeight());
        }

        @Test
        void N() {
            assertEquals(1141038, MoleculeWeight.N.getWeight());
        }

        @Test
        void O() {
            assertEquals(2373000, MoleculeWeight.O.getWeight());
        }

        @Test
        void P() {
            assertEquals(971167, MoleculeWeight.P.getWeight());
        }

        @Test
        void Q() {
            assertEquals(1281307, MoleculeWeight.Q.getWeight());
        }

        @Test
        void R() {
            assertEquals(1561875, MoleculeWeight.R.getWeight());
        }

        @Test
        void S() {
            assertEquals(870782, MoleculeWeight.S.getWeight());
        }

        @Test
        void T() {
            assertEquals(1011051, MoleculeWeight.T.getWeight());
        }

        @Test
        void U() {
            assertEquals(1500400, MoleculeWeight.U.getWeight());
        }

        @Test
        void V() {
            assertEquals(991326, MoleculeWeight.V.getWeight());
        }

        @Test
        void W() {
            assertEquals(1862132, MoleculeWeight.W.getWeight());
        }

        @Test
        void X() {
            assertEquals(1113306, MoleculeWeight.X.getWeight());
        }

        @Test
        void Y() {
            assertEquals(1631760, MoleculeWeight.Y.getWeight());
        }

        @Test
        void Z() {
            assertEquals(1287473, MoleculeWeight.Z.getWeight());
        }

        @Test
        void h2o() {
            assertEquals(180153, MoleculeWeight.h2o.getWeight());
        }
    }

    @ParameterizedTest
    @EnumSource(MoleculeWeight.class)
    void displayName(MoleculeWeight enm) {
        assertEquals(enm.toString(), enm.toDisplayName());
    }

    @Test
    void sequenceHasChar_notPartOfMoleculeWeight_willAdd0() {
        assertEquals(89, MoleculeWeight.calcMolecularWeight("AJ"));
    }
}
