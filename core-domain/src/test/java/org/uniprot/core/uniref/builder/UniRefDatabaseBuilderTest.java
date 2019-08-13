package org.uniprot.core.uniref.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefDatabase;
import org.uniprot.core.uniref.UniRefDatabaseType;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

class UniRefDatabaseBuilderTest {

	@Test
	void testFrom() {
		UniRefDatabaseType type = UniRefDatabaseType.UniRef100;
		LocalDate releaseDate = LocalDate.now();
		String version ="2019_07";
		UniRefDatabase database =new UniRefDatabaseBuilder().type(type).releaseDate(releaseDate)
				.version(version).build();
		UniRefDatabase database2 =new UniRefDatabaseBuilder().from(database).build();
		assertEquals(database, database2);
	}

	@Test
	void testType() {
		UniRefDatabaseType type = UniRefDatabaseType.UniRef90;
		UniRefDatabase database =new UniRefDatabaseBuilder().type(type).build();
		
		assertEquals(type, database.getType());

	}

	@Test
	void testReleaseDate() {
		UniRefDatabaseType type = UniRefDatabaseType.UniRef50;
		LocalDate releaseDate = LocalDate.now();
		String version ="2019_07";
		UniRefDatabase database =new UniRefDatabaseBuilder().type(type).releaseDate(releaseDate)
				.version(version).build();
		
		assertEquals(type, database.getType());
		assertEquals(releaseDate, database.getReleaseDate());
		assertEquals(version, database.getVersion());
	}

}

