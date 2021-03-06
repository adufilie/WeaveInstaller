/*
    Weave (Web-based Analysis and Visualization Environment)
    Copyright (C) 2008-2014 University of Massachusetts Lowell

    This file is a part of Weave.

    Weave is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License, Version 3,
    as published by the Free Software Foundation.

    Weave is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Weave.  If not, see <http://www.gnu.org/licenses/>.
*/

package weave.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import weave.Settings;
import weave.async.AsyncObserver;
import weave.async.AsyncFunction;

public class DownloadUtils extends TransferUtils
{
	/**
	 * Convert a scalar value to a string speed measure
	 * <br><br>
	 * Example Usage:
	 * <code>
	 * <pre>
	 * 	DownloadUtils.speedify( 23523523 )	= 22.43 MB/s
	 * 	DownloadUtils.speedify( 34637 )	= 33.8 KB/s
	 * </pre>
	 * </code>
	 * 
	 * @param speed The speed of the transfer
	 * @return The string representation of the transfer speed
	 */
	public static String speedify( int speed )
	{
		return speedify( (double)speed );
	}
	
	/**
	 * Convert a scalar value to a string speed measure
	 * <br><br>
	 * Example Usage:
	 * <code>
	 * <pre>
	 * 	DownloadUtils.speedify( 23523523 )	= 22.43 MB/s
	 * 	DownloadUtils.speedify( 34637 )	= 33.8 KB/s
	 * </pre>
	 * </code>
	 * 
	 * @param speed The speed of the transfer
	 * @return The string representation of the transfer speed
	 */
	public static String speedify( double speed )
	{
		int i = 0; 
		List<String> s = Arrays.asList("B/s", "KB/s", "MB/s", "GB/s", "TB/s");
		
		while( (speed/KB) > 1 ) {
			speed = speed / KB;
			i++;
		}
		return String.format("%." + i + "f %s", speed, s.get(i));
	}

