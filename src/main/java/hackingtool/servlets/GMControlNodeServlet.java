package hackingtool.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import hackingtool.dao.HackingDAO;
import hackingtool.devices.Account;
import hackingtool.devices.Alerts;
import hackingtool.devices.Hackable;
import hackingtool.services.HackingService;
import hackingtool.devices.IntruderStatus;
import hackingtool.devices.Privileges;
import hackingtool.devices.User;

/**
 * Servlet implementation class GMControlNodeServlet
 */
@WebServlet("/GMControlNode")
public class GMControlNodeServlet extends HttpServlet {
	private static final String UPDATE_ACCOUNT = "updateAccount";
	private static final String ACTION = "action";
	private static final String UPDATE_NODE = "updateNode";
	private static final String CONTROL_NODE_JSP = "WEB-INF/views/GMControlNode.jsp";
	private static final HackingService hackServ = new HackingDAO();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GMControlNodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("nodeID");
		String action = request.getParameter("action");
		Hackable node = null;
		int nodeID = 0;
	
		if (idParam != null) {
			nodeID = Integer.parseInt(idParam);
			node = hackServ.getNode(nodeID);
		}
		
		if (action != null) {
			if ("beginReboot".equalsIgnoreCase(action)) {
				// determine if mote, host, or server
				// mote, host take 1d6 TURNS to shutdown and same to reboot
				// serve takes 1d6 MINUTES to shutdown and same to reboot
			} else if ("completeReboot".equalsIgnoreCase(action)) {
				// Reset system and app damage and wounds
				// Users have to log back in
			}
		}
		
		List<User> users = hackServ.getAllUsers();
		request.setAttribute("users", users);
		request.setAttribute("node", node);
		request.getRequestDispatcher(CONTROL_NODE_JSP)
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Update Node Fields
		String idParam    = request.getParameter("nodeID");
		String nodeName	  = request.getParameter("nodeName") != null ? request.getParameter("nodeName") : "";
		String action	  = request.getParameter(ACTION) != null ? request.getParameter(ACTION) : "";
		String alertParam = request.getParameter("alert");
		String mindParam  = request.getParameter("mindware");
		String defParam   = request.getParameter("defended");
		String visParam   = request.getParameter("visible");
		String fwParam	  = request.getParameter("firewall");
		String infoParam  = request.getParameter("infosec");
		String nodeDamParam = request.getParameter("nodeDamage");
		String nodeWoundParam = request.getParameter("nodeWounds");
		
		int		nodeID	   = 0;
		int		firewall   = 0;
		int 	infosec    = 0;
		int 	nodeDamage = 0;
		int 	nodeWounds = 0;
		Alerts  alert    = null;
		Boolean mindware = false;
		Boolean defended = false;
		Boolean visible  = false;
		
		// Update Account Fields
		String accIDParam     = request.getParameter("accID");
		String accUserIDParam = request.getParameter("accUser");
		String statusParam    = request.getParameter("accStatus");
		String privParam      = request.getParameter("accPriv");
		String accDamParam    = request.getParameter("accDamage");
		String accWoundsParam = request.getParameter("accWounds");
		
		int    accID     = 0;
		int    accUserID = 0;
		int    accDamage = 0;
		int	   accWounds = 0;
		IntruderStatus accStatus  = IntruderStatus.COVERT;
		Privileges     accPriv = Privileges.PUBLIC;
		
		Hackable node = null;
		
		// Parse Parameters
		if (alertParam != null) {
			alert = Alerts.fromString(alertParam);
		}
		if (mindParam != null) {
			mindware = Boolean.valueOf(mindParam);
		}
		if (defParam != null) {
			defended = Boolean.valueOf(defParam);
		}
		if (visParam != null) {
			visible = Boolean.valueOf(visParam);
		}
		if (fwParam != null) {
			firewall = Integer.parseInt(fwParam);
		}
		if (infoParam != null) {
			infosec = Integer.parseInt(infoParam);
		}
		if (nodeDamParam != null) {
			nodeDamage = Integer.parseInt(nodeDamParam);
		}
		if (nodeWoundParam != null) {
			nodeWounds = Integer.parseInt(nodeWoundParam);
		}
		if (accIDParam != null) {
			accID = Integer.parseInt(accIDParam);
		}
		if (statusParam != null) {
			accStatus = IntruderStatus.fromString(statusParam);
		}
		if (privParam != null) {
			accPriv = Privileges.fromString(privParam);
		}
		if (accDamParam != null) {
			accDamage = Integer.parseInt(accDamParam);
		}
		if (accWoundsParam != null) {
			accWounds = Integer.parseInt(accWoundsParam);
		}
		if (accUserIDParam != null) {
			accUserID = Integer.parseInt(accUserIDParam);
		}
		
		// Actions
		if (idParam != null) {
			nodeID = Integer.parseInt(idParam);
			if (UPDATE_NODE.equalsIgnoreCase(action)) {
				node = hackServ.getNode(nodeID);
				node.setName(nodeName);
				node.setFirewall(firewall);
				node.setInfosec(infosec);
				node.setDamage(nodeDamage);
				node.setWounds(nodeWounds);
				node.setAlert(alert);
				node.setMindware(mindware);
				node.setDefended(defended);
				node.setVisible(visible);
				hackServ.updateNode(node);
			} else if (UPDATE_ACCOUNT.equalsIgnoreCase(action)) {

				Account account = hackServ.getAccount(accID);
				account.setStatus(accStatus);
				account.setPriv(accPriv);
				account.setDamage(accDamage);
				account.setWounds(accWounds);
				// Update the account
				hackServ.updateAccount(account);
			} else if ("deleteAccount".equalsIgnoreCase(action)) {
				if (accIDParam != null) {
					accID = Integer.parseInt(accIDParam);
				}
				hackServ.deleteAccount(accID);
			} else if ("createAccount".equalsIgnoreCase(action)) {
				hackServ.createAccount(new Account.Builder()
												  .setUser(hackServ.getUser(accUserID))
												  .setDeviceID(nodeID)
												  .setStatus(accStatus)
												  .setPriv(accPriv)
												  .setDur(hackServ.getUser(accUserID).getDurability())
												  .build() );
			}
		}
		List<User> users = hackServ.getAllUsers();
		node = hackServ.getNode(nodeID);
		request.setAttribute("node", node);
		request.setAttribute("users", users);
		request.getRequestDispatcher(CONTROL_NODE_JSP)
		.forward(request, response);
	}

}
