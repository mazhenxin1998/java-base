package com.mzx.actualcombat;

/**
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦? 如梦之梦.
 * @create 2020-09-17 09:29 周四.
 */
public class Apple {

    private Integer weight;
    private String color;

    public Apple(){

    }

    public Apple(Integer weight, String color){

        this.weight = weight;
        this.color = color;

    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
