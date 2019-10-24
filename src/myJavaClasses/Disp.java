package myJavaClasses;

public class Disp {
	
	// petits raccourcis de branleur
	private static String line = "————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————" ;
	private static String star = "****************************************************************************************************************************************************************************************" ;
	private static String htag = "########################################################################################################################################################################################" ;
	
	// méthodes de branleur
	public static void anyType(Object obj) {
		System.out.println(String.valueOf(obj));
	}

	public static void anyTypeThenLine(Object obj) {
		anyType(obj);
		line();
	}

	public static void anyTypeThenStar(Object obj) {
		anyType(obj);
		star();
	}

	public static void anyTypeThenHtag(Object obj) {
		anyType(obj);
		htag();
	}

	// BASIC DEBUGG STUFF
	public static void line() {
		anyType(line);
	}

	public static void star()
	{
		anyType(star);
	}

	public static void htag() {
		anyType(htag);
	}

	// SPECIAL STUFF
	public static void exc(String custom) {
		anyType("!!! " + custom + " !!!");
	}

	public static void exc(Exception ex) {
		exc(ex.toString());
	}

	public static void progress(int nb_actuel, int nb_total) {
		progress("**global**", nb_actuel, nb_total);
	}

	public static void progress(String label, int nb_actuel, int nb_total) {
		anyType(">>> Progress of [ " + label + " ] : " + nb_actuel + " / " + nb_total
				+ " | " + ( 100.0 * nb_actuel / nb_total ) + " %"
			);
	}

	public static void shortMsgStar(String msg, boolean wrapped) {
		String wrapper = generateWrapperFromMsg(msg, '*');
		if (wrapped) star();
		anyType(wrapper + "  " + msg + "  " + wrapper);
		if (wrapped) star();
	}

	public static void shortMsgLine(String msg, boolean wrapped) {
		String wrapper = generateWrapperFromMsg(msg, '—');
		if (wrapped) line();
		anyType(wrapper + "  " + msg + "  " + wrapper);
		if (wrapped) line();
	}

	private static String generateWrapperFromMsg(String msg, char symbol) {
		int wrapperLength = (line.length() - (msg.length() + 4)) / 2;
		String wrapper = "";

		for (int i=0 ; i<wrapperLength ; i++) {
			wrapper += symbol;
		}
		return wrapper;
	}

	public static void separatorStar() {
		star();
		star();
	}

	public static void separatorLine() {
		line();
		line();
	}

	public static void separatorMix() {
		line();
		star();
		star();
		line();
	}
}
