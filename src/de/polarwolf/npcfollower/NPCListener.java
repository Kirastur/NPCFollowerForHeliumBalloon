package de.polarwolf.npcfollower;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import de.polarwolf.heliumballoon.events.BalloonRebuildConfigEvent;
import de.polarwolf.heliumballoon.exception.BalloonException;

public class NPCListener implements Listener {

	public static final String HINT_PENDULUM = "pendulum";

	public static final double PENDULUM_LENGTH = 5.0; // in Blocks
	public static final double PENDULUM_AMPLITUDE = 60.0; // in Degree
	public static final double PENDULUM_DURATION = 3.0; // in Seconds

	protected final Main main;

	public NPCListener(Main main) {
		this.main = main;
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	// This event reloads our custom config from our own config.yml.
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBalloonRebuildConfigEvent(BalloonRebuildConfigEvent event) {
		if (event.isCancelled()) {
			return;
		}
		try {
			main.npcReload(event);
		} catch (BalloonException be) {
			be.printStackTrace();
			event.cancelWithReason(be);
		} catch (Exception e) {
			e.printStackTrace();
			event.cancelWithReason(new BalloonException(null, BalloonException.JAVA_EXCEPTION, null, e));
		}
	}

}
