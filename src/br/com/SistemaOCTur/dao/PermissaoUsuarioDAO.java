/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.dao;

import br.com.SistemaOCTur.entity.Permissao;
import br.com.SistemaOCTur.entity.PermissaoUsuario;
import br.com.SistemaOCTur.entity.Usuario;
import br.com.SistemaOCTur.model.GenericaDAO;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class PermissaoUsuarioDAO extends GenericaDAO<PermissaoUsuario> {

    public PermissaoUsuario pegarPorUsuarioPermissao(Usuario usuario, Permissao permissao) {
        entity = (PermissaoUsuario) criteria.add(Restrictions.eq("usuario", usuario)).add(Restrictions.eq("permissao", permissao)).uniqueResult();
        session.close();
        return entity;
    }

}
