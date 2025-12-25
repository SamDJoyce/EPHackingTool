package hackingtool.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hackingtool.dao.HackingDAO;
import hackingtool.devices.Device;
import hackingtool.devices.DeviceFactory;
import hackingtool.devices.Hackable;
import hackingtool.devices.User;
import hackingtool.hacking.Hacking;
import hackingtool.logging.Logger;
import hackingtool.logging.Observer;
import hackingtool.services.HackingService;

/**
 * Servlet implementation class HackingServlet
 */
@WebServlet("/Hacking")
public class HackingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String MISSING_TARGET_HACKER = "Missing or invalid target/hacker";
	private static final String NONE 		   = "none";
	private static final String TEST 		   = "test";
	private static final String EVENT_LOG 	   = "eventLog";
	private static final String LINKED_NODES   = "linkedNodes";
	private static final String HACKER 	       = "hacker";
	private static final String TARGET 		   = "target";
	private static final String SNIFFED 	   = "sniffed";
	private static final String HACKER_ID 	   = "hackerID";
	private static final String TARGET_ID 	   = "targetID";
	private static final String LOCAL 		   = "local";
	private static final String MESH_ATTACK    = "meshAttack";
	private static final String SUBVERSION     = "subversion";
	private static final String IMPROVE_STATUS = "improveStatus";
	private static final String HACKING_JSP    = "WEB-INF/views/Hacking.jsp";
	private static final String BRUTE_FORCE    = "bruteForce";
	private static final String ACTION 	  	   = "action";
	private static final String INTRUSION 	   = "intrusion";
	
	private static final HackingService hackServ = new HackingDAO();
	
//	// Create example system for testing
//	private static final Device target = DeviceFactory.getRandom("Mote");
//	// Create example user for testing
//	private static final User hacker = new User("TestUser", 75, 75, 25);
	
	// Fields
	private Observer logger;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HackingServlet() {
        super();
        logger = new Logger();
    }
// 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nodeParam    = request.getParameter(TARGET_ID);
		String hackerParam  = request.getParameter(HACKER_ID);
		String sniffedParam = request.getParameter(SNIFFED);
		
		Hackable target = null;
		User     hacker = null;
		Boolean sniffed = false;
		
		if (nodeParam != null) {
			int nodeID = Integer.parseInt(nodeParam);
			target = hackServ.getNode(nodeID);
		}
		if (hackerParam != null) {
			int hackerID = Integer.parseInt(hackerParam);
			hacker = hackServ.getUser(hackerID);
		}
		// Get sniffed
		if (sniffedParam != null) {
			sniffed = Boolean.valueOf(request.getParameter(SNIFFED)) ;
		}
		// Error
	    if (target == null || hacker == null) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
	                MISSING_TARGET_HACKER);
	        return;
	    }
		request.setAttribute(SNIFFED, sniffed);
	    request.setAttribute(TARGET, target);
	    request.setAttribute(HACKER, hacker);
	    request.setAttribute(LINKED_NODES, resolveLinkedNodes(target));
		
		request.getRequestDispatcher(HACKING_JSP)
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String  action			= null;
		String  bruteForceParam = null;
		Boolean bruteForce 		= false;
		Boolean local 			= false;
		Boolean sniffed 		= false;
		int     targetID		= 0;
		int 	hackerID		= 0;
		Hackable target 		= null;
		User 	hacker 			= null;
		Hacking hack			= null;
		List<Hackable> linkedNodes = null;
		
		
		// Get action
		if (request.getParameter(ACTION) != null) {
			action = request.getParameter(ACTION);
		} else {
			action = NONE;
		}
		// get brute force
		if (request.getParameter(BRUTE_FORCE) != null) {
			bruteForceParam = request.getParameter(BRUTE_FORCE);
			bruteForce = Boolean.valueOf(bruteForceParam);
		} 
		// get target
		if (request.getParameter(TARGET_ID) != null) {
			targetID = Integer.parseInt(request.getParameter(TARGET_ID));
			target = hackServ.getNode(targetID);
			// get Linked Nodes
			linkedNodes = resolveLinkedNodes(target);
		}
		// Get hacker
		if (request.getParameter(HACKER_ID) != null) {
			hackerID = Integer.parseInt(request.getParameter(HACKER_ID));
			hacker = hackServ.getUser(hackerID);
		}
		// Get sniffed
		if (request.getAttribute(SNIFFED) != null) {
			sniffed = Boolean.valueOf(request.getParameter(SNIFFED)) ;
		}
		
		hack = new Hacking(target, hacker, bruteForce);
		hack.addObserver(logger);
		
		// Check action
		if (action != null && !action.isEmpty()) {
			hack = new Hacking(target, hacker, bruteForce);
	        hack.addObserver(logger);
			if (INTRUSION.equalsIgnoreCase(action)) {
				hack.intrusion();
			} else if (IMPROVE_STATUS.equalsIgnoreCase(action)) {
				hack.upgradeStatus();
			} else if (SUBVERSION.equalsIgnoreCase(action)) {
				hack.subvertSystem();
			} else if (MESH_ATTACK.equalsIgnoreCase(action)) {
				if (request.getParameter(LOCAL) != null) {
					local = Boolean.valueOf(request.getParameter(LOCAL)) ;
				} else {
					local = false;
				}
				hack.meshAttack(local);
			}
		}
		
		request.setAttribute(TEST, hack.getTest());
		request.setAttribute(EVENT_LOG, logger.getEventLog());
		request.setAttribute(TARGET, hack.getTarget());
		request.setAttribute(HACKER, hack.getHacker());
		request.setAttribute(LINKED_NODES, linkedNodes);
		request.setAttribute(SNIFFED, sniffed);
		request.getRequestDispatcher(HACKING_JSP)
	       .forward(request, response);
	}
	
	private List<Hackable> resolveLinkedNodes(Hackable target) {
	    List<Hackable> linkedNodes = new ArrayList<>();
	    if (target != null && target.getLinkedNodes() != null) {
	        for (Integer id : target.getLinkedNodes()) {
	        	linkedNodes.add(hackServ.getNode(id));
	        }
	    }
	    return linkedNodes;
	}

}
