package alchemyplusplus.potion;

import alchemyplusplus.reference.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class PotionFluidBlock extends BlockFluidClassic
{
	private final FluidStack fluid;
	private Potion potion;

	@SideOnly(Side.CLIENT)
	protected IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon flowingIcon;

	public PotionFluidBlock(Fluid fluid, Material material)
	{
		super(fluid, material);

		this.fluid = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
		this.setBlockName(fluid.getUnlocalizedName());
	}

	public PotionFluidBlock(Fluid fluid, Material material, Potion potion)
	{
		super(fluid, material);
		this.potion = potion;
		this.fluid = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
		this.setBlockName(fluid.getUnlocalizedName());
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (this.potion != null)
		{
			if (entity instanceof EntityLivingBase)
			{
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(this.potion.getId(), 40, 0));
			}
		}

	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{

		super.updateTick(world, x, y, z, rand);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		this.stillIcon = register.registerIcon(Textures.Icon.POTION_STILL);
		this.flowingIcon = register.registerIcon(Textures.Icon.POTION_FLOWING);
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side > 1)
		{
			return this.flowingIcon;
		}
		return this.stillIcon;
	}

	@Override
	public String getLocalizedName()
	{
		return fluid.getLocalizedName();
	}

	@Override
	public String getUnlocalizedName()
	{
		return fluid.getUnlocalizedName();
	}

	@Override
	public int getBlockColor()
	{
		return fluid.getFluid().getColor();
	}

	@Override
	public int getRenderColor(int i)
	{
		return fluid.getFluid().getColor();
	}

	@Override
	public int colorMultiplier(IBlockAccess access, int x, int y, int z)
	{
		return fluid.getFluid().getColor();
	}
}
