import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Game {	
	private Hero hero;
	private Water[] water;
	private ArrayList<Pant> pants;
	private ArrayList<Fireball> fireballs;
	private ArrayList<Fire> fire;
	
	public Game(String level, Random randGen) {
		if (level.contains("RANDOM"))
		{
			createRandomLevel(level,randGen);
		}
		else
		{
			loadLevel(level, randGen);
		}
	}
	
	public String update(int time) {
		//Hero update
		hero.update(time, water);
		
		//Update Water array
		for (int i=0;i<water.length;i++) {
			if(water[i]!=null)
			{
				water[i] = water[i].update(time);
			}
		}
		
		//Update Pant arrayList
		for (int j=0;j<pants.size();j++) {
			pants.get(j).update(time);
		}
		
		//Update Fire arrayList
		for (int l=0;l<fire.size();l++) {
			Fireball temp = fire.get(l).update(time);
			if (temp!=null)
			{
					fireballs.add(temp);
			}
		}
		
		//Update Fireball arrayList
		for (int k=0;k<fireballs.size();k++) {
			fireballs.get(k).update(time);	
		}
		
		//Collision detection
		//Hero-Fireball
		if (hero.handleFireballCollisions(fireballs)) {
			return "QUIT";
		}
		//Fireball-Water
		for(int m=0;m<fireballs.size();m++) {
			fireballs.get(m).handleWaterCollisions(water);
		}
		//Fire-Water
		for(int n=0;n<fire.size();n++) {
			fire.get(n).handleWaterCollisions(water);
		}
		//Pant-Fire
		for(int o=0;o<pants.size();o++) {
			Fire burningPants = 
					pants.get(o).handleFireballCollisions(fireballs);
			if (burningPants!=null)
			{
				fire.add(burningPants);
			}
		}
		
		//Remover methods
		for(int p=0;p<pants.size();p++) {
			if (pants.get(p).shouldRemove()) 
			{
				pants.remove(p);
			}
		}
		for(int p=0;p<fire.size();p++) {
			if (fire.get(p).shouldRemove()) 
			{
				fire.remove(p);
			}
		}
		for(int p=0;p<fireballs.size();p++) {
			if (fireballs.get(p).shouldRemove()) 
			{
				fireballs.remove(p);
			}
		}
		
		//Level code
		if (pants.size()<=0) {
			return "QUIT";
		}
		if (fire.size()<=0 && pants.size()!=0) {
			return "ADVANCE";
		}
		
		
		return "CONTINUE";
	}	
	
	public String getHUDMessage() { 
		int i;
		int j;
		for(i=0;i<pants.size();i++) {
		}
		for(j=0;j<fire.size();j++) {
		}
		String string = String.format("Pants Left: %d \nFires Left: %d", i, j);
		return string; 
	}
	
	//Creates random level
	public void createRandomLevel(String level, Random randGen) {
		randGen = new Random();
		this.hero = new Hero(Engine.getWidth()/2,Engine.getHeight()/2,
				randGen.nextInt(2)+1);
		//Water array
		this.water = new Water[8];
		//Pant array
		this.pants = new ArrayList<Pant>();
		for (int i=0;i<20;i++) {
			randGen = new Random();
			float minX = 0.0f;
			float maxX = Engine.getWidth();
			float minY = 0.0f;
			float maxY = Engine.getHeight();
			Pant temp = new Pant(randGen.nextFloat()*(maxX - minX) + minX,
					randGen.nextFloat()* (maxY - minY) + minY,randGen);
			pants.add(temp);
		}
		//Fire array
		this.fire = new ArrayList<Fire>();
		for (int j=0;j<6;j++) {
			randGen = new Random();
			float minX = 0.0f;
			float maxX = Engine.getWidth();
			float minY = 0.0f;
			float maxY = Engine.getHeight();
			Fire temp = new Fire(randGen.nextFloat()*(maxX - minX) + minX,
					randGen.nextFloat()* (maxY - minY) + minY,randGen);
			fire.add(temp);
		}
		
		//Fireball ArrayList
		this.fireballs = new ArrayList<Fireball>();
	}
	
	//Loads a level from text file
	public void loadLevel(String level, Random randGen) {
		this.water = new Water[8];
		this.pants = new ArrayList<Pant>();
		this.fire= new ArrayList<Fire>();
		this.fireballs = new ArrayList<Fireball>();

		Scanner in = new Scanner(level);
		int controlType = 0;
		while (in.hasNext()) {
			String[] line = in.nextLine().split(" ");
			for (int i=0;i<line.length;i++) {
				String str = line[i].replaceAll(",", " ");
				line[i] = str;
				//System.out.print(line[i]+" ");
			}
			if (line[0].equals("ControlType:")) {
				controlType = Integer.parseInt(line[1]);
			}
			if (line[0].equals("FIRE")) {
				float x = Float.parseFloat(line[2]);
				float y = Float.parseFloat(line[3]);
				Fire flame = new Fire(x,y,randGen);
				fire.add(flame);
			}
			else if (line[0].equals("PANT")) {
				float x = Float.parseFloat(line[2]);
				float y = Float.parseFloat(line[3]);
				Pant pant = new Pant(x,y,randGen);
				pants.add(pant);
			}
			else if (line[0].equals("HERO")) {
				float x = Float.parseFloat(line[2]);
				float y = Float.parseFloat(line[3]);
				Hero hero = new Hero(x,y,controlType);
				this.hero = hero;
			}
		}
		in.close();
		if (controlType == 0) {
			System.out.println("Need to specify control type!");
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		Application.startEngineAndGame(args);		
	}
}
