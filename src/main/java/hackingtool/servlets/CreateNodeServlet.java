package hackingtool.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hackingtool.dao.HackingDAO;
import hackingtool.devices.Alerts;
import hackingtool.devices.Device;
import hackingtool.devices.DeviceFactory;
import hackingtool.devices.OS;
import hackingtool.devices.OSFactory;
import hackingtool.services.HackingService;

/**
 * Servlet implementation class CreateNodeServlet
 */
@WebServlet("/CreateNode")
public class CreateNodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final HackingService hackServ = new HackingDAO();
	private static final String CREATE_NODE_JSP = "WEB-INF/views/CreateNode.jsp";
	private static final String GMSCREEN = "GMScreen";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		request.getRequestDispatcher(CREATE_NODE_JSP)
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = null;
		
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		
		// Check Action
		if ("quickCreate".equalsIgnoreCase(action)) {
			// Create a new random Node of the chosen type
			if (request.getParameter("nodeType") != null) {
				String type = request.getParameter("nodeType");
				hackServ.createNode(DeviceFactory.get(type));
			}
		} else if ("fullCreate".equalsIgnoreCase(action) ) {
			String name		  = request.getParameter("nodeName") != null ? request.getParameter("nodeName") : "";
			String type		  = request.getParameter("nodeType") != null ? request.getParameter("nodeType") : "mote";
			String fwParam	  = request.getParameter("firewall");
			String infoParam  = request.getParameter("infosec");
			String armorParam = request.getParameter("armor");
			String durParam	  = request.getParameter("durability");
			String defParam   = request.getParameter("defended");
			String mindParam  = request.getParameter("mindware");
			String visParam	  = request.getParameter("visible");
			String alertParam = request.getParameter("alert");
			int	   firewall   = 0;
			int	   infosec    = 0;
			int	   armor      = 0;
			int	   durability = 0;
			Boolean defended  = false;
			Boolean mindware  = false;
			Boolean visible   = true;
			Alerts  alert     = Alerts.NONE;
			
			if (fwParam != null) {
				firewall = Integer.parseInt(fwParam);
			}
			if (infoParam != null) {
				infosec = Integer.parseInt(infoParam);
			}
			if (armorParam != null) {
				armor = Integer.parseInt(armorParam);
			}
			if (durParam != null) {
				durability = Integer.parseInt(durParam);
			}
			if (defParam != null) {
				defended = Boolean.valueOf(defParam);
			}
			if (mindParam != null) {
				mindware = Boolean.valueOf(mindParam);
			}
			if (visParam != null) {
				visible = Boolean.valueOf(visParam);
			}
			if (alertParam != null) {
				alert = Alerts.fromString(alertParam);
			}
			
			hackServ.createNode( new Device.Builder()
									  .setSystemName(name)
									  .setMindware(mindware)
									  .setDefended(defended)
									  .setVisible(visible)
									  .setAlert(alert)
									  .setOS(new OS(type,durability,armor,firewall,infosec,defended))
									  .build() );
			
		}
		
		request.getRequestDispatcher(GMSCREEN)
		.forward(request, response);
	}

}
