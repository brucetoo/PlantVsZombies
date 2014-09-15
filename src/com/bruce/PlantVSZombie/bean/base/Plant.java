package com.bruce.PlantVSZombie.bean.base;

/**
 * Created by Bruce
 * Data 2014/8/26
 * Time 15:00.
 */
public abstract class Plant extends BaseSprite {

    protected int life = 100; //生命值
    protected int line;// 行号
    protected int row;// 列号

    protected Plant(String filepath) {
        super(filepath);
        setScale(0.65);
        setAnchorPoint(0.5f, 0);//放在中间
    }

    /**
     * 被攻击
     */
    public void attacked(int attack) {
        life -= attack;
        if (life <= 0) {
            destroy();
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
