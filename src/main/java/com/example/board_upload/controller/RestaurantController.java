package com.example.board_upload.controller;

import com.example.board_upload.dto.PageRequestDTO;
import com.example.board_upload.dto.PageResponseDTO;
import com.example.board_upload.dto.RestaurantDTO;
import com.example.board_upload.service.RestaurantService;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/restaurant")
@Log4j2
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<RestaurantDTO> responseDTO = restaurantService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public void registerGET(){

    }

    @PostMapping("/register")
    public String registerPost(@Valid RestaurantDTO restaurantDTO,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("식당 업로드");

        if(bindingResult.hasErrors()) {
            log.info("has errors!");
            redirectAttributes.addFlashAttribute("error",bindingResult.getAllErrors());
            return "redirect:/restaurant/register";
        }

        log.info(restaurantDTO);

        Long rno = restaurantService.register(restaurantDTO);
        redirectAttributes.addFlashAttribute("result",rno);
        return "redirect:/restaurant/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long rno, PageRequestDTO pageRequestDTO, Model model) {
        RestaurantDTO restaurantDTO = restaurantService.readOne(rno);
        log.info(restaurantDTO);
        model.addAttribute("dto", restaurantDTO);
    }

    @PostMapping("/modify")
    public String modify(@Valid RestaurantDTO restaurantDTO,
                         BindingResult bindingResult,
                         PageRequestDTO pageRequestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("modify restaurant" + restaurantDTO );

        if (bindingResult.hasErrors()) {
            log.info("has errors!");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("rno", restaurantDTO.getRno());

            return "redirect:/restaurant/modify?"+link;
        }

        restaurantService.modify(restaurantDTO);
        redirectAttributes.addFlashAttribute("result","modified");
        redirectAttributes.addAttribute("rno", restaurantDTO.getRno());
        return "redirect:/restaurant/read";
    }

    @PostMapping("/remove")
    public String remove(Long rno, RedirectAttributes redirectAttributes) {
        log.info("remove restaurant"+rno);
        restaurantService.remove(rno);

        redirectAttributes.addFlashAttribute("result","removed");
        return "redirect:/restaurant/list";
    }







}
