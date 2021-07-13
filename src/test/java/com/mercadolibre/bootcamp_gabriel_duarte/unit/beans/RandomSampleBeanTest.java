package com.mercadolibre.bootcamp_gabriel_duarte.unit.beans;

import com.mercadolibre.bootcamp_gabriel_duarte.dtos.SampleDTO;
import com.mercadolibre.bootcamp_gabriel_duarte.beans.RandomSampleBean;

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