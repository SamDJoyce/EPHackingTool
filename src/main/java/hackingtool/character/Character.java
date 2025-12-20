package hackingtool.character;

import java.util.ArrayList;
import java.util.List;

import hackingtool.devices.Account;
import hackingtool.devices.Device;
import hackingtool.devices.DeviceFactory;
import hackingtool.devices.IntruderStatus;
import hackingtool.devices.Privileges;
import hackingtool.devices.User;

public class Character {

	private String name;
	// ----- Ego fields-----
	// Aptitudes
	private int cog;
	private int inu;
	private int ref;
	private int sav;
	private int som;
	private int wil;
	
	// Special Stats
	private int psiRating;
	private int stress;
	private int traumas;
	
	// Derived Ego Stats
	private int luc;
	private int traumaThresh;
	private int insanityRating;
	private int init;
	private int infection;
	
	// Ego Pools
	private int insight;
	private int moxie;
	private int vigor;
	private int flex;
	
	// Components
	private Morph  morph;
	private Device meshDevice;
	private User   user;
	private List<Trait> traits;
	
	// Skill ratings
		// Active Skills
	private int athletics;
	private int deceive;
	private int fray;
	private int freefall;
	private int guns;
	private int hardware1;
	private int hardware2;
	private int hardware3;
	private int infiltrate;
	private int infosec;
	private int interfaceSkill;
	private int kinesics;
	private int medicine1;
	private int medicine2;
	private int medicine3;
	private int melee;
	private int perceive;
	private int persuade;
	private int pilot1;
	private int pilot2;
	private int pilot3;
	private int program;
	private int provoke;
	private int psi;
	private int research;
	private int survival;
		// Know Skills
	private int academics;
	private int arts;
	private int interests;
	private int profession;
		// Rep
	private int aRep;
	private int cRep;
	private int fRep;
	private int gRep;
	private int iRep;
	private int xRep;
	
	// Constructor
	public Character(String name, int cog, int inu, int ref, int sav, int som, int wil) {
		this.name = name;
		// Aptitudes
		this.cog = cog;
		this.inu = inu;
		this.ref = ref;
		this.sav = sav;
		this.som = som;
		this.wil = wil;
		// Derived
		this.luc 		    = wil * 2;
		this.traumaThresh   = luc/5;
		this.insanityRating = luc * 2;
		this.init		    = (ref + inu)/5;
		this.infection	    = psiRating * 10;
		// Create device, user, account, then put them together
		meshDevice =  DeviceFactory.create("host");
		user = new User(name, meshDevice.getFirewall(), infosec, 20);
		Account account = new Account.Builder()
									 .setUser(user)
									 .setStatus(IntruderStatus.HIDDEN)
									 .setPriv(Privileges.ADMIN)
									 .setDur(user.getDurability())
									 .build();
		meshDevice.addAccount(account);
		
		traits = new ArrayList<>();
	}
	
	// Getters/Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		// Aptitudes
	public int getCog() {
		return cog;
	}
	
	public int getCogCheck() {
		return cog*3;
	}

	public void setCog(int cog) {
		this.cog = cog;
	}

	public int getInu() {
		return inu;
	}
	
	public int getInuCheck() {
		return inu*3;
	}

	public void setInu(int inu) {
		this.inu = inu;
	}

	public int getRef() {
		return ref;
	}
	
