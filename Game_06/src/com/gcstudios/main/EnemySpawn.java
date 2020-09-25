package com.gcstudios.main;

import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Entity;
import com.gcstudios.world.World;

public class EnemySpawn {
	
	public int timeBetw = 60*6;
	public int curTime  = 0;
	
	public void tick() {
		curTime++;
		if(curTime == timeBetw) {
			curTime = 0;
			int xInitial = Entity.rand.nextInt((World.WIDTH )* 16 - 16 -16 ) + 16;
			Enemy enemy = new Enemy(xInitial,250,16,16,1,Enemy.ENEMY1_RIGHT, Enemy.ENEMY1_LEFT);
			Game.entities.add(enemy);
		}
	}
}
