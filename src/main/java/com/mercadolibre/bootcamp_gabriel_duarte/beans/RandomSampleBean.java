package com.mercadolibre.bootcamp_gabriel_duarte.beans;

import com.mercadolibre.bootcamp_gabriel_duarte.dtos.SampleDTO;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomSampleBean {
	private Random random = new Random();

	public SampleDTO random() {
		return new SampleDTO(random.nextInt(Integer.MAX_VALUE));
	}
}

