/*
 * cliente.java
 *
 * Copyright 2012 MAHIQUES CARRASCO, Luis M <luimacar@pc0406>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 *
 *
 */


public class cliente {
    String hostc, hosts;
    //MiniORB
    int portc, ports, oid = 0, iid = 0;
    ProxyB pA;



    public static void main (String args[]) {
        /*for (int i = 0; i<args.length; i++)
        System.err.println(args[i]);*/
        cliente c = new cliente();
        if (! c.parseArgs(args)){
            System.err.println(
                "use:\n\t $ java cliente <hostc> <portc> <hosts> <ports> [oid] [iid]"
                );
        }

        c.pruebaProxyA();
    }


    public void pruebaProxyA(){
        pA = new ProxyB(new ObjectRef(hosts, ports, oid, iid));
        pA.save("prueba", 42);
        pA.save("maspruebas", 142);
        System.out.println(pA.load("maspruebas"));
        System.out.println(pA.load("prueba"));
        System.out.println(pA.add("prueba","prueba"));
    }

    public boolean parseArgs(String args[]){

        if (args.length < 6){
            System.err.println("Invalid argument number: " + args.length);
            return false;
        }

        hostc = args[0];

        try {
            portc = (new Integer(args[1])).intValue();
        } catch (Exception e) {
            System.err.println("Invalid port number: " + args[1]);
            return false;
        }

        hosts = args[2];

        try {
            ports = (new Integer(args[3])).intValue();
        } catch (Exception e) {
            System.err.println("Invalid port number: " + args[3]);
            return false;
        }

        //if (args.length > 4)
        try {
            oid = (new Integer(args[4])).intValue();
        } catch (Exception e) {
            System.err.println("Invalid oid number: " + args[4]);
            return false;
        }

        //if (args.length > 5)
        try {
            iid = (new Integer(args[5])).intValue();
        } catch (Exception e) {
            System.err.println("Invalid iid number: " + args[5]);
            return false;
        }

        return true;

    }
}

