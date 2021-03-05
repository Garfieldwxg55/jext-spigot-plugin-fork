package me.tajam.jext.command;

import me.tajam.jext.DiscContainer;
import me.tajam.jext.DiscPersistentDataHelper;
import me.tajam.jext.Log;
import me.tajam.jext.config.ConfigDiscManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ExecutorDiscReload extends ExecutorAdapter {

    public ExecutorDiscReload() {
        super("discreload");
    }

    @Override
    boolean executePlayer(Player sender, String[] args) {
        return mergedExecute(sender, args);
    }

    @Override
    boolean executeCommand(CommandSender sender, String[] args) {
        return mergedExecute(sender, args);
    }


    private boolean mergedExecute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack disc = player.getInventory().getItemInMainHand();

            if (isCustomDisc(disc)) {
                ItemMeta discMeta = disc.getItemMeta();
                String namespace = ConfigDiscManager.getInstance().getNamespaceFromModelData(discMeta.getCustomModelData());
                DiscContainer discContainer = ConfigDiscManager.getInstance().getDisc(namespace);

                player.getInventory().setItemInMainHand(discContainer.getDiscItem());
                new Log().info().t("Updated disc").send(player);
            } else {
                new Log().eror().t("That is not a valid disc").send(player);
            }
        } else {
            new Log().eror().t("This command is for players only").send(sender);
        }
        return true;
    }

    private boolean isCustomDisc(final ItemStack disc) {
        if (disc == null || !disc.hasItemMeta()) {
            return false;
        }
        final ItemMeta meta = disc.getItemMeta();
        final DiscPersistentDataHelper helper = new DiscPersistentDataHelper(meta);
        try {
            if (!helper.checkIdentifier()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
