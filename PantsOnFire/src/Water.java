
public class Water {

	private Graphic graphic;
	private float speed;
	private float distanceTraveled;

	public Water(float x, float y, float direction) {
		speed = 0.7f;
		graphic = new Graphic();
		graphic.setType("WATER");
		graphic.setX(x);
		graphic.setY(y);
		graphic.setDirection(direction);
		distanceTraveled = 0;
	}
	
	public Graphic getGraphic() {
		return graphic;
	}
	
	public Water update(int time)  {
		graphic.draw();
		graphic.setX(graphic.getX()+graphic.getDirectionX()*speed*time);
		graphic.setY(graphic.getY()+graphic.getDirectionY()*speed*time);
		distanceTraveled = distanceTraveled+(float)Math.sqrt((Math.pow
				(graphic.getDirectionX()*speed*time,2)
				+Math.pow(graphic.getDirectionY()*speed*time, 2)));
		if (distanceTraveled > 200){
			return null;
		}
		return this;
	}
}
