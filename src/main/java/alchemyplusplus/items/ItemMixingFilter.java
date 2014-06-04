package alchemyplusplus.items;

import java.util.List;
import java.util.Random;

import alchemyplusplus.APPConfigManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemMixingFilter extends APPItem {

    public ItemMixingFilter(int id, String iconName, int durability) {
        super(id, iconName);
        this.setMaxDamage(durability);
        this.isDamageable();
        this.maxStackSize = 1;
        this.setNoRepair();
        this.setCreativeTab(APPConfigManager.appCreativeTab);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (isRevealed(stack)) {
            list.add((stack.getTagCompound().getFloat("DurationDegradeFactor") > 0.6f ? EnumChatFormatting.DARK_RED : stack.getTagCompound().getFloat("DurationDegradeFactor") > 0.3f ? EnumChatFormatting.GOLD : EnumChatFormatting.DARK_GREEN) + "Time degrade factor: " + ((int) (stack.getTagCompound().getFloat("DurationDegradeFactor") * 100)) + "%");

            list.add((stack.getTagCompound().getFloat("LevelDegradeFactor") > 0.6f ? EnumChatFormatting.DARK_RED : stack.getTagCompound().getFloat("LevelDegradeFactor") > 0.3f ? EnumChatFormatting.GOLD : EnumChatFormatting.DARK_GREEN) + "Level degrade factor: " + ((int) (stack.getTagCompound().getFloat("LevelDegradeFactor") * 100)) + "%" + EnumChatFormatting.RESET);
            if (stack.getTagCompound().getBoolean("GoodBoost"))
                list.add("Buff Booster");
            else if (stack.getTagCompound().getBoolean("BadBoost"))
                list.add("Debuff Booster");
            else if (stack.getTagCompound().getBoolean("SplashBoost"))
                list.add("Splash Booster");
            if (stack.getTagCompound().getBoolean("Unbreaking"))
                list.add("Unbreaking");

        } else
            list.add("<Unknown>");
    }

    public static void generateCustomInfo(ItemStack stack) {
        if (hasCustomInfo(stack))
            return; // already generated
        else {
            if (!stack.hasTagCompound())
                stack.setTagCompound(new NBTTagCompound());
            Random random = new Random();
            NBTTagCompound tags = stack.getTagCompound();
            tags.setBoolean("CustomFilterInfo", true);
            tags.setFloat("DurationDegradeFactor", random.nextFloat());
            tags.setFloat("LevelDegradeFactor", random.nextFloat());

            // potion boost chance
            if (random.nextFloat() >= 0.65) {
                float boost = random.nextFloat();
                if (boost >= 0.8)
                    tags.setBoolean("SplashBoost", true);
                else if (boost >= 0.4)
                    tags.setBoolean("GoodBoost", true);
                else
                    tags.setBoolean("BadBoost", true);
            }
            if (random.nextFloat() >= 0.55) {
                tags.setBoolean("Unbreaking", true);
            }
        }
    }

    public static void reveal(ItemStack stack) {
        if (isRevealed(stack))
            return;
        else {
            generateCustomInfo(stack);
            stack.getTagCompound().setBoolean("Revealed", true);
        }

    }

    public static boolean hasCustomInfo(ItemStack stack) {
        return (stack.hasTagCompound() && stack.getTagCompound().getBoolean("CustomFilterInfo"));
    }

    public static boolean isRevealed(ItemStack stack) {
        return (stack.hasTagCompound() && stack.getTagCompound().getBoolean("Revealed"));
    }

    public static float getTimeDegradeFactor(ItemStack stack) {
        return stack.getTagCompound().getFloat("DurationDegradeFactor");
    }

    public static float getLevelDegradeFactor(ItemStack stack) {
        return stack.getTagCompound().getFloat("LevelDegradeFactor");
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        if (isRevealed(stack))
            return false;
        else {
            reveal(stack);
            return true;
        }
    }

}
