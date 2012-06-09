public class ProxyA extends Proxy implements A {
	public ProxyA(ObjectRef oref) {
		super(oref);
	}

	public void save(String s, int i) {
		Invocation invo = oref.newInvocation();
		invo.putInt(1);
		invo.putString(s);
		invo.putInt(i);
		invo.send();
		invo.waitEnd();
		return;
	}

	public int load(String s) {
		Invocation invo = oref.newInvocation();
		invo.putInt(2);
		invo.putString(s);
		invo.send();
		int ret = invo.getInt();
		invo.waitEnd();
		return ret;
	}
}
