/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.util;

import br.com.SistemaOCTur.entity.Evento;
import br.com.SistemaOCTur.entity.Salao;
import java.util.List;

/**
 *
 * @author OCTI01
 */
public class EventoMes {
    private Salao salao;
    private List<Evento> eventos;

    public Salao getSalao() {
        return salao;
    }

    public void setSalao(Salao salao) {
        this.salao = salao;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
    
}
