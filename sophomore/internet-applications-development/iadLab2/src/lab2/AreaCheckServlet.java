package lab2;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.pow;

@WebServlet("/areaCheck")
public class AreaCheckServlet extends HttpServlet {

    private static String checkAreaHit(double x, double y, double r) {
        if ((x<=0) && (y>=0) && (y<=(2*x+r))) {return "Point is in the area";}
        if ((x<=0) && (y<=0) && ((pow(x,2) + pow(y,2))<=pow(r,2))) {return "Point is in the area";}
        if ((x>=0) && (y<=0) && (x<=r && (y>=(-r)))) {return "Point is in the area";}
        return "Point is not in the area";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String res;
        String kx;
        String ky;
        String rad;
        String name;
        int getFl;
        String[] xk = {"-5","-4","-3","-2","-1","0","1","2","3"};
        getFl=0;
        response.setContentType("text/html");
        name = request.getParameter("name");
        kx = request.getParameter("koordX");
        request.setAttribute("X", kx);
        ky = request.getParameter("koordY");
        if ((ky!=null) && ky.contains(",")) {
            ky = ky.replace(",", ".");
        }
        request.setAttribute("Y", ky);
        rad = request.getParameter("radius");
        if ((rad!=null) && rad.contains(",")) {
            rad = rad.replace(",", ".");
        }
        request.setAttribute("RAD", rad);
        try {
            res = AreaCheckServlet.checkAreaHit(Double.parseDouble(kx), Double.parseDouble(ky), Double.parseDouble(rad));
            request.setAttribute("RESULT", res); }
        catch (NumberFormatException c) {
            kx = "--";
            ky = "--";
            rad = "--";
            res = "НЕ ВСЕ ПАРАМЕТРЫ ЧИСЛА";
            request.setAttribute("X",kx);
            request.setAttribute("Y",ky);
            request.setAttribute("RAD",rad);
            request.setAttribute("RESULT", res);
            getFl=1;
        }
        boolean containsX = Arrays.stream(xk).anyMatch(kx::equals);

        try {
            double yy = Double.parseDouble(ky);
            double rr = Double.parseDouble(rad);
            if (name.equals("TextForm") && !(((yy >= -5) && (yy <= 3)) && ((rr >= 2) && (rr <= 5)) && containsX)) {
                kx = "--";
                ky = "--";
                rad = "--";
                res = "НЕ НУЖНО МЕНЯТЬ КОД СТРАНИЦЫ";
                request.setAttribute("X", kx);
                request.setAttribute("Y", ky);
                request.setAttribute("RAD", rad);
                request.setAttribute("RESULT", res);
                getFl = 1;
            }
        } catch (NumberFormatException v ) {
            kx = "--";
            ky = "--";
            rad = "--";
            res = "НЕ НУЖНО МЕНЯТЬ КОД СТРАНИЦЫ";
            request.setAttribute("X", kx);
            request.setAttribute("Y", ky);
            request.setAttribute("RAD", rad);
            request.setAttribute("RESULT", res);
            getFl = 1;
        }
        ServletContext servletContext = getServletContext();
        ArrayList<Dot> newTr = (ArrayList<Dot>) servletContext.getAttribute("chTable");
        if (getFl==0) {
            Dot newDot = new Dot(kx,ky,rad,res);
            newTr.add(newDot);
        }
        request.getRequestDispatcher("checkTable.jsp").forward(request, response);

    }


}
