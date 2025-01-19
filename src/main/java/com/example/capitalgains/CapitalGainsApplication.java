package com.example.capitalgains;

import com.example.capitalgains.config.PropertiesConfig;
import com.example.capitalgains.domain.AssetOperation;
import com.example.capitalgains.domain.Fee;
import com.example.capitalgains.processor.FeeCalcProcessor;
import com.example.capitalgains.processor.InputProcessor;
import com.example.capitalgains.processor.OutputProcessor;
import com.example.capitalgains.processor.impl.FeeCalcImpl;
import com.example.capitalgains.processor.impl.InputStdinAssetOperations;
import com.example.capitalgains.processor.impl.OutputStdout;
import com.example.capitalgains.service.CommandLineService;
import com.example.capitalgains.utils.MapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@SpringBootApplication
public class CapitalGainsApplication implements CommandLineRunner {

    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	private final InputProcessor<List<AssetOperation>, String> inputStdin;
	private final FeeCalcProcessor<List<Fee>, List<AssetOperation>> feeCalc;
	private final OutputProcessor<List<Fee>> outputStdout;

    public CapitalGainsApplication(PropertiesConfig propertiesConfig) {
		ObjectMapper objectMapper = new ObjectMapper();
		MapperUtils mapperUtils = new MapperUtils(objectMapper, propertiesConfig);

        this.inputStdin = new InputStdinAssetOperations(mapperUtils);
		this.feeCalc = new FeeCalcImpl(mapperUtils);
		this.outputStdout = new OutputStdout(mapperUtils);
	}

	public static void main(String[] args) {
		SpringApplication.run(CapitalGainsApplication.class, args);
	}

	@Override
	public void run(String... args) {
		new CommandLineService(bufferedReader, inputStdin, feeCalc, outputStdout).runner();
	}
}
