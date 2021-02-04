/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stickfigure;

/**
 *
 * @author maeda
 */
public class CloseCombat {
    SecurityGuard guard;
    Sprite sprite;
    long last;
    public CloseCombat(SecurityGuard inGuard, Sprite inSprite){
        guard = inGuard;
        sprite = inSprite;
        getStartingPoint();

    }
    public void getStartingPoint(){
        RandomNumberGenerator i = new RandomNumberGenerator(10, 0, guard.goldMap.WIDTH-1);
        RandomNumberGenerator j = new RandomNumberGenerator(10, 0, guard.goldMap.HEIGHT-1);
        do{
            guard.x = i.getRandomNumber();
            guard.y = j.getRandomNumber();

        }while(guard.map.blocked((int)guard.x, (int)guard.y));
    }

    //chase
    public float incX(){
        int newX = (int) (guard.x + 1);
        if(guard.map.blocked(newX, (int)guard.y)){
            newX = (int) (guard.x - 2);
        }
        return newX;
    }

    public float incY(){
        int newY = (int) (guard.y + 1);
        if(guard.map.blocked((int)guard.x, newY)){
            newY = (int) (guard.y - 2);
        }
        return newY;
    }

    public float decX(){
        int newX = (int) (guard.x - 1);
        if(guard.map.blocked(newX, (int)guard.y)){
            newX = (int) (guard.x + 2);
        }
        return newX;
    }
    public float decY(){
        int newY = (int) (guard.y - 1);
        if(guard.map.blocked((int)guard.x, newY)){
            newY = (int) (guard.y + 2);
        }
        return newY;
    }

    public int getDistance(int thiefX, int thiefY, int guardX, int guardY){
        return (int) Math.sqrt(Math.pow(guardX-thiefX, 2)+Math.pow(guardY-thiefY, 2));
    }

    public void attack(){
        //System.out.println("yo");
        //int dFromThiefToGuard = getDistance((int)sprite.getX(), (int)sprite.getY(), (int)guard.x, (int)guard.y);
        /*long delta = (System.nanoTime() - last) / 1000000;
            last = System.nanoTime();
            // now this needs a bit of explaining. The amount of time
            // passed between rendering can vary quite alot. If we were
            // to move our player based on the normal delta it would
            // at times jump a long distance (if the delta value got really
            // high). So we divide the amount of time passed into segments
            // of 5 milliseconds and update based on that
            for (int i=0; i<delta/5; i++) {
                logic(5);
            }
            // after we've run through the segments if there is anything
            // left over we update for that
            if ((delta % 5) != 0) {
                logic(delta % 5);
            }*/
        if(sprite.getX()-guard.x>0){
            guard.x = decX();
        }else if(sprite.getX()-guard.x<0){
            guard.x = incX();
        }
        if(sprite.getY()-guard.y>0){
            guard.y = decY();
        }else if(sprite.getY()-guard.y<0){
            guard.y = incY();
        }


        if((int)(sprite.getX()-guard.x)==0 && (int)(sprite.getY()-guard.y)==0){
            System.out.println("ur eaten");
        }
        //guard.paint();
    }
     /*
        float dx = 0;
        float dy = 0;
        if(Math.abs(sprite.getX()-x)==1 && Math.abs(sprite.getX()-x)==1){
            //x = sprite.getX();
            //y = sprite.getY();
            move((x - sprite.getX()) *  0.003f,
                        (y - sprite.getY()) * 0.003f);
        }else {//if(Math.abs(sprite.getX()-x)<=10 && Math.abs(sprite.getY()-y)<=10){
            if(sprite.canFight){
                //the playe can fight and the guard must run away
                if(sprite.getX()-x>0 ){
                    //dx--;
                    moveLeft(Math.abs(sprite.getX()-x));
                }else if(sprite.getX()-x<0){
                    //dx++;
                    moveRight(Math.abs(sprite.getX()-x));
                }
                if(sprite.getY()-y>0 ){
                    //dy--;
                    moveDown(Math.abs(sprite.getY()-y));
                }else if(sprite.getY()-y<0){
                    //dy++;
                    moveUp(Math.abs(sprite.getY()-y));
                }

            }else if(!sprite.canFight){
                if(sprite.getX()-x>0 ){
                    //dx++;
                    moveRight(Math.abs(sprite.getX()-x));
                }else if(sprite.getX()-x<0 ){
                    //dx--;
                    moveLeft(Math.abs(sprite.getX()-x));
                }
                if(sprite.getY()-y>0 ){
                    //dy++;
                    moveUp(Math.abs(sprite.getY()-y));
                }else if(sprite.getY()-y<0 ){
                    //dy--;
                    moveDown(Math.abs(sprite.getY()-y));
                }
            }
        }
        /*if ((dx != 0) || (dy != 0)) {
            move(dx *  0.003f,
                        dy * 0.003f);
        }*
        if((int)(sprite.getX()-x)==0 && (int)(sprite.getY()-y)==0){
            System.out.println("ur eaten");
            //sprite.killGuard();
        }*/





    /*public void moveLeft(float dx){
        reset();
        left = true;
        for(int i=1; i<=dx; i++){
            if(map.blocked(-1 + x, 0 + y)){
                moveRight(dx-i );
                break;
            }
            move(-1 *  0.003f,
                        0 * 0.003f);
        }
    }

    public void moveRight(float dx){
        reset();
        right = true;
        for(int i=1; i<=dx; i++){
            if(map.blocked(1 + x, 0 + y)){
                moveLeft(dx-i );
                break;
            }
            move(1 *  0.003f,
                        0 * 0.003f);
        }
    }

    public void moveUp(float dy){
        reset();
        up = true;
        for(int i=1; i<=dy; i++){
            if(map.blocked(0 + x, 1 + y)){
                moveDown(dy-i );
                break;
            }
            move(0 *  0.003f,
                        1 * 0.003f);
        }

    }
    public void moveDown(float dy){
        reset();
        down = true;
        for(int i=1; i<=dy; i++){
            if(map.blocked(0 + x, -1 + y)){
                moveUp(dy-i );
                break;
            }
            move(0 *  0.003f,
                        -1 * 0.003f);
        }
    }
    public void reset(){
        up = down = left = right = false;
    }
     */

}
