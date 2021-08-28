package dev.cbyrne.axolotlviewer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AxolotlViewer implements ModInitializer {
    public static final Item FAKE_BLUE_BUCKET = new Item(new FabricItemSettings());
    public static final Item FAKE_CYAN_BUCKET = new Item(new FabricItemSettings());
    public static final Item FAKE_GOLD_BUCKET = new Item(new FabricItemSettings());
    public static final Item FAKE_WILD_BUCKET = new Item(new FabricItemSettings());

    /**
     * I know, this is terrible. Resources are weird, I couldn't find a better way.
     * I tried custom resource pack things, but it would never load the model for some reason unknown to me.
     */
    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("axolotlviewer", "axolotl_bucket_blue"), FAKE_BLUE_BUCKET);
        Registry.register(Registry.ITEM, new Identifier("axolotlviewer", "axolotl_bucket_cyan"), FAKE_CYAN_BUCKET);
        Registry.register(Registry.ITEM, new Identifier("axolotlviewer", "axolotl_bucket_gold"), FAKE_GOLD_BUCKET);
        Registry.register(Registry.ITEM, new Identifier("axolotlviewer", "axolotl_bucket_wild"), FAKE_WILD_BUCKET);
    }
}
