package com.gcstudios.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gcstudios.entities.Player;
import com.gcstudios.main.Game;
import com.gcstudios.world.World;

public class UI {
	
	public static int seconds = 0;
	public static int minutes = 0;
	public static int frames = 0;

	public void tick() {
		frames++;
		if(frames == 60) {
			frames = 0;
			seconds ++;
			if(seconds == 60) {
				seconds = 0;
				minutes ++;
				if(minutes % 1 == 0) {
					World.CICLO++;
					if(World.CICLO  > World.noite ) {
						World.CICLO = World.dia;
					}
				}
			}
		}
		
	}
		
	public void render(Graphics g) {
				g.setColor(Color.RED);
				g.fillRect(48, 20,150, 25);
			
				g.setColor(Color.GREEN);
				g.fillRect(48,  20, (int) ((Game.player.life )*1.5), 25);
			g.setColor(Color.WHITE);
			g.setFont(new Font ("arial", Font.BOLD , 24) );
			g.drawString((int)Game.player.life +"/" + Game.player.maxLife, 80, 42);
		
			String formatTime = "";
			//MINUTOS:
			if(minutes < 10) {
				formatTime += "0" + minutes+":";
			}else {
				formatTime += minutes+":";
			}
			//SEGUNDOS:
			if(seconds < 10) {
				formatTime += "0" + seconds;
			}else {
				formatTime += seconds;
			}
			g.setFont(new Font ("arial", Font.ROMAN_BASELINE , 18) );
			g.drawString("Tempo: "+formatTime,Game.WIDTH*Game.SCALE - 158,40);
	}
	
}
