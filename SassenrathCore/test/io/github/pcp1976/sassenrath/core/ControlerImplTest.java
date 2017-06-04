package io.github.pcp1976.sassenrath.core;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.github.pcp1976.sassenrath.api.Controller;
import io.github.pcp1976.sassenrath.api.extend.Pipe;
import io.github.pcp1976.sassenrath.pluginempty.PluginEmpty;

public class ControlerImplTest {
	static Controller controller;
	static Pipe p1;
	static Pipe p2;
	static Pipe p3;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		controller = new ControllerImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		controller = null;
	}

	@Before
	public void setUp() throws Exception {
		PluginEmpty p = new PluginEmpty();
		p1 = (Pipe) p.buildPlugin(); //we know this is a pipe - in reality we'd have to check
		p2 = (Pipe) p.buildPlugin();
		p3 = (Pipe) p.buildPlugin();
	}

	@After
	public void tearDown() throws Exception {
		p1 = null;
		p2 = null;
		p3 = null;
	}

	@Test
	public void testAddSink() {
		// let's simply add p2 as a sink to p1
		assertTrue(controller.addSink(p1, p2));
	}
	
	@Test
	public void testAddCycle(){
		// here we want to check that we can't add cyclic dependencies
		assertTrue(controller.addSink(p1, p2)); // this should be fine
		assertTrue(controller.addSink(p2, p3)); // this should also be fine
		assertFalse(controller.addSink(p3, p1)); // here there is a cycle - not fine
	} 

}
