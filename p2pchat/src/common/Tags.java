package common;

public class Tags {
	public static final String OPEN_CONN = "<connect>";
	public static final String END_CONN = "</connect>";
	public static final String OPEN_SEND = "<send>";
	public static final String END_SEND = "</send>";
	public static final String DISC = "<disconnect />";
	public static final int TAG_MAX_LENGTH = 50;
	public static final boolean validateTags(String start, String end){
		boolean r = false;
		String s, e;
		s = start.toLowerCase();
		e = end.toLowerCase();
		if (s.substring(1, s.length()).equals(e.substring(2, e.length())) && e.charAt(1) =='/' ){
			r = true;
		}
		return r;
	}
}
