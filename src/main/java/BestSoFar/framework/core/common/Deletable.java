package BestSoFar.framework.core.common;

/**
 * A deletable object.
 */
public interface Deletable {
    /**
     * Delete this object.  For immutable objects in some kind of framework,
     * this ensures the object will not be in subsequent versinos of the framework.
     */
    void delete();

    /**
     * Returns true iff this object was deleted.
     *
     * @return true iff this object was deleted.
     */
    boolean isDeleted();
}