package hackingtool.character;

import java.util.ArrayList;
import java.util.List;

public class Morph {
	
	// Fields
	private Boolean biomorph;
	private String  name;
	private int 	cost;
	private int 	avail;
	private int 	dur;
	private int 	woundthresh;
	private int 	deathRating;
	private int		insight;
	private int		moxie;
	private int		vigor;
	private int 	flex;
	private int		move;
	private int		run;
	private int		damage;
	private int		wounds;
	private List<Trait> traits;
	
	// Constructor
	public Morph(Boolean biomorph, String name, int cost, int avail, int dur,
				 int insight, int moxie, int vigor, int flex, int move, int run) {
		super();
		this.biomorph = biomorph;
		this.name = name;
		this.cost = cost;
		this.avail = avail;
		this.dur = dur;
		this.woundthresh = dur/5;
		this.deathRating = (int) Math.round(dur * (biomorph ? 1.5 : 2));
		this.insight = insight;
		this.moxie = moxie;
		this.vigor = vigor;
		this.flex = flex;
		this.move = move;
		this.run = run;
		this.traits = new ArrayList<>();
	}

	public Boolean isBiomorph() {
		return biomorph;
	}

	public void setBiomorph(Boolean biomorph) {
		this.biomorph = biomorph;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getAvail() {
		return avail;
	}

	public void setAvail(int avail) {
		this.avail = avail;
	}

	public int getDur() {
		return dur;
	}

	public void setDur(int dur) {
		this.dur = dur;
	}

	public int getWoundthresh() {
		return woundthresh;
	}

	public void setWoundthresh(int woundthresh) {
		this.woundthresh = woundthresh;
	}

	public int getDeathRating() {
		return deathRating;
	}

	public void setDeathRating(int deathRating) {
		this.deathRating = deathRating;
	}

	public int getInsight() {
		return insight;
	}

	public void setInsight(int insight) {
		this.insight = insight;
	}

	public int getMoxie() {
		return moxie;
	}

	public void setMoxie(int moxie) {
		this.moxie = moxie;
	}

	public int getVigor() {
		return vigor;
	}

	public void setVigor(int vigor) {
		this.vigor = vigor;
	}

	public int getFlex() {
		return flex;
	}

	public void setFlex(int flex) {
		this.flex = flex;
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	public int getRun() {
		return run;
	}

	public void setRun(int run) {
		this.run = run;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getWounds() {
		return wounds;
	}

	public void setWounds(int wounds) {
		this.wounds = wounds;
	}

	public List<Trait> getTraits() {
		return traits;
	}

	public void setTraits(List<Trait> traits) {
		this.traits = traits;
	}
	
	public void addTrait(Trait trait) {
		traits.add(trait);
	}
	
	public void removeTrait(Trait trait) {
		traits.remove(trait);
	}
	
	public Boolean hasTrait(Trait trait) {
		return traits.contains(trait);
	}

}
