package com.springheroes.wellco.controllers;


import com.springheroes.wellco.entities.Eval;
import com.springheroes.wellco.services.IEvalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eval")

public class EvalController {

    @Autowired
    IEvalService evalService;

    @PostMapping("/add")
    public Eval addEval(@RequestBody Eval e ) {
        return evalService.addEval(e) ;

    }

    @PutMapping("/update")
    public Eval updateEval(@RequestBody Eval e  ) {
        return evalService.updateEval(e) ;

    }

    @DeleteMapping("/delete/{idEval}")
    public void deleteEval(@PathVariable("idEval") int idEval ) {
        evalService.deleteEval(idEval);
    }

    @GetMapping("/all")
    public List<Eval> listEval( ) {
        return evalService.ListOfEval();
    }
}
