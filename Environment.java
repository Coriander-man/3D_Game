/* Copyright material for students taking COMP-2800 to work on assignment/labs/projects. */

package DogFight;

import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.vecmath.Vector3f;
import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Background;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.ImageComponent2D;
import org.jogamp.java3d.Material;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.Texture;
import org.jogamp.java3d.Texture2D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;
import org.jogamp.java3d.utils.geometry.Box;

import java.io.FileNotFoundException;
import java.util.Random;

public class Environment {
	private TransformGroup mainTG;		// Holds everything in the Environment class; this will be referenced by the DogFight class
	// add more private fields when needed

	protected BranchGroup load_Shape(String obj_name) {
		ObjectFile f = new ObjectFile(ObjectFile.RESIZE, (float) (60 * Math.PI / 180.0));
		Scene s = null;
		try {                                       // load object's definition file to 's'
			s = f.load("src/DogFight/objects/" + obj_name + ".obj");
		} catch (FileNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		} catch (ParsingErrorException e) {
			System.err.println(e);
			System.exit(1);
		} catch (IncorrectFormatException e) {
			System.err.println(e);
			System.exit(1);
		}
		return s.getSceneGroup();
	}



	public TransformGroup getMountain1() {//bigger one
		BranchGroup objBG1;
		objBG1 = load_Shape("Mountain11");
		Shape3D r1shape = (Shape3D) objBG1.getChild(0);
		Appearance appearance = new Appearance();
		Material material = new Material();

		appearance.setMaterial(material);
		r1shape.setAppearance(MountainApp());
		TransformGroup r1 = new TransformGroup();
		r1.addChild(objBG1);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(-1.1f,0.3f,-1.4f);
		t1.setTranslation(vector);
		t1.setScale(2);
		r1.setTransform(t1);
		return r1;
	}

	public TransformGroup getMountain2() {
		BranchGroup objBG1;
		objBG1 = load_Shape("Mountain11");
		Shape3D r1shape = (Shape3D) objBG1.getChild(0);
		r1shape.setAppearance(MountainApp());
		TransformGroup r1 = new TransformGroup();
		r1.addChild(objBG1);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(1.8f,0.1f,-2f);
		t1.setTranslation(vector);
		t1.setScale(1);
		r1.setTransform(t1);
		return r1;
	}



	public TransformGroup getMountain3() {
		BranchGroup objBG1;
		objBG1 = load_Shape("Mountain21");
		Shape3D r1shape = (Shape3D) objBG1.getChild(0);
		r1shape.setAppearance(MountainApp());
		TransformGroup r1 = new TransformGroup();
		r1.addChild(objBG1);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(1.1f,0.1f,1.1f);
		t1.setTranslation(vector);
		t1.setScale(2);
		r1.setTransform(t1);
		return r1;
	}

	public TransformGroup getMountain4() { //small moun
		BranchGroup objBG1;
		objBG1 = load_Shape("Mountain11");
		Shape3D r1shape = (Shape3D) objBG1.getChild(0);
		r1shape.setAppearance(MountainApp());
		TransformGroup r1 = new TransformGroup();
		r1.addChild(objBG1);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(-1.8f,0.1f,2f);
		t1.setTranslation(vector);
		t1.setScale(1);
		r1.setTransform(t1);
		return r1;
	}

	//Vector3f(red,green,blue)


