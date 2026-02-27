package org.uniprot.core.flatfile.parser.impl.aaentry;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.uniprot.core.flatfile.parser.UniProtParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniProtEntryIterator;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

/**
 *
 * @author jluo
 * @date: 30 Jan 2026
 *
*/

public class AAUniProtEntryIterator extends DefaultUniProtEntryIterator {
    public AAUniProtEntryIterator() {
        super();
        setIgnoreWrong(true);
    }

    public AAUniProtEntryIterator(int numberOfThreads, int entryQueuesize, int ffQueueSize) {
        super(numberOfThreads, entryQueuesize, ffQueueSize);
        setIgnoreWrong(true);
    }
    @Override
    protected ParsingTask createParsingTask() {
    	return new AAParsingTask(ffQueue, entriesQueue, this.parsingJobCountDownLatch);
    }
    
    public class AAParsingTask extends DefaultUniProtEntryIterator.ParsingTask{

		public AAParsingTask(BlockingQueue<String> ffQueue, BlockingQueue<UniProtKBEntry> queue, CountDownLatch countDown) {
			super(ffQueue, queue, countDown);
		}
    	
		@Override
		protected UniProtParser createEntryParser() {
        	return new AAUniProtParser(supportingDataMap, ignoreWrong);
        }

    }
}

