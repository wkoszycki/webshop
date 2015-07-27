package pl.koszycki.samples.webshop.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Wojciech Koszycki <wojciech.koszycki@gmail.com>
 */
public class ProductNotFoundException extends WebApplicationException {

    public ProductNotFoundException(String message) {
        super(Response
            .status(Response.Status.NOT_FOUND)
            .entity(message)
            .build());
    }

}
