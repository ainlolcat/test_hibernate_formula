package org.ainlolcat.samples.hft.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by ANIN0811 on 10.03.2016.
 */
@Embeddable
public class RelationPK implements Serializable {
    @Column(name = "from_item_id")
    private BigInteger fromItemId;

    @Column(name = "to_item_id")
    private BigInteger toItemId;

    public RelationPK() {
    }

    public RelationPK(BigInteger fromItemId, BigInteger toItemId) {
        this.fromItemId = fromItemId;
        this.toItemId = toItemId;
    }

    public BigInteger getFromItemId() {
        return fromItemId;
    }

    public void setFromItemId(BigInteger fromItemId) {
        this.fromItemId = fromItemId;
    }

    public BigInteger getToItemId() {
        return toItemId;
    }

    public void setToItemId(BigInteger toItemId) {
        this.toItemId = toItemId;
    }
}
