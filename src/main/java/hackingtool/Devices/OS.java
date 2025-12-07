package hackingtool.Devices;

public class OS {

    private int woundThresh;
    private int durability;
    private int deathRating;

    public OS(int durability) {
		this.durability = durability;
		this.woundThresh = durability/5;
		this.deathRating = durability*2;
	}
    	
    public int getWoundThresh() {
		return woundThresh;
	}

	public void setWoundThresh(int woundThresh) {
		this.woundThresh = woundThresh;
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public int getDeathRating() {
		return deathRating;
	}

	public void setDeathRating(int deathRating) {
		this.deathRating = deathRating;
	}



	
	

}
