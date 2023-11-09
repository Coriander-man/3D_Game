package DogFight;

import java.util.Iterator;

import org.jogamp.java3d.Behavior;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.WakeupCriterion;
import org.jogamp.java3d.WakeupOnElapsedFrames;

import org.jogamp.vecmath.Vector3d;
import org.jogamp.vecmath.Vector3f;

public class ExplodingPlane {
	private BranchGroup rootBG;
	private BranchGroup[] partBG;
	private TransformGroup[] posTG;
	
	private final int partsCount = 7;
	
	private ExplosionBehavior[] explodingParts;
	
	public ExplodingPlane() {
		rootBG = new BranchGroup();
		posTG = new TransformGroup[partsCount];
		partBG = new BranchGroup[partsCount];
		explodingParts = new ExplosionBehavior[partsCount];
		
		String directory = "src/DogFight/planes/su-47/";
		String[] partNames = {"body", "left_horizontal", "left_vertical", "left_wing", "right_horizontal", "right_vertical", "right_wing"};
		Transform3D temp1Trfm = new Transform3D();
		Transform3D temp2Trfm = new Transform3D();
		Transform3D temp3Trfm = new Transform3D();
		temp1Trfm.rotY(Math.PI);
		temp2Trfm.setTranslation(new Vector3f(0f,-0.25f,1.65f));
		temp1Trfm.mul(temp2Trfm);
		rootBG.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		for (int i = 0; i < partsCount; ++i) {
			partBG[i] = DFCommons.loadObj(directory + partNames[i] + ".obj", true).getSceneGroup();
			partBG[i].setCapability(BranchGroup.ALLOW_DETACH);
			posTG[i] = new TransformGroup(temp1Trfm);
			posTG[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			posTG[i].addChild(partBG[i]);
			rootBG.addChild(posTG[i]);
		}
		
		int i = 0;
		// body
		posTG[i].getTransform(temp1Trfm);
		temp2Trfm.setScale(0.5);
		temp1Trfm.mul(temp2Trfm);
		posTG[i].setTransform(temp1Trfm);
		explodingParts[i] = new ExplosionBehavior(posTG[i], 0, -0.001, 0, 0.562);
		
		++i;
		// left_horizontal
		posTG[i].getTransform(temp1Trfm);
		temp2Trfm.setScale(0.1);
		temp3Trfm.setTranslation(new Vector3f(1.6f, -0.5f, 0f));
		temp1Trfm.mul(temp2Trfm);
		temp1Trfm.mul(temp3Trfm);
		posTG[i].setTransform(temp1Trfm);
		explodingParts[i] = new ExplosionBehavior(posTG[i], 0.001, -0.001, 0.001, 0.225);
		
		++i;
		// left_vertical
		posTG[i].getTransform(temp1Trfm);
		temp2Trfm.setScale(0.085);
		temp3Trfm.setTranslation(new Vector3f(1.75f, 0.2f, -4f));
		temp1Trfm.mul(temp2Trfm);
		temp1Trfm.mul(temp3Trfm);
		posTG[i].setTransform(temp1Trfm);
		explodingParts[i] = new ExplosionBehavior(posTG[i], 0.001, 0.001, -0.001, 0.64);
		
		++i;
		// left_wing
		posTG[i].getTransform(temp1Trfm);
		temp2Trfm.setScale(0.18);
		temp3Trfm.setTranslation(new Vector3f(1.8f, -0.3f, -1f));
		temp1Trfm.mul(temp2Trfm);
		temp1Trfm.mul(temp3Trfm);
		posTG[i].setTransform(temp1Trfm);
		explodingParts[i] = new ExplosionBehavior(posTG[i], 0.001, -0.001, 0, 0.19);
		
		++i;
		// right_horizontal
		posTG[i].getTransform(temp1Trfm);
		temp2Trfm.setScale(0.1);
		temp3Trfm.setTranslation(new Vector3f(-1.6f, -0.5f, 0f));
		temp1Trfm.mul(temp2Trfm);
		temp1Trfm.mul(temp3Trfm);
		posTG[i].setTransform(temp1Trfm);
		explodingParts[i] = new ExplosionBehavior(posTG[i], -0.001, -0.001, 0.001, 0.3386);
		
		++i;
		// right_vertical
		posTG[i].getTransform(temp1Trfm);
		temp2Trfm.setScale(0.085);
		temp3Trfm.setTranslation(new Vector3f(-1.75f, 0.5f, -4f));
		temp1Trfm.mul(temp2Trfm);
		temp1Trfm.mul(temp3Trfm);
		posTG[i].setTransform(temp1Trfm);
		explodingParts[i] = new ExplosionBehavior(posTG[i], -0.001, 0.001, -0.001, 0.853);
		
		++i;
		// right_wing
		posTG[i].getTransform(temp1Trfm);
		temp2Trfm.setScale(0.18);
		temp3Trfm.setTranslation(new Vector3f(-1.8f, -0.3f, -1f));
		temp1Trfm.mul(temp2Trfm);
		temp1Trfm.mul(temp3Trfm);
		posTG[i].setTransform(temp1Trfm);
		explodingParts[i] = new ExplosionBehavior(posTG[i], -0.001, -0.001, 0, 0.189);
		
		
	}
	public BranchGroup getRootBG() {
		return rootBG;
	}
	public void explode() {
		for (int i = 0; i < partsCount; ++i) {
			explodingParts[i].setEnable(true);
		}
	}
}

class ExplosionBehavior extends Behavior {
	private TransformGroup rootTG;
	private WakeupOnElapsedFrames wakeFrame;
	
	private double x;
	private double y;
	private double z;
	
	private double distance;
	private double rate;
	
	private Transform3D trfmStep = new Transform3D();
	
	public ExplosionBehavior(TransformGroup rootTG, double x, double y, double z, double rate) {
		this.rootTG = rootTG;
		this.x = x;
		this.y = y;
		this.z = z;
		this.rate = rate;
		
		distance = Math.sqrt(x*x + y*y + z*z);
		
		rootTG.addChild(this);
		this.setEnable(false);
		this.setSchedulingBounds(DogFight.thousandBound);
	}
	@Override
	public void initialize() {
		wakeFrame = new WakeupOnElapsedFrames(0);
		wakeupOn(wakeFrame);
	}

	@Override
	public void processStimulus(Iterator<WakeupCriterion> arg0) {
		trfmStep.set(new Vector3d(rate * x / distance, rate * y / distance, rate * z / distance));
		if (y - 0.1 > 0) {
			y -= 0.1;
		}
		DFCommons.applyStep(rootTG, trfmStep);
		wakeupOn(wakeFrame);
	}
	
}
