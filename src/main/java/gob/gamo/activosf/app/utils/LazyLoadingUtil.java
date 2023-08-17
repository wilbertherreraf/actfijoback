package gob.gamo.activosf.app.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import gob.gamo.activosf.app.errors.DataException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LazyLoadingUtil {
	public static void initializeCollection(Collection<?> collection) {
	    if(collection == null) {
	        return;
	    }
	    collection.iterator().hasNext();
	}
	
	public static void load(Object o, String ... atributos){
		if (atributos == null) {
			return;
		}
		for (String atributo : atributos) {
			try {
				Object atr = o.getClass().getMethod(atributo, null).getDefaultValue();//  PropertyUtils.getProperty(o, atributo);
                log.warn("recuperar valor de objetooooooooo no implementeadp");          
				if (atr != null) {
					if(atr instanceof Collection<?>){
						initializeCollection((Collection<?>)atr);
					} else {
						atr.toString();
					}
				}
			} catch (Exception e) {
				new DataException("No se pudo realizar el LazyLoading de la clase: " +
					o.getClass() + " para el atributo: " + atributo , e);
			}
		}
		
	}
	
	public static void loadCollection(Collection<?> entities, String ... atributos) {
		if (atributos == null) {
			return;
		}
		for (Object object : entities) {
			load(object, atributos);
		}
	}
}
