package org.ainlolcat.samples.hft.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * Created by ANIN0811 on 10.03.2016.
 */
@Entity
@Table(name = "test_relations")
public class Relation {
    @Column(name = "relation_id")
    private BigInteger relationId;

    @Column(name = "storage_id")
    private BigInteger storageId;

    @EmbeddedId
    private RelationPK pk;

    public BigInteger getRelationId() {
        return relationId;
    }

    public void setRelationId(BigInteger relationId) {
        this.relationId = relationId;
    }

    public RelationPK getPk() {
        return pk;
    }

    public void setPk(RelationPK pk) {
        this.pk = pk;
    }

    public BigInteger getStorageId() {
        return storageId;
    }

    public void setStorageId(BigInteger storageId) {
        this.storageId = storageId;
    }
}
