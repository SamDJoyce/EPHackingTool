package hackingtool.devices;

public enum IntruderStatus {
	HIDDEN(0),
	COVERT(1),
	SPOTTED(2);
	
	private final int level;
	
	IntruderStatus(int level) {
		this.level = level;
	}

	public int getLevel() {
		return this.level;
	}
	
	public Boolean isHidden() {
		return level == HIDDEN.level;
	}
	
	public Boolean isCovert() {
		return this.level == COVERT.level;
	}
	
	public Boolean  isSpotted() {
		return this.level == SPOTTED.level;
	}
	
	public static IntruderStatus fromLevel (int level) {
		for (IntruderStatus i : IntruderStatus.values()) {
			if (i.getLevel() == level) {
				return i;
			}
			
		}
		return SPOTTED;
	}
}
