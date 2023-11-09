package DogFight;

import org.jogamp.vecmath.Matrix3d;
import org.jogamp.vecmath.Vector3d;

public class PacketMessage {
    //This is the Packet class. Everything in this object can be sent over the network!
    public boolean player;
    public Vector3d coord;
    public Matrix3d orient;
    public Vector3d coord2;
    public Matrix3d orient2;
}