package org.ainlolcat.samples.hft.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Created by ANIN0811 on 10.03.2016.
 */
@Entity
@Table(name = "test_storages")
public class Storage {

    @Id
    @Column(name = "storage_id")
    private BigInteger storageId;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = Item.class)
    @JoinColumn(name = "storage_id", updatable = false)
    @MapKey
    private List<Item> ietms;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = Relation.class)
    @JoinColumn(name = "storage_id", updatable = false)
    @Fetch(org.hibernate.annotations.FetchMode.SELECT)
    private List<Relation> relations;

    @ManyToMany()
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(formula =
            @JoinFormula(
                    value = "(select dep.from_item_id, dep.to_item_id from test_relations dep where dep.storage_id = ?)"
            )
        )
    })
    private Collection<Relation> extRelation;

    public Storage() {
    }

    public Storage(BigInteger storageId, String name) {
        this.storageId = storageId;
        this.name = name;
    }

    public BigInteger getStorageId() {
        return storageId;
    }

    public void setStorageId(BigInteger storageId) {
        this.storageId = storageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getIetms() {
        return ietms;
    }

    public void setIetms(List<Item> ietms) {
        this.ietms = ietms;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public Collection<Relation> getExtRelation() {
        return extRelation;
    }

    public void setExtRelation(Collection<Relation> extRelation) {
        this.extRelation = extRelation;
    }
}
