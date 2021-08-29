package dev.cbyrne.axolotlviewer;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class AxolotlViewer implements ClientModInitializer {
    public static String[] SUPPORTED_MODELS = {"cyan", "wild", "blue", "gold"};

    public static ModelIdentifier getModelIdentifier(String type) {
        return new ModelIdentifier(new Identifier("axolotlviewer", "axolotl_bucket_" + type), "inventory");
    }

    @Override
    public void onInitializeClient() {
    }
}
