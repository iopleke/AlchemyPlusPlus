package alchemyplusplus.utility;

import alchemyplusplus.BlockRegistry;
import alchemyplusplus.ItemRegistry;
import alchemyplusplus.item.MixingFilter;
import alchemyplusplus.block.complex.distillery.DistilleryTileEntity;
import alchemyplusplus.block.complex.extractor.ExtractorTileEntity;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerTileEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MixingHelper
{

    public static void addCustomPotionEffect(ItemStack stack, PotionEffect e, boolean degrade, ItemStack filter)
    {
        Random random = new Random();
        boolean badBonus = (isBadEffect(e.getPotionID()) && filter
                .getTagCompound().getBoolean("BadBoost"));
        boolean goodBonus = (!(isBadEffect(e.getPotionID())) && filter
                .getTagCompound().getBoolean("GoodBoost"));
        boolean splashBonus = (ItemPotion.isSplash(stack.getItemDamage()) && filter
                .getTagCompound().getBoolean("SplashBoost"));

        if (stack.hasTagCompound())
        {
            if (stack.getTagCompound().hasKey("CustomPotionEffects"))
            {

                NBTTagList list = stack.getTagCompound().getTagList("CustomPotionEffects", Constants.NBT.TAG_COMPOUND);
                NBTTagCompound effectTag = new NBTTagCompound();

                if ((badBonus || goodBonus || splashBonus)
                        && (random.nextFloat() > 0.75f))
                {
                    effectTag = upgradeAndWriteEffect(effectTag, e);
                } else if (degrade)
                {
                    effectTag = degradeAndWriteEffect(effectTag, e, filter);
                    if (effectTag == null)
                    {
                        return;
                    }
                } else
                {
                    e.writeCustomPotionEffectToNBT(effectTag);
                }
                list.appendTag(effectTag);
            } else
            {
                NBTTagList list = new NBTTagList();
                NBTTagCompound effectTag = new NBTTagCompound();
                if ((badBonus || goodBonus || splashBonus)
                        && (random.nextFloat() > 0.7f))
                {
                    effectTag = upgradeAndWriteEffect(effectTag, e);
                } else if (degrade)
                {
                    effectTag = degradeAndWriteEffect(effectTag, e, filter);
                    if (effectTag == null)
                    {
                        return;
                    }
                } else
                {
                    e.writeCustomPotionEffectToNBT(effectTag);
                }
                list.appendTag(effectTag);
                stack.getTagCompound().setTag("CustomPotionEffects", list);
            }
        } else
        {
            NBTTagCompound nbt = new NBTTagCompound();
            NBTTagList list = new NBTTagList();
            NBTTagCompound effectTag = new NBTTagCompound();
            if ((badBonus || goodBonus || splashBonus)
                    && (random.nextFloat() > 0.7f))
            {
                effectTag = upgradeAndWriteEffect(effectTag, e);
            } else if (degrade)
            {
                effectTag = degradeAndWriteEffect(effectTag, e, filter);
                if (effectTag == null)
                {
                    return;
                }
            } else
            {
                e.writeCustomPotionEffectToNBT(effectTag);
            }
            list.appendTag(effectTag);
            nbt.setTag("CustomPotionEffects", list);
            stack.setTagCompound(nbt);
        }

    }

    public static void addEffect(ItemStack stack, PotionEffect e)
    {

        if (!stack.hasTagCompound())
        {
            NBTTagCompound compound = new NBTTagCompound();
            stack.setTagCompound(compound);
        }

        if (!stack.getTagCompound().hasKey("CustomPotionEffects"))
        {
            NBTTagList list = new NBTTagList();
            stack.getTagCompound().setTag("CustomPotionEffects", list);
        }

        NBTTagList list = stack.getTagCompound().getTagList(
                "CustomPotionEffects", Constants.NBT.TAG_COMPOUND);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setByte("Amplifier", (byte) e.getAmplifier());
        tag.setByte("Id", (byte) e.getPotionID());
        tag.setInteger("Duration", e.getDuration());
        tag.setBoolean("Ambient", e.getIsAmbient());
        list.appendTag(tag);

    }

    public static void betterEffect(ArrayList effects, int p1, int p2)
    {
        if (((PotionEffect) effects.get(p2)).getAmplifier() > ((PotionEffect) effects
                .get(p1)).getAmplifier())
        {
            effects.remove(p1);
        } else if (((PotionEffect) effects.get(p2)).getAmplifier() == ((PotionEffect) effects
                .get(p1)).getAmplifier())
        {
            if (((PotionEffect) effects.get(p2)).getDuration() > ((PotionEffect) effects
                    .get(p1)).getDuration())
            {
                effects.remove(p1);
            } else
            {
                effects.remove(p2);
            }
        } else
        {
            effects.remove(p2);
        }
    }

    public static NBTTagCompound degradeAndWriteEffect(NBTTagCompound tag, PotionEffect e, ItemStack filter)
    {

        Random random = new Random();

        int amp = e.getAmplifier();
        float lvlDegradeChance = (amp == 0 ? 0.5f : 1f);

        if (random.nextFloat() < MixingFilter.getLevelDegradeFactor(filter)
                * lvlDegradeChance)
        {
            if (amp == 0)
            {
                return null;
            } else
            {
                tag.setByte("Amplifier", (byte) (e.getAmplifier() - 1));
            }

        } else
        {
            tag.setByte("Amplifier", (byte) e.getAmplifier());
        }

        tag.setByte("Id", (byte) e.getPotionID());
        int dur = e.getDuration();
        dur -= (MixingFilter.getTimeDegradeFactor(filter) * dur * random
                .nextFloat());
        tag.setInteger("Duration", dur);

        tag.setBoolean("Ambient", e.getIsAmbient());

        return tag;
    }

    public static boolean distillingPossible(DistilleryTileEntity te)
    {
        if (te.getStackInSlot(1) != null && te.getStackInSlot(2) != null
                && te.isItemValidForSlot(1, te.getStackInSlot(1))
                && te.isItemValidForSlot(2, te.getStackInSlot(2)))
        {
            if (te.getStackInSlot(2).getItemDamage() < 100)
            {
                return true;
            }

        }
        return false;
    }

    public static boolean extractingPossible(ExtractorTileEntity te)
    {
        if (te.getStackInSlot(0) != null && te.getStackInSlot(1) != null
                && te.getStackInSlot(2) != null
                && (isIngridient(te.getStackInSlot(0)))
                && (te.getStackInSlot(1).getItem() == Items.potionitem)
                && (te.getStackInSlot(1).getItemDamage() != 0)
                && (!te.getStackInSlot(1).hasTagCompound())
                && (te.getStackInSlot(2).getItem() == Items.glass_bottle))
        {
            if (te.getStackInSlot(0).getItem() == ItemRegistry.fishOil)
            {
                return (te.getStackInSlot(0).getItemDamage() == 100);
            } else
            {
                return true;
            }
        } else
        {
            return false;
        }
    }

    static public int getBoilingTemperature(ItemStack stack)
    {
        return 373;
    }

    static boolean isBadEffect(int id)
    {
        if (id == 1 || id == 3 || id == 5 || id == 6 || id == 8 || id == 10
                || id == 11 || id == 12 || id == 13 || id == 14 || id == 16)
        {
            return false;
        } else
        {
            return true;
        }
    }

    public static boolean isIngridient(ItemStack itemstack)
    {
        Item item = itemstack.getItem();
        if (item == ItemRegistry.fishOil || (item == Items.nether_star))
        {
            return true;
        }
        return false;
    }

    public static void mix(FluidMixerTileEntity entity)
    {
        MixingFilter.generateCustomInfo(entity.getStackInSlot(2));

        int newDamage = (int) (entity.getStackInSlot(2).getItemDamage() + ((entity
                .getStackInSlot(2).getTagCompound().getBoolean("Unbreaking")) ? 0.7f
                : 1f)
                * mixingCost(entity.getStackInSlot(0), entity.getStackInSlot(1)));

        int potionID = entity.getStackInSlot(0).getItemDamage();

        List l1 = ((ItemPotion) (entity.getStackInSlot(0).getItem()))
                .getEffects(entity.getStackInSlot(0));
        List l2 = ((ItemPotion) (entity.getStackInSlot(1).getItem()))
                .getEffects(entity.getStackInSlot(1));

        entity.setInventorySlotContents(0, entity.getStackInSlot(3));
        entity.setInventorySlotContents(1, entity.getStackInSlot(4));

        entity.setInventorySlotContents(3, new ItemStack(Items.potionitem, 1,
                potionID));
        entity.setInventorySlotContents(4, new ItemStack(Items.potionitem, 1,
                potionID));

        entity.getStackInSlot(3).setStackDisplayName(
                EnumChatFormatting.RESET + "Potion");
        entity.getStackInSlot(4).setStackDisplayName(
                EnumChatFormatting.RESET + "Potion");

        ArrayList compiled = new ArrayList();
        for (int i = 0; i < l1.size(); i++)
        {
            compiled.add(l1.get(i));
        }
        for (int i = 0; i < l2.size(); i++)
        {
            compiled.add(l2.get(i));
        }

        while (true)
        {
            boolean flag = false;
            for (int i = 0; i < compiled.size(); i++)
            {
                if (flag)
                {
                    break;
                }
                for (int j = 0; j < compiled.size(); j++)
                {
                    if (i == j)
                    {
                        continue;
                    }
                    if (((PotionEffect) compiled.get(i)).getPotionID() == ((PotionEffect) compiled
                            .get(j)).getPotionID())
                    {
                        betterEffect(compiled, i, j);
                        flag = true;
                        break;
                    }
                }
            }
            if (flag)
            {
                continue;
            }
            break;
        }

        for (int i = 0; i < compiled.size(); i++)
        {
            addCustomPotionEffect(entity.getStackInSlot(3),
                    ((PotionEffect) compiled.get(i)), true,
                    entity.getStackInSlot(2));
            addCustomPotionEffect(entity.getStackInSlot(4),
                    ((PotionEffect) compiled.get(i)), true,
                    entity.getStackInSlot(2));
        }

        if (entity.getStackInSlot(3).getTagCompound()
                .getTagList("CustomPotionEffects", Constants.NBT.TAG_COMPOUND).tagCount() == 0)
        {
            entity.getStackInSlot(3).setItemDamage(0);
            entity.getStackInSlot(3).setStackDisplayName(
                    EnumChatFormatting.RESET + "Unleavened Brew");
        }

        if (entity.getStackInSlot(4).getTagCompound()
                .getTagList("CustomPotionEffects", Constants.NBT.TAG_COMPOUND).tagCount() == 0)
        {
            entity.getStackInSlot(4).setItemDamage(0);
            entity.getStackInSlot(4).setStackDisplayName(
                    EnumChatFormatting.RESET + "Unleavened Brew");
        }

        entity.getStackInSlot(2).setItemDamage(newDamage);
        if (entity.getStackInSlot(2).getItemDamage() > entity.getStackInSlot(2)
                .getMaxDamage() - 100)
        {
            entity.setInventorySlotContents(2, null);
        }

    }

    public static int mixingCost(ItemStack stack1, ItemStack stack2)
    {
        int cost = 0;
        List l1 = ((ItemPotion) (stack1.getItem())).getEffects(stack1);
        if (l1 == null)
        {
            return 100000;
        }
        for (Object o : l1)
        {
            cost += 50;
        }

        List l2 = ((ItemPotion) (stack2.getItem())).getEffects(stack2);
        if (l2 == null)
        {
            return 100000;
        }
        for (Object o : l2)
        {
            cost += 50;
        }

        return cost;
    }

    public static boolean mixingPossible(ItemStack stack1, ItemStack stack2, ItemStack filter, ItemStack result1, ItemStack result2)
    {
        if ((stack1 == null || stack2 == null || filter == null
                || result1 == null || result2 == null)
                || (stack1.getItem() != Items.potionitem
                || stack2.getItem() != Items.potionitem || !(filter
                .getItem() instanceof MixingFilter))
                || (ItemPotion.isSplash(stack1.getItemDamage()) != ItemPotion
                .isSplash(stack2.getItemDamage()))
                || (filter.getMaxDamage() - filter.getItemDamage() < mixingCost(
                        stack1, stack2))
                || (result1.getItem() != result2.getItem() || result1.getItem() != Items.glass_bottle))
        {
            return false;
        } else
        {
            return true;
        }
    }

    public static void performDistillation(DistilleryTileEntity te)
    {

        if (te.getStackInSlot(1).getItem() == Item.getItemFromBlock(Blocks.log) || te.getStackInSlot(1).getItem() == Item.getItemFromBlock(Blocks.log2))
        {
            // Looks like we're making wood alcohol
            ItemStack stack = new ItemStack(ItemRegistry.woodAlcohol,
                    1, te.getStackInSlot(2).getItemDamage());
            if (te.getStackInSlot(2).getItem() == Items.glass_bottle)
            {
                // Remove the glass bottle, and put in a spirit bottle

                te.setInventorySlotContents(2, stack);
            }

            int spiritbottlefull = te.getStackInSlot(2).getItemDamage() + 2;
            if (spiritbottlefull > 100)
            {
                spiritbottlefull = 100;
            }

            stack = new ItemStack(ItemRegistry.woodAlcohol, 1,
                    spiritbottlefull);
            te.decrStackSize(1, 1);
            te.setInventorySlotContents(2, stack);

            double byproductchance = Math.random() * 100;

            if (byproductchance > 60)
            {
                ItemStack byproduct = te.getStackInSlot(0);
                if (byproduct == null)
                {
                    byproduct = new ItemStack(Items.coal, 1, 1);
                } else if (byproduct.getItem() == Items.coal && byproduct.getItemDamage() == 1)
                {
                    byproduct.stackSize++;
                    if (byproductchance > 95)
                    {
                        byproduct.stackSize++;
                    }
                }
                te.setInventorySlotContents(0, byproduct);
            }

        }

    }

    public static void performExtraction(ExtractorTileEntity te)
    {

        if (te.getStackInSlot(0).getItem() == ItemRegistry.squidEye)
        {
            // Night vision I - 8230 || splash - 16422
            if (te.getStackInSlot(1).getItemDamage() == 8230
                    || te.getStackInSlot(1).getItemDamage() == 16422)
            {
                addEffect(te.getStackInSlot(1), new PotionEffect(15, 100));
            }
        } else if (te.getStackInSlot(0).getItem() == ItemRegistry.confusionPotion)
        {
            // Slowness I - 8234 || splash - 16426
            if (te.getStackInSlot(1).getItemDamage() == 8234
                    || te.getStackInSlot(1).getItemDamage() == 16426)
            {
                addEffect(te.getStackInSlot(1), new PotionEffect(9, 100));
            }
        } else if (te.getStackInSlot(0).getItem() == ItemRegistry.springyCord)
        {
            // Speed I - 8194 || splash - 16386
            if (te.getStackInSlot(1).getItemDamage() == 8194
                    || te.getStackInSlot(1).getItemDamage() == 16386)
            {
                addEffect(te.getStackInSlot(1), new PotionEffect(8, 100));
            }
        } else if (te.getStackInSlot(0).getItem() == ItemRegistry.ironPowder)
        {
            // Strength I - 8201 || splash - 16393
            if (te.getStackInSlot(1).getItemDamage() == 8201
                    || te.getStackInSlot(1).getItemDamage() == 16393)
            {
                addEffect(te.getStackInSlot(1), new PotionEffect(11, 100));
            }
        } else if (te.getStackInSlot(0).getItem() == Item.getItemFromBlock(BlockRegistry.fleshBlock))
        {
            // Poison I - 8196 || splash - 16388
            if (te.getStackInSlot(1).getItemDamage() == 8196
                    || te.getStackInSlot(1).getItemDamage() == 16388)
            {
                addEffect(te.getStackInSlot(1), new PotionEffect(17, 100));
            }
        } else if (te.getStackInSlot(0).getItem() == ItemRegistry.fishOil)
        {
            // Regen I - 8193 || splash 16385
            if (te.getStackInSlot(1).getItemDamage() == 8193
                    || te.getStackInSlot(1).getItemDamage() == 16385)
            {
                addEffect(te.getStackInSlot(1), new PotionEffect(13, 100));
            }
        } else if (te.getStackInSlot(0).getItem() == Items.nether_star)
        {
            // Instant Damage I - 8268 || splash 16460
            if (te.getStackInSlot(1).getItemDamage() == 8268
                    || te.getStackInSlot(1).getItemDamage() == 16460)
            {
                addEffect(te.getStackInSlot(1), new PotionEffect(20, 100));
            }
        }

        te.decrStackSize(0, 1);
        ItemStack stack = te.getStackInSlot(2);
        te.setInventorySlotContents(2, te.getStackInSlot(1));
        te.setInventorySlotContents(1, stack);

    }

    public static void sprayingDegrade(ItemStack potion)
    {
        ArrayList<PotionEffect> list = (ArrayList<PotionEffect>) ((ItemPotion) (potion
                .getItem())).getEffects(potion);
        int effectAmount = 0;

        NBTTagList effects = new NBTTagList();
        if (!potion.hasTagCompound())
        {
            potion.setTagCompound(new NBTTagCompound());
        }
        potion.getTagCompound().setTag("CustomPotionEffects", effects);

        for (int i = 0; i < list.size(); i++)
        {

            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Id", (byte) list.get(i).getPotionID());
            tag.setByte("Amplifier", (byte) list.get(i).getAmplifier());
            int dur = (int) (list.get(i).getDuration() * 0.95f);
            if (dur < 200)
            {
                continue;
            }
            tag.setInteger("Duration", dur);
            tag.setBoolean("Ambient", list.get(i).getIsAmbient());

            effects.appendTag(tag);
            effectAmount++;
        }

        if (effectAmount == 0)
        {
            potion.setItemDamage(0);
            potion.setStackDisplayName(EnumChatFormatting.RESET + "Unleavened Brew");
        }

    }

    public static NBTTagCompound upgradeAndWriteEffect(NBTTagCompound tag, PotionEffect e)
    {

        Random random = new Random();

        tag.setByte("Id", (byte) e.getPotionID());

        int amp = e.getAmplifier();
        float lvlUpgradeChance = 0.5f - (amp * 0.1f);
        if (random.nextFloat() < lvlUpgradeChance)
        {
            tag.setByte("Amplifier", (byte) (e.getAmplifier() + 1));
        } else
        {
            tag.setByte("Amplifier", (byte) (e.getAmplifier()));
        }

        int dur = e.getDuration()
                + ((int) (e.getDuration() * 0.2f * random.nextFloat()));
        tag.setInteger("Duration", dur);

        tag.setBoolean("Ambient", e.getIsAmbient());

        return tag;

    }

}
