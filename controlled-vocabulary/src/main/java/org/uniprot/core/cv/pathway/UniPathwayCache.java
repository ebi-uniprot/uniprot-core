package org.uniprot.core.cv.pathway;

import org.uniprot.core.cv.common.AbstractFileReader;
import org.uniprot.core.cv.common.BaseCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum UniPathwayCache implements BaseCache<UniPathway> {
	INSTANCE;
	private static final String FTP_LOCATION = "unipathway.txt";
	private Map<String, List<UniPathway> > locationPathwayMap = new HashMap<>();
	private AbstractFileReader<UniPathway> reader;

	private String defaultDataLocation = FTP_LOCATION;

	@Override
	public String getDefaultDataFile() {
		return this.defaultDataLocation;
	}

	@Override
	public void setDefaultDataFile(String dataFile) {
		this.defaultDataLocation = dataFile;
	}

	@Override
	public Map<String, List<UniPathway>> getCacheMap() {
		return this.locationPathwayMap;
	}

	@Override
	public AbstractFileReader<UniPathway> getReader() {
		return this.reader != null ? this.reader : (this.reader = new UniPathwayFileReader());
	}
}
