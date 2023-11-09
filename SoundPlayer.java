package DogFight;

import com.jogamp.openal.AL;
import com.jogamp.openal.ALConstants;
import com.jogamp.openal.ALException;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;

import java.nio.ByteBuffer;
import java.util.HashMap;


public class SoundPlayer {
	private final static String SOUND_DIR = "src/DogFight/sounds/";
	private AL al;

	private HashMap<String, int[]> buffersMap;
	private HashMap<String, int[]> sourcesMap;

	public SoundPlayer() {
		buffersMap = new HashMap<>();
		sourcesMap = new HashMap<>();

		initOpenAL();
	}


	private void initOpenAL() {
		try {
			ALut.alutInit();
			al = ALFactory.getAL();
			al.alGetError();
		} catch (ALException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}




	public boolean load(String nm, boolean toLoop) {
		if (sourcesMap.get(nm) != null) {
			return true;
		}
		int[] buffer = initBuffer(nm);
		if (buffer == null)
			return false;

		int[] source = initSource(nm, buffer, toLoop);
		if (source == null) {
			al.alDeleteBuffers(1, buffer, 0);
			return false;
		}

		buffersMap.put(nm, buffer);
		sourcesMap.put(nm, source);
		return true;
	}


	private int[] initBuffer(String nm) {
		ByteBuffer[] data = new ByteBuffer[1];
		int[] format = new int[1], size = new int[1], freq = new int[1], loop = new int[1];

		String fnm = SOUND_DIR + nm + ".wav";
		try {
			ALut.alutLoadWAVFile(fnm, format, data, size, freq, loop);
		} catch (ALException e) {
			System.out.println("loading error");
			return null;
		}
		int[] buffer = new int[1];
		al.alGenBuffers(1, buffer, 0);
		if (al.alGetError() != ALConstants.AL_NO_ERROR) {
			return null;
		}
		al.alBufferData(buffer[0], format[0], data[0], size[0], freq[0]);

		return buffer;
	}


	private int[] initSource(String nm, int[] buf, boolean toLoop) {

		int[] source = new int[1];
		al.alGenSources(1, source, 0);
		if (al.alGetError() != ALConstants.AL_NO_ERROR) {
			return null;
		}


		al.alSourcei(source[0], ALConstants.AL_BUFFER, buf[0]);
		al.alSourcef(source[0], ALConstants.AL_PITCH, 1.0f);
		al.alSourcef(source[0], ALConstants.AL_GAIN, 1.0f);
		al.alSource3f(source[0], ALConstants.AL_POSITION, 0.0f, 0.0f, 0.0f);
		al.alSource3i(source[0], ALConstants.AL_VELOCITY, 0, 0, 0);
		if (toLoop)
			al.alSourcei(source[0], ALConstants.AL_LOOPING, ALConstants.AL_TRUE);
		else
			al.alSourcei(source[0], ALConstants.AL_LOOPING, ALConstants.AL_FALSE);

		if (al.alGetError() != ALConstants.AL_NO_ERROR) {
			return null;
		}
		return source;
	}


	public boolean setPos(String nm, float x, float y, float z) {
		int[] source = sourcesMap.get(nm);
		if (source == null) {
			System.out.println("audio not found" + nm);
			return false;
		}
		al.alSource3f(source[0], ALConstants.AL_POSITION, x, y, z);
		return true;
	}


	public boolean load(String nm, float x, float y, float z, boolean toLoop) {
		if (load(nm, toLoop))
			return setPos(nm, x, y, z);
		else
			return false;
	}


	public boolean play(String nm) {
		int[] source = sourcesMap.get(nm);
		if (source == null) {
			System.out.println("audio not found" + nm);
			return false;
		}
		al.alSourcePlay(source[0]);
		return true;
	}


	public boolean stop(String nm) {
		int[] source = sourcesMap.get(nm);
		if (source == null) {
			System.out.println("audio not found" + nm);
			return false;
		}
		al.alSourceStop(source[0]);
		return true;
	}

}