	public Appearance MountainApp() {
		Appearance appearance = new Appearance();
		String filename = "src/DogFight/images/mountainTexture.jpg";
		TextureLoader loader = new TextureLoader(filename,null);
		ImageComponent2D image = loader.getImage();
		if(image==null)
		{
			System.out.println("Can not load file: "+filename);
		}
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL,Texture.RGBA,image.getWidth(),image.getHeight());
		texture.setImage(0, image);
		appearance.setTexture(texture);
		return appearance;
	}



	public Appearance App(String pictureName) {
		Appearance appearance = new Appearance();
		String filename = "src/DogFight/images/"+pictureName+".jpg";
		TextureLoader loader = new TextureLoader(filename,null);
		ImageComponent2D image = loader.getImage();
		if(image==null)
		{
			System.out.println("Can not load file: "+filename);
		}
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL,Texture.RGBA,image.getWidth(),image.getHeight());
		texture.setImage(0, image);
		appearance.setTexture(texture);
		return appearance;
	}
	public TransformGroup getBox(Appearance app) {
		Box box1 = new Box(3.0f, 0.1f, 3.0f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, app);
		TransformGroup r1 = new TransformGroup();
		r1.addChild(box1);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(0f,-0.1f,0f);
		t1.setTranslation(vector);
		t1.setScale(1);
		r1.setTransform(t1);
		return r1;

	}
	//***************building*******************
	public TransformGroup getBuilding1(Appearance app,float VecX,float VecY, float VecZ) {
		Box box1 = new Box(0.3f, 1.5f, 0.3f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, app);
		TransformGroup r1 = new TransformGroup();
		r1.addChild(box1);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(VecX,VecY,VecZ);
		t1.setTranslation(vector);
		t1.setScale(1);
		r1.setTransform(t1);
		return r1;

	}

	public TransformGroup getBuilding2(Appearance app,float VecX,float VecY, float VecZ) {
		Box box1 = new Box(0.3f, 0.5f, 0.3f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, app);
		TransformGroup r1 = new TransformGroup();
		r1.addChild(box1);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(VecX,VecY,VecZ);
		t1.setTranslation(vector);
		t1.setScale(1);
		r1.setTransform(t1);
		return r1;

	}

	public TransformGroup getBuilding3(Appearance app,float VecX,float VecY, float VecZ) {
		Box box1 = new Box(0.2f, 1f, 0.2f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, app);
		TransformGroup r1 = new TransformGroup();
		r1.addChild(box1);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(VecX,VecY,VecZ);
		t1.setTranslation(vector);
		t1.setScale(1);
		r1.setTransform(t1);
		return r1;
	}

	public TransformGroup getBuilding4(Appearance app,float VecX,float VecY, float VecZ) {
		Box box1 = new Box(0.2f, 1.5f, 0.2f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, app);
		TransformGroup r1 = new TransformGroup();
		r1.addChild(box1);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(VecX,VecY,VecZ);
		t1.setTranslation(vector);
		t1.setScale(1);
		r1.setTransform(t1);
		return r1;

	}


	public TransformGroup getBuildingGroup(float x, float y, float z){
		TransformGroup b1 = new TransformGroup();
		b1.addChild(getBox(App("cement")));
		b1.addChild(getBuilding1(App("buildingText2"), -2f, 0.95f, -1.2f));
		b1.addChild(getBuilding2(App("buildingText3"), 1.2f, 0.35f, -1.2f));
		b1.addChild(getBuilding3(App("buildingText"), -1.5f, 0.6f, 1.2f));
		b1.addChild(getBuilding4(App("buildingText1"), 1.5f, 0.85f, 1.2f));

		b1.addChild(getBuilding1(App("buildingText1"), -2f, 0.85f, 0f));
		b1.addChild(getBuilding2(App("buildingText2"), 1.5f, 0.35f, 0f));
		b1.addChild(getBuilding3(App("buildingText3"), 0f, 0.6f, -2f));
		b1.addChild(getBuilding4(App("buildingText"), 0f, 0.85f, 1.2f));

		b1.addChild(getBuilding1(App("buildingText2"), -2f, 0.85f, -2f));
		b1.addChild(getBuilding2(App("buildingText3"), 1.2f, 0.35f, -2f));
		b1.addChild(getBuilding3(App("buildingText"), -2f, 0.6f, 2f));
		b1.addChild(getBuilding4(App("buildingText1"), 1.5f, 0.85f, 2f));
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(x,y,z);
		t1.setTranslation(vector);
		t1.setScale(1);
		b1.setTransform(t1);

		return b1;
	}

	public TransformGroup getMountainGroup(float x, float y, float z) {

		TransformGroup r1 = new TransformGroup();
		r1.addChild(getMountain1());
		r1.addChild(getMountain2());
		r1.addChild(getMountain3());
		r1.addChild(getMountain4());
		r1.addChild(getBox(MountainApp()));
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(x,y,z);
		t1.setTranslation(vector);
		t1.setScale(3.85);
		r1.setTransform(t1);
		return r1;
	}




	public TransformGroup getRoad(float VecX,float VecY, float VecZ)
	{
		Box road = new Box(3f, 0.1f, 0.4f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, App("road"));
		TransformGroup r1 = new TransformGroup();
		r1.addChild(road);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(VecX,VecY,VecZ);
		t1.setTranslation(vector);
		t1.setScale(1);
		r1.setTransform(t1);
		return r1;
	}

	public TransformGroup getRoadGroup(float x,float y,float z) {

		TransformGroup r1 = new TransformGroup();
		r1.addChild(getRoad(0,0f,0));
		r1.addChild(getRoad(6,0f,0));
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(x,y,z);
		t1.setTranslation(vector);
		t1.setScale(1);
		r1.setTransform(t1);
		return r1;
	}


	public TransformGroup getPlainGroup(float x, float y, float z)
	{
		TransformGroup r1 = new TransformGroup();
		r1.addChild(getBox(App("plainTexture2")));
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(x,y,z);
		t1.setTranslation(vector);
		t1.setScale(1);
		r1.setTransform(t1);
		return r1;

	}

	public TransformGroup getEnviroment() //include everything about ground
	{
		TransformGroup r1 = new TransformGroup();
		r1.addChild(getRoadGroup(0,-0.05f,1.6f)); //building and mountain done by Yiheng
		r1.addChild(getBuildingGroup(0,0,4.5f));
		r1.addChild(getBuildingGroup(6,0,4.5f));
		r1.addChild(getBuildingGroup(0,0,-1.3f));
		r1.addChild(getBuildingGroup(6,0,-1.3f));
		r1.addChild(getRoadGroup(12,-0.05f,1.6f));
		r1.addChild(getBuildingGroup(12,0,4.5f));
		r1.addChild(getBuildingGroup(18,0,4.5f));
		r1.addChild(getBuildingGroup(12,0,-1.3f));
		r1.addChild(getBuildingGroup(18,0,-1.3f));

		r1.addChild(getRoadGroup(0,-0.05f,-8.8f)); //building and mountain done by Yiheng
		r1.addChild(getBuildingGroup(0,0,-5.9f));
		r1.addChild(getBuildingGroup(6,0,-5.9f));
		r1.addChild(getBuildingGroup(0,0,-11.7f));
		r1.addChild(getBuildingGroup(6,0,-11.7f));
		r1.addChild(getRoadGroup(12,-0.05f,-8.8f));
		r1.addChild(getBuildingGroup(12,0,-5.9f));
		r1.addChild(getBuildingGroup(18,0,-5.9f));
		r1.addChild(getBuildingGroup(12,0,-11.7f));
		r1.addChild(getBuildingGroup(18,0,-11.7f));

		r1.addChild(getPlainGroup(0,0,10.5f)); //Plain done by Jin
		r1.addChild(getPlainGroup(6,0,10.5f));
		r1.addChild(getPlainGroup(12,0,10.5f));
		r1.addChild(getPlainGroup(18,0,10.5f));

		r1.addChild(getMountainGroup(9,0f,24.55f));


		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(0,-10,0);
		t1.setTranslation(vector);
		t1.setScale(3);
		r1.setTransform(t1);
		return r1;
	}


	public Environment() {
		// add transformations such as translations and scales and pass into the following TransformGroup() if needed
		mainTG = new TransformGroup();

		// Loads a background image; copied from the assignments
		Background bg = new Background();
		bg.setImage(new TextureLoader("src/DogFight/images/sky.jpg", null).getImage());			// /!\ Change "room.jpg" to whatever your image name is
		bg.setImageScaleMode(Background.SCALE_FIT_MAX);
		bg.setApplicationBounds(DogFight.hundredBound);

		mainTG.addChild(bg);

		mainTG.addChild(getEnviroment());
	}
	public TransformGroup get_Environment() {
		return mainTG;					// Returns the reference to Environment class
	}
}

