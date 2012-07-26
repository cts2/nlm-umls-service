package edu.mayo.cts2.framework.plugin.service.nlm.test

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.elasticsearch.client.Client
import org.springframework.aop.framework.ProxyFactory
import org.springframework.beans.factory.FactoryBean

import fr.pilato.spring.elasticsearch.ElasticsearchClientFactoryBean


class DelegatingClientFactory implements FactoryBean<Client> {

	Client delegate
	
	public void setNode(org.elasticsearch.node.Node node){
		def factory = new ElasticsearchClientFactoryBean()
	
		factory.setNode(node)
		
		delegate = factory.getObject()
	}

	class Interceptor implements MethodInterceptor{

		@Override
		Object invoke(MethodInvocation i) throws Throwable {

			def method = i.getMethod()

			method.invoke(delegate, i.getArguments())
		}
	}

	@Override
	public Client getObject() throws Exception {
		def pf = new ProxyFactory(Client, new Interceptor())
		pf.setProxyTargetClass(true)
		pf.addInterface(Client)
		pf.setTargetClass(Client)
		return pf.getProxy()
	}

	@Override
	public Class<?> getObjectType() {
		return Client;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}