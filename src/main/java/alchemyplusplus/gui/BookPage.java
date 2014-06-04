package mokonaDesu.alchemypp.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class BookPage {

	private String[] text = new String[32];
	private String[] title = new String[2];
	
	//Using one texture sheet for all the book pages... For now
	private static String texture = "AlchemyPP:textures/bookData/textureMap.png";
	
	//Image data for this page
	private ArrayList<PageImageData> images;
	
	public boolean load(String path) {
		System.out.println("Loading book page data: " + path);
		try {

			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			
			//reading titles
			for (int i = 0; i < 2; i++) this.title[i] = br.readLine();
			
			//reading text
			for (int i = 0; i < 32; i++) this.text[i] = br.readLine();
			
			int imageAmount = Integer.parseInt(br.readLine());
			images = new ArrayList<PageImageData>(imageAmount);
			
			for (int i = 0; i < imageAmount; i++) {
				PageImageData data = new PageImageData();
				data.page = Integer.parseInt(br.readLine());
				data.x = Integer.parseInt(br.readLine());
				data.y = Integer.parseInt(br.readLine());
				data.offX = Integer.parseInt(br.readLine());
				data.offY = Integer.parseInt(br.readLine());
				data.sizeX = Integer.parseInt(br.readLine());
				data.sizeY = Integer.parseInt(br.readLine());
				
				images.add(data);
			}
			
			br.close();
			return true;
		} catch (FileNotFoundException e) {
		System.out.println("Unable to read page data! File is missing! " + path);
		} catch (IOException e) {
		System.out.println("Unable to read page data! Data is corrupted! " + path);	
		}
		return false;
	}

	void drawPage(GuiAlchemicalGuide gui) {
		int x0 = (gui.width / 2) - 146, x1 = gui.width / 2, y0 = (gui.height - 180) / 2;
		
		//Rendering images on the page
		//First binding the texture
		gui.getRenderEngine().bindTexture(new ResourceLocation(texture));
		//then render all the images from the list
		for (PageImageData data : images) {
			 gui.drawTexturedModalRect(data.page == 0 ? x0 + data.x : x1 + data.x , y0 + data.y, data.offX, data.offY, data.sizeX, data.sizeY);  
		}
		
		 for (int i = 0; i < 16; i++) {
			 gui.getFontRenderer().drawString(text[i], x0 + 19, y0 + 14 + (i * 9), 0);
			 gui.getFontRenderer().drawString(text[i + 16], x1 + 19, y0 + 14 + (i * 9), 0);
		 }

		 gui.getFontRenderer().drawString(title[0], x0 + 130 - (5 * title[0].length()), y0 + 162, 0);
		 gui.getFontRenderer().drawString(title[1], x1 + 12, y0 + 162, 0);
	}
	
}
