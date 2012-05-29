public class ProxyB extends Proxy implements B {
	public ProxyB(ObjectRef oref) {
		super(oref);
	}

	public void save(String s, int i) {
		Invocation invo = oref.newInvocation();
		invo.putInt(1);
		invo.putString(s);
		invo.putInt(i);
		invo.waitEnd();
		return;
	}

	public int load(String s) {
		Invocation invo = oref.newInvocation();
		invo.putInt(2);
		invo.putString(s);
		int ret = invo.getInt();
		invo.waitEnd();
		return ret;
	}

	public int add(String a, String b) {
		Invocation invo = oref.newInvocation();
		invo.putInt(3);
		invo.putString(a);
		invo.putString(b);
		int ret = invo.getInt();
		invo.waitEnd();
		return ret;
	}

	public int sub(String a, String b) {
		Invocation invo = oref.newInvocation();
		invo.putInt(4);
		invo.putString(a);
		invo.putString(b);
		int ret = invo.getInt();
		invo.waitEnd();
		return ret;
	}
}
