package DogFight;

import java.util.Iterator;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Behavior;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.ImageComponent2D;
import org.jogamp.java3d.Switch;
import org.jogamp.java3d.Texture;
import org.jogamp.java3d.Texture2D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.WakeupCriterion;
import org.jogamp.java3d.WakeupOnElapsedFrames;
import org.jogamp.java3d.utils.geometry.Cone;
import org.jogamp.java3d.utils.geometry.Cylinder;
import org.jogamp.java3d.utils.image.TextureLoader;

import org.jogamp.vecmath.Vector3d;
import org.jogamp.vecmath.Vector3f;

/* A class to for bullet, adjust scaling if necessary */
public class Bullet extends Behavior {
	private Switch rootGroup;
	private TransformGroup bulletTG;
	private Transform3D initTrfm;
	private WakeupOnElapsedFrames wakeFrame = null;
	private Transform3D bulletStep = new Transform3D();

	public Bullet(String playerType) {

		rootGroup = new Switch();
		Appearance app = new Appearance();
		setTexture(app, "brass");

		Cylinder cylinder = new Cylinder(0.15f, 0.5f, Cylinder.GENERATE_NORMALS | Cylinder.GENERATE_TEXTURE_COORDS, app);
		Cone cone = new Cone(0.15f, 0.5f, Cone.GENERATE_NORMALS | Cone.GENERATE_TEXTURE_COORDS, app);
		
		Transform3D coneTranslator = new Transform3D();
		coneTranslator.setTranslation(new Vector3f(0.0f, 0.5f, 0.0f));
		TransformGroup coneTG = new TransformGroup(coneTranslator);
		coneTG.addChild(cone);
		
		Transform3D scaler = new Transform3D();
		scaler.setScale(0.1f);
		Transform3D rotator = new Transform3D();
		rotator.rotX(Math.PI / 2.0f);
		scaler.mul(rotator);
		TransformGroup orientTG = new TransformGroup(scaler);
		orientTG.addChild(cylinder);
		orientTG.addChild(coneTG);
		
		initTrfm = new Transform3D();
		initTrfm.setTranslation(new Vector3f(0.0f, -0.21f, 0.0f));

		bulletTG = new TransformGroup(initTrfm);
		bulletTG.addChild(orientTG);

		bulletTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		bulletTG.addChild(this);
		
		rootGroup.setCapability(Switch.ALLOW_SWITCH_WRITE);
		rootGroup.addChild(new BranchGroup());
		rootGroup.addChild(bulletTG);
		
		this.setSchedulingBounds(DogFight.tenBound);
		
		this.setEnable(false);
	}
	protected void setTexture(Appearance app, String name) {
		Texture2D texture = getTexture(name);
		app.setTexture(texture);
	}
	private Texture2D getTexture(String fileName) {
		String path = "src/DogFight/images/" + fileName + ".jpg";
		TextureLoader loader = new TextureLoader(path, null);
		ImageComponent2D image = loader.getImage();        // load the image
		if (image == null)
			System.out.println("Cannot open file: " + fileName);

		Texture2D texture = new Texture2D(Texture.BASE_LEVEL,
				Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);	// set image for the texture
		return texture;
	}
	public void reset_Position(Transform3D planePos) {
		bulletTG.setTransform(initTrfm);
	}
	public Switch getRootGroup() {
		return rootGroup;
	}
	public void enable() {
		rootGroup.setWhichChild(1);
		this.setEnable(true);
	}
	public void disable() {
		rootGroup.setWhichChild(0);
		this.setEnable(false);
	}
	@Override
	public void initialize() {
		wakeFrame = new WakeupOnElapsedFrames(0);
		wakeupOn(wakeFrame);
	}
	@Override
	public void processStimulus(Iterator<WakeupCriterion> arg0) {
		bulletStep.set(new Vector3d(0.0, 0.0, 1.0));
		DFCommons.applyStep(bulletTG, bulletStep);
		wakeupOn(wakeFrame);
	}
}
