import java.util.ArrayList;

public class Hero {	
	private Graphic graphic;
	private float   speed;
	private int     controlType;

	public Hero(float x, float y, int controlType) {
		speed = 0.12f;
		this.controlType=controlType;
		graphic = new Graphic();
		graphic.setType("HERO");
		graphic.setX(x);
		graphic.setY(y);
	}
	
	public Graphic getGraphic() {
		return graphic;
	}
	
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs)  {
		for(int i=0;i<fireballs.size();i++) {
			if(graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
				return true;
			}
		}
		return false;
	}
	
	public void update(int time, Water[] water) {
		//Draws the hero graphic
		graphic.draw();
		
		//Code for control schemes
		if (controlType == 1) {
			if (Engine.isKeyHeld("D")) {
				graphic.setDirection(0f);
				graphic.setX(graphic.getX()+speed*time);
			}
			if (Engine.isKeyHeld("A")) {
				graphic.setDirection(3.14f);
				graphic.setX(graphic.getX()-speed*time);
			}
			if (Engine.isKeyHeld("W")) {
				graphic.setDirection(4.71f);
				graphic.setY(graphic.getY()-speed*time);
			}
			if (Engine.isKeyHeld("S")) {
				graphic.setDirection(1.57f);
				graphic.setY(graphic.getY()+speed*time);
			}
		}
		else if (controlType == 2) {
			graphic.setDirection((float)Math.atan2
					(Engine.getMouseY()-graphic.getY(),
							Engine.getMouseX()-graphic.getX()));
			if (Engine.isKeyHeld("D")) {
				graphic.setX(graphic.getX()+speed*time);
			}
			if (Engine.isKeyHeld("A")) {
				graphic.setX(graphic.getX()-speed*time);
			}
			if (Engine.isKeyHeld("W")) {
				graphic.setY(graphic.getY()-speed*time);
			}
			if (Engine.isKeyHeld("S")) {
				graphic.setY(graphic.getY()+speed*time);
			}
		}
		else if (controlType == 3) {
			graphic.setDirection((float)Math.atan2
					(Engine.getMouseY()-graphic.getY(),
							Engine.getMouseX()-graphic.getX()));
			
			if (Math.sqrt(Math.pow(graphic.getX()-Engine.getMouseX(), 2)
					+Math.pow(graphic.getY()-Engine.getMouseY(), 2)) >= 20) {
				graphic.setX(graphic.getX()+graphic.getDirectionX()*speed*time);
				graphic.setY(graphic.getY()+graphic.getDirectionY()*speed*time);
			}
		}
		
		//Code for creating Water objects
		if (Engine.isKeyHeld("SPACE") || Engine.isKeyHeld("MOUSE")) {
			for (int i=0;i<water.length;i++) {
				if (water[i]==null) {
					Water drop = new Water(graphic.getX(),
							graphic.getY(),graphic.getDirection());
					water[i] = drop;
					break;
				}
			}
		}
	}

}
