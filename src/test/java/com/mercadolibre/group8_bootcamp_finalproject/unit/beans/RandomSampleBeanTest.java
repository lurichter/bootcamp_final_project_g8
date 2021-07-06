package com.mercadolibre.group8_bootcamp_finalproject.unit.beans;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.SampleDTO;
import com.mercadolibre.group8_bootcamp_finalproject.beans.RandomSampleBean;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomSampleBeanTest {

	@Test
	public void randomPositiveTestOK() {
		RandomSampleBean randomSample = new RandomSampleBean();

		SampleDTO sample = randomSample.random();
		
		assertTrue(sample.getRandom() >= 0);
	}
}
