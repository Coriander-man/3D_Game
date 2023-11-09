package DogFight;

import java.util.Iterator;

import org.jogamp.java3d.Behavior;
import org.jogamp.java3d.Bounds;
import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.WakeupCriterion;
import org.jogamp.java3d.WakeupOnCollisionEntry;
import org.jogamp.java3d.WakeupOnCollisionExit;

class CollisionDetector extends Behavior {
    private boolean inCollision = false;
    private TransformGroup planeTG;
    private BranchGroup bullets;
    private TransformGroup enemyTG;
    private BranchGroup enemyBullets;
    private WakeupOnCollisionEntry wEnter;
    private WakeupOnCollisionExit wExit;

    public CollisionDetector(TransformGroup planeTG, BranchGroup bullets, TransformGroup enemyTG, BranchGroup enemyBullets) {
        this.planeTG = planeTG;
        this.bullets = bullets;
        this.enemyTG = enemyTG;
        this.enemyBullets = enemyBullets;
        inCollision = false;
    }

    public void initialize() {
        wEnter = new WakeupOnCollisionEntry(planeTG,WakeupOnCollisionEntry.USE_BOUNDS);
        wExit = new WakeupOnCollisionExit(planeTG, WakeupOnCollisionExit.USE_BOUNDS);
        wakeupOn(wEnter);
    }

    public void processStimulus(Iterator<WakeupCriterion> iterator) {
        inCollision = !inCollision;
        if (inCollision) {
        	Bounds planeBound = planeTG.getBounds();
        	Bounds bulletBound = bullets.getBounds();
        	Bounds enemyBound = enemyTG.getBounds();
        	Bounds enemyBulletBound = enemyBullets.getBounds();
        	if (planeBound.intersect(enemyBulletBound)) {
        		DogFight.f1.takeDamage(10);
        	}
        	else if (planeBound.intersect(enemyBound)) {
        		DogFight.f1.explode();
        		DogFight.f2.explode();
        	} else if (!planeBound.intersect(bulletBound)) {
        		DogFight.f1.explode();
        	}
            wakeupOn(wExit);
        } else {
            wakeupOn(wEnter);
        }
    }
}