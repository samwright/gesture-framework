package io.github.samwright.framework.model.helper;

import io.github.samwright.framework.model.Processor;
import io.github.samwright.framework.model.Workflow;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * An object describing the input and output types of {@link Processor} objects.
 */
@AllArgsConstructor()
@EqualsAndHashCode
final public class TypeData {

    @Getter static private final TypeData defaultType;

    static {
        defaultType = new TypeData(Object.class, Object.class);
    }

    @Getter @NonNull private final Class inputType;
    @Getter @NonNull private final Class outputType;

    @Override
    public String toString() {
        return new StringBuilder("<")
                .append(inputType.getSimpleName())
                .append(",")
                .append(outputType.getSimpleName())
                .append(">")
                .toString();
    }

    /**
     * Checks if the associated {@link Processor} can do no conversion (ie. its output is its
     * input, without any processing taking place) while still being valid.
     *
     * @return true if the input type can be casted to the output type.
     */
    @SuppressWarnings("unchecked")
    public boolean canBeEmptyContainer() {
        return outputType.isAssignableFrom(inputType);
    }

    /**
     * Checks if this {@code Processor} can come before the other {@code Processor}.
     *
     * @param other the {@code TypeData} of the other {@code Processor}.
     * @return true iff this output type can be casted to the other's input type.
     */
    @SuppressWarnings("unchecked")
    public boolean canComeBefore(TypeData other) {
        return other.inputType.isAssignableFrom(this.outputType);
    }

    /**
     * Checks if this {@code Processor} can come after the other {@code Processor}.
     *
     * @param other the {@code TypeData} of the other {@code Processor}.
     * @return true iff the other's output type can be casted to this input type.
     */
    @SuppressWarnings("unchecked")
    public boolean canComeAfter(TypeData other) {
        return this.inputType.isAssignableFrom(other.outputType);
    }

    /**
     * Checks if this {@code Processor} can be the last element inside the given
     * {@link Workflow workflow}.
     *
     * @param workflow the {@code TypeData} of the {@code Workflow}.
     * @return true iff this output type can be casted to the workflow's output type.
     */
    @SuppressWarnings("unchecked")
    public boolean canBeAtEndOfWorkflow(TypeData workflow) {
        return workflow.outputType.isAssignableFrom(this.outputType);
    }

    /**
     * Checks if this {@code Processor} can be the first element inside the given
     * {@link Workflow workflow}.
     *
     * @param workflow the {@code TypeData} of the {@code Workflow}.
     * @return true iff the workflow's input type can be casted to this input type.
     */
    @SuppressWarnings("unchecked")
    public boolean canBeAtStartOfWorkflow(TypeData workflow) {
        return this.inputType.isAssignableFrom(workflow.inputType);
    }
}