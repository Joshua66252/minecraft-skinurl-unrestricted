package net.dbit.skinurlunrestrict.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HeadFeatureRenderer.class)
public abstract class MixinHeadFeature<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
    public MixinHeadFeature(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method="render",at=@At("HEAD"), cancellable = true)
    private void renderInject(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo info){
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
        if(itemStack.getNbt() != null) {
            if (itemStack.getNbt().asString().contains("fullbody")) {
                info.cancel();
            }
        }
    }
}
