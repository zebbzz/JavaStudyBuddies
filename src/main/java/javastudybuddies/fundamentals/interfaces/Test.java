package javastudybuddies.fundamentals.interfaces;

import static javastudybuddies.fundamentals.interfaces.Test.Jump.AIR;

interface GeneralJumpable {
    default void jump() {
        System.out.println("Jumping");
    }
}

interface AirJumpable extends  GeneralJumpable {
    default void jump() {
        System.out.println("Jumping in air");
    }
}

interface WaterJumpable extends GeneralJumpable {
    default void jump() {
        System.out.println("Jumping in water");
    }
}

public class Test implements AirJumpable, WaterJumpable  {
    public enum Jump {AIR, WATER;}

    public static void main(String[] args)  {
        Test test = new Test();
        test.jump(Jump.WATER);
    }

    @Override
    public void jump()  {
        jump(AIR);
    }

    public void jump(Jump jump)  {
        if  (jump == AIR)  {
            AirJumpable.super.jump();
        }  else  {
            WaterJumpable.super.jump();
        }
    }
}