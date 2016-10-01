/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.dao;

import br.com.SistemaOCTur.entity.Produto;
import br.com.SistemaOCTur.entity.TipoProduto;
import br.com.SistemaOCTur.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class ProdutoDAO extends GenericaDAO<Produto> {

    public List<Produto> pegarPorCategoriaProduto(TipoProduto categoria) {
        entitys = criteria.add(Restrictions.eq("categoria", categoria)).list();
        session.close();
        return entitys;
    }

}