	public int getRefCheck() {
		return ref*3;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public int getSav() {
		return sav;
	}
	
	public int getSavCheck() {
		return sav*3;
	}

	public void setSav(int sav) {
		this.sav = sav;
	}

	public int getSom() {
		return som;
	}
	
	public int getSomCheck() {
		return som*3;
	}

	public void setSom(int som) {
		this.som = som;
	}

	public int getWil() {
		return wil;
	}
	
	public int getWilCheck() {
		return wil*3;
	}

	public void setWil(int wil) {
		this.wil = wil;
	}
		// Special
	public int getPsiRating() {
		return psiRating;
	}

	public void setPsiRating(int psiRating) {
		this.psiRating = psiRating;
	}
		// Mental
	public int getStress() {
		return stress;
	}

	public void setStress(int stress) {
		this.stress = stress;
	}

	public int getTraumas() {
		return traumas;
	}

	public void setTraumas(int traumas) {
		this.traumas = traumas;
	}
		// Derived
	public int getLuc() {
		return luc;
	}

	public void setLuc(int luc) {
		this.luc = luc;
	}

	public int getTraumaThresh() {
		return traumaThresh;
	}

	public void setTraumaThresh(int traumaThresh) {
		this.traumaThresh = traumaThresh;
	}

	public int getInsanityRating() {
		return insanityRating;
	}

	public void setInsanityRating(int insanityRating) {
		this.insanityRating = insanityRating;
	}

	public int getInit() {
		return init;
	}

	public void setInit(int init) {
		this.init = init;
	}

	public int getInfection() {
		return infection;
	}

	public void setInfection(int infection) {
		this.infection = infection;
	}
		//Pools
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
		// Components
	public Morph getMorph() {
		return morph;
	}

	public void setMorph(Morph morph) {
		this.morph = morph;
	}

	public Device getMeshDevice() {
		return meshDevice;
	}

	public void setMeshDevice(Device meshDevice) {
		this.meshDevice = meshDevice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
		// Skills
			// Athletics
	public int getAthletics() {
		return athletics + som;
	}
	
	public int getAthleticsLevel() {
		return athletics;
	}

	public void setAthletics(int athletics) {
		this.athletics = athletics;
	}
			// Deceive
	public int getDeceive() {
		return deceive + sav;
	}
	
	public int getDeceiveLevel() {
		return deceive;
	}

	public void setDeceive(int deceive) {
		this.deceive = deceive;
	}
			// Fray
	public int getFray() {
		return fray + (ref*2);
	}

	public int getFrayLevel() {
		return fray;
	}
	
	public void setFray(int fray) {
		this.fray = fray;
	}
			// Freefall
	public int getFreefall() {
		return freefall + som;
	}
	
	public int getFreefallLevel() {
		return freefall;
	}

	public void setFreefall(int freefall) {
		this.freefall = freefall;
	}
			// Guns
	public int getGuns() {
		return guns + ref;
	}
	
	public int getGunsLevel() {
		return guns;
	}

	public void setGuns(int guns) {
		this.guns = guns;
	}
			//Hardware1
	public int getHardware1() {
		return hardware1 + cog;
	}
	
	public int getHardware1Level() {
		return hardware1;
	}

	public void setHardware1(int hardware1) {
		this.hardware1 = hardware1;
	}
			// Hardware2
	public int getHardware2() {
		return hardware2 + cog;
	}
	
	public int getHardware2Level() {
		return hardware2;
	}

	public void setHardware2(int hardware2) {
		this.hardware2 = hardware2;
	}
			// Hardware3
	public int getHardware3() {
		return hardware3 + cog;
	}
	
	public int getHardware3Level() {
		return hardware3;
	}
		
	public void setHardware3(int hardware3) {
		this.hardware3 = hardware3;
	}
			// Infiltrate
	public int getInfiltrate() {
		return infiltrate + ref;
	}
	
	public int getInfiltrateLevel() {
		return infiltrate;
	}

	public void setInfiltrate(int infiltrate) {
		this.infiltrate = infiltrate;
	}
			// Infosec
	public int getInfosec() {
		return infosec + cog;
	}
	
	public int getInfosecLevel() {
		return infosec;
	}

	public void setInfosec(int infosec) {
		this.infosec = infosec;
	}
			// Interface
	public int getInterface() {
		return interfaceSkill + cog;
	}
	
	public int getInterfaceLevel() {
		return interfaceSkill;
	}

	public void setInterface(int interfaceSkill) {
		this.interfaceSkill = interfaceSkill;
	}
			// Kinesics
	public int getKinesics() {
		return kinesics + sav;
	}
	
	public int getKinesicsLevel() {
		return kinesics;
	}

	public void setKinesics(int kinesics) {
		this.kinesics = kinesics;
	}
			// Medicine1
	public int getMedicine1() {
		return medicine1 + cog;
	}
	
	public int getMedicine1level() {
		return medicine1;
	}

	public void setMedicine1(int medicine1) {
		this.medicine1 = medicine1;
	}
			// Medicine2
	public int getMedicine2() {
		return medicine2 + cog;
	}
	
	public int getMedicine2Level() {
		return medicine2;
	}

	public void setMedicine2(int medicine2) {
		this.medicine2 = medicine2;
	}
			// Medicine3
	public int getMedicine3() {
		return medicine3 + cog;
	}
	
	public int getMedicine3Level() {
		return medicine3;
	}

	public void setMedicine3(int medicine3) {
		this.medicine3 = medicine3;
	}
			// Melee
	public int getMelee() {
		return melee + som;
	}
	
	public int getMeleeLevel() {
		return melee;
	}

	public void setMelee(int melee) {
		this.melee = melee;
	}
			// Perceive
	public int getPerceive() {
		return perceive + (inu*2);
	}
	
	public int getPerceivelevel() {
		return perceive;
	}

	public void setPerceive(int perceive) {
		this.perceive = perceive;
	}
			// Persuade
	public int getPersuade() {
		return persuade + sav;
	}
	
	public int getPersuadeLevel() {
		return persuade;
	}

	public void setPersuade(int persuade) {
		this.persuade = persuade;
	}
			// Pilot1
	public int getPilot1() {
		return pilot1 + ref;
	}
	
	public int getPilot1Level() {
		return pilot1;
	}

	public void setPilot1(int pilot1) {
		this.pilot1 = pilot1;
	}
			// Pilot2
	public int getPilot2() {
		return pilot2 + ref;
	}
	
	public int getPilot2Level() {
		return pilot2;
	}

	public void setPilot2(int pilot2) {
		this.pilot2 = pilot2;
	}
			// Pilot3
	public int getPilot3() {
		return pilot3 + ref;
	}
	
	public int getPilot3Level() {
		return pilot3;
	}

	public void setPilot3(int pilot3) {
		this.pilot3 = pilot3;
	}
			// Program
	public int getProgram() {
		return program + cog;
	}
	
	public int getProgramLevel() {
		return program;
	}

	public void setProgram(int program) {
		this.program = program;
	}
			// Provoke
	public int getProvoke() {
		return provoke + sav;
	}
	
	public int getProvokeLevel() {
		return provoke;
	}

	public void setProvoke(int provoke) {
		this.provoke = provoke;
	}
			// Psi
	public int getPsi() {
		return psi + wil;
	}
	
	public int getPsiLevel() {
		return psi;
	}

	public void setPsi(int psi) {
		this.psi = psi;
	}
			// Research
	public int getResearch() {
		return research + inu;
	}
	
	public int getResearchLevel() {
		return research;
	}

	public void setResearch(int research) {
		this.research = research;
	}
			// Survival
	public int getSurvival() {
		return survival + inu;
	}
	
	public int getSurvivalLevel() {
		return survival;
	}

	public void setSurvival(int survival) {
		this.survival = survival;
	}
		// Know Skills
			// Academics
	public int getAcademics() {
		return academics + cog;
	}
	
	public int getAcademicsLevel() {
		return academics;
	}

	public void setAcademics(int academics) {
		this.academics = academics;
	}
			// Arts
	public int getArts() {
		return arts + inu;
	}
	
	public int getArtsLevel() {
		return arts;
	}

	public void setArts(int arts) {
		this.arts = arts;
	}
			//Interest
	public int getInterests() {
		return interests + cog;
	}
	
	public int getInterestsLevel() {
		return interests;
	}

	public void setInterests(int interests) {
		this.interests = interests;
	}
			// Profession
	public int getProfession() {
		return profession + cog;
	}
	
	public int getProfessionLevel() {
		return profession;
	}

	public void setProfession(int profession) {
		this.profession = profession;
	}
	
		// Rep
	public int getaRep() {
		return aRep;
	}

	public void setaRep(int aRep) {
		this.aRep = aRep;
	}

	public int getcRep() {
		return cRep;
	}

	public void setcRep(int cRep) {
		this.cRep = cRep;
	}

	public int getfRep() {
		return fRep;
	}

	public void setfRep(int fRep) {
		this.fRep = fRep;
	}

	public int getgRep() {
		return gRep;
	}

	public void setgRep(int gRep) {
		this.gRep = gRep;
	}

	public int getiRep() {
		return iRep;
	}

	public void setiRep(int iRep) {
		this.iRep = iRep;
	}

	public int getxRep() {
		return xRep;
	}

	public void setxRep(int xRep) {
		this.xRep = xRep;
	}
	
	// Morph Pass-through methods
	public Boolean isBiomorph() {
		return morph.isBiomorph();
	}
	
	public String getMorphName() {
		return morph.getName();
	}
	
	public int getDur() {
		return morph.getDur();
	}

	public int getWoundthresh() {
		return morph.getWoundthresh();
	}
	
	public int getDeathRating() {
		return morph.getDeathRating();
	}

	public int getMorphInsight() {
		return morph.getInsight();
	}
	
	public int getMorphMoxie() {
		return morph.getMoxie();
	}
	
	public int getMorphVigor() {
		return morph.getVigor();
	}
	
	public int getMorphFlex() {
		return morph.getFlex();
	}
	
	public int getMove() {
		return morph.getMove();
	}
	
	public int getRun() {
		return morph.getRun();
	}
	
	public int getDamage() {
		return morph.getDamage();
	}
	
	public void setDamage(int damage) {
		morph.setDamage(damage);
	}
	
	public int getWounds() {
		return morph.getWounds();
	}

	public void setWounds(int wounds) {
		morph.setWounds(wounds);
	}
	
	public List<Trait> getMorphTraits(){
		return morph.getTraits();
	}
	
	public void setMorphTraits(List<Trait> traits) {
		morph.setTraits(traits);
	}
	
	public void addMorphTrait(Trait trait) {
		morph.addTrait(trait);
	}
	
	public void removeMorphTrait(Trait trait) {
		morph.removeTrait(trait);
	}
	
	public Boolean morphHasTrait(Trait trait) {
		return morph.hasTrait(trait);
	}
	
}
