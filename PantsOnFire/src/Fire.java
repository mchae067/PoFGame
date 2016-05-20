import java.util.Random;

public class Fire {
	private Graphic graphic;
	private Random randGen;
	private int fireballCountdown;
	private int heat;
	private boolean canShoot;

	public Fire(float x, float y, Random randGen) {
		graphic = new Graphic();
		heat = 40;
		canShoot = true;
		//3000 to 5999 is too slow...
		//fireballCountdown = randGen.nextInt(2999)+3000
		randGen = new Random();
		fireballCountdown = randGen.nextInt(150)+220;
		graphic.setType("FIRE");
		graphic.setX(x);
		graphic.setY(y);
	}
	
	public Graphic getGraphic() {
		return graphic;
	}
	
	public boolean shouldRemove() {
		if (!canShoot)
		{
			return true;
		}
		return false;
	}
	
	public void handleWaterCollisions(Water[] water) {
		for(int i=0;i<water.length;i++) {
			if (water[i]!=null) {
				if(graphic.isCollidingWith(water[i].getGraphic())) {
					heat--;
					water[i].getGraphic().destroy();
					water[i] = null;
					if (heat<=0) {
						graphic.destroy();
						canShoot = false;
					}
				}
			}
		}
	}
	
	public Fireball update(int time) {
		graphic.draw();
		fireballCountdown--;
		if (fireballCountdown<=0 && canShoot) {
			randGen = new Random();
			float max = (float)Math.PI*2;
			Fireball fireball = new Fireball(graphic.getX(),
					graphic.getY(), randGen.nextFloat()*(max));
			//3000 to 5999 is too slow...
			//fireballCountdown = randGen.nextInt(2999)+3000
			fireballCountdown = randGen.nextInt(150)+220;
			return fireball;
		}
		return null;
	}
}
