package dev.blue.blocks;

public class MetaDecimal {
	private long i;
	private static char[] symbols = {'0','1','2','3','4','5','6','7','8','9',
									 'a','b','c','d','e','f','g','h','i','j',
									 'k','l','m','n','o','p','q','r','s','t',
									 'u','v','w','x','y','z','A','B','C','D',
									 'E','F','G','H','I','J','K','L','M','N',
									 'O','P','Q','R','S','T','U','V','W','X',
									 'Y','Z','あ','い','う','え','お','か','く','き',
									 'け','こ','た','ち','つ','て','と','は','ひ','ふ',
									 'へ','ほ','ま','み','む','め','も','さ','し','す',
									 'せ','そ','な','に','ぬ','ね','の','ん','ら','り'};
	
	public MetaDecimal(long i) {
		this.i = i;
	}
	
	public String parse() {
		return parseFromInt(i);
	}
	
	private String parseFromInt(long i) {
		String trs = "";
		if(i >= symbols.length) {
			trs += parseFromInt(Math.floorDiv(i, 100));
			i -= (Math.floorDiv(i, 100)*100);
		}
		trs += symbols[(int)i];
		return trs;
	}
}
