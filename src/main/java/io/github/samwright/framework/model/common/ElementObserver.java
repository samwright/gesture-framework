package io.github.samwright.framework.model.common;

import io.github.samwright.framework.model.Element;
import io.github.samwright.framework.model.Processor;
import io.github.samwright.framework.model.helper.Mediator;

import java.util.List;

/**
 * An object the observes the processing of data in a {@link Element}.
 */
public interface ElementObserver {

    /**
     * Notify this {@code ElementObserver} that a {@link Processor} it observes is about to output
     * a {@link Mediator} object.
     *
     * @param processedData the {@link Mediator} the observed {@link Processor} is about to output.
     */
    void handleProcessedData(Mediator processedData);

    /**
     * Notify this {@code ElementObserver} that a {@link Processor} it observes is about to output
     * a list of {@link Mediator} objects it created from a single input training datum.
     *
     * @param processedTrainingData the list of {@link Mediator} objects the observed
     *                              {@code Processor} created from a single input training datum.
     */
    void handleProcessedTrainingData(List<Mediator> processedTrainingData);

}
