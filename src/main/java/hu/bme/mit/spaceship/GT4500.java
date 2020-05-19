package hu.bme.mit.spaceship;

//Ez egy teszt a konfliktushoz.
/**
 * A simple spaceship with two proton torpedo stores and four lasers
 */
public class GT4500 implements SpaceShip {

  private final TorpedoStore primaryTorpedoStore;
  private final TorpedoStore secondaryTorpedoStore;

  private boolean wasPrimaryFiredLast = false;

  public GT4500(final TorpedoStore ts1, final TorpedoStore ts2) {
    this.primaryTorpedoStore = ts1;
    this.secondaryTorpedoStore = ts2;
  }

  public boolean fireLaser(final FiringMode firingMode) {
    // TODO not implemented yet
    return false;
  }

  /**
  * Tries to fire the torpedo stores of the ship.
  *
  * @param firingMode how many torpedo bays to fire
  * 	SINGLE: fires only one of the bays.
  * 			- For the first time the primary store is fired.
  * 			- To give some cooling time to the torpedo stores, torpedo stores are fired alternating.
  * 			- But if the store next in line is empty, the ship tries to fire the other store.
  * 			- If the fired store reports a failure, the ship does not try to fire the other one.
  * 	ALL:	tries to fire both of the torpedo stores.
  *
  * @return whether at least one torpedo was fired successfully
  */
  @Override
  public boolean fireTorpedo(final FiringMode firingMode) {

    boolean firingSuccess = false;

    if (firingMode == FiringMode.SINGLE) {
        if (wasPrimaryFiredLast) {
          // try to fire the secondary first
          if (! secondaryTorpedoStore.isEmpty()) {
            firingSuccess = secondaryTorpedoStore.fire(1);
            wasPrimaryFiredLast = false;
          }
          else {
            // although primary was fired last time, but the secondary is empty
            // thus try to fire primary again
            if (! primaryTorpedoStore.isEmpty()) {
              firingSuccess = primaryTorpedoStore.fire(1);
              wasPrimaryFiredLast = true;
            }

            // if both of the stores are empty, nothing can be done, return failure
          }
        }
        else {
          // try to fire the primary first
          if (! primaryTorpedoStore.isEmpty()) {
            firingSuccess = primaryTorpedoStore.fire(1);
            wasPrimaryFiredLast = true;
          }
          else {
            // although secondary was fired last time, but primary is empty
            // thus try to fire secondary again
            if (! secondaryTorpedoStore.isEmpty()) {
              firingSuccess = secondaryTorpedoStore.fire(1);
              wasPrimaryFiredLast = false;
            }

            // if both of the stores are empty, nothing can be done, return failure
          }
        }
      }
      else{
        // try to fire both of the torpedo stores
        
        boolean primaryFiringSuccess = false;
        boolean secondaryFiringSuccess = false;

        if(! primaryTorpedoStore.isEmpty()){
          primaryFiringSuccess = primaryTorpedoStore.fire(1);
          wasPrimaryFiredLast = true;
        }
        if(! secondaryTorpedoStore.isEmpty()){
          secondaryFiringSuccess = secondaryTorpedoStore.fire(1);
          wasPrimaryFiredLast = false;
        }
  
        if(primaryFiringSuccess || secondaryFiringSuccess){
          firingSuccess = true;
        }
      }
    return firingSuccess;
  }

}
