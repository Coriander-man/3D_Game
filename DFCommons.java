
package DogFight;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.JPanel;

import org.jdesktop.j3d.examples.sound.BackgroundSoundBehavior;
import org.jdesktop.j3d.examples.sound.PointSoundBehavior;
import org.jdesktop.j3d.examples.sound.audio.JOALMixer;
import org.jogamp.java3d.*;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.java3d.utils.universe.Viewer;
import org.jogamp.vecmath.*;

public class DFCommons extends JPanel {
	private static final long serialVersionUID = 1L;
	public static SoundPlayer sound = new SoundPlayer();
	public final static Color3f Red = new Color3f(1.0f, 0.0f, 0.0f);
	public final static Color3f Green = new Color3f(0.0f, 1.0f, 0.0f);
	public final static Color3f Blue = new Color3f(0.0f, 0.0f, 1.0f);
	public final static Color3f Yellow = new Color3f(1.0f, 1.0f, 0.0f);
	public final static Color3f Cyan = new Color3f(0.0f, 1.0f, 1.0f);
	public final static Color3f Orange = new Color3f(1.0f, 0.5f, 0.0f);
	public final static Color3f Magenta = new Color3f(1.0f, 0.0f, 1.0f);
	public final static Color3f White = new Color3f(1.0f, 1.0f, 1.0f);
	public final static Color3f Grey = new Color3f(0.35f, 0.35f, 0.35f);
	public final static Color3f Black = new Color3f(0.0f, 0.0f, 0.0f);
	public final static Color3f[] clr_list = {Blue, Green, Red, Yellow,
			Cyan, Orange, Magenta, Grey};
	public final static int clr_num = 8;
	private static Color3f[] mtl_clrs = {White, Grey, Black};

	public final static BoundingSphere maxBound = new BoundingSphere(new Point3d(0f, 0f, 0f), Double.MAX_VALUE);
	public final static BoundingSphere twentyBS = new BoundingSphere(new Point3d(), 20.0);
	
	public static Transform3D position = new Transform3D();

