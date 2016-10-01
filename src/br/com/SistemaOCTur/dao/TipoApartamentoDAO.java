/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.dao;

import br.com.SistemaOCTur.entity.TipoApartamento;
import br.com.SistemaOCTur.model.GenericaDAO;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class TipoApartamentoDAO extends GenericaDAO<TipoApartamento> {

    public TipoApartamento pegarPorNome(String nome) {
        entity = (TipoApartamento) criteria.add(Restrictions.eq("nome", nome)).uniqueResult();
        session.close();
        return entity;
    }

}
