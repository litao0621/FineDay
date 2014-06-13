package com.gitonway.fineday.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HTTP;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;

public class OtherUtils {
	private OtherUtils() {
	}

	/**
	 * @param context
	 * @param dirName
	 *            Only the folder name, not full path.
	 * @return app_cache_path/dirName
	 */
	public static String getDiskCacheDir(Context context, String dirName) {
		String cachePath = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File externalCacheDir = context.getExternalCacheDir();
			if (externalCacheDir != null) {
				cachePath = externalCacheDir.getPath();
			}
		}
		if (cachePath == null) {
			cachePath = context.getCacheDir().getPath();
		}

		return cachePath + File.separator + dirName;
	}

	public static long getAvailableSpace(File dir) {
		try {
			final StatFs stats = new StatFs(dir.getPath());
			return (long) stats.getBlockSize()
					* (long) stats.getAvailableBlocks();
		} catch (Throwable e) {
			LogUtils.e(e.getMessage(), e);
			return -1;
		}

	}

	public static boolean isSupportRange(final HttpResponse response) {
		if (response == null)
			return false;
		Header header = response.getFirstHeader("Accept-Ranges");
		if (header != null) {
			return "bytes".equals(header.getValue());
		}
		header = response.getFirstHeader("Content-Range");
		if (header != null) {
			String value = header.getValue();
			return value != null && value.startsWith("bytes");
		}
		return false;
	}

//	public static String getFileNameFromHttpResponse(final HttpResponse response) {
//		if (response == null)
//			return null;
//		String result = null;
//		Header header = response.getFirstHeader("Content-Disposition");
//		if (header != null) {
//			for (HeaderElement element : header.getElements()) {
//				NameValuePair fileNamePair = element
//						.getParameterByName("filename");
//				if (fileNamePair != null) {
//					result = fileNamePair.getValue();
//					// try to get correct encoding str
//					result = CharsetUtils.toCharset(result, HTTP.UTF_8,
//							result.length());
//					break;
//				}
//			}
//		}
//		return result;
//	}

	public static Charset getCharsetFromHttpRequest(
			final HttpRequestBase request) {
		if (request == null)
			return null;
		String charsetName = null;
		Header header = request.getFirstHeader("Content-Type");
		if (header != null) {
			for (HeaderElement element : header.getElements()) {
				NameValuePair charsetPair = element
						.getParameterByName("charset");
				if (charsetPair != null) {
					charsetName = charsetPair.getValue();
					break;
				}
			}
		}

		boolean isSupportedCharset = false;
		if (!TextUtils.isEmpty(charsetName)) {
			try {
				isSupportedCharset = Charset.isSupported(charsetName);
			} catch (Throwable e) {
			}
		}

		return isSupportedCharset ? Charset.forName(charsetName) : null;
	}

	private static final int STRING_BUFFER_LENGTH = 100;

	public static long sizeOfString(final String str, String charset)
			throws UnsupportedEncodingException {
		if (TextUtils.isEmpty(str)) {
			return 0;
		}
		int len = str.length();
		if (len < STRING_BUFFER_LENGTH) {
			return str.getBytes(charset).length;
		}
		long size = 0;
		for (int i = 0; i < len; i += STRING_BUFFER_LENGTH) {
			int end = i + STRING_BUFFER_LENGTH;
			end = end < len ? end : len;
			String temp = getSubString(str, i, end);
			size += temp.getBytes(charset).length;
		}
		return size;
	}

	// get the sub string for large string
	public static String getSubString(final String str, int start, int end) {
		return new String(str.substring(start, end));
	}

	public static StackTraceElement getCurrentStackTraceElement() {
		return Thread.currentThread().getStackTrace()[3];
	}

	public static StackTraceElement getCallerStackTraceElement() {
		return Thread.currentThread().getStackTrace()[4];
	}

	private static TrustManager[] trustAllCerts;

	public static void trustAllSSLForHttpsURLConnection() {
		// Create a trust manager that does not validate certificate chains
		if (trustAllCerts == null) {
			trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			} };
		}
		// Install the all-trusting trust manager
		final SSLContext sslContext;
		try {
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCerts, null);
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());
		} catch (Throwable e) {
			LogUtils.e(e.getMessage(), e);
		}
		HttpsURLConnection
				.setDefaultHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	}
}
