package net.dbit.skinurlunrestrict.mixin;

import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.PlayerSkinTexture;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@org.spongepowered.asm.mixin.Mixin(value = PlayerSkinTexture.class)
public class MixinAllowAllSize {
    private static void stripColor(NativeImage image, int x1, int y1, int x2, int y2) {
        int i;
        int j;
        for(i = x1; i < x2; ++i) {
            for(j = y1; j < y2; ++j) {
                int k = image.getColor(i, j);
                if ((k >> 24 & 255) < 128) {
                    return;
                }
            }
        }

        for(i = x1; i < x2; ++i) {
            for(j = y1; j < y2; ++j) {
                image.setColor(i, j, image.getColor(i, j) & 16777215);
            }
        }

    }

    private static void stripAlpha(NativeImage image, int x1, int y1, int x2, int y2) {
        for(int i = x1; i < x2; ++i) {
            for(int j = y1; j < y2; ++j) {
                image.setColor(i, j, image.getColor(i, j) | -16777216);
            }
        }

    }
    @Inject(method="remapTexture",at=@At("HEAD"), cancellable = true)
    private void domainInject(NativeImage image, CallbackInfoReturnable info){
        int i = image.getHeight();
        int j = image.getWidth();
        boolean bl = i == 32;
        if (bl) {
            NativeImage nativeImage = new NativeImage(64, 64, true);
            nativeImage.copyFrom(image);
            image.close();
            image = nativeImage;
            nativeImage.fillRect(0, 32, 64, 32, 0);
            nativeImage.copyRect(4, 16, 16, 32, 4, 4, true, false);
            nativeImage.copyRect(8, 16, 16, 32, 4, 4, true, false);
            nativeImage.copyRect(0, 20, 24, 32, 4, 12, true, false);
            nativeImage.copyRect(4, 20, 16, 32, 4, 12, true, false);
            nativeImage.copyRect(8, 20, 8, 32, 4, 12, true, false);
            nativeImage.copyRect(12, 20, 16, 32, 4, 12, true, false);
            nativeImage.copyRect(44, 16, -8, 32, 4, 4, true, false);
            nativeImage.copyRect(48, 16, -8, 32, 4, 4, true, false);
            nativeImage.copyRect(40, 20, 0, 32, 4, 12, true, false);
            nativeImage.copyRect(44, 20, -8, 32, 4, 12, true, false);
            nativeImage.copyRect(48, 20, -16, 32, 4, 12, true, false);
            nativeImage.copyRect(52, 20, -8, 32, 4, 12, true, false);
        }

        //stripAlpha(image, 0, 0, 32, 16);
        if (bl) {
            stripColor(image, 32, 0, 64, 32);
        }

        //stripAlpha(image, 0, 16, 64, 32);
        //stripAlpha(image, 16, 48, 48, 64);
        info.setReturnValue(image);
    }
}

