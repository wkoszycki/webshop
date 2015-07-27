package pl.koszycki.samples.webshop.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Wojciech Koszycki <wojciech.koszycki@gmail.com>
 */
public class InvalidQuantityException extends WebApplicationException {

    public InvalidQuantityException(String message) {
        super(Response
            .status(Response.Status.BAD_REQUEST)
            .entity(message)
            .build());
    }

}
