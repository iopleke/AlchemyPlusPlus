package alchemyplusplus.potion.fluid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fluids.FluidTank;

public class PotionFluidTank extends FluidTank
{
    // Stored potion information
    public int potionID;
    public int potionDamageValue;
    public List<PotionEffect> potionEffects;

    public PotionFluidTank(PotionFluidStack stack, int capacity)
    {
        super(stack, capacity);

        potionEffects = stack.potionEffects;
    }

    public PotionFluidTank(int capacity)
    {
        super(null, capacity);
        potionEffects = new ArrayList<PotionEffect>();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setInteger("diffuserTankAmount", getFluidAmount());
        nbt.setInteger("effectsCount", potionEffects.size());
        Iterator effectIterator = potionEffects.iterator();
        int count = 0;
        while (effectIterator.hasNext())
        {
            PotionEffect effect = (PotionEffect) effectIterator.next();
            nbt.setInteger("effect" + count, effect.getPotionID());
            nbt.setInteger("duration" + count, effect.getDuration());
            count++;
        }
        return nbt;
    }

    @Override
    public FluidTank readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        potionEffects = new ArrayList();

        int count = nbt.getInteger("effectsCount");
        while (count >= 0)
        {
            int effectID = nbt.getInteger("effect" + count);
            int duration = nbt.getInteger("duration" + count);
            int amplifier = 0;

            potionEffects.add(new PotionEffect(effectID, duration, amplifier));
            count--;
        }

        return this;
    }
}
