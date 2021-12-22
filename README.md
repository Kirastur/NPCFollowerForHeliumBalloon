# NPCFollowerForHeliumBalloon

This is a Demo-Plugin for HeliumBalloon to demonstrate how you can create and use custom Observers.

You can create a Balloon which follows a given Entity. In this example the Entity is identified by it's name, e.g. a citizens NPC.

This is a very rudimentary plugin. Is does not care about:
* BalloonRefreshAllEvent
* NPC Despawn
* Persistence
* ChunkHandling

Install the HeliumBalloon plugin from https://www.spigotmc.org/resources/heliumballoon.95902

To watch the demo, do the following:

1. Install the citizens plugin
       https://www.spigotmc.org/resources/citizens.13811/
       http://ci.citizensnpcs.co/job/Citizens2

2. Create a new NPC "demo" (If you can't see the NPC, move a bit away from your position and turn around):
       "/npc create demo"

3. Set three waypoints (so they build a triangle)
     * Enter "/npc path" to enter the waypoint editor
     * Use "Left moue click" to place the three waypoints
     * Enter "/npc path" to exit the waypoint editor
   ==> You see the npc hiking around

4. Optional: Reduce the speed of the NPC:
       "/npc speed 0.8"

5. Attach the pet to the NPC:
      "/npcfolllowerset demo"
   ==> Now the lantern is following the player

6. Remove the lantern pet:
       "/npcfollowerremove"
