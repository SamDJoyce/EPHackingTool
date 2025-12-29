package hackingtool.devices;

public enum Privileges {
	PUBLIC(0),
	USER(1),
	SECURITY(2),
	ADMIN(3);
	
	private final int level;
	
	Privileges(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public static Privileges fromLevel(int level) {
		for (Privileges p : Privileges.values()) {
			if (p.getLevel() == level) {
				return p;
			}
		}
		return PUBLIC;
	}
	public static Privileges fromString(String priv) {
		for (Privileges p : Privileges.values()) {
			if (p.toString().equalsIgnoreCase(priv)) {
				return p;
			}
		}
		return PUBLIC;
	}
}
