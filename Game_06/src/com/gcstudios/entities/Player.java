package com.gcstudios.entities;


import java.awt.Graphics;

import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;


public class Player extends Entity{

	
	public boolean right,left;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;	
	private BufferedImage[] lastDir;
	private BufferedImage jumpPlayer;
	private BufferedImage fallPlayer;
	
	public BufferedImage attackRight;
	public BufferedImage attackLeft;
	
	public int dir = 1;
	private double gravity = 2;
	
	public double life = 100;
	public int maxLife = 100;
	
	public boolean Running = false;
	public boolean jump = false;
	private boolean jumpAnim = false;
	private boolean fallAnim = false;
	
	public boolean isJumping = false;
	public int jumpHeight = 48;
	public int jumpFrames = 0;
	
	public boolean attack = false;
	public boolean isAttacking = false;
	public int attackFrames = 0;
	public int maxFramesAttack = 20;
	
	private int framesAnimation = 0;

	
	private int maxSprite = 5;
	private int curSprite = 0;
	
	
	private boolean moved = false;
	
	private int frames = 0,maxFrames = 10,index = 0, maxIndex = 3;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		attackRight = Game.spritesheet.getSprite(80, 96, 16,16);
		attackLeft = Game.spritesheet.getSprite(80, 112, 16,16);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		
		jumpPlayer = Game.spritesheet.getSprite(16, 80, 16, 16);
		fallPlayer = Game.spritesheet.getSprite(0, 80, 16, 16);
		lastDir = rightPlayer;
		
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(0 + (i*16), 48, 16, 16);
			}
			for(int i = 0; i < 4; i++) {
				leftPlayer[i] = Game.spritesheet.getSprite(0 + (i*16), 64, 16, 16);
				}
	}
	
	public void Run() {
		if(Running) {
			speed = 2;
		}else {
			speed = 1;
		}
	}
	
	public void Gravity() {
		if(World.isFree((int)x,(int)(y+gravity)) && isJumping == false) {
			y+=gravity;
			fallAnim = true;
		}
	}
	
	public void Movement() {
		moved = false;
		if (right && World.isFree ( (int) (x + speed), this.getY())) {
		lastDir = rightPlayer;
			moved = true;
			x+=speed;
			dir = 1;
		}
		else if (left && World.isFree( (int) (x - speed), this.getY())) {
			lastDir = leftPlayer;
			moved = true;
			x-=speed;
			
		}
	}
	public void Jump() {
		
		if(jump) {
			
			if(!World.isFree(this.getX(),this.getY()+1)) {
				isJumping = true;
				jumpAnim = true;
				fallAnim = false;
			}else {
				jump = false;
				fallAnim = false;
			
			}
		}
		
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY()-2)) {
				y-=2;
				jumpFrames+=2;
				
				if(jumpFrames == jumpHeight) {
				
					jumpAnim = false;
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			}else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
				fallAnim = false;
			}
		}
	}
	
	public void Attack() {
		if(attack) {
			if(isAttacking == false) {
			attack = false;
			isAttacking = true;
			}
		}
		if(isAttacking) {
			attackFrames++;
			
			if(attackFrames == maxFramesAttack) {
				attackFrames = 0;
				isAttacking = false;
			}
		}
	}
	public void CameraClamp() {
		Camera.x = Camera.clamp((int)x - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)y - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);
	}
	public void Animation() {
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index =0;
				}
			}
		}
	}
	
	public void CollisionEnemy() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
				if(Entity.rand.nextInt(100) < 30) {
					if(Entity.isColidding(this, e)) {
						life -= 0.3;
						if(isAttacking) {
							((Enemy) e).vida-= 10;
					}
					
					}
					
				}
				
			}
		}
	}

	public void tick(){
		
		depth = 2;
		
		Run();
		Gravity();
		Movement();
		Jump();
		Attack();
		CameraClamp();
		Animation();
		CollisionEnemy();
		
		
	}
	
	public void render(Graphics g){
		super.render(g);
		framesAnimation++;
		if(framesAnimation == maxFrames) {
			if(curSprite < maxSprite) {
				curSprite++;
			}else {
				curSprite --;
			}
			
			framesAnimation = 0;
			if(curSprite == maxSprite) {
				curSprite = 0;
			}
		}
		
		if(!moved) {
			if(!isAttacking) {
		
			}
			if(dir == 1) {

				if(isAttacking) {
					g.drawImage(attackRight, this.getX()- Camera.x, this.getY()- Camera.y, null);
				}else {
			
				sprite = Entity.PLAYER_SPRITE_RIGHT[0];
				jumpPlayer = Game.spritesheet.getSprite(16, 80, 16, 16);
				fallPlayer = Game.spritesheet.getSprite(0, 80, 16, 16);
				}
				
			}if(dir == -1) {
				
				
				if(isAttacking) {
					g.drawImage(attackLeft, this.getX()- Camera.x , this.getY()- Camera.y, null);
				}else {
					sprite = Entity.PLAYER_SPRITE_LEFT[0];
					jumpPlayer = Game.spritesheet.getSprite(0, 96, 16, 16);
					fallPlayer = Game.spritesheet.getSprite(16, 96, 16, 16);
				}
			
			
			}
			 if(jumpAnim) {
				sprite = jumpPlayer;
			}else if(fallAnim) {
				sprite = fallPlayer;
				fallAnim = false;
			}
		}else {
			
			if (right) {
				
				if(isAttacking) {
					g.drawImage(attackRight, this.getX()- Camera.x , this.getY()- Camera.y,null);
				}else {
					sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
				}
		}else if (left) {
		
			if(isAttacking) {
				g.drawImage(attackLeft, this.getX()- Camera.x  , this.getY()- Camera.y,null);
			}else {
				sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
			}
		}
			
		}
	
		}	
}
	



