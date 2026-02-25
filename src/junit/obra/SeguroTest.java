package junit.obra;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import obra.Seguro;

public class SeguroTest {
	
	@Test
	public void testEquals() {
		Seguro s = new Seguro(10, "yay");
		Seguro scpy = new Seguro(10, "yay");
		Seguro s1 = new Seguro(11, "yayaa");
		Seguro s2 = new Seguro(10, "noo");
		Seguro s3 = new Seguro(111, "yay");
	
		assertTrue(s.equals(s));
		assertTrue(s.equals(scpy));
		assertFalse(s.equals(s1));
		assertFalse(s.equals(null));
		assertFalse(s.equals("tarta"));
		assertFalse(s.equals(s2));
		assertFalse(s.equals(s3));
	}
}
