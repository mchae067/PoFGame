
public class Fireball {	
	private Graphic graphic;
	private float speed;
	private boolean isAlive;
	 
	public Fireball(float x, float y, float directionAngle) {
		speed = 0.2f;
		isAlive = true;
		graphic = new Graphic();
		graphic.setType("FIREBALL");
		graphic.setX(x);
		graphic.setY(y);
		graphic.setDirection(directionAngle);
	}
	
	public Graphic getGraphic() {
		return graphic;
	}
	
	public boolean shouldRemove() {
		if (!isAlive) {
			return true;
		}
		return false;
	}
	
	public void handleWaterCollisions(Water[] water) {
		for(int i=0;i<water.length;i++) {
			if (water[i]!=null) {
				if(graphic.isCollidingWith(water[i].getGraphic())) {
					graphic.destroy();
					isAlive = false;
					water[i].getGraphic().destroy();
					water[i] = null;
				}
			}
		}
	}
	
	public void destroy() {
		graphic.destroy();
		isAlive = false;
	}
	
	public void update(int time) {
		graphic.draw();
		graphic.setX(graphic.getX()+graphic.getDirectionX()*speed*time);
		graphic.setY(graphic.getY()+graphic.getDirectionY()*speed*time);
		if (graphic.getX()<=-100 || graphic.getX()>=Engine.getWidth()+100
				|| graphic.getY()<=-100 
				|| graphic.getY()>=Engine.getHeight()+100) {
			destroy();
		}
	}

}
