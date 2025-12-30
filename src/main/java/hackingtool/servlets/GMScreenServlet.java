package hackingtool.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import hackingtool.dao.HackingDAO;
import hackingtool.devices.Hackable;
import hackingtool.devices.User;
import hackingtool.services.HackingService;


/**
 * Servlet implementation class GMScreenServlet
 */
@WebServlet("/GMScreen")
public class GMScreenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String GM_JSP = "WEB-INF/views/GMScreen.jsp";
    private static final HackingService hackServ = new HackingDAO();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GMScreenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Hackable> nodes = hackServ.getAllNodes();
		List<User>     users = hackServ.getAllUsers();
		
		request.setAttribute("users", users);
		request.setAttribute("nodes", nodes);
		request.getRequestDispatcher(GM_JSP)
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String idParam = request.getParameter("nodeID");
		
		
		if (action != null) {
			// Check action
			if ("createNode".equalsIgnoreCase(action)) {
				
			} else if ("deleteNode".equalsIgnoreCase(action) && idParam != null) {
				int nodeID = Integer.parseInt(idParam);
				hackServ.deleteNode(nodeID);
			} else if ("deleteAllNodes".equalsIgnoreCase(action)) {
				List<Hackable> nodes = hackServ.getAllNodes();
				for (Hackable node : nodes) {
					hackServ.deleteNode(node.getID());
				}
			} else if ("createUser".equalsIgnoreCase(action)) {
				String userName   = request.getParameter("userName") != null ? request.getParameter("userName") : "";
				String fwParam    = request.getParameter("firewall");
				String infoParam  = request.getParameter("infosec");
				String durParam   = request.getParameter("durability");
				int	   firewall   = 0;
				int	   infosec    = 0;
				int    durability = 0;
				
				if (fwParam   != null
			    &&  infoParam != null
				&&  durParam  != null) {
						firewall = Integer.parseInt(fwParam);
						infosec = Integer.parseInt(infoParam);
						durability = Integer.parseInt(durParam);
						hackServ.createUser(userName, firewall, infosec, durability);
				}
			} else if ("deleteUser".equalsIgnoreCase(action)) {
				String userIDParam = request.getParameter("userID");
				int userID = 0;
				if (userIDParam != null) {
					userID = Integer.parseInt(userIDParam);
					hackServ.deleteUser(userID);
				}
			}
		}

		List<Hackable> nodes = hackServ.getAllNodes();
		List<User> users = hackServ.getAllUsers();
		
		request.setAttribute("users", users);
		request.setAttribute("nodes", nodes);
		request.getRequestDispatcher(GM_JSP)
		.forward(request, response);
	}

}
