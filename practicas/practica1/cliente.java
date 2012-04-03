
import java.net.*;		// El paquete java.net se necesita para	//
						// las clases Socket, ServerSocket,	//
						// DatagramSocket y DatagramPacket.	//
import java.io.*;		// El paquete java.io se necesita para	//
						// las clases InputStream y OutputStream//

public class cliente{
	
	public void client_udp(InetAddress server, int port) {
		byte[] buf = new byte[1];
		DatagramSocket ds;
		DatagramPacket dp;
		
		buf[0] = 'u';
		
		try {
		ds = new DatagramSocket();
		dp = new DatagramPacket(buf,buf.length,server,port);
		
		ds.send(dp);
		ds.receive(dp);
		ds.close();
		//System.out.println(new String(dp.getData()));
		}  catch (Exception e){ 
			System.out.println("No se ha podido realizar la comunicación.");
			return;
		}
	}
	
	
	public void client_tcp(InetAddress server, int port) {
		byte[] buf = new byte[1];
		Socket sck;
		InputStream si;
		OutputStream so;
		
		buf[0] = 't';
		
		try {
		sck = new Socket(server, port);
		si = sck.getInputStream();
		so = sck.getOutputStream();
		so.write(buf);
		si.read(buf);
		sck.close();
		//System.out.println(new String(buf));
		}  catch (Exception e){ 
			System.out.println("No se ha podido realizar la comunicación.");
			return;
		}
	}
	
	public void client_local(cliente_local cl){
		byte[] buf = new byte[1];
		buf[0] = 'X';
		cl.servicio_local(buf);
		
	}
	
	public static void main (String[] args){
		
		int port = 5000;
		InetAddress server = null;
		boolean udp = false;
		boolean local = false;
		long MAXLOOP = 0;
		cliente worker = new cliente();
		cliente_local cl = new cliente_local();
		
		// $ java cliente (u|t|l) <iteraciones> [<servidor> <puerto>]
		
		
		if (args.length < 2) {
            System.out.println("Número de argumentos erróneo");
            return;
        }
        
        if (args[0].equalsIgnoreCase("u")) {
            //System.out.println("Cliente UDP");
            udp = true;
        } else if (args[0].equalsIgnoreCase("t")) {
            //System.out.println("Cliente TCP");
            udp = false;
        }      
        else if (args[0].equalsIgnoreCase("l")) {
            //System.out.println("Cliente local");
            local = true;
        }
        
        try {			// Obtener el número de iteraciones a partir //
						// del segundo argumento.		    //
            MAXLOOP = (new Long(args[1])).longValue();
        }catch (Exception e) {
				System.out.println(args[1] + " no es un número valido de iteraciones");
				return;
			}
        
        if (!local){
			try {			// Obtener la dirección del servidor a partir	//
							// del tercer argumento.		//
				server = InetAddress.getByName(args[2]);
			} catch (Exception e) {
				System.out.println(args[2] + " no es un nombre válido de servidor");
				return;
			}
			
			
			try {			// Obtener el número de puerto a partir	//
							// del cuarto argumento.		//
				port = (new Integer(args[3])).intValue();
			} catch (Exception e) {
				System.out.println(args[3] + " no es un número válido de puerto");
				return;
			}
        }
        
        long inicio = System.nanoTime();
        for (int i=0; i<MAXLOOP; i++){
        
		 /*lógica del programa*/
		if (udp)
			worker.client_udp(server,port);
		else if (!local) 
			worker.client_tcp(server,port);
		else ; 
			worker.client_local(cl);
			
		}
		long tiempo = (System.nanoTime() - inicio);
		double tiempo2 = (new Double(tiempo)/new Double(MAXLOOP))/new Double(1000000);
		System.out.printf("%.6g\n",tiempo2);
	}
	
	
}

class cliente_local{
		
		public byte[] servicio_local(byte[] b){		
			
			return b;
		}
		
		 	
	}

