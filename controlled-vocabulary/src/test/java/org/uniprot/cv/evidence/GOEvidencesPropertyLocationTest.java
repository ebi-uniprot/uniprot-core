package org.uniprot.cv.evidence;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.cv.common.CVSystemProperties.GAF_ECO_LOCATION;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.uniprot.cv.Reloader;

@TestInstance(Lifecycle.PER_CLASS)
class GOEvidencesPropertyLocationTest {
    private File tmpFile;
    private Method convertGAFToECO;
    private Object enumInstance;

    @BeforeAll
    void setUp()
            throws IOException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException,
                    ClassNotFoundException {
        writeDataInfile();

        System.setProperty(GAF_ECO_LOCATION, tmpFile.getAbsolutePath());

        /*
         * singleton enum pattern, jvm will load it only once depending on property set or not
         * need to load it again (to test different behaviours) with different class loader
         */
        Class<?> goEvidences =
                Class.forName("org.uniprot.cv.evidence.GOEvidences", true, new Reloader());
        convertGAFToECO = goEvidences.getDeclaredMethod("convertGAFToECO", String.class);
        enumInstance = goEvidences.getField("INSTANCE").get(null);
    }

    private void writeDataInfile() throws IOException {
        tmpFile = File.createTempFile("test", ".tmp");
        FileWriter writer = new FileWriter(tmpFile);
        writer.write("ND\tDefault\tECO:0000307");
        writer.close();
    }

    @Test
    void shouldNotExist() throws InvocationTargetException, IllegalAccessException {
        String val = "IBA";
        Optional<String> eco = (Optional<String>) convertGAFToECO.invoke(enumInstance, val);
        assertFalse(eco.isPresent());
    }

    @Test
    void onlyValueInFile() throws InvocationTargetException, IllegalAccessException {
        String val = "ND";
        Optional<String> eco = (Optional<String>) convertGAFToECO.invoke(enumInstance, val);
        assertTrue(eco.isPresent());
        assertEquals("ECO:0000307", eco.get());
    }

    @AfterAll
    void cleanUp() {
        tmpFile.delete();
        System.clearProperty(GAF_ECO_LOCATION);
    }
}