	/**
	 * Download a file from the URL and save it to the destination location
	 * <br><br>
	 * Example Usage:
	 * <code>
	 * <pre>
	 * 	URL url = new URL("http://google.com/some/file.zip");
	 * 	File dest = new File("/path/to/local/file/", "filename.zip");
	 * 	
	 * 	int status = DownloadUtils.download( url, dest );
	 * </pre>
	 * </code>
	 * 
	 * @param url The URL to access the file(s) from
	 * @param destination The local file to save the download to
	 * @return The exit status of the transfer <code>FAILED, COMPLETE, CANCELLED, OFFLINE</code>
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static int download(URL url, File destination) throws IOException, InterruptedException
	{
		return download(url, destination, null);
	}


	/**
	 * Download a file from the URL and save it to the destination location
	 * <br><br>
	 * Example Usage:
	 * <code>
	 * <pre>
	 * 	final String url = "http://google.com/some/file.zip";
	 * 	final File dest = new File("/path/to/local/file/", "filename.zip");
	 * 	
	 * 	final {@link AsyncObserver} observer = new AsyncObserver() {
	 * 		public void onUpdate() {
	 * 			// DO STATUS UPDATES HERE
	 * 			//
	 * 			progressBar.setValue( info.progress );
	 * 		}
	 *	};
	 *	{@link AsyncFunction} task = new AsyncTask() {
	 *		public Object doInBackground() {
	 * 			return DownloadUtils.download( url, dest, observer, 512 * DownloadUtils.KB );
	 *		}
	 *	};
	 *	task.execute();
	 *
	 * </pre>
	 * </code>
	 * 
	 * @param url The URL string to access the file(s) from
	 * @param destination The local file to save the download to
	 * @param observer The observer to watch the status of the transfer
	 * @return The exit status of the transfer <code>FAILED, COMPLETE, CANCELLED, OFFLINE</code>
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static int download(String url, File destination, AsyncObserver observer, int throttle) throws IOException, InterruptedException
	{
		return download(new URL(url), destination, observer, throttle);
	}
	
	/**
	 * Download a file from the URL and save it to the destination location
	 * <br><br>
	 * Example Usage:
	 * <code>
	 * <pre>
	 * 	final String url = "http://google.com/some/file.zip";
	 * 	final File dest = new File("/path/to/local/file/", "filename.zip");
	 * 	
	 * 	final {@link AsyncObserver} observer = new AsyncObserver() {
	 * 		public void onUpdate() {
	 * 			// DO STATUS UPDATES HERE
	 * 			//
	 * 			progressBar.setValue( info.progress );
	 * 		}
	 *	};
	 *	{@link AsyncFunction} task = new AsyncTask() {
	 *		public Object doInBackground() {
	 * 			return DownloadUtils.download( url, dest, observer );
	 *		}
	 *	};
	 *	task.execute();
	 *
	 * </pre>
	 * </code>
	 * 
	 * @param url The URL string to access the file(s) from
	 * @param destination The local file to save the download to
	 * @param observer The observer to watch the status of the transfer
	 * @return The exit status of the transfer <code>FAILED, COMPLETE, CANCELLED, OFFLINE</code>
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static int download(String url, File destination, AsyncObserver observer) throws IOException, InterruptedException
	{
		return download(new URL(url), destination, observer);
	}
	
	/**
	 * Download a file from the URL and save it to the destination location
	 * <br><br>
	 * Example Usage:
	 * <code>
	 * <pre>
	 * 	final URL url = new URL("http://google.com/some/file.zip");
	 * 	final File dest = new File("/path/to/local/file/", "filename.zip");
	 * 	
	 * 	final {@link AsyncObserver} observer = new AsyncObserver() {
	 * 		public void onUpdate() {
	 * 			// DO STATUS UPDATES HERE
	 * 			//
	 * 			progressBar.setValue( info.progress );
	 * 		}
	 *	};
	 *	{@link AsyncFunction} task = new AsyncTask() {
	 *		public Object doInBackground() {
	 * 			return DownloadUtils.download( url, dest, observer );
	 *		}
	 *	};
	 *	task.execute();
	 *
	 * </pre>
	 * </code>
	 * 
	 * @param url The URL to access the file(s) from
	 * @param destination The local file to save the download to
	 * @param observer The observer to watch the status of the transfer
	 * @return The exit status of the transfer <code>FAILED, COMPLETE, CANCELLED, OFFLINE</code>
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static int download(URL url, File destination, AsyncObserver observer) throws IOException, InterruptedException
	{
		return download(url, destination, observer, 0);
	}

	/**
	 * Download a file from the URL and save it to the destination location
	 * <br><br>
	 * Example Usage:
	 * <code>
	 * <pre>
	 * 	final URL url = new URL("http://google.com/some/file.zip");
	 * 	final File dest = new File("/path/to/local/file/", "filename.zip");
	 * 	
	 * 	final {@link AsyncObserver} observer = new AsyncObserver() {
	 * 		public void onUpdate() {
	 * 			// DO STATUS UPDATES HERE
	 * 			//
	 * 			progressBar.setValue( info.progress );
	 * 		}
	 *	};
	 *	{@link AsyncFunction} task = new AsyncTask() {
	 *		public Object doInBackground() {
	 * 			return DownloadUtils.download( url, dest, observer, 500 * DownloadUtils.KB );
	 *		}
	 *	};
	 *	task.execute();
	 *
	 * </pre>
	 * </code>
	 * 
	 * @param url The URL to access the file(s) from
	 * @param destination The local file to save the download to
	 * @param observer The observer to watch the status of the transfer
	 * @return The exit status of the transfer <code>FAILED, COMPLETE, CANCELLED, OFFLINE</code>
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static int download(URL url, File destination, AsyncObserver observer, int throttle) throws IOException, InterruptedException
	{
		HttpURLConnection conn = null;
		InputStream in = null;
		OutputStream out = null;
		
		if( Settings.isOfflineMode() )
			return OFFLINE;

		// Check to see if required arguments are non-null
		if( url == null || destination == null )
			throw new NullPointerException("URL or Destination File cannot be null");
		
		// If no exception was thrown, assert that URL and destination are non-null
		assert url != null;
		assert destination != null;
		
		conn = (HttpURLConnection)url.openConnection();
		in = conn.getInputStream();
		out = new FileOutputStream(destination);
		
		return FileUtils.copy(in, out, observer, throttle);
	}
}
