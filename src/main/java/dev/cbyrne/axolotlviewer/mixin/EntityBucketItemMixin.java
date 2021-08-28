package dev.cbyrne.axolotlviewer.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EntityBucketItem.class)
public class EntityBucketItemMixin {
    @Shadow
    @Final
    private EntityType<?> entityType;

    @Inject(method = "appendTooltip", at = @At("RETURN"))
    private void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (entityType == EntityType.AXOLOTL) {
            var nbtCompound = stack.getNbt();
            if (nbtCompound == null || !nbtCompound.contains("Variant")) return;

            var variant = AxolotlEntity.Variant.VARIANTS[nbtCompound.getInt("Variant")].getName();
            switch (variant) {
                case "lucy" -> variant = variant + " (Pink)";
                case "wild" -> variant = variant + " (Brown)";
            }

            tooltip.add(Text.of(StringUtils.capitalize(variant)).shallowCopy().formatted(Formatting.ITALIC, Formatting.GRAY));
        }
    }
}
