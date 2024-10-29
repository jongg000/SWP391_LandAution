package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.Service.UserService;
import com.se1858.G5.LandAuction.DTO.UsersChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserChatController {

    private final UserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public UsersChatDTO addUser(
            @Payload UsersChatDTO users,
            HttpServletRequest request,
            Authentication authentication
    ) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        UsersChatDTO usersChatDTO = new UsersChatDTO(username, username);
        return usersChatDTO;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public UsersChatDTO disconnectUser(
            @Payload UsersChatDTO users
    ) {

        return users;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UsersChatDTO>> findConnectedUsers(@RequestParam(required = false) String nickname, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        return ResponseEntity.ok(userService.findConnectedUsers(username));
    }
}
