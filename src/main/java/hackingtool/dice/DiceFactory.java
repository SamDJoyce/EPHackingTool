package hackingtool.dice;

public class DiceFactory {

	private DiceFactory() {
	}
	
	public static Dice get(Types dieType) {
		return new Dice(dieType.get());
	}
	
}
