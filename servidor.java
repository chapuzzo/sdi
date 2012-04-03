/************************************************************************/
/*									*/
/* FICHERO: servidor.java						*/
/*									*/
/* DESCRIPCIÓN: Contiene la implementación del servidor de la 1a prác-	*/
/*	tica. Ofrece un método por cada una de las peticiones que es	*/
/*	capaz de atender (UDP y TCP). Existe una subclase "multi" que	*/
/*	permite la atención de múltiples peticiones mediante múltiples	*/
/*	hilos de ejecución (TCP).					*/
/*									*/
/************************************************************************/

import java.net.*;		// El paquete java.net se necesita para	//
				// las clases Socket, ServerSocket,	//
				// DatagramSocket y DatagramPacket.	//
import java.io.*;		// El paquete java.io se necesita para	//
				// las clases InputStream y OutputStream//

/*======================================================================*/
/* servidor								*/
/*	Clase que implementa un método por cada modalidad de llamada.	*/
/*									*/
/*======================================================================*/
public class servidor {
				// La subclase multi implementa el	//
				// código de los hilos de ejecución que	//
				// se utilizan en la variante multi-	//
				// thread del servidor TCP.		//
    class multi  implements Runnable {
        Socket so;		// El socket "so" se utiliza para con-	//
				// testar una petición, después se	//
				// cierra.				//
        long dur;		// "dur" mantiene la duración de la	//
				// espera asociada a cada petición.	//

				// En el constructor se pasa el socket	//
				// TCP a utilizar y la duración de la	//
				// espera de la próxima petición.	//
        public multi(Socket s, long duration) {
            so = s;
            dur = duration;
        }
				// Este es el código que ejecuta cada	//
				// uno de los hilos servidores.		//
        public synchronized void  run() {
            try {
				// Se obtiene un stream de entrada	//
				// desde el socket.			//
                InputStream  is = so.getInputStream();
				// Se hace lo mismo para el stream de	//
				// salida en el que se va a dejar la	//
				// respuesta.				//
                OutputStream os = so.getOutputStream();
                int ch;
               
				// El cliente sólo envía un carácter.	//
                ch = is.read();
		if ( dur > 0 )
                  wait(dur);	// Esperamos el tiempo indicado.	//
                os.write(65);	// La contestación sólo es un carácter.	//
                so.close();	// Se cierra el socket, ya no se va a	//
				// volver a utilizar.			//
            } catch (IOException e) {
                System.out.println("Algo fue mal");
            } catch (InterruptedException e) {
                System.out.println("Adiós");
            }
        }
    }
    
				// Método a utilizar con un cliente	//
				// UDP.					//
    public synchronized void server_udp(int port, long dur) {
            DatagramSocket ds;	// Socket para UDP.			//
				// El contenido del paquete UDP va a	//
				// ser un vector de bytes con una sola	//
				// componente.				//
            byte[]         buf = new byte[1];
				// El paquete UDP se crea con el vector	//
				// anterior.				//
            DatagramPacket dp  = new DatagramPacket(buf,1);
            
            try {		// Hay que crear un socket UDP asociado	//
				// al puerto especificado como primer	//
				// argumento.				//
                ds = new DatagramSocket(port);
            } catch (Exception e) {
                System.out.println("No se ha podido asociar el socket " +
			"UDP al puerto " + port);
                return;
            }
                
            while (true) {
                try {
				// Se recibe una petición por parte del	//
				// cliente. Una vez obtenida hay que	//
				// esperar el tiempo indicado para si-	//
				// mular la ejecución de la petición.	//
                    ds.receive(dp);
                    if ( dur > 0 )
			wait(dur);
				// Al final se contesta, reenviando el	//
				// mismo paquete recibido.		//
                    ds.send(dp);
                } catch (IOException e) {
                    System.out.println("No se ha podido recibir.");
                    return;
                } catch (InterruptedException e) {
                    System.out.println("Adiós");
                    return;
                }
            }
    }
    
