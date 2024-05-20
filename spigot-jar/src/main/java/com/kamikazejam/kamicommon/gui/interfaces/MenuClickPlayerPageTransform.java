package com.kamikazejam.kamicommon.gui.interfaces;

import com.kamikazejam.kamicommon.xseries.XSound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@SuppressWarnings("unused")
public class MenuClickPlayerPageTransform implements MenuClickInfo {

    private final MenuClickPlayerPage click;
    public MenuClickPlayerPageTransform(MenuClickPlayerPage click) {
        this.click = click;
    }

    @Override
    public void onItemClickMember(Player player, InventoryClickEvent event, int page) {
        if (click != null) {
            player.playSound(player.getLocation(), XSound.UI_BUTTON_CLICK.parseSound(), 1, 2);
            click.onPlayerPageClick(player, event.getClick(), page);
        }
    }
}
