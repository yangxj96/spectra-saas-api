/*
 *  Copyright (c) 2021 - 2023
 *  作者：杨新杰(Jack Young)
 *  邮箱：yangxj96@gmail.com
 *  日期：2023-08-08 11:51:26
 *  Copyright (c) 2021 - 2023
 */

package io.github.yangxj96.starter.remote;

import feign.Feign;
import feign.Target;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.openfeign.*;
import org.springframework.util.StringUtils;


public class FeignCircuitBreakerTargeter implements Targeter {

	private final CircuitBreakerFactory circuitBreakerFactory;

	private final boolean circuitBreakerGroupEnabled;

	private final CircuitBreakerNameResolver circuitBreakerNameResolver;

	public FeignCircuitBreakerTargeter(CircuitBreakerFactory circuitBreakerFactory, boolean circuitBreakerGroupEnabled,
			CircuitBreakerNameResolver circuitBreakerNameResolver) {
		this.circuitBreakerFactory = circuitBreakerFactory;
		this.circuitBreakerGroupEnabled = circuitBreakerGroupEnabled;
		this.circuitBreakerNameResolver = circuitBreakerNameResolver;
	}

	@Override
	public <T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignClientFactory context,
						Target.HardCodedTarget<T> target) {
		if (!(feign instanceof FeignCircuitBreaker.Builder builder)) {
			return feign.target(target);
		}
		String name = !StringUtils.hasText(factory.getContextId()) ? factory.getName() : factory.getContextId();
		Class<?> fallback = factory.getFallback();
		if (fallback != void.class) {
			return targetWithFallback(name, context, target, builder, fallback);
		}
		Class<?> fallbackFactory = factory.getFallbackFactory();
		if (fallbackFactory != void.class) {
			return targetWithFallbackFactory(name, context, target, builder, fallbackFactory);
		}
		return builder(name, builder).target(target);
	}

	private <T> T targetWithFallbackFactory(String feignClientName, FeignClientFactory context,
			Target.HardCodedTarget<T> target, FeignCircuitBreaker.Builder builder, Class<?> fallbackFactoryClass) {
		FallbackFactory<? extends T> fallbackFactory = (FallbackFactory<? extends T>) getFromContext("fallbackFactory",
				feignClientName, context, fallbackFactoryClass, FallbackFactory.class);
		return builder(feignClientName, builder).target(target, fallbackFactory);
	}

	private <T> T targetWithFallback(String feignClientName, FeignClientFactory context,
			Target.HardCodedTarget<T> target, FeignCircuitBreaker.Builder builder, Class<?> fallback) {
		T fallbackInstance = getFromContext("fallback", feignClientName, context, fallback, target.type());
		return builder(feignClientName, builder).target(target, fallbackInstance);
	}

	private <T> T getFromContext(String fallbackMechanism, String feignClientName, FeignClientFactory context,
			Class<?> beanType, Class<T> targetType) {
		Object fallbackInstance = context.getInstance(feignClientName, beanType);
		if (fallbackInstance == null) {
			throw new IllegalStateException(
					String.format("No " + fallbackMechanism + " instance of type %s found for feign client %s",
							beanType, feignClientName));
		}

		if (fallbackInstance instanceof FactoryBean<?> factoryBean) {
			try {
				fallbackInstance = factoryBean.getObject();
			}
			catch (Exception e) {
				throw new IllegalStateException(fallbackMechanism + " create fail", e);
			}

			if (!targetType.isAssignableFrom(fallbackInstance.getClass())) {
				throw new IllegalStateException(String.format("Incompatible " + fallbackMechanism
						+ " instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
						fallbackInstance.getClass(), targetType, feignClientName));
			}

		}
		else {
			if (!targetType.isAssignableFrom(beanType)) {
				throw new IllegalStateException(String.format("Incompatible " + fallbackMechanism
						+ " instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
						beanType, targetType, feignClientName));
			}
		}
		return (T) fallbackInstance;
	}

	private FeignCircuitBreaker.Builder builder(String feignClientName, FeignCircuitBreaker.Builder builder) {
		return builder.circuitBreakerFactory(circuitBreakerFactory).feignClientName(feignClientName)
				.circuitBreakerGroupEnabled(circuitBreakerGroupEnabled)
				.circuitBreakerNameResolver(circuitBreakerNameResolver);
	}

}