				// Método a utilizar con clientes TCP.	//
    public synchronized void server_tcp(int port, long dur) {
        ServerSocket ss;
        Socket       so;
        InputStream  is;
        OutputStream os;

        try {			// Se crea el socket servidor ss, aso-	//
				// ciándolo al número de puerto especi-	//
				// ficado como primer argumento.	//
            ss = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("No se puede asociar el puerto " + port +
		" al socket TCP.");
            return;
        }
        
        while (true) {
            try {
				// Esperar conexión por parte del 	//
				// cliente y generar un nuevo socket	//
				// para atenderla cuando se establezca.	//
                so = ss.accept();
				// "is" va a permitir la lectura de	//
				// información existente en el socket.	//
                is = so.getInputStream();
				// "os" se utiliza para escribir infor-	//
				// mación que podrá obtener el cliente.	//
                os = so.getOutputStream();
                int ch;
               
                ch = is.read();	// Esperar hasta poder leer un byte.	//
		if ( dur > 0 )
                  wait(dur);	// Simular la atención de la petición.	//
                os.write(65);	// Enviar la respuesta.			//
                so.close();	// Cerrar el socket utilizado para	//
				// esta conexión.			//
            } catch (IOException e) {
                System.out.println("Algo fue mal");
            } catch (InterruptedException e) {
                System.out.println("Adiós");
            }
        }
    }
    
				// Versión TCP con mútiples hilos.	//
    public synchronized void server_tcp_mt(int port, long dur) {
        ServerSocket ss;
        Socket       so;

        try {
				// Asociar un puerto al socket servidor	//
				// a utilizar para esperar las conexio-	//
				// nes de los clientes.			//
            ss = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("No se puede asociar el puerto " + port +
		" al socket TCP.");
            return;
        }
        
        while (true) {
            try {
				// Aceptar las conexiones de los clien-	//
				// tes, creando un nuevo socket por	//
				// cada una de ellas.			//
                so = ss.accept();
				// Crear un thread "multi" que sirva	//
				// la petición que acaba de llegar.	//
                new Thread(new multi(so,dur)).start();
            } catch (IOException e) {
                System.out.println("Algo fue mal");
            }
        }
    }
    
    public static void main(String[] args) {
        int        port = 8000;
        boolean    udp  = false;
        boolean    MT   = false;
        long       duration = 0;
        servidor   worker   = new servidor();
        
        			// Extraccion de argumentos.		//
        if (args.length < 2) {
            System.out.println("Número de argumentos erróneo");
            return;
        }
				// Comprobar el primer argumento. Si es	//
				// "u", utilizar el servidor UDP. Si es	//
				// "t", utilizar el servidor TCP. En	//
				// cualquier otro caso, utilizar el	//
				// servidor multithread.		//
        if (args[0].equalsIgnoreCase("u")) {
            System.out.println("Servidor UDP");
            udp = true;
        } else if (args[0].equalsIgnoreCase("t")) {
            System.out.println("Servidor TCP");
            udp = false;
        } else {
            System.out.println("Servidor TCP MT");
            MT = true;
        }
        
        try {			// Obtener el número de puerto a partir	//
				// del segundo argumento.		//
            port = (new Integer(args[1])).intValue();
        } catch (Exception e) {
            System.out.println(args[1] + " no es un número válido de puerto");
            return;
        }
        
				// Si existe un tercer argumento será	//
				// la duración a asignar al servicio de	//
				// cada petición.			//
        if (args.length == 3) {
            try {
                duration = (new Long(args[2])).longValue();
				// La duración no puede ser negativa.	//
		if (duration < 0)
			duration = -duration;
            } catch (Exception e) {
                System.out.println(args[2] + "no es un valor adecuado de" +
				   " duración.");
                return;
            }
        } else {
            duration = 0;
        }
        
        			// Lógica del programa.			//
        if (udp) {
            worker.server_udp(port,duration);
        } else if (MT) {
            worker.server_tcp_mt(port,duration);
        } else {
            worker.server_tcp(port,duration);
        }
    }
}
