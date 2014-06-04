package alchemyplusplus.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class Book {

	public ArrayList<BookPage> pages;
	private boolean loaded = false;
	
	public int pageAmount = 0;
	
	public static Book alchemicalGude;
	
	public static Book load(String bookName) {
		Book book = new Book();

		File dataFile = new File(Minecraft.getMinecraft().mcDataDir.toString() + "/mods/bookData/" + bookName + ".book");
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			
			int pageAmount = Integer.parseInt(br.readLine());
			book.pageAmount = pageAmount;
			book.pages = new ArrayList<BookPage>(pageAmount);
			
			for (int i = 0; i < pageAmount; i++) {
				BookPage curr = new BookPage();
				if (!curr.load(Minecraft.getMinecraft().mcDataDir.toString() + "/mods/bookData/" + br.readLine() + ".page")) {
					br.close();
					return book;
				}
				book.pages.add(curr);
			}
			
			br.close();
			book.loaded = true;
			return book;
			
		} catch (FileNotFoundException e) {
			System.err.println("Unable to load " + bookName + "! Description file is missing!");
		} catch (IOException e) {
			System.err.println("Unable to load " + bookName + "! Data corrupted!");
		}
		
		return book;

	}
	
	public boolean isLoaded() {
		return this.loaded;
	}
	
	public static void loadAll() {
		alchemicalGude = load("alchemicalGuide");
	}
	
	public BookPage getPage(int number) {
		return this.pages.get(number);
	}
	
}
