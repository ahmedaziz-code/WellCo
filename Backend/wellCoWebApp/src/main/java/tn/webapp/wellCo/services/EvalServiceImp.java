package com.springheroes.wellco.services;

import com.springheroes.wellco.entities.Eval;
import com.springheroes.wellco.repositories.EvalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvalServiceImp implements IEvalService{
    @Autowired
    EvalRepo evalRepo ;

    @Override
    public Eval addEval(Eval e) {
        return evalRepo.save(e);
    }

    @Override
    public Eval updateEval(Eval eval) {

        return evalRepo.save(eval);
    }

    @Override
    public void deleteEval(int idEval) {
        Eval eval = evalRepo.findById(idEval).get();
        evalRepo.delete(eval);

    }

    @Override
    public List<Eval> ListOfEval() {
        return evalRepo.findAll();
    }
}
