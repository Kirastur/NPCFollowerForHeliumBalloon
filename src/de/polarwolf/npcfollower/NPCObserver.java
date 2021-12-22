package de.polarwolf.npcfollower;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import de.polarwolf.heliumballoon.observers.FollowingObserver;
import de.polarwolf.heliumballoon.config.ConfigBalloonSet;
import de.polarwolf.heliumballoon.config.ConfigPart;
import de.polarwolf.heliumballoon.exception.BalloonException;
import de.polarwolf.heliumballoon.oscillators.Oscillator;

public class NPCObserver extends FollowingObserver {

	protected final Main main;
	protected final Entity npc;

	public NPCObserver(Main main, ConfigBalloonSet configBalloonSet, ConfigPart part, Oscillator oscillator, Entity npc)
			throws BalloonException {
		super(npc.getWorld(), configBalloonSet, part, oscillator);
		this.main = main;
		this.npc = npc;
	}

	@Override
	protected Location getMasterLocation() {
		return npc.getLocation();
	}

}
