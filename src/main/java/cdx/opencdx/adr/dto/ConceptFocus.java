package cdx.opencdx.adr.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

/**
 * Enum representing different concept focus modes.
 */
public enum ConceptFocus {
    /**
     * Represents the concept focus mode "SELF"
     */
    @JsonEnumDefaultValue
    SELF,
    /**
     * This variable represents a concept focus on the descendants of a given symbol.
     * A descendant symbol is any symbol that is directly or indirectly derived from the given symbol.
     * The DESCENDANTS focus can be used to perform operations or access information related to the descendants of a symbol.
     * This concept focus is part of an enumerated type called ConceptFocus which defines various types of concept foci.
     *
     * Example usage:
     * ConceptFocus focus = ConceptFocus.DESCENDANTS;
     */
    DESCENDANTS,
    /**
     *
     */
    DESCENDANTS_OR_SELF,
    /**
     * Represents the concept focus on ancestors.
     */
    ANCESTORS,
    /**
     * Represents a concept focus type that includes the ancestors or the concept itself.
     * Ancestors are the concepts that are higher in the hierarchy relative to a given concept.
     * The concept itself is also included in the focus type.
     */
    ANCESTORS_OR_SELF,
    /**
     * Specifies the focus on the immediate children of a concept.
     */
    CHILDREN,
    /**
     * Represents the option to include the current symbol and its children when performing a certain operation.
     * This option is used in conjunction with other options to define the scope of the operation.
     */
    CHILDREN_OR_SELF,
    /**
     * The PARENT enum constant represents the concept focus on the parent of a given node.
     * It is used in the context of tree-like structures to determine the scope or focus of operations or queries.
     * The parent of a node is the immediate ancestor of that node. In other words, it is the node that is one level above the given node.
     */
    PARENT,
    /**
     * Represents the concept focus of "parent or self" in a hierarchy.
     *
     * This concept focus indicates that the selection should include the parent
     * element and the current element itself.
     *
     * It is one of the several possible values defined in the {@link ConceptFocus}
     * enum.
     */
    PARENT_OR_SELF,
    /**
     * Represents a member of a concept focus.
     */
    MEMBER,
    /**
     * Represents the concept focus for a search or operation.
     * The ANY concept focus allows for searching or operating on any concept.
     *
     * It is one of the possible values of the ConceptFocus enum.
     *
     * @see ConceptFocus
     */
    ANY;
}
