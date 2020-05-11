package org.uniprot.cv.xdb;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.cv.common.CVSystemProperties.DR_ORD_LOCATION;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.cv.Reloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UniProtKBCrossReferenceDisplayOrderPropertyLocationTest {
    private Method enumMethod;
    private Object enumInstance;

    @BeforeAll
    void setUp()
            throws IllegalAccessException, NoSuchMethodException, NoSuchFieldException,
                    ClassNotFoundException {
        System.setProperty(DR_ORD_LOCATION, "xdb/dr_ord");

        /*
         * singleton enum pattern, jvm will load it only once depending on property set or not
         * need to load it again (to test different behaviours) with different class loader
         */
        Class<?> goEvidences =
                Class.forName(
                        "org.uniprot.cv.xdb.UniProtCrossReferenceDisplayOrder",
                        true,
                        new Reloader());
        enumMethod = goEvidences.getDeclaredMethod("getOrderedDatabases");
        enumInstance = goEvidences.getField("INSTANCE").get(null);
    }

    @Test
    void canLoadListWithValues() throws InvocationTargetException, IllegalAccessException {
        List<UniProtDatabaseDetail> list =
                (List<UniProtDatabaseDetail>) enumMethod.invoke(enumInstance);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals("EMBL", list.get(0).getName());
    }

    @AfterAll
    void cleanUp() {
        System.clearProperty(DR_ORD_LOCATION);
    }
}
