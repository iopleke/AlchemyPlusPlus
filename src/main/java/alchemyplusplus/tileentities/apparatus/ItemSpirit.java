package alchemyplusplus.tileentities.apparatus;

import java.util.List;

import alchemyplusplus.utility.BlockRegistry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpirit extends Item
{

    public static void fill(ItemStack stack, int percent)
    {
        if (stack.getItemDamage() < 100)
        {
            stack.setItemDamage(stack.getItemDamage() + percent);
        }
        if (stack.getItemDamage() > 100)
        {
            stack.setItemDamage(100);
        }
    }

    private final Icon icons[] = new Icon[6];

    public ItemSpirit(int id)
    {
        super(id);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.getItemDamage() == 100)
        {
            list.add("Bottle is full");
        } else
        {
            list.add("Bottle is " + stack.getItemDamage() + "% full");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int damage)
    {
        return this.icons[damage / 20];
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
            int par4, int par5, int par6, int par7, float par8, float par9,
            float par10)
    {

        if (!world.isRemote)
        {

            if (world.getBlockId(par4, par5, par6) == BlockRegistry.appBlockAlchemicalApparatus.blockID)
            {
                TileEntityAlchemicalApparatus entity = ((TileEntityAlchemicalApparatus) world
                        .getBlockTileEntity(par4, par5, par6));
                if (entity.hasBottom()
                        && entity.bottom instanceof ApparatusApplicationSpiritLamp)
                {
                    (((ApparatusApplicationSpiritLamp) ((TileEntityAlchemicalApparatus) world
                            .getBlockTileEntity(par4, par5, par6)).bottom))
                            .fill(stack);
                    player.addChatMessage("Filled the lamp");
                    PacketDispatcher.sendPacketToAllPlayers(world
                            .getBlockTileEntity(par4, par5, par6)
                            .getDescriptionPacket());
                    return true;
                } else
                {
                    player.addChatMessage("This Apparatus has no spiritlamp to fill");
                    return true;
                }
            }

        }

        if (!player.isSneaking() && stack.getItemDamage() >= 10)
        {
            world.playSoundAtEntity(player, "random.drink", 1F,
                    1F / (itemRand.nextFloat() * 0.4F + 0.8F));
            player.addPotionEffect(new PotionEffect(9, 600));
            player.addPotionEffect(new PotionEffect(5, 400, 3));
            if (stack.getItemDamage() == 10)
            {
                stack.itemID = Item.glassBottle.itemID;
                stack.stackSize = 1;
                stack.setItemDamage(0);
            } else
            {
                stack.setItemDamage(stack.getItemDamage() - 10);
            }

            return true;
        }

        if (!player.isSneaking() || stack.getItemDamage() == 100)
        {
            return false;

        } else
        {
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        for (int i = 0; i < 6; i++)
        {
            this.icons[i] = iconRegister.registerIcon("AlchemyPlusPlus:spirit"
                    + (i * 20));
        }
    }

}
