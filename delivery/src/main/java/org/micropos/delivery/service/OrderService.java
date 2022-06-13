package org.micropos.delivery.service;

import java.util.function.Consumer;

import org.micropos.delivery.model.Order;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import feign.codec.Decoder;
import feign.codec.Encoder;

@FeignClient(name = "orders", path = "/api")
public interface OrderService {
    @GetMapping("/{id}")
    public Order get(@PathVariable String id);
}

@Configuration
class FeignConfig {
    private ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;

    /**
     * @return
     */
    @Bean
    Encoder feignEncoder() {
        return new SpringEncoder(messageConverters);
    }

    /**
     * @return
     */
    @Bean
    Decoder feignDecoder() {
        return new SpringDecoder(messageConverters, new EmptyObjectProvider<>());
    }

    class EmptyObjectProvider<T> implements ObjectProvider<T> {

        @Override
        public T getObject(Object... args) throws BeansException {
            return null;
        }
    
        @Override
        public T getIfAvailable() throws BeansException {
            return null;
        }
    
        @Override
        public T getIfUnique() throws BeansException {
            return null;
        }
    
        @Override
        public T getObject() throws BeansException {
            return null;
        }
    
        @Override
        public void forEach(Consumer<? super T> action) {
            // do nothing
        }
    
    }
}