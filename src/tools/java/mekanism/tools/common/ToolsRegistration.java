package mekanism.tools.common;

import mekanism.tools.common.config.MekanismToolsConfig;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = MekanismTools.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ToolsRegistration {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //Setup our config as things like materials are valid by now. Use highest priority
        //TODO: 1.15, overwrite a lot more in the different tool classes and stuff, and let this be properly synced
        // from the server instead of being force loaded early
        MekanismToolsConfig.loadFromFiles();
    }

    @SubscribeEvent
    public static void onLoad(ModConfig.Loading configEvent) {
        //TODO: Would be nice to load the tools config from here except that we need the values for registering the items
    }

    @SubscribeEvent
    public static void onFileChange(ModConfig.ConfigReloading configEvent) {
        //TODO: Handle reloading
    }
}