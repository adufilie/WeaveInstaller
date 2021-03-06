/*
    Weave (Web-based Analysis and Visualization Environment)
    Copyright (C) 2008-2015 University of Massachusetts Lowell

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

package weave.managers;

import java.net.URL;

public class ResourceManager
{
	public static URL IMAGE_NULL = ResourceManager.class.getResource("/resources/null.png");
	
	public static URL ICON_TRAY_LOGO = ResourceManager.class.getResource("/resources/icon.png");
	public static URL ICON_TRAY_LOGO_LARGE = ResourceManager.class.getResource("/resources/icon_large.png");
	
	public static URL ICON_TRAY_ONLINE = ResourceManager.class.getResource("/resources/icon.png");
	public static URL ICON_TRAY_OFFLINE = ResourceManager.class.getResource("/resources/icon.png");
	public static URL ICON_TRAY_ERROR = ResourceManager.class.getResource("/resources/icon.png");
	public static URL IMAGE_OIC_LOGO = ResourceManager.class.getResource("/resources/oic4.png");
	public static URL IMAGE_W_LOGO = ResourceManager.class.getResource("/resources/iconw3.png");
	
	public static URL IMAGE_JETTY = ResourceManager.class.getResource("/resources/jetty.png");
	public static URL IMAGE_GLASSFISH = ResourceManager.class.getResource("/resources/glassfish.png");
	public static URL IMAGE_TOMCAT = ResourceManager.class.getResource("/resources/tomcat.png");
	
	public static URL IMAGE_SQLITE = ResourceManager.class.getResource("/resources/sqlite.png");
	public static URL IMAGE_MYSQL = ResourceManager.class.getResource("/resources/mysql.png");
	public static URL IMAGE_ORACLE = ResourceManager.class.getResource("/resources/oracle.png");
	public static URL IMAGE_POSTGRESQL = ResourceManager.class.getResource("/resources/postgresql.png");
}