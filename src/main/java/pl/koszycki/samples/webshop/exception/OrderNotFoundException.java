package pl.koszycki.samples.webshop.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Wojciech Koszycki <wojciech.koszycki@gmail.com>
 */
public class OrderNotFoundException extends WebApplicationException {

    public OrderNotFoundException(String message) {
        super(Response
            .status(Response.Status.NOT_FOUND)
            .entity(message)
            .type(MediaType.TEXT_PLAIN)
            .build());
    }

}
