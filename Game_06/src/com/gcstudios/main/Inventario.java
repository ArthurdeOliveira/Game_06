package com.gcstudios.main;

import java.awt.Color;
import java.awt.Graphics;

import com.gcstudios.entities.Player;
import com.gcstudios.world.Camera;
import com.gcstudios.world.FloorTile;
import com.gcstudios.world.Tile;
import com.gcstudios.world.WallTile;
import com.gcstudios.world.World;

public class Inventario {
	
	public boolean isPressed = false;
	public boolean rightMouseButton = false;
	public int mx,my;
	
	public int selected = 0;
	
	
	public String curText = "";
	public boolean txt = false;
	
	public int boxSize = 48;
	

	public String[] items = {"pa","picareta","sword","terra","grama","pedra","areia","madeira","neve",""};
	
	public int initialPosition = ((Game.WIDTH * Game.SCALE) / 2 ) - ((items.length* boxSize) / 2);
	

		
	public void tick() {
		if(isPressed) {
			isPressed = false;
			if(mx >= initialPosition && mx < initialPosition+(boxSize*items.length)) {
				if(my >= Game.HEIGHT*Game.SCALE - (boxSize +1) && my < (Game.HEIGHT*Game.SCALE - (boxSize +1) + boxSize)) {
					//System.out.println("Area de seleção");
					selected = (int) (mx-initialPosition)/ boxSize;
				}
			}
			 
		}
		if(rightMouseButton) {
			rightMouseButton = false;
			mx = mx/3 + Camera.x;
			my = my/3 + Camera.y;
			
			int tilex = mx/World.TILE_SIZE;
			int tiley = my/World.TILE_SIZE;
			if(World.tiles[tilex + tiley* World.WIDTH].solid == false) {
				//System.out.println("Construir");
				if(items[selected] == "grama") {
					World.tiles[tilex + tiley* World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_GRAMA);
				}else if(items[selected] == "terra") {
					World.tiles[tilex + tiley* World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_TERRA);
					
				}else if(items[selected] == "areia") {
					World.tiles[tilex + tiley* World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_AREIA);
					
				}else if(items[selected] == "pedra") {
					World.tiles[tilex + tiley* World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_PEDRA);
					
				}else if(items[selected] == "pa") {
					
					World.tiles[tilex + tiley* World.WIDTH] = new FloorTile(tilex*16,tiley*16,Tile.TILE_AR);
				}else if(items[selected] == "madeira") {
					World.tiles[tilex + tiley* World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_MADEIRA);
				}else if(items[selected] == "neve") {
					World.tiles[tilex + tiley* World.WIDTH] = new WallTile(tilex*16,tiley*16,Tile.TILE_NEVE);
				}else if(items[selected] == "picareta") {
					if(World.tiles[(tilex)+tiley*World.WIDTH]  instanceof WallTile && World.tiles[(tilex)+tiley*World.WIDTH].solid == false) {
					World.tiles[tilex+ tiley* World.WIDTH] = new FloorTile(tilex*16,tiley*16,Tile.TILE_AR);
					}
				}else if(items[selected] == "sword") {
					Game.player.attack = true;
				}
				if(World.isFree(Game.player.getX(), Game.player.getY()) == false) {
					World.tiles[tilex + tiley* World.WIDTH] = new FloorTile(tilex*16,tiley*16,Tile.TILE_AR);
				}
			}
		}
	
	
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < items.length; i++) {
			
			g.setColor(Color.GRAY);
			g.fillRect(initialPosition + (i*boxSize)+ 1, (Game.HEIGHT*Game.SCALE) - boxSize +1, boxSize, boxSize);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(initialPosition + (i*boxSize)+1, (Game.HEIGHT*Game.SCALE) - boxSize+1, boxSize, boxSize);
	
			if(items[i] == "grama") {
				g.drawImage(Tile.TILE_GRAMA,initialPosition + (i*boxSize) + 8, (Game.HEIGHT*Game.SCALE)- boxSize +8, 32,32, null);
			}else if(items[i] == "terra") {
				
			
				g.drawImage(Tile.TILE_TERRA,initialPosition + (i*boxSize) + 8, (Game.HEIGHT*Game.SCALE)- boxSize +8, 32,32, null);
			}else if(items[i] == "neve") {
				
				g.drawImage(Tile.TILE_NEVE,initialPosition + (i*boxSize) + 8, (Game.HEIGHT*Game.SCALE)- boxSize +8, 32,32, null);
			}else if(items[i] == "areia") {
				
				g.drawImage(Tile.TILE_AREIA,initialPosition + (i*boxSize) + 8, (Game.HEIGHT*Game.SCALE)- boxSize +8, 32,32, null);
			}else if(items[i] == "madeira") {
				
					g.drawImage(Tile.TILE_MADEIRA,initialPosition + (i*boxSize) + 8, (Game.HEIGHT*Game.SCALE)- boxSize +8, 32,32, null);
			}else if(items[i] == "ar"){
				
				g.drawImage(Tile.TILE_AR,initialPosition + (i*boxSize) + 8, (Game.HEIGHT*Game.SCALE)- boxSize +8, 32,32, null);
			}else if(items[i] == "pedra") {
			
				g.drawImage(Tile.TILE_PEDRA,initialPosition + (i*boxSize) + 8, (Game.HEIGHT*Game.SCALE)- boxSize +8, 32,32, null);	
			}else if(items[i] == "pa") {
				
				g.drawImage(Tile.TILE_PA,initialPosition + (i*boxSize) + 8, (Game.HEIGHT*Game.SCALE)- boxSize +8, 32,32, null);	
			}else if(items[i] == "picareta") {
				
				g.drawImage(Tile.TILE_PICARETA,initialPosition + (i*boxSize) + 8, (Game.HEIGHT*Game.SCALE)- boxSize +8, 32,32, null);	
			}else if(items[i] == "sword") {
				
				g.drawImage(Tile.TILE_SWORD,initialPosition + (i*boxSize) + 8, (Game.HEIGHT*Game.SCALE)- boxSize +8, 32,32, null);	
			}
			
			if(selected == i) {
				g.setColor(Color.BLACK);
				g.drawRect(initialPosition + (i*boxSize), (Game.HEIGHT*Game.SCALE) - boxSize+2 , boxSize, boxSize -3);
			}
		}
	}
}
