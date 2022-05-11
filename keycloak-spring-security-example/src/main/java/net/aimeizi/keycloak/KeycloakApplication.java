package net.aimeizi.keycloak;

import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@SpringBootApplication
public class KeycloakApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(KeycloakApplication.class);
        application.setAdditionalProfiles("keycloak");
        application.run(args);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/admin")
    public String admin() {
        return "Admin";
    }

    @Secured("ROLE_USER")
    @GetMapping("/user")
    public String user() {
        return "User";
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/principal")
    public Object principal() {

        /**        获取当前用户信息的方式：
         *
         *         // 在Controller方法中传入Authentication对象
         *         authentication.getName();
         *         // 在Controller方法中传入HttpServletRequest对象
         *         request.getUserPrincipal().getName();
         *         // 通过SecurityContextHolder工具类获取
         *         SecurityContextHolder.getContext().getAuthentication().getName();
         *
         */

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 检查是否存在认证用户
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            // 获取角色
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                System.out.println(grantedAuthority.getAuthority());
            }
        }
        System.out.println(authentication.getName());
        Object principal = authentication.getPrincipal();
        System.out.println("principal = " + principal);
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        System.out.println("account = " + account);
        return SecurityUtils.getIDToken();
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(HttpServletRequest request) {
        try {
            request.logout();
            return "logout success";
        } catch (ServletException e) {
            return "logout fail";
        }
    }

}
