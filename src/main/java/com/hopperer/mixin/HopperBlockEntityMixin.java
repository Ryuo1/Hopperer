package com.hopperer.mixin;

import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {

    @Unique
    private boolean isSuperHopper = false;

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void onLoadAdditional(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        if (tag.contains("super_hopper")) {
            // Unpacks the Optional safely; defaults to false if empty
            this.isSuperHopper = tag.getBoolean("super_hopper").orElse(false);
        }
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void onSaveAdditional(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        if (this.isSuperHopper) {
            tag.putBoolean("super_hopper", true);
        }
    }

    @Inject(method = "skipCooldown", at = @At("HEAD"), cancellable = true)
    private void bypassCooldown(CallbackInfoReturnable<Boolean> cir) {
        if (this.isSuperHopper) {
            cir.setReturnValue(false);
        }
    }
}
