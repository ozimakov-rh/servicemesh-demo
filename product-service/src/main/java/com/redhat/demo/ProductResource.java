package com.redhat.demo;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestStreamElementType;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @RestClient
    RatingService ratingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Product> fetchProducts() {
        return productRepository.streamAll()
                .map(this::entityToProduct);
    }

    @GET
    @Path("/stream")
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<Product> fetchProductsAsStream() {
        return fetchProducts();
    }

    @GET
    @Path("/{id}")
    public Uni<ProductDetails> fetchProductDetailsById(@PathParam("id") long id) {
        return productRepository.findById(id)
                .onItem().ifNull().failWith(NotFoundException::new)
                .flatMap(this::entityToProductDetails);
    }

    @POST
    public Uni<ProductDetails> createProduct(Product product) {
        return Panache.withTransaction(() ->
                productRepository.persist(this.productToEntity(product))
                        .flatMap(this::entityToProductDetails)
        );
    }

    @PUT
    @Path("/{id}")
    public Uni<ProductDetails> updateProduct(@PathParam("id") long id, Product product) {
        return Panache.withTransaction(() ->
                productRepository.findById(id)
                        .onItem().ifNull().failWith(NotFoundException::new)
                        .map(entity -> this.updateEntity(entity, product))
                        .flatMap(productRepository::persist)
                        .flatMap(this::entityToProductDetails)
        );
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> deleteProduct(@PathParam("id") long id) {
        return Panache.withTransaction(() ->
                productRepository.deleteById(id)
                        .onItem().call(result -> {
                            if (!result) throw new NotFoundException();
                            return Uni.createFrom().item(true);
                        }).replaceWithVoid()
        );
    }

    // mappers
    private Product entityToProduct(ProductEntity entity) {
        return new Product(entity.id, entity.name, entity.description);
    }

    private Uni<ProductDetails> entityToProductDetails(ProductEntity entity) {
        return retrieveProductRating(entity.id)
                .map(rating -> new ProductDetails(entity.id, entity.name, entity.description, rating != null ? rating.avg() : 0, rating != null ? rating.count() : 0)
        );
    }

    private ProductEntity productToEntity(Product product) {
        var entity = new ProductEntity();
        entity.id = product.id();
        return updateEntity(entity, product);
    }

    private ProductEntity updateEntity(ProductEntity entity, Product product) {
        entity.name = product.name();
        entity.description = product.description();
        return entity;
    }

    @Timeout(value = 250)
    @Fallback(fallbackMethod = "fallbackRating")
    @CircuitBreaker
    public Uni<ProductRating> retrieveProductRating(long productId) {
        return ratingService.getProductRating(productId);
    }

    public Uni<ProductRating> fallbackRating(long productId) {
        return Uni.createFrom().item(new ProductRating(productId, 0, 0));
    }


}