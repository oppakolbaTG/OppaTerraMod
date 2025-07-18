package net.oppakolba.oppamod.entity.projectile;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class FireballEntity extends AbstractHurtingProjectile  {
    private static final EntityDataAccessor<Float> POWER = SynchedEntityData.defineId(FireballEntity.class, EntityDataSerializers.FLOAT);

    public FireballEntity(EntityType<? extends FireballEntity> entityType, Level level){
        super(entityType, level);
    }

    public FireballEntity(EntityType<? extends FireballEntity> entityType, Level level, LivingEntity entity, double xP, double yP, double zP, float power){
        super(entityType, entity, xP, yP, zP, level);
        this.entityData.set(POWER, power);
        this.setPos(this.getX(), this.getY() + 4, this.getZ());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(POWER, 1.5f);
    }

    @Override
    public void tick() {
        super.tick();;

    }

    public float getPower(){
        return this.entityData.get(POWER);
    }


    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        if(!this.level.isClientSide()){
            Entity owner = this.getOwner();
            this.level.explode(owner , this.getX(), this.getY(), this.getZ(),getPower() / 2.0f, Explosion.BlockInteraction.DESTROY);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if(!this.level.isClientSide()){
            Entity owner = this.getOwner();
            this.level.explode(owner , this.getX(), this.getY(), this.getZ(),getPower() / 2.0f, Explosion.BlockInteraction.DESTROY);
            this.discard();
        }
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }


}
