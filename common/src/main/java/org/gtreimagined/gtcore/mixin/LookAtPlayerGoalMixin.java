package org.gtreimagined.gtcore.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.gtreimagined.gtcore.events.GTCommonEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(LookAtPlayerGoal.class)
public class LookAtPlayerGoalMixin {

    @Shadow
    @Nullable
    protected Entity lookAt;
    @Shadow
    @Final
    protected Mob mob;
    @Shadow
    private int lookTime;
    @Unique
    private float gtcore$bearLookDistance;
    @Unique
    private TargetingConditions gtcore$bearLookAtContext;

    @Inject(method = "<init>(Lnet/minecraft/world/entity/Mob;Ljava/lang/Class;FFZ)V", at = @At("TAIL"))
    private void gtcore$injectInit(Mob mob, Class lookAtType, float lookDistance, float probability, boolean onlyHorizontal, CallbackInfo ci){
        if (mob.getClass() == Creeper.class){
            gtcore$bearLookDistance = lookDistance * 8;
            gtcore$bearLookAtContext = TargetingConditions.forNonCombat().range(gtcore$bearLookDistance).selector((livingEntity) -> EntitySelector.notRiding(mob).test(livingEntity));
        }
    }

    @Redirect(method = "canUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getNearestPlayer(Lnet/minecraft/world/entity/ai/targeting/TargetingConditions;Lnet/minecraft/world/entity/LivingEntity;DDD)Lnet/minecraft/world/entity/player/Player;"))
    private Player gtcore$redirectGetPlayer(Level instance, TargetingConditions targetingConditions, LivingEntity mob, double x, double y, double z){
        if (mob.getClass() == Creeper.class){
            Player player = this.mob.level.getPlayerByUUID(GTCommonEvents.TRINS_UUID);
            if (player != null && !player.isCreative()){
                if (gtcore$bearLookAtContext.test(mob, player)){
                    return player;
                }
            }
        }
        return instance.getNearestPlayer(targetingConditions, mob, x, y, z);
    }

    @Inject(method = "canContinueToUse", at = @At("HEAD"), cancellable = true)
    private void gtcore$injectCanContinueToUse(CallbackInfoReturnable<Boolean> cir){
        if (this.lookAt != null && this.mob.getClass() == Creeper.class){
            if (this.lookAt.getUUID().equals(GTCommonEvents.TRINS_UUID)){
                boolean look;
                if (!this.lookAt.isAlive()) {
                    look = false;
                } else if (this.mob.distanceToSqr(this.lookAt) > (double)(this.gtcore$bearLookDistance * this.gtcore$bearLookDistance)) {
                    look = false;
                } else {
                    look = this.lookTime > 0;
                }
                cir.setReturnValue(look);
            }
        }
    }
}
