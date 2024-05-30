package eu.deyanix.pi4juart;

import com.pi4j.Pi4J;
import com.pi4j.boardinfo.util.BoardInfoHelper;
import com.pi4j.context.Context;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.StopBits;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.plugin.pigpio.PiGpioPlugin;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider;
import com.pi4j.provider.Providers;
import com.pi4j.registry.Registry;
import com.pi4j.util.Console;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws Exception {
		final Console console = new Console();
		console.title("<-- The Pi4J Project -->", "Minimal Example project");

		final Context pi4j = Pi4J.newAutoContext();


		console.println("Board model: " + pi4j.boardInfo().getBoardModel().getLabel());
		console.println("Operating system: " + pi4j.boardInfo().getOperatingSystem());
		console.println("Java versions: " + pi4j.boardInfo().getJavaInfo());
		console.println("Board model: " + BoardInfoHelper.current().getBoardModel().getLabel());
		console.println("Raspberry Pi model with RP1 chip (Raspberry Pi 5): " + BoardInfoHelper.usesRP1());
		console.println("OS is 64-bit: " + BoardInfoHelper.is64bit());
		console.println("JVM memory used (MB): " + BoardInfoHelper.getJvmMemory().getUsedInMb());
		console.println("Board temperature (°C): " + BoardInfoHelper.getBoardReading().getTemperatureInCelsius());

		Platforms platforms = pi4j.platforms();
		console.box("Pi4J PLATFORMS");
		console.println();
		platforms.describe().print(System.out);
		console.println();

		Platform platform = pi4j.platform();
		console.box("Pi4J DEFAULT PLATFORM");
		console.println();
		platform.describe().print(System.out);
		console.println();

		Providers providers = pi4j.providers();
		console.box("Pi4J PROVIDERS");
		console.println();
		providers.describe().print(System.out);
		console.println();

		Registry registry = pi4j.registry();
		console.box("Pi4J REGISTRY");
		console.println();
		registry.describe().print(System.out);
		console.println();

		console.println(PiGpioPlugin.ID);

		final SerialConfig serialConfig = Serial.newConfigBuilder(pi4j)
				.baud(Baud._9600)
				.dataBits(DataBits._8)
				.parity(Parity.NONE)
				.stopBits(StopBits._1)
				.flowControl(FlowControl.NONE)
				.id("my-serial")
				.device("/dev/ttyS0")
				.provider(PiGpioSerialProvider.class)
				.build();

		try (Serial serial = pi4j.create(serialConfig)) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(serial.getInputStream()));
			StringBuilder line = new StringBuilder();
			while (serial.isOpen()) {
				int index = serial.available();
				while (index > 0) {
					char b = (char) reader.read();
					if (b == '\n') {
						if (!line.isEmpty()) {
							console.println("Data: '" + line + "'");
							line = new StringBuilder();
						}
					} else {
						line.append(b);
					}
					index--;
				}
			}
		}
	}
}
