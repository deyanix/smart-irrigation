module com.pi4j.example {
	// Pi4J MODULES
	requires com.pi4j;
	requires com.pi4j.library.gpiod;
	requires com.pi4j.plugin.gpiod;
	requires com.pi4j.library.pigpio;
	requires com.pi4j.plugin.pigpio;
	requires com.pi4j.library.linuxfs;
	requires com.pi4j.plugin.linuxfs;
	requires com.pi4j.plugin.raspberrypi;

	// SLF4J MODULES
	requires org.slf4j;
	requires org.slf4j.simple;

	uses com.pi4j.extension.Extension;
	uses com.pi4j.provider.Provider;

	opens eu.deyanix.pi4juart to com.pi4j;
}
