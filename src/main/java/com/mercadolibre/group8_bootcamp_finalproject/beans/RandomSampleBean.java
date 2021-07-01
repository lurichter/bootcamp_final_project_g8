package com.mercadolibre.group8_bootcamp_finalproject.beans;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.SampleDTO;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomSampleBean {
	private Random random = new Random();

	public SampleDTO random() {
		return new SampleDTO(random.nextInt(Integer.MAX_VALUE));
	}
}

