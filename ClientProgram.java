package DogFight;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientProgram {
	public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        client.start();
        
        
        client.connect(5000, "localhost", 54555); // replace with your server's address and port
        
        Kryo kryo = client.getKryo();
       // kryo.register(String[].class);
        kryo.register(PacketMessage.class);
        kryo.register(org.jogamp.vecmath.Vector3d.class);
        kryo.register(org.jogamp.vecmath.Matrix3d.class);
        
        
        Scanner sc= new Scanner(System.in);    //System.in is a standard input stream  
        System.out.print("Enter your player number:");  
        int playernum= sc.nextInt();  
        
        System.out.println("Client started as player " + playernum);
        
        PacketMessage geout = new PacketMessage();
        
        
        
        while(true) {
        DogFight y = new DogFight(true);
		String[] x = null;
		//y.create_Scene();
		y.main(x);
		//y.test();
		client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
            	//System.out.println("Received object: " + object);
                if (object instanceof PacketMessage) {
                    
                    PacketMessage messagein = (PacketMessage) object;
                    System.out.println("Received message from client:" + messagein.player + " "+ messagein.coord + " " + messagein.orient+ " " + messagein.coord2 + " " + messagein.orient2);
                    if(playernum == 1 && messagein.coord2 != null) {
                    	DogFight.movey2(messagein.coord2, messagein.orient2);
                    	System.out.println("moved");
                    }
                    if(playernum == 2 && messagein.coord != null) {
                    	DogFight.movey2(messagein.coord, messagein.orient);
                    	
                    }
                    //System.out.println("Received message from server:" +messagein.coord );
//                    
                }
            }
        });
		
		
        while (true) {
            String[] message = {"hello"};
            PacketMessage messageout = new PacketMessage();
           
            if(playernum == 1) {
            	messageout.player = true;
            	messageout.coord =  DogFight.getCoords();
                messageout.orient = DogFight.getOrient();
            }
            if(playernum == 2) {
            	messageout.player = false;
            	messageout.coord2 =  DogFight.getCoords();
                messageout.orient2 = DogFight.getOrient();
            }
            
           
           
            client.sendTCP(messageout); // send the message to the server over TCP
            
            TimeUnit.SECONDS.sleep((long) 1.0); // wait for 1 second before sending the next message
            
            
			
        }

        
        }
    }
}