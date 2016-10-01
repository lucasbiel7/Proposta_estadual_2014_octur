/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.dao;

import br.com.SistemaOCTur.entity.Cliente;
import br.com.SistemaOCTur.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class ClienteDAO extends GenericaDAO<Cliente> {

    public List<Cliente> pesquisarPorNome(String text) {
        entitys = criteria.add(Restrictions.or(Restrictions.like("nome", text, MatchMode.ANYWHERE),Restrictions.like("sobrenome", text, MatchMode.ANYWHERE))).list();
        session.close();
        return entitys;
    }

}
