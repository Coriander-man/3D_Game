package DogFight;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.jogamp.java3d.Alpha;
import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Behavior;
import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Canvas3D;
import org.jogamp.java3d.Geometry;
import org.jogamp.java3d.GeometryArray;
import org.jogamp.java3d.ImageComponent2D;
import org.jogamp.java3d.Material;
import org.jogamp.java3d.OrientedShape3D;
import org.jogamp.java3d.QuadArray;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.Switch;
import org.jogamp.java3d.Texture;
import org.jogamp.java3d.Texture2D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.TransparencyAttributes;
import org.jogamp.java3d.TransparencyInterpolator;
import org.jogamp.java3d.TriangleArray;
import org.jogamp.java3d.WakeupCriterion;
import org.jogamp.java3d.WakeupOnElapsedFrames;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.utils.geometry.Box;
import org.jogamp.java3d.utils.geometry.Cone;
import org.jogamp.java3d.utils.geometry.Cylinder;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3d;
import org.jogamp.vecmath.Vector3f;

public class Fighter {
	private BranchGroup fighterBG;		// Holds everything in the Fighter class. This will be referenced by the DogFight class
	private Switch mainGroup;
	private boolean player;
	private Plane plane;
	private ExplodingPlane exPlane;
	private Fighter enemy;	
	private int health = 100;
	
	public Fighter(String fighterName, String design, String playerType) {
		this.player = playerType.equals("player");
		mainGroup = new Switch();
		fighterBG = new BranchGroup();
		plane = new Plane(fighterName, design, playerType);
		exPlane = new ExplodingPlane();
		mainGroup.addChild(plane.getRootTG());
		mainGroup.addChild(exPlane.getRootBG());
		fighterBG.addChild(mainGroup);
		DFCommons.playSound(4);
		mainGroup.setWhichChild(0);
		mainGroup.setCapability(Switch.ALLOW_SWITCH_WRITE);
		fighterBG.addChild(DFCommons.addLights(DFCommons.White, 2));
	}
	public Fighter(Canvas3D canvas3d) {
		// TODO Auto-generated constructor stub
		
	}
	public void takeDamage(int damage) {
		health -= damage;
		if (health <= 0)
			explode();
	}
	public void setEnemy(Fighter enemy) {
		this.enemy = enemy;
	}
	public Switch getFighterGroup() {
		return mainGroup;
	}
	public BranchGroup getFighterBG() {
		return fighterBG;
	}
	public Plane getPlane() {
		return plane;
	}
	public void explode() {
		DFCommons.playSound(2);
		DFCommons.playSound(5);
		DogFight.alive = false;
		exPlane.explode();
		mainGroup.setWhichChild(1);
	}
}

class Plane extends Behavior {
	private Scene s;
	private TransformGroup rootTG;
	private BranchGroup bulletBG;
	private BranchGroup planeBG;
	private TransformGroup planeTG;
	private int partsCount;
	private String design;
	private Queue<Bullet> unusedBullets;
	private Queue<Bullet> usedBullets;
	private Bullet[] bullets;
	private String playerType;
	
	private Transform3D planePos = new Transform3D();
	private Transform3D trfmStep = new Transform3D();
	private WakeupOnElapsedFrames wakeFrame = null;
	private float throttle = 0.05f;
	private int bulletCount = 50;
	private int shotsFired = 0;
	private Bullet bullet = null;
	private CollisionDetector cd;
	
	public Plane(String planeFileName, String design, String playerType) {
		this.playerType = playerType;
		
		String directory = "src/DogFight/planes/" + planeFileName + ".obj";
		
		this.design = design;
		s = DFCommons.loadObj(directory, design.equals("material"));
		
		planeBG = s.getSceneGroup();
		partsCount = planeBG.numChildren();
		setDesign();
		
		planePos = new Transform3D();
		planePos.rotY(Math.PI);
		
		Transform3D translator = new Transform3D();
		translator.setTranslation(new Vector3f(0f,-0.35f,2.5f));
		
		planePos.mul(translator);
		planePos.setScale(0.5f);
		
		bulletBG = new BranchGroup();
		rootTG = new TransformGroup(planePos);
		planeTG = new TransformGroup();
		planeTG.addChild(planeBG);
		rootTG.addChild(planeTG);
		rootTG.addChild(bulletBG);
		rootTG.addChild(this);
		rootTG.setCollidable(true);
		
		rootTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		rootTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		create_Bullets();
	}
	protected void setDesign() {
		Appearance app = null;
		if (design.equals("material"))
			return;
		
		if (design.equals("texture")) {
			Texture2D texture = getTexture("metal");
			app = new Appearance();
			app.setTexture(texture);
		} else {
			app = DFCommons.objAppearance(new Color3f(0f, 0f, 0f));
		}
		for (int i = 0; i < partsCount; ++i) {
			((Shape3D) planeBG.getChild(i)).setAppearance(app);
		}
		
	}
	private Texture2D getTexture(String name) {
		String filename = "src/DogFight/images/" + name + ".jpg";
		TextureLoader loader = new TextureLoader(filename, null);
		ImageComponent2D image = loader.getImage();
		if (image == null)
			System.out.println("Cannot open file: " + filename);
		
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		return texture;
	}
	public Scene getPlaneScene() {
		return s;
	}
	public BranchGroup getShapeBG() {
		return planeBG;
	}
	public TransformGroup getRootTG() {
		return rootTG;
	}
	public TransformGroup getPlaneTG() {
		return planeTG;
	}
	public BranchGroup getBulletBG() {
		return bulletBG;
	}
	public void accelerate(float offset) {
		if (0.001f <= throttle+offset && throttle+offset <= 1f)
			throttle += offset;
	}
	public void setEnemy(Plane enemy) {
		cd = new CollisionDetector(planeTG, bulletBG, enemy.getPlaneTG(), enemy.getBulletBG());
		cd.setSchedulingBounds(DogFight.hundredBound);
		rootTG.addChild(cd);
		this.setSchedulingBounds(DogFight.hundredBound);
	}
	public void create_Bullets() {
		unusedBullets = new LinkedList<Bullet>();
		bullets = new Bullet[bulletCount];
		for (int i = 0; i < bulletCount; ++i) {
			bullet = new Bullet(playerType);
			bulletBG.addChild(bullet.getRootGroup());
			unusedBullets.add(bullet);
			bullets[i] = bullet;
		}
		usedBullets = new LinkedList<Bullet>();
	}
	public void shoot() {
		DFCommons.playSound(6);
		bullet = unusedBullets.poll();
		if (bullet == null) return;
		bullet.enable();
		usedBullets.add(bullet);
		
		if (shotsFired == bulletCount-10) reset_First_Bullet();
		else shotsFired++;
	}
	private void reset_First_Bullet() {
		bullet = usedBullets.poll();
		if (bullet == null) return;
		bullet.disable();
		DogFight.viewTG.getTransform(planePos);
		bullet.reset_Position(planePos);
		unusedBullets.add(bullet);
	}
	@Override
	public void initialize() {
		wakeFrame = new WakeupOnElapsedFrames(0);
		wakeupOn(wakeFrame);
	}
	@Override
	public void processStimulus(Iterator<WakeupCriterion> arg0) {
		if (playerType.equals("player")) {
			trfmStep.set(new Vector3d(0.0, 0.0, -throttle));
			DFCommons.applyStep(DogFight.viewTG, trfmStep);
		} else {
			trfmStep.set(new Vector3d(0.0, 0.0, throttle));
			DFCommons.applyStep(rootTG, trfmStep);
		}
		
		wakeupOn(wakeFrame);
	}
}
