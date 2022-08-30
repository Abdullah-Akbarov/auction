package controller;

import com.google.gson.Gson;
import dao.LotDao;
import dao.impl.LotDaoImpl;
import model.Lot;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/view-lots")
public class ViewLotsController extends HttpServlet {
    private final LotDao lotDao = LotDaoImpl.getLotDao();
    private final Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Lot> all = lotDao.getAll();
        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(all));
        resp.getWriter().close();
    }
}
