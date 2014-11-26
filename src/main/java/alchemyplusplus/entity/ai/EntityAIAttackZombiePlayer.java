package alchemyplusplus.entity.ai;

import alchemyplusplus.helper.ZombieHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityAIAttackZombiePlayer extends EntityAIBase
{

    World worldObj;
    EntityCreature attacker;
    EntityPlayer zombiePlayer;
    /**
     * An amount of decrementing ticks that allows the entity to attack once the tick reaches 0.
     */
    int attackTick;
    /**
     * The speed with which the mob will approach the target
     */
    double speedTowardsTarget;
    /**
     * When true, the mob will continue chasing its target, even if it can't find a path to them right now.
     */
    boolean longMemory;
    /**
     * The PathEntity of our entity.
     */
    PathEntity entityPathEntity;
    private int field_75445_i;
    private double field_151497_i;
    private double field_151495_j;
    private double field_151496_k;
    private double radius;
    private static final String __OBFID = "CL_00001595";

    private int failedPathFindingPenalty;

    public EntityAIAttackZombiePlayer(EntityCreature creature, double speed, double radius, boolean longMemory)
    {
        this.attacker = creature;
        this.worldObj = creature.worldObj;
        this.speedTowardsTarget = speed;
        this.longMemory = longMemory;
        this.radius = radius;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        zombiePlayer = ZombieHelper.getNearestZombie(this.attacker, radius, false);

        if (zombiePlayer == null || !zombiePlayer.isEntityAlive() || this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
        {
            return false;
        } else
        {
            if (--this.field_75445_i <= 0)
            {
                this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(zombiePlayer);
                this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
                return this.entityPathEntity != null;
            } else
            {
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {;
        return zombiePlayer == null ? false : (!zombiePlayer.isEntityAlive() ? false : (!this.longMemory ? !this.attacker.getNavigator().noPath() : this.attacker.isWithinHomeDistance(MathHelper.floor_double(zombiePlayer.posX), MathHelper.floor_double(zombiePlayer.posY), MathHelper.floor_double(zombiePlayer.posZ))));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.field_75445_i = 0;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.attacker.getNavigator().clearPathEntity();
        this.zombiePlayer = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.attacker.getLookHelper().setLookPositionWithEntity(zombiePlayer, 30.0F, 30.0F);
        double d0 = this.attacker.getDistanceSq(zombiePlayer.posX, zombiePlayer.boundingBox.minY, zombiePlayer.posZ);
        double d1 = (double) (this.attacker.width * 2.0F * this.attacker.width * 2.0F + zombiePlayer.width);
        --this.field_75445_i;

        if ((this.longMemory || this.attacker.getEntitySenses().canSee(zombiePlayer)) && this.field_75445_i <= 0 && (this.field_151497_i == 0.0D && this.field_151495_j == 0.0D && this.field_151496_k == 0.0D || zombiePlayer.getDistanceSq(this.field_151497_i, this.field_151495_j, this.field_151496_k) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F))
        {
            this.field_151497_i = zombiePlayer.posX;
            this.field_151495_j = zombiePlayer.boundingBox.minY;
            this.field_151496_k = zombiePlayer.posZ;
            this.field_75445_i = failedPathFindingPenalty + 4 + this.attacker.getRNG().nextInt(7);

            if (this.attacker.getNavigator().getPath() != null)
            {
                PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
                if (finalPathPoint != null && zombiePlayer.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1)
                {
                    failedPathFindingPenalty = 0;
                } else
                {
                    failedPathFindingPenalty += 10;
                }
            } else
            {
                failedPathFindingPenalty += 10;
            }

            if (d0 > 1024.0D)
            {
                this.field_75445_i += 10;
            } else if (d0 > 256.0D)
            {
                this.field_75445_i += 5;
            }

            if (!this.attacker.getNavigator().tryMoveToEntityLiving(zombiePlayer, this.speedTowardsTarget))
            {
                this.field_75445_i += 15;
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);

        if (d0 <= d1 && this.attackTick <= 20)
        {
            this.attackTick = 20;
            zombiePlayer.attackEntityFrom(DamageSource.causeMobDamage(attacker), 1F + ((float) worldObj.rand.nextInt(4)) / 3);
        }
    }
}
