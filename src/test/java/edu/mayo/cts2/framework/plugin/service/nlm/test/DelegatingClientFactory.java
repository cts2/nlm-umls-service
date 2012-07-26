package edu.mayo.cts2.framework.plugin.service.nlm.test;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.elasticsearch.client.Client;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

import fr.pilato.spring.elasticsearch.ElasticsearchClientFactoryBean;


public class DelegatingClientFactory implements FactoryBean<Client> {

	Client delegate;
	
	public void setNode(org.elasticsearch.node.Node node) throws Exception{
		ElasticsearchClientFactoryBean factory = new ElasticsearchClientFactoryBean();
	
		factory.setNode(node);
		factory.afterPropertiesSet();
		
		delegate = factory.getObject();
	}

	class Interceptor implements MethodInterceptor{

		@Override
		public Object invoke(MethodInvocation i) throws Throwable {
			Method method = i.getMethod();

			return method.invoke(delegate, i.getArguments());
		}
	}

	@Override
	public Client getObject() throws Exception {
		ProxyFactory pf = new ProxyFactory(Client.class, new Interceptor());
		pf.setProxyTargetClass(true);
		pf.addInterface(Client.class);
		pf.setTargetClass(Client.class);
		
		return (Client) pf.getProxy();
	}

	@Override
	public Class<?> getObjectType() {
		return Client.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}