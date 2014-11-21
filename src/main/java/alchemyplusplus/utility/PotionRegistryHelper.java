package alchemyplusplus.utility;

import net.minecraft.potion.Potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PotionRegistryHelper
{
    public static boolean expandPotionRegistry()
    {
        for (Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                    Potion[] potionTypes;
                    potionTypes = (Potion[])f.get(null);
                    final Potion[] newPotionTypes = new Potion[Math.max(256,potionTypes.length)];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                    return true;
                }
            } catch (Exception e) {
                System.err.println("Severe error, please report this to the mod author:");
                System.err.println(e);
                return false;
            }
        }
        return false;
    }
}
