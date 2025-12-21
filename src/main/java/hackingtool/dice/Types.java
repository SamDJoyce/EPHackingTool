package hackingtool.dice;

public enum Types {
	PERCENTILE(100),
	D10(10),
	D6(6);
	
	private final int sides;
	
	Types (int sides){
		this.sides = sides;
	}
	
	public int get() {
		return this.sides;
	}
	
	public static Types fromInt(int n) {
		for (Types t : Types.values()) {
			if (t.get() == n) {
				return t;
			}
		}
		return PERCENTILE;
	}
}
