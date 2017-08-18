package co.andrex.inc.readerAction;

import java.awt.Dimension;
import java.awt.Toolkit;

public class test {

	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Double width = screenSize.getWidth();
		Double height = screenSize.getHeight();

		System.out.println("width: " + width.intValue() + " height: "
				+ height.intValue());
		
		if(width>2000){
			
		}
	}

}
