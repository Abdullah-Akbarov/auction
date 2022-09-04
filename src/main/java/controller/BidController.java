package controller;

import com.google.gson.Gson;
import model.Message;
import model.User;
import service.BidService;
import service.impl.BidServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/add-bid")
public class BidController extends HttpServlet {
    private final BidService bidService = new BidServiceImpl();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("bidder");
        String lotId = req.getParameter("id");
        String bid = req.getParameter("bid");

        Message message = bidService.saveBid(Integer.parseInt(lotId), Double.parseDouble(bid), user);
        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(message));
        resp.getWriter().close();
    }
}
