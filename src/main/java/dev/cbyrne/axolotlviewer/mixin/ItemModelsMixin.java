package dev.cbyrne.axolotlviewer.mixin;

import dev.cbyrne.axolotlviewer.AxolotlViewer;
import dev.cbyrne.axolotlviewer.mixin.accessor.EntityBucketItemAccessor;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemModels.class)
public abstract class ItemModelsMixin {
    @Shadow
    @Nullable
    public abstract BakedModel getModel(Item item);

    @Shadow
    public abstract BakedModelManager getModelManager();

    @Inject(
        method = "getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;",
        at = @At(value = "HEAD"),
        cancellable = true
    )
    private void changeItemModelOnAxolotlBucket(ItemStack stack, CallbackInfoReturnable<BakedModel> cir) {
        if (!(stack.getItem() instanceof EntityBucketItem bucketItem) || ((EntityBucketItemAccessor) bucketItem).getEntityType() != EntityType.AXOLOTL)
            return;

        var nbtCompound = stack.getNbt();
        if (nbtCompound == null || !nbtCompound.contains("Variant")) {
            return;
        }

        var variant = nbtCompound.getInt("Variant");
        var manager = getModelManager();
        var model = switch (AxolotlEntity.Variant.VARIANTS[variant]) {
            case WILD -> manager.getModel(AxolotlViewer.getModelIdentifier("wild"));
            case GOLD -> manager.getModel(AxolotlViewer.getModelIdentifier("gold"));
            case CYAN -> manager.getModel(AxolotlViewer.getModelIdentifier("cyan"));
            case BLUE -> manager.getModel(AxolotlViewer.getModelIdentifier("blue"));
            default -> getModel(stack.getItem());
        };

        cir.setReturnValue(model);
    }
}
