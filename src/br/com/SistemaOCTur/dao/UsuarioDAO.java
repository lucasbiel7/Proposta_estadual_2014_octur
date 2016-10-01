/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.dao;

import br.com.SistemaOCTur.entity.Cargo;
import br.com.SistemaOCTur.entity.Usuario;
import br.com.SistemaOCTur.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class UsuarioDAO extends GenericaDAO<Usuario> {

    public List<Usuario> pegarTodosPorCargo(Cargo cargo) {
        entitys = criteria.add(Restrictions.eq("cargo", cargo)).list();
        session.close();
        return entitys;
    }

    public List<Usuario> pegarTodosPorNaoCargo(Cargo cargo) {
        entitys = criteria.add(Restrictions.not(Restrictions.eq("cargo", cargo))).list();
        session.close();
        return entitys;
    }

    public List<Usuario> pegarTodosPorCargo(String nome, Cargo cargo) {
        entitys = criteria.add(Restrictions.like("nome", nome, MatchMode.START)).add(Restrictions.eq("cargo", cargo)).list();
        session.close();
        return entitys;
    }

    public List<Usuario> pegarTodosPorNaoCargo(String nome, Cargo cargo) {
        entitys = criteria.add(Restrictions.like("nome", nome, MatchMode.START)).add(Restrictions.not(Restrictions.eq("cargo", cargo))).list();
        session.close();
        return entitys;
    }

    public List<Usuario> pegarTodosPorNome(String nome) {
        entitys = criteria.add(Restrictions.like("nome", nome, MatchMode.START)).list();
        session.close();
        return entitys;
    }

    public Usuario login(String text) {
        entity = (Usuario) criteria.add(Restrictions.eq("nome", text)).uniqueResult();
        session.close();
        return entity;
    }

}
