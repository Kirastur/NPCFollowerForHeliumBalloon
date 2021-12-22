package de.polarwolf.npcfollower;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import de.polarwolf.heliumballoon.api.HeliumBalloonAPI;
import de.polarwolf.heliumballoon.api.HeliumBalloonProvider;
import de.polarwolf.heliumballoon.config.ConfigPart;
import de.polarwolf.heliumballoon.config.ConfigSection;
import de.polarwolf.heliumballoon.config.ConfigTemplate;
import de.polarwolf.heliumballoon.events.BalloonRebuildConfigEvent;
import de.polarwolf.heliumballoon.exception.BalloonException;
import de.polarwolf.heliumballoon.oscillators.DefaultOscillator;
import de.polarwolf.heliumballoon.oscillators.Oscillator;

public final class Main extends JavaPlugin {

	protected HeliumBalloonAPI api = null;
	protected ConfigSection balloonConfigSection = null;
	protected NPCObserver npcObserver = null;

	void npcReload(BalloonRebuildConfigEvent event) throws BalloonException {
		reloadConfig();
		balloonConfigSection = event.buildConfigSectionFromConfigFile(this);
		event.addSection(balloonConfigSection);
	}

	public boolean npcSet(CommandSender sender, Entity npcEntity) {
		// This plugin is limited to one NPC at the same time.
		// You can enhance this by implementing a list of NPCs.
		if (this.npcObserver != null) {
			sender.sendMessage("You have already have a NPC assigned. Remove it first with /npcfollowerremove");
			return false;
		}

		// Get the HeliumAPI.
		// Don't do this at startup, because another plugin can override it.
		api = HeliumBalloonProvider.getAPI();
		if (api == null) {
			sender.sendMessage("The HeliumBalloon API is not avail");
			return false;
		}

		// Keep things simple.
		// We use a predefined template from our config.
		// We assume that that this template contains only one part (!!!)
		// (in this example the "elements" part).
		ConfigTemplate template = balloonConfigSection.findTemplate("npclantern");
		ConfigPart part = template.getParts().get(0);
		Oscillator oscillator = new DefaultOscillator(template.getRule());
		oscillator.setDeflectionState(true);
		try {
			NPCObserver newObserver = new NPCObserver(this, template, part, oscillator, npcEntity);
			api.addObserver(newObserver);
			npcObserver = newObserver;
		} catch (BalloonException e) {
			sender.sendMessage(e.getMessage());
			return false;
		}
		sender.sendMessage("Set OK");
		return true;
	}

	public boolean npcRemove(CommandSender sender) {
		if (npcObserver == null) {
			sender.sendMessage("You don't have a npc association. Create it first with /npcfollowerset");
			return false;
		}

		// Yes, just cancel the observer.
		// The pet will removed automatically.
		npcObserver.cancel();
		npcObserver = null;

		sender.sendMessage("Remove OK");
		return true;
	}

	@Override
	public void onEnable() {
		saveDefaultConfig();
		new NPCListener(this);
		new SetCommand(this);
		new RemoveCommand(this);
		this.getLogger().info("You can now assign a pet to NPCs.");
		this.getLogger().info("Use /npcfollowerset and /npcfolloerremove");
	}

}
