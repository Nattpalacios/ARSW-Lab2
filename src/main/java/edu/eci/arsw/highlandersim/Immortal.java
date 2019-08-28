package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback=null;
    
    private int health;
    
    private int defaultDamageValue;

    private final List<Immortal> immortalsPopulation;
    
    public static AtomicBoolean pausarHilo;
    
    private boolean vivo = true;
    
    private static final Object tieLock = new Object(); 
    
    private boolean running = true;
    
    private final String name;

    private final Random r = new Random(System.currentTimeMillis());


    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb, AtomicBoolean pausarHilo) {
        super(name);
        this.updateCallback=ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = health;
        this.defaultDamageValue=defaultDamageValue;
        this.pausarHilo = pausarHilo;
 
    }

    public void run() {

        while (running) {
        	
        	synchronized (pausarHilo) {
				while(pausarHilo.get()) {
					try {
						pausarHilo.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
					
			}
            Immortal im;
            
            synchronized (immortalsPopulation) {
            	int myIndex = immortalsPopulation.indexOf(this);

                int nextFighterIndex = r.nextInt(immortalsPopulation.size());

                //avoid self-fight
                if (nextFighterIndex == myIndex) {
                    nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
                }

                im = immortalsPopulation.get(nextFighterIndex);

                this.transferirVida(im);
			}

            

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
    
    public void setRunning(boolean running) {
    	this.running = running;
    }

    public void transferirVida(Immortal i2) {
    	Immortal im = this;
    	
    	class Helper {
    		public void fight(Immortal i2) {

    	        if (i2.getHealth() > 0) {
    	            i2.changeHealth(i2.getHealth() - defaultDamageValue);
    	            if(i2.getHealth() == 0) vivo = false;
    	            health += defaultDamageValue;
    	            updateCallback.processReport("Fight: " + this + " vs " + i2+"\n");
    	        } else {
    	            updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
    	        }

    	    }
    	}
    	int fromHash = System.identityHashCode(this);
		int toHash = System.identityHashCode(i2);
		
		if (fromHash < toHash) {
			synchronized (this) {
				synchronized (i2) {
					new Helper().fight(i2);
				}
			}
		} else if (fromHash > toHash) {
			synchronized (i2) {
				synchronized (this) {
					new Helper().fight(i2);
				}
			}
		} else {
			synchronized (tieLock) {
				synchronized (this) {
					synchronized (i2) {
						new Helper().fight(i2);
					}
				}
			}
		}
    }
    /*public void fight(Immortal i2) {

        if (i2.getHealth() > 0) {
            i2.changeHealth(i2.getHealth() - defaultDamageValue);
            this.health += defaultDamageValue;
            updateCallback.processReport("Fight: " + this + " vs " + i2+"\n");
        } else {
            updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
        }

    }*/

    public void changeHealth(int v) {
        health = v;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String toString() {

        return name + "[" + health + "]";
    }

}
