package model;

import physics.Vector;

public class Ball extends FallingItem implements Enemy {

    public Ball(Model parent, double _posX, double _posY) {
        super(parent, _posX, _posY, 100, 100);
        this.gravityVector = new Vector(0,0);
        imageName = "ball.png";
    }

    @Override
    public boolean tick(){
        this.goalVelocity.x = ((parent.getPlayer().getPosX() + (parent.getPlayer().getWidth() / 2) - (getPosX() + (getWidth() / 2))) / 100);
        this.goalVelocity.y = ((parent.getPlayer().getPosY() + (parent.getPlayer().getHeight() / 2) - (getPosY() + (getHeight() / 2))) / 100);;

        return super.tick();
    }    
}