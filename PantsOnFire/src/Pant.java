import java.util.ArrayList;
import java.util.Random;

public class Pant {
	private Graphic graphic;
	private Random randGen;
	private boolean isAlive;
	 
	public Pant(float x, float y, Random randGen) {
		graphic = new Graphic();
		isAlive = true;
		graphic.setType("PANT");
		graphic.setX(x);
		graphic.setY(y);
	}
	
	public Graphic getGraphic() {
		return graphic;
	}
	
	public boolean shouldRemove()  {
		if (!isAlive) {
			return true;
		}
		return false;
	}
	
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for(int i=0;i<fireballs.size();i++) {
			if(graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
				Fire fire = new Fire(graphic.getX(),graphic.getY(),randGen);
				graphic.destroy();
				isAlive = false;
				fireballs.get(i).destroy();
				return fire;
			}
		}
		return null;
	}
	
	public void update(int time)  {
		graphic.draw();
	}


}
