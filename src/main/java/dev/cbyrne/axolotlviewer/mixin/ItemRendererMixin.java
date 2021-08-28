package dev.cbyrne.axolotlviewer.mixin;

import dev.cbyrne.axolotlviewer.AxolotlViewer;
import dev.cbyrne.axolotlviewer.mixin.accessor.EntityBucketItemAccessor;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Redirect(method = "getHeldItemModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemModels;getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;"))
    private BakedModel redirectGetModel(ItemModels itemModels, ItemStack stack) {
        if (!(stack.getItem() instanceof EntityBucketItem bucketItem) || ((EntityBucketItemAccessor) bucketItem).getEntityType() != EntityType.AXOLOTL)
            return itemModels.getModel(stack);

        var nbtCompound = stack.getNbt();
        if (nbtCompound == null || !nbtCompound.contains("Variant")) return itemModels.getModel(stack);

        var variant = nbtCompound.getInt("Variant");
        var item = switch (AxolotlEntity.Variant.VARIANTS[variant]) {
            case WILD -> AxolotlViewer.FAKE_WILD_BUCKET;
            case GOLD -> AxolotlViewer.FAKE_GOLD_BUCKET;
            case CYAN -> AxolotlViewer.FAKE_CYAN_BUCKET;
            case BLUE -> AxolotlViewer.FAKE_BLUE_BUCKET;
            default -> stack.getItem();
        };

        return itemModels.getModel(item);
    }
}
