/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.model;

import br.com.SistemaOCTur.control.Banco;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author OCTI01
 * @param <Entity>
 */
public class GenericaDAO<Entity> {

    protected Session session;
    protected Entity entity;
    protected List<Entity> entitys;
    protected Class<Entity> classe;
    protected Criteria criteria;

    public GenericaDAO() {
        session = Banco.getSessionFactory().openSession();
        session.beginTransaction();
        classe = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        criteria = session.createCriteria(classe);
    }

    public void cadastrar(Entity entity) {
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    public void editar(Entity entity) {
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    public void excluir(Entity entity) {
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    public List<Entity> pegarTodos() {
        entitys = criteria.list();
        session.close();
        return entitys;
    }

    public Entity pegarPorId(Serializable serializable) {
        entity = (Entity) session.get(classe, serializable);
        session.close();
        return entity;
    }
}
