package net.dbit.skinurlunrestrict.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@org.spongepowered.asm.mixin.Mixin(value = AbstractClientPlayerEntity.class)
public abstract class MixinPlayerSkin extends PlayerEntity {

    public MixinPlayerSkin(ClientWorld world, GameProfile profile) {
        super(world, world.getSpawnPos(), world.getSpawnAngle(), profile);
    }

    @Inject(method="getSkinTexture",at=@At("HEAD"), cancellable = true)
    private void textureInject(CallbackInfoReturnable<Identifier> info){
        ItemStack headItem = this.getEquippedStack(EquipmentSlot.HEAD);
        if(headItem.getItem().equals(Items.PLAYER_HEAD) && headItem.getNbt() != null){
            if(headItem.getNbt().asString().contains("fullbody")){
                NbtCompound nbtCompound = headItem.getNbt();
                GameProfile gameProfile = null;
                if (nbtCompound.contains("SkullOwner", 10)) {
                    gameProfile = NbtHelper.toGameProfile(nbtCompound.getCompound("SkullOwner"));
                } else if (nbtCompound.contains("SkullOwner", 8) && !StringUtils.isBlank(nbtCompound.getString("SkullOwner"))) {
                    gameProfile = new GameProfile((UUID)null, nbtCompound.getString("SkullOwner"));
                }
                if(gameProfile != null) {
                    MinecraftClient minecraftClient = MinecraftClient.getInstance();
                    info.setReturnValue(minecraftClient.getSkinProvider().loadSkin(gameProfile));
                }
            }
        }
    }
}
