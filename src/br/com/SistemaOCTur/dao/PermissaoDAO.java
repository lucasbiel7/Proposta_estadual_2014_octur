/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.dao;

import br.com.SistemaOCTur.entity.Modulo;
import br.com.SistemaOCTur.entity.Permissao;
import br.com.SistemaOCTur.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class PermissaoDAO extends GenericaDAO<Permissao> {

    public List<Permissao> pegarTodosPorModulo(Modulo modulo) {
        entitys = criteria.add(Restrictions.eq("modulo", modulo)).list();
        session.close();
        return entitys;
    }
    
}
