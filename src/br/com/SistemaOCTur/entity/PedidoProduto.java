/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SistemaOCTur.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author OCTI01
 */
@Entity
public class PedidoProduto implements Serializable {

    @EmbeddedId
    private PedidoProdutoID id;
    private int quantidade;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date tempo;
    private double total;

    public PedidoProdutoID getId() {
        return id;
    }

    public void setId(PedidoProdutoID id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getTempo() {
        return tempo;
    }

    public void setTempo(Date tempo) {
        this.tempo = tempo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void calcularTotal() {
        total = 0;
        for (int i = 0; i < getQuantidade(); i++) {
            total += getId().getProduto().getValor();
        }
    }

    public void calcularTempo() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Time.valueOf("00:00:00"));
        Calendar produto = Calendar.getInstance();
        produto.setTime(id.getProduto().getTempo());
        for (int i = 0; i < getQuantidade(); i++) {
            calendar.add(Calendar.HOUR, produto.get(Calendar.HOUR));
            calendar.add(Calendar.MINUTE, produto.get(Calendar.MINUTE));
            calendar.add(Calendar.SECOND, produto.get(Calendar.SECOND));
        }
        setTempo(calendar.getTime());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PedidoProduto other = (PedidoProduto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public static class PedidoProdutoID implements Serializable{

        @ManyToOne
        private Pedido pedido;
        @ManyToOne
        private Produto produto;

        public Pedido getPedido() {
            return pedido;
        }

        public void setPedido(Pedido pedido) {
            this.pedido = pedido;
        }

        public Produto getProduto() {
            return produto;
        }

        public void setProduto(Produto produto) {
            this.produto = produto;
        }

        public PedidoProdutoID(Pedido pedido, Produto produto) {
            this.pedido = pedido;
            this.produto = produto;
        }

        public PedidoProdutoID() {
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 97 * hash + Objects.hashCode(this.pedido);
            hash = 97 * hash + Objects.hashCode(this.produto);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final PedidoProdutoID other = (PedidoProdutoID) obj;
            if (!Objects.equals(this.pedido, other.pedido)) {
                return false;
            }
            if (!Objects.equals(this.produto, other.produto)) {
                return false;
            }
            return true;
        }

    }

    @Override
    public String toString() {
        return "";
    }

}
