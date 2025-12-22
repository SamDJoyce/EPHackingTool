package hackingtool.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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
		String nodeParam = request.getParameter("targetID");
		String hackerParam = request.getParameter("hackerID");
		
		Hackable target = null;
		User     hacker = null;
		
		if (nodeParam != null) {
			int nodeID = Integer.parseInt(nodeParam);
			target = hackServ.getNode(nodeID);
		}
		if (hackerParam != null) {
			int hackerID = Integer.parseInt(hackerParam);
			hacker = hackServ.getUser(hackerID);
		}
		// Error
	    if (target == null || hacker == null) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
	                "Missing or invalid target/hacker");
	        return;
	    }
		
	    request.setAttribute("target", target);
	    request.setAttribute("hacker", hacker);
		
		request.getRequestDispatcher(HACKING_JSP)
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String  action;
		String  bruteForceParam;
		Boolean bruteForce = false;
		Boolean local;
		int targetID;
		int hackerID;
		Hackable target = null;
		User hacker = null;
		
		Hacking hack = new Hacking(target, hacker, bruteForce);
		hack.addObserver(logger);
		
		
		// Get action
		if (request.getParameter(ACTION) != null) {
			action = request.getParameter(ACTION);
		} else {
			action = "none";
		}
		// get brute force
		if (request.getParameter(BRUTE_FORCE) != null) {
			bruteForceParam = request.getParameter(BRUTE_FORCE);
			bruteForce = Boolean.valueOf(bruteForceParam);
		} 
		// get target
		if (request.getParameter("targetID") != null) {
			targetID = Integer.parseInt(request.getParameter("targetID"));
			target = hackServ.getNode(targetID);
		}
		// Get hacker
		if (request.getParameter("hackerID") != null) {
			hackerID = Integer.parseInt(request.getParameter("hackerID"));
			hacker = hackServ.getUser(hackerID);
		}
		
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
		
		request.setAttribute("test", hack.getTest());
		request.setAttribute("eventLog", logger.getEventLog());
		request.setAttribute("target", hack.getTarget());
		request.setAttribute("hacker", hack.getHacker());
		request.getRequestDispatcher(HACKING_JSP)
	       .forward(request, response);
	}

}
