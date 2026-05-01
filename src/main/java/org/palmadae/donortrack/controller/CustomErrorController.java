package org.palmadae.donortrack.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request,
                              HttpSession session,
                              Model model) {

        Object exceptionObj = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        Object sessionError = session.getAttribute("errorMessage");

        if (sessionError != null) {
            model.addAttribute("message", sessionError.toString());
            session.removeAttribute("errorMessage");
            return "error";
        }

        if (exceptionObj instanceof Exception ex) {
            model.addAttribute("exception", ex);
        }

        if (statusObj != null) {
            model.addAttribute("statusCode", statusObj.toString());
        }

        return "error";
    }
}