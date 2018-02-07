package uk.ac.ebi.uniprot.ffwriter.line;

import java.util.ArrayList;
import java.util.List;


public class LineBuilder {
	private final String prefix ;
	private List<String> lines = new ArrayList<String>();
	private StringBuilder currentLine =new StringBuilder();
	private static final String defaultSeparator =", ";
	public static final int LINE_LENGTH = 75;
	private int lineLength =LINE_LENGTH;
	private final String lineStart;
	private final LineType lineType;


	public LineBuilder(String prefix, LineType lineType){
		this.prefix =prefix;
		currentLine.append(prefix);
		lineStart=prefix;
		this.lineType= lineType;
	}
	public void setLineLength(int length){
		this.lineLength =length;
	}
	public void addItem(String item, String separator) {
		if(currentLine.toString().equals(lineStart)){
			currentLine.append(item);
			resetCurrentLine(separator);
		}else{
			if((currentLine.length() + item.length() + separator.length())>lineLength){
				lines.add(currentLine.toString());
				currentLine = new StringBuilder();
				currentLine.append(prefix);
				currentLine.append(item);
				resetCurrentLine(separator);
			}else{
				currentLine.append(separator);
				currentLine.append(item);
			}
		}
	}
//	private void resetCurrentLine (){
//		if(currentLine.length()>lineLength){
//			String temp = LineWrapperHelper.StandardWrapper(currentLine,
//					lineType, LineWrapperHelper.SPACE, " ", LINE_LENGTH,"\n", "").toString();
//			String[] temps = temp.split("\n");
//			for(int i=0; i<temps.length-1; i++)
//				lines.add(temps[i]);
//			currentLine = new StringBuilder(temps[temps.length -1]);
//			
//		}
//	}
	private void resetCurrentLine (String separator ){
		if(currentLine.length()>lineLength){
			List<String> tempLines = FFLineWrapper.buildLines(currentLine.toString(), 
					 separator, lineType+FFLineConstant.DEFAUT_LINESPACE,  lineLength);
			for(int i=0; i<tempLines.size()-1; i++)
				lines.add(tempLines.get(i));
			currentLine = new StringBuilder(tempLines.get(tempLines.size()-1));
			
		}
	}
	
	public void addItem(String item) {
		addItem(item, defaultSeparator);
	}
	public void addEnding(String end){
		if(!currentLine.equals(lineStart)){
			currentLine.append(end);
		}
	}
	public void finish(String end){
		if(!currentLine.equals(lineStart)){
			currentLine.append(end);
			lines.add(currentLine.toString());
		}
	}
	public List<String> getLines(){
		return lines;
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(String line:lines){
			sb.append(line).append('\n');
		}
		return sb.toString();
	}
}
