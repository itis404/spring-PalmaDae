package org.palmadae.donortrack.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.palmadae.donortrack.dto.UserDto;
import org.palmadae.donortrack.exception.custom.BusinessException;
import org.palmadae.donortrack.exception.custom.api.donorsearch.DonorSearchUnavailableException;
import org.palmadae.donortrack.exception.custom.email.EmailAlreadyExistsException;
import org.palmadae.donortrack.exception.custom.email.EmailCodeExpiredException;
import org.palmadae.donortrack.exception.custom.email.InvalidEmailCodeException;
import org.palmadae.donortrack.exception.custom.event.*;
import org.palmadae.donortrack.exception.custom.event.chat.EventChatIsNotActiveException;
import org.palmadae.donortrack.exception.custom.event.chat.EventChatIsNotApprovedException;
import org.palmadae.donortrack.exception.custom.event.chat.EventChatNotFoundExceptiion;
import org.palmadae.donortrack.exception.custom.event.chat.EventChatSecurityException;
import org.palmadae.donortrack.exception.custom.user.InvalidOldPasswordException;
import org.palmadae.donortrack.exception.custom.user.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        log.error("Необработанное исключение: ", e);

        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("exception", e);

        return modelAndView;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException e,
                                     RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/auth/login";
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailExists(EmailAlreadyExistsException e,
                                    RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/profile/edit";
    }

    @ExceptionHandler(BusinessException.class)
    public String handle(BusinessException e, HttpServletRequest request) {
        request.getSession().setAttribute("errorMessage", e.getMessage());
        return "redirect:/error";
    }

    @ExceptionHandler(DonorSearchUnavailableException.class)
    public String handleDonorSearch(DonorSearchUnavailableException e,
                                    RedirectAttributes ra) {

        ra.addFlashAttribute("errorMessage",
                "Сервис станций временно недоступен");

        return "redirect:/home";
    }

    @ExceptionHandler(InvalidEmailCodeException.class)
    public String handleInvalidCode(InvalidEmailCodeException e,
                                    Model model,
                                    HttpSession session) {

        model.addAttribute("error", e.getMessage());

        UserDto pendingUser = (UserDto) session.getAttribute("pendingUser");
        if (pendingUser != null) {
            model.addAttribute("pendingUser", pendingUser);
        }

        return "auth/confirm-code";
    }

    @ExceptionHandler(EmailCodeExpiredException.class)
    public String handleExpired(EmailCodeExpiredException e,
                                RedirectAttributes ra) {

        ra.addFlashAttribute("errorMessage", e.getMessage());

        return "redirect:/auth/registration";
    }

    @ExceptionHandler({
            EventNotFoundException.class,
            EventFullException.class,
            EventNotApprovedException.class,
            OrganizerCannotLeaveEventException.class,
            EventSecurityException.class
    })
    public String handleEvent(BusinessException e, RedirectAttributes ra) {

        ra.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/events/all";
    }

    @ExceptionHandler(value = {
            EventChatNotFoundExceptiion.class,
            EventChatIsNotActiveException.class,
            EventChatIsNotApprovedException.class,
            EventChatSecurityException.class
    })
    public String handleChat(BusinessException e, RedirectAttributes ra) {

        ra.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/events/all";
    }

    @ExceptionHandler(InvalidOldPasswordException.class)
    public String handlePassword(InvalidOldPasswordException e,
                                 RedirectAttributes ra) {

        ra.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/profile/edit";
    }
}
