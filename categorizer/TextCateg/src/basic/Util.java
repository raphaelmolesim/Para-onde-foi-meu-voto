package basic;

import java.util.ArrayList;
import java.util.List;

public class Util {

	public static String[] tokenize(String input) {
		if (input != null) {
			String[] words = input.split("\\s");
			return words;

		} else {
			return new String[1];
		}
	}

	public static List<String> segmentWords(String s) {
		List<String> ret = new ArrayList<String>();

		for (String word : s.split("\\s")) {
			if (word.length() > 0) {
				ret.add(word);
			}
		}
		return ret;
	}
}
