package hackingtool.character;

public class MorphFactory {

	private static final String OPERATOR 		= "operator";
	private static final String AGENT 			= "agent";
	private static final String IKON 			= "ikon";
	private static final String DIGIMORPH 		= "digimorph";
	private static final String FLEXBOT 		= "flexbot";
	private static final String REAPER 			= "reaper";
	private static final String ARACHNOID 		= "arachnoid";
	private static final String STEEL 			= "steel";
	private static final String SLITHEROID 		= "slitheroid";
	private static final String GALATEA 		= "galatea";
	private static final String SAVANT 			= "savant";
	private static final String SYNTH 			= "synth";
	private static final String SWARMANOID 		= "swarmanoid";
	private static final String DRAGONFLY 		= "dragonfly";
	private static final String SPARE 			= "spare";
	private static final String CASE 		    = "case";
	private static final String NEO_ORANGUTAN   = "neoOrangutan";
	private static final String NEO_OCTOPUS 	= "neoOctopus";
	private static final String NEO_GORILLA 	= "neoGorilla";
	private static final String NEO_NEANDERTHAL = "neoNeanderthal";
	private static final String NEO_CHIMP 		= "neoChimp";
	private static final String NEO_AVIAN 		= "neoAvian";
	private static final String SHAPER 			= "shaper";
	private static final String SECURITY_POD 	= "securityPod";
	private static final String PLEASURE_POD 	= "pleasurePod";
	private static final String NOVACRAB 		= "novacrab";
	private static final String WORKER_POD 		= "workerPod";
	private static final String BASIC_POD 		= "basicPod";
	private static final String REMADE 			= "remade";
	private static final String GHOST 			= "ghost";
	private static final String FURY 			= "fury";
	private static final String SYLPH 			= "sylph";
	private static final String OLYMPIAN 		= "olympian";
	private static final String MENTON 			= "menton";
	private static final String HIBERNOID 		= "hibernoid";
	private static final String FUTURA 			= "futura";
	private static final String BOUNCER 		= "bouncer";
	private static final String RUSTER			= "ruster";
	private static final String NEOTENIC 		= "neotenic";
	private static final String EXALT 			= "exalt";
	private static final String SPLICER 		= "splicer";
	private static final String FLAT 			= "flat";

	private MorphFactory() {
	}
	
