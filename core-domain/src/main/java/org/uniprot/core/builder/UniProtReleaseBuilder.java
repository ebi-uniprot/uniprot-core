package org.uniprot.core.builder;

import java.time.LocalDate;

import org.uniprot.core.Builder;
import org.uniprot.core.UniProtRelease;
import org.uniprot.core.impl.UniProtReleaseImpl;

/**
 *
 * @author jluo
 * @date: 13 Aug 2019
 *
*/

public class UniProtReleaseBuilder implements Builder<UniProtReleaseBuilder, UniProtRelease> {
	private String currentVersion;
	private LocalDate currentReleaseDate;
	private String nextVersion;	
	private LocalDate nextReleaseDate;
	
	public UniProtReleaseBuilder currentVersion(String currentVersion) {
		this.currentVersion =currentVersion;
		return this;
	}
	public UniProtReleaseBuilder currentReleaseDate(LocalDate currentReleaseDate) {
		this.currentReleaseDate =currentReleaseDate;
		return this;
	}
	
	public UniProtReleaseBuilder nextVersion(String nextVersion) {
		this.nextVersion =nextVersion;
		return this;
	}
	public UniProtReleaseBuilder nextReleaseDate(LocalDate nextReleaseDate) {
		this.nextReleaseDate =nextReleaseDate;
		return this;
	}
	
	@Override
	public UniProtRelease build() {
		return new UniProtReleaseImpl( currentVersion,  currentReleaseDate,
				 nextVersion,  nextReleaseDate) ;
	}

	@Override
	public UniProtReleaseBuilder from(UniProtRelease instance) {
		return this.currentVersion(instance.getCurrentVersion())
				.currentReleaseDate(instance.getCurrentReleaseDate())
				.nextVersion(instance.getNextVersion())
				.nextReleaseDate(instance.getNextReleaseDate());
	}

}

