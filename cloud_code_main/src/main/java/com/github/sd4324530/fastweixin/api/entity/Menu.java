package com.github.sd4324530.fastweixin.api.entity;

import com.github.sd4324530.fastweixin.exception.WeixinException;

import java.util.List;

/**
 * �˵����󣬰������в˵���ť
 *
 * @author peiyu
 */
public class Menu extends BaseModel {

    /**
     * һ���˵��б����3��
     */
    private List<MenuButton> button;

    public List<MenuButton> getButton() {
        return button;
    }

    public void setButton(List<MenuButton> button) {
        if (null == button || button.size() > 3) {
            throw new WeixinException("���˵����3��");
        }
        this.button = button;
    }
}
