package cdx.opencdx.adr.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enum representing different concept focus modes.
 */
@Schema(description = "Concept focus modes.")
public enum ConceptFocus {
    /**
     * Represents the concept focus mode "SELF"
     */
    @JsonEnumDefaultValue
    @Schema(description = "Focus on the current concept.")
    SELF,
    /**
     * This variable represents a concept focus on the descendants of a given symbol.
     * A descendant symbol is any symbol that is directly or indirectly derived from the given symbol.
     * The DESCENDANTS focus can be used to perform operations or access information related to the descendants of a symbol.
     * This concept focus is part of an enumerated type called ConceptFocus which defines various types of concept foci.
     * <p>
     * Example usage:
     * ConceptFocus focus = ConceptFocus.DESCENDANTS;
     */
    @Schema(description = "Focus on the descendants of the current concept.")
    DESCENDANTS,
    /**
     * Describes the concept focus type that includes the descendants or the concept itself.
     */
    @Schema(description = "Focus on the descendants or the current concept.")
    DESCENDANTS_OR_SELF,
    /**
     * Represents the concept focus on ancestors.
     */
    @Schema(description = "Focus on the ancestors of the current concept.")
    ANCESTORS,
    /**
     * Represents a concept focus type that includes the ancestors or the concept itself.
     * Ancestors are the concepts that are higher in the hierarchy relative to a given concept.
     * The concept itself is also included in the focus type.
     */
    @Schema(description = "Focus on the ancestors or the current concept.")
    ANCESTORS_OR_SELF,
    /**
     * Specifies the focus on the immediate children of a concept.
     */
    @Schema(description = "Focus on the immediate children of the current concept.")
    CHILDREN,
    /**
     * Represents the option to include the current symbol and its children when performing a certain operation.
     * This option is used in conjunction with other options to define the scope of the operation.
     */
    @Schema(description = "Focus on the children or the current concept.")
    CHILDREN_OR_SELF,
    /**
     * The PARENT enum constant represents the concept focus on the parent of a given node.
     * It is used in the context of tree-like structures to determine the scope or focus of operations or queries.
     * The parent of a node is the immediate ancestor of that node. In other words, it is the node that is one level above the given node.
     */
    @Schema(description = "Focus on the parent of the current concept.")
    PARENT,
    /**
     * Represents the concept focus of "parent or self" in a hierarchy.
     * <p>
     * This concept focus indicates that the selection should include the parent
     * element and the current element itself.
     * <p>
     * It is one of the several possible values defined in the {@link ConceptFocus}
     * enum.
     */
    @Schema(description = "Focus on the parent or the current concept.")
    PARENT_OR_SELF,
    /**
     * Represents a member of a concept focus.
     */
    @Schema(description = "Focus on the members of the current concept.")
    MEMBER,

    /**
     * Represents a concept focus on the timing of the concept.
     */
    @Schema(description = "Focus on the timing of the concept.")
    DATE
}
