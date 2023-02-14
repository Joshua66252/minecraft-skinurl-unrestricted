package net.dbit.skinurlunrestrict.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import net.dbit.skinurlunrestrict.Main;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@org.spongepowered.asm.mixin.Mixin(value = YggdrasilMinecraftSessionService.class,remap = false)
public class MixinAllowAllDomains {
    @Inject(method="isAllowedTextureDomain",at=@At("HEAD"), cancellable = true)
    private static void domainInject(final String url, CallbackInfoReturnable info){
        info.setReturnValue(true);
    }
}
