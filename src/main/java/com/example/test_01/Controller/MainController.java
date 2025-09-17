package com.example.test_01.Controller;

import com.example.test_01.DTO.TestDTO;
import com.example.test_01.Entity.TestEntity;
import com.example.test_01.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    TestService service;

    @GetMapping(value = "/")
    public String main(){
        return "main";
    }

    @GetMapping(value = "/input")
    public String input01(TestDTO testDTO, Model mo){
        mo.addAttribute("testDTO", testDTO);
        return "testinput";
    }

    @PostMapping(value = "/save")
    public String save01(TestDTO dto) {
        service.save(dto);
        return "redirect:/";
    }

    @GetMapping(value = "/output")
    public String output01(Model mo){
        List<TestEntity> list = service.out();
        mo.addAttribute("list", list);
        return "testoutput";
    }

    @GetMapping(value = "/update")
    public String update01(@RequestParam("num") long num, Model mo) {
        TestEntity entity = service.update(num);
        mo.addAttribute("update", entity);

        return "testupdate";
    }

    @PostMapping(value = "/update_save")
    public String update01_2(@ModelAttribute("update") TestEntity entity) {
        service.update_save(entity);

        return "redirect:/output";
    }

    @GetMapping(value = "/delete")
    public String delete01(@RequestParam("num") long num, Model mo) {
        service.delete(num);

        return "redirect:/output";
    }
}