	public static Morph get(String type) {
		// Biomorphs
		if (FLAT.equalsIgnoreCase(type)) {
			return new Morph(true,FLAT,0,30,30,0,0,0,0,4,20);
		} else if (SPLICER.equalsIgnoreCase(type)){
			return new Morph(true,SPLICER,1,90,30,0,0,0,1,4,20);
		} else if (EXALT.equalsIgnoreCase(type)){
			return new Morph(true,EXALT,2,70,35,1,1,1,0,4,20);
		} else if (NEOTENIC.equalsIgnoreCase(type)){
			return new Morph(true,NEOTENIC,2,50,30,2,1,1,0,4,12);
		} else if (RUSTER.equalsIgnoreCase(type)){
			return new Morph(true,RUSTER,3,70,35,0,1,1,1,4,20);
		} else if (BOUNCER.equalsIgnoreCase(type)){
			return new Morph(true,BOUNCER,4,60,35,1,0,1,2,4,12);
		} else if (FUTURA.equalsIgnoreCase(type)){
			return new Morph(true,FUTURA,4,25,35,2,4,1,0,4,20);
		} else if (HIBERNOID.equalsIgnoreCase(type)){
			return new Morph(true,HIBERNOID,4,70,35,1,1,0,2,4,20);
		} else if (MENTON.equalsIgnoreCase(type)){
			return new Morph(true,MENTON,4,60,35,3,1,1,1,4,20);
		} else if (OLYMPIAN.equalsIgnoreCase(type)){
			return new Morph(true,OLYMPIAN,4,60,40,1,1,3,1,4,20);
		} else if (SYLPH.equalsIgnoreCase(type)){
			return new Morph(true,SYLPH,4,60,30,1,3,1,1,4,20);
		} else if (FURY.equalsIgnoreCase(type)){
			return new Morph(true,FURY,6,40,50,1,1,4,2,4,20);
		} else if (GHOST.equalsIgnoreCase(type)){
			return new Morph(true,GHOST,6,40,45,2,1,3,2,4,20);
		} else if (REMADE.equalsIgnoreCase(type)){
			return new Morph(true,REMADE,7,30,45,2,2,2,2,4,20);
		// Pod Morphs
		} else if (BASIC_POD.equalsIgnoreCase(type)){
			return new Morph(true,BASIC_POD,1,80,30,0,0,1,0,4,20);
		} else if (WORKER_POD.equalsIgnoreCase(type)){
			return new Morph(true,WORKER_POD,3,70,35,0,0,2,1,4,20);
		} else if (NOVACRAB.equalsIgnoreCase(type)){
			return new Morph(true,NOVACRAB,4,50,45,0,0,3,0,4,20);
		} else if (PLEASURE_POD.equalsIgnoreCase(type)){
			return new Morph(true,PLEASURE_POD,4,70,30,0,3,0,0,4,20);
		} else if (SECURITY_POD.equalsIgnoreCase(type)){
			return new Morph(true,SECURITY_POD,5,60,35,1,0,2,1,4,20);
		} else if (SHAPER.equalsIgnoreCase(type)){
			return new Morph(true,SHAPER,5,40,35,1,2,0,0,4,20);
		// Uplift Morphs
		} else if (NEO_AVIAN.equalsIgnoreCase(type)){
			return new Morph(true,NEO_AVIAN,0,50,25,2,1,0,0,8,40);
		} else if (NEO_CHIMP.equalsIgnoreCase(type)){
			return new Morph(true,NEO_CHIMP,1,50,30,0,2,1,0,4,12);
		} else if (NEO_NEANDERTHAL.equalsIgnoreCase(type)){
			return new Morph(true,NEO_NEANDERTHAL,2,35,35,0,1,2,1,4,20);
		} else if (NEO_GORILLA.equalsIgnoreCase(type)){
			return new Morph(true,NEO_GORILLA,3,40,45,0,0,3,1,4,12);
		} else if (NEO_OCTOPUS.equalsIgnoreCase(type)){
			return new Morph(true,NEO_OCTOPUS,3,35,30,1,1,1,2,4,20);
		} else if (NEO_ORANGUTAN.equalsIgnoreCase(type)){
			return new Morph(true,NEO_ORANGUTAN,3,35,35,1,1,2,0,4,20);
		// Synth Morphs
		} else if (CASE.equalsIgnoreCase(type)){
			return new Morph(false,CASE,0,100,25,0,0,0,0,4,12);
		} else if (SPARE.equalsIgnoreCase(type)){
			return new Morph(false,SPARE,0,80,15,0,1,0,0,4,12);
		} else if (DRAGONFLY.equalsIgnoreCase(type)){
			return new Morph(false,DRAGONFLY,1,70,25,1,0,1,0,8,32);
		} else if (SWARMANOID.equalsIgnoreCase(type)){
			return new Morph(false,SWARMANOID,2,60,40,2,0,0,0,4,20);
		} else if (SYNTH.equalsIgnoreCase(type)){
			return new Morph(false,SYNTH,3,80,40,0,0,1,1,4,20);
		} else if (SAVANT.equalsIgnoreCase(type)){
			return new Morph(false,SAVANT,4,50,35,3,0,1,1,4,20);
		} else if (GALATEA.equalsIgnoreCase(type)){
			return new Morph(false,GALATEA,5,50,40,1,2,1,1,4,20);
		} else if (SLITHEROID.equalsIgnoreCase(type)){
			return new Morph(false,SLITHEROID,5,50,45,0,1,3,0,4,12);
		} else if (STEEL.equalsIgnoreCase(type)){
			return new Morph(false,STEEL,5,50,40,0,1,3,1,4,20);
		} else if (ARACHNOID.equalsIgnoreCase(type)){
			return new Morph(false,ARACHNOID,6,40,55,1,0,3,0,4,20);
		} else if (REAPER.equalsIgnoreCase(type)){
			return new Morph(false,REAPER,12,10,60,1,0,6,1,4,20);
		} else if (FLEXBOT.equalsIgnoreCase(type)){
			// TODO *****
			return null;
		// Infomorphs
		} else if (DIGIMORPH.equalsIgnoreCase(type)){
			return new Morph(false,DIGIMORPH,0,100,25,0,0,0,0,4,20);
		} else if (IKON.equalsIgnoreCase(type)){
			return new Morph(false,IKON,1,100,30,1,3,0,0,4,20);
		} else if (AGENT.equalsIgnoreCase(type)){
			return new Morph(false,AGENT,2,100,40,4,0,0,0,4,20);
		} else if (OPERATOR.equalsIgnoreCase(type)){
			return new Morph(false,OPERATOR,2,100,35,3,0,0,1,4,20);
		} else {
			return null;
		}
	}
	

}
