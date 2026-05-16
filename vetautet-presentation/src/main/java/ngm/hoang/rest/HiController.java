package ngm.hoang.rest;

import lombok.RequiredArgsConstructor;
import ngm.hoang.service.HiAppService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HiController {
    private final HiAppService hiAppService;
    @GetMapping
    public String sayHello() {
        return hiAppService.sayHi("Hoang");
    }
}