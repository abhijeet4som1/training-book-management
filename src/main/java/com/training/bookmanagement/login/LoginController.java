package com.training.bookmanagement.login;

import com.training.bookmanagement.util.common.AbstractBaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class LoginController extends AbstractBaseController {

    @PostMapping("/login")
    public ResponseEntity saveBooks(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        if ("test".equals(loginDto.getUserName())
                && "patanahi".equals(loginDto.getPassword())) {
            response.addCookie(new Cookie("access-token", "test-token"));
            return ok();
        }
        return failure("Username or password didn't match.");
    }
}
