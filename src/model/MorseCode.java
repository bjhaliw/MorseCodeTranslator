package model;

public enum MorseCode {
	A ("A", ".-"), B ("B", "-..."), C("C", "-.-."), D("D", "-.."), E("E", "."), F("F", "..-."),
	G("G", "--."), H("H", "...."), I("I", ".."), J("J", ".---"), K("K", "-.-"), L("L", ".-.."),
	M("M", "--"), N("N", "-."), O("O", "---"), P("P", ".--."), Q("Q", "--.-"), R("R", ".-."),
	S("S", "..."), T("T", "-"), U("U", "..-"), V("V", "...-"), W("W", ".--"), X("X", "-..-"),
	Y("Y", "-.--"), Z("Z", "--..");

	
	private final String letter, code;
	private MorseCode(String letter, String code) {
		this.letter = letter;
		this.code = code;
	}
	
	public String getLetter() {
		return this.letter;
	}
	
	public String getCode() {
		return this.code;
	}
	
	
}
