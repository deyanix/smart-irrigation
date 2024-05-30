package eu.deyanix.pi4juart.controller;

import com.pi4j.io.serial.Serial;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SerialController {
	private final Serial serial;

	public SerialController(Serial serial) {
		this.serial = serial;
	}

	@PostMapping("/serial")
	public void write(@RequestBody String text) throws IOException {
		serial.getOutputStream().write(text.getBytes());
		serial.getOutputStream().write('\n');
		serial.getOutputStream().write('\r');
	}
}
