package org.seckill.entity;

import java.util.Date;

public class SuccessSecKilled {
    private Long seckillId;
    private String phone;
    private Short state;
    private Date createTime;
    private SecKill secKill;

    public SecKill getSecKill() {
        return secKill;
    }

    public void setSecKill(SecKill secKill) {
        this.secKill = secKill;
    }

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SuccessSecKilled{" +
                "seckillId=" + seckillId +
                ", phone='" + phone + '\'' +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
