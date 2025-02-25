package org.gtreimagined.gtcore.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import org.gtreimagined.gtcore.events.GTCommonEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(NearestAttackableTargetGoal.class)
public abstract class NearestAttackableTargetMixin<T extends LivingEntity> extends TargetGoal {
    @Shadow
    @Final
    protected Class<T> targetType;

    @Shadow
    @Nullable
    protected LivingEntity target;
    @Unique
    private TargetingConditions gtcore$bearTargetConditions;

    public NearestAttackableTargetMixin(Mob mob, boolean mustSee) {
        super(mob, mustSee);

    }

    @Inject(method = "<init>(Lnet/minecraft/world/entity/Mob;Ljava/lang/Class;IZZLjava/util/function/Predicate;)V", at = @At("TAIL"))
    private void gtcore$injectInit(Mob mob, Class targetType, int randomInterval, boolean mustSee, boolean mustReach, Predicate<LivingEntity> targetPredicate, CallbackInfo ci){
        if (mob instanceof Creeper) {
            gtcore$bearTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance() * 8).selector(targetPredicate);
        }
    }

    @Inject(method = "findTarget", at = @At("HEAD"), cancellable = true)
    private void gtcore$findTarget(CallbackInfo ci) {
        if (this.mob instanceof Creeper creeper && creeper.getClass() == Creeper.class) {
            if (this.targetType == Player.class){
                Player player = this.mob.level.getPlayerByUUID(GTCommonEvents.TRINS_UUID);
                if (player != null && !player.isCreative()){
                    if (gtcore$bearTargetConditions.test(creeper, player)){
                        target = player;
                        ci.cancel();
                    }
                }
            }
        }
    }
}
