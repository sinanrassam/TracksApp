package com.lostanimals.tracks.utils;

class EmojiRemover {
	
	private static final String EMOJI_PATTERN = ("[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]");
	
	static String processString(String string) {
		return string.replaceAll(EMOJI_PATTERN, "");
	}
}
