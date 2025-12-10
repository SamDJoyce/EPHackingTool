package hackingtool.dice;

public class DiceFactory {

	private DiceFactory() {
	}
	
	public static Dice get(Types dieType) {
		if (Types.PERCENTILE.equals(dieType)) {
			return new Dice(Types.PERCENTILE.get());
		}
		if (Types.D10.equals(dieType)) {
			return new Dice(Types.D10.get());
		}
		if (Types.D6.equals(dieType)) {
			return new Dice(Types.D6.get());
		}
		return new Dice(Types.PERCENTILE.get());
	}
	
}
