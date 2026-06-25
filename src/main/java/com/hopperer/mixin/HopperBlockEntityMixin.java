package com.hopperer.mixin;

import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
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

    @Inject(method = "readNbt", at = @At("TAIL"))
    private void onReadNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries, CallbackInfo ci) {
        if (nbt.contains("super_hopper")) {
            this.isSuperHopper = nbt.getBoolean("super_hopper");
        }
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void onWriteNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries, CallbackInfo ci) {
        if (this.isSuperHopper) {
            nbt.putBoolean("super_hopper", true);
        }
    }

    @Inject(method = "needsCooldown", at = @At("HEAD"), cancellable = true)
    private void bypassCooldown(CallbackInfoReturnable<Boolean> cir) {
        if (this.isSuperHopper) {
            cir.setReturnValue(false);
        }
    }
}