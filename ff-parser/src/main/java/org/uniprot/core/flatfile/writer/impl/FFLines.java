package org.uniprot.core.flatfile.writer.impl;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;

public class FFLines {
	public static FFLine create(List<String> lls){
		return new FFLineImpl(lls);
	}
	public static FFLine create(){
		return new FFLineImpl(new ArrayList<String>());
	}
	static class FFLineImpl implements FFLine {
		
		public List<String> lls;
		
		FFLineImpl(List<String> lls){
			this.lls =lls;
		}
		@Override
		public List<String> lines() {
			if(lls ==null)
				lls = new ArrayList<>();
			return lls;
		}
		@Override
		public String toString(){
			if(lls ==null)
				return "";
			StringBuilder sb = new StringBuilder();
			boolean isFirst =true;
			for(String ll:lls){
				if(!isFirst){
					sb.append('\n');
				}
				sb.append(ll);
				isFirst =false;
			}
			return sb.toString();
		}
		@Override
		public void add(FFLine line) {
			if(lls ==null){
				lls =new ArrayList<>();
			}
			lls.addAll(line.lines());
			
		}
		@Override
		public boolean isEmpty() {
			return lls.isEmpty();
		}
		@Override
		public void add(String line) {
			if((line ==null) ||line.isEmpty())
				return;
			if(lls ==null){
				lls =new ArrayList<>();
			}
			
			lls.add(line);
		}
	}

}
