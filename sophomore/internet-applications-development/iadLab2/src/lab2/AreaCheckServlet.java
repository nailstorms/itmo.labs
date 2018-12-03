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
        if ((x<=0) && (y<=0) && ((pow(x,2) + pow(y,2))<=pow((r/2),2))) {return "Point is in the area";}
        if ((x>=0) && (y<=0) && (x<=r && (y>=(-r)))) {return "Point is in the area";}
        return "Point is not in the area";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            double x = Double.parseDouble(request.getParameter("x"));
            double y = Double.parseDouble(request.getParameter("y"));
            double r = Double.parseDouble(request.getParameter("r"));


        } catch (Exception exc) {

        }
    }

}
