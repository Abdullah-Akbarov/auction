package component;

import com.google.gson.Gson;
import dao.LotDao;
import dao.impl.LotDaoImpl;
import model.Lot;
import model.Message;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter("/close-lot")
public class LotFilter implements Filter {
    private final Gson gson = new Gson();
    private final LotDao lotDao = LotDaoImpl.getLotDao();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("application/json");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String id = request.getParameter("id");
        Optional<Lot> byId = lotDao.getById(Integer.parseInt(id));
        if (byId.isPresent()){
            if (!byId.get().isActive()){
                response.getWriter().print(gson.toJson(new Message(403, "Lot already been closed", null)));
                response.getWriter().close();
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            response.getWriter().print(gson.toJson(new Message(404, "Not Found", null)));
            response.getWriter().close();
        }
    }
}
