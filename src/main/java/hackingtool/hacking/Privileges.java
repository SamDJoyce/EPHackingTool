package hackingtool.hacking;

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
		for (Privileges a : Privileges.values()) {
			if (a.getLevel() == level) {
				return a;
			}
		}
		return PUBLIC;
	}
	
}
