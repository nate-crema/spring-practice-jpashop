package jpabook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute( "data", "hello!!" ); // model에 'data' attribute를 'hello!!'로 설정
        return "hello"; // 화면이름: hello
    }
}
