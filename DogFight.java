package DogFight;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jogamp.java3d.BoundingSphere;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Canvas3D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.View;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.Matrix3d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public class DogFight extends JPanel implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	public static Fighter f1;
	public static Plane p1;
	
	public static Fighter f2;
	public static Plane p2;
	
	private static SimpleUniverse su = null; //simple universe moved to a private global variable

	private static BranchGroup fighterBG = null;
	private static BranchGroup fighterBG2 = null;
	public static TransformGroup fighterTG2 = new TransformGroup();
	private static BranchGroup enemyBG = new BranchGroup();
	
	private static float yawRate = 0.05f;
	public static TransformGroup viewTG = new TransformGroup();
	public static Transform3D trfmStep = new Transform3D();
	 
	public final static BoundingSphere thousandBound = new BoundingSphere(new Point3d(), 1000.0);
	public final static BoundingSphere hundredBound = new BoundingSphere(new Point3d(), 100.0);
	public final static BoundingSphere tenBound = new BoundingSphere(new Point3d(), 10.0);
	
	public static boolean keyw = false;
	public static boolean keys = false;
	public static boolean keya = false;
	public static boolean keyd = false;
	public static boolean keyq = false;
	public static boolean keye = false;
	public static boolean keyshft = false;
	public static boolean keyctrl = false;
	public static boolean keyspce = false;
	public static boolean keyp = false;
	public static boolean viewnum = false;
	
	public static boolean player1 = true;
	
	public static boolean alive = true;

	public void test() {
		while(true) {
			try {
				  Thread.sleep(10000);
				} catch (InterruptedException e) {
				  Thread.currentThread().interrupt();
				}
			System.out.println("Server Connected");
		}
		
	}
	public DogFight(BranchGroup sceneBG) {
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);

		canvas.addKeyListener(this);
		su = new SimpleUniverse(canvas);  
		
		viewTG = su.getViewingPlatform().getViewPlatformTransform();
		viewTG.addChild(fighterBG);
			
		View view = su.getViewer().getView();
			
		view.setBackClipDistance(2000);
		Vector3d vector = new Vector3d(-5.0, 0.0, 5.0);

		// Create a matrix with the values:
		
		Matrix3d matrix = new Matrix3d(1.0, 0.0, 0.0, 
		                               0.0, 1.0, 0.0, 
		                               0.0, 0.0, 1.0);
		DogFight.movey2(vector, matrix);

		sceneBG.compile();		                           				// optimize the BranchGroup
		su.addBranchGraph(sceneBG);                        				// attach the scene to SimpleUniverse
		su.addBranchGraph(enemyBG);
		setLayout(new BorderLayout());
		add("Center", canvas);
		frame.setSize(1200, 720);                      // set the size of the JFrame
		frame.setVisible(true);
	}
	
	public DogFight(boolean x) {
		
		// TODO Auto-generated constructor stub
	}

	public static BranchGroup create_Scene() {
		BranchGroup sceneBG = new BranchGroup();           			// create the scene's BranchGroup
		
		f1 = new Fighter("su-47", "material", "player");
		p1 = f1.getPlane();
		fighterBG = f1.getFighterBG();
		
		f2 = new Fighter("su-47", "material", "enemy");
		p2 = f2.getPlane();
		fighterBG2 = f2.getFighterBG();
		fighterTG2.addChild(fighterBG2);
		fighterTG2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		enemyBG.addChild(fighterTG2);
		
		p1.setEnemy(p2);
		p2.setEnemy(p1);
		
        sceneBG.addChild(new Environment().get_Environment());
        sceneBG.addChild(DFCommons.addLights(DFCommons.White, 2));
        sceneBG.addChild(new Tracker(DFCommons.Green).getRootBG());
		return sceneBG;
	}

	public void main(String[] args) {
		frame = new JFrame("DogFight");
		
		frame.getContentPane().add(new DogFight(create_Scene()));  		// create an instance of the class
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static Plane getp1() {
		return p1;
	}
	public static Plane getp2() {
		return p2;
	}
	public static void movey() {
		System.out.flush();
		
		if(!alive)
			return;
		
		if(keyw) {
			trfmStep.rotX(-0.03f);
			DFCommons.applyStep(viewTG, trfmStep);
		}
		if(keys) {
			trfmStep.rotX(0.03f);
			DFCommons.applyStep(viewTG, trfmStep);
		}
		if(keya) {
			trfmStep.rotZ(0.1f);
			DFCommons.applyStep(viewTG, trfmStep);
		}
		if(keyd) {
			trfmStep.rotZ(-0.1f);
			DFCommons.applyStep(viewTG, trfmStep);
		}
		if(keyq) {
			trfmStep.rotY(yawRate);
			DFCommons.applyStep(viewTG, trfmStep);
		}
		if(keye) {
			trfmStep.rotY(-yawRate);
			DFCommons.applyStep(viewTG, trfmStep);
		}
		if(keyshft) {
			p1.accelerate(0.05f);
		}
		if(keyctrl) {
			p1.accelerate(-0.05f);
		}
		if(keyspce) {
			p1.shoot();
		}
	}
	public static void movey2(Vector3d input, Matrix3d rotate) {
		Transform3D newTrfm = new Transform3D();
		newTrfm.set(input);
		DFCommons.applyStep(fighterTG2, newTrfm);
		
		newTrfm.set(rotate);
		DFCommons.applyStep(fighterTG2, newTrfm);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_W:
			keyw = true;
			break;
		case KeyEvent.VK_S:
			keys = true;
			break;
		case KeyEvent.VK_A:
			keya = true;
			break;
		case KeyEvent.VK_D:
			keyd = true;
			break;
		case KeyEvent.VK_Q:
			keyq = true;
			break;
		case KeyEvent.VK_E:
			keye = true;
			break;
		case KeyEvent.VK_SHIFT:
			keyshft = true;
			break;
		case KeyEvent.VK_CONTROL:
			keyctrl = true;
			break;
		case KeyEvent.VK_SPACE:
			keyspce = true;
			break;
		}
		
		movey();
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_W:
			keyw = false;
			break;
		case KeyEvent.VK_S:
			keys = false;
			break;
		case KeyEvent.VK_A:
			keya = false;
			break;
		case KeyEvent.VK_D:
			keyd = false;
			break;
		case KeyEvent.VK_Q:
			keyq = false;
			break;
		case KeyEvent.VK_E:
			keye = false;
			break;
		case KeyEvent.VK_SHIFT:
			keyshft = false;
			break;
		case KeyEvent.VK_CONTROL:
			keyctrl = false;
			break;
		case KeyEvent.VK_SPACE:
			keyspce = false;
			DFCommons.playSound(7);
			break;
		}
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public static Vector3d getCoords() {
		
		Transform3D t3d = new Transform3D();
		TransformGroup tg = (TransformGroup) fighterBG.getParent();
		tg.getTransform(t3d);
		Vector3d position = new Vector3d();
		t3d.get(position);

		//System.out.println("Fighter position: " + position);
		return position;
	}
	
	public static Matrix3d getOrient() {
		
		Transform3D t3d = new Transform3D();
		TransformGroup tg = (TransformGroup) fighterBG.getParent();
		tg.getTransform(t3d);
		Matrix3d rotation = new Matrix3d();
		t3d.get(rotation);
		//System.out.println("Orientation: " + rotation);

		return rotation;
	}
	
}