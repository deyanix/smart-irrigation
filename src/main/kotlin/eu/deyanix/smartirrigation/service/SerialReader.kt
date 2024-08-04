package eu.deyanix.smartirrigation.service

import com.pi4j.io.serial.Serial
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

@Service
@ConditionalOnProperty("application.serial.enabled")
class SerialReader(
	private val serial: Serial?
) {
	private val lock: Lock = ReentrantLock()
	private val bufferedConditional: Condition = lock.newCondition()
	private val future: ScheduledFuture<*> = Executors.newSingleThreadScheduledExecutor()
		.scheduleWithFixedDelay(SerialReaderRunnable(), 0, 10, TimeUnit.MILLISECONDS)
	private val buffer: Deque<String> = LinkedList()
	private val currentBuffer: StringBuffer = StringBuffer()

	fun readLine(): String {
		lock.lock()
		try {
			while (buffer.isEmpty()) {
				bufferedConditional.await()
			}
			return nextLine()!!
		} finally {
			lock.unlock()
		}
	}

	fun nextLine(): String? {
		return buffer.poll()
	}

	fun nextLines(): Array<String> {
		val lines = buffer.toTypedArray()
		clear()
		return lines
	}

	fun clear() {
		buffer.clear()
	}

	inner class SerialReaderRunnable : Runnable {
		override fun run() {
			if (!serial!!.isOpen) {
				future.cancel(false)
			}

			val length: Int = serial.available()
			if (length == 0) {
				return
			}

			try {
				lock.lock()
				val bytes = ByteArray(length)
				serial.read(bytes, length)
				for (byte in bytes) {
					val char = byte.toInt().toChar()
					if (char == '\n' || char == '\r') {
						if (currentBuffer.isNotEmpty()) {
							buffer.add(currentBuffer.toString())
							currentBuffer.setLength(0)
							bufferedConditional.signal()
						}
					} else {
						currentBuffer.append(char)
					}
				}
			} finally {
				lock.unlock()
			}
		}
	}
}