	public static Scene loadObj(String directory, boolean hasMtl) {
		Scene s = null;
		ObjectFile loader = null;
		int flags = ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY;
		
		try {
			if (hasMtl) {
				loader = new ObjectFile(flags);
				File file = new File(directory);
				s = loader.load(file.toURI().toURL());
			} else {
				loader = new ObjectFile(flags, (float) (60 * Math.PI / 180.0));
				s = loader.load(directory);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		} catch (ParsingErrorException e) {
			System.err.println(e);
			System.exit(1);
		} catch (IncorrectFormatException e) {
			System.err.println(e);
			System.exit(1);
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
		return s;
	}
    
	public static void applyStep(TransformGroup tg, Transform3D trfm) {
		tg.getTransform(position);
		position.mul(trfm);
		tg.setTransform(position);
	}
	
	public static Appearance objAppearance(Color3f m_clr) {		
		Material mtl = new Material();                     // define material's attributes
		mtl.setShininess(32);
		mtl.setAmbientColor(mtl_clrs[0]);                   // use them to define different materials
		mtl.setDiffuseColor(m_clr);
		mtl.setSpecularColor(mtl_clrs[1]);
		mtl.setEmissiveColor(mtl_clrs[2]);                  // use it to switch button on/off
		mtl.setLightingEnable(true);

		Appearance app = new Appearance();
		app.setMaterial(mtl);                              // set appearance's material
		return app;
	}

	/* a function to enable audio */
	public static void enableAudio(SimpleUniverse su){
		JOALMixer mixer = null;     // create a JOALMixer
		Viewer viewer = su.getViewer();
		viewer.getView().setBackClipDistance(20.0f);    // disappear beyond 20f

		if (mixer == null && viewer.getView().getUserHeadToVworldEnable()){
			mixer = new JOALMixer(viewer.getPhysicalEnvironment());
			if (!mixer.initialize()){   // add audio device
				System.out.println("Open AL failed to init");
				viewer.getPhysicalEnvironment().setAudioDevice(null);
			}
		}
	}

	/* a function to create point sound effect, use fileName and PointSound as parameters, use ps to control sound pause and resume */
	public static PointSound pointSoundCrash(String fileName, PointSound ps){
		//Point2f[] distanceGain = {new Point2f(10.0f, 6.0f), // Full volume
		//		new Point2f(20.0f, 4.0f), // Half volume
		//		new Point2f(30.0f, 3.0f), // Quarter volume
		//		new Point2f(50.0f, 0.0f) // Zero volume
		//};

		URL url = null;
		String filePath = "src/DogFight/sounds/" + fileName + ".wav";
		try {
			url = new URL("file", "localhost", filePath);
		} catch (Exception e) {
			System.out.println("Can't open " + filePath);
		}

		// PointSound ps = new PointSound();   // create point sound
		ps.setInitialGain(3.0f);    // set initial gain
		ps.setLoop(-1);     // 0: once, 1: loop
		//ps.setDistanceGain(distanceGain); // Set sound distance
		//ps.setPosition(new Point3f(0.0f, 0.0f, 0.0f));  // set sound position
		// create and position a point sound
		PointSoundBehavior pointSoundBehavior = new PointSoundBehavior(ps, url, new Point3f(0.0f, 0.0f, 0.0f));
		pointSoundBehavior.setSchedulingBounds(DogFight.hundredBound); // set scheduling
		return ps;
	}

	public static PointSound pointSoundExplosion(String fileName, PointSound ps){
		//Point2f[] distanceGain = {new Point2f(10.0f, 6.0f), // Full volume
		//		new Point2f(20.0f, 4.0f), // Half volume
		//		new Point2f(30.0f, 3.0f), // Quarter volume
		//		new Point2f(50.0f, 0.0f) // Zero volume
		//};

		URL url = null;
		String filePath = "src/DogFight/sounds/" + fileName + ".wav";
		try {
			url = new URL("file", "localhost", filePath);
		} catch (Exception e) {
			System.out.println("Can't open " + filePath);
		}
		ps.setCapability(PointSound.ALLOW_ENABLE_WRITE);

		//PointSound ps = new PointSound();   // create point sound
		ps.setInitialGain(3.0f);    // set initial gain
		ps.setLoop(1);     // 0: once, 1: loop
		//ps.setDistanceGain(distanceGain); // Set sound distance
		//ps.setPosition(new Point3f(0.0f, 0.0f, 0.0f));  // set sound position
		// create and position a point sound
		PointSoundBehavior pointSoundBehavior = new PointSoundBehavior(ps, url, new Point3f(0.0f, 0.0f, 0.0f));
		pointSoundBehavior.setSchedulingBounds(DogFight.hundredBound); // set scheduling
		return ps;
	}

	public static PointSound pointSoundShot(String fileName, PointSound ps){
		//Point2f[] distanceGain = {new Point2f(10.0f, 6.0f), // Full volume
		//		new Point2f(20.0f, 4.0f), // Half volume
		//		new Point2f(30.0f, 3.0f), // Quarter volume
		//		new Point2f(50.0f, 0.0f) // Zero volume
		//};

		URL url = null;
		String filePath = "src/DogFight/sounds/" + fileName + ".wav";
		try {
			url = new URL("file", "localhost", filePath);
		} catch (Exception e) {
			System.out.println("Can't open " + filePath);
		}

		//PointSound ps = new PointSound();   // create point sound
		ps.setInitialGain(6.0f);    // set initial gain
		ps.setLoop(-1);     // 0: once, 1: loop
		//ps.setDistanceGain(distanceGain); // Set sound distance
		//ps.setPosition(new Point3f(0.0f, 0.0f, 0.0f));  // set sound position
		// create and position a point sound
		PointSoundBehavior pointSoundBehavior = new PointSoundBehavior(ps, url, new Point3f(0.0f, 0.0f, 0.0f));
		pointSoundBehavior.setSchedulingBounds(DogFight.hundredBound); // set scheduling
		return ps;
	}

	public static PointSound pointSoundEngine(String fileName, PointSound ps){
		//Point2f[] distanceGain = {new Point2f(10.0f, 6.0f), // Full volume
		//		new Point2f(20.0f, 4.0f), // Half volume
		//		new Point2f(30.0f, 3.0f), // Quarter volume
		//		new Point2f(50.0f, 0.0f) // Zero volume

		//};
		ps.setCapability(PointSound.ALLOW_ENABLE_WRITE);

		URL url = null;
		String filePath = "src/DogFight/sounds/" + fileName + ".wav";
		try {
			url = new URL("file", "localhost", filePath);
		} catch (Exception e) {
			System.out.println("Can't open " + filePath);
		}

		//PointSound ps = new PointSound();   // create point sound
		ps.setInitialGain(0.1f);    // set initial gain
		ps.setLoop(1);     // 0: once, 1: loop
		//ps.setDistanceGain(distanceGain); // Set sound distance
		//ps.setPosition(new Point3f(0.0f, 0.0f, 0.0f));  // set sound position
		// create and position a point sound
		PointSoundBehavior pointSoundBehavior = new PointSoundBehavior(ps, url, new Point3f(0.0f, 0.0f, 0.0f));
		pointSoundBehavior.setSchedulingBounds(DogFight.hundredBound); // set scheduling
		return ps;
	}

	/* a function to create background sound effect */
	public static BackgroundSound backgroundSound(String fileName, BackgroundSound bgs){
		URL url = null;
		String filePath = "src/DogFight/sounds/" + fileName + ".wav";
		try {
			url = new URL("file", "localhost", filePath);
		} catch (Exception e) {
			System.out.println("Can't open " + filePath);
		}

		//BackgroundSound bgs = new BackgroundSound();    // create a background sound
		bgs.setInitialGain(0.4f);  // lower its volume
		BackgroundSoundBehavior player = new BackgroundSoundBehavior(bgs, url); // create the sound behavior
		player.setSchedulingBounds(DogFight.hundredBound);  // set scheduling bound

		return bgs;
	}
	
	/* a function to place one light or two lights at opposite locations */
	public static BranchGroup addLights(Color3f clr, int p_num) {
		BranchGroup lightBG = new BranchGroup();
		Point3f atn = new Point3f(0.5f, 0.0f, 0.0f);
		PointLight ptLight;
		float adjt = 1f;
		for (int i = 0; (i < p_num) && (i < 2); i++) {
			if (i > 0) 
				adjt = -1f; 
			ptLight = new PointLight(clr, new Point3f(3.0f * adjt, 1.0f, 3.0f  * adjt), atn);
			ptLight.setInfluencingBounds(DogFight.hundredBound);
			lightBG.addChild(ptLight);
		}
		return lightBG;
	}

	public static void playSound(int key) {
		String snd_pt = "engine";
		sound.load(snd_pt, 2, 0, 0, true);

		if (key ==2){
			snd_pt = "explosion1";
			sound.load(snd_pt, 2, 0, 0, true);
			sound.play(snd_pt);

			try {
				Thread.sleep(500); // sleep for 0.5 secs
			} catch (InterruptedException ex) {}
			sound.stop(snd_pt);
		}
		if(key==3)
		{
			snd_pt = "gunshot2";
			sound.load(snd_pt, 2, 0, 0, true);
			sound.play(snd_pt);

			try {
				Thread.sleep(100); 
			} catch (InterruptedException ex) {}
			sound.stop(snd_pt);
		}
		if(key==4)
		{
			snd_pt = "engine";
			sound.load(snd_pt, 2, 0, 0, true);
			sound.play(snd_pt);
		}
		if(key==5)
		{
			snd_pt = "engine";
			sound.stop(snd_pt);
		}
		if(key==6)
		{
			snd_pt = "gunshot2";
			sound.load(snd_pt, 2, 0, 0, true);
			sound.play(snd_pt);
		}
		if(key==7)
		{
			snd_pt = "gunshot2";
			sound.stop(snd_pt);
		}
	}
}
