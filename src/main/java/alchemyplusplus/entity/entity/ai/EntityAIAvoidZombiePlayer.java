package alchemyplusplus.entity.entity.ai;

import alchemyplusplus.helper.ZombieHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

public class EntityAIAvoidZombiePlayer extends EntityAIBase
{

    /**
     * The entity we are attached to
     */
    private EntityCreature theEntity;
    private double farSpeed;
    private double nearSpeed;
    private Entity closestLivingEntity;
    private float distanceFromEntity;
    /**
     * The PathEntity of our entity
     */
    private PathEntity entityPathEntity;
    /**
     * The PathNavigate of our entity
     */
    private PathNavigate entityPathNavigate;
    /**
     * The class of the entity we should avoid
     */
    private static final String __OBFID = "CL_00001574";

    public EntityAIAvoidZombiePlayer(EntityCreature entityCreature, float proximity, double slowSpeed, double closeSpeed)
    {
        this.theEntity = entityCreature;
        this.distanceFromEntity = proximity;
        this.farSpeed = slowSpeed;
        this.nearSpeed = closeSpeed;
        this.entityPathNavigate = entityCreature.getNavigator();
        this.setMutexBits(1);
    }

    @Override
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        this.closestLivingEntity = ZombieHelper.getNearestZombie(this.theEntity, this.distanceFromEntity, true);

        if (this.closestLivingEntity == null)
        {
            return false;
        }

        Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, Vec3.createVectorHelper(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));

        if (vec3 == null)
        {
            return false;
        } else if (this.closestLivingEntity.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity))
        {
            return false;
        } else
        {
            this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
            return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(vec3);
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.entityPathNavigate.noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.closestLivingEntity = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D)
        {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
        } else
        {
            this.theEntity.getNavigator().setSpeed(this.farSpeed);
        }
    }
}
