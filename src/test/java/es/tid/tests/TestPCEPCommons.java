package es.tid.tests;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.tid.pce.pcep.objects.BandwidthRequested;
import es.tid.pce.pcep.objects.EndPointsIPv4;

public class TestPCEPCommons {
	public static void createAllFields(Object object){
		try {
			//System.out.println("Looking at "+object.getClass().getName() );
			List<Field> fieldListNS = new ArrayList<Field>();
			List<Field> fieldList= Arrays.asList(object.getClass().getDeclaredFields());
			for (Field field : fieldList) {
				if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
					fieldListNS.add(field);
					Type ty=field.getGenericType();
					System.out.println("Type: "+ty);

					if (ty instanceof Class){
						Object o=null;
						Class c =(Class)ty;
						Method method = object.getClass().getMethod("set"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()),field.getType());
						//System.out.println("mi "+method.getName());
						if (c.isPrimitive()){
								fillPrimitive(object,method,ty);
						} else if (c.isArray()){
							
							Class c2=c.getComponentType();
							//System.out.println("c2: "+c2.getName()); 
							o = Array.newInstance(c2, 5);
							
							method.invoke(object, o);
							//System.out.println("FIXME:  ES UN ARRAY OJO ");
						}
						
						else {
						
							//System.out.println("me "+method.getName());
							 if (c.getName().equals("String")) {
									System.out.println("FIXME:  String");
									o="TEST";
							 } else if  (c.getName().equals("java.net.Inet4Address")) {
									o=Inet4Address.getByName("1.1.1.1");
							 }else if  (c.getName().equals("es.tid.pce.pcep.objects.EndPoints")) {
									o = new EndPointsIPv4();
									createAllFields(o);
							 }else if  (c.getName().equals("es.tid.pce.pcep.objects.Bandwidth")) {
								 o= new BandwidthRequested();
								 createAllFields(o);
								}
							 else {
									o = ((Class)ty).newInstance();	
									createAllFields(o);
							 }
							
							method.invoke(object, o);

						}
					}else if (ty instanceof ParameterizedType){
						ParameterizedType pt=(ParameterizedType)ty;
						Type rt=pt.getRawType();
						Type at=pt.getActualTypeArguments()[0];
						//System.out.println("tn "+pt.getTypeName());
						//System.out.println("rt "+pt.getRawType().getTypeName() );
						//System.out.println("ot "+pt.getOwnerType().getTypeName() );
						//System.out.println("at "+pt.getActualTypeArguments()[0].getTypeName());
						//getActualTypeArguments()
						if (rt instanceof Class){
							Class ca=(Class)rt;
							
							if (ca.getName().equals("java.util.LinkedList")){
								String name="get"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase());
								Method method = object.getClass().getMethod("get"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()));
								Object res=method.invoke(object);
								Method[] methods =res.getClass().getDeclaredMethods();	
								if  (((Class)at).getName().equals("es.tid.rsvp.objects.subobjects.EROSubobject")) {
									System.out.println("FIXME: es.tid.rsvp.objects.subobjects.EROSubobject");
								}else {
									Object o = ((Class)at).newInstance();
									createAllFields(o);
									methods[0].invoke(res, o);
								}
								
	
							}
			
						}
						

					}

				}		    			    	
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
		} 			
	}

	public static void fillPrimitive(Object object, Method method,Type ty) {
		try {
			if (ty.getTypeName().equals("int")){

				method.invoke(object, 0);

			}else if (ty.getTypeName().equals("boolean")){

				method.invoke(object,true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
