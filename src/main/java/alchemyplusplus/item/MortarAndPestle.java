package alchemyplusplus.item;

import alchemyplusplus.AlchemyPlusPlus;
import alchemyplusplus.reference.Naming;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MortarAndPestle extends ItemBasic
{

    private IIcon[] icons;
    private IIcon filledIcon;
    public boolean isFilled = false;

    public MortarAndPestle(String itemname)
    {
        super(itemname);
        this.iconName = AlchemyPlusPlus.ID + ":" + itemname;
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

                    Material material = world.getBlock(i, j, k).getMaterial();
                    int l = world.getBlockMetadata(i, j, k);

                    if (material == Material.water && l == 0)
                    {

                        this.setFilled(true);
                        return stack;

                    }
                }
            }
        }
        this.setFilled(false);
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[4];
        filledIcon = iconRegister.registerIcon(iconName + "Water");
        icons[0] = iconRegister.registerIcon(iconName + "Wooden");
        icons[1] = iconRegister.registerIcon(iconName + "Stone");
        icons[2] = iconRegister.registerIcon(iconName + "Iron");
        icons[3] = iconRegister.registerIcon(iconName + "Obsidian");

    }

    public final void setFilled(boolean filledState)
    {
        this.isFilled = filledState;
    }

    public boolean getFilled()
    {
        return this.isFilled;
    }

    public final void toggleFilled()
    {
        this.isFilled = !this.isFilled;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
        if (pass == 1)
        {
            if (this.isFilled)
            {
                return this.filledIcon;
            }
        }
        return icons[damage];
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
