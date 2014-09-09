package alchemyplusplus.reference;

import alchemyplusplus.utility.ResourceLocationHelper;
import net.minecraft.util.ResourceLocation;

public class Textures
{
    public static class Gui
    {
        public static final String GUI_SHEET_LOCATION = "textures/gui/";
        public static final ResourceLocation BOOK_LEFT = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "bookLeft.png");
        public static final ResourceLocation BOOK_RIGHT = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "bookRight.png");
        public static final ResourceLocation DISTLLERY = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "distillery.png");
        public static final ResourceLocation EXTRACTOR = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "extractor.png");
        public static final ResourceLocation MIXER = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "mixer.png");
    }

    public static class Pages
    {
        public static final String BOOK_DATA_LOCATION = "textures/bookData/";
        public static final ResourceLocation ALL = ResourceLocationHelper.getResourceLocation(BOOK_DATA_LOCATION + "textureMap.png");
    }

    public static class Model
    {
        public static final String MODEL_TEXTURE_LOCATION = "textures/models/";
        public static final ResourceLocation DIFFUSER = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "diffuser.png");
        public static final ResourceLocation POTION_JUG = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "potionJug.png");
        public static final ResourceLocation DISTILLERY = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "distillery.png");
        public static final ResourceLocation LIQUID_MIXER = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "liquidMixer.png");
    }
}
