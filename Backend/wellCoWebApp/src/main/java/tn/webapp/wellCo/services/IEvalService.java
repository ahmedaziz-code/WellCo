package com.springheroes.wellco.services;

import com.springheroes.wellco.entities.Eval;

import java.util.List;


public interface IEvalService {

    Eval addEval(Eval e);
    Eval updateEval(Eval e);
    void deleteEval(int idEval);
    List<Eval> ListOfEval();

}
