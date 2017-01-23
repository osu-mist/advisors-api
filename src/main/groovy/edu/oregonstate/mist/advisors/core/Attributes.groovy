package edu.oregonstate.mist.advisors.core

class Attributes {
    String lastName
    String firstName
    String username
    String effectiveTermCode
    String advisorTypeCode
    Boolean primary

    int hashCode() {
        int result
        result = (lastName != null ? lastName.hashCode() : 0)
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0)
        result = 31 * result + (effectiveTermCode != null ? effectiveTermCode.hashCode() : 0)
        result = 31 * result + (advisorTypeCode != null ? advisorTypeCode.hashCode() : 0)
        result = 31 * result + (primary != null ? primary.hashCode() : 0)

        result
    }
}
