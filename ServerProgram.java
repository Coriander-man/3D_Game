package DogFight;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerProgram extends Listener {
	public PacketMessage mess = new PacketMessage();
	
public static void main(String[] args) throws IOException, InterruptedException {
    Server server = new Server();
    server.start();
    server.bind(54555); // replace with your desired port
    
    Kryo kryo = server.getKryo();
    kryo.register(PacketMessage.class);
    kryo.register(org.jogamp.vecmath.Vector3d.class);
    kryo.register(org.jogamp.vecmath.Matrix3d.class);
    //kryo.register(String[].class);
  
    
    System.out.println("Server started");
    PacketMessage storage = new PacketMessage();
    server.addListener(new Listener() {
    	public void received(Connection connection, Object object) {
        	//System.out.println("Received object: " + object);
        	//System.out.println("object instanceof PacketMessage: " + (object instanceof PacketMessage));
            if (object instanceof PacketMessage) {
            	
            	
            	PacketMessage message = (PacketMessage) object;
            	if (message.player == false) {
            		System.out.println("Received message from client:" + message.player + " " + message.coord2 + " " + message.orient2);
            		storage.coord2 = message.coord2;
            		storage.orient2 = message.orient2;
            	}
            	else if (message.player == true) {
            		storage.coord = message.coord;
            		storage.orient = message.orient;
            		System.out.println("Received message from client:" + message.player + " " + message.coord + " " + message.orient);
            	}
                
                

                //String[] response = {"response1", "response2"};
                //message.message[1] = "response1";
                connection.sendTCP(storage);
            }
            
        }
    });

    while (true) {
        // do any other server-side processing here
        //TimeUnit.SECONDS.sleep(1); // wait for 1 second before sending the next message
    }
}
public void disconnected(Connection c){
	System.out.println("A client disconnected!");
}

}