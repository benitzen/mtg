package com.aa.mtg.randomgame;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class RandomGameController {

    private AtomicInteger atomicInteger = new AtomicInteger(1);

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String randomGame() {
        return "redirect:/ui/game/" + atomicInteger.incrementAndGet() / 2;
    }

}
