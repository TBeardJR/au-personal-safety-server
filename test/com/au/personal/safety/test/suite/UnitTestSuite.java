package com.au.personal.safety.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.au.personal.safety.test.validator.*;





@RunWith(Suite.class)

@Suite.SuiteClasses({
	LocationResourceValidatorTest.class,
	EmailResourceValidatorTest.class
})

public class UnitTestSuite {
}

