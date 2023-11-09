package DogFight;

import java.util.Iterator;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Behavior;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.LineArray;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.WakeupCriterion;
import org.jogamp.java3d.WakeupOnElapsedFrames;

import org.jogamp.vecmath.Color3f;
import org.jogamp.vecmath.Point3f;
import org.jogamp.vecmath.Vector3f;

public class Tracker extends Behavior {
	private BranchGroup rootBG;
	
	private Vector3f vector = new Vector3f();
	private Transform3D planePos = new Transform3D();
	private LineArray tracker;
	private Shape3D shape;
	
	private final int pointCount = 4;
	private WakeupOnElapsedFrames wakeFrame = null;
	
	public Tracker(Color3f color) {
		
		Point3f planeCoordinates = getPoint(DogFight.getp1().getRootTG());
		Point3f enemyCoordinates = getPoint(DogFight.getp2().getRootTG());
		tracker = new LineArray(2, LineArray.COLOR_3 | LineArray.COORDINATES);
		
//		for (int i = 0; i < pointCount / 2; ++i) {
//			tracker.setCoordinate(i*2, planeCoordinates);
//			tracker.setCoordinate((i*2)+1, enemyCoordinates);
//			
//			tracker.setColor(i*2, color);
//			tracker.setColor((i*2)+1, color);
//		}
		tracker.setCoordinate(0, planeCoordinates);
		tracker.setCoordinate(1, enemyCoordinates);
		
		tracker.setColor(0, color);
		tracker.setColor(1, color);
		tracker.setCapability(LineArray.ALLOW_COORDINATE_READ);
		tracker.setCapability(LineArray.ALLOW_COORDINATE_WRITE);
		
		shape = new Shape3D(tracker, new Appearance());
		shape.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
		
		rootBG = new BranchGroup();
		rootBG.addChild(this);
		rootBG.addChild(shape);
		this.setEnable(true);
		this.setCollidable(false);
		this.setSchedulingBounds(DogFight.thousandBound);
	}
	private Point3f getPoint(TransformGroup tg) {
		planePos = new Transform3D();
		tg.getTransform(planePos);
		vector = new Vector3f();
		planePos.get(vector);
		Point3f point = new Point3f();
		vector.get(point);
		return point;
	}
	public BranchGroup getRootBG() {
		return rootBG;
	}
	@Override
	public void initialize() {
		wakeFrame = new WakeupOnElapsedFrames(0);
		wakeupOn(wakeFrame);
	}

	@Override
	public void processStimulus(Iterator<WakeupCriterion> arg0) {
		Point3f planePoint = getPoint(DogFight.getp1().getPlaneTG());
		tracker.setCoordinate(0, planePoint);
//		tracker.setCoordinate(3, planePoint);
		Point3f enemyPoint = getPoint(DogFight.getp2().getPlaneTG());
		tracker.setCoordinate(1, enemyPoint);
//		tracker.setCoordinate(2, enemyPoint);
		
		
		shape.setGeometry(tracker);
		wakeupOn(wakeFrame);
	}
}
