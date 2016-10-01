/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.dao;

import br.com.SistemaOCTur.entity.Evento;
import br.com.SistemaOCTur.entity.Salao;
import br.com.SistemaOCTur.model.GenericaDAO;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class EventoDAO extends GenericaDAO<Evento> {

    public boolean validar(Evento evento) {
        entitys = criteria.add(Restrictions.eq("salao", evento.getSalao()))
                .add(Restrictions.or(Restrictions.between("inicio", evento.getInicio(), evento.getFim()), Restrictions.between("fim", evento.getInicio(), evento.getFim()), Restrictions.or(Restrictions.and(Restrictions.ge("inicio", evento.getInicio()), Restrictions.le("fim", evento.getInicio())), Restrictions.and(Restrictions.le("inicio", evento.getFim()), Restrictions.ge("fim", evento.getFim()))))).list();
        session.close();
        return entitys.isEmpty();
    }

    public Evento pegarPorSalaoDia(Salao salao, Date time) {
        entity = (Evento) criteria.add(Restrictions.eq("salao", salao)).add(Restrictions.and(Restrictions.le("inicio", time), Restrictions.ge("fim", time))).uniqueResult();
        session.close();
        return entity;
    }

    public List<Evento> pegarEntreData(Date inicio, Date fim) {
        entitys = criteria.add(Restrictions.or(Restrictions.between("inicio", inicio, fim),Restrictions.between("fim", inicio, fim))).addOrder(Order.asc("salao")).list();
        session.close();
        return entitys;
    }

}
