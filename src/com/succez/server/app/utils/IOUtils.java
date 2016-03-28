package com.succez.server.app.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
	
	public static void free(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
