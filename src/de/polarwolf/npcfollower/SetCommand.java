package de.polarwolf.npcfollower;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SetCommand implements CommandExecutor {

	public static final String COMMAND_SET = "npcfollowerset";

	protected final Main main;

	public SetCommand(Main main) {
		this.main = main;
		main.getCommand(COMMAND_SET).setExecutor(this);
	}

	protected Entity findEntity(String entityName, World world) {
		for (Entity myEntity : world.getEntities()) {
			String customName = myEntity.getCustomName();
			if ((customName != null) && customName.equals(entityName)) {
				return myEntity;
			}
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length != 1) {
			sender.sendMessage("Usage: /npcfollowerset <NPC-Name>");
			return false;
		}
		
		World world;
		if (sender instanceof Player player) {
			world = player.getWorld();
		} else {
			world = main.getServer().getWorld("world");
		}

		String followerName = args[0];
		Entity entity = findEntity(followerName, world);
		if (entity == null) {
			sender.sendMessage("Entity not found");
			return true;
		}

		main.npcSet(sender, entity);
		return true;
	}

}
