package co.andrex.inc.readerAction;

import org.apache.commons.lang3.StringUtils;

public class test {

	public static void main(String[] args) {
		String nombre = "1234567";
		String x = nombre;
		String number = "";
		int z = x.length();
		for (int y = z ; y > 0; y--) {
			int index = y-1;
			Character cara = x.charAt(index);
			System.out.println(cara);
			if (StringUtils.isNumeric(nombre)){
				char c = x.charAt(index);
				number += c;
			} else {
				break;
			}
			
		}
	}

}
