package org.ainlolcat.samples.hft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * Created by ANIN0811 on 10.03.2016.
 */
@Entity
@Table(name = "test_items")
public class Item {

    @Id
    @Column(name = "item_id")
    private BigInteger itemId;

    private String name;

    @Column(name = "storage_id")
    private BigInteger storageId;

    public Item() {
    }

    public Item(BigInteger itemId, String name) {
        this.itemId = itemId;
        this.name = name;
    }

    public BigInteger getItemId() {
        return itemId;
    }

    public void setItemId(BigInteger itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getStorageId() {
        return storageId;
    }

    public void setStorageId(BigInteger storageId) {
        this.storageId = storageId;
    }
}
