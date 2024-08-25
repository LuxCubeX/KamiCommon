package com.kamikazejam.kamicommon.nms.item;

import com.kamikazejam.kamicommon.nms.abstraction.item.NmsItemMethods;
import net.minecraft.server.v1_11_R1.LocaleI18n;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class NmsItemMethods_1_11_R1 implements NmsItemMethods {
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull String getI18NItemName(@NotNull ItemStack itemStack) {
        net.minecraft.server.v1_11_R1.ItemStack nmsCopy = CraftItemStack.asNMSCopy(itemStack);
        return LocaleI18n.get(nmsCopy.getItem().a(nmsCopy) + ".name");
    }
}
