package mokonaDesu.alchemypp.model;

import mokonaDesu.alchemypp.tileentities.TileEntityAlchemicalApparatus;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public abstract class APPModel extends ModelBase {

	public abstract void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, TileEntityAlchemicalApparatus tileEntity);
	
}
