package pl.koszycki.samples.webshop.resource;

import pl.koszycki.samples.webshop.domain.Order;
import pl.koszycki.samples.webshop.domain.dto.OrderDto;
import pl.koszycki.samples.webshop.domain.dto.ProductDto;
import pl.koszycki.samples.webshop.exception.InvalidQuantityException;
import pl.koszycki.samples.webshop.exception.OrderNotFoundException;
import pl.koszycki.samples.webshop.exception.ProductNotFoundException;
import pl.koszycki.samples.webshop.service.ShopService;

import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Wojciech Koszycki
 */
@Path("shop/v1.0")
@Produces("application/json")
@RequestScoped
public class ShopResource {

    @Inject
    protected ShopService shopService;

    @GET
    @Path("/products")
    public Response getProducts() throws ProductNotFoundException {
        Collection<ProductDto> products = shopService.getProductsWithQuantity();
        return Response.ok()
            .entity(products)
            .build();
    }

    @GET
    @Path("/order/{id}")
    public Response getOrder(@PathParam("id") long id) throws OrderNotFoundException {
        Order order = shopService.getOrder(id);
        return Response.ok()
            .entity(order)
            .build();
    }

    @POST
    @Path("/order")
    @Consumes("application/json")
    public Response createOrder(OrderDto order) throws ProductNotFoundException,
                                                       InvalidQuantityException {
        Order result = shopService.processOrder(order);
        return Response.status(Response.Status.CREATED)
            .entity(result)
            .build();

    }

}
