package com.garg.websites.greplin;

/**
 * http://challenge.greplin.com/
 */
public class Challenge1 {
	/**
	 * Embedded in this block of text is the password for level 2. The password
	 * is the longest substring that is the same in reverse.
	 * 
	 * As an example, if the input was "I like racecars that go fast" the
	 * password would be "racecar".
	 * 
	 */
	public static String longestPalindrome(String text) {
		text = padtext(text);
		int maxlen = 0, maxlenIndex = -1;
		for (int center = 0; center < text.length(); center++) {
			int len = longestpalinCenteredAtX(text, center);
			if (len > maxlen) {
				maxlen = len;
				maxlenIndex = center;
			}
		}
		text = text.substring(maxlenIndex - maxlen / 2, maxlenIndex + maxlen / 2 + 1);
		return unpadText(text);
	}

	private static String unpadText(String text) {
		char[] newtext = new char[(text.length() - 1) / 2];
		for (int i = 0, j = 0; i < text.length(); i++) {
			if (text.charAt(i) != '#') {
				newtext[j++] = text.charAt(i);
			}
		}
		return String.valueOf(newtext);
	}

	private static String padtext(String text) {
		char[] newtext = new char[2 * text.length() + 1];
		for (int i = 0; i < text.length(); i++) {
			newtext[2 * i] = '#';
			newtext[2 * i + 1] = text.charAt(i);
		}
		newtext[2 * text.length()] = '#';
		return String.valueOf(newtext);
	}

	private static int longestpalinCenteredAtX(String text, int x) {
		int rp = x + 1, lp = x - 1;
		int len = 1;
		while (rp < text.length() && lp > -1
				&& text.charAt(rp) == text.charAt(lp)) {
			rp++;
			lp--;
			len = len + 2;
		}
		return len;
	}

	public static void main(String[] args) {
		String text = "FourscoreandsevenyearsagoourfaathersbroughtforthonthiscontainentanewnationconceivedinzLibertyanddedicatedtothepropositionthatallmenarecreatedequalNowweareengagedinagreahtcivilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
		System.out.println(longestPalindrome(text));
	}
}
