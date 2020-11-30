package org.uniprot.cv.go;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Created 27/11/2020
 *
 * @author Edd
 */
class GORepoFactoryTest {
    @Test
    void canInstantiate() {
        GORepo repo = GORepoFactory.createRepo("src/test/resources/go");
        assertThat(repo, is(notNullValue()));
    }
}
