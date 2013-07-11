package org.prevayler.foundation.monitor;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;

import java.io.File;
import de.ovgu.cide.jakutil.*;

public interface Monitor {
	@Stub
	void notify(Class clazz, String message);

	@Stub
	void notify(Class clazz, String message, Exception ex);

	@Stub
	void notify(Class clazz, String message, File file);

	@Stub
	void notify(Class clazz, String message, File file, Exception ex);
}
