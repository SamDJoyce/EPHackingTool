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
import hackingtool.services.HackingService;

/**
 * Servlet implementation class NodesServlet
 */
@WebServlet("/Nodes")
public class NodesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String NODES_JSP = "WEB-INF/views/DetectedNodes.jsp";
	private static final HackingService hackServ = new HackingDAO();
	

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NodesServlet() {
        super();
//        createTestDevices();
//        hackServ.createUser("Test User", 80, 80, 25);
//    	hackServ.createLink(8, 5);
    }
    
    private void createTestDevices() {
//    	for (int i = 0; i < 10; i++) {
//        	Device device = DeviceFactory.get("mote");
//        	device.setName(device.getName() + "-" + i);
//        	if (i == 5) device.setVisible(false);
//        	hackServ.createNode(device);
//        }

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setAttribute("nodes", hackServ.getAllNodes());
		request.setAttribute("user", hackServ.getUser(1));
		// Forward to Nodes.jsp view
		request.getRequestDispatcher(NODES_JSP)
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
