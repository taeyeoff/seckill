package org.seckill.dto;

import org.seckill.entity.SecKill;
import org.seckill.entity.SuccessSecKilled;
import org.seckill.enums.SecKillStateEnum;

public class SecKillExecution {
    private long secKillId;
    private int state;
    private String stateInfo;
    private SuccessSecKilled successSecKilled;

    public SecKillExecution(long secKillId, SecKillStateEnum stat, SuccessSecKilled successSecKilled) {
        this.secKillId = secKillId;
        this.state = stat.getState();
        this.stateInfo = stat.getStateInfo();
        this.successSecKilled = successSecKilled;
    }

    public SecKillExecution(long secKillId,SecKillStateEnum stat ) {
        this.secKillId = secKillId;
        this.state = stat.getState();
        this.stateInfo = stat.getStateInfo();
    }

    public long getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(long secKillId) {
        this.secKillId = secKillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessSecKilled getSuccessSecKilled() {
        return successSecKilled;
    }

    public void setSuccessSecKilled(SuccessSecKilled successSecKilled) {
        this.successSecKilled = successSecKilled;
    }

    @Override
    public String toString() {
        return "SecKillExecution{" +
                "secKillId=" + secKillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successSecKilled=" + successSecKilled +
                '}';
    }
}
