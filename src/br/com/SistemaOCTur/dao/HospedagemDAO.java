/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.dao;

import br.com.SistemaOCTur.entity.Cliente;
import br.com.SistemaOCTur.entity.Hospedagem;
import br.com.SistemaOCTur.entity.TipoApartamento;
import br.com.SistemaOCTur.model.GenericaDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class HospedagemDAO extends GenericaDAO<Hospedagem> {

    public List<Hospedagem> pesquisarPorNome(String text) {
        List<Cliente> clientes = new ClienteDAO().pesquisarPorNome(text);
        if (clientes.isEmpty()) {
            session.close();
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.in("cliente", clientes)).list();
        session.close();
        return entitys;
    }

    public List<Hospedagem> filtrar(Date entrada, String status) {
        if (entrada != null) {
            criteria.add(Restrictions.eq("entrada", entrada));
        }
        if (status != null && !status.isEmpty()) {
            criteria.add(Restrictions.eq("status", status));
        }
        entitys = criteria.list();
        session.close();
        return entitys;
    }

    public List<Hospedagem> pegarEntreODia(Date data) {
        entitys = criteria.add(Restrictions.le("entrada", data)).add(Restrictions.ge("saida", data)).list();
        session.close();
        return entitys;
    }

    public List<Hospedagem> pegarEntreODia(Date data, int apartamento) {
        entitys = criteria.add(Restrictions.eq("apartamento", apartamento)).add(Restrictions.le("entrada", data)).add(Restrictions.ge("saida", data)).list();
        session.close();
        return entitys;
    }

    public List<Hospedagem> pegarCheckIn(Date data) {
        entitys = criteria.add(Restrictions.eq("entrada", data)).list();
        session.close();
        return entitys;
    }

    public List<Hospedagem> pegarCheckOut(Date data) {
        entitys = criteria.add(Restrictions.eq("saida", data)).list();
        session.close();
        return entitys;
    }

    public List<Hospedagem> pegarTodosEntre(Date inicio, Date fim) {
        entitys = criteria.add(Restrictions.or(Restrictions.between("entrada", inicio, fim), Restrictions.between("saida", inicio, fim))).list();
        session.close();
        return entitys;
    }

    public List<Hospedagem> pegarPorTipoApartamento(TipoApartamento tipoApartamento, Date inicio, Date fim) {
        entitys = criteria.add(Restrictions.eq("tipoApartamento", tipoApartamento.getNome())).add(Restrictions.or(Restrictions.between("entrada", inicio, fim), Restrictions.between("saida", inicio, fim))).list();
        session.close();
        return entitys;
    }

}
