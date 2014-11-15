package alchemyplusplus.potion;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class PotionFluidHelper
{
    public static Fluid getFluidForBlock(Block block)
    {
        // more hotfixing the broken vanilla fluid handling (fluid registry only has blocks for still water & still lava)
        if (block == Blocks.flowing_water)
        {
            return FluidRegistry.WATER;
        }

        if (block == Blocks.flowing_lava)
        {
            return FluidRegistry.LAVA;
        }

        return FluidRegistry.lookupFluidForBlock(block);
    }

    public static Block getBlockForFluid(Fluid fluid)
    {
        Block fluidBlock = fluid.getBlock();

        if (fluidBlock == Blocks.water)
        {
            fluidBlock = Blocks.flowing_water;
        } else if (fluidBlock == Blocks.lava)
        {
            fluidBlock = Blocks.flowing_lava;
        }

        return fluidBlock;
    }

    public static FluidStack getFluidFromWorld(World world, int[] coords)
    {
        if (world.getTileEntity(coords[0], coords[1], coords[2]) != null)
        {
            // todo: send EventOmnibucketPickupNBT

            return null;
        }

        Block block = world.getBlock(coords[0], coords[1], coords[2]);
        int l = world.getBlockMetadata(coords[0], coords[1], coords[2]);

        Fluid targetFluid = getFluidForBlock(block);

        if (l == 0 && targetFluid != null)
        {
            return new FluidStack(targetFluid, 1000);
        }

        return null;
    }

    // todo: send event for NBT fluid placement
    public static boolean placeFluidInWorld(int[] coords, World world, FluidStack fluidstack)
    {
        Fluid fluid = fluidstack.getFluid();
        if (!fluid.canBePlacedInWorld() || fluidstack.tag != null)
        {
            return false;
        }

        Block block = world.getBlock(coords[0], coords[1], coords[2]);
        Material material = block.getMaterial();

        if (material.isSolid())
        {
            return false;
        }

        if (world.provider.isHellWorld && fluid == FluidRegistry.WATER)
        {
            world.playSoundEffect(coords[0] + 0.5D, coords[1] + 0.5D, coords[2] + 0.5D, "random.fizz", 0.5F, 2.6F + world.rand.nextFloat() - world.rand.nextFloat() * 0.8F);

            for (int l = 0; l < 8; ++l)
            {
                world.spawnParticle("largesmoke", coords[0] + Math.random(), coords[1] + Math.random(), coords[2] + Math.random(), 0.0D, 0.0D, 0.0D);
            }
        } else
        {
            if (!world.isRemote && !material.isLiquid())
            {
                world.func_147480_a(coords[0], coords[1], coords[2], true);
            }

            world.setBlock(coords[0], coords[1], coords[2], getBlockForFluid(fluid), 0, 3);
        }

        return true;
    }

    public static boolean playerPlaceFluid(EntityPlayer player, int[] coords, int side, ItemStack stack, World world, FluidStack fluidstack)
    {
        return player.canPlayerEdit(coords[0], coords[1], coords[2], side, stack) && placeFluidInWorld(coords, world, fluidstack);
    }

    public static FluidStack playerPickupFluid(EntityPlayer player, World world, int[] coords, int side, ItemStack stack)
    {
        return player.canPlayerEdit(coords[0], coords[1], coords[2], side, stack) ? getFluidFromWorld(world, coords) : null;
    }

    public static boolean insertFluid(FluidStack stack, IFluidHandler fluidHandler, ForgeDirection direction)
    {
        int inserted = fluidHandler.fill(direction, stack, false);

        if (inserted != stack.amount)
        {
            return false;
        }

        fluidHandler.fill(direction, stack, true);

        return true;
    }

    public static boolean insertFluid(FluidStack stack, IFluidHandler fluidHandler)
    {
        return insertFluid(stack, fluidHandler, ForgeDirection.UNKNOWN);
    }

    public static boolean insertFluid(FluidStack stack, IFluidTank fluidHandler)
    {
        int inserted = fluidHandler.fill(stack, false);

        if (inserted != stack.amount)
        {
            return false;
        }

        fluidHandler.fill(stack, true);

        return true;
    }
}
