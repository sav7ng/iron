package run.aquan.iron.system.controller.admin;

import io.swagger.annotations.Api;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.aquan.iron.system.core.Result;
import run.aquan.iron.system.core.ResultResponse;

/**
 * @Class WebsocketController
 * @Description TODO
 * @Author Aquan
 * @Date 2020/3/24 12:10
 * @Version 1.0
 **/
@Api(tags = "消息提醒模块")
@RestController
@RequestMapping("/api/admin/message")
public class WebsocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebsocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("send")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    // @SendTo("/topic/subscribe")// 如果服务器接受到了消息，就会对订阅了 @SendTo 括号中的地址的浏览器发送消息。
    public Result send(@RequestParam(value = "message") String message) {
        // 广播
        messagingTemplate.convertAndSend("/topic/subscribe", message);
        // 通过用户ID实现点对点
        // messagingTemplate.convertAndSendToUser("aquan","/queue/subscribe", "您收到了新的消息");
        return ResultResponse.genSuccessResult("发送广播成功！");
    }

}

