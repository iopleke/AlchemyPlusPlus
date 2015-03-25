package alchemyplusplus.item;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.reference.Naming;
import alchemyplusplus.registry.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jakimbox.prefab.item.BasicItem;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MortarAndPestle extends BasicItem
{

    private IIcon[] icons;
    private IIcon filledIcon;

    public MortarAndPestle(String itemname)
    {
        super(AlchemyPlusPlus.ID, itemname);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

            if (movingobjectposition != null)
            {

                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                {
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    Block block = world.getBlock(i, j, k);
                    Material material = block.getMaterial();
                    int meta = world.getBlockMetadata(i, j, k);

                    if (material == Material.water && meta == 0)
                    {
                        this.setFilled(true, stack);
                        return stack;
                    }
                    if (material == Material.grass || block instanceof BlockTallGrass)
                    {
                        if (this.getFilled(stack))
                        {
                            EntityItem itemDrop = player.dropItem(ItemRegistry.pasteGrass, 1);
                            itemDrop.delayBeforeCanPickup = 0;
                        } else
                        {
                            EntityItem itemDrop = player.dropItem(ItemRegistry.crushedGrass, 1);
                            itemDrop.delayBeforeCanPickup = 0;
                        }
                    } else if (material == Material.leaves || block instanceof BlockLeaves)
                    {
                        if (this.getFilled(stack))
                        {
                            EntityItem itemDrop = player.dropItem(ItemRegistry.pasteLeaves, 1);
                            itemDrop.delayBeforeCanPickup = 0;
                        } else
                        {
                            EntityItem itemDrop = player.dropItem(ItemRegistry.crushedLeaves, 1);
                            itemDrop.delayBeforeCanPickup = 0;
                        }
                    }
                }
            }
        }
        this.setFilled(false, stack);
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[4];
        filledIcon = iconRegister.registerIcon(iconString + "Water");
        icons[0] = iconRegister.registerIcon(iconString + "Wooden");
        icons[1] = iconRegister.registerIcon(iconString + "Stone");
        icons[2] = iconRegister.registerIcon(iconString + "Iron");
        icons[3] = iconRegister.registerIcon(iconString + "Obsidian");

    }

    public void setFilled(boolean state, ItemStack stack)
    {
        if (!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setBoolean("Filled", state);
    }

    public boolean getFilled(ItemStack stack)
    {
        if (!stack.hasTagCompound())
        {
            return false;
        }
        return stack.getTagCompound().getBoolean("Filled");
    }

    public final void toggleFilled(ItemStack stack)
    {
        setFilled(!getFilled(stack), stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass == 1)
        {
            if (getFilled(stack))
            {
                return this.filledIcon;
            }
        }
        return icons[stack.getItemDamage()];
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i <= 3; ++i)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
    {
        String tier;
        switch (itemstack.getItemDamage())
        {
            case 0:
                tier = "wood";
                break;
            case 1:
                tier = "stone";
                break;
            case 2:
                tier = "iron";
                break;
            case 3:
                tier = "obsidian";
                break;
            default:
                tier = "wood";
                break;
        }
        String material = StatCollector.translateToLocal("item.mortarAndPestle.material") + ": " + StatCollector.translateToLocal("item.mortarAndPestle.material." + tier);
        String flavorText = StatCollector.translateToLocal("item.mortarAndPestle.flavor." + tier);
        list.add(Naming.Colors.Yellow + material);
        list.add(Naming.Colors.LightBlue + flavorText.substring(0, flavorText.indexOf(",")));
        list.add(Naming.Colors.LightBlue + flavorText.substring(flavorText.indexOf(",") + 2));
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return 2;
    }
}
