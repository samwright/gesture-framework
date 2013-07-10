package BestSoFar.framework.core.common;

import BestSoFar.framework.core.helper.ImmutableVersion;

/**
 * An object which eventually becomes immutable.
 * <p/>
 * It might be immutable from creation, or it might start as mutable and become immutable when
 * {@code finalise(..)} is called.
 * <p/>
 * The convention to use is {@code X getX()} for accessors and
 * {@code EventuallyImmutable withX(newX)} for mutators.
 * <p/>
 * Whilst {@code this.isMutable()}, mutators mutate the internal data and return the same object.
 * Once immutable, mutators create a mutable clone and mutations are applied to it, i.e.
 * {@code createMutableClone().withX(newX)}.
 * <p/>
 * Mutations can therefore be strung together, e.g. {@code obj.withX(newX).withY(newY).withZ(newZ)}
 * will return {@code obj} with the mutations if {@code obj.isMutable()} or
 * {@code obj.createMutableClone()} with the mutations otherwise.
 * <p/>
 * Immutable objects are made aware of replacements ready to take their place with
 * {@code original.replaceWith(replacement)}.  It then updates its {@link ImmutableVersion}
 * information and generates the {@code ImmutableVersion} for the replacement,
 * which is passed in {@code replacement.finalise(version)} and ensures the replacement is
 * finalised (i.e. immutable).
 * <p/>
 * An object may be finalised multiple times to update it with new version information.  No new
 * objects are created in doing so, and the only effect is to save teh new version information.
 *
 * @see BestSoFar.framework.core.helper.MutabilityHelper MutabilityHelper
 */
public interface EventuallyImmutable extends Deletable {
    /**
     * Create a mutable clone of this object.  It will remain mutable until passed as a
     * {@code replacement} to {@code replaceWith(replacement)}, at which point
     * {@code finalise(..)} will be called to effect the change to an immutable object.
     *
     * @return a mutable clone.
     */
    EventuallyImmutable createMutableClone();

    /**
     * If this object is mutable, this method will make it immutable. In any case,
     * it will save the new version information.
     * <p/>
     * Calling {@code replaceWith(replacement)} creates the new {@link ImmutableVersion version}
     * information and passes it to {@code replacement.finalise(version)}.
     *
     * @param version the new version information for this object.
     */
    void finalise(ImmutableVersion version);

    /**
     * Returns true iff this object is mutable, meaning it was created as mutable and has not
     * been finalised.
     *
     * @return true iff this object is mutable.
     */
    boolean isMutable();

    /**
     * Propose a replacement for this object.  How this is handled is determined by the concrete
     * class implementing this.
     * <p/>
     * If the replacement has already replaced another object, this tells that object to discard
     * its replacement.  This means a sequence of versions between {@code EventuallyImmutable}
     * objects {@code start} and {@code end} can be contracted using
     *
     * <pre>{@code
     * start.discardReplacement();
     * start.replaceWith(end);
     * }</pre>
     *
     * @param replacement the replacement to propose.
     * @throws RuntimeException if this object is still mutable,
     *                          or has already been replaced or deleted.
     */
    void replaceWith(EventuallyImmutable replacement);

    /**
     * Discards any replacement to this object and ensures this object is not deleted,
     * therefore allowing for it to be replaced by another {@code EventuallyImmutable} object.
     */
    void discardReplacement();

    /**
     * Gets the {@link ImmutableVersion} object that describes this object.  It contains
     * information about this version of the {@code EventuallyImmutable} object,
     * and its earlier and later versions.
     *
     * @return the version information about this object.
     */
    ImmutableVersion getVersion();
}