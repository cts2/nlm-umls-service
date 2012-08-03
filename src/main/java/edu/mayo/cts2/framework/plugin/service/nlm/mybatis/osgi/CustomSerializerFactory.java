package edu.mayo.cts2.framework.plugin.service.nlm.mybatis.osgi;

import com.caucho.hessian.io.AbstractSerializerFactory;
import com.caucho.hessian.io.BeanDeserializer;
import com.caucho.hessian.io.Deserializer;
import com.caucho.hessian.io.HessianProtocolException;
import com.caucho.hessian.io.Serializer;

@SuppressWarnings("rawtypes") 
public class CustomSerializerFactory extends AbstractSerializerFactory {

	public Deserializer getDeserializer(Class cl)
			throws HessianProtocolException {
		if (cl.getName().startsWith("gov.cdc.vocab.service")) {
			return new BeanDeserializer(cl);
		} else {
			return null;
		}
	}

	@Override
	public Serializer getSerializer(Class cl) throws HessianProtocolException {
		return null;
	}

}
