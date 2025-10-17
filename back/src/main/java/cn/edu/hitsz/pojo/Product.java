package cn.edu.hitsz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 货品表，记录商品信息和价格
 *
 * @TableName products
 */
@TableName(value = "products")
public class Product {
    /**
     * 主键，自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名称（唯一）
     */
    private String name;

    /**
     * 进货价
     */
    private BigDecimal purchasePrice;

    /**
     * 批发价
     */
    private BigDecimal wholesalePrice;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    public Product() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPurchasePrice() {
        return this.purchasePrice;
    }

    public BigDecimal getWholesalePrice() {
        return this.wholesalePrice;
    }

    public BigDecimal getRetailPrice() {
        return this.retailPrice;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Product)) return false;
        final Product other = (Product) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$purchasePrice = this.getPurchasePrice();
        final Object other$purchasePrice = other.getPurchasePrice();
        if (this$purchasePrice == null ? other$purchasePrice != null : !this$purchasePrice.equals(other$purchasePrice))
            return false;
        final Object this$wholesalePrice = this.getWholesalePrice();
        final Object other$wholesalePrice = other.getWholesalePrice();
        if (this$wholesalePrice == null ? other$wholesalePrice != null : !this$wholesalePrice.equals(other$wholesalePrice))
            return false;
        final Object this$retailPrice = this.getRetailPrice();
        final Object other$retailPrice = other.getRetailPrice();
        if (this$retailPrice == null ? other$retailPrice != null : !this$retailPrice.equals(other$retailPrice))
            return false;
        final Object this$createTime = this.getCreateTime();
        final Object other$createTime = other.getCreateTime();
        if (this$createTime == null ? other$createTime != null : !this$createTime.equals(other$createTime))
            return false;
        final Object this$updateTime = this.getUpdateTime();
        final Object other$updateTime = other.getUpdateTime();
        if (this$updateTime == null ? other$updateTime != null : !this$updateTime.equals(other$updateTime))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Product;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $purchasePrice = this.getPurchasePrice();
        result = result * PRIME + ($purchasePrice == null ? 43 : $purchasePrice.hashCode());
        final Object $wholesalePrice = this.getWholesalePrice();
        result = result * PRIME + ($wholesalePrice == null ? 43 : $wholesalePrice.hashCode());
        final Object $retailPrice = this.getRetailPrice();
        result = result * PRIME + ($retailPrice == null ? 43 : $retailPrice.hashCode());
        final Object $createTime = this.getCreateTime();
        result = result * PRIME + ($createTime == null ? 43 : $createTime.hashCode());
        final Object $updateTime = this.getUpdateTime();
        result = result * PRIME + ($updateTime == null ? 43 : $updateTime.hashCode());
        return result;
    }

    public String toString() {
        return "Product(id=" + this.getId() + ", name=" + this.getName() + ", purchasePrice=" + this.getPurchasePrice() + ", wholesalePrice=" + this.getWholesalePrice() + ", retailPrice=" + this.getRetailPrice() + ", createTime=" + this.getCreateTime() + ", updateTime=" + this.getUpdateTime() + ")";
    }
}