package hackingtool.dice;

public class DiceFactory {

	private DiceFactory() {
	}
	
	public static Dice get(Types dieType) {
		return new Dice(dieType.get());
	}
	
	public static Dice get(int sides) {
		return new Dice(sides);
	}
	
}
