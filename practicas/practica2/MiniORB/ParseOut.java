import java.io.*;

public class ParseOut {
	// OutputStream to write data to
	private OutputStream os;

	public ParseOut(OutputStream os) {
		this.os = os;
	}

	public void putInt(int v) {
		byte[] b = new byte[4];

		for (int i = 0; i < 4; i++) {
			b[3 - i] = (byte) (v % 256);
			v = v / 256;
		}
		try {
			os.write(b);
		} catch (IOException ioe) {
			throw new MiniORBException("cannot putInt!!");
		}
	}

	public void putLong(long v) {
		putInt((int) v);
	}

	public void putBool(boolean v) {
		try {
			if (v) {
				// Not equal to 0 is true
				os.write(1);
			} else {
				// 0 is false
				os.write(0);
			}
		} catch (IOException ioe) {
			throw new MiniORBException("cannot putBool!!");
		}
	}

	public void putString(String v) {
		byte[] b = v.getBytes();
		// First save its length
		putInt(b.length);

		try {
			os.write(b);
		} catch (IOException ioe) {
			throw new MiniORBException("cannot putString!!");
		}
	}

	public void putObject(Object obj) {
		Proxy px = null;
		// //System.out.println("pO->"+obj.getClass().getInterfaces()[0].getName());
		if (obj == null) {
			px = new Proxy(new ObjectRef("", 0, 0, ""));
		} else if (obj instanceof Proxy) {
			px = (Proxy) obj;
		} else {
			try {
				Class<?>[] v = obj.getClass().getInterfaces();
				for (int x = 0; x < v.length; x++) {
					Class<?> i = v[x];
					String SkeletonName = "Skeleton" + i.getName();
					Class<?> cls = Class.forName(SkeletonName);
					MiniORB orb = MiniORB.getOrb();
					Skeleton sk = (Skeleton) cls.newInstance();
					px = orb.addObject(obj, sk);
				}
			} catch (Exception E) {
				throw new MiniORBException("cannot putObject!!");
			}
		}
		putObjectRef(px.oref);
	}

	public void putObjectRef(ObjectRef oref) {
		try {
			// Write the address of the ORB that stores the object
			putString(oref.getHost());
			// Write the port of the ORB that stores the object
			putInt(oref.getPort());
			// Write the object id of the object
			putInt(oref.getOid());
			// Write the interface id of the object
			putString(oref.getIid());
		} catch (Exception ioe) {
			System.out.println("ieeep!! ALTO AHI!!");
			ioe.printStackTrace();
			throw new MiniORBException("cannot putObjectRef!!");
		}
	}

	public void putException(MiniORBException MIOE) {
		putString(MIOE.getMessage());
	}

	public void putList(Object[] list) {
		try {
			putInt(list.length);
			for (Object o : list) {
				putObject(o);
			}
		} catch (MiniORBException MiOE) {
			throw new MiniORBException("cannot putList!!");
		}
	}

	public void putStringList(String[] s) {
		try {
			putInt(s.length);
			for (String o : s) {
				putString(o);
			}
		} catch (MiniORBException MiOE) {
			throw new MiniORBException("cannot putStringList!!");
		}		
	}
}
