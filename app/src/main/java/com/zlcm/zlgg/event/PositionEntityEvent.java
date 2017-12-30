package com.zlcm.zlgg.event;

import com.zlcm.zlgg.lib.PositionEntity;

import java.io.Serializable;

/**
 * Created by lizhe on 2017/12/26.
 * 类介绍：
 */

public class PositionEntityEvent implements Serializable{

    PositionEntity positionEntity;

    int code;

    public PositionEntityEvent(PositionEntity positionEntity, int code) {
        this.positionEntity = positionEntity;
        this.code = code;
    }

    public PositionEntity getPositionEntity() {
        return positionEntity;
    }

    public void setPositionEntity(PositionEntity positionEntity) {
        this.positionEntity = positionEntity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
