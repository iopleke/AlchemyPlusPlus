package alchemyplusplus.reference;

import alchemyplusplus.AlchemyPlusPlus;
import net.minecraft.util.ResourceLocation;

public class Textures
{
	public static class Gui
	{
		public static final String GUI_SHEET_LOCATION = "textures/gui/";
		public static final ResourceLocation BOOK_LEFT = Location.getResourceLocation(GUI_SHEET_LOCATION + "bookLeft.png");
		public static final ResourceLocation BOOK_RIGHT = Location.getResourceLocation(GUI_SHEET_LOCATION + "bookRight.png");
		public static final ResourceLocation DISTLLERY = Location.getResourceLocation(GUI_SHEET_LOCATION + "distillery.png");
		public static final ResourceLocation EXTRACTOR = Location.getResourceLocation(GUI_SHEET_LOCATION + "extractor.png");
		public static final ResourceLocation MIXER = Location.getResourceLocation(GUI_SHEET_LOCATION + "mixer.png");
	}

	public static class Page
	{
		public static final String BOOK_DATA_LOCATION = "textures/bookData/";
		public static final ResourceLocation ALL = Location.getResourceLocation(BOOK_DATA_LOCATION + "textureMap.png");
	}

	public static class Model
	{
		public static final String MODEL_TEXTURE_LOCATION = "textures/models/";
		public static final ResourceLocation DIFFUSER = Location.getResourceLocation(MODEL_TEXTURE_LOCATION + "diffuser.png");
		public static final ResourceLocation DISTILLERY = Location.getResourceLocation(MODEL_TEXTURE_LOCATION + "distillery.png");
		public static final ResourceLocation FLUID_MIXER = Location.getResourceLocation(MODEL_TEXTURE_LOCATION + "fluidMixer.png");
		public static final ResourceLocation POTION_JUG = Location.getResourceLocation(MODEL_TEXTURE_LOCATION + "potionJug.png");
	}

	public static class Icon
	{
		public static final String ICON_SUFFIX = "Icon";
		public static final String DIFFUSER = AlchemyPlusPlus.ID + ":" + Naming.Blocks.DIFFUSER + Textures.Icon.ICON_SUFFIX;
		public static final String POTION_JUG = AlchemyPlusPlus.ID + ":" + Naming.Blocks.POTION_JUG + Textures.Icon.ICON_SUFFIX;
		public static final String POTION_STILL = AlchemyPlusPlus.ID + ":fluidStill" + Textures.Icon.ICON_SUFFIX;
		public static final String POTION_FLOWING = AlchemyPlusPlus.ID + ":fluidFlowing" + Textures.Icon.ICON_SUFFIX;
	}
}
