package unit_test;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ObjectCollectionServiceTester
{

	models.ObjectData data1 = new models.ObjectData("TEMPERATURE", "DOUBLE", 98.6);
	models.ObjectData data2 = new models.ObjectData("NAME" , "STRING" , "Mathew");
	models.ObjectData data3 = new models.ObjectData("IS_HUNGRY" , "BOOLEAN" , true);

	services.ObjectCollectionService obj_svc = services.ObjectCollectionService.getInstance();



	@SuppressWarnings("restriction")
	@Before
	public void setUp()
	{
		obj_svc.insertDataObject(data1.getName(), data1);
		obj_svc.insertDataObject(data2.getName(), data2);
		obj_svc.insertDataObject("IS_HUNGRY", data3);
	}



	@SuppressWarnings("restriction")
	@Test
	public void testRetrieveRuleObject()
	{
		assertEquals(obj_svc.retrieveDataObject(data1.getName()),data1);
		assertEquals(obj_svc.retrieveDataObject("RANGE"), null);
		assertEquals(obj_svc.retrieveDataObject("name"), data2);
	}


	@SuppressWarnings("restriction")
	@Test
	public void testClearObjectService()
	{
		obj_svc.clearObjectService();

		assertEquals(obj_svc.toString(), null);
	}



}