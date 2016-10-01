/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.dao;

import br.com.SistemaOCTur.entity.Pedido;
import br.com.SistemaOCTur.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class PedidoDAO extends GenericaDAO<Pedido> {

    public List<Pedido> pegarTodosPorApartamento(int apartamento) {
        entitys = criteria.add(Restrictions.eq("apartamento", apartamento)).list();
        session.close();
        return entitys;
    }

}
