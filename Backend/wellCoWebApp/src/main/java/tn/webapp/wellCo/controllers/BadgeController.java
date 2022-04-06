package com.springheroes.wellco.controllers;


import com.springheroes.wellco.entities.Badge;
import com.springheroes.wellco.services.IBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/badge")

public class BadgeController {

    @Autowired
    IBadgeService badgeService ;

    @PostMapping("/add")
    @ResponseBody
    public Badge addBadge(@RequestBody Badge b ) {
        return badgeService.addBadge(b) ;

    }

    @PutMapping("/update")
    @ResponseBody
    public Badge updateBadge( @RequestBody Badge b ) {
        return badgeService.updateBadge(b) ;

    }

    @DeleteMapping("/delete/{idBadge}")
    @ResponseBody
    public void deleteBadge(@PathVariable("idBadge") int idBadge ) {
         badgeService.deleteBadge(idBadge);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Badge> listBadge( ) {
         return badgeService.ListOfBadge();
    }
}
