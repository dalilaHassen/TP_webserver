package lab.mvc.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab.mvc.model.Person;

public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. Read the person's information sent by the HTML form
        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String birthDay  = request.getParameter("birthDay");
        String sex       = request.getParameter("sex");

        // 2. Create the bean (the Model)
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthDay(birthDay);
        person.setSex(sex);

        // 3. Add the bean to the request
        request.setAttribute("person", person);

        // 4. Choose the view: 30 years or more -> old.jsp, otherwise young.jsp
        String page = "/young.jsp";
        try {
            LocalDate birth = LocalDate.parse(birthDay, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            int age = Period.between(birth, LocalDate.now()).getYears();
            if (age >= 30) {
                page = "/old.jsp";
            }
        } catch (Exception e) {
            // invalid or missing date: keep young.jsp
        }

        // 5. Hand over to the JSP (the View)
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.include(request, response);
    }
}