class SimpleEnvironment {
	private TransformGroup mainTG;
	public SimpleEnvironment() {
		mainTG = new TransformGroup();

		Background bg = new Background();
		bg.setImage(new TextureLoader("src/DogFight/images/sky.jpg", null).getImage());			// /!\ Change "room.jpg" to whatever your image name is
		bg.setImageScaleMode(Background.SCALE_FIT_MAX);
		bg.setApplicationBounds(DFCommons.maxBound);

		mainTG.addChild(bg);
		
		Random rand = new Random();
		int map = rand.nextInt(0,2);
		if(map == 0) {
			mainTG.addChild(getMountain1());
		}
		else{
			mainTG.addChild(getMountain2());
		}
		
	}
	public TransformGroup getMountain1() {//bigger one
		BranchGroup objBG1;
		String directory = "src/DogFight/objects/DuneMap.obj";
		objBG1 = DFCommons.loadObj(directory, true).getSceneGroup();
		//Shape3D r1shape = (Shape3D) objBG1.getChild(0);
		//r1shape.setAppearance(MountainApp());
		TransformGroup r1 = new TransformGroup();
		r1.addChild(objBG1);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(1.1f,-30.0f,1.1f);
		t1.setTranslation(vector);
		t1.setScale(1000);
		r1.setTransform(t1);
		return r1;
	}
	public TransformGroup getMountain2() {//bigger one
		BranchGroup objBG1;
		String directory = "src/DogFight/objects/RockyCliffs.obj";
		objBG1 = DFCommons.loadObj(directory, true).getSceneGroup();
		//Shape3D r1shape = (Shape3D) objBG1.getChild(0);
		//r1shape.setAppearance(MountainApp());
		TransformGroup r1 = new TransformGroup();
		r1.addChild(objBG1);
		Transform3D t1 = new Transform3D();
		Vector3f vector = new Vector3f(1.1f,-30.0f,1.1f);
		t1.setTranslation(vector);
		t1.setScale(1000);
		r1.setTransform(t1);
		return r1;
	}
	public Appearance MountainApp() {
		Appearance appearance = new Appearance();
		String filename = "src/DogFight/images/mountainTexture.jpg";
		TextureLoader loader = new TextureLoader(filename,null);
		ImageComponent2D image = loader.getImage();
		if(image==null)
		{
			System.out.println("Can not load file: "+filename);
		}
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL,Texture.RGBA,image.getWidth(),image.getHeight());
		texture.setImage(0, image);
		appearance.setTexture(texture);
		return appearance;
	}
	public TransformGroup get_Environment() {
		return mainTG;
	}
}