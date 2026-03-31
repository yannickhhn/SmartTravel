//----------------------------------------------
//Assignment 1 
//Package exceptions 
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package exceptions;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
