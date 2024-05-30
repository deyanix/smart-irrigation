package eu.deyanix.pi4juart;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.StopBits;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class Pi4jContextConfiguration {

	private final Logger logger = LoggerFactory.getLogger(Pi4jContextConfiguration.class);
	private Context pi4j;
	private Serial serial;

	public Pi4jContextConfiguration() {
		try {
			this.pi4j = Pi4J.newAutoContext();
			this.serial = pi4j.create(getDefaultSerialConfig());
		} catch (Exception e) {
			logger.error("Pi4J library failed to load", e);
		}
	}

	public SerialConfig getDefaultSerialConfig() {
		return Serial.newConfigBuilder(pi4j)
				.baud(Baud._9600)
				.dataBits(DataBits._8)
				.parity(Parity.NONE)
				.stopBits(StopBits._1)
				.flowControl(FlowControl.NONE)
				.id("my-serial")
				.device("/dev/ttyS0")
				.provider(PiGpioSerialProvider.class)
				.build();
	}

	@Bean
	public Context getContext() {
		return pi4j;
	}

	@Bean
	public Serial getSerial() {
		return serial;
	}
}
