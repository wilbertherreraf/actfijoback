package gob.gamo.activosf.app.depre.convert;


import java.util.List;

import static java.lang.String.format;

/**
 * This exception is a custom runtime exception thrown when the AccountInterpreter is trying to post transactions into Accounts.
 */
public class AccountInterpreterException extends RuntimeException {

    /**
     * Creates an AccountInterpreterException a RuntimeException using details of the number of entries being posted, the transaction an the Exception itself
     *
     * @param entries     Entries being posted in the transaction
     * @param transaction The transaction which we use to post entries into the accounts
     * @param e           The exception created at runtime due to unexpected Transaction behaviour
     */
    public AccountInterpreterException(final List<String> entries, final String transaction, final Exception e) {

        super(format("Could not post %s entries, in the transaction: %s caused by: %s", entries.size(), transaction, e.getCause()), e);
    }
}
