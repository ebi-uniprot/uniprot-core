package org.uniprot.cv.xdb;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.cv.common.CVSystemProperties.DR_DATABASE_TYPES_LOCATION;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.cv.Reloader;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UniProtDatabaseTypesPropertyLocationTest {
    private File tmpFile;
    private Method enumMethod;
    private Object enumInstance;

    @BeforeAll
    void setUp()
            throws IllegalAccessException, NoSuchMethodException, NoSuchFieldException,
                    ClassNotFoundException, IOException {
        writeDataInfile();

        System.setProperty(DR_DATABASE_TYPES_LOCATION, tmpFile.getAbsolutePath());

        /*
         * singleton enum pattern, jvm will load it only once depending on property set or not
         * need to load it again (to test different behaviours) with different class loader
         */
        Class<?> goEvidences =
                Class.forName("org.uniprot.cv.xdb.UniProtDatabaseTypes", true, new Reloader());
        enumMethod = goEvidences.getDeclaredMethod("getAllDbTypes");
        enumInstance = goEvidences.getField("INSTANCE").get(null);
    }

    private void writeDataInfile() throws IOException {
        tmpFile = File.createTempFile("test-db", ".tmp");
        FileWriter writer = new FileWriter(tmpFile);
        writer.write("[]");
        writer.close();
    }

    @Test
    void canLoadListWithValues() throws InvocationTargetException, IllegalAccessException {
        List<UniProtDatabaseDetail> list =
                (List<UniProtDatabaseDetail>) enumMethod.invoke(enumInstance);
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @AfterAll
    void cleanUp() {
        tmpFile.delete();
        System.clearProperty(DR_DATABASE_TYPES_LOCATION);
    }
}
