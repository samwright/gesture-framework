package io.github.samwright.framework.model;

import io.github.samwright.framework.model.common.ChildOf;
import io.github.samwright.framework.model.common.ElementObserver;
import io.github.samwright.framework.model.helper.TypeData;

import java.util.Set;

/**
 * An {@code Element} is a {@link Processor} which sits inside a {@link Workflow}.
 *
 * It can be observed by {@link ElementObserver} objects, which are registered with the
 * {@code withObservers(newObservers)} method.
 */
public interface Element extends Processor, ChildOf<Workflow> {

    /**
     * Gets the {@link ElementObserver} objects that are observing this.  When this is
     * replaced, the observers are updated to their latest versions.
     *
     * @return the {@link ElementObserver} objects that are observing this.
     */
    Set<ElementObserver> getObservers();

    /**
     * Returns this (or if this is not mutable, a clone) with the given set of observers.
     *
     * @param newObservers the observers who's latest versions will be in the returned
     *                     {@code Element}.
     * @return an {@code Element} with the latest versions of the given {@code newObservers}.
     */
    Element withObservers(Set<ElementObserver> newObservers);

    /**
     * Return a clone of this with the given {@link TypeData}.  It will only work if this is
     * immutable (since the parametric types must be set at instantiation).
     *
     * @param newTypeData the type data to put in the returned clone.
     * @return a clone of this with the given {@code TypeData}.
     * @throws RuntimeException if this is still mutable.
     */
    Element withTypeData(TypeData newTypeData);

    @Override
    Element withParent(Workflow newParent);

    @Override
    Element createMutableClone();

    @Override
    Element getCurrentVersion();
}
