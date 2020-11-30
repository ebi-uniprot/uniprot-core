package org.uniprot.cv.go;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.uniprot.cv.common.CVSystemProperties;

/**
 * Created 27/11/2020
 *
 * @author Edd
 */
class GORepoFactoryTest {
    @Test
    void canInstantiateWithPath() {
        GORepo repo = GORepoFactory.createRepo("src/test/resources/go");
        assertThat(repo, is(notNullValue()));
    }

    @Test
    void emptyPath_causesException() {
        assertThrows(Exception.class, () -> GORepoFactory.createRepo(""));
    }

    @Test
    void nullPath_causesException() {
        assertThrows(Exception.class, () -> GORepoFactory.createRepo(null));
    }

    @Test
    void canInstantiateWhenCorrectSystemProperty() {
        System.setProperty(CVSystemProperties.GO_LOCATION, "src/test/resources/go");
        GORepo repo = GORepoFactory.createRepo();
        assertThat(repo, is(notNullValue()));
    }

    @Test
    void noPathAndNoSystemProperty_causesException() {
        System.clearProperty(CVSystemProperties.GO_LOCATION);
        assertThrows(Exception.class, GORepoFactory::createRepo);
    }
}
