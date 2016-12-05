package co.andrex.inc.readerAction;

import java.io.File;

public class test {

	public static void main(String[] args) {
		File oldfile = new File("d://test.txt");
		File newfile = new File("d://test231.txt");

		if (oldfile.renameTo(newfile)) {
			System.out.println("File renamed");
		} else {
			System.out.println("Sorry! the file can't be renamed");
		}

	}

}
